package io.hurx.utils;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Utility class for managing resources, particularly images.
 */
public class Resources {
    
    /**
     * Loads an image as an ImageIcon from the specified relative path.
     *
     * @param path   the relative path to the image file, starting from the resources folder.
     * @param width  the desired width of the image after scaling.
     * @param height the desired height of the image after scaling.
     * @return an ImageIcon containing the loaded and scaled image, or null if the image could not be loaded.
     */
    public static ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            // Use the class loader to load the image from resources
            URL resourceUrl = Resources.class.getClassLoader().getResource(path);
            if (resourceUrl != null) {
                // Load the image
                Image image = ImageIO.read(resourceUrl);
                // Resize the image
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage); // Return the resized image
            } else {
                System.err.println("Couldn't find file: " + path);
                return null; // Or return a default icon
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null; // Or return a default icon
        }
    }
}
