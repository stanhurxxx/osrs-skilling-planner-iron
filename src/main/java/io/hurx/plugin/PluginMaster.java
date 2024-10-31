package io.hurx.plugin;

import io.hurx.models.views.View;
import io.hurx.plugin.slayer.SlayerView;
import io.hurx.plugin.loggedOut.LoggedOutView;
import io.hurx.plugin.overview.OverviewView;

/**
 * The PluginMaster class manages the views for the Ironman Skilling Planner plugin.
 * It extends the Master class to provide functionality for handling multiple views
 * associated with the plugin.
 */
public class PluginMaster extends View.Master<PluginMaster, PluginViews, PluginRepository> {

    /**
     * Constructs a PluginMaster instance with the specified root and repository.
     *
     * @param root the root of the plugin, providing context and access to other components.
     * @param repository the repository that holds the data for this master view.
     */
    public PluginMaster(Plugin root, PluginRepository repository) {
        super(root, repository.view, repository, PluginViews.values());
        add(new SlayerView(this)); // Adds the SlayerView to the list of managed views.
        add(new LoggedOutView(this));
        add(new OverviewView(this));
    }
}