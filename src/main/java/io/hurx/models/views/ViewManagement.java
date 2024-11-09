package io.hurx.models.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import io.hurx.components.comboBox.ComboBox;
import io.hurx.models.IconPaths;
import io.hurx.models.repository.Repository;
import io.hurx.models.repository.Repository.Property;
import io.hurx.models.views.ViewManagement.Entity.Master;
import io.hurx.models.views.ViewManagement.Entity.Master.OneToMany;
import io.hurx.plugin.PluginPanel;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code ViewManagement} class provides a comprehensive framework for managing GUI views,
 * repositories, and components in a modular, flexible way. This class defines the main
 * structures and components for handling a GUI application that uses multiple views and
 * repositories, supporting one-to-many relationships and custom view rendering with
 * color-coded selection and validation states.
 *
 * <p>Key features include:
 * <ul>
 *     <li>Managing views and repositories through a {@link Master} class hierarchy.</li>
 *     <li>Defining and updating GUI elements using {@link ComboBoxModel} for dynamic selection and display.</li>
 *     <li>Supporting flexible component structures, including {@link Container} and {@link Component} classes.</li>
 *     <li>Enabling one-to-many relationships between repositories via the {@link OneToMany} class.</li>
 *     <li>Modularizing view management to allow customization and scalability.</li>
 * </ul>
 *
 * <p>This class is designed for applications with complex data interactions and multi-view requirements,
 * providing a structured, reusable approach to GUI component management.
 *
 * @param <TRepository> the type representing the repository model
 * @param <TEnum>       the type representing the views enumeration
 */
public class ViewManagement {
    /**
     * The {@code Entity} class provides a foundational structure for defining various entities
     * within the {@code ViewManagement} framework. It serves as a base for creating objects 
     * that represent individual components or elements in the application.
     * 
     * <p>Within {@code Entity}, the nested abstract classes {@code Base} and {@code Element} 
     * offer common functionality and structure, which can be further extended to define specific
     * behaviors and characteristics of different entity types.
     */
    public static class Entity {

        /**
         * The {@code Base} class serves as the foundational abstract class for all entities
         * within the {@code Entity} class hierarchy. It provides access to the {@code PluginPanel} 
         * root, which represents the main container or environment in which the entities operate.
         * 
         * <p>Subclasses of {@code Base} can utilize this root to interact with the broader 
         * application context and manage their display or data management in a cohesive manner.
         */
        public static abstract class Base {
            
            /**
             * Returns the {@code PluginPanel} root associated with this entity.
             * 
             * <p>This root is intended to represent the primary application panel or 
             * component environment, allowing subclasses to interact with the visual or 
             * data framework.
             *
             * @return the {@code PluginPanel} root of this entity
             */
            public final PluginPanel getRoot() {
                return root;
            }

            /** 
             * The main container or environment in which this entity operates.
             */
            protected final PluginPanel root;

            /**
             * Constructs a new {@code Base} entity with the specified {@code PluginPanel} root.
             * 
             * @param root the {@code PluginPanel} root component associated with this entity
             */
            public Base(PluginPanel root) {
                this.root = root;
            }    
        }

        /**
         * The {@code Element} class is an abstract subclass of {@code Base}, providing a 
         * foundational structure for elements that will extend it. These elements are 
         * intended to represent individual components or visual items within the 
         * {@code PluginPanel} root.
         * 
         * <p>By extending {@code Base}, {@code Element} inherits access to the root 
         * {@code PluginPanel}, ensuring that all elements have a reference to their main
         * application context.
         */
        public static abstract class Element extends Base {

            /**
             * Constructs a new {@code Element} with the specified {@code PluginPanel} root.
             * 
             * <p>This constructor allows {@code Element} subclasses to initialize with a 
             * reference to the main application environment, which they can use to manage 
             * or display themselves within the broader framework.
             * 
             * @param root the {@code PluginPanel} root component associated with this element
             */
            public Element(PluginPanel root) {
                super(root);
            }
        }

        /**
         * The {@code Master} class is an extension of {@code Element} and acts as the main controller
         * for managing a collection of views and containers. It provides functionality to interact with
         * a repository, handle view changes, and manage complex parent-child relationships among entities.
         * 
         * <p>Each {@code Master} instance is associated with a repository and a set of view enumerations,
         * which it uses to manage the active view and respond to property changes.
         *
         * @param <TRepository> the type of the repository this {@code Master} is associated with
         * @param <TEnum> the enumeration representing different views within the {@code Master}
         */
        public static class Master<TRepository extends Repository<?>, TEnum extends Views> extends Element {
            /**
             * Checks whether this {@code Master} instance has been validated.
             *
             * @return {@code false} by default, as validation logic may be implemented in subclasses
             */
            public boolean isValidated() {
                return false;
            }
            
