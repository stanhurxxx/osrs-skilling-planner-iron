package io.hurx.views.slayer.components.targetXP;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.Icon;
import io.hurx.models.Skills;
import io.hurx.views.slayer.SlayerView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;
import io.hurx.components.table.defaultTable.DefaultTable;

public class TargetXPTable extends DefaultTable {
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
    private SlayerView view;

    private List<Icon> icons;

    private List<Skills> skills = List.of(new Skills[] {
        Skills.Slayer,
        Skills.Attack,
        Skills.Strength,
        Skills.Defence,
        Skills.Ranged,
        Skills.Magic,
        Skills.Hitpoints,
        Skills.Prayer,
        Skills.Herblore
    });

    public TargetXPTable(SlayerView view) {
        super(DefaultTable.Options.Builder.construct().columnCount(ICONS_PER_ROW).build());
        this.panel = view.getPanel();
        this.view = view;
        TargetXPTable table = this;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            model.addColumn("");
        }
        for (int i = 0; i < ICONS_PER_ROW; i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        super.setRowHeight(230 / ICONS_PER_ROW);
        fillTableModel();
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent e) {
        Point point = e.getPoint();
        int row = rowAtPoint(point);
        int col = columnAtPoint(point);
        int index = row * ICONS_PER_ROW + col;
        if (index >= icons.size() || index >= skills.size()) {
            return null;
        }
        else {
            Integer amount = icons.get(index).getAmount();
            if (amount == null) return null;

            return skills.get(index).getName() + ": " + NumberFormat.getInstance(Locale.US).format(amount);
        }
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel)this.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }

        icons = new ArrayList<>();

        for (Skills skill : skills) {
            Icon icon = new Icon(Resources.loadImageIcon(skill.getIconPath().getPath(), (getWidth() - H_PADDING * 2) / ICONS_PER_ROW, (getWidth() - V_PADDING * 2) / ICONS_PER_ROW), 200_000_000);
            icon.setOpaque(true);
            icon.setHPadding(H_PADDING);
            icon.setVPadding(V_PADDING);
            icon.setBackground(Theme.TABLE_BG_COLOR);
            icon.setSize(getWidth() / ICONS_PER_ROW, getWidth() / ICONS_PER_ROW);
            icons.add(icon);
        }

        for (int i = 0; i <= Math.ceil((icons.size() - 1) / ICONS_PER_ROW); i ++) {
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
