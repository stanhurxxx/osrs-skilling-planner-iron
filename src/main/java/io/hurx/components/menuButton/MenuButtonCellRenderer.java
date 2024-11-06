package io.hurx.components.menuButton;

import io.hurx.utils.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A custom cell renderer for JTable that renders a MenuButton and changes its background color on hover.
 * This renderer is designed to provide visual feedback by highlighting the button when hovered.
 */
public class MenuButtonCellRenderer extends DefaultTableCellRenderer {
    /**
     * The button
     */
    public MenuButton menuButton() {
        return menuButton;
    }
    private MenuButton menuButton;

    /**
     * Constructs a MenuButtonCellRenderer for a specific MenuButton instance.
     * Removes any previous MouseAdapter attached to the MenuButton before adding a new one.
     *
     * @param menuButton the MenuButton instance to render and attach mouse events to.
     */
    public MenuButtonCellRenderer(MenuButton menuButton) {
        this.menuButton = menuButton;
    }

    /**
     * Configures the appearance of the MenuButton in the cell based on hover state.
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
        if (value instanceof MenuButton) {
            MenuButton button = (MenuButton) value;
            button.setOpaque(true);

            // Apply background color based on hover state.
            if (button.isSelected()) {
                if (button.isHovered()) {
                    button.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                } else {
                    button.setBackground(Theme.TABLE_BG_COLOR_SELECTED_HOVER);
                }
            }
            else {
                if (button.isHovered()) {
                    button.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                } else {
                    button.setBackground(Theme.TABLE_BG_COLOR);
                }
            }
            return button; // Return the button component for rendering
        }
        return null; // Return null if value is not a MenuButton
    }
}
