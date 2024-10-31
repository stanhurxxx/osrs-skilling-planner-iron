package io.hurx.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import io.hurx.utils.Theme;

/**
 * The Icon component is a custom JPanel that displays an icon along with
 * an optional count and a sub-icon. It supports customizable padding
 * for layout adjustments.
 */
public class Icon extends JPanel {
    private ImageIcon icon; // The primary icon to be displayed
    private Integer amount; // The amount to be displayed alongside the icon
    private int hPadding = 10; // Horizontal padding around the icon
    private int vPadding = 10; // Vertical padding around the icon
    private ImageIcon subIcon; // The secondary icon to be displayed

    /**
     * Constructs an Icon component with the specified primary icon.
     *
     * @param icon The primary ImageIcon to display.
     */
    public Icon(ImageIcon icon) {
        this.icon = icon;
        this.amount = null; // Default amount to null
    }

    /**
     * Constructs an Icon component with the specified primary icon and amount.
     *
     * @param icon   The primary ImageIcon to display.
     * @param amount The amount to display alongside the icon.
     */
    public Icon(ImageIcon icon, Integer amount) {
        this.icon = icon;
        this.amount = amount;
    }

    /**
     * Retrieves the primary icon.
     *
     * @return The primary ImageIcon.
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Retrieves the amount displayed alongside the icon.
     *
     * @return The amount as an Integer, or null if not set.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the horizontal padding around the icon.
     *
     * @param hPadding The horizontal padding to set.
     */
    public void setHPadding(int hPadding) {
        this.hPadding = hPadding;
    }

    /**
     * Sets the vertical padding around the icon.
     *
     * @param vPadding The vertical padding to set.
     */
    public void setVPadding(int vPadding) {
        this.vPadding = vPadding;
    }

    /**
     * Retrieves the secondary icon, if set.
     *
     * @return The secondary ImageIcon, or null if not set.
     */
    public ImageIcon getSubIcon() {
        return this.subIcon;
    }

    /**
     * Sets the secondary icon to be displayed alongside the primary icon.
     *
     * @param subIcon The secondary ImageIcon to set.
     */
    public void setSubIcon(ImageIcon subIcon) {
        this.subIcon = subIcon;
    }

    /**
     * Paints the component, including the background, primary icon, amount,
     * and sub-icon.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background
        if (getBackground() != null) {
            setOpaque(true);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw the primary icon
        if (getIcon() != null) {
            g.drawImage(getIcon().getImage(), hPadding, vPadding, 
                         this.getWidth() - hPadding * 2, 
                         this.getWidth() - hPadding * 2, this);
        }

        // Draw the count
        if (getAmount() != null) {
            g.setColor(getAmount() >= 10_000_000 ? Theme.TABLE_FG_COLOR_SUCCESS :
                        getAmount() >= 100_000 ? Color.WHITE : Theme.TABLE_FG_COLOR_WARN);
            String string = getAmount() >= 10_000_000
                ? Integer.toString((int)Math.floor(getAmount() / 1_000_000)) + "M"
                : getAmount() >= 100_000
                    ? Integer.toString((int)Math.floor(getAmount() / 1_000)) + "K"
                    : Integer.toString(getAmount());
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(string, 2, 12); // Draw the amount text
        }

        // Draw the sub icon
        if (subIcon != null) {
            g.drawImage(subIcon.getImage(), 
                         (int)Math.round(getWidth() * 0.4), 
                         (int)Math.round(getWidth() * 0.4), 
                         (int)Math.round(getWidth() * 0.6), 
                         (int)Math.round(getWidth() * 0.6), this);
        }
    }
}
