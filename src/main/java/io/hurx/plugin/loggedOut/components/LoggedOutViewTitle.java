package io.hurx.plugin.loggedOut.components;

import io.hurx.components.TitleLabel;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.containers.LoggedOutViewContainer;

/**
 * Represents the title component for the logged-out view in the plugin.
 * It displays the title "Login" to prompt the user for authentication.
 */
public class LoggedOutViewTitle extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {

    /**
     * Constructs a new LoggedOutViewTitle instance.
     *
     * @param container the container for the logged-out view
     */
    public LoggedOutViewTitle(LoggedOutViewContainer container) {
        super(container, new TitleLabel("Login"));
    }
}
