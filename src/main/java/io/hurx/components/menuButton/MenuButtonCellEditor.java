package io.hurx.components.menuButton;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;

import java.util.EventObject;

/**
 * A cell editor for JTable that uses a MenuButton component.
 * This class allows a MenuButton to be used as an editor within a table cell.
 */
public class MenuButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    /** The MenuButton component used as the cell editor. */
    private MenuButton button;

    /** The index of the cell being edited. */
    private int index;

    /**
     * Constructs a MenuButtonCellEditor with no initial button.
     * The button will be set for each row as needed.
     */
    public MenuButtonCellEditor() {
        // Default constructor
    }

    /**
     * Sets the index of the cell being edited.
     *
     * @param index the index of the cell
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Retrieves the index of the cell being edited.
     *
     * @return the index of the cell
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retrieves the MenuButton component used for editing.
     *
     * @return the MenuButton component
     */
    public MenuButton getButton() {
        return this.button;
    }

    /**
     * Sets the MenuButton component for this editor.
     *
     * @param button the MenuButton to set as the editor
     */
    public void setButton(MenuButton button) {
        this.button = button;
    }

    /**
     * Returns the current value of the editor, which is the MenuButton.
     *
     * @return the current MenuButton value
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
     * Prepares the MenuButton component for editing by setting its state based on
     * the value in the table cell.
     *
     * @param table the JTable that is being edited
     * @param value the current value in the cell
     * @param isSelected indicates whether the cell is selected
     * @param row the row index of the cell being edited
     * @param column the column index of the cell being edited
     * @return the MenuButton component for editing
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the button state based on the table cell value if needed
        return button;
    }
}