package io.hurx.plugin.slayer.views.list;

import io.hurx.components.Label;
import io.hurx.models.IconPaths;
import io.hurx.models.Skills;
import io.hurx.models.views.ViewManagement;
import io.hurx.plugin.PluginPanel;
import io.hurx.plugin.slayer.views.list.views.manageOptions.SlayerListManageOptionsView;
import io.hurx.plugin.slayer.views.list.views.manageTasks.SlayerListManageTasksView;
import io.hurx.plugin.slayer.views.list.views.manageXPRates.SlayerListManageXPRatesView;
import io.hurx.repository.ProfileRepository;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.repository.slayer.SlayerPlanningRepository;
import io.hurx.utils.Resources;

import java.text.NumberFormat;
import java.util.Locale;

public class SlayerListMaster extends ViewManagement.Entity.Master<SlayerListRepository, SlayerListViews> {
    private SlayerListCalculator calculator;

    public SlayerListMaster(PluginPanel root, SlayerListRepository repository) {
        super(
            root,
            repository,
            SlayerListViews.values(),
            repository.view
        );
        calculator = new SlayerListCalculator(repository);
        addView(new SlayerListManageTasksView(this));
        addView(new SlayerListManageXPRatesView(this));
        addView(new SlayerListManageOptionsView(this));
        addContainer(new Container(this));
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
        float targetXP = currentXP;
        for (SlayerPlanningRepository planning : profile.slayer.plannings.values()) {
            float planningStartXP = Math.min(currentXP, planning.startXP.get());
            float planningEndXP = Math.min(currentXP, planning.endXP.get());
            targetXP += planningEndXP - planningStartXP;
        }
        return targetXP / calculator.getXpPerHour(Skills.Slayer);
    }

    /** The default container for all slayer list views */
    public static class Container extends ViewManagement.Entity.Container<SlayerListMaster, SlayerListRepository, SlayerListViews> {
        public Container(SlayerListMaster master) {
            super(master);
            add(
                new Table()
                    .row(new Object[] {
                        Resources.loadImageIcon(IconPaths.NPCDuradel.getPath(), 64, 64),
                        Resources.loadImageIcon(IconPaths.NPCNieve.getPath(), 64, 64),
                    })
                    .row(new Object[] {
                        new Label.Plain("Duradel"),
                        new Label.Plain("Nieve")
                    })
                    .row(new Object[] {
                        new Label.Plain("Points per task"),
                        new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(getMaster().calculator.getPointsPerTaskIncludingSkips() * 100) / 100))
                    })
                    .row(new Object[] {
                        new Label.Plain("Skip percentage"),
                        new Label.Plain(
                        (float) Math.round(getMaster().calculator.getSkipPercentage() * 100) / 100
                            + "% / "
                            + ((float) Math.round(getMaster().calculator.getBreakEvenSkipPercentage() * 100) / 100) + "%"
                        )
                    })
                    .row(new Object[] {
                        new Label.Plain("XP rate"),
                        new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(getMaster().calculator.getXpPerHour(Skills.Slayer) * 100) / 100))
                    })
                    .row(new Object[] {
                        new Label.Plain("Hours left"),
                        new Label.Plain(NumberFormat.getInstance(Locale.US).format((float) Math.round(master.calculateHoursLeft() * 100) / 100))
                    })
            );
        }

        public static class Table extends io.hurx.components.table.Table {

        }
    }
}
