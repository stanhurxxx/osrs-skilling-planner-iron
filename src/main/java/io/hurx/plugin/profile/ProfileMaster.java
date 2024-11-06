package io.hurx.plugin.profile;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import io.hurx.components.MultiComboBox;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginPanel;
import io.hurx.plugin.profile.overview.OverviewView;
import io.hurx.plugin.profile.slayer.SlayerView;

public class ProfileMaster extends ViewManagement.Entity.Master<ProfileRepository, ProfileViews> {
    public ProfileMaster(PluginPanel root, ProfileRepository repository) {
        super(root, repository, ProfileViews.values(), null);
        Container container = new Container(this);
        addContainer(container);
        addView(new SlayerView(this));
        addView(new OverviewView(this));
        onChangeView(() -> {
            container.onChangeView();
        });
    }

    /**
     * The container of the plugin master
     */
    public static class Container extends ViewManagement.Entity.Container<ProfileMaster, ProfileRepository, ProfileViews> {
        private ViewManagement.Entity.Component<ProfileMaster, ProfileRepository, ProfileViews> navigationComponent;

        public Container(ProfileMaster master) {
            super(master);
            navigationComponent = new NavigationComponent(this);
            add(navigationComponent);
        }

        /**
         * Callback for when the views have changed, generates a List of
         * JComboBoxes for view navigation.
         */
        private void onChangeView() {
            getRoot().getMasterHierarchy().clear();
            getRoot().findComponentsToBeRendered(new ArrayList<>());
            ViewManagement.Entity.View<?, ?, ?> view = getMaster().getActiveView();
            if (view != null) {
                if (navigationComponent != null) {
                    remove(navigationComponent);
                    navigationComponent = null;
                }
                return;
            }
            int limit = 2;
            List<JComboBox<?>> comboBoxes = new ArrayList<>();
            for (int i = Math.max(0, getRoot().getMasterHierarchy().size() - limit); i < getRoot().getMasterHierarchy().size(); i ++) {
                ViewManagement.Entity.Master<?, ?> m = getRoot().getMasterHierarchy().get(i);
                comboBoxes.add(m.createViewsComboBox());
                limit --;
                if (limit == 0) break;
            }
            if (comboBoxes.size() == 0) {
                comboBoxes.add(getMaster().createViewsComboBox());
            }
            if (navigationComponent == null) {
                navigationComponent = new NavigationComponent(this);
            }
            navigationComponent.setComponent(new MultiComboBox(comboBoxes.toArray(new JComboBox<?>[0])));
        }
    }
    
    public static class NavigationComponent extends ViewManagement.Entity.Component<ProfileMaster, ProfileRepository, ProfileViews> {       
        public NavigationComponent(Container container) {
            super(container, container.getMaster().createViewsComboBox());
        }
    }
}
