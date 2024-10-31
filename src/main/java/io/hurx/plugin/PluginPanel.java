package io.hurx.plugin;

import io.hurx.utils.Theme;
import net.runelite.client.ui.DynamicGridLayout;

/**
 * The main panel
 */
public class PluginPanel extends net.runelite.client.ui.PluginPanel {  
    /**
     * Gets the main plugin entry instance
     * @return
     */
    public Plugin getPlugin() {
        return plugin;
    }
    private Plugin plugin;

    public PluginPanel(Plugin plugin) throws Exception {
        this.plugin = plugin;
        setLayout(new DynamicGridLayout(0, 1, 0, 0));
        setOpaque(true);
        setBackground(Theme.BG_COLOR);
        setDoubleBuffered(true);
    }
}
