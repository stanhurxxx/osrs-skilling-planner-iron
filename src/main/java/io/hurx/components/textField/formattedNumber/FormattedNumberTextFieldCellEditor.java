package io.hurx.components.textField.formattedNumber;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * A custom cell editor for JTable that allows for editing of numeric values
 * using a FormattedNumberTextField. The editor supports formatted
 * input with thousand separators and up to two decimal places.
 */
public class FormattedNumberTextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
    private FormattedNumberTextField textField;

    /**
     * Constructs a FormattedNumberTextFieldCellEditor with a new FormattedNumberTextField.
     */
    public FormattedNumberTextFieldCellEditor(FormattedNumberTextField textField) {
        this.textField = textField;
        configureTextField(textField);
    }

    /**
     * Configures the specified text field with actions for stopping editing.
     * Binds the ENTER key to stop cell editing when pressed.
     *
     * @param textField The text field to configure with key bindings.
     */
    public void configureTextField(FormattedNumberTextField textField) {
        textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "stopCellEditing");
        textField.getActionMap().put("stopCellEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing(); // Stop editing when ENTER is pressed
            }
        });
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true; // Always select the cell when editing begins
    }

    @Override
    public boolean stopCellEditing() {
        try {
            BigDecimal value = textField.parseInput(textField.getText());
            if (value == null) {
                throw new NumberFormatException("Parsed value is null");
            }
            // Notify listeners that editing has stopped
            super.fireEditingStopped();
            return true; // Indicate successful editing
        } catch (Exception e) {
            // Handle invalid input by resetting to a default value
            textField.setValue(BigDecimal.ZERO);
            System.err.println("Invalid input, resetting to zero: " + e.getMessage());
            return false; // Indicate that editing was not successful
        }
    }

    @Override
    public Object getCellEditorValue() {
        // Return the edited value as BigDecimal
        try {
            BigDecimal value = textField.parseInput(textField.getText());
            return value != null ? value : BigDecimal.ZERO;  // Default to BigDecimal.ZERO if null
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
        if (value instanceof Number) {
            textField.setValue(value); // Set the provided numeric value
        }
        return textField; // Return the text field as the editor component
    }
}
