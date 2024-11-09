package io.hurx.plugin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import io.hurx.components.table.Table;
import io.hurx.models.views.ViewManagement;
import io.hurx.utils.Theme;
import net.runelite.client.ui.DynamicGridLayout;

/**
 * The {@code PluginPanel} class represents the main panel for the Ironman Skilling Planner plugin.
 * It provides a user interface layout for the plugin's features and functionalities, allowing 
 * dynamic rendering of views and components based on the plugin state.
 */
public class PluginPanel extends net.runelite.client.ui.PluginPanel {
    /**
     * A list that maintains the hierarchy of master views, starting from the deepest nested master view.
     */
    private List<ViewManagement.Entity.Master<?, ?>> masterHierarchy = new ArrayList<>();

    /**
     * The instance of the main plugin associated with this panel.
     */
    private Plugin plugin;

    /**
     * Indicates whether the panel is ready for rendering.
     */
    private boolean ready = false;

    /**
     * Keeps track of the reloaded masters, so they don't reload more than once.
     */
    private List<ViewManagement.Entity.Master<?, ?>> reloadedMasters = new ArrayList<>();

    /**
     * Retrieves the hierarchy of master views associated with this panel.
     *
     * @return a list of master views of type {@code List<ViewManagement.Entity.Master<?, ?>>}
     */
    public List<ViewManagement.Entity.Master<?, ?>> getMasterHierarchy() {
        return masterHierarchy;
    }

    /**
     * Checks if the panel is ready for rendering.
     *
     * @return {@code true} if the panel is ready; {@code false} otherwise
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Sets the ready state of the panel.
     *
     * @param ready the new ready state to set
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Constructs a {@code PluginPanel} with the specified plugin instance.
     *
     * @param plugin the instance of the plugin associated with this panel
     * @throws Exception if an error occurs during panel initialization
     */
    public PluginPanel(Plugin plugin) throws Exception {
        this.plugin = plugin;
        setLayout(new DynamicGridLayout(0, 1, 0, 0)); // Sets the layout manager for this panel
        setOpaque(true); // Makes the panel opaque
        setBackground(Theme.BG_COLOR); // Sets the background color of the panel
        setDoubleBuffered(true); // Enables double buffering for smoother rendering
    }

    /**
     * Gets the main plugin instance associated with this panel.
     *
     * @return the plugin instance that this panel is associated with
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Renders the views into the component.
     * Clears the existing hierarchy and checks if the panel is ready before rendering.
     */
    public void render() {
        masterHierarchy.clear();
        if (!ready) return;

        // Reload all outdated repositories
        reloadRepositories();

        // Run all on change view runnables
        for (Runnable runnable : getPlugin().getMaster().getOnChangeViewRunnables()) {
            runnable.run();
        }

        // Run all on before render runnables
        List<ViewManagement.Entity.Container<?, ?, ?>> onBeforeRenderContainers = new ArrayList<>();
        findOnBeforeRendersToExecuteInMaster(plugin.getMaster(), onBeforeRenderContainers);
        for (int i = onBeforeRenderContainers.size() - 1; i >= 0; i --) {
            ViewManagement.Entity.Container<?, ?, ?> container = onBeforeRenderContainers.get(i);
            for (Runnable runnable : container.onnBeforeRenderRunnables()) {
                runnable.run();
            }
        }

        // Find what's to be rendered
        List<JComponent> toBeRendered = new ArrayList<>();
        findComponentsToBeRendered(toBeRendered);

        // Do the actual render process
        List<JComponent> rendered = new ArrayList<>();
        renderMaster(rendered, toBeRendered, plugin.getMaster());

        // Remove components that arent rendered.
        int renderedComponentCount = rendered.size();
        while (getComponentCount() > renderedComponentCount) {
            remove(renderedComponentCount);
        }

        // Set the z order of all components
        for (int i = 0; i < getComponentCount(); i ++) {
            Component c = getComponent(i);
            if (c == null) continue;
            setComponentZOrder(c, i);
        }

        // Repaint the panel
        revalidate();
        repaint();
    }

    /**
     * Reloads all repositories in the plugin's master entity.
     */
    public void reloadRepositories() {
        reloadedMasters.clear();
        reloadRepositoriesInMaster(getPlugin().getMaster());
        reloadedMasters.clear();
    }

    /**
     * Recursively reloads repositories in the specified master entity.
     *
     * @param master the master entity from which to reload repositories
     */
    private void findOnBeforeRendersToExecuteInMaster(ViewManagement.Entity.Master<?, ?> master, List<ViewManagement.Entity.Container<?, ?, ?>> containers) {
        if (reloadedMasters.contains(master)) return;
        File file = new File(master.getRepository().generatePath() + ".json");
        if (file.lastModified() != master.getRepository().lastModified()) {
            master.getRepository().initialize(); // Initialize the repository associated with the master entity.
        }
        reloadedMasters.add(master); // Keep track of reloaded masters
        for (ViewManagement.Entity.Container<?, ?, ?> container : master.getContainers()) {
            findOnBeforeRendersToExecuteInContainer(container, containers); // Reload repositories in each container of the master.
        }
        for (ViewManagement.Entity.View<?, ?, ?> view : master.getViews()) {
            for (ViewManagement.Entity.Container<?, ?, ?> container : view.getContainers()) {
                findOnBeforeRendersToExecuteInContainer(container, containers); // Reload repositories in each container of the view.
            }
        }
    }

