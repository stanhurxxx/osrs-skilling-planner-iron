package io.hurx.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import io.hurx.components.MultiComboBox;
import io.hurx.components.table.defaultTable.DefaultTable;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.loggedOut.LoggedOutView;
import io.hurx.plugin.overview.OverviewView;
import io.hurx.plugin.slayer.SlayerView;

/**
 * The view master for the entire plugin
 */
public class PluginMaster extends ViewManagement.Entity.Master<PluginRepository, PluginViews> {
    public PluginMaster(PluginPanel root, PluginRepository repository) {
        super(root, repository, PluginViews.values(), repository.view);
        Container container = new Container(this);
        addContainer(container);
        addView(new SlayerView(this));
        addView(new LoggedOutView(this));
        addView(new OverviewView(this));
        onChangeView(() -> {
            container.onChangeView();
        });
    }

    /**
     * The container of the plugin master
     */
    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        private ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> navigationComponent;

        public Container(PluginMaster master) {
            super(master);
            navigationComponent = new NavigationComponent(this);
            addElement(navigationComponent);
        }

        /**
         * Callback for when the views have changed, generates a List of
         * JComboBoxes for view navigation.
         */
        private void onChangeView() {
            getRoot().getMasterHierarchy().clear();
            getRoot().findComponentsToBeRendered(new ArrayList<>());
            ViewManagement.Entity.View<?, ?, ?> view = getMaster().getActiveView();
            if (view != null && view.getView() == PluginViews.LoggedOut) {
                if (navigationComponent != null) {
                    removeElement(navigationComponent);
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
            navigationComponent.setComponent(new MultiComboBox(comboBoxes.toArray(new JComboBox<?>[comboBoxes.size()]), DefaultTable.Options.builder().columnCount(comboBoxes.size()).build()));
        }
    }
    
    public static class NavigationComponent extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {       
        public NavigationComponent(Container container) {
            super(container, container.getMaster().createViewsComboBox());
        }
    }
}