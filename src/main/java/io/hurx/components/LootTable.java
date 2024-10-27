package io.hurx.components;

import javax.swing.*;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.models.Skills;
import io.hurx.models.View;
import io.hurx.models.items.Items;
import io.hurx.views.slayer.SlayerView;
import io.hurx.views.slayer.components.targetXP.TargetXPTable;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class LootTable extends JTable {
    public final static int ICONS_PER_ROW = 5;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 10;

    /**
     * Get the panel
     * @return
     */
    public SkillingPlannerPanel getPanel() {
        return panel;
    }

    /**
     * The ref to the main panel
     */
    private SkillingPlannerPanel panel;

    /**
     * The slayer view
     */
    private View view;

    /**
     * The item names
     */
    private List<String> itemNames;

    /**
     * Reference to all icons used
     */
    private List<Icon> icons;

    public LootTable(View view) {
        super(new DefaultTableModel());
        this.view = view;
        this.panel = view.getPanel();
        LootTable table = this;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            model.addColumn("");
        }
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        table.setDoubleBuffered(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setRowHeight(230 / ICONS_PER_ROW);
        super.setVisible(true);
        fillTableModel();
    }

    
    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        Point point = e.getPoint();
        int row = rowAtPoint(point);
        int col = columnAtPoint(point);
        int index = row * ICONS_PER_ROW + col;
        if (index >= icons.size() || index >= itemNames.size()) {
            return null;
        }
        else {
            Integer amount = icons.get(index).getAmount();
            if (amount == null) return null;

            return NumberFormat.getInstance(Locale.US).format(amount) + " x " + itemNames.get(index);
        }
    }

    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel)this.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }

        itemNames = new ArrayList<>();
        icons = new ArrayList<>();

        // TODO: dummy
        for (Items item : Items.values()) {
            Icon icon = new Icon(item.getItem().getIcon(), (int)Math.round(Math.random() * 100_000 + (Math.random() > 0.5 ? 100_000 : 0) + (Math.random() > 0.5 ? 10_000_000 : 0)));
            icon.setOpaque(true);
            icon.setHPadding(H_PADDING);
            icon.setVPadding(V_PADDING);
            icon.setBackground(Theme.TABLE_BG_COLOR);
            icon.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
            icons.add(icon);
            itemNames.add(item.getItem().getName());
        }

        Icon icon = new Icon(Items.Coins.getItem().getIcon(), 1_337_000_000);
        icon.setOpaque(true);
        icon.setHPadding(H_PADDING);
        icon.setVPadding(V_PADDING);
        icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
        icon.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
        icons.add(0, icon);
        itemNames.add(0, Items.Coins.getItem().getName());

        for (int i = 0; i < 3; i ++) {
            List<Object> cells = new ArrayList<>();

            for (int j = 0; j < ICONS_PER_ROW; j ++) {
                int k = i * ICONS_PER_ROW + j;
                if (k >= icons.size()) {
                    JLabel label = new JLabel("");
                    label.setOpaque(true);
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    label.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
                    cells.add(label);
                }
                else {
                    cells.add(icons.get(k));
                }
            }

            model.addRow(cells.toArray());
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
            label.setOpaque(true);
            label.setBackground(Theme.TABLE_BG_COLOR);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            if (value instanceof Icon) {
                return (Icon) value;
            }
            else if (value instanceof JLabel) {
                return (JLabel) value;
            }
            return label;
        }
    }
}
