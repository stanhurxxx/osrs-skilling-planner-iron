package io.hurx.views.slayer;

import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.components.LootTable;
import io.hurx.components.PlainLabel;
import io.hurx.components.TitleLabel;
import io.hurx.models.CombatStyle;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import io.hurx.models.items.Items;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.components.MasterTable;
import io.hurx.views.slayer.components.targetXP.TargetXPTable;
import io.hurx.views.slayer.components.tasks.TasksTable;
import io.hurx.views.slayer.components.tasks.title.TasksTitleTable;
import io.hurx.views.slayer.components.variants.CombatStyleTable;
import io.hurx.views.slayer.components.variants.VariantTable;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * The slayer view
 */
public class SlayerView extends View {
    /**
     * Get the slayer monsters
     * @return
     */
    public SlayerAssignment[] getSlayerAssignments() {
        SlayerMasters master = panel.getCache().getData().slayerMaster;
        return master.getMaster().getAssignments();
    }

    private TitleLabel masterLabel = new TitleLabel("Slayer master");
    
    private MasterTable masterTable;

    private TasksTitleTable tasksTitleTable;

    private TasksTable tasksTable;

    private VariantTable variantTable;
    
    private PlainLabel variantXpDistributionLabel = new PlainLabel("Rates");
    
    private PlainLabel variantCompletionTimeLabel = new PlainLabel("Completion time");
    
    private CombatStyleTable variantCombatStyleTable;
    
    private TitleLabel targetXPLabel = new TitleLabel("Target XP");
    
    private TargetXPTable targetXPTable;

    private TitleLabel alchLootLabel = new TitleLabel("Alch loot"); 

    private LootTable alchLootTable;

    private TitleLabel dropTradeLootLabel = new TitleLabel("Drop trade loot");
    
    private LootTable dropTradeLootTable;

    private TitleLabel suppliesLabel = new TitleLabel("Supplies");
    
    private PlainLabel suppliesGainedLabel = new PlainLabel("Gained");
    
    private LootTable suppliesGainedLootTable;

    private PlainLabel suppliesCostLabel = new PlainLabel("Cost");

    private LootTable suppliesCostLootTable;

    public SlayerView(SkillingPlannerPanel panel) {
        super(ViewNames.Slayer, panel);        
        panel.setOpaque(true);
        panel.setBackground(Theme.BG_COLOR);
        panel.setDoubleBuffered(true);
    }

    @Override
    public void load() {
        panel.removeAll();

        masterTable = new MasterTable(this);
        tasksTitleTable = new TasksTitleTable(this);
        tasksTable = new TasksTable(this);
        variantTable = new VariantTable(this);
        variantCombatStyleTable = new CombatStyleTable(this);
        targetXPTable = new TargetXPTable(this);
        alchLootTable = new LootTable(this);
        dropTradeLootTable = new LootTable(this);
        suppliesGainedLootTable = new LootTable(this);
        suppliesCostLootTable = new LootTable(this);

        panel.add(panel.createSelect());
        
        panel.add(masterLabel);
        panel.add(masterTable);

        panel.add(tasksTitleTable);

        SlayerMonsters monster = panel.getCache().getData().slayerSelectedMonster;
        Monsters variant = panel.getCache().getData().slayerSelectedVariant;
        setSelectedMonster(monster);
        if (variant != null) setSelectedVariant(variant);
    }

