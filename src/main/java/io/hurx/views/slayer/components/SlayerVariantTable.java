package io.hurx.views.slayer.components;

import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSlider;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.JNumberSlider;
import io.hurx.components.JNumberSliderCellEditor;
import io.hurx.models.slayer.masters.Duradel;
import io.hurx.models.slayer.masters.Nieve;
import io.hurx.models.slayer.masters.SlayerMaster;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SlayerVariantTable extends JTable {
    public final static int ICON_SIZE = 32;
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
     * The hovered column index
     */
    private int hoveredColumnIndex = -1;

    /**
     * The hovered row index
     */
    private int hoveredRowIndex = -1;

    /**
     * The view
     */
    private SlayerView view;

    public SlayerVariantTable(SlayerView view, SkillingPlannerPanel panel) {
        super(new DefaultTableModel());
        SlayerVariantTable table = this;
        this.panel = panel;
        this.view = view;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        super.setFillsViewportHeight(true);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        super.setVisible(true);
        for (int i = 0; i < 2; i ++) {
            model.addColumn("");
        }
        for (int i = 0; i < 2; i ++) {
            super.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        super.setRowHeight(ICON_SIZE + V_PADDING * 2);
        super.setOpaque(true);
        super.setBackground(Theme.TABLE_BG_COLOR);
        super.getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING);
        super.getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING);
        super.getColumnModel().getColumn(1).setCellEditor(new JNumberSliderCellEditor());
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (hoveredColumnIndex == 0) {
                    panel.getCache().getData().slayerSelectedVariant = getVariants()[hoveredRowIndex];
                    try {
                        panel.getCache().save();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);
                if (col != hoveredColumnIndex || row != hoveredRowIndex) {
                    hoveredColumnIndex = col;
                    hoveredRowIndex = row;
                }
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        });
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
        for (SlayerMonsters variant : getVariants()) {
            JTextField textField = new JTextField();
            textField.setText("100.000");
            model.addRow(
                new Object[] {
                    Resources.loadImageIcon(variant.getIconPath(), ICON_SIZE, ICON_SIZE),
                    new JNumberSlider()
                }
            );
        }
    }

    private SlayerMonster getCurrentSlayerMonster() {
        SlayerMonsters monster = panel.getCache().getData().slayerSelectedMonster;
        SlayerMasters slayerMaster = panel.getCache().getData().slayerMaster;
        SlayerMaster master = slayerMaster == SlayerMasters.Duradel ? new Duradel() : new Nieve();
        SlayerMonster assignment = null;
        for (int i = 0; i < master.getAssignments().length; i ++) {
            SlayerMonster s = master.getAssignments()[i];
            if (s.name == monster) {
                return s;
            }
        }
        return null;
    }

    private SlayerMonsters[] getVariants() {
        SlayerMonster monster = getCurrentSlayerMonster();
        return monster == null ? new SlayerMonsters[0] : monster.variants;
    }

    /**
     *  Custom cell renderer for the icon column
     */
    public static class CellRenderer extends DefaultTableCellRenderer {
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
                label.setBorder(BorderFactory.createEmptyBorder(V_PADDING, H_PADDING, V_PADDING, H_PADDING * 2));
            }
            else if (value instanceof Integer) {
                JNumberSlider slider = new JNumberSlider();
                slider.setBorder(BorderFactory.createEmptyBorder(V_PADDING, 0, V_PADDING, 0));
                slider.setPreferredSize(new Dimension(getWidth() - (H_PADDING * 2), JNumberSlider.SIZE + (V_PADDING * 2)));
                slider.setCurrentValue((Integer)value);
                return slider;
            }
            else if (value instanceof JNumberSlider) {
                JNumberSlider slider = (JNumberSlider) value;
                slider.setOpaque(true);
                slider.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                return slider;
            }
            
            return label;
        }
    }
}
