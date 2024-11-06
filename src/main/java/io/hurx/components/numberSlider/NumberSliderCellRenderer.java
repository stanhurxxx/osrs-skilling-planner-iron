package io.hurx.components.numberSlider;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A custom cell renderer for JTable that renders a NumberSlider component.
 * This renderer is useful for displaying a slider within a table cell, updating
 * the slider's position to reflect the cell's integer value.
 */
public class NumberSliderCellRenderer extends DefaultTableCellRenderer {
    // The NumberSlider instance used to render the cell
    private NumberSlider slider;

    /**
     * Constructs a NumberSliderCellRenderer that uses the given NumberSlider
     * to render cell values in a JTable.
     *
     * @param slider the NumberSlider instance to be used for rendering the cell.
     */
    public NumberSliderCellRenderer(NumberSlider slider) {
        this.slider = slider;
    }

    /**
     * Configures the appearance and value of the NumberSlider in the cell based on the cell's value.
     * Sets the slider's current value if the cell value is an Integer, and returns the slider
     * component for rendering in the JTable.
     *
     * @param table      the JTable that uses this renderer.
     * @param value      the value to assign to the cell; expected to be an Integer.
     * @param isSelected true if the cell is selected.
     * @param hasFocus   true if the cell has focus.
     * @param row        the row of the cell being rendered.
     * @param column     the column of the cell being rendered.
     * @return the component used for drawing the cell, configured to display the NumberSlider.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Set the slider value based on the table cell value if it's an Integer
        if (value instanceof Integer) {
            slider.setCurrentValue((Integer) value);
        }
        return slider; // Return the configured slider
    }
}
