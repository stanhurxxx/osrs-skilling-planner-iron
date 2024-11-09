package io.hurx.components.numberSlider;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.hurx.components.EditableComponent;
import io.hurx.utils.Theme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom component that represents a number slider, allowing users to select a numeric value
 * within a specified range by dragging a handle along a line.
 */
public class NumberSlider extends JComponent implements EditableComponent {
    /** Horizontal padding around the slider. */
    public final static int H_PADDING = 10;
    /** Width of the slider handle. */
    public final static int SIZE = 20;

    /** Minimum value of the slider. */
    private int minValue = 1;
    /** Maximum value of the slider. */
    private int maxValue = 150;
    /** Current selected value of the slider. */
    private int currentValue = 1;
    /** Indicator of whether the handle is currently being dragged. */
    private boolean isHandleDragging = false;
    /** Format for displaying the slider's current value. */
    private NumberSliderFormat format = NumberSliderFormat.Default;

    // List of change listeners to notify about value changes
    private final List<ChangeListener> changeListeners = new ArrayList<>();

    /**
     * Constructs a NumberSlider with default minimum and maximum values,
     * and sets up mouse listeners for interaction.
     */
    public NumberSlider() {
        setPreferredSize(new Dimension(300, SIZE));
        
        // Mouse listener to handle dragging and clicking
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if the mouse click is inside the slider handle area
                if (e.getX() >= getHandlePosition(currentValue) && 
                    e.getX() <= getHandlePosition(currentValue) + getHandleWidth()) {
                    isHandleDragging = true; // Start dragging
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isHandleDragging = false; // Stop dragging
                int newValue = valueForPosition(e.getX());
                setCurrentValue(newValue); // Update the slider's value
            }
        });

        // Mouse motion listener for dragging behavior
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isHandleDragging) { // If dragging is active
                    int newValue = valueForPosition(e.getX());
                    setCurrentValue(newValue); // Update the slider's value
                }
            }
        });
    }

    @Override
    public NumberSlider onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
    }

    /**
     * Retrieves the width of the slider handle based on the current format.
     *
     * @return the width of the handle
     */
    public int getHandleWidth() {
        return this.format == NumberSliderFormat.Percentage
            ? 48
            : this.format == NumberSliderFormat.Default 
                ? 40
                : 64;
    }

    /**
     * Sets the format for the slider, adjusting the min and max values if needed.
     *
     * @param format the format to set for the slider
     */
    public void setFormat(NumberSliderFormat format) {
        this.format = format;
        if (format == NumberSliderFormat.Percentage) {
            this.minValue = 0;
            this.maxValue = 100;
        }
    }

    /**
     * Sets the minimum value for the slider. If the current value is less than
     * the new minimum, it is clamped to the minimum value.
     *
     * @param minValue the new minimum value
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (currentValue < minValue) {
            currentValue = minValue;
            notifyChangeListeners();
        }
        repaint();
    }

    /**
     * Sets the maximum value for the slider. If the current value is greater than
     * the new maximum, it is clamped to the maximum value.
     *
     * @param maxValue the new maximum value
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (currentValue > maxValue) {
            currentValue = maxValue;
            notifyChangeListeners();
        }
        repaint();
    }

    /**
     * Sets the current value of the slider, ensuring it respects the minimum and maximum values.
     *
     * @param currentValue the new current value
     */
    public void setCurrentValue(int currentValue) {
        if (currentValue < minValue) {
            currentValue = minValue; // Clamp to minValue
        }
        if (currentValue > maxValue) {
            currentValue = maxValue; // Clamp to maxValue
        }
        if (this.currentValue != currentValue) {
            this.currentValue = currentValue;
            notifyChangeListeners();
            revalidate();
            repaint();
        }
    }

    /**
     * Retrieves the current value of the slider.
     *
     * @return the current value
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Adds a ChangeListener that will be notified when the slider's value changes.
     *
     * @param listener the listener to add
     */
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    /**
     * Notifies all registered ChangeListeners of a change in the slider's value.
     */
    private void notifyChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(event);
        }
    }

    /**
     * Calculates the position of the slider handle based on the current value.
     *
     * @param value the current value
     * @return the x-coordinate of the handle position
     */
    private int getHandlePosition(int value) {
        return (int) ((double) (value - minValue) / (maxValue - minValue) * (getWidth() - getHandleWidth() - H_PADDING * 2)) + H_PADDING;
    }

    /**
     * Calculates the value corresponding to a given x-coordinate on the slider.
     *
     * @param x the x-coordinate
     * @return the value at the specified position
     */
    private int valueForPosition(int x) {
        // Adjust x to account for the left half of the handle
        int adjustedX = x - H_PADDING / 2 - (getHandleWidth() / 2);

        // Clamp the adjusted position to avoid negative and overflow
        if (adjustedX < 0) {
            return minValue; // If it goes beyond the left edge
        } else if (adjustedX > (getWidth() - getHandleWidth() - H_PADDING)) {
            return maxValue; // If it goes beyond the right edge
        }

        // Calculate the value based on the adjusted position
        return (int) (minValue + ((double) adjustedX / (getWidth() - getHandleWidth() - H_PADDING)) * (maxValue - minValue));
    }

    /**
     * Retrieves the label to be displayed on the slider handle based on the current format.
     *
     * @return the label for the current value
     */
    private String getLabel() {
        return format == NumberSliderFormat.Default
            ? Integer.toString(currentValue)
            : format == NumberSliderFormat.Percentage
                ? Integer.toString(currentValue) + "%"
                : (int)Math.floor(currentValue / 60) + "h " + (int)(currentValue % 60) + "m";
    }

    /**
     * Paints the component, drawing the slider line, handle, and current value label.
     *
     * @param g the Graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the slider line
        g.setColor(Theme.TABLE_BG_COLOR_HOVER);
        g.fillRect(H_PADDING, (getHeight() - SIZE) / 2, getWidth() - H_PADDING * 2, SIZE);
        
        // Draw the handle
        g.setColor(Theme.TABLE_BG_COLOR);
        g.fillRect(getHandlePosition(currentValue), (getHeight() - SIZE) / 2, getHandleWidth(), SIZE);
        
        // Draw the current value in the handle
        g.setColor(Color.WHITE);
        String label = getLabel();
        g.drawString(label, getHandlePosition(currentValue) + getHandleWidth() / 2 - Math.round(label.length() * (format == NumberSliderFormat.Minutes ? 3.5f : 4f)), (getHeight() - SIZE) / 2 + 17);
    }
}