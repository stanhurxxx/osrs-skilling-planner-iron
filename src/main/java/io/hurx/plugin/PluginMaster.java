package io.hurx.plugin;

import io.hurx.models.views.View;
import io.hurx.plugin.slayer.SlayerView;

public class PluginMaster extends View.Master<PluginMaster, PluginViews, PluginRepository> {
    public PluginMaster(Plugin root, PluginRepository repository) {
        super(root, repository.view, repository, PluginViews.values());
        add(new SlayerView(null));
    }
}
