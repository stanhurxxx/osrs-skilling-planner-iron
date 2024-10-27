package io.hurx.views.slayer.components.variants;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.components.Icon;
import io.hurx.components.numberSlider.NumberSlider;
import io.hurx.components.numberSlider.NumberSliderCellEditor;
import io.hurx.components.numberSlider.NumberSliderFormat;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;

import java.awt.*;
import java.util.*;

public class VariantTable extends JTable {
    public final static int ICON_SIZE = 28;
    public final static int H_PADDING = 8;
    public final static int V_PADDING = 7;

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

    /**
     * The slider cell editor
     */
    private NumberSliderCellEditor sliderCellEditor = new NumberSliderCellEditor() {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            NumberSlider slider = sliders.get(row); // Get the appropriate slider for this row
            if (slider == null) {
                setIndex(-1);
                setSlider(null);
                return null;
            }
            super.setSlider(slider);       // Set it in the editor
            super.setIndex(row);
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public boolean stopCellEditing() {
            int index = getIndex();
            if (index == -1) return false;

            CacheData data = panel.getCache().getData();
            SlayerMonsters monster = data.slayerSelectedMonster;
            System.out.println("2");
            if (monster == null) return false;

            Monsters[] monsters = data.slayerMonsterVariationOrder.get(monster);
            System.out.println("3");
            if (index >= monsters.length) return false;

            view.setVariation(monsters[index], getSlider().getCurrentValue());

            return true;
        }
    };

    private java.util.List<NumberSlider> sliders = new ArrayList<>();

    public VariantTable(SlayerView view) {
        super(new DefaultTableModel());
        VariantTable table = this;
        this.panel = view.getPanel();
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
        super.getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMinWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(1).setCellEditor(sliderCellEditor);
        
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (hoveredColumnIndex == 0) {
                    CacheData data = panel.getCache().getData();
                    Monsters[] monsters = data.slayerMonsterVariationOrder.get(data.slayerSelectedMonster);
                    Monsters monster = monsters[hoveredRowIndex];
                    view.setSelectedVariant(monster);
                }
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredColumnIndex = -1;
                hoveredRowIndex = -1;
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
    public String getToolTipText(java.awt.event.MouseEvent e) {
        // Get the location of the mouse event
        Point p = e.getPoint();
        // Find out which row and column the mouse is over
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        SlayerAssignment[] assignments = view.getSlayerAssignments();
        CacheData data = panel.getCache().getData();
        SlayerMonsters monster = data.slayerSelectedMonster;
        SlayerAssignment assignment = null;

        if (monster == null) return null;

        for (SlayerAssignment a : assignments) {
            if (a.getMonster() == monster) {
                assignment = a;
                break;
            }
        }

        if (assignment == null) return null;

        Monsters[] monsters = data.slayerMonsterVariationOrder.get(monster);
        Monsters variant = monsters[rowIndex];
        
        if (variant == null) return null;

        switch (colIndex) {
            case 0: {
                return variant.getDescription();
            }
            case 1: {
                return "Percentage of the task to kill this variant";
            }
        }

        return null;
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return false; // Prevent cell selection
    }

    public void fillTableModel() {
        DefaultTableModel model = (DefaultTableModel)this.getModel();

        sliders.clear();
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }

        CacheData data = panel.getCache().getData();
        SlayerMonsters monster = data.slayerSelectedMonster;
        
        if (monster == null) return;

        Map<Monsters, Integer> variation = data.slayerMonsterVariation.get(monster);

        if (variation == null) return;

        Monsters[] monsters = data.slayerMonsterVariationOrder.get(monster);

        for (Monsters variant : monsters) {
            Icon icon = new Icon(Resources.loadImageIcon(variant.getIconPath().getPath(), ICON_SIZE, ICON_SIZE), (int)Math.round(Math.random() * 100_000 + (Math.random() > 0.5 ? 1_000_000 : 0)));
            icon.setHPadding(H_PADDING);
            icon.setVPadding(V_PADDING);
            
            NumberSlider slider = new NumberSlider();
            slider.setFormat(NumberSliderFormat.Percentage);
            slider.setMinValue(0);
            slider.setMaxValue(100);
            slider.setCurrentValue(variation.get(variant));

            sliders.add(slider);

            model.addRow(
                new Object[] {
                    icon,
                    slider
                }
            );
        }
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
            
            VariantTable variantTable = (VariantTable)table;
            if (value instanceof Icon) {
                CacheData data = variantTable.panel.getCache().getData();
                Monsters[] monsters = data.slayerMonsterVariationOrder.get(data.slayerSelectedMonster);
                Monsters monster = monsters[row];
                Icon icon = (Icon) value;
                icon.setBackground(Theme.TABLE_BG_COLOR);
                if (data.slayerSelectedVariant == monster) {
                    icon.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                }
                else if (row == variantTable.hoveredRowIndex && column == 0 && column == variantTable.hoveredColumnIndex) {
                    icon.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                }
                else {
                    icon.setBackground(Theme.TABLE_BG_COLOR);
                }
                return icon;
            }
            else if (value instanceof Integer) {
                NumberSlider slider = variantTable.sliders.get(row);
                slider.setCurrentValue((Integer)value);
                return slider;
            }
            else if (value instanceof NumberSlider) {
                NumberSlider slider = variantTable.sliders.get(row);
                return slider;
            }
            
            return label;
        }
    }
}
