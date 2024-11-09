package io.hurx.plugin.slayer.views.list;

import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.slayer.SlayerMaster;
import io.hurx.plugin.slayer.SlayerViews;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.repository.slayer.SlayerRepository;

import javax.swing.*;

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
        super(master, SlayerViews.List);
        add(new Container(master));
    }

    /** Container for slayer list view */
    public static class Container extends ViewManagement.Entity.Container<SlayerMaster, SlayerRepository, SlayerViews> {
        public Container(SlayerMaster master) {
            super(master);
            master.getRepository().listUuid.listen(new Repository.Property.Listener<String>() {
                @Override
                public void onSet(String oldValue, String newValue) {
                    load(getMaster().getRepository().getList());
                }
            });
            load(getMaster().getRepository().getList());
        }

        private void load(SlayerListRepository list) {
            removeAll();
            if (list == null) return;
            add(new SlayerListMaster(getRoot(), list));
            getRoot().render();
        }
    }
}
