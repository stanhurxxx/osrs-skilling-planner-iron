package io.hurx.components;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import io.hurx.utils.Theme;

public class Padding extends JLabel {
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

    public Padding() {
        super("");
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TITLE_V_PADDING, 0, 0, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
    }
}