            /**
             * Retrieves the currently active view within this {@code Master}.
             *
             * @return the active {@code View} instance, or {@code null} if no view is active
             */
            public View<? extends Master<TRepository, TEnum>, TRepository, TEnum> getActiveView() {
                for (OneToMany oneToMany : oneToManyRelations) {
                    String uuid = oneToMany.getUuidProperty().get();
                    if (uuid != null) {
                        return oneToMany.getView();
                    }
                }
                for (View<Master<TRepository, TEnum>, TRepository, TEnum> view : views) {
                    if (view.getView() == viewProperty.get()) {
                        return view;
                    }
                }
                return null;
            }
        
            /**
             * Retrieves the repository associated with this {@code Master}.
             *
             * @return the {@code TRepository} instance associated with this {@code Master}
             */
            public final TRepository getRepository() {
                return repository;
            }
            private TRepository repository;

            /**
             * Retrieves the list of containers managed by this {@code Master}.
             *
             * @return a list of {@code Container} instances associated with this {@code Master}
             */
            public final List<Container<Master<TRepository, TEnum>, TRepository, TEnum>> getContainers() {
                return containers;
            }
            private final List<Container<Master<TRepository, TEnum>, TRepository, TEnum>> containers = new ArrayList<>();
            
            /**
             * Retrieves the list of views managed by this {@code Master}.
             *
             * @return a list of {@code View} instances associated with this {@code Master}
             */
            public final List<View<Master<TRepository, TEnum>, TRepository, TEnum>> getViews() {
                return views;
            }
            private final List<View<Master<TRepository, TEnum>, TRepository, TEnum>> views = new ArrayList<>();

            /**
             * Retrieves the enumeration array representing available views for this {@code Master}.
             *
             * @return an array of {@code TEnum} representing the available views
             */
            public final TEnum[] getViewsEnum() {
                return viewsEnum;
            }
            private final TEnum[] viewsEnum;

            /**
             * Retrieves the property representing the currently selected view.
             *
             * @return the {@code Repository.Property} representing the current view
             */
            public final Repository.Property<TEnum> getViewProperty() {
                return viewProperty;
            }
            private final Repository.Property<TEnum> viewProperty;

            /**
             * Retrieves the list of {@code Runnable} actions triggered on view change.
             *
             * @return a list of {@code Runnable} instances for handling view change events
             */
            public final List<Runnable> getOnChangeViewRunnables() {
                return onChangeViewRunnables;
            }
            private final List<Runnable> onChangeViewRunnables = new ArrayList<>();

            /**
             * Retrieves the list of {@code Runnable} actions triggered on selection changes in
             * one-to-many relationships.
             *
             * @return a list of {@code Runnable} instances for handling selection changes
             */
            public final List<Runnable> getOnOneToManySelectionChangesRunnables() {
                return onOneToManySelectionChangesRunnables;
            }
            private final List<Runnable> onOneToManySelectionChangesRunnables = new ArrayList<>();

            /** GET The runnables to run on the onReady event */
            public final List<Runnable> onReadyRunnables() {
                return onReadyRunnables;
            }
            /** The runnables to run on the onReady event */
            private final List<Runnable> onReadyRunnables = new ArrayList<>();

            /**
             * A list of one to many relations, for adding multiple combo boxes.
             */
            private List<OneToMany> oneToManyRelations = new ArrayList<>(); 

            /**
             * Constructs a new {@code Master} instance with the specified parameters.
             *
             * @param root the root {@code PluginPanel} for this {@code Master}
             * @param repository the repository instance this {@code Master} is associated with
             * @param viewsEnum the enumerations representing available views
             * @param viewProperty the property representing the active view
             */
            public Master(PluginPanel root, TRepository repository, TEnum[] viewsEnum, Repository.Property<TEnum> viewProperty) {
                super(root);
                this.repository = repository;
                this.viewsEnum = viewsEnum;
                this.viewProperty = viewProperty;
            }

            /**
             * Finds and returns the deepest nested {@code Master} in the hierarchy.
             *
             * @return the deepest {@code Master} instance found
             */
            public Master<?, ?> findDeepestMaster() {
                List<Master<?, ?>> masters = new ArrayList<>();
                findDeepestMasterInMaster(this, masters);
                return masters.get(masters.size() - 1);
            }

            /**
             * Finds the deepest master in a master
             * @param master the master
             * @param traverse the traversed list
             */
            private void findDeepestMasterInMaster(Master<?, ?> master, List<Master<?, ?>> traverse) {
                traverse.add(master);
                for (Container<?, ?, ?> container : master.getContainers()) {
                    findDeepestMasterInContainer(container, traverse);
                }
                for (View<?, ?, ?> view : master.getViews()) {
                    findDeepestMasterInView(view, traverse);
                }
            }

