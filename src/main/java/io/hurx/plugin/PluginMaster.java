package io.hurx.plugin;

import io.hurx.components.MultiComboBox;
import io.hurx.components.Padding;
import io.hurx.components.comboBox.ComboBox;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.overview.OverviewView;
import io.hurx.plugin.profile.ProfileView;
import io.hurx.plugin.slayer.SlayerView;
import io.hurx.repository.PluginRepository;
import io.hurx.utils.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * The view master for the entire plugin
 */
public class PluginMaster extends ViewManagement.Entity.Master<PluginRepository, PluginViews> {
    @SuppressWarnings("unchecked")
    @Override
    public ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> getActiveView() {
        if (getRepository().account == null || getRepository().account.profileUuid.get() == null) {
            return null;
        }
        else {
            return (ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews>) super.getActiveView();
        }
    }

    public PluginMaster(PluginPanel root, PluginRepository repository) {
        super(root, repository, PluginViews.values(), repository.view);

        // Triggers when the plugin is ready to be rendered
        onReady(() -> {
            // The main container, containing the navigation
            Container container = new Container(this);
            addContainer(container);
            onChangeView(container::onChangeView);

            // Adds the views
            addView(new OverviewView(this));
            addView(new SlayerView(this));
            addView(new ProfileView(this));

            // Add the profile container to container of the master, so it shows either
            // when the view is Profile, or when there is no profile selected.
            addContainer(new ProfileView.Container(this));

            // Trigger on change view initially
            container.onChangeView();

            // Start rendering
            getRoot().render();
        });
    }

    /**
     * The container of the plugin master
     */
    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        @Override
        public boolean isVisible() {
            return getMaster().getRepository().account.getProfile() != null;
        }

        public Container(PluginMaster master) {
            super(master);
        }

        /**
         * Callback for when the views have changed, generates a List of
         * JComboBoxes for view navigation.
         */
        private void onChangeView() {
            if (!isVisible()) return;

            removeAll();

            getRoot().getMasterHierarchy().clear();
            getRoot().findComponentsToBeRendered(new ArrayList<>());
            ViewManagement.Entity.View<?, ?, ?> view = getMaster().getActiveView();
            int limit = 2;
            List<ComboBox<?>> comboBoxes = new ArrayList<>();
            for (int i = Math.max(0, getRoot().getMasterHierarchy().size() - limit); i < getRoot().getMasterHierarchy().size(); i ++) {
                ViewManagement.Entity.Master<?, ?> m = getRoot().getMasterHierarchy().get(i);
                comboBoxes.add(m.createViewsComboBox());
                limit --;
                if (limit == 0) break;
            }
            if (comboBoxes.isEmpty()) {
                comboBoxes.add(getMaster().createViewsComboBox());
            }

            add(new MultiComboBox(comboBoxes.toArray(new ComboBox<?>[0])).render());
            add(new Padding(Theme.TITLE_V_PADDING));
        }
    }
}