package io.hurx.plugin;

import io.hurx.components.MultiComboBox;
import io.hurx.components.Padding;
import io.hurx.components.comboBox.ComboBox;
import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.overview.OverviewView;
import io.hurx.plugin.profile.ProfileView;
import io.hurx.plugin.slayer.SlayerView;
import io.hurx.repository.PluginRepository;
import io.hurx.repository.ProfileRepository;
import io.hurx.utils.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * The view master for the entire plugin
 */
public class PluginMaster extends ViewManagement.Entity.Master<PluginRepository, PluginViews> {
    public PluginMaster(PluginPanel root, PluginRepository repository) {
        super(root, repository, PluginViews.values(), () -> {
            PluginRepository pluginRepository = (PluginRepository) Repository.registered.get(repository.generatePath());
            if (pluginRepository == null) return null;
            if (pluginRepository.account == null) return null;
            ProfileRepository profileRepository = pluginRepository.account.getProfile();
            if (profileRepository == null) return null;
            return profileRepository.view;
        });

        // The main container, containing the navigation
        Container container = new Container(this);
        addContainer(container);

        // Adds the views
        addView(new OverviewView(this));
        addView(new SlayerView(this));
        addView(new ProfileView(this));

        // Add the profile container to container of the master, so it shows either
        // when the view is Profile, or when there is no profile selected.
        addContainer(new ProfileView.Container(this));
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
            onBeforeRender(this::onBeforeRender);
        }

        /**
         * Callback for when the views have changed, generates a List of
         * JComboBoxes for view navigation.
         */
        private void onBeforeRender() {
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