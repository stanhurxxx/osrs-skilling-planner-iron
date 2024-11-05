package io.hurx.plugin.loggedOut;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.containers.LoggedOutViewContainer;

/**
 * Represents the logged-out view of the plugin.
 * 
 * This class is responsible for the presentation and management of the logged-out 
 * state of the plugin, allowing users to interact with the login interface.
 */
public class LoggedOutView extends ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> {
    /**
     * Constructs a new LoggedOutView instance.
     *
     * @param master the PluginMaster instance that manages the plugin's lifecycle
     */
    public LoggedOutView(PluginMaster master) {
        super(master, PluginViews.LoggedOut);
        addContainer(new LoggedOutViewContainer(master));
    }
}
