package io.hurx.plugin;

import io.hurx.utils.Theme;
import net.runelite.client.ui.DynamicGridLayout;

/**
 * The PluginPanel class represents the main panel for the Ironman Skilling Planner plugin.
 * It provides a user interface layout for the plugin's features and functionalities.
 */
public class PluginPanel extends net.runelite.client.ui.PluginPanel {
    
    /** 
     * The instance of the main plugin associated with this panel.
     */
    private Plugin plugin;

    /**
     * Constructs a PluginPanel with the specified plugin instance.
     *
     * @param plugin the instance of the plugin that this panel is associated with.
     * @throws Exception if an error occurs during panel initialization.
     */
    public PluginPanel(Plugin plugin) throws Exception {
        this.plugin = plugin;
        setLayout(new DynamicGridLayout(0, 1, 0, 0)); // Sets the layout manager for this panel.
        setOpaque(true); // Makes the panel opaque.
        setBackground(Theme.BG_COLOR); // Sets the background color of the panel.
        setDoubleBuffered(true); // Enables double buffering for smoother rendering.
    }

    /**
     * Gets the main plugin instance associated with this panel.
     *
     * @return the Plugin instance that this panel is associated with.
     */
    public Plugin getPlugin() {
        return plugin;
    }
}
