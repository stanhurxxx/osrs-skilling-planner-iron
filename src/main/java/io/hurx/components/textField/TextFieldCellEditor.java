package io.hurx.components.textField;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

import java.util.EventObject;

public class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor  {
    private JTextField textField;

    public TextFieldCellEditor(JTextField textField) {
        super();
        this.textField = textField;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText(); // Return the current value of the slider
    }

     @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            textField.setText((String)value);
        }
        textField.selectAll();
        textField.requestFocusInWindow();
        return textField;
    }
}