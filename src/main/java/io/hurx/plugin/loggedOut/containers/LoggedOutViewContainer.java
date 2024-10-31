package io.hurx.plugin.loggedOut.containers;

import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.LoggedOutView;

/**
 * Container for the logged-out view in the plugin.
 * 
 * This class manages the logged-out view and its associated components,
 * providing the necessary context and functionality to the view.
 */
public class LoggedOutViewContainer extends View.Container<PluginMaster, LoggedOutView, LoggedOutViewContainer, PluginViews, PluginRepository> {

    /**
     * Constructs a new LoggedOutViewContainer instance.
     *
     * @param view the logged-out view that this container will manage
     */
    public LoggedOutViewContainer(LoggedOutView view) {
        super(view);
    }
}
