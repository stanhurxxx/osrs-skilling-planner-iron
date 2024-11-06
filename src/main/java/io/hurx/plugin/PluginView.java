package io.hurx.plugin;

import io.hurx.components.Label;
import io.hurx.components.Padding;
import io.hurx.components.button.Button;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.table.Table;
import io.hurx.components.textField.TextField;
import io.hurx.models.MenuButtons;
import io.hurx.models.views.ViewManagement;

import io.hurx.plugin.profile.ProfileRepository;
import io.hurx.utils.Theme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class PluginView extends ViewManagement.Entity.View<PluginMaster, PluginRepository, PluginViews> {
    public PluginView(PluginMaster master) {
        super(master, PluginViews.Profile);
        add(new Container(master));
    }

    public static class Container extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
        private TextField textField = new TextField("Default profile");

        public SelectProfileListContainer selectProfileListContainer() {
            return selectProfileListContainer;
        }
        private SelectProfileListContainer selectProfileListContainer;

        public Container(PluginMaster master) {
            super(master);
            add(new Label.Title("New profile").render());
            add(new Padding(Theme.TITLE_V_PADDING / 2));
            add(new Table()
                .row(new Object[] { textField })
                .row(new Object[] { new Button("Create").onStopCellEditing(() -> {
                    ProfileRepository profileRepository = new ProfileRepository(getMaster().getRoot().getPlugin(), UUID.randomUUID().toString());
                    profileRepository.name.replace(textField.getText());
                    profileRepository.initialize();

                    getMaster().getRepository().profiles.add(profileRepository);
                    getMaster().getRepository().profile.replace(profileRepository.getFileName());
                    getMaster().getRepository().save();

                    selectProfileListContainer.load();
                    getMaster().getRoot().render();
                }) })
                .render()
            );
            add(new Padding(Theme.TITLE_V_PADDING));
            add(new Label.Title("Select a profile").render());
            add(new Padding(Theme.TITLE_V_PADDING));
            selectProfileListContainer = new SelectProfileListContainer(master);
            add(selectProfileListContainer);
            add(new Padding(Theme.TITLE_V_PADDING / 2));
        }

        public static class SelectProfileListContainer extends ViewManagement.Entity.Container<PluginMaster, PluginRepository, PluginViews> {
            @Override
            public boolean isVisible() {
                return getMaster().getRepository().profiles.size() > 0;
            }

            public SelectProfileListContainer(PluginMaster master) {
                super(master);
                load();
            }

            public void load() {
                this.removeAll();
                for (ProfileRepository repository : getMaster().getRepository().profiles.values()) {
                    if (getMaster().getRepository().profile.get() != null
                            && repository.getFileName() != null
                            && getMaster().getRepository().profile.get().equals(repository.getFileName())) {
                        add(new SelectProfileEntryComponent(this, repository));
                    }
                }
                for (ProfileRepository repository : getMaster().getRepository().profiles.values()) {
                    if (getMaster().getRepository().profile.get() == null
                            || repository.getFileName() == null
                            || !getMaster().getRepository().profile.get().equals(repository.getFileName())) {
                        add(new SelectProfileEntryComponent(this, repository));
                    }
                }
            }

            public static class SelectProfileEntryComponent extends ViewManagement.Entity.Component<PluginMaster, PluginRepository, PluginViews> {
                private ProfileRepository profile = null;

                private TextField textField = null;

                public SelectProfileEntryComponent(SelectProfileListContainer container, ProfileRepository profile) {
                    super(container, null);
                    this.profile = profile;
                    save();
                }

                public void edit() {
                    textField = new TextField(profile.name.get())
                        .onStopCellEditing(() -> {
                            save();
                        })
                        .isSelected(isSelected());
                    Label label = new Label(new Object[] {
                        textField,
                        new MenuButton(MenuButtons.Save)
                            .onStopCellEditing(() -> {
                                edit();
                            })
                            .isSelected(isSelected()),
                    });
                    textField.requestFocusInWindow();
                    textField.selectAll();
                    setComponent(label);
                }

                public void select() {
                    getContainer().getMaster().getRepository().profile.replace(profile.getFileName());
                    getContainer().getMaster().getRepository().save();
                    ((SelectProfileListContainer)getContainer()).load();
                }

                public void save() {
                    String name = textField == null ? profile.name.get() : textField.getText();
                    name = name.isEmpty() ? "Unnamed profile" : name;
                    Label label = new Label(new Object[] {
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
                            .onStopCellEditing(() -> {
                                edit();
                            })
                    });
                    if (textField != null) {
                        profile.name.replace(name);
                        profile.save();
                    }
                    setComponent(label);
                }

                public boolean isSelected() {
                    return getContainer().getMaster().getRepository().profile.get() != null
                        && profile.getFileName() != null
                        && getContainer().getMaster().getRepository().profile.get().equals(profile.getFileName());
                }

                public void duplicate() {
                    // TODO: duplicate
                }

                public void delete() {
                    // TODO: delete
                }

                public void export() {
                    // TODO: export
                }
            }
        }
    }
}
