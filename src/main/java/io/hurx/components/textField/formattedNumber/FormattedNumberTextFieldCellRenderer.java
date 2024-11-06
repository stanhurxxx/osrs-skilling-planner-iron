package io.hurx.components.textField.formattedNumber;

import io.hurx.components.textField.TextField;
import io.hurx.utils.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * A custom cell renderer for displaying a formatted number in a JTable cell
 * using a FormattedNumberTextField component. This renderer is useful for
 * displaying numbers in a formatted way within table cells.
 */
public class FormattedNumberTextFieldCellRenderer extends DefaultTableCellRenderer {

    /** The FormattedNumberTextField instance used to render the cell */
    public FormattedNumberTextField textField() {
        return textField;
    }
    private FormattedNumberTextField textField;

    /**
     * Constructs a FormattedNumberTextFieldCellRenderer that uses the given FormattedNumberTextField
     * to render cell values in a JTable.
     *
     * @param textField the FormattedNumberTextField instance to be used for rendering the cell.
     */
    public FormattedNumberTextFieldCellRenderer(FormattedNumberTextField textField) {
        this.textField = textField;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Number) {
            textField.setValue(value); // Set the provided numeric value
        }
        return processTextField(textField, isSelected); // Return the text field as the editor component
    }

    /**
     * Processes the text field based on rendering
     * @param textField the text field
     * @param isSelected whether its selected
     * @return the processed text field
     */
    private FormattedNumberTextField processTextField(FormattedNumberTextField textField, boolean isSelected) {
        if (isSelected || textField.isHovered()) {
            textField.setBackground(Theme.TABLE_BG_COLOR_HOVER);
        }
        else {
            textField.setBackground(Theme.TABLE_BG_COLOR_IN_BETWEEN_HOVER);
        }
        textField.setSelectionColor(Theme.TABLE_BG_COLOR_SELECTED);
        textField.setSelectedTextColor(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING));
        return textField;
    }
}
