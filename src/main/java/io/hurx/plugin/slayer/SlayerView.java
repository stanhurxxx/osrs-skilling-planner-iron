package io.hurx.plugin.slayer;

import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;

public class SlayerView extends View<PluginMaster, PluginViews, PluginRepository> {
    public SlayerView(PluginMaster master) {
        super(master, PluginViews.Slayer);
    }
}
