package io.hurx.components.numberSlider;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;

import java.util.EventObject;
import java.util.List;
import java.util.ArrayList;

/**
 * A custom cell editor for JTable that uses a NumberSlider component.
 * This editor allows users to edit cell values by sliding a handle
 * along a numerical range.
 */
public class NumberSliderCellEditor extends AbstractCellEditor implements TableCellEditor {
    /** The NumberSlider component used for editing cell values. */
    private NumberSlider slider;

    /** The index of the row being edited. */
    private int index;

    /** List of NumberSlider instances to handle mouse listeners. */
    private List<NumberSlider> mouseListeners = new ArrayList<>();

    /**
     * Constructs a NumberSliderCellEditor. The slider will be set for each row.
     */
    public NumberSliderCellEditor() {
        // Default constructor
    }

    /**
     * Sets the index of the row being edited.
     *
     * @param index the index of the row
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Retrieves the index of the row being edited.
     *
     * @return the index of the row
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retrieves the NumberSlider associated with this editor.
     *
     * @return the current NumberSlider
     */
    public NumberSlider getSlider() {
        return this.slider;
    }

    /**
     * Sets the NumberSlider for this editor and adds a mouse listener
     * to stop editing when the slider is released.
     *
     * @param slider the NumberSlider to set
     */
    public void setSlider(NumberSlider slider) {
        this.slider = slider;
        // Add mouse listener if not already present
        if (!mouseListeners.contains(slider)) {
            NumberSliderCellEditor editor = this;
            slider.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    editor.stopCellEditing(); // Stop editing when mouse is released
                }
            });
            mouseListeners.add(slider);
        }
    }

    /**
     * Returns the current value of the slider when editing is stopped.
     *
     * @return the current value of the slider
     */
    @Override
    public Object getCellEditorValue() {
        return slider.getCurrentValue(); // Return the current value of the slider
    }

    /**
     * Indicates whether the cell is editable.
     *
     * @param e the event that triggered the call
     * @return true, indicating that all cells are editable
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Allow editing for all events
    }

    /**
     * Configures the slider component for the given table cell.
     * Sets the slider's value based on the table cell value if it's an Integer.
     *
     * @param table the JTable that is being edited
     * @param value the value of the cell being edited
     * @param isSelected whether the cell is selected
     * @param row the row index of the cell being edited
     * @param column the column index of the cell being edited
     * @return the NumberSlider component configured for editing
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set the slider value based on the table cell value if it's an Integer
        if (value instanceof Integer) {
            slider.setCurrentValue((Integer) value);
        }
        return slider; // Return the configured slider
    }
}