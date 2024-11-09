package io.hurx.plugin.slayer.views.planner;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.SlayerMaster;
import io.hurx.plugin.slayer.SlayerViews;
import io.hurx.repository.slayer.SlayerRepository;

/** The slayer planner view */
public class SlayerPlannerView extends ViewManagement.Entity.View<SlayerMaster, SlayerRepository, SlayerViews> {
    public SlayerPlannerView(SlayerMaster master) {
        super(master, SlayerViews.Planner);
    }
}
