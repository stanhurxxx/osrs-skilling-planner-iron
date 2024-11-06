package io.hurx.plugin;

import io.hurx.models.views.ViewManagement;

/**
 * The view master for the entire plugin
 */
public class PluginMaster extends ViewManagement.Entity.Master<PluginRepository, PluginViews> {    
    public PluginMaster(PluginPanel root, PluginRepository repository) {
        super(root, repository, PluginViews.values(), repository.view);
        addView(new PluginView(this));
    }
}