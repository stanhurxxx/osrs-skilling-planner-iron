package io.hurx.plugin.profile.slayer.views.list;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.profile.slayer.SlayerMaster;
import io.hurx.plugin.profile.slayer.SlayerViews;
import io.hurx.plugin.profile.slayer.repository.SlayerRepository;

/**
 * The view for a slayer list
 */
public class SlayerListView extends ViewManagement.Entity.View<SlayerMaster, SlayerRepository, SlayerViews> {
    @Override
    public boolean isValidated() {
        // TODO:
        return true;
    }
    
    public SlayerListView(SlayerMaster master) {
        super(master, SlayerViews.Overview);
    }
}
