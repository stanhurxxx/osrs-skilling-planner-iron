package io.hurx.models.views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

/**
 * Represents a view in the Java Swing application, allowing for a hierarchy of views.
 *
 * @param <TMaster> The type of the master view that manages this view.
 * @param <TViewEnum> The type of the view enum associated with this view.
 * @param <TRepository> The type of the repository associated with this view.
 */
public abstract class View<TMaster extends View.Master<TMaster, TViewEnum, TRepository>, TViewEnum extends Views, TRepository extends Repository<?>> {
    
    /** The master view that manages this view. */
    protected final TMaster master;

    /** The root panel of the view hierarchy. */
    protected final Plugin root;

    /** The list of elements contained in this view. */
    private final List<Container<TMaster, View<TMaster, TViewEnum, TRepository>, ?, TViewEnum, TRepository>> containers = new ArrayList<>();

    /** The associated view enum value. */
    private TViewEnum view;

    /**
     * Constructs a View with the specified master and view.
     *
     * @param master the master managing this view.
     * @param view the view enum value.
     */
    public View(TMaster master, TViewEnum view) {
        this.master = master;
        this.root = master.getRoot();
        this.view = view;
    }

    /**
     * Retrieves the master view that manages this view.
     *
     * @return the master view.
     */
    public TMaster getMaster() {
        return master;
    }

    /**
     * Retrieves the root panel of the view hierarchy.
     *
     * @return the root panel.
     */
    public Plugin getRoot() {
        return root;
    }

    /**
     * Retrieves the list of elements contained in this view.
     *
     * @return a list of elements.
     */
    public List<Container<TMaster, View<TMaster, TViewEnum, TRepository>, ?, TViewEnum, TRepository>> getContainers() {
        return containers;
    }

    /**
     * Retrieves the associated view enum value.
     *
     * @return the view enum value.
     */
    public TViewEnum getView() {
        return view;
    }

    /**
     * Adds one or more containers to this view.
     * <p>
     * The containers are appended to the list of elements contained in this view.
     * This method does not perform any validation on the containers being added.
     * </p>
     *
     * @param containers one or more containers to be added to this view.
     */
    public void add(Container<?, ?, ?, ?, ?> ...containers) {
        // TODO: Implementation goes here
    }

    /**
     * Inserts one or more containers at the specified index in this view.
     * <p>
     * The containers will be added starting from the given index, shifting any existing
     * containers to the right. If the index is out of bounds, an exception may be thrown
     * or the containers may not be added.
     * </p>
     *
     * @param index the index at which to insert the containers.
     * @param containers one or more containers to be inserted.
     */
    public void insert(int index, Container<?, ?, ?, ?, ?> ...containers) {
        // TODO: Implementation goes here
    }

    /**
     * Removes one or more specified containers from this view.
     * <p>
     * This method searches for the provided containers and removes them from the list
     * of elements contained in this view. If a container is not found, it will not be
     * removed, and no exception will be thrown.
     * </p>
     *
     * @param containers one or more containers to be removed from this view.
     */
    public void remove(Container<?, ?, ?, ?, ?> ...containers) {
        // TODO: Implementation goes here
    }

    /**
     * Removes containers at the specified indices from this view.
     * <p>
     * The method takes a list of indices and removes the corresponding containers
     * from the list of elements. If any index is out of bounds, it may throw an exception.
     * </p>
     *
     * @param indices one or more indices of containers to be removed.
     */
    public void remove(int ...indices) {
        // TODO: Implementation goes here
    }

    /**
     * Clears all containers from this view.
     * <p>
     * This method removes all elements from the list of containers, effectively
     * resetting the view to its initial state. No exceptions will be thrown,
     * and the operation will be performed silently.
     * </p>
     */
    public void clear() {
        // TODO: Implementation goes here
    }

    /**
     * Base class for elements within a view.
     */
    public static class Element {
        
        /** The root panel of the view hierarchy. */
        protected final Plugin root;

        /**
         * Constructs an Element with the specified root.
         *
         * @param root the root panel for this element.
         */
        public Element(Plugin root) {
            this.root = root;
        }