            /**
             * Finds the deepest master in a view
             * @param master the master
             * @param traverse the traversed list
             */
            private void findDeepestMasterInView(View<?, ?, ?> view, List<Master<?, ?>> traverse) {
                for (Container<?, ?, ?> container : view.getContainers()) {
                    findDeepestMasterInContainer(container, traverse);
                }
            }

            /**
             * Finds the deepest master in a container
             * @param master the master
             * @param traverse the traversed list
             */
            private void findDeepestMasterInContainer(Container<?, ?, ?> container, List<Master<?, ?>> traverse) {
                for (Element element : container.getElements()) {
                    if (element instanceof Master) {
                        findDeepestMasterInMaster((Master<?, ?>) element, traverse);
                    }
                }
            }
            
            /**
             * Finds the parent master, if any.
             * @return
             */
            public Master<?, ?> findParent() {
                Master<?, ?> lastMaster = null;
                List<Master<?, ?>> traverse = new ArrayList<>();
                findParentInMaster(root.getPlugin().getMaster(), traverse);
                if (traverse.size() > 1) {
                    return traverse.get(traverse.size() - 1);
                }
                return lastMaster;
            }

            /**
             * Finds the parent in a master
             * @param master the master
             * @param traverse the traversed list
             */
            private void findParentInMaster(Master<?, ?> master, List<Master<?, ?>> traverse) {
                if (master != this) {
                    traverse.add(master);
                }
                for (Container<?, ?, ?> container : root.getPlugin().getMaster().getContainers()) {
                    findParentInContainer(container, traverse);
                }
                View<?, ?, ?> activeView = master.getActiveView();
                if (activeView == null) return;
                findParentInView(activeView, traverse);
            }

            /**
             * Finds a parent in a view
             * @param view the view
             * @param traverse the traversed list
             */
            private void findParentInView(View<?, ?, ?> view, List<Master<?, ?>> traverse) {
                for (Container<?, ?, ?> container : view.getContainers()) {
                    findParentInContainer(container, traverse);
                }
            }

            /**
             * Finds a parent in a container
             * @param container the container
             * @param traverse the traversed list
             */
            private void findParentInContainer(Container<?, ?, ?> container, List<Master<?, ?>> traverse) {
                for (Element element : container.getElements()) {
                    if (element instanceof Master) {
                        findParentInMaster((Master<?, ?>) element, traverse);
                    }
                }
            }

            /**
             * Adds the specified containers to this {@code Master}.
             *
             * @param containers the containers to add
             */
            @SuppressWarnings("unchecked")
            @SafeVarargs
            public final void addContainer(Container<?, TRepository, TEnum> ...containers) {
                for (Container<?, TRepository, TEnum> container : containers) {
                    this.containers.add((Container<Master<TRepository, TEnum>, TRepository, TEnum>)container);
                }
            }

            /**
             * Adds the specified views to this {@code Master}.
             *
             * @param views the views to add
             */
            @SuppressWarnings("unchecked")
            @SafeVarargs
            public final void addView(View<?, TRepository, TEnum> ...views) {
                for (View<?, TRepository, TEnum> view : views) {
                    this.views.add((View<Master<TRepository, TEnum>, TRepository, TEnum>)view);
                }
            }

            /**
             * Inserts one or multiple containers at a given index
             * @param index the index
             * @param containers the containers
             */
            @SuppressWarnings("unchecked")
            @SafeVarargs
            public final void insertContainer(int index, Container<?, TRepository, TEnum> ...containers) {
                for (int i = 0; i < containers.length; i ++) {
                    int j = index + i;
                    if (j < this.containers.size()) {
                        this.containers.add(j, (Container<Master<TRepository, TEnum>, TRepository, TEnum>)containers[i]);
                    }
                    else {
                        this.containers.add((Container<Master<TRepository, TEnum>, TRepository, TEnum>)containers[i]);
                    }
                }
            }

            /**
             * Inserts one or multiple views at a given index
             * @param index the index
             * @param views the views
             */
            @SuppressWarnings("unchecked")
            @SafeVarargs
            public final void insertView(int index, View<?, TRepository, TEnum> ...views) {
                for (int i = 0; i < views.length; i ++) {
                    int j = index + i;
                    if (j < this.views.size()) {
                        this.views.add(j, (View<Master<TRepository, TEnum>, TRepository, TEnum>)views[i]);
                    }
                    else {
                        this.views.add((View<Master<TRepository, TEnum>, TRepository, TEnum>)views[i]);
                    }
                }
            }

            /**
             * Removes the specified containers from this {@code Master}.
             *
             * @param containers the containers to remove
             */
            @SafeVarargs
            public final void removeContainer(Container<?, TRepository, TEnum> ...containers) {
                for (Container<?, TRepository, TEnum> container : containers) {
                    if (this.containers.contains(container)) {
                        this.containers.remove(container);
                    }
                }
            }

