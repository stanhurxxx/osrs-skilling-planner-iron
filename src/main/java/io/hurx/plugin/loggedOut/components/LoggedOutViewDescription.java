package io.hurx.plugin.loggedOut.components;

import io.hurx.components.PlainLabel;
import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.LoggedOutView;
import io.hurx.plugin.loggedOut.containers.LoggedOutViewContainer;

/**
 * Represents the description component for the logged-out view in the plugin.
 * It displays a message prompting the user to log in to use the plugin features.
 */
public class LoggedOutViewDescription extends View.Component<PlainLabel, PluginMaster, LoggedOutView, LoggedOutViewContainer, PluginViews, PluginRepository> {

    /**
     * Constructs a new LoggedOutViewDescription instance.
     *
     * @param container the container for the logged-out view
     */
    public LoggedOutViewDescription(LoggedOutViewContainer container) {
        super(container, new PlainLabel("Login to use this plugin."));
    }
}
