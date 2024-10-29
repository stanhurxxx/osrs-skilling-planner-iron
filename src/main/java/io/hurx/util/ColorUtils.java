package io.hurx.util;

import java.awt.Color;

public class ColorUtils {
    public static Color mergeColors(Color color1, Color color2, float percentage) {
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
