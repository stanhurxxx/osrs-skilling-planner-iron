package io.hurx.components;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.text.ParseException;

public class AbbreviatedNumberFormattedTextField extends JFormattedTextField {

    public AbbreviatedNumberFormattedTextField() {
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
                    return null;
                return super.stringToValue(text);
            }
        };
        numberFormatter.setValueClass(BigDecimal.class);  // Use BigDecimal for precision
        numberFormatter.setAllowsInvalid(false);  // Allow invalid characters
        numberFormatter.setOverwriteMode(false);  // Allows editing without overwriting
        numberFormatter.setMinimum(null);  // Allows empty values

        // Set the formatter to the JFormattedTextField
        this.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            super.setValue(null); // Allow null
            return;
        }

        if (value instanceof Number) {
            try {
                // If it's a number, format it with thousand separators
                super.setValue(formatWithThousandSeparators((Number) value));
            } catch (Exception e) {
                super.setValue(value); // Handle unexpected types
            }
        } else {
            super.setValue(value); // Handle unexpected types
        }
    }

    // Method to format the value with thousand separators
    private String formatWithThousandSeparators(Number number) {
        double value = number.doubleValue();
        // Format the number with thousand separators and up to two decimal places
        return NumberFormat.getInstance(Locale.US).format(value);
    }

    // Custom method to parse values when editing
    public BigDecimal parseInput(String input) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            return null;  // Return null for empty input
        }

        // Remove everything except numbers, commas, and dots
        input = input.replaceAll("[^\\d,\\.]", "");

        // Remove commas for parsing and ensure the input can be parsed as BigDecimal
        input = input.replaceAll(",", ""); // Remove thousand separators for parsing

        // Validate that we have a valid number format
        if (input.isEmpty()) {
            return null; // If the input is empty after filtering, return null
        }

        return new BigDecimal(input);
    }
}