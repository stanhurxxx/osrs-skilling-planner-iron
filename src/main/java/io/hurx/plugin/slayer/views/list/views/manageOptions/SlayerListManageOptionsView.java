package io.hurx.plugin.slayer.views.list.views.manageOptions;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.views.list.SlayerListMaster;
import io.hurx.plugin.slayer.views.list.SlayerListViews;
import io.hurx.repository.slayer.SlayerListRepository;

/** Subview to manage the xp rates in the slayer list view */
public class SlayerListManageOptionsView extends ViewManagement.Entity.View<SlayerListMaster, SlayerListRepository, SlayerListViews> {
    public SlayerListManageOptionsView(SlayerListMaster master) {
        super(master, SlayerListViews.ManageOptions);
    }
}
