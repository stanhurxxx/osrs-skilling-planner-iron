package io.hurx.views.slayer;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.data.SlayerListData;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.components.LootTable;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.MultiComboBox;
import io.hurx.components.Padding;
import io.hurx.components.PlainLabel;
import io.hurx.components.TitleLabel;
import io.hurx.models.CombatStyle;
import io.hurx.models.IconPaths;
import io.hurx.models.MenuIcons;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.components.MasterTable;
import io.hurx.views.slayer.components.targetXP.TargetXPTable;
import io.hurx.views.slayer.components.list.tasks.TasksTable;
import io.hurx.views.slayer.components.list.variants.CombatStyleTable;
import io.hurx.views.slayer.components.list.variants.VariantTable;
import io.hurx.views.slayer.components.overview.SlayerOverviewPercentageTable;
import io.hurx.views.slayer.components.overview.SlayerOverviewTable;
import io.hurx.views.slayer.components.planner.SlayerListsTable;
import io.hurx.views.slayer.components.planner.planning.SlayerPlanningPercentageTable;
import io.hurx.views.slayer.components.planner.planning.SlayerPlanningTable;
import io.hurx.views.slayer.components.planner.planning.SlayerPlanningTopTable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * The slayer view
 */
public class SlayerView extends View {
    /**
     * Get the slayer monsters
     * @return
     */
    public SlayerAssignment[] getSlayerAssignments() {
        SlayerListData list = panel.getCache().getData().getSlayer().getList();
        if (list == null) return new SlayerAssignment[] {};
        return list.getMaster().getMaster().getAssignments();
    }

    private TitleLabel overviewLabel = new TitleLabel("Slayer overview");
    private SlayerOverviewTable overviewTable;
    private SlayerOverviewPercentageTable overviewPercentageTable;

    private TitleLabel plannerListsLabel = new TitleLabel("Slayer lists");
    private SlayerListsTable plannerListsTable;
    private TitleLabel plannerLabel = new TitleLabel("Planning");
    private List<SlayerPlanningTopTable> plannerTopTables;
    private List<SlayerPlanningTable> plannerTables;
    private List<SlayerPlanningPercentageTable> plannerPercentageTables;

