package io.hurx.plugin.slayer.views.overview;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.SlayerMaster;
import io.hurx.plugin.slayer.SlayerViews;
import io.hurx.repository.slayer.SlayerRepository;

/** The slayer overview view */
public class SlayerOverviewView extends ViewManagement.Entity.View<SlayerMaster, SlayerRepository, SlayerViews> {
    public SlayerOverviewView(SlayerMaster master) {
        super(master, SlayerViews.Overview);
    }
}
