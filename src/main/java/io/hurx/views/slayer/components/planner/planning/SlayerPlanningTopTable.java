package io.hurx.views.slayer.components.planner.planning;

import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.views.slayer.SlayerView;
import io.hurx.Resources;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.data.SlayerListData;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.components.Icon;
import io.hurx.components.JComboBoxCellEditor;

import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class SlayerPlanningTopTable extends DefaultTable {
    public final static int ICON_SIZE = 20;
    public final static int H_PADDING = 10;
    public final static int V_PADDING = 5;
    public final static int ROW_HEIGHT = 30;

    public SlayerPlanningData getPlanning() {
        for(SlayerPlanningData planning : view.getPanel().getCache().getData().getSlayer().getPlannings()) {
            if (planning.getUuid().equals(uuid)) {
                return planning;
            }
        }
        return null;
    }

    private SlayerView view;
    private JComboBox<SlayerListData> comboBox;
    private String uuid;

    private JComboBoxCellEditor cellEditor = new JComboBoxCellEditor() {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            super.setBox(comboBox);
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public boolean stopCellEditing() {
            view.setSelectedList(getPlanning(), ((SlayerListData)comboBox.getSelectedItem()).getUuid());
            return super.stopCellEditing();
        }
    };

    public SlayerPlanningTopTable(SlayerView view, String uuid) {
        super(DefaultTable.Options.Builder.construct().columnCount(2).build());
        this.view = view;
        this.uuid = uuid;
        fillTableModel();
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        createListComboBox();
        setRowHeight(ICON_SIZE + V_PADDING * 2);
        getColumnModel().getColumn(0).setMinWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setPreferredWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setMaxWidth(ICON_SIZE + H_PADDING * 2);
        getColumnModel().getColumn(0).setWidth(ICON_SIZE + H_PADDING * 2);

        for (int i = 0; i < getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
        getColumnModel().getColumn(1).setCellEditor(cellEditor);
        SlayerListData list = view.getPanel().getCache().getData().getSlayer().findListByUuid(getPlanning().getListUuid());

        if (list == null) return;
        DefaultTableModel model = (DefaultTableModel)getModel();
        Icon icon = new Icon(Resources.loadImageIcon(list.getMaster().getIconPath().getPath(), ICON_SIZE, ICON_SIZE));
        icon.setVPadding(V_PADDING);
        icon.setHPadding(H_PADDING);
        icon.setBackground(Theme.TABLE_BG_COLOR);
        model.addRow(new Object[] {
            icon,
            comboBox
        });
    }

    private JComboBox<?> createListComboBox() {
        SlayerPlanningTopTable table = this;
        CacheData data = view.getPanel().getCache().getData();
        List<SlayerListData> comboBoxValues = new ArrayList<>();
        SlayerListData selectedItem = null;
        for (SlayerListData list : data.getSlayer().getLists()) {
            comboBoxValues.add(list);
            if (getPlanning().getListUuid().equals(list.getUuid())) {
                selectedItem = list;
            }
        }
        comboBox = new JComboBox<>(comboBoxValues.toArray(new SlayerListData[comboBoxValues.size()]));
        comboBox.setSelectedItem(selectedItem);
        // Custom renderer to display icon and name
        comboBox.setRenderer(new ListCellRenderer<SlayerListData>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends SlayerListData> list,
                    SlayerListData value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = new JLabel();
                
                label.setText(value.getName());
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setOpaque(true);

                if (value.getUuid().equals(getPlanning().getListUuid())) {
                    label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                    label.setForeground(Color.white);
                }
                else if (isSelected) {
                    label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    label.setForeground(Color.white);
                }
                else {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    label.setForeground(Color.white);
                }

                return label;
            }
        });

        // Add an ActionListener to handle selection changes
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                SlayerListData selectedOption = (SlayerListData) comboBox.getSelectedItem();
                view.setSelectedList(getPlanning(), selectedOption.getUuid());
            }
        });

        return comboBox;
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            SlayerPlanningTopTable topTable = (SlayerPlanningTopTable) table;
            if (value instanceof Icon) {
                return (Icon)value;
            }
            else if (value instanceof JComboBox) {
                JComboBox<?> box = (JComboBox<?>) value;
                box.setBorder(BorderFactory.createEmptyBorder());
                return topTable.comboBox;
            }
            else if (value instanceof SlayerListData) {
                topTable.comboBox.setBorder(BorderFactory.createEmptyBorder());
                topTable.comboBox.setSelectedItem(value);
                topTable.view.setSelectedList(topTable.getPlanning(), ((SlayerListData)topTable.comboBox.getSelectedItem()).getUuid());
                return topTable.comboBox;
            }
            return null;
        }
    }
}
