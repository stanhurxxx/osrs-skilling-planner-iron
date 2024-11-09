package io.hurx.components.textField;

import io.hurx.components.EditableComponent;
import io.hurx.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for JTextField with default text field styling options
 */
public class TextField extends JTextField implements EditableComponent {
    /** Set the hovered state */
    public TextField isHovered(boolean isHovered) {
        this.isHovered = isHovered;
        return this;
    }

    /** Is the text field hovered? */
    public boolean isHovered() {
        return isHovered;
    }

    // Flag indicating whether the text field is currently hovered.
    private boolean isHovered = false;

    /** Set the selected state */
    public TextField isSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    /** Is the text field selected? */
    public boolean isSelected() {
        return isSelected;
    }

    // Flag indicating whether the text field is currently selected.
    private boolean isSelected = false;

    public TextField(String text) {
        super(text);
        setFont(Theme.LABEL_FONT);
        setOpaque(true);
        setBackground(Theme.TABLE_BG_COLOR_HOVER);
        setForeground(Theme.TABLE_FG_COLOR_HOVER);
    }

    @Override
    public TextField onChange(Runnable runnable) {
        List<Runnable> runnables = onStopCellEditingRunnables.getOrDefault(this, new ArrayList<>());
        runnables.add(runnable);
        onStopCellEditingRunnables.put(this, runnables);
        return this;
    }
}
