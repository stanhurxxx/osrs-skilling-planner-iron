package io.hurx.plugin.loggedOut;

import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;

/**
 * The overview view
 */
public class LoggedOutView extends View<PluginMaster, PluginViews, PluginRepository> {
    public LoggedOutView(PluginMaster master) {
        super(master, PluginViews.LoggedOut);
    }
}
