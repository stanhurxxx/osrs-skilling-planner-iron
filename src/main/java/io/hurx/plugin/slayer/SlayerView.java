package io.hurx.plugin.slayer;

import javax.swing.JLabel;

import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginViews;
import io.hurx.repository.PluginRepository;
import io.hurx.repository.ProfileRepository;
import io.hurx.repository.slayer.SlayerRepository;

/**
 * Represents the Slayer view in the plugin system.
 * <p>
 * This class extends the generic {@link ViewManagement.Entity.View} class, allowing it
 * to manage and display information related to Slayer tasks within
 * the plugin framework.
 * </p>
 */
public class SlayerView extends ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> {
    @Override
    public boolean isValidated() {
        // TODO:
        return true;
    }

    /**
     * Constructs a new SlayerView instance.
     *
     * @param master the master plugin instance that this view is associated with
     */
    public SlayerView(PluginMaster master) {
        super(master, PluginViews.Slayer);
        add(new Container(master));
    }

    /**
     * The slayer view container
     */
    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        public Container(PluginMaster master) {
            super(master);
            load(master.getRepository().account.getProfile());
            onBeforeRender(() -> {
                if (getMaster().getRepository().account.getProfile() == null) load(null);
                else if (getMaster().getRepository().account.getProfile().view.wasDirty()) {
                    load(getMaster().getRepository().account.getProfile());
                }
            });
        }

        private void load(ProfileRepository profileRepository) {
            removeAll();
            if (profileRepository == null) return;
            if (getMaster().getRepository().account == null || getMaster().getRepository().account.getProfile() == null || getMaster().getRepository().account.getProfile().slayer == null) return;
            getMaster().getRepository().account.getProfile().initialize();
            add(new SlayerMaster(getMaster().getRoot(), getMaster().getRepository().account.getProfile().slayer));
        }
    }
}
