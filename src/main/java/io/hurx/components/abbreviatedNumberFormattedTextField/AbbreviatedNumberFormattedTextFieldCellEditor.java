package io.hurx.components.abbreviatedNumberFormattedTextField;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class AbbreviatedNumberFormattedTextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
    private AbbreviatedNumberFormattedTextField textField;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public AbbreviatedNumberFormattedTextFieldCellEditor() {
        this.textField = new AbbreviatedNumberFormattedTextField();
        configureTextField(textField);
    }

    // Configure the textField with actions for stopping editing
    public void configureTextField(AbbreviatedNumberFormattedTextField textField) {
        textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "stopCellEditing");
        textField.getActionMap().put("stopCellEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });
    }

    public AbbreviatedNumberFormattedTextField getTextField() {
        return this.textField;
    }

    // Set up the text field for the editor each time it's called for a new cell
    public void setTextField(AbbreviatedNumberFormattedTextField textField) {
        configureTextField(textField);
        this.textField = textField;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        try {
            BigDecimal value = textField.parseInput(textField.getText());
            if (value == null) {
                throw new NumberFormatException("Parsed value is null");
            }
            // Update the internal value if parsing succeeds
            super.fireEditingStopped();
            return true;
        } catch (Exception e) {
            // Handle invalid input by resetting to a default value
            textField.setValue(BigDecimal.ZERO);
            System.err.println("Invalid input, resetting to zero: " + e.getMessage());
            return false;
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