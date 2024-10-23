package io.hurx.components;
import javax.swing.*;
import java.awt.*;

import io.hurx.Theme;

public class PlainLabel extends JLabel {
    /**
     * Get the JLabel
     * @return
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * The embedded label
     */
    private JLabel label;

    public PlainLabel(String text) {
        super(text);
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.LABEL_SIZE));
    }
}
