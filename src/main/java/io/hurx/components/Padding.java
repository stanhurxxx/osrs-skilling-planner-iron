package io.hurx.components;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import io.hurx.utils.Theme;

/**
 * The Padding class is a custom JLabel that adds padding to the top of a component.
 * It is designed to be used when a visual gap is needed between components in a 
 * user interface.
 */
public class Padding extends JLabel {
    /**
     * The embedded label used for displaying text or content.
     */
    private JLabel label;

    /**
     * Gets the embedded JLabel.
     *
     * @return The JLabel that is embedded in this Padding component.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Constructs a Padding instance with the specified padding.
     *
     * @param padding The amount of padding (in pixels) to apply to the top of the label.
     */
    public Padding(int padding) {
        super("");
        this.setBorder(BorderFactory.createEmptyBorder(padding, 0, 0, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
    }

    /**
     * Constructs a Padding instance with default padding.
     * The default padding is obtained from the Theme utility class.
     */
    public Padding() {
        super("");
        this.setBorder(BorderFactory.createEmptyBorder(Theme.TITLE_V_PADDING, 0, 0, 0));
        this.setFont(new Font("Arial", Font.BOLD, Theme.TITLE_SIZE));
    }
}