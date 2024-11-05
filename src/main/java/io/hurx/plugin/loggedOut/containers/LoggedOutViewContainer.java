package io.hurx.plugin.loggedOut.containers;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.components.LoggedOutViewDescription;
import io.hurx.plugin.loggedOut.components.LoggedOutViewTitle;

/**
 * Container for the logged-out view in the plugin.
 * 
 * This class manages the logged-out view and its associated components,
 * providing the necessary context and functionality to the view.
 */
public class LoggedOutViewContainer extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {

    /**
     * Constructs a new LoggedOutViewContainer instance.
     *
     * @param view the logged-out view that this container will manage
     */
    public LoggedOutViewContainer(PluginMaster master) {
        super(master);
        addElement(new LoggedOutViewTitle(this));
        addElement(new LoggedOutViewDescription(this));
    }
}