            /**
             * Removes the specified containers from this {@code Master}.
             *
             * @param indices the indices to remove
             */
            @SuppressWarnings("unchecked")
            public final void removeContainer(int ...indices) {
                List<Container<?, TRepository, TEnum>> toRemove = new ArrayList<>();
                for (int i = 0; i < this.containers.size(); i ++) {
                    Container<?, TRepository, TEnum> container = this.containers.get(i);
                    for (int index : indices) {
                        if (index == i) {
                            toRemove.add(container);
                        }
                    }
                }
                removeContainer((Container<?, TRepository, TEnum>[])toRemove.toArray());
            }

            /**
             * Removes the specified views from this {@code Master}.
             *
             * @param views the views to remove
             */
            @SafeVarargs
            public final void removeView(View<?, TRepository, TEnum> ...views) {
                for (View<?, TRepository, TEnum> view : views) {
                    if (this.views.contains(view)) {
                        this.views.remove(view);
                    }
                }
            }

            /**
             * Removes the specified views by indices from this {@code Master}.
             *
             * @param indices the indices to remove
             */
            @SuppressWarnings("unchecked")
            public final void removeView(int ...indices) {
                List<View<Master<TRepository, TEnum>, TRepository, TEnum>> toRemove = new ArrayList<>();
                for (int i = 0; i < this.views.size(); i ++) {
                    View<Master<TRepository, TEnum>, TRepository, TEnum> view = this.views.get(i);
                    for (int index : indices) {
                        if (index == i) {
                            toRemove.add(view);
                        }
                    }
                }
                removeView((View<Master<TRepository, TEnum>, TRepository, TEnum>[])toRemove.toArray());
            }

            /**
             * Sets up a one-to-many relationship with the specified {@code OneToMany} instance.
             *
             * @param oneToMany the {@code OneToMany} relationship to add
             */
            public void oneToMany(OneToMany oneToMany) {
                oneToManyRelations.add(oneToMany);
            }

            /**
             * Adds a {@code Runnable} action to be triggered on view changes.
             *
             * @param runnable the action to add
             */
            public final void onChangeView(Runnable runnable) {
                onChangeViewRunnables.add(runnable);
            }

            /** Adds a runnable for the onReady event */
            public final void onReady(Runnable runnable) { onReadyRunnables.add(runnable); }

            /**
             * Adds a {@code Runnable} action to be triggered when selection changes in one-to-many relationships.
             *
             * @param runnable the action to add
             */
            public final void onOneToManySelectionChanges(Runnable runnable) {
                onOneToManySelectionChangesRunnables.add(runnable);
            }

