package io.hurx.components;
import javax.swing.*;
import java.awt.*;

import io.hurx.Theme;

public class TitleLabel extends JLabel {
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

    public TitleLabel(String text) {
        super(text);
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TITLE_V_PADDING, Theme.TITLE_H_PADDING, Theme.TITLE_V_PADDING, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
    }
}