    /**
     * Reloads repositories in the specified container.
     *
     * @param container the container from which to reload repositories
     */
    private void findOnBeforeRendersToExecuteInContainer(ViewManagement.Entity.Container<?, ?, ?> container, List<ViewManagement.Entity.Container<?, ?, ?>> containers) {
        containers.add(container);
        for (ViewManagement.Entity.Element element : container.getElements()) {
            if (element instanceof ViewManagement.Entity.Master) {
                findOnBeforeRendersToExecuteInMaster((ViewManagement.Entity.Master<?, ?>) element, containers); // Recursively reload repositories if the element is a master.
            }
            else if (element instanceof ViewManagement.Entity.Container) {
                findOnBeforeRendersToExecuteInContainer((ViewManagement.Entity.Container<?, ?, ?>) element, containers);
            }
        }
    }

    /**
     * Recursively reloads repositories in the specified master entity.
     *
     * @param master the master entity from which to reload repositories
     */
    private void reloadRepositoriesInMaster(ViewManagement.Entity.Master<?, ?> master) {
        if (reloadedMasters.contains(master)) return;
        File file = new File(master.getRepository().generatePath() + ".json");
        if (file.lastModified() != master.getRepository().lastModified()) {
            master.getRepository().initialize(); // Initialize the repository associated with the master entity.
        }
        reloadedMasters.add(master); // Keep track of reloaded masters
        for (ViewManagement.Entity.Container<?, ?, ?> container : master.getContainers()) {
            reloadRepositoriesInContainer(container); // Reload repositories in each container of the master.
        }
        for (ViewManagement.Entity.View<?, ?, ?> view : master.getViews()) {
            for (ViewManagement.Entity.Container<?, ?, ?> container : view.getContainers()) {
                reloadRepositoriesInContainer(container); // Reload repositories in each container of the view.
            }
        }
    }

    /**
     * Reloads repositories in the specified container.
     *
     * @param container the container from which to reload repositories
     */
    private void reloadRepositoriesInContainer(ViewManagement.Entity.Container<?, ?, ?> container) {
        for (ViewManagement.Entity.Element element : container.getElements()) {
            if (element instanceof ViewManagement.Entity.Master) {
                reloadRepositoriesInMaster((ViewManagement.Entity.Master<?, ?>) element); // Recursively reload repositories if the element is a master.
            }
            else if (element instanceof ViewManagement.Entity.Container) {
                reloadRepositoriesInContainer((ViewManagement.Entity.Container<?, ?, ?>) element);
            }
        }
    }

    /**
     * Finds components that need to be rendered.
     *
     * @param toBeRendered the list to populate with components to be rendered
     */
    public void findComponentsToBeRendered(List<JComponent> toBeRendered) {
        findComponentsToBeRenderedInMaster(toBeRendered, getPlugin().getMaster());
    }

    /**
     * Recursively finds components to be rendered in a master.
     *
     * @param toBeRendered the list to populate with components to be rendered
     * @param master the master entity to search within
     */
    private void findComponentsToBeRenderedInMaster(List<JComponent> toBeRendered, ViewManagement.Entity.Master<?, ?> master) {
        if (master == null) return;

        ViewManagement.Entity.View<?, ?, ?> view = master.getActiveView();
        masterHierarchy.add(master);

        for (ViewManagement.Entity.Container<?, ?, ?> container : master.getContainers()) {
            findComponentsToBeRenderedInContainer(toBeRendered, container);
        }
        if (view != null) {
            for (ViewManagement.Entity.Container<?, ?, ?> container : view.getContainers()) {
                findComponentsToBeRenderedInContainer(toBeRendered, container);
            }
        }
    }

    /**
     * Finds components to be rendered within a specific container.
     *
     * @param toBeRendered the list to populate with components to be rendered
     * @param container the container entity to search within
     */
    private void findComponentsToBeRenderedInContainer(List<JComponent> toBeRendered, ViewManagement.Entity.Container<?, ?, ?> container) {
        if (!container.isVisible()) return;
        for (ViewManagement.Entity.Element element : container.getElements()) {
            if (element instanceof ViewManagement.Entity.Master) {
                findComponentsToBeRenderedInMaster(toBeRendered, (ViewManagement.Entity.Master<?, ?>) element);
            } else if (element instanceof ViewManagement.Entity.Component) {
                ViewManagement.Entity.Component<?, ?, ?> component = (ViewManagement.Entity.Component<?, ?, ?>) element;
                toBeRendered.add(component.getComponent());
            } else if (element instanceof ViewManagement.Entity.Container) {
                findComponentsToBeRenderedInContainer(toBeRendered, (ViewManagement.Entity.Container<?, ?, ?>) element);
            }
        }
    }

