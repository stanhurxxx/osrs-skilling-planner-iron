package io.hurx.components.menuButton;

import javax.swing.JPanel;

import io.hurx.Resources;
import io.hurx.Theme;
import io.hurx.models.MenuIcons;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

/**
 * The Icon component
 */
public abstract class MenuButton extends JPanel {
    /**
     * The icon
     * @return
     */
    public MenuIcons getIcon() {
        return icon;
    }
    private MenuIcons icon;

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

    private ImageIcon imageIcon;

    public MenuButton(MenuIcons icon) {
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
     * The callback when the button has been clicked
     * @param e the mouse event
     */
    public abstract void mouseReleased(MouseEvent e);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background
        if (getBackground() != null) {
            setOpaque(true);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        // Draw the icon
        if (getIcon() != null) {
            g.drawImage(imageIcon.getImage(), hPadding, vPadding, this.getWidth() - hPadding * 2, this.getWidth() - hPadding * 2, this);
        }
    }
}