        /**
         * Retrieves the root of the view hierarchy.
         *
         * @return the root panel.
         */
        public Plugin getRoot() {
            return root;
        }
    }

    /**
     * Represents a master view that manages a collection of views.
     *
     * @param <TMaster> the type of master.
     * @param <TView> the type of view managed by this master.
     * @param <TRepository> the type of repository associated with this master.
     */
    public static class Master<TMaster extends Master<TMaster, TView, TRepository>, TView extends Views, TRepository extends Repository<?>> extends Element {
        
        /** The cache property containing the currently active view. */
        protected Repository.Property<TView> cacheProperty;

        /** Listener for changes in the cache property. */
        protected final Repository.Property.Listener<TView> cachePropertyListener;

        /**
         * Retrieves the repository associated with this master.
         *
         * @return the repository of type {@link TRepository} associated with this master.
         */
        public TRepository getRepository() {
            return repository;
        }

        /** The repository associated with this master view. */
        protected final TRepository repository;

        /** The list of views managed by this master. */
        private final List<View<TMaster, TView, TRepository>> views = new ArrayList<>();

        /**
         * Adds one or more views to this master view.
         * <p>
         * The views are appended to the list of views managed by this master.
         * This method does not perform any validation on the views being added.
         * </p>
         *
         * @param views one or more views to be added to this master view.
         */
        public void add(@SuppressWarnings("unchecked") View<TMaster, TView, TRepository> ...views) {
            // TODO: Implementation goes here
        }

        /**
         * Inserts one or more views at the specified index in this master view.
         * <p>
         * The views will be added starting from the given index, shifting any existing
         * views to the right. If the index is out of bounds, an exception may be thrown
         * or the views may not be added.
         * </p>
         *
         * @param index the index at which to insert the views.
         * @param views one or more views to be inserted.
         */
        public void insert(int index, @SuppressWarnings("unchecked") View<TMaster, TView, TRepository> ...views) {
            // TODO: Implementation goes here
        }

        /**
         * Removes one or more specified views from this master view.
         * <p>
         * This method searches for the provided views and removes them from the list
         * of views managed by this master. If a view is not found, it will not be
         * removed, and no exception will be thrown.
         * </p>
         *
         * @param views one or more views to be removed from this master view.
         */
        public void remove(@SuppressWarnings("unchecked") View<TMaster, TView, TRepository> ...views) {
            // TODO: Implementation goes here
        }

        /**
         * Removes views at the specified indices from this master view.
         * <p>
         * The method takes a list of indices and removes the corresponding views
         * from the list of managed views. If any index is out of bounds, it may throw an exception.
         * </p>
         *
         * @param indices one or more indices of views to be removed.
         */
        public void remove(int ...indices) {
            // TODO: Implementation goes here
        }

        /**
         * Clears all views from this master view.
         * <p>
         * This method removes all views from the list of managed views, effectively
         * resetting the master view to its initial state. No exceptions will be thrown,
         * and the operation will be performed silently.
         * </p>
         */
        public void clear() {
            // TODO: Implementation goes here
        }

        /**
         * Constructs a Master with the specified root panel and repository.
         *
         * @param root the root panel.
         * @param cacheProperty the property that stores the currently active view.
         * @param repository the repository associated with this master.
         * @param views an array of views managed by this master.
         */
        public Master(Plugin root, Repository.Property<TView> cacheProperty, TRepository repository, Views[] views) {
            super(root);
            this.cacheProperty = cacheProperty;
            this.repository = repository;
            this.cachePropertyListener = new Repository.Property.Listener<TView>() {
                @Override
                public void onSet(TView oldValue, TView newValue) {
                    navigate(newValue);
                }
            };
            this.cacheProperty.listen(cachePropertyListener);
        }

        /**
         * Retrieves the currently active view managed by this master.
         *
         * @return the active view, or null if none is active.
         */
        public final View<TMaster, TView, TRepository> getActiveView() {
            for (View<TMaster, TView, TRepository> view : views) {
                if (view.getView() == view.getMaster().cacheProperty.get()) {
                    return view;
                }
            }
            return null;
        }

