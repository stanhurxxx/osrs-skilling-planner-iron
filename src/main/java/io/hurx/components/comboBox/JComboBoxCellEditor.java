package io.hurx.components.comboBox;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;

import java.util.EventObject;

/**
 * A custom cell editor for use in a JTable, where each cell contains a JComboBox.
 * This editor supports the ability to display a JComboBox in each cell
 * and interact with it for selection.
 */
public class JComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    /** The JComboBox component used for editing the cell's value. */
    private JComboBox<?> box;
    
    /** Index used to keep track of the current cell being edited. */
    private int index;

    /**
     * Default constructor for creating a JComboBoxCellEditor.
     */
    public JComboBoxCellEditor() {}

    /**
     * Sets the index of the cell currently being edited.
     *
     * @param index the index to set for the editor
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Retrieves the index of the cell currently being edited.
     *
     * @return the index of the cell currently being edited
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retrieves the JComboBox component used by this editor.
     *
     * @return the JComboBox component
     */
    public JComboBox<?> getBox() {
        return this.box;
    }

    /**
     * Sets the JComboBox component used by this editor.
     *
     * @param box the JComboBox component to be used in cell editing
     */
    public void setBox(JComboBox<?> box) {
        this.box = box;
    }

    /**
     * Retrieves the current value in the JComboBox selected by the user.
     *
     * @return the selected item from the JComboBox
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
     * setting the JComboBox to the current value in the cell.
     *
     * @param table the JTable that is asking the editor to edit
     * @param value the value of the cell to edit
     * @param isSelected whether the cell is selected
     * @param row the row of the cell being edited
     * @param column the column of the cell being edited
     * @return the component used for editing, which is the JComboBox in this case
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            box.setSelectedItem(value);
        }
        return box;
    }
}