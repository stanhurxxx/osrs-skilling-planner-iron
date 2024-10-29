package io.hurx.views.slayer.components.manage.xpRates;

import io.hurx.Resources;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.data.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.SlayerBracelets;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.views.slayer.SlayerView;
import io.hurx.views.slayer.components.list.tasks.TasksTable;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

public class XpRateMonsters extends DefaultTable {
    public final static int ICON_SIZE = 25;
    public static final int ICONS_PER_ROW = 5;
    public static final int V_PADDING = 10;
    public static final int H_PADDING = 10;

    private List<Monsters> monsters;

    private SlayerView view;

    public XpRateMonsters(SlayerView view, List<Monsters> monsters) {
        super(DefaultTable.Options.Builder.construct().columnCount(ICONS_PER_ROW).build());
        this.monsters = monsters;
        this.view = view;
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
        if (index == 1 * 5 + 4) {
            return (monsters.size() - index - 1) + " more";
        }
        if (index >= monsters.size()) {
            return null;
        }
        else {
            return monsters.get(index).getDescription();
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
        loop: for (int i = 0; i <= Math.ceil((monsters.size() - 1) / ICONS_PER_ROW); i ++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < 5; j ++) {
                int index = i * ICONS_PER_ROW + j;
                if (index == 1 * 5 + 4) {
                    JLabel label = new JLabel("+" + (monsters.size() - index - 1));
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    row.add(label);
                    model.addRow(row.toArray());
                    break loop;
                }
                if (index > monsters.size() - 1) {
                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    row.add(label);
                }
                else {
                    Icon icon = new Icon(Resources.loadImageIcon(monsters.get(index).getIconPath().getPath(), 100, 100));
                    icon.setOpaque(true);
                    icon.setBackground(Theme.TABLE_BG_COLOR);
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
            if (value instanceof JComponent) {
                if (value instanceof Icon) {
                    Icon icon = (Icon) value;
                    icon.setBackground(Theme.TABLE_BG_COLOR);
                }
                return (JComponent)value;
            }
            return label;
        }
    } 
}
