package io.hurx.views.slayer.components.list.variants;

import java.awt.Component;
import java.awt.Point;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.data.SlayerListData;
import io.hurx.components.Icon;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextField;
import io.hurx.components.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextFieldCellEditor;
import io.hurx.components.numberSlider.NumberSlider;
import io.hurx.components.numberSlider.NumberSliderFormat;
import io.hurx.components.numberSlider.NumberSliderCellEditor;
import io.hurx.models.CombatStyle;
import io.hurx.models.IconPaths;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.SlayerView;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import io.hurx.components.table.defaultTable.DefaultTable;

public class CombatStyleTable extends DefaultTable {
    public final static int ICON_SIZE = 24;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 5;

    public int hoveredColumnIndex = -1;
    public int hoveredRowIndex = -1;

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

    private List<AbbreviatedNumberFormattedTextField> textFields = new ArrayList<>();

    private AbbreviatedNumberFormattedTextFieldCellEditor textFieldCellEditor = new AbbreviatedNumberFormattedTextFieldCellEditor() {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            AbbreviatedNumberFormattedTextField textField = textFields.get(row); // Get the appropriate slider for this row
            if (textField == null) {
                setIndex(-1);
                setTextField(null);
                return null;
            };
            super.setTextField(textField); // Set it in the editor
            super.setIndex(row);
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public boolean stopCellEditing() {
            int index = getIndex();
            if (index == -1) return false;

            CacheData data = panel.getCache().getData();
            SlayerListData list = data.getSlayer().getList();
            if (list == null) return false;

            SlayerMonsters monster = list.getSelectedMonster();
            if (monster == null) return false;

            view.setXpRate(
                index == 0
                    ? CombatStyle.Attack
                    : index == 1
                        ? CombatStyle.Ranged
                        : CombatStyle.Magic,
                getTextField().getValueAsInt()
            );

            return super.stopCellEditing();
        }
    };

    private NumberSlider slider;

