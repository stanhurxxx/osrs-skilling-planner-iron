package io.hurx.plugin.slayer;

import io.hurx.models.views.View;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;

/**
 * Represents the Slayer view in the plugin system.
 * <p>
 * This class extends the generic {@link View} class, allowing it
 * to manage and display information related to Slayer tasks within
 * the plugin framework.
 * </p>
 */
public class SlayerView extends View<PluginMaster, PluginViews, PluginRepository> {

    /**
     * Constructs a new SlayerView instance.
     *
     * @param master the master plugin instance that this view is associated with
     */
    public SlayerView(PluginMaster master) {
        super(master, PluginViews.Slayer);
    }
}
