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
        return container == null || container.master == null || container.master.isValidated();
    }

    /** Ref to the container of slayer list view */
    private Container container;
    
    public SlayerListView(SlayerMaster master) {
        super(master, SlayerViews.List);
        container = new Container(master);
        add(container);
    }

    /** Container for slayer list view */
    public static class Container extends ViewManagement.Entity.Container<SlayerMaster, SlayerRepository, SlayerViews> {
        /** The master for the slayer list view */
        private SlayerListMaster master;

        public Container(SlayerMaster master) {
            super(master);
            load(getMaster().getRepository().getList());
            onBeforeRender(() -> {
                if (getMaster().getRepository().listUuid.wasDirty()) {
                    load(getMaster().getRepository().getList());
                }
            });
        }

        /** Loads a new slayer list master when the list changes, or initially */
        private void load(SlayerListRepository list) {
            removeAll();
            if (list == null) return;
            master = new SlayerListMaster(getRoot(), list);
            add(master);
        }
    }
}
