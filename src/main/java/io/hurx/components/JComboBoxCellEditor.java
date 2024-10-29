package io.hurx.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellEditor;

import java.util.EventObject;

public class JComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JComboBox<?> box;

    private int index;

    public JComboBoxCellEditor() {}

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public JComboBox<?> getBox() {
        return this.box;
    }

    public void setBox(JComboBox<?> box) {
        this.box = box;
    }

    @Override
    public Object getCellEditorValue() {
        return box.getSelectedItem(); // Return the current value of the slider
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            box.setSelectedItem(value);
        }
        return box;
    }
}