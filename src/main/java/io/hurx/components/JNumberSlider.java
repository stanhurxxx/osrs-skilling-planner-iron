package io.hurx.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.hurx.Theme;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class JNumberSlider extends JComponent {
    public final static int H_PADDING = 5;
    public final static int SIZE = 20; // Width of the slider handle
    private int minValue = 1;
    private int maxValue = 150;
    private int currentValue = 1; // Current selected value
    private int dragHandle = -1; // -1 for none, 0 for handle
    private boolean percentage = false;

    // List of change listeners
    private final List<ChangeListener> changeListeners = new ArrayList<>();

    public JNumberSlider() {
        setPreferredSize(new Dimension(300, SIZE));
        
        // Mouse listener to handle dragging and clicking
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if the mouse click is inside the slider handle area
                if (e.getX() >= getHandlePosition(currentValue) && 
                    e.getX() <= getHandlePosition(currentValue) + getHandleWidth()) {
                    dragHandle = 0; // Start dragging
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragHandle = -1; // Stop dragging
                int newValue = valueForPosition(e.getX());
                setCurrentValue(newValue); // Update the slider's value
            }
        });

        // Mouse motion listener for dragging behavior
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragHandle == 0) { // If dragging is active
                    int newValue = valueForPosition(e.getX());
                    setCurrentValue(newValue); // Update the slider's value
                }
            }
        });
    }

    public int getHandleWidth() {
        return this.percentage ? 44 : 40;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = true;
        if (percentage) {
            this.minValue = 0;
            this.maxValue = 100;
        }
    }

    // Set minimum value and update the slider
    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (currentValue < minValue) {
            currentValue = minValue;
            notifyChangeListeners();
        }
        repaint();
    }

    // Set maximum value and update the slider
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (currentValue > maxValue) {
            currentValue = maxValue;
            notifyChangeListeners();
        }
        repaint();
    }

    // Set current value and ensure it respects the min and max
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

    // Get the current value
    public int getCurrentValue() {
        return currentValue;
    }

    // Add a ChangeListener
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    // Notify all registered ChangeListeners
    private void notifyChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(event);
        }
    }

    private int getHandlePosition(int value) {
        return (int) ((double) (value - minValue) / (maxValue - minValue) * (getWidth() - getHandleWidth() - 2 * H_PADDING));
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the slider line
        g.setColor(Theme.TABLE_BG_COLOR_HOVER);
        g.fillRect(0, (getHeight() - SIZE) / 2, getWidth() - H_PADDING - 5, SIZE);
        
        // Draw the handle
        g.setColor(Theme.TABLE_BG_COLOR);
        g.fillRect(getHandlePosition(currentValue), (getHeight() - SIZE) / 2, getHandleWidth(), SIZE);
        
        // Draw the current value in the handle
        
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(currentValue), getHandlePosition(currentValue) + getHandleWidth() / 2 - (Integer.toString(currentValue).length() * 4), (getHeight() - SIZE) / 2 + 17);
    }
}