    /**
     * Renders the specified master and its components.
     *
     * @param rendered the list of already rendered components
     * @param toBeRendered the list of components to render
     * @param master the master entity to render
     */
    private void renderMaster(List<JComponent> rendered, List<JComponent> toBeRendered, ViewManagement.Entity.Master<?, ?> master) {
        ViewManagement.Entity.View<?, ?, ?> view = master.getActiveView(); 

        for (ViewManagement.Entity.Container<?, ?, ?> container : master.getContainers()) {
            renderContainer(rendered, toBeRendered, container);
        }
        for (ViewManagement.Entity.View<?, ?, ?> otherView : master.getViews()) {
            if (otherView == view) {
                renderView(rendered, toBeRendered, view);
            }
        }
    }

    /**
     * Renders the specified view and its components.
     *
     * @param rendered the list of already rendered components
     * @param toBeRendered the list of components to render
     * @param view the view entity to render
     */
    private void renderView(List<JComponent> rendered, List<JComponent> toBeRendered, ViewManagement.Entity.View<?, ?, ?> view) {
        for (ViewManagement.Entity.Container<?, ?, ?> container : view.getContainers()) {
            renderContainer(rendered, toBeRendered, container);
        }
    }

    /**
     * Renders the specified container and its components.
     *
     * @param rendered the list of already rendered components
     * @param toBeRendered the list of components to render
     * @param container the container entity to render
     */
    private void renderContainer(List<JComponent> rendered, List<JComponent> toBeRendered, ViewManagement.Entity.Container<?, ?, ?> container) {
        if (!container.isVisible()) return;
        for (ViewManagement.Entity.Element element : container.getElements()) {
            if (element instanceof ViewManagement.Entity.Master) {
                ViewManagement.Entity.Master<?, ?> m = (ViewManagement.Entity.Master<?, ?>) element;
                this.renderMaster(rendered, toBeRendered, m);
            } else if (element instanceof ViewManagement.Entity.Component) {
                ViewManagement.Entity.Component<?, ?, ?> component = (ViewManagement.Entity.Component<?, ?, ?>) element;
                this.renderComponent(rendered, toBeRendered, component);
            } else if (element instanceof ViewManagement.Entity.Container) {
                this.renderContainer(rendered, toBeRendered, (ViewManagement.Entity.Container<?, ?, ?>) element);
            }
        }
    }

    /**
     * Renders a specific component, updating its state and adding it to the rendered list.
     *
     * @param rendered a list of already rendered components
     * @param toBeRendered the list of components to render
     * @param component the component entity to render
     */
    private void renderComponent(List<JComponent> rendered, List<JComponent> toBeRendered, ViewManagement.Entity.Component<?, ?, ?> component) {
        // Execute any update runnables associated with the component
        for (Runnable runnable : component.getOnUpdateRunnables()) {
            runnable.run();
        }

        JComponent jComponent = component.getComponent();
        boolean found = false;

        // A list of components that should not be rendered
        List<JComponent> dontRender = new ArrayList<>();

        // Loop through components that have not been marked as rendered yet
        for (int i = rendered.size(); i < getComponentCount(); i++) {
            JComponent jComponentAtIndex = (JComponent) getComponent(i);
            if (jComponent == getComponent(i)) {
                jComponent.revalidate();
                jComponent.repaint();
                found = true;
                break;
            }
            if (!rendered.contains(jComponentAtIndex) && !toBeRendered.contains(jComponentAtIndex)) {
                // Add previously rendered components that shouldn't be rendered again
                dontRender.add(jComponentAtIndex);
            }
        }

        // Remove components that shouldn't be rendered
        for (int i = 0; i < dontRender.size(); i++) {
            for (int j = rendered.size(); j < getComponentCount(); j++) {
                JComponent jComponentAtIndex = (JComponent) getComponent(j);
                if (dontRender.contains(jComponentAtIndex)) {
                    remove(jComponentAtIndex);
                }
            }
        }

        // Add the component if not updated
        if (!found) {
            if (rendered.size() < getComponentCount()) {
                add(jComponent, rendered.size());
            } else {
                add(jComponent);
            }
        } else {
            if (rendered.size() < getComponentCount()) {
                if (getComponent(rendered.size()) != jComponent) {
                    remove(jComponent);
                }
                add(jComponent, rendered.size());
            } else {
                remove(jComponent);
                add(jComponent);
            }
        }
        
        // Mark the component as rendered
        rendered.add(jComponent);
    }
}
