package io.hurx.utils;

import java.awt.Color;

/**
 * Utility class for color operations.
 */
public class Colors {
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
