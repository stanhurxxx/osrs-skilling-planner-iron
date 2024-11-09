package io.hurx.plugin.overview;

import javax.swing.JLabel;

import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginViews;
import io.hurx.repository.PluginRepository;

/**
 * Represents the overview view of the plugin.
 * 
 * This class is responsible for displaying the main overview interface,
 * which presents various statistics, financial information, and other
 * relevant data to the user. It extends the base View class, utilizing
 * the plugin's master and repository for lifecycle management and data access.
 */
public class OverviewView extends ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> {
    @Override
    public boolean isValidated() {
        // TODO:
        return true;
    }
    
    /**
     * Constructs a new OverviewView instance.
     *
     * @param master the PluginMaster instance that manages the plugin's lifecycle
     */
    public OverviewView(PluginMaster master) {
        super(master, PluginViews.Overview);
        add(new Container(master));
    }

    /**
     * The overview container
     */
    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        public Container(PluginMaster master) {
            super(master);
            add(new SomeComponent(this));
        }
    }

    // TODO: remove
    public static class SomeComponent extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {
        public SomeComponent(Container container) {
            super(container, new JLabel("Overview!"));
        }
    }

    // @Override
    // public void load() {
        // panel.add(panel.createPagesComboBox());

        // panel.add(new TitleLabel("Hourlies"));
        // // panel.add(new Table(
        // //     panel,
        // //     new Object[][] {
        // //         {
        // //             Resources.loadImageIcon("icons/herb.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Herb runs"),
        // //             new JLabel("12.019 left")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Contracts"),
        // //             new JLabel("12.019 left")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/birdhouse.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Birdhouses"),
        // //             new JLabel("12.019 left")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/seaweed.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Seaweed"),
        // //             new JLabel("12.019 left")
        // //         }
        // //     }
        // // ));

        // panel.add(new TitleLabel("Money"));
        // // panel.add(new Table(
        // //     panel,
        // //     new Object[][] {
        // //         {
        // //             Resources.loadImageIcon("icons/herb.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Profit"),
        // //             new JLabel("5.01B")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Profit (drop trade)"),
        // //             new JLabel("1.5B")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Costs"),
        // //             new JLabel("3.5B")
        // //         },
        // //         {
        // //             Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Spare money"),
        // //             new JLabel("3.01B")
        // //         }
        // //     }
        // // ));

        // panel.add(new TitleLabel("Stats"));
        // // panel.add(new Table(
        // //     panel,
        // //     new Object[][] {
        // //         {
        // //             Resources.loadImageIcon("icons/slayer.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
        // //             new JLabel("Slayer"),
        // //             new JLabel("983.05 EHP")
        // //         }
        // //     }
        // // ));
    // }
}