        /**
         * Retrieves all views managed by this master.
         *
         * @return a list of views.
         */
        public List<View<TMaster, TView, TRepository>> getViews() {
            return views;
        }

        /**
         * Navigates to a specified view and updates the cache property.
         *
         * @param to the view to navigate to.
         */
        public void navigate(TView to) {
            this.cacheProperty.set(to);
            try {
                this.repository.save();
                this.update(this);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        /**
         * Updates the view container when changes are made.
         *
         * @param element the element that triggered the update.
         */
        public void update(Element element) {
            // TODO: Implement update logic.
            Master<?, ?, ?> master = this.getRoot().getMaster();
            int index = 0;
            for (View<?, ?, ?> view : master.getViews()) {
                for (Element viewElement : view.getContainers()) {
                    if (viewElement == element) {
                        // TODO: Start updating this element.
                    }
                    index++;
                }
            }
        }

        /**
         * Creates a combo box for selecting views managed by this master.
         *
         * @return a JComboBox populated with the views.
         */
        public JComboBox<?> createViewsComboBox() {
            JComboBox<View<TMaster, TView, TRepository>> cb = new JComboBox<>();
            for (View<TMaster, TView, TRepository> view : views) {
                if (view.getView().getName() == null) continue;
                cb.addItem(view);
            }
            cb.setSelectedItem(getActiveView());
            cb.setRenderer(new ListCellRenderer<View<TMaster, TView, TRepository>>() {
                @Override
                public JComponent getListCellRendererComponent(
                    JList<? extends View<TMaster, TView, TRepository>> list,
                    View<TMaster, TView, TRepository> value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                    if (value.getView().getName() == null) return null;

                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setText(value.getView().getName());
                    if (value.getView().getIconPath() != null) {
                        label.setIcon(Resources.loadImageIcon(value.getView().getIconPath().getPath(), 18, 18));
                    }

                    // Set label color based on selection state
                    if (value == getActiveView()) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                        label.setForeground(Color.white);
                    } else if (isSelected) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        label.setForeground(Color.white);
                    } else {
                        label.setBackground(Theme.TABLE_BG_COLOR);
                        label.setForeground(Color.white);
                    }

                    return label;
                }
            });
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    @SuppressWarnings("unchecked")
                    View<TMaster, TView, TRepository> selectedOption = (View<TMaster, TView, TRepository>) cb.getSelectedItem();

                    if (selectedOption == getActiveView()) {
                        return;
                    }

                    navigate(selectedOption.getView());
                }
            });
            return cb;
        }
    }

    /**
     * Represents a container for views and components.
     *
     * @param <TMaster> the type of master.
     * @param <TView> the type of view managed by this container.
     * @param <TContainer> the type of this container.
     * @param <TViewEnum> the type of the view enum.
     * @param <TRepository> the type of repository associated with this container.
     */
    public static class Container<TMaster extends Master<TMaster, TViewEnum, TRepository>, TView extends View<TMaster, TViewEnum, TRepository>, TContainer extends Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum extends Views, TRepository extends Repository<?>> extends Element {
        
        /** The list of components contained within this container. */
        private final List<Component<?, TMaster, TView, Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum, TRepository>> components = new ArrayList<>();

        /**
         * Retrieves the view associated with this container.
         *
         * This method returns the view that this container manages, allowing access to
         * the structure and behavior defined in the view. The view is responsible for
         * presenting data and handling user interactions, and this method provides
         * a way for components within the container to reference the view they belong to.
         *
         * @return the view associated with this container.
         */
        public TView getView() {
            return view;
        }

        /** The view managed by this container. */
        private final TView view;

        /**
         * Constructs a Container with the specified view.
         *
         * @param view the view managed by this container.
         */
        public Container(TView view) {
            super(view.getRoot());
            this.view = view;
        }

        /**
         * Retrieves all components in this container.
         *
         * @return a list of components.
         */
        public List<Component<?, TMaster, TView, Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum, TRepository>> getComponents() {
            return components;
        }

        /**
         * Adds one or more components to this container.
         * <p>
         * The components are appended to the list of components contained within this container.
         * This method does not perform any validation on the components being added.
         * </p>
         *
         * @param components one or more components to be added to this container.
         */
        public void add(@SuppressWarnings("unchecked") Component<?, TMaster, TView, Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum, TRepository> ...components) {
            // TODO: Implementation goes here
        }

        /**
         * Inserts one or more components at the specified index in this container.
         * <p>
         * The components will be added starting from the given index, shifting any existing
         * components to the right. If the index is out of bounds, an exception may be thrown
         * or the components may not be added.
         * </p>
         *
         * @param index the index at which to insert the components.
         * @param components one or more components to be inserted.
         */
        public void insert(int index, @SuppressWarnings("unchecked") Component<?, TMaster, TView, Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum, TRepository> ...components) {
            // TODO: Implementation goes here
        }

        /**
         * Removes one or more specified components from this container.
         * <p>
         * This method searches for the provided components and removes them from the list
         * of components contained within this container. If a component is not found, it will not be
         * removed, and no exception will be thrown.
         * </p>
         *
         * @param components one or more components to be removed from this container.
         */
        public void remove(@SuppressWarnings("unchecked") Component<?, TMaster, TView, Container<TMaster, TView, TContainer, TViewEnum, TRepository>, TViewEnum, TRepository> ...components) {
            // TODO: Implementation goes here
        }

        /**
         * Removes components at the specified indices from this container.
         * <p>
         * The method takes a list of indices and removes the corresponding components
         * from the list of contained components. If any index is out of bounds, it may throw an exception.
         * </p>
         *
         * @param indices one or more indices of components to be removed.
         */
        public void remove(int ...indices) {
            // TODO: Implementation goes here
        }

        /**
         * Clears all components from this container.
         * <p>
         * This method removes all components from the list of contained components, effectively
         * resetting the container to its initial state. No exceptions will be thrown,
         * and the operation will be performed silently.
         * </p>
         */
        public void clear() {
            // TODO: Implementation goes here
        }
    }

    /**
     * Represents a component that is part of a view in the Java Swing application.
     *
     * This class acts as a bridge between the Swing component and the view structure,
     * allowing the component to interact with its container and the view it belongs to.
     *
     * @param <TComponent> the type of the Swing component.
     * @param <TMaster> the type of the master view that manages this component.
     * @param <TView> the type of the view that this component is part of.
     * @param <TContainer> the type of the container that holds this component.
     * @param <TViewEnum> the enumeration of views associated with this component.
     * @param <TRepository> the type of the repository used for data management.
     */
    public static class Component<  TComponent extends JComponent,
                                    TMaster extends Master<TMaster, TViewEnum, TRepository>, 
                                    TView extends View<TMaster, TViewEnum, TRepository>, 
                                    TContainer extends Container<TMaster, TView, ?, TViewEnum, TRepository>, 
                                    TViewEnum extends Views,
                                    TRepository extends Repository<?>> extends Element {

        /** The Swing component associated with this view component. */
        private TComponent component;

        /** The view that this component is part of. */
        private final TView view;

        /** The container that holds this component. */
        private final TContainer container;

        /**
        * Retrieves the Swing component associated with this view component.
        *
        * @return the Swing component.
        */
        public TComponent getComponent() {
            return component;
        }

        /**
        * Sets the Swing component associated with this view component.
        *
        * @param component the Swing component to set.
        */
        public void setComponent(TComponent component) {
            this.component = component;
        }

        /**
        * Retrieves the view that this component is part of.
        *
        * @return the associated view.
        */
        public TView getView() {
            return view;
        }

        /**
        * Retrieves the container that holds this component.
        *
        * @return the associated container.
        */
        public TContainer getContainer() {
            return container;
        }

        /**
        * Constructs a Component with the specified container and Swing component.
        *
        * This constructor initializes the component, its container, and the view it belongs to,
        * setting up the necessary relationships for proper view management.
        *
        * @param container the container holding this component.
        * @param component the Swing component associated with this component.
        */
        public Component(TContainer container, TComponent component) {
            super(container.getRoot());
            this.component = component;
            this.container = container;
            this.view = this.container.getView();
        }
    }
}