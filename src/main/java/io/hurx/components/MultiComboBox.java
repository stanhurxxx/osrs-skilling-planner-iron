package io.hurx.components;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.views.slayer.SlayerView.ComboBoxModel;

public class MultiComboBox extends DefaultTable {
    public JComboBox<?>[] getControls() {
        return this.controls;
    }
    
    private JComboBox<?>[] controls;

    public MultiComboBox(JComboBox<?>[] controls) {
        super(DefaultTable.Options.Builder.construct().build());
        this.controls = controls;
        this.fillTableModel();
    }

    public MultiComboBox(JComboBox<?>[] controls, DefaultTable.Options options) {
        super(options);
        this.controls = controls;
    }

    @Override
    public void fillTableModel() {
        super.fillTableModel();
        DefaultTableModel model = (DefaultTableModel)this.getModel();
        for (int i = 0; i < options.getColumnCount(); i ++) {
            getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
            getColumnModel().getColumn(i).setCellEditor(new JComboBoxCellEditor() {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    JComboBox<?> box = controls[column];
                    if (box == null) {
                        setIndex(-1);
                        setBox(null);
                        return null;
                    };
                    super.setBox(box);
                    super.setIndex(column);
                    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
                }

                @Override
                public boolean stopCellEditing() {
                    int index = getIndex();
                    if (index == -1) return false;
                    return super.stopCellEditing();
                }
            });
        }
        model.addRow(this.controls);
    }

    public static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            MultiComboBox thisTable = (MultiComboBox)table;
            if (value instanceof JComboBox) {
                return (JComboBox<?>)value;
            }
            else if (value instanceof ComboBoxModel) {
                JComboBox<?> box = thisTable.controls[column];
                if (box == null) {
                    return null;
                }
                box.setSelectedItem(value);
                return box;
            }
            return null;
        }
    }
}