            /**
             * Creates a combo box for selecting views managed by this master.
             *
             * @return a ComboBox populated with the views.
             */
            public ComboBox<?> createViewsComboBox() {
                ComboBox<ComboBoxModel> cb = new ComboBox<>();
                View<?, ?, ?> activeView = getActiveView();
                ComboBoxModel selected = null;
                for (View<Master<TRepository, TEnum>, TRepository, TEnum> view : getViews()) {
                    if (view.getView().getName() != null) {
                        ComboBoxModel model = new ComboBoxModel(view);
                        cb.addItem(model);
                        if (model.isSelected()) {
                            selected = model;
                        }
                    }
                }
                if (!oneToManyRelations.isEmpty()) {
                    for (OneToMany oneToMany : oneToManyRelations) {
                        for (Property<? extends Repository<? extends TRepository>> repository : oneToMany.listProperty.properties()) {
                            ComboBoxModel model = new ComboBoxModel(repository.get(), oneToMany);
                            cb.addItem(model);
                            if (model.isSelected()) {
                                selected = model;
                            }
                        }
                    }
                }
                if (selected != null) {
                    cb.setSelectedItem(selected);
                }
                cb.setRenderer(new ListCellRenderer<ComboBoxModel>() {
                    @Override
                    public JComponent getListCellRendererComponent(
                        JList<? extends ComboBoxModel> list,
                        ComboBoxModel value,
                        int index,
                        boolean isSelected,
                        boolean cellHasFocus) {

                        if (value == null) return new JLabel("");

                        String name = value.getName();
                        if (name == null) {
                            name = "";
                        }

                        JLabel label = new JLabel();
                        label.setOpaque(true);
                        label.setText(name);
                        if (value.icon != null) {
                            label.setIcon(value.icon);
                        }

                        // Set label color based on selection state
                        if (!value.isValidated()) {
                            label.setBackground(Theme.TABLE_BG_COLOR_DANGER);
                            label.setForeground(Color.white);
                        } else if (value.isSelected()) {
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
                        ComboBoxModel selectedOption = (ComboBoxModel) cb.getSelectedItem();

                        if (selectedOption == null) return;

                        // Check if a View is selected
                        if (selectedOption.getView() != null) {
                            View<?, ?, ?> activeView = getActiveView();
                            if (activeView == null || selectedOption.getView() != activeView.getView()) {
                                getViewProperty().replace(selectedOption.getView());
                                for (OneToMany oneToMany : oneToManyRelations) {
                                    oneToMany.uuidProperty.replace(null);
                                }
                                repository.save();
                                for (Runnable runnable : onChangeViewRunnables) {
                                    runnable.run();
                                }
                                getRoot().render();
                            }
                        }

                        // Check if a Repository (via OneToMany) is selected
                        else if (selectedOption.getUuid() != null && selectedOption.getOneToMany() != null) {
                            String currentUuid = selectedOption.getOneToMany().getUuidProperty().get();
                            String newFileName = selectedOption.getUuid();
                            if (currentUuid == null || !currentUuid.equals(newFileName)) {
                                selectedOption.getOneToMany().getUuidProperty().replace(newFileName);
                                repository.save();
                                for (Runnable runnable : onChangeViewRunnables) {
                                    runnable.run();
                                }
                                getRoot().render();
                            }
                        }
                    }
                });
                return cb;
            }

            /**
             * The {@code OneToMany} class represents a one-to-many relationship, associating a list of repositories
             * with a unique identifier property. It provides functionality to manage and retrieve the associated 
             * repositories, UUID property, and view, enabling interaction with a master-detail structure.
             * 
             * <p>Instances of {@code OneToMany} are primarily used within {@code Master} classes to handle 
             * specific relationships and to facilitate view and repository management based on unique identifiers.
             */
            public class OneToMany {

                /**
                 * Retrieves the list property representing repositories in this one-to-many relationship.
                 *
                 * @return a {@code Repository.Property.List} instance containing repositories related to this entity
                 */
                public Repository.Property.List<? extends Repository<? extends TRepository>> getListProperty() {
                    return listProperty;
                }

                /** Holds the list property for repositories involved in this one-to-many relationship. */
                private Repository.Property.List<? extends Repository<? extends TRepository>> listProperty;

                /**
                 * Retrieves the property representing the unique identifier (UUID) for the relationship.
                 *
                 * @return a {@code Repository.Property<String>} instance containing the UUID for this relationship
                 */
                public Repository.Property<String> getUuidProperty() {
                    return uuidProperty;
                }

                /** Holds the UUID property for this relationship, used to uniquely identify associated entities. */
                private Repository.Property<String> uuidProperty;

                /**
                 * Sets the view associated with this one-to-many relationship.
                 *
                 * @param repositoryUuidView the {@code View} to be set for this one-to-many relationship
                 */
                public void setView(View<? extends Master<TRepository, TEnum>, TRepository, TEnum> repositoryUuidView) {
                    this.view = repositoryUuidView;
                }

                /**
                 * Retrieves the view associated with this one-to-many relationship.
                 *
                 * @return the {@code View} instance currently associated with this one-to-many relationship
                 */
                public View<? extends Master<TRepository, TEnum>, TRepository, TEnum> getView() {
                    return this.view;
                }

                /** Holds the view associated with this one-to-many relationship, typically used for UI or data representation. */
                private View<? extends Master<TRepository, TEnum>, TRepository, TEnum> view;

                /**
                 * Constructs a new {@code OneToMany} instance with the specified properties and view.
                 *
                 * @param listProperty the list property containing repositories associated with this relationship
                 * @param uuidProperty the UUID property used to uniquely identify each entity in this relationship
                 * @param view the view associated with this relationship, typically for UI or navigation purposes
                 */
                public OneToMany(
                        Repository.Property.List<? extends Repository<TRepository>> listProperty,
                        Repository.Property<String> uuidProperty,
                        View<? extends Master<TRepository, TEnum>, TRepository, TEnum> view) {
                    this.listProperty = listProperty;
                    this.uuidProperty = uuidProperty;
                    this.view = view;
                }
            }

            /**
             * The {@code ComboBoxModel} class represents a model for items in a combo box.
             * It encapsulates details about a repository or a view, including their names,
             * file names, and icons. This model can be used to manage selections in 
             * user interfaces, particularly where a one-to-many relationship exists.
             * 
             * @param <TEnum> the enumeration type associated with the views in this model
             */
            public class ComboBoxModel {
                
                /** The file name associated with the repository, if any. */
                private final String uuid;

                /**
                 * Retrieves the file name associated with this model.
                 *
                 * @return the file name as a {@code String}
                 */
                public final String getUuid() {
                    return uuid;
                }

                /** The name displayed for this model in the combo box. */
                private final String name;

                /**
                 * Retrieves the name associated with this model.
                 *
                 * @return the name as a {@code String}
                 */
                public final String getName() {
                    return name;
                }

                /** The icon path associated with this model. */
                private final IconPaths iconPath;

                /**
                 * Retrieves the icon path associated with this model.
                 *
                 * @return the icon path as an {@code IconPaths} instance
                 */
                public final IconPaths getIconPath() {
                    return iconPath;
                }

                /** The view associated with this model, if any. */
                private final TEnum view;

                /**
                 * Retrieves the view associated with this model.
                 *
                 * @return the view as an instance of type {@code TEnum}
                 */
                public final TEnum getView() {
                    return view;
                }

                /**
                 * Determines whether this model is selected based on the associated repository 
                 * or view. It checks if the UUID property of a {@code OneToMany} instance matches 
                 * the file name of the repository, or if the view is currently active.
                 *
                 * @return {@code true} if this model is selected; {@code false} otherwise
                 */
                public boolean isSelected() {
                    View<?, ?, ?> view = getActiveView();
                    if (oneToMany != null) {
                        return oneToMany.getView().view == view.view && !oneToMany.getUuidProperty().isNull() && getUuid() != null && oneToMany.getUuidProperty().get().equals(getUuid());
                    } else if (view != null) {
                        return this.getView() == view.view;
                    } else {
                        return false;
                    }
                }

                /**
                 * Checks whether this model is validated. Validation checks if the associated 
                 * view is valid or if the repository is valid, if present.
                 *
                 * @return {@code true} if this model is validated; {@code false} otherwise
                 */
                public boolean isValidated() {
                    for (View<?, ?, TEnum> view : getViews()) {
                        if (view.view == this.view) {
                            return view.isValidated();
                        }
                    }
                    return repository == null || repository.isValidated();
                }

                /** The {@code OneToMany} relationship associated with this model, if any. */
                private OneToMany oneToMany;

                /**
                 * Retrieves the {@code OneToMany} relationship associated with this model.
                 *
                 * @return the {@code OneToMany} instance, or {@code null} if not applicable
                 */
                public OneToMany getOneToMany() {
                    return oneToMany;
                }

                /** The repository associated with this model, if any. */
                private Repository<?> repository;

                /**
                 * Retrieves the repository associated with this model.
                 *
                 * @return the {@code Repository} instance, or {@code null} if not applicable
                 */
                public Repository<?> getRepository() {
                    return repository;
                }

                /** GET The preloaded icon */
                public Icon icon() {
                    return icon;
                }
                /** The preloaded icon */
                private final Icon icon;

                /**
                 * Constructs a {@code ComboBoxModel} instance for a given repository and 
                 * associated {@code OneToMany} relationship.
                 *
                 * @param repository the repository associated with this model
                 * @param oneToMany the {@code OneToMany} relationship associated with this model
                 */
                public ComboBoxModel(Repository<?> repository, OneToMany oneToMany) {
                    this.name = repository.toString();
                    this.iconPath = repository.getIconPath();
                    this.uuid = repository.getUuid();
                    this.view = null;
                    this.oneToMany = oneToMany;
                    this.repository = repository;
                    if (iconPath != null) {
                        this.icon = Resources.loadImageIcon(iconPath.getPath(), 18, 18);
                    }
                    else {
                        this.icon = null;
                    }
                }

                /**
                 * Constructs a {@code ComboBoxModel} instance for a given view.
                 *
                 * @param view the view associated with this model
                 */
                public ComboBoxModel(View<?, ?, TEnum> view) {
                    this.name = view.getView().getName();
                    this.iconPath = view.getView().getIconPath();
                    this.uuid = null;
                    this.view = view.getView();
                    if (iconPath != null) {
                        this.icon = Resources.loadImageIcon(iconPath.getPath(), 18, 18);
                    }
                    else {
                        this.icon = null;
                    }
                }
            }
        }

