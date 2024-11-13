package io.hurx.components.menuButton;

import javax.swing.*;

import io.hurx.components.EditableComponent;
import io.hurx.models.MenuButtons;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract class representing a menu button with an icon.
 * This class extends JPanel and provides functionalities to handle
 * mouse events and rendering of the button with an icon.
 */
public class MenuButton extends JPanel implements EditableComponent {
    
    /** The icon associated with this menu button. */
    private MenuButtons icon;

    /** The horizontal padding around the icon. */
    private int hPadding = 10;

    /** The vertical padding around the icon. */
    private int vPadding = 10;

    /** The image icon loaded from resources. */
    private ImageIcon imageIcon;

    // The MenuButton instance being rendered.
    private MenuButton menuButton;

    /** Is the button hovered? */
    public boolean isHovered() {
        return isHovered;
    }

    /** Set the hovered state */
    public void isHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    // Flag indicating whether the button is currently hovered.
    private boolean isHovered = false;

    /** Is the button selected? */
    public boolean isSelected() {
        return isSelected;
    }

    /** Set the selected state */
    public MenuButton isSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    // Flag indicating whether the button is currently selected.
    private boolean isSelected = false;

    /** The runnables to run on click */
    private List<Runnable> onClickRunnables = new ArrayList<>();

    /** GET The last mouse event */
    public MouseEvent event() {
        return event;
    }
    /** The last mouse event */
    private MouseEvent event = null;

    /**
     * Constructs a MenuButton with the specified icon.
     *
     * @param icon the MenuButtons object representing the icon for the button
     */
    public MenuButton(MenuButtons icon) {
        MenuButton button = this;
        this.icon = icon;
        this.imageIcon = Resources.loadImageIcon(icon.getIconPath().getPath(), 512, 512);
        vPadding = 5;
        hPadding = 5;
        setToolTipText(icon.getName());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                event = e;
                for (Runnable runnable : onClickRunnables) {
                    runnable.run();
                }
            }
        });
    }

    public MenuButton onClick(Runnable runnable) {
        onClickRunnables.add(runnable);
        return this;
    }

    @Override
    public MenuButton onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
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
    public void mouseReleased(MouseEvent e) {}

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
        setOpaque(true);
        if (getBackground() != null) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getHeight(), getHeight());
        }
        
        // Draw the icon if available
        if (getIcon() != null) {
            g.drawImage(
                imageIcon.getImage(),
                hPadding,
                vPadding,
                this.getHeight() - hPadding * 2,
                this.getHeight() - vPadding * 2,
                this
            );
        }
    }
}