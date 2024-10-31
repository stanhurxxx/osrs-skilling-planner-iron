package io.hurx.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import io.hurx.utils.Colors;
import io.hurx.utils.Theme;

/**
 * The ProgressBar class is a custom JPanel that visually represents a 
 * percentage value as a horizontal bar. It is designed to provide 
 * feedback on progress, with a color gradient indicating different 
 * levels of completion.
 */
public class ProgressBar extends JPanel {
    /**
     * The current percentage value of the progress bar. 
     * It is a float value between 0 and 100.
     */
    private float value = 10;

    /**
     * Constructs a ProgressBar with the specified initial value.
     *
     * @param value The initial percentage value (0 to 100) for the progress bar.
     */
    public ProgressBar(float value) {
        this.value = value;
    }

    /**
     * Sets the percentage value of the progress bar.
     * 
     * @param value The new percentage value (0 to 100) to be set for the progress bar.
     */
    public void setValue(float value) {
        this.value = value;
        repaint(); // Repaint the component to reflect the new value
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background with a color that varies based on the percentage value
        g.setColor(Colors.mergeColors(Theme.TABLE_BG_COLOR_DANGER, Theme.TABLE_BG_COLOR_SUCCESS, value));
        g.fillRect(0, 0, (int)Math.round(getWidth() * value / 100), getHeight());
        
        // Prepare the percentage text to be displayed on the progress bar
        String string = Integer.toString((int)Math.round(value)) + "%";
        g.setColor(new Color(234, 234, 234)); // Set text color
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Draw the percentage text at the center of the progress bar
        g.drawString(string, (int)Math.round((getWidth() / 2) - ((string.length()) * 4)), getHeight() / 2 + 5); // Adjust the position as necessary
    }
}
