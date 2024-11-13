package io.hurx.plugin.slayer;

import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginPanel;
import io.hurx.plugin.slayer.views.overview.SlayerOverviewView;
import io.hurx.plugin.slayer.views.planner.SlayerPlannerView;
import io.hurx.repository.slayer.SlayerRepository;
import io.hurx.plugin.slayer.views.list.SlayerListView;

/**
 * The master for the slayer view in the plugin views
 */
public class SlayerMaster extends ViewManagement.Entity.Master<SlayerRepository, SlayerViews> {    
    public SlayerMaster(PluginPanel root, SlayerRepository repository) {
        super(
            root,
            repository,
            SlayerViews.values(),
            () -> {
                SlayerRepository slayerRepository = (SlayerRepository) Repository.registered.get(repository.generatePath());
                return slayerRepository.view;
            }
        );
        // Adds a one to many relation to the slayer lists,
        // so they appear in the combo box.
        oneToMany(new OneToMany(
            repository,
            repository.lists,
            repository.listUuid,
            new SlayerListView(this)
        ));
        addView(new SlayerOverviewView(this));
        addView(new SlayerPlannerView(this));
    }
}
