package io.hurx.components;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import io.hurx.Theme;

import java.awt.*;

/**
 * The Icon component
 */
public class Icon extends JPanel {
    /**
     * The icon
     * @return
     */
    public ImageIcon getIcon() {
        return icon;
    }
    private ImageIcon icon;

    /**
     * The amount displayed
     */
    public Integer getAmount() {
        return amount;
    }
    private Integer amount;

    /**
     * Set the horizontal padding
     * @param hPadding the padding
     */
    public void setHPadding(int hPadding) {
        this.hPadding = hPadding;
    }
    private int hPadding = 10;

    /**
     * Set the vertical padding
     * @param vPadding
     */
    public void setVPadding(int vPadding) {
        this.vPadding = vPadding;
    }
    private int vPadding = 10;

    public Icon(ImageIcon icon) {
        this.icon = icon;
        this.amount = null;
    }

    public Icon(ImageIcon icon, Integer amount) {
        this.icon = icon;
        this.amount = amount;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the icon
        if (getIcon() != null) {
            g.drawImage(getIcon().getImage(), hPadding, vPadding, this.getWidth() - hPadding * 2, this.getWidth() - hPadding * 2, this);
        }
        // Draw the count
        if (getAmount() != null) {
            g.setColor(getAmount() >= 10_000_000 ? Theme.TABLE_FG_COLOR_SUCCESS : getAmount() >= 100_000 ? Color.WHITE : Theme.TABLE_FG_COLOR_WARN); // Set the color for the count text
            String string = getAmount() >= 10_000_000
                ? Integer.toString((int)Math.floor(getAmount() / 1_000_000)) + "M"
                : getAmount() >= 100_000
                    ? Integer.toString((int)Math.floor(getAmount() / 1_000)) + "K"
                    : Integer.toString(getAmount());
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(string, 2, 12); // Adjust the position as necessary
        }
    }
}
