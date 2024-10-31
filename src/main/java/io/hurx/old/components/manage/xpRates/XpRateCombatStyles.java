package io.hurx.old.components.manage.xpRates;

import io.hurx.cache.data.entities.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.models.CombatStyle;
import io.hurx.old.SlayerView;
import io.hurx.util.cache.Cache;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

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

public class XpRateCombatStyles extends DefaultTable {
    public final static int ICON_SIZE = 25;
    public static final int ICONS_PER_ROW = 5;
    public static final int V_PADDING = 10;
    public static final int H_PADDING = 10;

    private int hoveredRowIndex = -1;
    private int hoveredColumnIndex = -1;

    private SlayerView view;

    public List<CombatStyle> getStyles() {
        SlayerListData slayerList = Cache.getRepository().getSlayer().getList();
        if (slayerList == null) return new ArrayList<>();
        List<CombatStyle> styles = (
            CombatStyle.getMeleeStyles().contains(slayerList.getCombatStyleFilter())
                ? CombatStyle.getMeleeStyles()
                : CombatStyle.getRangedStyles().contains(slayerList.getCombatStyleFilter())
                    ? CombatStyle.getRangedStyles()
                    : CombatStyle.getMagicStyles()
        );
        List<CombatStyle> filtered = new ArrayList<>();
        for (CombatStyle style : styles) {
            if (style == slayerList.getCombatStyleFilter()) {
                continue;
            }
            filtered.add(style);
        }
        return filtered;
    }

    public XpRateCombatStyles(SlayerView view) {
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
        if (index == 1 * 5 + 4) {
            return (getStyles().size() - index - 1) + " more";
        }
        if (index >= getStyles().size()) {
            return null;
        }
        else {
            return getStyles().get(index).getName();
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
        
        List<CombatStyle> styles = getStyles();
        for (int i = 0; i <= Math.ceil((styles.size() - 1) / ICONS_PER_ROW); i ++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < 5; j ++) {
                int index = i * ICONS_PER_ROW + j;
                if (index > styles.size() - 1) {
                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    row.add(label);
                }
                else {
                    Icon icon = new Icon(Resources.loadImageIcon(styles.get(index).getIconPath().getPath(), 256, 256));
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
            XpRateCombatStyles styles = (XpRateCombatStyles) table;
            if (value instanceof JComponent) {
                if (value instanceof Icon) {
                    Icon icon = (Icon) value;
                    if (styles.hoveredColumnIndex == column && styles.hoveredRowIndex == row) {
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
