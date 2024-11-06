package io.hurx.plugin.profile.slayer;

import javax.swing.JLabel;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.profile.ProfileMaster;
import io.hurx.plugin.profile.ProfileRepository;
import io.hurx.plugin.profile.ProfileViews;
import io.hurx.plugin.profile.slayer.repository.SlayerRepository;

/**
 * Represents the Slayer view in the plugin system.
 * <p>
 * This class extends the generic {@link View} class, allowing it
 * to manage and display information related to Slayer tasks within
 * the plugin framework.
 * </p>
 */
public class SlayerView extends ViewManagement.Entity.View<ProfileMaster, ProfileRepository, ProfileViews> {
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
    public SlayerView(ProfileMaster master) {
        super(master, ProfileViews.Slayer);
        add(new Container(master));
    }

    /**
     * The slayer view container
     */
    public static class Container extends ViewManagement.Entity.Container<ProfileMaster, ProfileRepository, ProfileViews> {
        public Container(ProfileMaster master) {
            super(master);
            add(new SomeComponent(this));
            add(new SomeComponent(this));
            add(new SlayerMaster(master.getRoot(), new SlayerRepository(master.getRepository()).initialize()));
        }
    }

    // TODO: remove
    public static class SomeComponent extends ViewManagement.Entity.Component<ProfileMaster, ProfileRepository, ProfileViews> {
        public SomeComponent(Container container) {
            super(container, new JLabel("Test slayer"));
        }
    }
}
