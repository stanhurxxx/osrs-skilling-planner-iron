package io.hurx.plugin.profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.hurx.components.Label;
import io.hurx.components.Padding;
import io.hurx.components.button.Button;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.table.Table;
import io.hurx.components.textField.TextField;
import io.hurx.models.MenuButtons;
import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;

import io.hurx.plugin.PluginMaster;
import io.hurx.plugin.PluginViews;
import io.hurx.repository.PluginRepository;
import io.hurx.repository.ProfileRepository;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.repository.slayer.SlayerPlanningRepository;
import io.hurx.repository.slayer.SlayerRepository;
import io.hurx.utils.Json;
import io.hurx.utils.Theme;
import net.runelite.http.api.config.Profile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class ProfileView extends ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> {
    public ProfileView(PluginMaster master) {
        super(master, PluginViews.Profile);
    }

    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        @Override
        public boolean isVisible() {
            return getMaster().getRepository().view.get() == PluginViews.Profile
                || getMaster().getRepository().account.getProfile() == null;
        }

        private final TextField textField = new TextField("New profile").onChange(this::create);

        public Container(PluginMaster master) {
            super(master);
            add(new Label.Title(new Object[] {
                new JLabel("New profile"),
                new MenuButton(MenuButtons.Upload).onClick(this::upload)
            }).render());
            add(new Padding(Theme.TITLE_V_PADDING / 2));
            add(new Table()
                .row(new Object[] { textField } )
                .row(new Object[] { new Button("Create").onClick(this::create) })
                .render()
            );
            add(new SelectProfileListContainer(master));
        }

        private void upload() {
            // Create a JFileChooser instance
            JFileChooser fileChooser = new JFileChooser();

            // Set a .json filter to show only JSON files in the dialog
            FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
            fileChooser.setFileFilter(jsonFilter);

            // Open the load dialog
            int userSelection = fileChooser.showOpenDialog(null);

            boolean reload = false;

            // If the user selects a file
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                try {
                    Json.includeSerializationIgnoreFields(true);
                    ProfileRepository profileRepository = (ProfileRepository) new ProfileRepository(getMaster().getRepository()).upload(fileToLoad);

                    getMaster().getRepository().profiles.add(profileRepository);
                    getMaster().getRepository().save();

                    getRoot().render();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error importing profile: " + e.getMessage());
                    e.printStackTrace();
                }
                Json.includeSerializationIgnoreFields(false);
            }
        }

        /** Creates a new profile */
        private void create() {
            ProfileRepository profileRepository = new ProfileRepository(getMaster().getRepository(), UUID.randomUUID().toString());
            profileRepository.name.replace(textField.getText());
            profileRepository.initialize();

            getMaster().getRepository().profiles.add(profileRepository);
            getMaster().getRepository().account.profileUuid.replace(profileRepository.getUuid());
            getMaster().getRepository().save();

            getMaster().getRoot().render();
        }

        public static class SelectProfileListContainer extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
            private long accountLastModified = -1;
            private long pluginLastModified = -1;
            private boolean addedNew = false;

            @Override
            public boolean isVisible() {
                return getMaster().getRepository().profiles.size() > 0;
            }

            public SelectProfileListContainer(PluginMaster master) {
                super(master);
                onBeforeRender(this::load);
                master.getRepository().profiles.listen(new Repository.Property.List.Listener<ProfileRepository>() {
                    @Override
                    public void onAdd(Repository.Property<ProfileRepository> property, int key, ProfileRepository newValue) {
                        addedNew = true;
                    }
                });
            }

            public void load() {
                // Return if nothing changed
                if (
                    pluginLastModified == getMaster().getRepository().lastModified()
                    && accountLastModified == getMaster().getRepository().account.lastModified()
                    && !addedNew
                ) {
                    return;
                }

                // Reset and load all components into this container
                this.removeAll();
                add(new Padding(Theme.TITLE_V_PADDING));
                add(new Label.Title("Select a profile").render());
                add(new Padding(Theme.TITLE_V_PADDING));
                for (ProfileRepository repository : getMaster().getRepository().profiles.values()) {
                    if (getMaster().getRepository().account.profileUuid.get() != null
                            && repository.getUuid() != null
                            && getMaster().getRepository().account.profileUuid.get().equals(repository.getUuid())) {
                        add(new SelectProfileEntryComponent(this, repository));
                    }
                }
                for (ProfileRepository repository : getMaster().getRepository().profiles.values()) {
                    if (getMaster().getRepository().account.profileUuid.get() == null
                            || repository.getUuid() == null
                            || !getMaster().getRepository().account.profileUuid.get().equals(repository.getUuid())) {
                        add(new SelectProfileEntryComponent(this, repository));
                    }
                }
                pluginLastModified = getMaster().getRepository().lastModified();
                accountLastModified = getMaster().getRepository().account.lastModified();
                addedNew = false;
            }

            public static class SelectProfileEntryComponent extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {
                private ProfileRepository profile = null;

                private TextField textField = null;

                public SelectProfileEntryComponent(SelectProfileListContainer container, ProfileRepository profile) {
                    super(container, null);
                    this.profile = profile;
                    save();
                    onSetComponent(() -> {
                        getRoot().render();
                    });
                }

                /** Shorthand for checking if this profile is selected */
                public boolean isSelected() {
                    return getContainer().getMaster().getRepository().account.profileUuid.get() != null
                            && profile.getUuid() != null
                            && getContainer().getMaster().getRepository().account.profileUuid.get().equals(profile.getUuid());
                }

                /** GET The mouse listener for the table. */
                private MouseListener mouseListener() {
                    return new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                JPopupMenu popupMenu = new JPopupMenu();
                                JMenuItem exportProfile = new JMenuItem("Export profile");
                                exportProfile.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        download();
                                    }
                                });
                                JMenuItem duplicateProfile = new JMenuItem("Duplicate profile");
                                duplicateProfile.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        duplicate();
                                    }
                                });
                                JMenuItem deleteProfile = new JMenuItem("Delete profile");
                                deleteProfile.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        delete();
                                    }
                                });
                                popupMenu.add(exportProfile);
                                popupMenu.add(duplicateProfile);
                                popupMenu.add(deleteProfile);
                                popupMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    };
                }

                /** Loads the component that can edit the name of the profile */
                public void edit() {
                    textField = new TextField(profile.name.get())
                        .onChange(this::save)
                        .isSelected(isSelected());
                    Label label = new Label(new Object[] {
                        textField,
                        new MenuButton(MenuButtons.Save)
                            .isSelected(isSelected())
                            .onClick(this::edit)
                    });
                    textField.requestFocusInWindow();
                    textField.selectAll();
                    setComponent(label);
                }

                /** Loads the default component, and saves the data from the edit component, if any. */
                public void save() {
                    String name = textField == null ? profile.name.get() : textField.getText();
                    name = name.isEmpty() ? "Unnamed profile" : name;
                    Label label = (Label) new Label(new Object[] {
                            new Label.Plain(name)
                                    .isSelected(isSelected())
                                    .hoverable(true)
                                    .mouseListener(new MouseAdapter() {
                                @Override
                                public void mouseReleased(MouseEvent e) {
                                    if (e.getButton() == MouseEvent.BUTTON1) {
                                        select();
                                    }
                                }
                            }),
                            new MenuButton(MenuButtons.Edit)
                                .isSelected(isSelected())
                                .onClick(this::edit)
                    }).mouseListener(mouseListener());
                    if (textField != null) {
                        profile.name.replace(name);
                        profile.save();
                    }
                    setComponent(label);
                }

                /** Selects the profile of this component */
                public void select() {
                    getContainer().getMaster().getRepository().account.profileUuid.replace(profile.getUuid());
                    getContainer().getMaster().getRepository().save();
                    getRoot().render();
                }

                /** Downloads the profile to .json */
                private void download() {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files (*.json)", "json");
                    chooser.setFileFilter(filter);
                    chooser.setSelectedFile(new File(profile.name.get() + ".json"));
                    int userSelection = chooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = chooser.getSelectedFile();
                        if (!fileToSave.getName().endsWith(".json")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
                        }
                        Json.includeSerializationIgnoreFields(true);
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                            Json.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                            String jsonString = profile.download();
                            writer.write(jsonString);
                        }
                        catch (JsonProcessingException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error exporting profile to JSON: " + ex.getMessage());
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error exporting profile: " + ex.getMessage());
                        }
                        Json.includeSerializationIgnoreFields(false);
                    }
                }

                /** Duplicates the profile */
                public void duplicate() {
                    String newName = (String) JOptionPane.showInputDialog(
                            null,
                            "Enter new name:",
                            "Duplicating profile \"" + profile.name.get() + "\"",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            profile.name.get()
                    );
                    if (newName == null) newName = "Unnamed profile";
                    ProfileRepository duplicate = (ProfileRepository) profile.duplicate();
                    duplicate.name.replace(newName);
                    getContainer().getMaster().getRepository().profiles.add(duplicate);
                    getContainer().getMaster().getRepository().save();
                    getRoot().render();
                }

                /** Deletes this profile */
                public void delete() {
                    int option = JOptionPane.showOptionDialog(
                        null,
                        "Are you sure you wish to delete the profile \"" + profile.name.get() + "\"? This can not be undone.",
                        "Delete profile",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] { "Yes", "No" },
                        null
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        getContainer().getMaster().getRepository().profiles.remove(profile);
                        getContainer().getMaster().getRepository().save();
                        getRoot().render();
                    }
                }
            }
        }
    }
}
