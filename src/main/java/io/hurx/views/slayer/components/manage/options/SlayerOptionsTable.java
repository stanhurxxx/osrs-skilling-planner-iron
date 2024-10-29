package io.hurx.views.slayer.components.manage.options;

import io.hurx.Resources;
import io.hurx.Theme;
import io.hurx.cache.data.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.views.slayer.SlayerOptions;
import io.hurx.views.slayer.SlayerView;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SlayerOptionsTable extends DefaultTable {
    public final static int ICON_SIZE = 25;
    public static final int ICONS_PER_ROW = 5;
    public static final int V_PADDING = 10;
    public static final int H_PADDING = 10;

    private int hoveredRowIndex = -1;
    private int hoveredColumnIndex = -1;

    private SlayerView view;

    public List<SlayerOptions> getOptions() {
        return List.of(SlayerOptions.values());
    }

    public SlayerOptionsTable(SlayerView view) {
        super(DefaultTable.Options.Builder.construct().columnCount(ICONS_PER_ROW).build());
        this.view = view;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                int col = columnAtPoint(point);
                if (hoveredRowIndex != row || hoveredColumnIndex != col) {
                    hoveredRowIndex = row;
                    hoveredColumnIndex = col;
                    revalidate();
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRowIndex = -1;
                hoveredColumnIndex = -1;
                revalidate();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                int col = columnAtPoint(point);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    SlayerListData list = view.getPanel().getCache().getData().getSlayer().getList();
                    if (list == null) return;

                    SlayerOptions option = getOptions().get(row * ICONS_PER_ROW + col);

                    if (list.getOptions().contains(option)) {
                        list.getOptions().remove(option);
                    }
                    else {
                        SlayerOptions[][] conflicting = new SlayerOptions[][] {
                            new SlayerOptions[] {
                                SlayerOptions.Augury,
                                SlayerOptions.MysticMight
                            },
                            new SlayerOptions[] {
                                SlayerOptions.Rigour,
                                SlayerOptions.EagleEye
                            }
                        };
                        for (SlayerOptions[] options : conflicting) {
                            boolean found = false;
                            for (SlayerOptions o : options) {
                                if (o == option) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                for (SlayerOptions o : options) {
                                    if (list.getOptions().contains(o)) list.getOptions().remove(o);
                                }
                            }
                        }
                        list.getOptions().add(option);
                    }

                    try {
                        view.getPanel().getCache().save();
                        view.updateManageXPRatesOptionsSubView();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int row = rowAtPoint(point);
                int col = columnAtPoint(point);
                if (hoveredRowIndex != row || hoveredColumnIndex != col) {
                    hoveredRowIndex = row;
                    hoveredColumnIndex = col;
                    revalidate();
                    repaint();
                }
            }
        });
        fillTableModel();
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        // Get the location of the mouse event
        Point p = e.getPoint();
        // Find out which row and column the mouse is over
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        int index = rowIndex * ICONS_PER_ROW + colIndex;

        if (index >= getOptions().size()) {
            return null;
        }
        else {
            return getOptions().get(index).getDescription();
        }
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            super.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        setRowHeight(Theme.TABLE_V_PADDING * 2 + ICON_SIZE);
        
        List<SlayerOptions> options = getOptions();
        for (int i = 0; i <= Math.ceil((options.size() - 1) / ICONS_PER_ROW); i ++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < 5; j ++) {
                int index = i * ICONS_PER_ROW + j;
                if (index > options.size() - 1) {
                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    row.add(label);
                }
                else {
                    Icon icon = new Icon(options.get(index).getImage());
                    icon.setVPadding(V_PADDING);
                    icon.setHPadding(H_PADDING);
                    icon.setOpaque(true);
                    row.add(icon);
                }
            }
            model.addRow(row.toArray());
        }
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SlayerOptionsTable styles = (SlayerOptionsTable) table;
            if (value instanceof JComponent) {
                if (value instanceof Icon) {
                    Icon icon = (Icon) value;
                    SlayerListData list = styles.view.getPanel().getCache().getData().getSlayer().getList();
                    if (list == null) return null;
                    if (list.getOptions().contains(styles.getOptions().get(row * ICONS_PER_ROW + column))) {
                        icon.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                    }
                    else if (styles.hoveredColumnIndex == column && styles.hoveredRowIndex == row) {
                        icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    }
                    else {
                        icon.setBackground(Theme.TABLE_BG_COLOR);
                    }
                }
                return (JComponent)value;
            }
            return label;
        }
    } 
}
