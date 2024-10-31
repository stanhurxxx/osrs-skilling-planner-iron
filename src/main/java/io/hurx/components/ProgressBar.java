package io.hurx.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import io.hurx.utils.Colors;
import io.hurx.utils.Theme;

/**
 * The Icon component
 */
public class ProgressBar extends JPanel {
    /**
     * Set the percentage value
     * @param value
     */
    public void setValue(float value) {
        this.value = value;
    }
    private float value = 10;

    public ProgressBar(float value) {
        this.value = value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background
        g.setColor(Colors.mergeColors(Theme.TABLE_BG_COLOR_DANGER, Theme.TABLE_BG_COLOR_SUCCESS, value));
        g.fillRect(0, 0, (int)Math.round(getWidth() * value / 100), getHeight());
        
        // Draw the percentage text
        String string = Integer.toString((int)Math.round(value)) + "%";

        g.setColor(new Color(234, 234, 234));
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(string, (int)Math.round((getWidth() / 2) - ((string.length()) * 4)), getHeight() / 2 + 5); // Adjust the position as necessary
    }
}
