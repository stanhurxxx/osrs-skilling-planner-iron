package io.hurx.plugin.loggedOut.containers;

import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.loggedOut.LoggedOutView;

public class LoggedOutViewContainer extends View.Container<PluginMaster, LoggedOutView, LoggedOutViewContainer, PluginViews, PluginRepository> {
    public LoggedOutViewContainer(LoggedOutView view) {
        super(view);
    }
}