        /**
         * The {@code Container} class represents a container that holds elements 
         * and provides methods to manage them. This class is generic and operates 
         * with a master entity, allowing for flexible handling of different 
         * repository types and view enumerations.
         *
         * @param <TMaster> the type of the master entity, which extends {@code Master}
         * @param <TRepository> the type of the repository, which extends {@code Repository}
         * @param <TEnum> the enumeration type of views, extending {@code Views}
         */
        public static class Container<TMaster extends Master<TRepository, TEnum>, 
            TRepository extends Repository<?>, 
            TEnum extends Views> extends Element {

            /**
             * Indicates whether the container should be visible, and thus rendered.
             */
            public boolean isVisible() {
                return true;
            }

            /** The master entity associated with this container. */
            private final TMaster master;

            /**
            * Retrieves the master entity associated with this container.
            *
            * @return the master entity of type {@code TMaster}
            */
            public final TMaster getMaster() {
                return master;
            }

            /** The list of elements contained within this container. */
            private final List<Element> elements = new ArrayList<>();

            /**
            * Retrieves the list of elements contained within this container.
            *
            * @return a list of elements as {@code List<Element>}
            */
            public final List<Element> getElements() {
                return elements;
            }

            /** GET The runnables to run before a render cycle */
            public List<Runnable> onnBeforeRenderRunnables() {
                return onBeforeRenderRunnables;
            }
            /** The runnables to run before a render cycle */
            private List<Runnable> onBeforeRenderRunnables = new ArrayList<>();

            /**
            * Constructs a {@code Container} instance with the specified master entity.
            *
            * @param master the master entity to associate with this container
            */
            public Container(TMaster master) {
                super(master.getRoot());
                this.master = master;
            }

            /** Adds a runnable to run before each render cycle. */
            public void onBeforeRender(Runnable runnable) {
                this.onBeforeRenderRunnables.add(runnable);
            }

            /**
            * Adds one or more elements to this container.
            * The elements are appended to the end of the current element list.
            *
            * @param elements the elements to add
            */
            public final void add(Element ...elements) {
                for (Element element : elements) {
                    this.elements.add(element);
                }
            }

            /**
             * Adds one or more components to this container.
             * @param jComponents
             */
            public final void add(JComponent ...jComponents) {
                List<Element> elements = new ArrayList<>();
                for (int i = 0; i < jComponents.length; i ++) {
                    JComponent jComponent = jComponents[i];
                    elements.add(new Component<TMaster, TRepository, TEnum>(
                        this,
                        jComponent
                    ));
                }
                add(elements.toArray(new Element[0]));
            }

            /**
            * Inserts one or more elements at the specified index in this container.
            * If the index is greater than the current size of the element list,
            * the elements are added at the end.
            *
            * @param index the index at which to insert the elements
            * @param elements the elements to insert
            */
            public final void insert(int index, Element ...elements) {
                for (int i = 0; i < elements.length; i++) {
                    int j = index + i;
                    if (j < this.elements.size()) {
                        this.elements.add(j, elements[i]);
                    } else {
                        this.elements.add(elements[i]);
                    }
                }
            }

            /**
             * Inserts one or more components to this container.
             * @param index the index
             * @param jComponents the components
             */
            public final void insert(int index, JComponent ...jComponents) {
                List<Element> elements = new ArrayList<>();
                for (int i = 0; i < jComponents.length; i ++) {
                    JComponent jComponent = jComponents[i];
                    elements.add(new Component<TMaster, TRepository, TEnum>(
                            this,
                            jComponent
                    ));
                }
                insert(index, elements.toArray(new Element[0]));
            }

            /**
            * Removes one or more specified elements from this container.
            * If an element is contained in the list, it is removed.
            *
            * @param elements the elements to remove
            */
            public final void remove(Element ...elements) {
                for (Element element : elements) {
                    if (this.elements.contains(element)) this.elements.remove(element);
                }
            }

            /**
            * Removes elements at the specified indices from this container.
            * Elements are removed in the order specified by the indices.
            *
            * @param indices the indices of the elements to remove
            */
            public final void remove(int ...indices) {
                List<Element> toRemove = new ArrayList<>();
                for (int i = 0; i < this.elements.size(); i++) {
                    Element element = this.elements.get(i);
                    for (int index : indices) {
                        if (index == i) {
                            toRemove.add(element);
                        }
                    }
                }
                remove(toRemove.toArray(new Element[0]));
            }

            /**
             * Removes all the elements from a container
             */
            public final void removeAll() {
                this.elements.clear();
            }
        }

