package io.hurx.components.button;

import io.hurx.utils.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ButtonCellRenderer extends DefaultTableCellRenderer {
    /** The Button instance being rendered. */
    public Button button() {
        return button;
    }
    private Button button;

    /**
     * Constructs a ButtonCellRenderer for a specific Button instance.
     * Removes any previous MouseAdapter attached to the Button before adding a new one.
     *
     * @param button the Button instance to render and attach mouse events to.
     */
    public ButtonCellRenderer(Button button) {
        this.button = button;
    }

    /**
     * Configures the appearance of the Button in the cell based on hover state.
     * Changes the background color when hovered to provide visual feedback.
     *
     * @param table      the JTable that uses this renderer.
     * @param value      the value to assign to the cell; expected to be a MenuButton.
     * @param isSelected true if the cell is selected.
     * @param hasFocus   true if the cell has focus.
     * @param row        the row of the cell being rendered.
     * @param column     the column of the cell being rendered.
     * @return the component used for drawing the cell, configured based on hover state.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Button) {
            Button button = (Button) value;
            button.setOpaque(true);

            // Apply background color based on hover state.
            if (button.isHovered()) {
                button.setBackground(Theme.TABLE_BG_COLOR_SELECTED_HOVER);
            } else {
                button.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
            }
            return button; // Return the button component for rendering
        }
        return null; // Return null if value is not a Button
    }
}
