package io.hurx.components.comboBox;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;

import java.util.EventObject;

/**
 * A custom cell editor for use in a JTable, where each cell contains a ComboBox.
 * This editor supports the ability to display a ComboBox in each cell
 * and interact with it for selection.
 */
public class ComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    /** The ComboBox component used for editing the cell's value. */
    private ComboBox<?> box;

    /**
     * Default constructor for creating a ComboBoxCellEditor.
     */
    public ComboBoxCellEditor(ComboBox<?> box) {
        this.box = box;
    }

    /**
     * Retrieves the current value in the ComboBox selected by the user.
     *
     * @return the selected item from the ComboBox
     */
    @Override
    public Object getCellEditorValue() {
        return box.getSelectedItem();
    }

    /**
     * Determines if the cell is editable based on the given event.
     *
     * @param e the event that triggers cell editing
     * @return {@code true} to indicate the cell is always editable
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    /**
     * Configures the editor for a cell at the specified row and column,
     * setting the ComboBox to the current value in the cell.
     *
     * @param table the JTable that is asking the editor to edit
     * @param value the value of the cell to edit
     * @param isSelected whether the cell is selected
     * @param row the row of the cell being edited
     * @param column the column of the cell being edited
     * @return the component used for editing, which is the ComboBox in this case
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            box.setSelectedItem(value);
        }
        return box;
    }
}