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
 * @param <M> The type of the master view that manages this view.
 * @param <V> The type of the view enum associated with this view.
 * @param <R> The type of the repository associated with this view.
 */
public abstract class View<M extends View.Master<M, V, R>, V extends Views, R extends Repository<?>> {
    
    /** The master view that manages this view. */
    protected final M master;

    /** The root panel of the view hierarchy. */
    protected final Plugin root;

    /** The list of elements contained in this view. */
    private final List<Element> elements = new ArrayList<>();

    /** The associated view enum value. */
    private V view;

    /**
     * Constructs a View with the specified master and view.
     *
     * @param master the master managing this view.
     * @param view the view enum value.
     */
    public View(M master, V view) {
        this.master = master;
        this.root = master.getRoot();
        this.view = view;
    }

    /**
     * Retrieves the master view that manages this view.
     *
     * @return the master view.
     */
    public M getMaster() {
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
    public List<Element> getElements() {
        return elements;
    }

    /**
     * Retrieves the associated view enum value.
     *
     * @return the view enum value.
     */
    public V getView() {
        return view;
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
     * @param <M> the type of master.
     * @param <V> the type of view managed by this master.
     * @param <R> the type of repository associated with this master.
     */
    public static class Master<M extends Master<M, V, R>, V extends Views, R extends Repository<?>> extends Element {
        
        /** The cache property containing the currently active view. */
        protected Repository.Property<V> cacheProperty;

        /** Listener for changes in the cache property. */
        protected final Repository.Property.Listener<V> cachePropertyListener;

        /**
         * Retrieves the repository associated with this master.
         *
         * @return the repository of type {@link R} associated with this master.
         */
        public R getRepository() {
            return repository;
        }

        /** The repository associated with this master view. */
        protected final R repository;

        /** The list of views managed by this master. */
        private final List<View<M, V, R>> views = new ArrayList<>();

        /**
         * Constructs a Master with the specified root panel and repository.
         *
         * @param root the root panel.
         * @param cacheProperty the property that stores the currently active view.
         * @param repository the repository associated with this master.
         * @param views an array of views managed by this master.
         */
        public Master(Plugin root, Repository.Property<V> cacheProperty, R repository, Views[] views) {
            super(root);
            this.cacheProperty = cacheProperty;
            this.repository = repository;
            this.cachePropertyListener = new Repository.Property.Listener<V>() {
                @Override
                public void onSet(V oldValue, V newValue) {
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
        public final View<M, V, R> getActiveView() {
            for (View<M, V, R> view : views) {
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
        public List<View<M, V, R>> getViews() {
            return views;
        }

        /**
         * Navigates to a specified view and updates the cache property.
         *
         * @param to the view to navigate to.
         */
        public void navigate(V to) {
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
                for (Element viewElement : view.getElements()) {
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
            JComboBox<View<M, V, R>> cb = new JComboBox<>();
            for (View<M, V, R> view : views) {
                if (view.getView().getName() == null) continue;
                cb.addItem(view);
            }
            cb.setSelectedItem(getActiveView());
            cb.setRenderer(new ListCellRenderer<View<M, V, R>>() {
                @Override
                public JComponent getListCellRendererComponent(
                    JList<? extends View<M, V, R>> list,
                    View<M, V, R> value,
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
                    View<M, V, R> selectedOption = (View<M, V, R>) cb.getSelectedItem();

                    if (selectedOption == getActiveView()) {
                        return;
                    }

                    navigate(selectedOption.getView());
                }
            });
            return cb;
        }

        // The remaining methods related to adding, removing, and clearing views would follow a similar pattern.
    }

    /**
     * Represents a container for views and components.
     *
     * @param <M> the type of master.
     * @param <V> the type of view managed by this container.
     * @param <C> the type of this container.
     * @param <VV> the type of the view enum.
     * @param <R> the type of repository associated with this container.
     */
    public static class Container<M extends Master<M, VV, R>, V extends View<M, VV, R>, C extends Container<M, V, C, VV, R>, VV extends Views, R extends Repository<?>> extends Element {
        
        /** The list of components contained within this container. */
        private final List<Component<?, M, V, C, VV, R>> components = new ArrayList<>();

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
        public V getView() {
            return view;
        }

        /** The view managed by this container. */
        private final V view;

        /**
         * Constructs a Container with the specified view.
         *
         * @param view the view managed by this container.
         */
        public Container(V view) {
            super(view.getRoot());
            this.view = view;
        }

        /**
         * Retrieves all components in this container.
         *
         * @return a list of components.
         */
        public List<Component<?, M, V, C, VV, R>> getComponents() {
            return components;
        }
    }

    /**
     * Represents a component that is part of a view in the Java Swing application.
     *
     * This class acts as a bridge between the Swing component and the view structure,
     * allowing the component to interact with its container and the view it belongs to.
     *
     * @param <T> the type of the Swing component.
     * @param <M> the type of the master view that manages this component.
     * @param <V> the type of the view that this component is part of.
     * @param <C> the type of the container that holds this component.
     * @param <VV> the enumeration of views associated with this component.
     * @param <R> the type of the repository used for data management.
     */
    public static class Component<  T extends JComponent,
                                    M extends Master<M, VV, R>, 
                                    V extends View<M, VV, R>, 
                                    C extends Container<M, V, C, VV, R>, 
                                    VV extends Views,
                                    R extends Repository<?>> extends Element {

        /** The Swing component associated with this view component. */
        private T component;

        /** The view that this component is part of. */
        private final V view;

        /** The container that holds this component. */
        private final C container;

        /**
        * Retrieves the Swing component associated with this view component.
        *
        * @return the Swing component.
        */
        public T getComponent() {
            return component;
        }

        /**
        * Sets the Swing component associated with this view component.
        *
        * @param component the Swing component to set.
        */
        public void setComponent(T component) {
            this.component = component;
        }

        /**
        * Retrieves the view that this component is part of.
        *
        * @return the associated view.
        */
        public V getView() {
            return view;
        }

        /**
        * Retrieves the container that holds this component.
        *
        * @return the associated container.
        */
        public C getContainer() {
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
        public Component(C container, T component) {
            super(container.getRoot());
            this.component = component;
            this.container = container;
            this.view = this.container.getView();
        }
    }
}