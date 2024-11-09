package io.hurx.plugin.slayer.views.list.views.manageTasks;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.views.list.SlayerListMaster;
import io.hurx.plugin.slayer.views.list.SlayerListViews;
import io.hurx.repository.slayer.SlayerListRepository;

/** The manage task sub view in the slayer list view */
public class SlayerListManageTasksView extends ViewManagement.Entity.View<SlayerListMaster, SlayerListRepository, SlayerListViews> {
    public SlayerListManageTasksView(SlayerListMaster master) {
        super(master, SlayerListViews.ManageTasks);
    }
}