    /**
     * Sets the selected slayer master
     * @param master the master
     */
    public void setSlayerMaster(SlayerMasters master) {
        SlayerMasters currentMaster = panel.getCache().getData().slayerMaster;
        if (master == currentMaster) return;
        panel.getCache().getData().slayerMaster = master;
        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.setSelectedMonster(null);
            tasksTable.fillTableModel();
            tasksTable.revalidate();
            tasksTable.repaint();
        }
    }

    /**
     * Sets the selected slayer monster
     * @param monster the monster
     */
    public void setSelectedMonster(SlayerMonsters monster) {
        CacheData data = panel.getCache().getData();
        data.slayerSelectedMonster = monster;
        data.slayerSelectedVariant = monster == null
            ? null
            : monster.getMonsters()[0];
        try {
            panel.getCache().save();   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            tasksTitleTable.onSetSelectedMonster();
            if (monster == null) {
                for (int i = panel.getComponentCount() - 1; i >= 4; i --) {
                    panel.remove(i);
                }
                panel.add(tasksTable, 4);
            }
            else {
                for (int i = panel.getComponentCount() - 1; i >= 4; i --) {
                    panel.remove(i);
                }
                panel.add(variantTable, 4);
                if (data.slayerSelectedVariant.getStats().getSlayer() == null) {
                    panel.add(variantXpDistributionLabel);
                }
                else {
                    panel.add(variantCompletionTimeLabel);
                }
                panel.add(variantCombatStyleTable);
                variantTable.fillTableModel();
                variantCombatStyleTable.fillTableModel();
            }
            panel.add(targetXPLabel);
            panel.add(targetXPTable);
            panel.add(alchLootLabel);
            panel.add(alchLootTable);
            panel.add(this.dropTradeLootLabel);
            panel.add(dropTradeLootTable);
            panel.add(this.suppliesLabel);
            panel.add(this.suppliesCostLabel);
            panel.add(suppliesGainedLootTable);
            panel.add(this.suppliesGainedLabel);
            panel.add(suppliesCostLootTable);
            for (int i = 4; i < panel.getComponentCount(); i ++) {
                panel.setComponentZOrder(panel.getComponent(i), i);
            }
            panel.revalidate();
            panel.repaint();
        }
    }

    public void setSelectedVariant(Monsters variant) {
        CacheData data = panel.getCache().getData();
        if (data.slayerSelectedMonster == null) return;
        if (variant == null) return;
        try {
            data.slayerSelectedVariant = variant;
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Change the label
            this.panel.remove(5);
            if (variant.getStats().getSlayer() == null) {
                this.panel.add(variantXpDistributionLabel, 5);
            }
            else {
                this.panel.add(variantCompletionTimeLabel, 5);
            }

            // Reload the view
            int variantCombatStyleTableIndex = List.of(this.panel.getComponents()).indexOf(this.variantCombatStyleTable);
            if (variantCombatStyleTableIndex == -1) return;
            this.panel.remove(this.variantCombatStyleTable);
            this.variantCombatStyleTable = new CombatStyleTable(this);
            this.panel.add(this.variantCombatStyleTable, variantCombatStyleTableIndex);
            this.variantCombatStyleTable.fillTableModel();
            this.variantCombatStyleTable.revalidate();
            this.variantCombatStyleTable.repaint();

            panel.revalidate();
            panel.repaint();
        }
    }

    public void setCombatStyleForVariant(CombatStyle combatStyle) {
        CacheData data = panel.getCache().getData();
        Monsters variant = data.slayerSelectedVariant;
        if (variant == null) return;

        if (CombatStyle.melee.contains(combatStyle)) {
            CombatStyle current = data.slayerMonsterCombatStyleMelee.get(variant);
            if (current == combatStyle) return;
            data.slayerMonsterCombatStyleMelee.put(variant, combatStyle);
        }
        else if (CombatStyle.ranged.contains(combatStyle)) {
            CombatStyle current = data.slayerMonsterCombatStyleRanged.get(variant);
            if (current == combatStyle) return;
            data.slayerMonsterCombatStyleRanged.put(variant, combatStyle);
        }
        else if (CombatStyle.magic.contains(combatStyle)) {
            CombatStyle current = data.slayerMonsterCombatStyleMagic.get(variant);
            if (current == combatStyle) return;
            data.slayerMonsterCombatStyleMagic.put(variant, combatStyle);
        }

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.variantCombatStyleTable.fillTableModel();
            this.variantCombatStyleTable.revalidate();
            this.variantCombatStyleTable.repaint();
        }
    }

    public void setVariation(Monsters monster, int percentage) {
        System.out.println("Percentage: " + percentage);

        CacheData data = panel.getCache().getData();
        SlayerMonsters m = data.slayerSelectedMonster;
        if (monster == null) return;
    
        // Get the variation percentages
        Map<Monsters, Integer> percentages = data.slayerMonsterVariation.get(m);

        // Get the variation order
        List<Monsters> order = List.of(data.slayerMonsterVariationOrder.get(m));

        // Get the index
        int index = order.indexOf(monster);

        // The previous percentage
        int previousPercentage = percentages.get(monster);

        // Increase
        if (percentage > previousPercentage) {
            float toAdjust = percentage - previousPercentage;
            float adjusted = 0;

            // Counting
            float percentageBefore = 0;
            for (int i = index - 1; i >= 0; i --) {
                Monsters monsterOther = order.get(i);
                float percentageOther = percentages.get(monsterOther);
                percentageBefore += percentageOther;
            }
            float percentageAfter = 0;
            for (int i = index + 1; i < percentages.size(); i ++) {
                Monsters monsterOther = order.get(i);
                float percentageOther = percentages.get(monsterOther);
                percentageAfter += percentageOther;
            }
            float percentageBeforeAndAfter = percentageBefore + percentageAfter; 

            // First decrease the percentages before it gradually
            if (percentageBefore > 0) {
                for (int i = index - 1; i >= 0; i --) {
                    System.out.println("To adjust: " + toAdjust);
                    Monsters monsterOther = order.get(i);
                    float percentageOther = percentages.get(monsterOther);
                    float decreased = Math.round(toAdjust * (percentageOther / percentageBeforeAndAfter));
                    float actualDecreased = Math.round(Math.min(decreased, percentageOther));
                    adjusted += Math.round(actualDecreased);
                    percentages.put(monsterOther, Math.round(percentageOther - actualDecreased));
                }
            }

            // And decrease the percentages after it gradually
            if (percentageAfter > 0) {
                for (int i = index + 1; i < percentages.size(); i ++) {
                    Monsters monsterOther = order.get(i);
                    float percentageOther = percentages.get(monsterOther);
                    float decreased = Math.round(toAdjust * (percentageOther / percentageBeforeAndAfter));
                    float actualDecreased = Math.min(decreased, percentageOther);
                    adjusted += Math.round(actualDecreased);
                    percentages.put(monsterOther, Math.round(percentageOther - actualDecreased));
                }
            }

            toAdjust -= adjusted;
            if (toAdjust > 0) {
                percentage += Math.ceil(toAdjust);
            }
        }

        // Decrease
        else {
            float toAdjust = previousPercentage - percentage;
            float adjusted = 0;

            float percentageBefore = 0;
            for (int i = index - 1; i >= 0; i --) {
                Monsters monsterOther = order.get(i);
                float percentageOther = percentages.get(monsterOther);
                percentageBefore += percentageOther;
            }
            float percentageAfter = 0;
            for (int i = index + 1; i < percentages.size(); i ++) {
                Monsters monsterOther = order.get(i);
                float percentageOther = percentages.get(monsterOther);
                percentageAfter += percentageOther;
            }
            float percentageBeforeAndAfter = percentageBefore + percentageAfter;

            // Increase the percentages after it gradually
            if (percentageAfter > 0) {
                for (int i = index + 1; i < percentages.size(); i ++) {
                    Monsters monsterOther = order.get(i);
                    float percentageOther = percentages.get(monsterOther);
                    float increased = Math.round(toAdjust * (percentageOther / percentageBeforeAndAfter));
                    adjusted += increased;
                    percentages.put(monsterOther, Math.round(percentageOther + increased));
                }
            }

            // Then increase the percentages before it gradually
            if (percentageBefore > 0) {
                for (int i = index - 1; i >= 0; i --) {
                    Monsters monsterOther = order.get(i);
                    float percentageOther = percentages.get(monsterOther);
                    float increased = Math.round(toAdjust * (percentageOther / percentageBeforeAndAfter));
                    adjusted += increased;
                    percentages.put(monsterOther, Math.round(percentageOther + increased));
                }
            }

            toAdjust -= adjusted;
            if (toAdjust > 0) {
                percentage += Math.ceil(toAdjust);
            }
        }

        // Put the percentage in the list
        percentages.put(monster, percentage);
        data.slayerMonsterVariation.put(m, percentages);

        // Order the array
        data.slayerMonsterVariationOrder.put(
            m,
            percentages.entrySet()
                .stream()
                .sorted(Map.Entry.<Monsters, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ))
                .keySet()
                .toArray(Monsters[]::new) // Safely cast to Monsters[]
        );

        // Save the values
        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Reload the view
            int variantTableIndex = List.of(this.panel.getComponents()).indexOf(this.variantTable);
            this.panel.remove(this.variantTable);
            this.variantTable = new VariantTable(this);
            this.panel.add(this.variantTable, variantTableIndex);
            this.variantTable.fillTableModel();
            this.variantTable.revalidate();
            this.variantTable.repaint();
            this.panel.revalidate();
            this.panel.repaint();
        }
    }

    public void setXpRate(CombatStyle combatStyle, int xpRate) {
        CacheData data = panel.getCache().getData();
        Monsters monster = data.slayerSelectedVariant;
        if (monster == null) return;

        if (CombatStyle.melee.contains(combatStyle)) {
            System.out.println("Attack");
            data.slayerMonsterCombatStyleMeleeHourlyRate.put(monster, xpRate);
        }
        else if (CombatStyle.ranged.contains(combatStyle)) {
            System.out.println("Range");
            data.slayerMonsterCombatStyleRangedHourlyRate.put(monster, xpRate);
        }
        else if (CombatStyle.magic.contains(combatStyle)) {
            System.out.println("Mage");
            data.slayerMonsterCombatStyleMagicHourlyRate.put(monster, xpRate);
        }

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.variantCombatStyleTable.fillTableModel();
            this.variantCombatStyleTable.revalidate();
            this.variantCombatStyleTable.repaint();
        }
    }

    public void setCompletionTime(int minutes) {
        CacheData data = panel.getCache().getData();
        Monsters monster = data.slayerSelectedVariant;
        if (monster == null) return;

        data.slayerMonsterCompletionTime.put(monster, minutes);

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.variantCombatStyleTable.fillTableModel();
            this.variantCombatStyleTable.revalidate();
            this.variantCombatStyleTable.repaint();
        }
    }
}