    private TitleLabel listMasterLabel;
    private MasterTable listMasterTable;
    private TitleLabel listTasksTitleLabel;
    private TasksTable listTasksTable;
    private VariantTable listVariantTable;
    private PlainLabel listVariantXpDistributionLabel = new PlainLabel("Rates");
    private PlainLabel listVariantCompletionTimeLabel = new PlainLabel("Completion time");
    private CombatStyleTable listVariantCombatStyleTable;
    
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
    }

    @Override
    public void load() {
        panel.removeAll();

        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();

        panel.add(new MultiComboBox(new JComboBox<?>[] {
            panel.createPagesComboBox(),
            createListComboBox()
        }) {
            @Override
            public void fillTableModel() {
                super.fillTableModel();
                for (int i = 0; i < this.options.getColumnCount(); i ++) {
                    getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
                }
            }

            class CellRenderer extends MultiComboBox.CellRenderer {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    MultiComboBox thisTable = (MultiComboBox)table;
                    if (value instanceof SlayerListData) {
                        JComboBox<?> box = thisTable.getControls()[column];
                        for (int i = 0; i < box.getItemCount(); i ++) {
                            ComboBoxModel model = (ComboBoxModel)box.getItemAt(i);
                            if (model.list == value) {
                                box.setSelectedItem(model);
                            }
                        }
                        return box;
                    }
                    else if (value instanceof SlayerViewNames) {
                        JComboBox<?> box = thisTable.getControls()[column];
                        for (int i = 0; i < box.getItemCount(); i ++) {
                            ComboBoxModel model = (ComboBoxModel)box.getItemAt(i);
                            if (model.view == value) {
                                box.setSelectedItem(model);
                            }
                        }
                        return box;
                    }
                    else if (value instanceof ViewNames) {
                        JComboBox<?> box = thisTable.getControls()[column];
                        for (int i = 0; i < box.getItemCount(); i ++) {
                            ViewNames view = (ViewNames)box.getItemAt(i);
                            if (view == value) {
                                box.setSelectedItem(view);
                            }
                        }
                        return box;
                    }
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            }
        });

        if (list == null) {
            if (data.getSlayer().getView() == SlayerViewNames.Overview) {
                overviewTable = new SlayerOverviewTable(this);
                overviewPercentageTable = new SlayerOverviewPercentageTable(50);
                targetXPTable = new TargetXPTable(this);
                alchLootTable = new LootTable(this);
                dropTradeLootTable = new LootTable(this);
                suppliesGainedLootTable = new LootTable(this);
                suppliesCostLootTable = new LootTable(this);
                panel.add(overviewLabel);
                panel.add(overviewTable);
                panel.add(overviewPercentageTable);
                panel.add(targetXPLabel);
                panel.add(targetXPTable);
                panel.add(alchLootLabel);
                panel.add(alchLootTable);
                panel.add(dropTradeLootLabel);
                panel.add(dropTradeLootTable);
                panel.add(suppliesLabel);
                panel.add(suppliesGainedLabel);
                panel.add(suppliesGainedLootTable);
                panel.add(suppliesCostLabel);
                panel.add(suppliesCostLootTable);
            }
            else if (data.getSlayer().getView() == SlayerViewNames.Planner) {
                // TODO:
                plannerListsTable = new SlayerListsTable(this);
                plannerTopTables = new ArrayList<>();
                plannerTables = new ArrayList<>();
                plannerPercentageTables = new ArrayList<>();
                panel.add(plannerListsLabel);
                panel.add(plannerListsTable);
                panel.add(plannerLabel);
                for (int i = 0; i < data.getSlayer().getPlannings().size(); i ++) {
                    SlayerPlanningData planning = data.getSlayer().getPlannings().get(i);
                    if (i > 0) {
                        panel.add(new Padding());
                    }
                    SlayerPlanningTopTable topTable = new SlayerPlanningTopTable(this, planning.getUuid());
                    SlayerPlanningTable planningTable = new SlayerPlanningTable(this, planning.getUuid());
                    SlayerPlanningPercentageTable percentageTable = new SlayerPlanningPercentageTable(this, planning.getUuid());
                    panel.add(topTable);
                    panel.add(planningTable);
                    panel.add(percentageTable);
                    plannerTopTables.add(topTable);
                    plannerTables.add(planningTable);
                    plannerPercentageTables.add(percentageTable);
                }
            }
        }
        else {
            listMasterLabel = new TitleLabel(list.getName(), new Component[] {
                new MenuButton(MenuIcons.Edit) {
                    public void mouseReleased(MouseEvent e) {
                        System.out.println("Edit!");
                    }
                }
            });
            
            listMasterTable = new MasterTable(this);
            listTasksTitleLabel = new TitleLabel("Tasks", new Component[] {
                createListTasksComboBox()
            }) {
                @Override
                public void initialize() {
                    super.initialize();
                    int labelWidth = 67;
                    getColumnModel().getColumn(0).setMinWidth(labelWidth);
                    getColumnModel().getColumn(0).setMaxWidth(labelWidth);
                    getColumnModel().getColumn(0).setWidth(labelWidth);
                    getColumnModel().getColumn(0).setPreferredWidth(labelWidth);

                    getColumnModel().getColumn(1).setMinWidth(223 - labelWidth);
                    getColumnModel().getColumn(1).setMaxWidth(223 - labelWidth);
                    getColumnModel().getColumn(1).setWidth(223 - labelWidth);
                    getColumnModel().getColumn(1).setPreferredWidth(223 - labelWidth);
                }
            };
            listTasksTable = new TasksTable(this);
            listVariantTable = new VariantTable(this);
            listVariantCombatStyleTable = new CombatStyleTable(this);
            targetXPTable = new TargetXPTable(this);
            alchLootTable = new LootTable(this);
            dropTradeLootTable = new LootTable(this);
            suppliesGainedLootTable = new LootTable(this);
            suppliesCostLootTable = new LootTable(this);

            panel.add(listMasterLabel);
            panel.add(listMasterTable);
    
            panel.add(listTasksTitleLabel);

            SlayerMonsters monster = list.getSelectedMonster();
            Monsters variant = list.getSelectedVariant();
            setSelectedMonster(monster);
            if (variant != null) setSelectedVariant(variant);
        }
    }

    /**
     * Sets the selected slayer master
     * @param master the master
     */
    public void setSlayerMaster(SlayerMasters master) {
        SlayerListData list = panel.getCache().getData().getSlayer().getList();
        SlayerMasters currentMaster = list.getMaster();
        if (master == currentMaster) return;
        if (list == null) return;
        
        list.setMaster(master);
        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.setSelectedMonster(null);
            listTasksTable.fillTableModel();
            listTasksTable.revalidate();
            listTasksTable.repaint();
        }
    }

    /**
     * Sets the selected slayer monster
     * @param monster the monster
     */
    public void setSelectedMonster(SlayerMonsters monster) {
        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        list.setSelectedMonster(monster);
        list.setSelectedVariant(
            monster == null
            ? null
            : monster.getMonsters()[0]
        );
        try {
            panel.getCache().save();   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            listTasksTitleLabel.setOtherColumns(new Component[] {
                createListTasksComboBox()
            });
            listTasksTitleLabel.revalidate();
            listTasksTitleLabel.repaint();
            if (monster == null) {
                for (int i = panel.getComponentCount() - 1; i >= 4; i --) {
                    panel.remove(i);
                }
                panel.add(listTasksTable, 4);
            }
            else {
                for (int i = panel.getComponentCount() - 1; i >= 4; i --) {
                    panel.remove(i);
                }
                panel.add(listVariantTable, 4);
                if (list.getSelectedVariant().getStats().getSlayer() == null) {
                    panel.add(listVariantXpDistributionLabel);
                }
                else {
                    panel.add(listVariantCompletionTimeLabel);
                }
                panel.add(listVariantCombatStyleTable);
                listVariantTable.fillTableModel();
                listVariantCombatStyleTable.fillTableModel();
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
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        if (list.getSelectedMonster() == null) return;
        if (variant == null) return;
        try {
            list.setSelectedVariant(variant);
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Change the label
            this.panel.remove(5);
            if (variant.getStats().getSlayer() == null) {
                this.panel.add(listVariantXpDistributionLabel, 5);
            }
            else {
                this.panel.add(listVariantCompletionTimeLabel, 5);
            }

            // Reload the view
            int variantCombatStyleTableIndex = List.of(this.panel.getComponents()).indexOf(this.listVariantCombatStyleTable);
            if (variantCombatStyleTableIndex == -1) return;
            this.panel.remove(this.listVariantCombatStyleTable);
            this.listVariantCombatStyleTable = new CombatStyleTable(this);
            this.panel.add(this.listVariantCombatStyleTable, variantCombatStyleTableIndex);
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();

            panel.revalidate();
            panel.repaint();
        }
    }

    public void setCombatStyleForVariant(CombatStyle combatStyle) {
        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        Monsters variant = list.getSelectedVariant();
        if (variant == null) return;

        if (CombatStyle.getMeleeStyles().contains(combatStyle)) {
            CombatStyle current = list.getMeleeStyles().get(variant);
            if (current == combatStyle) return;
            list.getMeleeStyles().put(variant, combatStyle);
        }
        else if (CombatStyle.getRangedStyles().contains(combatStyle)) {
            CombatStyle current = list.getRangedStyles().get(variant);
            if (current == combatStyle) return;
            list.getRangedStyles().put(variant, combatStyle);
        }
        else if (CombatStyle.getMagicStyles().contains(combatStyle)) {
            CombatStyle current = list.getMagicStyles().get(variant);
            if (current == combatStyle) return;
            list.getMagicStyles().put(variant, combatStyle);
        }

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();
        }
    }

    public void setVariation(Monsters monster, int percentage) {
        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        SlayerMonsters m = list.getSelectedMonster();
        if (monster == null) return;
    
        // Get the variation percentages
        Map<Monsters, Integer> percentages = list.getVariations().get(m);

        // Get the variation order
        List<Monsters> order = List.of(list.getVariationOrders().get(m));

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
        list.getVariations().put(m, percentages);

        // Order the array
        list.getVariationOrders().put(
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
            int variantTableIndex = List.of(this.panel.getComponents()).indexOf(this.listVariantTable);
            this.panel.remove(this.listVariantTable);
            this.listVariantTable = new VariantTable(this);
            this.panel.add(this.listVariantTable, variantTableIndex);
            this.listVariantTable.fillTableModel();
            this.listVariantTable.revalidate();
            this.listVariantTable.repaint();
            this.panel.revalidate();
            this.panel.repaint();
        }
    }

    public void setXpRate(CombatStyle combatStyle, int xpRate) {
        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        Monsters monster = list.getSelectedVariant();
        if (monster == null) return;

        if (CombatStyle.getMeleeStyles().contains(combatStyle)) {
            list.getMeleeHourlyRates().put(monster, xpRate);
        }
        else if (CombatStyle.getRangedStyles().contains(combatStyle)) {
            list.getRangedHourlyRates().put(monster, xpRate);
        }
        else if (CombatStyle.getMagicStyles().contains(combatStyle)) {
            list.getMagicHourlyRates().put(monster, xpRate);
        }

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();
        }
    }

    public void setCompletionTime(int minutes) {
        CacheData data = panel.getCache().getData();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        Monsters monster = list.getSelectedVariant();
        if (monster == null) return;

        list.getMonsterCompletionTimes().put(monster, minutes);

        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();
        }
    }

    public void setSelectedList(SlayerPlanningData planning, String listUuid) {
        CacheData data = panel.getCache().getData();
        planning.setListUuid(listUuid);
        try {
            panel.getCache().save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            int startIndex = -1;
            for (int i = 0; i < panel.getComponents().length; i ++) {
                Component component = panel.getComponent(i);
                if (component instanceof SlayerPlanningTopTable) {
                    startIndex = i;
                    break;
                }
            }
            if (startIndex == -1) return;
            for (int i = 0; i < plannerTopTables.size(); i ++) {
                if (!plannerTopTables.get(i).getPlanning().getUuid().equals(planning.getUuid())) {
                    continue;
                }
                int index = startIndex + i * 4;
                panel.remove(index);
                panel.remove(index);
                panel.remove(index);
                plannerTopTables.remove(i);
                plannerTables.remove(i);
                plannerPercentageTables.remove(i);
                plannerTopTables.add(i, new SlayerPlanningTopTable(this, planning.getUuid()));
                plannerTables.add(i, new SlayerPlanningTable(this, planning.getUuid()));
                plannerPercentageTables.add(i, new SlayerPlanningPercentageTable(this, planning.getUuid()));
                panel.add(plannerTopTables.get(i), index);
                panel.add(plannerTables.get(i), index + 1);
                panel.add(plannerPercentageTables.get(i), index + 2);
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    private JComboBox<?> createListComboBox() {
        CacheData data = panel.getCache().getData();
        List<ComboBoxModel> comboBoxValues = new ArrayList<>();
        ComboBoxModel selectedItem = null;
        for (SlayerViewNames view : SlayerViewNames.values()) {
            ComboBoxModel model = new ComboBoxModel(view);
            comboBoxValues.add(model);
            if (view == data.getSlayer().getView()) {
                selectedItem = model;
            }
        }
        for (SlayerListData list : data.getSlayer().getLists()) {
            ComboBoxModel model = new ComboBoxModel(list);
            comboBoxValues.add(model);
            if (list.getUuid().equals(data.getSlayer().getListUuid())) {
                selectedItem = model;
            }
        }
        JComboBox<ComboBoxModel> cb = new JComboBox<>(comboBoxValues.toArray(new ComboBoxModel[comboBoxValues.size()]));
        cb.setSelectedItem(selectedItem);
        // Custom renderer to display icon and name
        cb.setRenderer(new ListCellRenderer<ComboBoxModel>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends ComboBoxModel> list,
                    ComboBoxModel value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = new JLabel();

                if (value == null) {
                    label.setText("Select an option");
                }
                else if (value.list != null) {
                    label.setText(value.list.getName());
                    label.setIcon(Resources.loadImageIcon(value.list.getMaster().getIconPath().getPath(), 18, 18));
                }
                else if (value.view != null) {
                    label.setText(value.view.getName());
                    label.setIcon(Resources.loadImageIcon(value.view.getIconPath().getPath(), 18, 18));
                }

                label.setOpaque(true);

                if (
                    value == null || (
                        (value.list != null && value.list == panel.getCache().getData().getSlayer().getList())
                        || (value.view != null && value.view == panel.getCache().getData().getSlayer().getView())
                    )
                ) {
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

        // Add an ActionListener to handle selection changes
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                ComboBoxModel selectedOption = (ComboBoxModel) cb.getSelectedItem();
                CacheData data = panel.getCache().getData();

                if (selectedOption.view != null && data.getSlayer().getView() != selectedOption.view) {
                    data.getSlayer().setListUuid(null);
                    data.getSlayer().setView(selectedOption.view);
                }
                else if (selectedOption.list != null && data.getSlayer().getList() != selectedOption.list) {
                    data.getSlayer().setListUuid(selectedOption.list.getUuid());
                    data.getSlayer().setView(null);
                }
                else {
                    return;
                }
                
                try {
                    panel.getCache().save();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    load();
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });
        return cb;
    }

    private JComboBox<TaskComboBoxModel> createListTasksComboBox() {
        CacheData data = panel.getCache().getData();
        SlayerListData slayerList = data.getSlayer().getList();
        if (slayerList == null) return null;

        List<TaskComboBoxModel> comboBoxValues = new ArrayList<>();
        TaskComboBoxModel allTasks = new TaskComboBoxModel(IconPaths.SkillSlayer);
        TaskComboBoxModel selectedItem = null;
        comboBoxValues.add(allTasks);
        if (slayerList.getSelectedMonster() == null) {
            selectedItem = allTasks;
        }
        for (SlayerAssignment assignment : slayerList.getMaster().getMaster().getAssignments()) {
            TaskComboBoxModel model = new TaskComboBoxModel(assignment.getMonster());
            comboBoxValues.add(model);
            if (slayerList.getSelectedMonster() == assignment.getMonster()) {
                selectedItem = model;
            }
        }

        JComboBox<TaskComboBoxModel> cb = new JComboBox<>(comboBoxValues.toArray(new TaskComboBoxModel[comboBoxValues.size()])) {
            @Override
            public String getToolTipText(MouseEvent e) {
                TaskComboBoxModel item = (TaskComboBoxModel)getSelectedItem();
                return item == null || item.monster == null
                    ? null
                    : item.monster.getName();
            }
        };
        cb.setSelectedItem(selectedItem);

        cb.setRenderer(new ListCellRenderer<TaskComboBoxModel>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends TaskComboBoxModel> list,
                    TaskComboBoxModel value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = new JLabel();

                if (value == null) {
                    label.setText("Select an option");
                }
                else if (value.icon != null) {
                    label.setText("All tasks");
                    label.setIcon(Resources.loadImageIcon(value.icon.getPath(), 18, 18));
                }
                else if (value.monster != null) {
                    label.setText(value.monster.getName());
                    label.setIcon(Resources.loadImageIcon(value.monster.getIconPath().getPath(), 18, 18));
                }

                label.setOpaque(true);

                if (
                    value == null || (
                        (value.icon != null && slayerList.getSelectedMonster() == null)
                        || (value.monster != null && slayerList.getSelectedMonster() == value.monster)
                    )
                ) {
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
                TaskComboBoxModel selectedOption = (TaskComboBoxModel)cb.getSelectedItem();

                if (
                    (selectedOption.icon != null && slayerList.getSelectedMonster() != null)
                    || (selectedOption.monster != null && slayerList.getSelectedMonster() != selectedOption.monster)
                ) {
                    setSelectedMonster(selectedOption.icon == null ? selectedOption.monster : null);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        return cb;
    } 

    public static class TaskComboBoxModel {
        private SlayerMonsters monster;

        private IconPaths icon;

        public TaskComboBoxModel(SlayerMonsters monster) {
            this.monster = monster;
            this.icon = null;
        }

        public TaskComboBoxModel(IconPaths icon) {
            this.icon = icon;
            this.monster = null;
        }
    }

    public static class ComboBoxModel {
        public SlayerViewNames getView() {
            return view;
        }
        public SlayerViewNames view = null;

        public SlayerListData getList() {
            return list;
        }
        public SlayerListData list = null;

        public ComboBoxModel(SlayerViewNames view) {
            this.view = view;
        }

        public ComboBoxModel(SlayerListData list) {
            this.list = list;
        }
    }
}
