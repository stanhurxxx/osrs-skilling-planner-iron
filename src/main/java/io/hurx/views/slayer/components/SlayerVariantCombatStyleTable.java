package io.hurx.views.slayer.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTable;
import io.hurx.components.AbbreviatedNumberFormattedTextField;
import javax.swing.DefaultCellEditor;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.JNumberSlider;
import io.hurx.components.JNumberSliderCellEditor;
import io.hurx.components.AbbreviatedNumberFormattedTextFieldCellEditor;
import io.hurx.models.CombatStyle;
import io.hurx.models.slayer.masters.Duradel;
import io.hurx.models.slayer.masters.Nieve;
import io.hurx.models.slayer.masters.SlayerMaster;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;
import javax.swing.border.LineBorder;

public class SlayerVariantCombatStyleTable extends JTable {
    public final static int ICON_SIZE = 24;
    public final static int H_PADDING = 5;
    public final static int V_PADDING = 5;

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
     * The view
     */
    private SlayerView view;

    public SlayerVariantCombatStyleTable(SlayerView view, SkillingPlannerPanel panel) {
        super(new DefaultTableModel());
        SlayerVariantCombatStyleTable table = this;
        this.panel = panel;
        this.view = view;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        for (int i = 0; i < 2; i ++) {
            model.addColumn("");
        }
        super.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());
        super.getColumnModel().getColumn(1).setCellRenderer(new TextFieldCellRenderer());
        super.setRowHeight(ICON_SIZE + V_PADDING * 2);
        super.setOpaque(true);
        super.setBackground(Theme.TABLE_BG_COLOR);
        super.getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING);
        super.getColumnModel().getColumn(0).setMinWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(1).setCellEditor(new AbbreviatedNumberFormattedTextFieldCellEditor());
        fillTableModel();
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return false; // Prevent cell selection
    }

    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }
        SlayerMonsters monster = panel.getCache().getData().slayerSelectedMonster;
        SlayerMasters slayerMaster = panel.getCache().getData().slayerMaster;
        SlayerMaster master = slayerMaster == SlayerMasters.Duradel ? new Duradel() : new Nieve();
        SlayerMonster assignment = null;
        for (int i = 0; i < master.getAssignments().length; i ++) {
            SlayerMonster s = master.getAssignments()[i];
            if (s.name == monster) {
                assignment = s;
            }
        }
        if (assignment != null) {
            // TODO:
            AbbreviatedNumberFormattedTextField textFieldMelee = new AbbreviatedNumberFormattedTextField();
            textFieldMelee.setValue((Integer)100_000);
            AbbreviatedNumberFormattedTextField textFieldRanged = new AbbreviatedNumberFormattedTextField();
            textFieldRanged.setValue((Integer)100_000);
            AbbreviatedNumberFormattedTextField textFieldMagic = new AbbreviatedNumberFormattedTextField();
            textFieldMagic.setValue((Integer)100_000);
            model.addRow(new Object[] {
                Resources.loadImageIcon(CombatStyle.Controlled.getIconPath(), ICON_SIZE, ICON_SIZE),
                textFieldMelee
            });
            model.addRow(new Object[] {
                Resources.loadImageIcon(CombatStyle.Ranged.getIconPath(), ICON_SIZE, ICON_SIZE),
                textFieldRanged
            });
            model.addRow(new Object[] {
                Resources.loadImageIcon(CombatStyle.Magic.getIconPath(), ICON_SIZE, ICON_SIZE),
                textFieldMagic
            });
        }
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class IconCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setOpaque(true);
            label.setBackground(Theme.TABLE_BG_COLOR);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            
            if (value instanceof ImageIcon) {
                label.setIcon((ImageIcon) value);
                label.setText(""); // Clear the text
            }

            return label;
        }
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class TextFieldCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof AbbreviatedNumberFormattedTextField) {
                AbbreviatedNumberFormattedTextField textField = (AbbreviatedNumberFormattedTextField) value;
                textField.setOpaque(true);
                textField.setBackground(Theme.TABLE_BG_COLOR);
                textField.setForeground(Color.white);
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBorder(BorderFactory.createEmptyBorder(V_PADDING, H_PADDING, V_PADDING, H_PADDING));
                return textField;
            }
            else {
                AbbreviatedNumberFormattedTextField textField = new AbbreviatedNumberFormattedTextField();
                textField.setOpaque(true);
                textField.setBackground(Theme.TABLE_BG_COLOR);
                textField.setForeground(Color.white);
                textField.setValue(value);
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBorder(BorderFactory.createEmptyBorder(V_PADDING, H_PADDING, V_PADDING, H_PADDING));
                return textField;
            }
        }
    }
}
