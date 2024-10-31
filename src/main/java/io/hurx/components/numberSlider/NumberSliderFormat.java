package io.hurx.components.numberSlider;

/**
 * An enumeration that represents the different formats available for the NumberSlider.
 * This enum defines how the current value of the slider is displayed to the user.
 */
public enum NumberSliderFormat {
    /**
     * Default format. Displays the current value as a simple integer.
     */
    Default,

    /**
     * Percentage format. Displays the current value as a percentage (0-100%).
     */
    Percentage,

    /**
     * Minutes format. Displays the current value in hours and minutes.
     * For example, 130 will be displayed as "2h 10m".
     */
    Minutes
}