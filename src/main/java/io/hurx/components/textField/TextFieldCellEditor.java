package io.hurx.components.textField;

import io.hurx.utils.Theme;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * A custom cell editor for JTable that allows for editing text values 
 * using a TextField. This editor facilitates inline editing of string values
 * in a JTable cell.
 */
public class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
    private TextField textField;

    /**
     * Constructs a TextFieldCellEditor with the specified TextField.
     *
     * @param textField The TextField to be used for editing cell values.
     */
    public TextFieldCellEditor(TextField textField) {
        super();
        this.textField = textField; // Initialize the text field
    }

    /**
     * Stops editing and notifies any listeners that editing has stopped.
     *
     * @return true if editing was stopped successfully.
     */
    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing(); // Call the superclass method to stop editing
    }

    /**
     * Determines whether the cell is editable.
     *
     * @param e The event that triggered the check.
     * @return true, indicating that the cell is editable for all events.
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    /**
     * Retrieves the current value of the text field as a String.
     *
     * @return The text currently contained in the text field.
     */
    @Override
    public Object getCellEditorValue() {
        return textField.getText(); // Return the current value of the text field
    }

    /**
     * Prepares the text field for editing by setting its current value
     * and selecting all text.
     *
     * @param table The JTable that is being edited.
     * @param value The current value of the cell being edited.
     * @param isSelected Indicates whether the cell is selected.
     * @param row The row index of the cell being edited.
     * @param column The column index of the cell being edited.
     * @return The TextField component that will be used for editing.
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            textField.setText((String) value); // Set the text field's value
        }
        textField.selectAll(); // Select all text for easier editing
        textField.requestFocusInWindow(); // Request focus for the text field
        return textField; // Return the text field as the editor component
    }
}