        /**
         * The {@code Component} class represents a UI component that is associated 
         * with a {@code Container}. This class extends {@code Element} and 
         * allows for managing a Swing {@code JComponent} while providing 
         * functionality for updating and handling actions associated with the 
         * component.
         *
         * @param <TMaster> the type of the master entity, which extends {@code Master}
         * @param <TRepository> the type of the repository, which extends {@code Repository}
         * @param <TEnum> the enumeration type of views, extending {@code Views}
         */
        public static class Component<TMaster extends Master<TRepository, TEnum>, TRepository extends Repository<?>, TEnum extends Views> extends Element {
            /** The Swing component associated with this {@code Component}. */
            private JComponent component;

            /**
             * Sets the Swing component for this {@code Component}.
             *
             * @param component the Swing component to set
             */
            public final void setComponent(JComponent component) {
                this.component = component;
                for (Runnable runnable : onSetComponentRunnables) {
                    runnable.run();
                }
            }

            /**
             * Retrieves the Swing component associated with this {@code Component}.
             *
             * @return the associated Swing component of type {@code JComponent}
             */
            public final JComponent getComponent() {
                return component;
            }

            /** The container that this component belongs to. */
            private final Container<TMaster, TRepository, TEnum> container;

            /**
             * Retrieves the container that this {@code Component} is associated with.
             *
             * @return the container of type {@code Container<TMaster, TRepository, TEnum>}
             */
            public final Container<TMaster, TRepository, TEnum> getContainer() {
                return container;
            }

