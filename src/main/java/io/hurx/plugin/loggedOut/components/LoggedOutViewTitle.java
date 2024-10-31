package io.hurx.plugin.loggedOut.components;

import io.hurx.components.TitleLabel;
import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.LoggedOutView;
import io.hurx.plugin.loggedOut.containers.LoggedOutViewContainer;

public class LoggedOutViewTitle extends View.Component<TitleLabel, PluginMaster, LoggedOutView, LoggedOutViewContainer, PluginViews, PluginRepository> {
    public LoggedOutViewTitle(LoggedOutViewContainer container) {
        super(container, new TitleLabel("Login"));
    }
}