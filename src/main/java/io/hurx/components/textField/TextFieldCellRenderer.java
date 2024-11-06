package io.hurx.components.textField;

import io.hurx.utils.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A custom cell renderer for JTable that displays a TextField
 * for string values in the table cells. This renderer allows
 * for a more interactive appearance, simulating an editable
 * text field while still being used in a read-only context.
 */
public class TextFieldCellRenderer extends DefaultTableCellRenderer {
    /**
     * The attached text field
     */
    public TextField textField() {
        return textField;
    }
    private TextField textField;

    /**
     * Constructs a TextFieldCellRenderer with the specified TextField.
     *
     * @param textField the TextField to use for rendering cell values
     */
    public TextFieldCellRenderer(TextField textField) {
        this.textField = textField;
    }

    /**
     * Returns the component used for rendering the cell at the specified
     * row and column. If the value is a TextField, it is returned
     * directly. If the value is a String, the text field's text is
     * set to that String, and the text field is returned.
     *
     * @param table the JTable that is asking the renderer to draw
     * @param value the value of the cell to be rendered
     * @param isSelected true if the cell is to be rendered with selection highlighting
     * @param hasFocus true if the cell has focus
     * @param row the row index of the cell being rendered
     * @param column the column index of the cell being rendered
     * @return the component used for rendering the cell
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof TextField) {
            return processTextField((TextField) value); // Return the TextField if value is a TextField
        } else if (value instanceof String) {
            textField.setText((String) value); // Set the text field's text for String values
            return processTextField(textField); // Return the configured TextField
        }
        return null; // Return null if value is not a TextField or String
    }

    /**
     * Processes the text field based on rendering
     * @param textField the text field
     * @return the processed text field
     */
    private TextField processTextField(TextField textField) {
        if (textField.isSelected()) {
            if (textField.isHovered()) {
                textField.setBackground(Theme.TABLE_BG_COLOR_SELECTED_HOVER);
            }
            else {
                textField.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
            }
        }
        else {
            if (textField.isHovered()) {
                textField.setBackground(Theme.TABLE_BG_COLOR_HOVER);
            }
            else {
                textField.setBackground(Theme.TABLE_BG_COLOR);
            }
        }
        textField.setSelectionColor(Theme.TABLE_BG_COLOR_SELECTED);
        textField.setSelectedTextColor(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING));
        return textField;
    }
}