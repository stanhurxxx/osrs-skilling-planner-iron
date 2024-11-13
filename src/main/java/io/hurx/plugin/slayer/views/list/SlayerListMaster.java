package io.hurx.plugin.slayer.views.list;

import io.hurx.components.Label;
import io.hurx.components.Padding;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.table.Table;
import io.hurx.components.textField.TextField;
import io.hurx.models.IconPaths;
import io.hurx.models.MenuButtons;
import io.hurx.models.Skills;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginPanel;
import io.hurx.plugin.slayer.views.list.views.manageOptions.SlayerListManageOptionsView;
import io.hurx.plugin.slayer.views.list.views.manageTasks.SlayerListManageTasksView;
import io.hurx.plugin.slayer.views.list.views.manageXPRates.SlayerListManageXPRatesView;
import io.hurx.repository.ProfileRepository;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.repository.slayer.SlayerPlanningRepository;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class SlayerListMaster extends ViewManagement.Entity.Master<SlayerListRepository, SlayerListViews> {
    @Override
    public boolean isValidated() {
        return calculator.getPointsPerTaskIncludingSkips() > 0;
    }

    /** GET the slayer list calculator */
    public SlayerListCalculator calculator() {
        return calculator;
    }

    /** Ref to the slayer list calculator */
    private SlayerListCalculator calculator;

    /** The repository */
    private SlayerListRepository repository;

    public SlayerListMaster(PluginPanel root, SlayerListRepository repository) {
        super(
            root,
            repository,
            SlayerListViews.values(),
            () -> {
                SlayerListRepository slayerListRepository = (SlayerListRepository) Repository.registered.get(repository.generatePath());
                return slayerListRepository.view;
            }
        );
        this.repository = repository;
        calculator = new SlayerListCalculator(repository);
        Container container = new Container(this);
        addContainer(container);
        addView(new SlayerListManageTasksView(this));
        addView(new SlayerListManageXPRatesView(this));
        addView(new SlayerListManageOptionsView(this));
        // Initialize the plugin master with the new views
        for (Runnable runnable : getRoot().getPlugin().getMaster().getOnChangeViewRunnables()) {
            runnable.run();
        }
    }

    /** Calculates the hours left, based on all plannings for this slayer list and the current slayer xp */
    public float calculateHoursLeft() {
        float hoursLeft = 0f;
        ProfileRepository profile = getRoot().getPlugin().getMaster().getRepository().account.getProfile();
        if (profile == null) return 0f;

        float currentXP = profile.xp.get(Skills.Slayer, 0f);
        float remainingXP = 0f;
        for (SlayerPlanningRepository planning : profile.slayer.plannings.values()) {
            if (!planning.listUuid.isEqualTo(repository.uuid())) continue;
            float planningStartXP = Math.max(0, planning.startXP.get() - currentXP);
            float planningEndXP = Math.max(0, planning.endXP.get() - currentXP);
            remainingXP += planningEndXP - planningStartXP;
        }
        return Math.round(remainingXP / calculator.getXpPerHour(Skills.Slayer) * 100) / 100f;
    }

    /** The default container for all slayer list views */
    public static class Container extends ViewManagement.Entity.Container<SlayerListMaster, SlayerListRepository, SlayerListViews> {
        private Table table;

        private TextField textField;

        private Label label;

        public Container(SlayerListMaster master) {
            super(master);
            updateTable();
            onBeforeRender(this::update);
        }

        /** Updates the container */
        private void update() {
            if (getMaster().repository.wasDirty()) {
                getMaster().calculator = new SlayerListCalculator(getMaster().getRepository());
                updateTable();
                getMaster().updateViews();
            }
        }

        /** Sets the label for non-editing (default) */
        private void setDefaultLabel() {
            Label.Plain plainLabel = new Label.Plain(getMaster().getRepository().name.getOrDefault("Unnamed list"))
                .border(BorderFactory.createEmptyBorder(0, Theme.CONTROLS_H_PADDING, 0, Theme.CONTROLS_H_PADDING))
                .font(Theme.TITLE_FONT);
            label = (Label) new Label()
                .row(new Object[] {
                    plainLabel,
                    new MenuButton(MenuButtons.Edit).onClick(() -> {
                        setEditLabel();
                        getRoot().render();
                    })
                })
                .group(new Table.Group().colorInactive(Theme.BG_COLOR).coordinate(new Point(0, 0)))
                .validatable(true)
                .render();
            if (!getElements().isEmpty()) {
                remove(0);
            }
            insert(0, label);
        }

        /** Sets the label for editing */
        private void setEditLabel() {
            if (textField == null) {
                textField = new TextField("")
                    .font(Theme.TITLE_FONT)
                    .onChange(() -> {
                        getMaster().getRepository().name.replace(textField.getText());
                        setDefaultLabel();
                        getRoot().render();
                    });
            }
            textField.setText(getMaster().getRepository().name.getOrDefault("Unnamed list"));
            label = (Label) new Label()
                .row(new Object[] {
                    textField,
                    new MenuButton(MenuButtons.Save).onClick(() -> {
                        getMaster().getRepository().name.replace(textField.getText());
                        setDefaultLabel();
                        getRoot().render();
                    })
                })
                .render();
            SwingUtilities.invokeLater(() -> {
                if (textField.requestFocusInWindow()) {
                    textField.selectAll();
                }
            });
            if (!getElements().isEmpty()) {
                remove(0);
            }
            insert(0, label);
        }

        /** Updates and adds the table */
        private void updateTable() {
            removeAll();

            if (label == null) {
                setDefaultLabel();
            }
            else {
                add(label);
            }

            add(new Padding(Theme.TITLE_V_PADDING));

            Object[] pointsPerTask = new Object[] {
                new Label.Plain("Points per task"),
                new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(getMaster().calculator.getPointsPerTaskIncludingSkips() * 100) / 100))
            };
            Object[] skipPercentage = new Object[] {
                new Label.Plain("Skip percentage"),
                new Label.Plain(
                    (float) Math.round(getMaster().calculator.getSkipPercentage() * 100) / 100
                        + "% / "
                        + ((float) Math.round(getMaster().calculator.getBreakEvenSkipPercentage() * 100) / 100) + "%"
                )
            };
            Object[] xpRate = new Object[] {
                new Label.Plain("XP rate"),
                new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(getMaster().calculator.getXpPerHour(Skills.Slayer) * 100) / 100))
            };
            Object[] hoursLeft = new Object[] {
                new Label.Plain("Hours left"),
                new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(getMaster().calculateHoursLeft() * 100) / 100))
            };
            Color colorValidated = Theme.TABLE_BG_COLOR_SUCCESS;
            Color colorInvalidated = Theme.TABLE_BG_COLOR_DANGER;
            Color colorHover = Theme.TABLE_BG_COLOR_HOVER;
            Color colorInactive = Theme.TABLE_BG_COLOR;
            int imageSize = 64;
            if (table == null) {
                table = new Table()
                    .row(new Object[] {
                        Resources.loadImageIcon(IconPaths.NPCDuradel.getPath(), imageSize, imageSize),
                        Resources.loadImageIcon(IconPaths.NPCNieve.getPath(), imageSize, imageSize),
                    })
                    .row(new Object[] {
                        new Label.Plain("Duradel"),
                        new Label.Plain("Nieve")
                    })
                    .row(pointsPerTask)
                    .row(skipPercentage)
                    .row(xpRate)
                    .row(hoursLeft)
                    .group(
                        new Table.Group() {
                            @Override
                            public boolean isValidatable() {
                                return isActive();
                            }

                            @Override
                            public boolean isActive() {
                                return getMaster().repository.master.isEqualTo(SlayerMasters.Duradel);
                            }

                            @Override
                            public boolean isHoverable() {
                                return !isActive();
                            }
                        }
                        .coordinate(new Point(0, 0))
                        .coordinate(new Point(0, 1))
                        .colorValidated(colorValidated)
                        .colorHover(colorHover)
                        .colorInvalidated(colorInvalidated)
                        .colorInactive(colorInactive)
                        .onClick(() -> {
                            if (getMaster().repository.master.isEqualTo(SlayerMasters.Duradel)) {
                                return;
                            }
                            getMaster().repository.master.replace(SlayerMasters.Duradel);
                            getRoot().render();
                        })
                    )
                    .group(
                        new Table.Group() {
                            @Override
                            public boolean isValidatable() {
                                return isActive();
                            }

                            @Override
                            public boolean isActive() {
                                return getMaster().repository.master.isEqualTo(SlayerMasters.Nieve);
                            }

                            @Override
                            public boolean isHoverable() {
                                return !isActive();
                            }
                        }
                        .coordinate(new Point(1, 0))
                        .coordinate(new Point(1, 1))
                        .colorValidated(colorValidated)
                        .colorHover(colorHover)
                        .colorInvalidated(colorInvalidated)
                        .colorInactive(colorInactive)
                        .onClick(() -> {
                            if (getMaster().repository.master.isEqualTo(SlayerMasters.Nieve)) {
                                return;
                            }
                            getMaster().repository.master.replace(SlayerMasters.Nieve);
                            getRoot().render();
                        })
                    )
                    .validatable(true);
                Table.Group group = new Table.Group();
                for (int i = 0; i < table.rows.size(); i ++) {
                    if (i < 2) continue;
                    for (int j = 0; j < table.rows.get(i).length; j ++) {
                        group.coordinate(new Point(j, i));
                    }
                }
                group.colorValidated(colorValidated);
                group.colorInvalidated(colorInvalidated);
                table.group(group);
                table.height(0, imageSize + Theme.TABLE_V_PADDING);
                table.horizontalAlignment(SwingConstants.CENTER);
                table.verticalAlignment(SwingConstants.CENTER);
                table.render();
            }
            else {
                table.rows.set(2, pointsPerTask);
                table.rows.set(3, skipPercentage);
                table.rows.set(4, xpRate);
                table.rows.set(5, hoursLeft);
                table.render();
            }
            add(table);
        }
    }
}
