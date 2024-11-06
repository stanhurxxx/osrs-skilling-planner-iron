package io.hurx.components.comboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A custom cell renderer for JTable that displays a ComboBox
 * for values in the table cells. This renderer allows for a more
 * interactive appearance, simulating a combo box while rendering
 * values in a read-only context.
 */
public class ComboBoxCellRenderer extends DefaultTableCellRenderer {
    private ComboBox<?> comboBox;

    /**
     * Constructs a ComboBoxCellRenderer with the specified ComboBox.
     *
     * @param comboBox the ComboBox to use for rendering cell values
     */
    public ComboBoxCellRenderer(ComboBox<?> comboBox) {
        this.comboBox = comboBox;
    }

    /**
     * Returns the component used for rendering the cell at the specified
     * row and column. If the value is a ComboBox, it is returned
     * directly. Otherwise, the selected item of the combo box is set
     * to the value of the cell and the combo box is returned.
     *
     * @param table the JTable that is asking the renderer to draw
     * @param value the value of the cell to be rendered
     * @param isSelected true if the cell is to be rendered with selection highlighting
     * @param hasFocus true if the cell has focus
     * @param row the row index of the cell being rendered
     * @param column the column index of the cell being rendered
     * @return the component used for rendering the cell, which can be a ComboBox or a configured component
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ComboBox) {
            return (ComboBox<?>) value; // Return the ComboBox if the value is a ComboBox
        } else {
            comboBox.setSelectedItem(value); // Set the combo box's selected item for non-ComboBox values
            return comboBox; // Return the configured ComboBox
        }
    }
}