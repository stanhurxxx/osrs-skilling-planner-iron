package io.hurx.components;

import javax.swing.*;
import javax.swing.AbstractAction;
import java.awt.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class AbbreviatedNumberFormattedTextFieldCellEditor extends AbstractCellEditor implements TableCellEditor { 
    private AbbreviatedNumberFormattedTextField textField;

    public AbbreviatedNumberFormattedTextFieldCellEditor() {
        textField = new AbbreviatedNumberFormattedTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "stopCellEditing");
        textField.getActionMap().put("stopCellEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });
    }

    @Override
    public boolean stopCellEditing() {
        // Fire editing stopped event
        super.fireEditingStopped();
        return false;
    }

    @Override
    public Object getCellEditorValue() {
        // Return the edited value (as BigDecimal)
        try {
            BigDecimal value = textField.parseInput(textField.getText());
            return value != null ? value : BigDecimal.ZERO;  // Default to BigDecimal.ZERO on null
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;  // Default in case of parsing failure
        }
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the value in the textField based on the cell's value
        if (value instanceof Number) {
            textField.setValue(value); // Ensure value is a Number
        } else {
            textField.setValue(BigDecimal.ZERO);  // Default if the value is null or invalid
        }
        return textField; // Return the text field as the editor component
    }
}