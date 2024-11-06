package io.hurx.components.button;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * A cell editor for JTable that uses a Button component.
 * This class allows a Button to be used as an editor within a table cell.
 */
public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

    /** The Button component used as the cell editor. */
    private Button button;

    /**
     * Constructs a ButtonCellEditor with no initial button.
     * The button will be set for each row as needed.
     */
    public ButtonCellEditor(Button button) {
        this.button = button;
    }

    /**
     * Returns the current value of the editor, which is the Button.
     *
     * @return the current Button value
     */
    @Override
    public Object getCellEditorValue() {
        return button; // Return the current value of the button
    }

    /**
     * Determines whether the cell is editable.
     *
     * @param e the event triggering the edit action
     * @return true, allowing editing for all events
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    /**
     * Prepares the Button component for editing by setting its state based on
     * the value in the table cell.
     *
     * @param table the JTable that is being edited
     * @param value the current value in the cell
     * @param isSelected indicates whether the cell is selected
     * @param row the row index of the cell being edited
     * @param column the column index of the cell being edited
     * @return the Button component for editing
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the button state based on the table cell value if needed
        return button;
    }
}