package io.hurx.utils;

import java.awt.Color;

/**
 * Utility class for color operations.
 */
public class Colors {
    /**
     * Lightens a color by a specified percentage.
     *
     * @param color The original color to lighten.
     * @param percentage The percentage by which to lighten the color. A value between 0 and 100.
     * @return A new Color object that is the lightened version of the original color.
     */
    public static Color lightenColor(Color color, float percentage) {
        // Clamp the percentage to ensure it is between 0 and 100
        percentage = Math.max(0, Math.min(percentage, 100));

        // Convert percentage to a factor between 1 and 1 + percentage / 100
        float factor = 1 + (percentage / 100);

        // Get the RGB values of the original color
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Lighten the color by increasing each component based on the factor
        int newRed = (int) Math.min(255, red * factor);
        int newGreen = (int) Math.min(255, green * factor);
        int newBlue = (int) Math.min(255, blue * factor);

        // Return a new Color object with the lightened RGB values
        return new Color(newRed, newGreen, newBlue);
    }

    /**
     * Merges two colors based on a specified percentage.
     *
     * @param color1    the first color to merge.
     * @param color2    the second color to merge.
     * @param percentage the percentage of the second color to include in the merge,
     *                   expressed as a value between 0 and 100.
     * @return a new Color that represents the merged result of color1 and color2.
     * @throws IllegalArgumentException if the percentage is not between 0 and 100.
     */
    public static Color mergeColors(Color color1, Color color2, float percentage) throws IllegalArgumentException {
        // Ensure percentage is within 0-100 range
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }

        // Calculate the ratio of each color based on the percentage
        float ratio = percentage / 100.0f;

        // Interpolate the RGB values
        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

        return new Color(red, green, blue);
    }
}
