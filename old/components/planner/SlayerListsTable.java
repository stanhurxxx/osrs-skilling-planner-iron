package io.hurx.old.components.planner;

import io.hurx.cache.data.entities.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.components.MultiComboBox;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.old.SlayerView;
import io.hurx.old.SlayerView.ComboBoxModel;
import io.hurx.plugin.PluginRepository;
import io.hurx.util.cache.Cache;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Component;
import java.util.UUID;

public class SlayerListsTable extends DefaultTable {
    public final static int ICON_SIZE = 24;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 5;

    private int hoveredColIndex = -1;
    private int hoveredRowIndex = -1;

    private SlayerView view;

    public SlayerListsTable(SlayerView view) {
        super(
            DefaultTable.Options.Builder
                .construct()
                .columnCount(2)
                .build()
        );
        this.view = view;
        SlayerListsTable table = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    PluginRepository data = Cache.getRepository();
                    Point point = e.getPoint();
                    int row = table.rowAtPoint(point);
                    int col = table.columnAtPoint(point);
                    SlayerListData list = data.getSlayer().getLists().get(row);
                    if (list == null) {
                        return;
                    }

                    // Duplicate option
                    JMenuItem duplicate = new JMenuItem("Duplicate list");
                    duplicate.addActionListener(ae -> {
                        view.duplicateList(list);
                    });
                    popupMenu.add(duplicate);

                    // Export option
                    JMenuItem export = new JMenuItem("Export list");
                    export.addActionListener(ae -> {
                        view.saveSlayerList(list);
                    });
                    popupMenu.add(export);

                    // Reset
                    JMenuItem reset = new JMenuItem("Reset list to defaults");
                    reset.addActionListener(ae -> {
                        view.resetDefaultsForList(list);
                    });
                    popupMenu.add(reset);
    
                    // Delete option
                    if (Cache.getRepository().getSlayer().getLists().size() > 1) {
                        JMenuItem delete = new JMenuItem("Delete list");
                        delete.addActionListener(ae -> {
                            view.deleteList(list);
                        });
                        popupMenu.add(delete);
                    }
    
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                else if (e.getButton() == MouseEvent.BUTTON1) {
                    PluginRepository data = Cache.getRepository();
                    Point point = e.getPoint();
                    int row = table.rowAtPoint(point);
                    int col = table.columnAtPoint(point);
                    SlayerListData list = data.getSlayer().getLists().get(row);
                    data.getSlayer().setListUuid(list.getUuid());
                    data.getSlayer().setView(null);
                    try {
                        Cache.save();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    finally {
                        view.load();
                        view.getPanel().revalidate();
                        view.getPanel().repaint();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);
                if (hoveredColIndex != col || hoveredRowIndex != row) {
                    hoveredColIndex = col;
                    hoveredRowIndex = row;
                    table.revalidate();
                    table.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredColIndex = -1;
                hoveredRowIndex = -1;
                table.revalidate();
                table.repaint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);
                if (hoveredColIndex != col || hoveredRowIndex != row) {
                    hoveredColIndex = col;
                    hoveredRowIndex = row;
                    table.revalidate();
                    table.repaint();
                }
            }
        });
        fillTableModel();
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setMinWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setWidth(ICON_SIZE + H_PADDING * 2);
        for (int i = 0; i < options.getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        DefaultTableModel model = (DefaultTableModel)getModel();
        setRowHeight(ICON_SIZE + V_PADDING * 2);
        PluginRepository data = Cache.getRepository();
        for (SlayerListData list : data.getSlayer().getLists()) {
            Icon icon = new Icon(Resources.loadImageIcon(list.getMaster().getIconPath().getFileName(), ICON_SIZE, ICON_SIZE));
            icon.setVPadding(V_PADDING);
            icon.setHPadding(H_PADDING);

            JLabel label = new JLabel(list.getName());
            label.setHorizontalAlignment(SwingConstants.CENTER);

            model.addRow(new Object[] {
                icon,
                label
            });
        }
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            SlayerListsTable thisTable = (SlayerListsTable)table;
            if (value instanceof Icon) {
                Icon icon = (Icon) value;
                icon.setOpaque(true);
                if (thisTable.hoveredRowIndex == row) {
                    icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                }
                else if (row % 2 == 0) {
                    icon.setBackground(Theme.TABLE_BG_COLOR);
                }
                else {
                    icon.setBackground(Theme.TABLE_BG_COLOR_ALT);
                }
                return (Icon)value;
            }
            else if (value instanceof JLabel) {
                JLabel label = (JLabel)value;
                label.setOpaque(true);
                if (thisTable.hoveredRowIndex == row) {
                    label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                }
                else if (row % 2 == 0) {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                }
                else {
                    label.setBackground(Theme.TABLE_BG_COLOR_ALT);
                }
                return label;
            }
            return null;
        }
    }
}
