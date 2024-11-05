package io.hurx.plugin.slayer;

import javax.swing.JLabel;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.slayer.repository.SlayerRepository;

/**
 * Represents the Slayer view in the plugin system.
 * <p>
 * This class extends the generic {@link View} class, allowing it
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
        addContainer(new Container(master));
    }

    /**
     * The slayer view container
     */
    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        public Container(PluginMaster master) {
            super(master);
            addElement(new SomeComponent(this));
            addElement(new SomeComponent(this));
            addElement(new SlayerMaster(master.getRoot(), new SlayerRepository(master.getRepository()).initialize()));
        }
    }

    // TODO: remove
    public static class SomeComponent extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {
        public SomeComponent(Container container) {
            super(container, new JLabel("Test slayer"));
        }
    }
}
