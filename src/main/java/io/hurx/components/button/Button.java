package io.hurx.components.button;

import io.hurx.components.EditableComponent;
import io.hurx.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for a JButton with default styling
 */
public class Button extends JButton implements EditableComponent {
    /** Set the hovered state */
    public void isHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    /** Is the button hovered? */
    public boolean isHovered() {
        return isHovered;
    }

    // Flag indicating whether the button is currently hovered.
    private boolean isHovered = false;

    /** The runnables to run on click */
    private List<Runnable> onClickRunnables = new ArrayList<>();

    public Button(String text) {
        super(text);
        Button button = this;
        setOpaque(true);
        setBackground(Theme.TABLE_BG_COLOR_SELECTED);
        setFont(Theme.LABEL_FONT);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (Runnable runnable : onClickRunnables) {
                        runnable.run();
                    }
                }
            }
        });
    }

    public Button onClick(Runnable runnable) {
        onClickRunnables.add(runnable);
        return this;
    }

    @Override
    public Button onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
    }
}
