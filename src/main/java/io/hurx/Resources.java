package io.hurx;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Resources management
 */
public class Resources {
    /**
     * Loads an image
     * @param path the relative path from resources folder.
     * @param width the width of the image
     * @param height the height of the image
     * @return
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
