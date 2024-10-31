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
 */
public abstract class View<M extends View.Master<M, V, R>, V extends Views, R extends Repository<?>> {
    /**
     * Retrieves the view master that manages this view.
     *
     * @return the master view.
     */
    public M getMaster() {
        return master;
    }
    protected final M master;

    /**
     * Retrieves the root panel of the view hierarchy.
     *
     * @return the root panel.
     */
    public Plugin getRoot() {
        return root;
    }
    protected final Plugin root;

    /**
     * Retrieves the list of elements contained in this view.
     *
     * @return a list of elements.
     */
    public List<Element> getElements() {
        return elements;
    }
    private final List<Element> elements = new ArrayList<>();

    /**
     * Get the associated view enum value
     * @return
     */
    public V getView() {
        return view;
    }
    private V view;

    /**
     * Constructs a View with the specified master.
     *
     * @param master the master managing this view.
     * @param view the view enum value
     */
    public View(M master, V view) {
        this.master = master;
        this.root = master.getRoot();
        this.view = view;
    }

    /**
     * Base class for elements within a view.
     */
    public static class Element {
        /**
         * Retrieves the root of the view hierarchy.
         *
         * @return the root panel.
         */
        public Plugin getRoot() {
            return root;
        }
        protected final Plugin root;

        /**
         * Constructs an Element with the specified root.
         *
         * @param root the root panel for this element.
         */
        public Element(Plugin root) {
            this.root = root;
        }
    }

    /**
     * Represents a master view that manages a collection of views.
     *
     * @param <M> the type of master.
     */
    public static class Master<M extends Master<M, V, R>, V extends Views, R extends Repository<?>> extends Element {
        /**
         * Adds a view to the master.
         *
         * @param views the view to add.
         */
        @SafeVarargs
        public final Master<M, V, R> add(View<M, V, R> ...views) {
            // TODO: Implement add logic.
            return this;
        }

        /**
         * Inserts a view to the master.
         *
         * @param index the index to insert at
         * @param views the views to insert.
         */
        @SafeVarargs
        public final Master<M, V, R> insert(int index, View<M, V, R> ...views) {
            // TODO: Implement add logic.
            return this;
        }

        /**
         * Removes a specific view from the master.
         *
         * @param views the view to remove.
         */
        @SafeVarargs
        public final Master<M, V, R> remove(View<M, V, R> ...views) {
            // TODO: Implement remove logic.
            return this;
        }

        /**
         * Removes a view by its index.
         *
         * @param index the index of the view to remove.
         */
        @SafeVarargs
        public final Master<M, V, R> remove(int ...indices) {
            // TODO: Implement remove by index logic.
            return this;
        }

        /**
         * Clears all views from the master.
         */
        public Master<M, V, R> clear() {
            // TODO: Implement clear logic.
            return this;
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
        private final List<View<M, V, R>> views = new ArrayList<>();

        /**
         * The cache property containing the cache view.
         */
        protected Repository.Property<V> cacheProperty;

        protected final Repository.Property.Listener<Integer, V> cachePropertyListener;

        public R getRepository() {
            return repository;
        }
        protected final R repository;

        /**
         * Constructs a Master with the specified root panel.
         *
         * @param root the root panel.
         */
        public Master(Plugin root, Repository.Property<V> cacheProperty, R repository, Views[] views) {
            super(root);
            this.cacheProperty = cacheProperty;
            this.cachePropertyListener = new Repository.Property.Listener<Integer, V>() {
                @Override
                public void onSet(Integer key, V newValue, V oldValue) {
                    navigate(newValue);
                }
            };
            this.cacheProperty.listen(cachePropertyListener);
            this.repository = repository;
        }

        /**
         * Navigates to a view
         * @param to
         */
        public void navigate(V to) {
            this.cacheProperty.set(to);
            try {
                this.repository.save();
                this.update();
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        /**
         * Updates the view container
         */
        public void update() {
            // TODO:
        }

        /**
         * Creates a combo box for the views
         * @return
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

                    if (value == getActiveView()) {
                        label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                        label.setForeground(Color.white);
                    }
                    else if (isSelected) {
                        label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                        label.setForeground(Color.white);
                    }
                    else {
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
    }

    /**
     * Represents a container for views and components.
     *
     * @param <M> the type of master.
     * @param <V> the type of view managed by this container.
     * @param <C> the type of this container.
     */
    public static class Container<M extends Master<M, VV, R>, V extends View<M, VV, R>, C extends Container<M, V, C, VV, R>, VV extends Views, R extends Repository<?>> extends Element {
        /**
         * Adds a component to this container.
         *
         * @param component the component to add.
         */
        @SafeVarargs
        public final Container<M, V, C, VV, R> add(Component<?, M, V, C, VV, R> ...components) {
            // TODO: Implement add logic.
            return this;
        }

        /**
         * Removes a component from this container.
         *
         * @param component the component to remove.
         */
        @SafeVarargs
        public final Container<M, V, C, VV, R> remove(Component<?, M, V, C, VV, R> ...components) {
            // TODO: Implement remove logic.
            return this;
        }

        /**
         * Inserts one or multiple component from this container.
         *
         * @param component the component to remove.
         */
        @SafeVarargs
        public final Container<M, V, C, VV, R> insert(Component<?, M, V, C, VV, R> ...components) {
            // TODO: Implement remove logic.
            return this;
        }

        /**
         * Clears all components from this container.
         */
        public Container<M, V, C, VV, R> clear() {
            // TODO: Implement clear logic.
            return this;
        }

        /**
         * Removes a component by its index.
         *
         * @param index the index of the component to remove.
         */
        @SafeVarargs
        public final Container<M, V, C, VV, R> remove(int ...indices) {
            // TODO: Implement remove by index logic.
            return this;
        }

        /**
         * Retrieves all components in this container.
         *
         * @return a list of components.
         */
        public List<Component<?, M, V, C, VV, R>> getComponents() {
            return components;
        }
        private final List<Component<?, M, V, C, VV, R>> components = new ArrayList<>();

        /**
         * Retrieves the view managed by this container.
         *
         * @return the view.
         */
        public V getView() {
            return view;
        }
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
    }

    /**
     * Represents a component that is part of a view.
     *
     * @param <T> the type of the Swing component.
     * @param <M> the type of the master view.
     * @param <V> the type of the view.
     * @param <C> the type of the container.
     */
    public static class Component<T extends JComponent, M extends Master<M, VV, R>, V extends View<M, VV, R>, C extends Container<M, V, C, VV, R>, VV extends Views, R extends Repository<?>> extends Element {
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
        private T component;

        /**
         * Retrieves the view that this component is part of.
         *
         * @return the associated view.
         */
        public V getView() {
            return view;
        }
        private final V view;

        /**
         * Retrieves the container that holds this component.
         *
         * @return the associated container.
         */
        public C getContainer() {
            return container;
        }
        private final C container;

        /**
         * Constructs a Component with the specified container and Swing component.
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