            /** The list of runnables to be executed on updates. */
            private final List<Runnable> onUpdateRunnables = new ArrayList<>();

            /**
             * Retrieves the list of runnables that will be executed when the
             * component is updated.
             *
             * @return a list of runnables as {@code List<Runnable>}
             */
            public final List<Runnable> getOnUpdateRunnables() {
                return onUpdateRunnables;
            }

            /** The runnables to run when the component is set */
            private final List<Runnable> onSetComponentRunnables = new ArrayList<>();

            /**
             * Constructs a {@code Component} instance with the specified container 
             * and Swing component.
             *
             * @param container the container to associate with this component
             * @param component the Swing component to associate with this component
             */
            public Component(Container<TMaster, TRepository, TEnum> container, JComponent component) {
                super(container.getRoot());
                this.component = component;
                this.container = container;
            }

            /**
             * Adds a runnable to be executed when the component is updated.
             *
             * @param runnable the runnable to be added
             */
            public final void onUpdate(Runnable runnable) {
                this.onUpdateRunnables.add(runnable);
            }

            /** Runs a runnable when this.setComponent has been called. */
            public final void onSetComponent(Runnable runnable) { this.onSetComponentRunnables.add(runnable); }
        }

        /**
         * The {@code View} class represents a visual representation of a 
         * master entity, providing functionality to manage and interact 
         * with a collection of {@code Container} instances. 
         * This class extends {@code Base} and supports operations 
         * for validating views and managing containers within a master entity.
         *
         * @param <TMaster> the type of the master entity, which extends {@code Master}
         * @param <TRepository> the type of the repository, which extends {@code Repository}
         * @param <TEnum> the enumeration type of views, extending {@code Views}
         */
        public static class View<TMaster extends Master<TRepository, TEnum>, TRepository extends Repository<?>, TEnum extends Views> extends Base {
            /**
             * Checks whether the view is validated.
             *
             * @return {@code false} by default, indicating the view is not validated
             */
            public boolean isValidated() {
                return true;
            }

            /** The master entity associated with this view. */
            private final TMaster master;

            /**
             * Retrieves the master entity associated with this view.
             *
             * @return the master entity of type {@code TMaster}
             */
            public final TMaster getMaster() {
                return master;
            }

            /** A list of containers associated with this view. */
            private final List<Container<TMaster, TRepository, TEnum>> containers = new ArrayList<>();

            /**
             * Retrieves the list of containers associated with this view.
             *
             * @return a list of containers of type {@code List<Container<TMaster, TRepository, TEnum>>}
             */
            public final List<Container<TMaster, TRepository, TEnum>> getContainers() {
                return containers;
            }

            /** The enumeration value representing the view type. */
            private final TEnum view;

            /**
             * Retrieves the enumeration value representing the view type.
             *
             * @return the view type of type {@code TEnum}
             */
            public final TEnum getView() {
                return view;
            }

            /**
             * Constructs a {@code View} instance with the specified master entity 
             * and view type.
             *
             * @param master the master entity to associate with this view
             * @param view the view type to associate with this view
             */
            public View(TMaster master, TEnum view) {
                super(master.getRoot());
                this.master = master;
                this.view = view;
            }

            /**
             * Adds one or more containers to this view.
             *
             * @param containers the containers to add
             */
            @SafeVarargs
            public final void add(Container<TMaster, TRepository, TEnum> ...containers) {
                this.containers.addAll(Arrays.asList(containers));
            }

            /**
             * Inserts one or more containers at a specified index in this view.
             *
             * @param index the index at which to insert the containers
             * @param containers the containers to insert
             */
            @SafeVarargs
            public final void insert(int index, Container<TMaster, TRepository, TEnum> ...containers) {
                for (int i = 0; i < containers.length; i++) {
                    int j = index + i;
                    if (j < this.containers.size()) {
                        this.containers.add(j, containers[i]);
                    } else {
                        this.containers.add(containers[i]);
                    }
                }
            }

            /**
             * Removes one or more specified containers from this view.
             *
             * @param containers the containers to remove
             */
            @SafeVarargs
            public final void remove(Container<TMaster, TRepository, TEnum> ...containers) {
                for (Container<TMaster, TRepository, TEnum> container : containers) {
                    this.containers.remove(container);
                }
            }

            /**
             * Removes containers at specified indices from this view.
             *
             * @param indices the indices of the containers to remove
             */
            @SuppressWarnings("unchecked")
            public final void remove(int ...indices) {
                List<Container<TMaster, TRepository, TEnum>> toRemove = new ArrayList<>();
                for (int i = 0; i < this.containers.size(); i++) {
                    Container<TMaster, TRepository, TEnum> container = this.containers.get(i);
                    for (int index : indices) {
                        if (index == i) {
                            toRemove.add(container);
                        }
                    }
                }
                remove(toRemove.toArray(new Container[0]));
            }
        }
    }
}
