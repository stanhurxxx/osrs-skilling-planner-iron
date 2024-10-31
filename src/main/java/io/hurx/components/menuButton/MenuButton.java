package io.hurx.components.menuButton;

import javax.swing.JPanel;

import io.hurx.models.MenuButtons;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

/**
 * An abstract class representing a menu button with an icon.
 * This class extends JPanel and provides functionalities to handle
 * mouse events and rendering of the button with an icon.
 */
public abstract class MenuButton extends JPanel {
    
    /** The icon associated with this menu button. */
    private MenuButtons icon;

    /** The horizontal padding around the icon. */
    private int hPadding = 10;

    /** The vertical padding around the icon. */
    private int vPadding = 10;

    /** The image icon loaded from resources. */
    private ImageIcon imageIcon;

    /**
     * Constructs a MenuButton with the specified icon.
     *
     * @param icon the MenuButtons object representing the icon for the button
     */
    public MenuButton(MenuButtons icon) {
        MenuButton button = this;
        this.icon = icon;
        this.imageIcon = Resources.loadImageIcon(icon.getIconPath().getPath(), 512, 512);
        vPadding = 2;
        hPadding = 2;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    button.mouseReleased(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Theme.TABLE_BG_COLOR_HOVER);
                revalidate();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(null);
                revalidate();
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setBackground(Theme.TABLE_BG_COLOR_HOVER);
                revalidate();
                repaint();
            }
        });

        setToolTipText(icon.getName());
    }

    /**
     * Retrieves the icon associated with this menu button.
     *
     * @return the MenuButtons object representing the icon
     */
    public MenuButtons getIcon() {
        return icon;
    }

    /**
     * Sets the horizontal padding around the icon.
     *
     * @param hPadding the padding value to set
     */
    public void setHPadding(int hPadding) {
        this.hPadding = hPadding;
    }

    /**
     * Sets the vertical padding around the icon.
     *
     * @param vPadding the padding value to set
     */
    public void setVPadding(int vPadding) {
        this.vPadding = vPadding;
    }

    /**
     * The callback method to be implemented by subclasses
     * for handling mouse release events on the button.
     *
     * @param e the mouse event triggered by the release action
     */
    public abstract void mouseReleased(MouseEvent e);

    /**
     * Paints the component. This method is called whenever
     * the component needs to be rendered.
     *
     * @param g the graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background if set
        if (getBackground() != null) {
            setOpaque(true);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Draw the icon if available
        if (getIcon() != null) {
            g.drawImage(imageIcon.getImage(), hPadding, vPadding,
                         this.getWidth() - hPadding * 2, this.getWidth() - hPadding * 2, this);
        }
    }
}