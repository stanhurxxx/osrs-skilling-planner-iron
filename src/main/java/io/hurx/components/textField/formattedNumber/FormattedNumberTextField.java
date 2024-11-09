package io.hurx.components.textField.formattedNumber;

import io.hurx.components.EditableComponent;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;

/**
 * A custom formatted text field for inputting and displaying numbers with
 * thousand separators and up to two decimal places.
 */
public class FormattedNumberTextField extends JFormattedTextField implements EditableComponent {
    /** Set the hovered state */
    public void isHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    /** Is the text field hovered? */
    public boolean isHovered() {
        return isHovered;
    }

    // Flag indicating whether the text field is currently hovered.
    private boolean isHovered = false;

    /**
     * Constructs a FormattedNumberTextField with default settings.
     * Initializes the number formatter for thousand separators and decimal places.
     */
    public FormattedNumberTextField() {
        super();

        // Create a number formatter for thousand separators and decimals
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setGroupingUsed(true);  // Enables thousand separators
        numberFormat.setMaximumFractionDigits(2);  // Allows up to two decimal places

        // Set formatter for the JFormattedTextField
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.length() == 0)
                    return null; // Allow empty input
                return super.stringToValue(text);
            }
        };
        numberFormatter.setValueClass(BigDecimal.class);  // Use BigDecimal for precision
        numberFormatter.setAllowsInvalid(false);  // Disallow invalid characters
        numberFormatter.setOverwriteMode(false);  // Allows editing without overwriting
        numberFormatter.setMinimum(BigDecimal.ZERO);  // Minimum value is 0

        // Set the formatter to the JFormattedTextField
        this.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));

        // Center align the text field and set an empty border
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public FormattedNumberTextField onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
    }

    /**
     * Retrieves the current value as an integer from the text field.
     *
     * @return The current value as an integer, or 0 if the value is null or cannot be parsed.
     */
    public int getValueAsInt() {
        Object value = super.getValue();
        if (value == null) {
            return 0; // Return 0 for null values
        }

        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue(); // Convert BigDecimal to int
        } else if (value instanceof Number) {
            return ((Number) value).intValue(); // Handle other possible numeric types
        } else {
            try {
                // Attempt to parse unexpected types as BigDecimal
                BigDecimal parsedValue = parseInput(value.toString());
                return parsedValue != null ? parsedValue.intValue() : 0;
            } catch (Exception e) {
                return 0; // Return 0 for parsing errors
            }
        }
    }

    /**
     * Sets the value of the text field. The value can be a Number or any other
     * object, which will be formatted accordingly.
     *
     * @param value The value to set in the text field. It can be null, a Number,
     *              or any other object.
     */
    @Override
    public void setValue(Object value) {
        if (value == null) {
            super.setValue(null); // Allow null
            return;
        }

        if (value instanceof Number) {
            try {
                // Format the number with thousand separators
                super.setValue(formatWithThousandSeparators((Number) value));
            } catch (Exception e) {
                super.setValue(value); // Handle unexpected types
            }
        } else {
            super.setValue(value); // Handle unexpected types
        }
    }

    /**
     * Formats the given number with thousand separators.
     *
     * @param number The number to format.
     * @return A string representation of the number with thousand separators.
     */
    private String formatWithThousandSeparators(Number number) {
        double value = number.doubleValue();
        // Format the number with thousand separators and up to two decimal places
        return NumberFormat.getInstance(Locale.US).format(value);
    }

    /**
     * Parses the input string to a BigDecimal, ensuring that the string represents
     * a valid non-negative number.
     *
     * @param input The input string to parse.
     * @return The parsed BigDecimal value, or null if the input is empty.
     * @throws ParseException If the input is invalid or represents a negative number.
     */
    public BigDecimal parseInput(String input) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            return null;  // Return null for empty input
        }

        // Remove everything except numbers, commas, and dots
        input = input.replaceAll("[^\\d,\\.]", "");

        // Remove commas for parsing to ensure the input can be parsed as BigDecimal
        input = input.replaceAll(",", ""); // Remove thousand separators

        // Validate that we have a valid number format
        if (input.isEmpty()) {
            return null; // If the input is empty after filtering, return null
        }

        BigDecimal parsedValue = new BigDecimal(input);

        // Ensure the parsed value is non-negative
        if (parsedValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new ParseException("Negative values are not allowed", 0);
        }

        return parsedValue;
    }
}
