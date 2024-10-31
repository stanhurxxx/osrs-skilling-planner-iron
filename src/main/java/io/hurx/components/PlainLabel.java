package io.hurx.components;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import io.hurx.utils.Theme;

/**
 * The PlainLabel class is a custom JLabel designed to display text
 * with specific padding and font styles. It simplifies the creation
 * of labeled components in the user interface by encapsulating common
 * configurations such as border and font settings.
 */
public class PlainLabel extends JLabel {
    /**
     * The embedded label used for displaying text or content.
     */
    private JLabel label;

    /**
     * Gets the embedded JLabel.
     *
     * @return The JLabel that is embedded in this PlainLabel component.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Constructs a PlainLabel instance with the specified text.
     * This constructor applies padding and sets the font style.
     *
     * @param text The text to be displayed by this label.
     */
    public PlainLabel(String text) {
        super(text);
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TABLE_V_PADDING, Theme.TABLE_H_PADDING, Theme.TABLE_V_PADDING, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.LABEL_SIZE));
    }
}