    /**
     * The slider cell editor
     */
    private NumberSliderCellEditor sliderCellEditor = new NumberSliderCellEditor() {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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
            SlayerListData list = data.getSlayer().getList();
            if (list == null) return false;
            
            SlayerMonsters monster = list.getSelectedMonster();
            if (monster == null) return false;

            Monsters[] monsters = list.getVariationOrders().get(monster);
            if (index >= monsters.length) return false;

            view.setCompletionTime(getSlider().getCurrentValue());

            return super.stopCellEditing();
        }
    };

    public CombatStyleTable(SlayerView view) {
        super(DefaultTable.Options.Builder.construct().columnCount(2).build());
        CombatStyleTable table = this;
        this.panel = view.getPanel();
        this.view = view;
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < 2; i ++) {
            model.addColumn("");
        }
        super.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());
        super.setRowHeight(ICON_SIZE + V_PADDING * 2);
        super.setOpaque(true);
        super.setBackground(Theme.TABLE_BG_COLOR);
        super.getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMinWidth(ICON_SIZE + H_PADDING * 2);
        super.getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING * 2);
        super.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                CacheData data = panel.getCache().getData();
                SlayerListData list = data.getSlayer().getList();
                if (list == null) return;

                Monsters monster = list.getSelectedVariant();
                if (monster == null) return;

                if (monster.getStats().getSlayer() == null) {
                    if (hoveredColumnIndex == 0) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        switch (hoveredRowIndex) {
                            case 0: {
                                CombatStyle style = list.getMeleeStyles().get(monster);
                                for (CombatStyle combatStyle : CombatStyle.getMeleeStyles()) {
                                    if (combatStyle == style) continue;
    
                                    JMenuItem item = new JMenuItem(combatStyle.getName());
                                    item.addActionListener(ae -> {
                                        view.setCombatStyleForVariant(combatStyle);
                                    });
    
                                    popupMenu.add(item);
                                }
                                break;
                            }
                            case 1: {
                                CombatStyle style = list.getRangedStyles().get(monster);
                                for (CombatStyle combatStyle : CombatStyle.getRangedStyles()) {
                                    if (combatStyle == style) continue;
    
                                    JMenuItem item = new JMenuItem(combatStyle.getName());
                                    item.addActionListener(ae -> {
                                        view.setCombatStyleForVariant(combatStyle);
                                    });
    
                                    popupMenu.add(item);
                                }
                                break;
                            }
                            case 2: {
                                CombatStyle style = list.getMagicStyles().get(monster);
                                for (CombatStyle combatStyle : CombatStyle.getMagicStyles()) {
                                    if (combatStyle == style) continue;
    
                                    JMenuItem item = new JMenuItem(combatStyle.getName());
                                    item.addActionListener(ae -> {
                                        view.setCombatStyleForVariant(combatStyle);
                                    });
    
                                    popupMenu.add(item);
                                }
                                break;
                            }
                        }
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }

                    table.clearSelection();
                    table.revalidate();
                    table.repaint();
                }
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
                CacheData data = panel.getCache().getData();
                SlayerListData list = data.getSlayer().getList();
                if (list == null) return;
                if (list.getSelectedVariant().getStats().getSlayer() == null) {
                    if (col != hoveredColumnIndex || row != hoveredRowIndex) {
                        hoveredColumnIndex = col;
                        hoveredRowIndex = row;
                    }
                }
                else {
                    hoveredColumnIndex = -1;
                    hoveredRowIndex = -1;
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

        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return null;
        Monsters monster = list.getSelectedVariant();
        if (monster.getStats().getSlayer() == null) {
            switch (rowIndex) {
                case 0: {
                    switch (colIndex) {
                        case 0: {
                            return list.getMeleeStyles().get(monster).getName();
                        }
                        case 1: {
                            return "Set a melee xp rate for this monster";
                        }
                    }
                }
                case 1: {
                    switch (colIndex) {
                        case 0: {
                            return list.getRangedStyles().get(monster).getName();
                        }
                        case 1: {
                            return "Set a ranged xp rate for this monster";
                        }
                    }
                }
                case 2: {
                    switch (colIndex) {
                        case 0: {
                            return list.getMagicStyles().get(monster).getName();
                        }
                        case 1: {
                            return "Set a magic xp rate for this monster";
                        }
                    }
                }
            }
        }
        else {
            switch (rowIndex) {
                case 0: {
                    switch (colIndex) {
                        case 1: {
                            return "Set a completion time for this run";
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return false; // Prevent cell selection
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        
        DefaultTableModel model = (DefaultTableModel)this.getModel();

        textFields.clear();
        slider = null;
        for (int i = model.getRowCount() - 1; i >= 0; i --) {
            model.removeRow(i);
        }

        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        SlayerMonsters monster = list.getSelectedMonster();
        Monsters variant = list.getSelectedVariant();
        
        // No monster variant selected
        if (variant == null) return;
        
        SlayerMasters slayerMaster = list.getMaster();
        SlayerAssignment assignment = null;
        for (int i = 0; i < slayerMaster.getMaster().getAssignments().length; i ++) {
            SlayerAssignment s = slayerMaster.getMaster().getAssignments()[i];
            if (s.getMonster() == monster) {
                assignment = s;
            }
        }

        // Not an assignment of this slayer master
        if (assignment == null) {
            view.setSelectedMonster(null);
            return;
        }

        if (variant.getStats().getSlayer() == null) {
            super.getColumnModel().getColumn(1).setCellRenderer(new TextFieldCellRenderer());
            super.getColumnModel().getColumn(1).setCellEditor(textFieldCellEditor);

            CombatStyle meleeStyle = list.getMeleeStyles().get(variant);
            int meleeXpPerHour = list.getMeleeHourlyRates().get(variant);
            CombatStyle rangedStyle = list.getRangedStyles().get(variant);
            int rangedXpPerHour = list.getRangedHourlyRates().get(variant);
            CombatStyle magicStyle = list.getMagicStyles().get(variant);
            int magicXpPerHour = list.getMagicHourlyRates().get(variant);
            
            AbbreviatedNumberFormattedTextField textFieldMelee = new AbbreviatedNumberFormattedTextField();
            textFieldMelee.setValue((Integer)meleeXpPerHour);
            textFields.add(textFieldMelee);
            AbbreviatedNumberFormattedTextField textFieldRanged = new AbbreviatedNumberFormattedTextField();
            textFieldRanged.setValue((Integer)rangedXpPerHour);
            textFields.add(textFieldRanged);
            AbbreviatedNumberFormattedTextField textFieldMagic = new AbbreviatedNumberFormattedTextField();
            textFieldMagic.setValue((Integer)magicXpPerHour);
            AbbreviatedNumberFormattedTextField textFieldMinutes = new AbbreviatedNumberFormattedTextField();
            textFieldMinutes.setValue((Integer) 180);
            textFields.add(textFieldMagic);
            for (AbbreviatedNumberFormattedTextField textField : textFields) {
                textFieldCellEditor.configureTextField(textField);
            }
            
            Icon meleeIcon = new Icon(Resources.loadImageIcon(meleeStyle.getIconPath().getPath(), ICON_SIZE, ICON_SIZE));
            Icon rangedIcon = new Icon(Resources.loadImageIcon(rangedStyle.getIconPath().getPath(), ICON_SIZE, ICON_SIZE));
            Icon magicIcon = new Icon(Resources.loadImageIcon(magicStyle.getIconPath().getPath(), ICON_SIZE, ICON_SIZE));
            for (Icon icon : new Icon[] { meleeIcon, rangedIcon, magicIcon }) {
                icon.setHPadding(H_PADDING);
                icon.setVPadding(V_PADDING);
                icon.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
            }
            model.addRow(new Object[] {
                meleeIcon,
                textFieldMelee
            });
            model.addRow(new Object[] {
                rangedIcon,
                textFieldRanged
            });
            model.addRow(new Object[] {
                magicIcon,
                textFieldMagic
            });
        }

        // Jad / Zuk
        else {
            super.getColumnModel().getColumn(1).setCellRenderer(new JNumberSliderCellRenderer());
            super.getColumnModel().getColumn(1).setCellEditor(sliderCellEditor);

            slider = new NumberSlider();
            slider.setMinValue(1);
            slider.setMaxValue(120);
            slider.setCurrentValue(list.getMonsterCompletionTimes().get(variant));
            slider.setFormat(NumberSliderFormat.Minutes);

            Icon timeIcon = new Icon(Resources.loadImageIcon(IconPaths.SkillEHP.getPath(), ICON_SIZE, ICON_SIZE));
            timeIcon.setHPadding(H_PADDING);
            timeIcon.setVPadding(V_PADDING);
            timeIcon.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
            
            model.addRow(new Object[] {
                timeIcon,
                slider
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
            
            if (value instanceof Icon) {
                Icon icon = (Icon)value;
                CombatStyleTable styleTable = (CombatStyleTable) table;
                if (styleTable.hoveredColumnIndex == column && styleTable.hoveredRowIndex == row) {
                    icon.setBackground(Theme.TABLE_BG_COLOR);
                }
                else {
                    icon.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                }
                return icon;
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
            CombatStyleTable styleTable = (CombatStyleTable) table;
            if (value instanceof AbbreviatedNumberFormattedTextField) {
                AbbreviatedNumberFormattedTextField textField = (AbbreviatedNumberFormattedTextField) value;
                return textField;
            }
            else {
                AbbreviatedNumberFormattedTextField textField = styleTable.textFields.get(row);
                textField.setValue(value);
                styleTable.view.setXpRate(
                    row == 0
                        ? CombatStyle.Attack
                        : row == 1
                            ? CombatStyle.Ranged
                            : CombatStyle.Magic,
                    textField.getValueAsInt()
                );
                return textField;
            }
        }
    }

    public static class JNumberSliderCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            CombatStyleTable styleTable = (CombatStyleTable) table;
            if (value instanceof NumberSlider) {
                NumberSlider slider = (NumberSlider) value;
                return slider;
            }
            else {
                NumberSlider slider = styleTable.slider;
                if (value instanceof Integer) {
                    slider.setCurrentValue((Integer)value);
                    styleTable.view.setCompletionTime((Integer) value);
                }
                return slider;
            }
        }
    }
}
