package io.hurx.old;

import io.hurx.cache.data.entities.SlayerListData;
import io.hurx.cache.data.entities.SlayerPlanningData;
import io.hurx.components.LootTable;
import io.hurx.components.menuButton.MenuButton;
import io.hurx.components.textField.abbreviatedNumberFormattedTextField.AbbreviatedNumberFormattedTextField;
import io.hurx.components.MultiComboBox;
import io.hurx.components.Padding;
import io.hurx.components.PlainLabel;
import io.hurx.components.TitleLabel;
import io.hurx.models.CombatStyle;
import io.hurx.models.IconPaths;
import io.hurx.models.MenuIcons;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.models.views.View;
import io.hurx.models.views.ViewElement;
import io.hurx.old.components.MasterTable;
import io.hurx.old.components.manage.list.tasks.TasksTable;
import io.hurx.old.components.manage.list.variants.CombatStyleTable;
import io.hurx.old.components.manage.list.variants.VariantTable;
import io.hurx.old.components.manage.options.SlayerOptionsTable;
import io.hurx.old.components.manage.xpRates.TextFieldContainer;
import io.hurx.old.components.manage.xpRates.XpRateCombatStyles;
import io.hurx.old.components.manage.xpRates.XpRateMonsters;
import io.hurx.old.components.overview.SlayerOverviewPercentageTable;
import io.hurx.old.components.overview.SlayerOverviewTable;
import io.hurx.old.components.planner.SlayerListsTable;
import io.hurx.old.components.planner.planning.SlayerPlanningPercentageTable;
import io.hurx.old.components.planner.planning.SlayerPlanningTable;
import io.hurx.old.components.planner.planning.SlayerPlanningTopTable;
import io.hurx.old.components.targetXP.TargetXPTable;
import io.hurx.plugin.PluginPanel;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.PluginViews;
import io.hurx.plugin.slayer.SlayerSubViewNames;
import io.hurx.plugin.slayer.SlayerViewNames;
import io.hurx.plugin.slayer.views.list.SlayerListCalculator;
import io.hurx.util.cache.Cache;
import io.hurx.utils.Resources;
import io.hurx.utils.Theme;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;
import java.util.HashMap;
import javax.swing.BorderFactory;

/**
 * The slayer view
 */
public class SlayerView extends View {
    /**
     * Get the slayer monsters
     * @return
     */
    public SlayerAssignment[] getSlayerAssignments() {
        SlayerListData list = Cache.getRepository().getSlayer().getList();
        if (list == null) return new SlayerAssignment[] {};
        return list.getMaster().getMaster().getAssignments();
    }

    private TitleLabel overviewLabel = new TitleLabel("Slayer overview");
    private SlayerOverviewTable overviewTable;
    private SlayerOverviewPercentageTable overviewPercentageTable;

    private TitleLabel plannerListsLabel;
    private SlayerListsTable plannerListsTable;
    private TitleLabel plannerLabel;
    private List<SlayerPlanningTopTable> plannerTopTables;
    private List<SlayerPlanningTable> plannerTables;
    private List<SlayerPlanningPercentageTable> plannerPercentageTables;

    private TitleLabel listMasterLabel;
    private MasterTable listMasterTable;
    private TitleLabel listManageTitleLabel;
    private TitleLabel listTasksTitleLabel;
    private TasksTable listTasksTable;
    private VariantTable listVariantTable;
    private PlainLabel listVariantXpDistributionLabel = new PlainLabel("Rates");
    private PlainLabel listVariantCompletionTimeLabel = new PlainLabel("Completion time");
    private CombatStyleTable listVariantCombatStyleTable;

    private int listManageXPRatesIndex = -1;
    private int listManageOptionsIndex = -1;
    private List<TextFieldContainer> listManageXPRatesTextFieldContainers;
    private List<XpRateMonsters> listManageXPRatesMonsters;
    private List<Padding> listManageXPRatesPaddings;
    private TitleLabel listManageXpRatesLabel;
    private PlainLabel listManageXpRatesChangeStyleLabel;
    private XpRateCombatStyles listManageXpRatesCombatStyles;
    private TitleLabel listManageOptionsLabel;
    private SlayerOptionsTable listManageOptionsTable;
    
    private TitleLabel targetXPLabel = new TitleLabel("Target XP");
    private TargetXPTable targetXPTable;
    private TitleLabel alchLootLabel = new TitleLabel("Alch loot"); 
    private LootTable alchLootTable;
    private TitleLabel dropTradeLootLabel = new TitleLabel("Drop trade loot");
    private LootTable dropTradeLootTable;
    private TitleLabel suppliesLabel = new TitleLabel("Supplies", true);
    private PlainLabel suppliesGainedLabel = new PlainLabel("Gained");
    private LootTable suppliesGainedLootTable;
    private PlainLabel suppliesCostLabel = new PlainLabel("Cost");
    private LootTable suppliesCostLootTable;

    public SlayerView(PluginPanel panel) {
        super(PluginViews.Slayer, panel, Arrays.asList(new Element[] {}));
    }

    @Override
    public void load() {
        panel.removeAll();

        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();

        panel.add(new MultiComboBox(new JComboBox<?>[] {
            panel.createViewsComboBox(),
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
                    else if (value instanceof PluginViews) {
                        JComboBox<?> box = thisTable.getControls()[column];
                        for (int i = 0; i < box.getItemCount(); i ++) {
                            PluginViews view = (PluginViews)box.getItemAt(i);
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
                targetXPLabel = new TitleLabel("Target XP");
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
                plannerListsLabel = new TitleLabel("Slayer lists", new Component[] {
                    new MenuButton(MenuIcons.Upload) {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            loadSlayerList(); 
                        }
                    },
                    new MenuButton(MenuIcons.Add) {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            SlayerListData list = new SlayerListData();
                            Cache.getRepository().getSlayer().getLists().add(list);
                            Cache.getRepository().getSlayer().setListUuid(list.getUuid());
                            Cache.getRepository().getSlayer().setView(null);
                            try {
                                Cache.save();
                                load();
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                plannerLabel = new TitleLabel("Planning", new Component[] {
                    new MenuButton(MenuIcons.Add) {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            SlayerPlanningData planning = new SlayerPlanningData();
                            planning.setStartXP(200_000_000);
                            planning.setListUuid(data.getSlayer().getLists().get(data.getSlayer().getLists().size() - 1).getUuid());
                            planning.setEndXP(200_000_000);
                            Cache.getRepository().getSlayer().getPlannings().add(planning);
                            updatePlannings();
                        }
                    }
                });
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
                    for (Component component : new Component[] {
                        topTable, planningTable, percentageTable
                    }) {
                        component.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                if (e.getButton() == MouseEvent.BUTTON3) {
                                    JPopupMenu popupMenu = new JPopupMenu();
                                    PluginRepository data = Cache.getRepository();
                                    JMenuItem delete = new JMenuItem("Delete planning item");
                                    delete.addActionListener(ae -> {
                                        deletePlanning(planning);
                                    });
                                    popupMenu.add(delete);
                                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                                }
                            }
                        });
                    }
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
            JTextField text = new JTextField() {
                @Override
                public void setText(String text) {
                    setListName(text);
                    super.setText(text);
                }
            };
            text.setText(list.getName());
            text.setBackground(Theme.TABLE_BG_COLOR_HOVER);
            listMasterLabel = new TitleLabel(text, new Component[] {
                new MenuButton(MenuIcons.Reset) {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        resetDefaultsForList(list);
                    }
                },
                new MenuButton(MenuIcons.Download) {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        saveSlayerList(list);
                    }
                }
            }, true);
            
            listMasterTable = new MasterTable(this);

            listManageTitleLabel = new TitleLabel("View", new Component[] {
                createListManageComboBox()
            }) {
                @Override
                public void initialize() {
                    super.initialize();
                    int labelWidth = 58;
                    getColumnModel().getColumn(0).setMinWidth(labelWidth);
                    getColumnModel().getColumn(0).setMaxWidth(labelWidth);
                    getColumnModel().getColumn(0).setWidth(labelWidth);
                    getColumnModel().getColumn(0).setPreferredWidth(labelWidth);

                    getColumnModel().getColumn(1).setMinWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setMaxWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setPreferredWidth(223 - labelWidth + 15);
                }
            };
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
            listManageXpRatesLabel = new TitleLabel("Rates", new Component[] {
                createListManageXpRatesComboBox()
            }) {
                @Override
                public void initialize() {
                    super.initialize();
                    int labelWidth = 65;
                    getColumnModel().getColumn(0).setMinWidth(labelWidth);
                    getColumnModel().getColumn(0).setMaxWidth(labelWidth);
                    getColumnModel().getColumn(0).setWidth(labelWidth);
                    getColumnModel().getColumn(0).setPreferredWidth(labelWidth);

                    getColumnModel().getColumn(1).setMinWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setMaxWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setWidth(223 - labelWidth + 15);
                    getColumnModel().getColumn(1).setPreferredWidth(223 - labelWidth + 15);
                }
            };

            listManageOptionsLabel = new TitleLabel("Options");

            panel.add(listMasterLabel);
            panel.add(listManageTitleLabel);
            panel.add(listMasterTable);
            
            SlayerSubViewNames subView = list.getSubView();
            if (subView == SlayerSubViewNames.ManageTasks) {
                panel.add(listTasksTitleLabel);

                SlayerMonsters monster = list.getSelectedMonster();
                Monsters variant = list.getSelectedVariant();
                setSelectedMonster(monster);
                if (variant != null) setSelectedVariant(variant);
            }
            else if (subView == SlayerSubViewNames.ManageXPRates) {
                panel.add(listManageXpRatesLabel);
                listManageXPRatesIndex = panel.getComponentCount();
                updateManageXPRatesSubView();
            }
            else if (subView == SlayerSubViewNames.ManageOptions) {
                panel.add(listManageOptionsLabel);
                listManageOptionsIndex = panel.getComponentCount();
                updateManageXPRatesOptionsSubView();
            }
        }
    }

    public void updateManageXPRatesOptionsSubView() {
        if (listManageOptionsIndex == -1) return;
        for (int i = listManageOptionsIndex; i < panel.getComponentCount(); i ++) {
            if (panel.getComponentCount() > i) {
                panel.remove(i);
            }
        }
        listManageOptionsTable = new SlayerOptionsTable(this);
        panel.add(listManageOptionsTable);
        panel.revalidate();
        panel.repaint();
    }

    public void updateManageXPRatesSubView() {
        if (listManageXPRatesIndex == -1) return;
        for (int i = listManageXPRatesIndex; i < panel.getComponentCount(); i ++) {
            panel.remove(i);
        }
        listManageXPRatesTextFieldContainers = new ArrayList<>();
        listManageXPRatesPaddings = new ArrayList<>();
        listManageXPRatesMonsters = new ArrayList<>();
        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        CombatStyle combatStyle = list.getCombatStyleFilter();
        Map<Integer, List<Monsters>> values = new HashMap<>();
        for (Object raw : new Object[] { new Object[] { list.getMeleeHourlyRates(), list.getMeleeStyles() }, new Object[] { list.getMagicHourlyRates(), list.getMagicStyles() }, new Object[] { list.getRangedHourlyRates(), list.getRangedStyles() } }) {
            Map<Monsters, Integer> hourlyRates = (Map<Monsters, Integer>) ((Object[])raw)[0];
            Map<Monsters, CombatStyle> combatStyles = (Map<Monsters, CombatStyle>) ((Object[])raw)[1];
            
            for (Monsters monster : combatStyles.keySet()) {
                CombatStyle style = combatStyles.get(monster);
                if (style != combatStyle) continue;

                List<Monsters> existingBatch = values.get(hourlyRates.get(monster));
                List<Monsters> batch = existingBatch == null ? new ArrayList<>() : existingBatch;
                if (batch.indexOf(monster) == -1) {
                    batch.add(monster);
                }
                values.put(hourlyRates.get(monster), batch);
            }
        }

        List<Integer> integers = new ArrayList<>();
        for (int integer : values.keySet()) {
            integers.add(integer);
        }
        integers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });
        for (Integer value : integers) {
            if (value == null) continue;

            List<Monsters> monsters = values.get(value);
            listManageXPRatesMonsters.add(new XpRateMonsters(this, monsters));

            AbbreviatedNumberFormattedTextField textField = new AbbreviatedNumberFormattedTextField();
            textField.setValue(value);
            textField.setBackground(Theme.TABLE_BG_COLOR_HOVER);
            textField.setBorder(BorderFactory.createEmptyBorder());
            TextFieldContainer textFieldContainer = new TextFieldContainer(integers.indexOf(value), this, textField);
            
            Padding padding = new Padding();
            listManageXPRatesPaddings.add(padding);

            listManageXPRatesTextFieldContainers.add(textFieldContainer);
        }
        for (int i = 0; i < listManageXPRatesTextFieldContainers.size(); i ++) {
            panel.add(listManageXPRatesMonsters.get(i));
            panel.add(listManageXPRatesTextFieldContainers.get(i));
            if (i < listManageXPRatesTextFieldContainers.size() - 1) panel.add(listManageXPRatesPaddings.get(i));
        }
        listManageXpRatesChangeStyleLabel = new PlainLabel("Change styles to");
        listManageXpRatesCombatStyles = new XpRateCombatStyles(this);
        panel.add(listManageXpRatesChangeStyleLabel);
        panel.add(listManageXpRatesCombatStyles);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Sets the selected slayer master
     * @param master the master
     */
    public void setSlayerMaster(SlayerMasters master) {
        SlayerListData list = Cache.getRepository().getSlayer().getList();
        SlayerMasters currentMaster = list.getMaster();
        if (master == currentMaster) return;
        if (list == null) return;
        
        list.setMaster(master);
        try {
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.setSelectedMonster(null);
            listTasksTable.fillTableModel();
            listTasksTable.revalidate();
            listTasksTable.repaint();
            updateMasterTable();
        }
    }

    /**
     * Sets the selected slayer monster
     * @param monster the monster
     */
    public void setSelectedMonster(SlayerMonsters monster) {
        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        list.setSelectedMonster(monster);
        list.setSelectedVariant(
            monster == null
            ? null
            : monster.getMonsters()[0]
        );
        try {
            Cache.save();   
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
                for (int i = panel.getComponentCount() - 1; i >= 5; i --) {
                    panel.remove(i);
                }
                panel.add(listTasksTable, 5);
            }
            else {
                for (int i = panel.getComponentCount() - 1; i >= 5; i --) {
                    panel.remove(i);
                }
                panel.add(listVariantTable, 5);
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
            targetXPLabel = new TitleLabel("XP gained");
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
        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        if (list.getSelectedMonster() == null) return;
        if (variant == null) return;
        try {
            list.setSelectedVariant(variant);
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Change the label
            this.panel.remove(6);
            if (variant.getStats().getSlayer() == null) {
                this.panel.add(listVariantXpDistributionLabel, 6);
            }
            else {
                this.panel.add(listVariantCompletionTimeLabel, 6);
            }

            // Reload the view
            int variantCombatStyleTableIndex = Arrays.asList(this.panel.getComponents()).indexOf(this.listVariantCombatStyleTable);
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
        PluginRepository data = Cache.getRepository();
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
            Cache.save();
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
        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;

        SlayerMonsters m = list.getSelectedMonster();
        if (monster == null) return;
    
        // Get the variation percentages
        Map<Monsters, Integer> percentages = list.getVariations().get(m);

        // Get the variation order
        List<Monsters> order = Arrays.asList(list.getVariationOrders().get(m));

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
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Reload the view
            int variantTableIndex = Arrays.asList(this.panel.getComponents()).indexOf(this.listVariantTable);
            this.panel.remove(this.listVariantTable);
            this.listVariantTable = new VariantTable(this);
            this.panel.add(this.listVariantTable, variantTableIndex);
            this.listVariantTable.fillTableModel();
            this.listVariantTable.revalidate();
            this.listVariantTable.repaint();
            updateMasterTable();
            this.panel.revalidate();
            this.panel.repaint();
        }
    }

    public void setXpRate(CombatStyle combatStyle, int xpRate) {
        PluginRepository data = Cache.getRepository();
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
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();
            updateMasterTable();
        }
    }

    public void setCompletionTime(int minutes) {
        PluginRepository data = Cache.getRepository();
        SlayerListData list = data.getSlayer().getList();
        if (list == null) return;
        Monsters monster = list.getSelectedVariant();
        if (monster == null) return;

        list.getMonsterCompletionTimes().put(monster, minutes);

        try {
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.listVariantCombatStyleTable.fillTableModel();
            this.listVariantCombatStyleTable.revalidate();
            this.listVariantCombatStyleTable.repaint();
            updateMasterTable();
        }
    }

    public void setSelectedList(SlayerPlanningData planning, String listUuid) {
        PluginRepository data = Cache.getRepository();
        planning.setListUuid(listUuid);
        try {
            Cache.save();
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

    public void setListName(String name) {
        SlayerListData list = Cache.getRepository().getSlayer().getList();
        if (list == null) return;

        if (list.getName().equals(name)) return;

        list.setName(name);

        try {
            Cache.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.load();
        }
    }

    public void loadSlayerList() {
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
            ObjectMapper objectMapper = new ObjectMapper();
            File fileToLoad = fileChooser.getSelectedFile();
            SlayerListData data;

            String input = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                input += reader.readLine() + "\n";
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading slayer list: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            try {
                data = objectMapper.readValue(input, SlayerListData.class);
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Slayer list is corrupted: " + e.getMessage());
                return;
            }

            String uuid = data.getUuid();
            SlayerListData existingList = Cache.getRepository().getSlayer().findListByFileName(uuid);
            if (existingList != null) {
                int option = JOptionPane.showOptionDialog(
                    null,
                    "You are trying to import a slayer list that already exists, would you like to replace the existing slayer list, or duplicate this one into a new slayer list?",
                    "Slayer list exists",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[] { "Replace", "Duplicate", "Cancel" },
                    null
                );
                // Replace
                if (option == JOptionPane.YES_OPTION) {
                    int index = Cache.getRepository().getSlayer().getLists().indexOf(existingList);
                    Cache.getRepository().getSlayer().getLists().remove(existingList);
                    Cache.getRepository().getSlayer().getLists().add(index, data);
                    reload = true;
                }
                // Duplicate
                else if (option == JOptionPane.NO_OPTION) {
                    data.setUuid(UUID.randomUUID().toString());
                    Cache.getRepository().getSlayer().getLists().add(data);
                    reload = true;
                }
            }
            // Create new list
            else {
                Cache.getRepository().getSlayer().getLists().add(data);
                reload = true;
            }
        }

        if (reload) {
            try {
                Cache.save();
                load();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Could not load slayer list: " + ex.getMessage());
            }
        }
    }

    public void saveSlayerList(SlayerListData list) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
        chooser.setFileFilter(jsonFilter);

        // Set default file name and extension
        chooser.setSelectedFile(new File(list.getName() + ".json"));

        int userSelection = chooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".json")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                String jsonString = objectMapper.writeValueAsString(list);
                writer.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving slayer list: " + e.getMessage());
            }
        }
    }

    public void resetDefaultsForList(SlayerListData list) {
        boolean reload = false;
        int option = JOptionPane.showOptionDialog(
            null,
            "Are you sure you wish to reset the slayer list \"" + list.getName() + "\" to default settings? This can not be undone.",
            "Reset to default settings",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[] { "Yes", "No" },
            null
        );
        // Reset
        if (option == JOptionPane.YES_OPTION) {
            SlayerListData newList = new SlayerListData();
            newList.setName(list.getName());
            newList.setUuid(list.getUuid());
            int index = Cache.getRepository().getSlayer().getLists().indexOf(list);
            Cache.getRepository().getSlayer().getLists().remove(list);
            Cache.getRepository().getSlayer().getLists().add(index == -1 ? 0 : index, newList);
            reload = true;
        }
        // Reload
        if (reload) {
            try {
                Cache.save();
                load();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deleteList(SlayerListData list) {
        if (Cache.getRepository().getSlayer().getLists().size() == 1) return;
        boolean reload = false;
        int option = JOptionPane.showOptionDialog(
            null,
            "Are you sure you wish to delete the slayer list \"" + list.getName() + "\"? This can not be undone.",
            "Delete slayer list",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[] { "Yes", "No" },
            null
        );
        // Reset
        if (option == JOptionPane.YES_OPTION) {
            Cache.getRepository().getSlayer().getLists().remove(list);
            for (SlayerPlanningData planning : Cache.getRepository().getSlayer().getPlannings()) {
                if (planning.getListUuid().equals(list.getUuid())) {
                    planning.setListUuid(Cache.getRepository().getSlayer().getLists().get(Cache.getRepository().getSlayer().getLists().size() - 1).getUuid());
                }
            }
            reload = true;
        }
        // Reload
        if (reload) {
            try {
                Cache.save();
                load();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void duplicateList(SlayerListData list) {
        ObjectMapper objectMapper = new ObjectMapper();

        String input = "";
        SlayerListData newList;
        try {
            input = objectMapper.writeValueAsString(list);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        try {
            newList = objectMapper.readValue(input, SlayerListData.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        newList.setUuid(UUID.randomUUID().toString());
        Cache.getRepository().getSlayer().getLists().add(newList);
        try {
            Cache.save();
            load();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    public void editXPRate(int index, int xpRate) {
        System.out.println("Index: " + index + "; Xp rate: " + xpRate);
    }

    public void editPlanning(SlayerPlanningData planning, int startXp) {
        planning.setCreatedAt(System.currentTimeMillis());
        planning.setStartXP(startXp);
        updatePlannings();
    }

    public void deletePlanning(SlayerPlanningData planning) {
        if (Cache.getRepository().getSlayer().getPlannings().size() == 1) return;
        boolean reload = false;
        int option = JOptionPane.showOptionDialog(
            null,
            "Are you sure you wish to delete the planning item starting at: " + NumberFormat.getInstance(Locale.US).format(planning.getStartXP()) + " xp?",
            "Delete planning item",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[] { "Yes", "No" },
            null
        );
        // Reset
        if (option == JOptionPane.YES_OPTION) {
            Cache.getRepository().getSlayer().getPlannings().remove(planning);
            reload = true;
        }
        // Reload
        if (reload) {
            updatePlannings();
        }
    }

    public void updatePlannings() {
        PluginRepository data = Cache.getRepository();
        List<SlayerPlanningData> delete = new ArrayList<>();
        data.getSlayer().getPlannings().sort(new Comparator<SlayerPlanningData>() {
            @Override
            public int compare(SlayerPlanningData m1, SlayerPlanningData m2) {
                int compare1 = Integer.compare(m1.getStartXP(), m2.getStartXP());
                if (compare1 == 0) {
                    int compare2 = Long.compare(m2.getCreatedAt(), m1.getCreatedAt());
                    if (compare2 < 0) {
                        delete.add(m2);
                    }
                    else {
                        delete.add(m1);
                    }
                    return compare2;
                }
                return compare1;
            }
        });
        for (SlayerPlanningData d : delete) {
            data.getSlayer().getPlannings().remove(d);
        }
        if (data.getSlayer().getPlannings().get(0).getStartXP() > 0) {
            SlayerPlanningData planning = new SlayerPlanningData();
            planning.setStartXP(0);
            planning.setListUuid(data.getSlayer().getLists().get(data.getSlayer().getLists().size() - 1).getUuid());
            data.getSlayer().getPlannings().add(0, planning);
        }
        for (int i = 0; i < data.getSlayer().getPlannings().size(); i ++) {
            SlayerPlanningData planning = data.getSlayer().getPlannings().get(i);
            SlayerPlanningData nextPlanning = i + 1 < data.getSlayer().getPlannings().size()
                ? data.getSlayer().getPlannings().get(i + 1)
                : null;
            if (nextPlanning == null) {
                planning.setEndXP(200_000_000);
            }
            else {
                planning.setEndXP(nextPlanning.getStartXP());
            }
        }
        try {
            Cache.save();
            load();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JComboBox<?> createListComboBox() {
        PluginRepository data = Cache.getRepository();
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
                    label.setIcon(Resources.loadImageIcon(value.list.getMaster().getIconPath().getFileName(), 18, 18));
                }
                else if (value.view != null) {
                    label.setText(value.view.getName());
                    label.setIcon(Resources.loadImageIcon(value.view.getIconPath().getPath(), 18, 18));
                }

                label.setOpaque(true);

                if (
                    value == null || (
                        (value.list != null && value.list == Cache.getRepository().getSlayer().getList())
                        || (value.view != null && value.view == Cache.getRepository().getSlayer().getView())
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
                PluginRepository data = Cache.getRepository();

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
                    Cache.save();
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
        PluginRepository data = Cache.getRepository();
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

    private JComboBox<SlayerSubViewNames> createListManageComboBox() {
        PluginRepository data = Cache.getRepository();
        SlayerListData slayerList = data.getSlayer().getList();
        if (slayerList == null) return null;

        SlayerSubViewNames subView = slayerList.getSubView();

        List<SlayerSubViewNames> comboBoxValues = new ArrayList<>();
        for (SlayerSubViewNames s : SlayerSubViewNames.values()) {
            comboBoxValues.add(s);
        }

        JComboBox<SlayerSubViewNames> cb = new JComboBox<>(comboBoxValues.toArray(new SlayerSubViewNames[comboBoxValues.size()])) {
            @Override
            public String getToolTipText(MouseEvent e) {
                SlayerSubViewNames item = (SlayerSubViewNames)getSelectedItem();
                return item == null
                    ? null
                    : item.getDescription();
            }
        };
        cb.setSelectedItem(subView);

        cb.setRenderer(new ListCellRenderer<SlayerSubViewNames>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends SlayerSubViewNames> list,
                    SlayerSubViewNames value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = new JLabel();

                if (value == null) {
                    label.setText("Select an option");
                }
                else {
                    label.setText(value.getName());
                    label.setIcon(Resources.loadImageIcon(value.getIconPath().getPath(), 18, 18));
                }

                label.setOpaque(true);

                if (
                    value == null || value == data.getSlayer().getList().getSubView()
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
                SlayerSubViewNames selectedOption = (SlayerSubViewNames)cb.getSelectedItem();

                if (selectedOption != null && selectedOption != data.getSlayer().getList().getSubView()) {
                    data.getSlayer().getList().setSubView(selectedOption);
                    try {
                        Cache.save();
                        load();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        return cb;
    } 

    private JComboBox<CombatStyle> createListManageXpRatesComboBox() {
        PluginRepository data = Cache.getRepository();
        SlayerListData slayerList = data.getSlayer().getList();
        if (slayerList == null) return null;

        CombatStyle filter = slayerList.getCombatStyleFilter();

        List<CombatStyle> comboBoxValues = new ArrayList<>();
        for (CombatStyle style : new CombatStyle[] {
            CombatStyle.DefenceLast,
            CombatStyle.Controlled,
            CombatStyle.Attack,
            CombatStyle.Strength,
            CombatStyle.Defence,
            CombatStyle.Ranged,
            CombatStyle.RangedDefensive,
            CombatStyle.RegChinsShort,
            CombatStyle.RegChinsMedium,
            CombatStyle.RegChinsLong,
            CombatStyle.RedChinsShort,
            CombatStyle.RedChinsMedium,
            CombatStyle.RedChinsLong,
            CombatStyle.BlackChinsShort,
            CombatStyle.BlackChinsMedium,
            CombatStyle.BlackChinsLong,
            CombatStyle.Magic,
            CombatStyle.IceBurst,
            CombatStyle.IceBurstDefensive,
            CombatStyle.BloodBurst,
            CombatStyle.BloodBurstDefensive,
            CombatStyle.SmokeBurst,
            CombatStyle.SmokeBurstDefensive,
            CombatStyle.ShadowBurst,
            CombatStyle.ShadowBurstDefensive,
            CombatStyle.IceBarrage,
            CombatStyle.IceBarrageDefensive,
            CombatStyle.BloodBarrage,
            CombatStyle.BloodBarrageDefensive,
            CombatStyle.SmokeBarrage,
            CombatStyle.SmokeBarrageDefensive,
            CombatStyle.ShadowBarrage,
            CombatStyle.ShadowBarrageDefensive
        }) {
            for (Monsters monster : Monsters.values()) {
                if (monster == Monsters.Jad || monster == Monsters.Zuk) continue;
                CombatStyle melee = slayerList.getMeleeStyles().get(monster);
                CombatStyle ranged = slayerList.getRangedStyles().get(monster);
                CombatStyle magic = slayerList.getMagicStyles().get(monster);
                if (melee == style) {
                    Integer hourlyRate = slayerList.getMeleeHourlyRates().get(monster);
                    if (hourlyRate > 0) {
                        comboBoxValues.add(style);
                        break;
                    }
                }
                else if (ranged == style) {
                    Integer hourlyRate = slayerList.getRangedHourlyRates().get(monster);
                    if (hourlyRate > 0) {
                        comboBoxValues.add(style);
                        break;
                    }
                }
                else if (magic == style) {
                    Integer hourlyRate = slayerList.getMagicHourlyRates().get(monster);
                    if (hourlyRate > 0) {
                        comboBoxValues.add(style);
                        break;
                    }
                }
            }
        }

        JComboBox<CombatStyle> cb = new JComboBox<>(comboBoxValues.toArray(new CombatStyle[comboBoxValues.size()])) {
            @Override
            public String getToolTipText(MouseEvent e) {
                CombatStyle item = (CombatStyle)getSelectedItem();
                return item == null
                    ? null
                    : item.getName();
            }
        };
        cb.setSelectedItem(filter);

        cb.setRenderer(new ListCellRenderer<CombatStyle>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends CombatStyle> list,
                    CombatStyle value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = new JLabel();

                if (value == null) {
                    label.setText("Select an option");
                }
                else {
                    label.setText(value.getName());
                    label.setIcon(Resources.loadImageIcon(value.getIconPath().getPath(), 18, 18));
                }

                label.setOpaque(true);

                if (
                    value == null || value == slayerList.getCombatStyleFilter()
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
                CombatStyle selectedOption = (CombatStyle)cb.getSelectedItem();

                if (selectedOption != null && selectedOption != slayerList.getCombatStyleFilter()) {
                    data.getSlayer().getList().setCombatStyleFilter(selectedOption);
                    try {
                        Cache.save();
                        load();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        return cb;
    } 

    public SlayerListCalculator getCalculator() {
        return SlayerListCalculator.getInstance(Cache.getRepository().getSlayer().getListUuid());
    }

    public Object[][] getMasterTableRows() {
        SlayerListCalculator calculator = getCalculator();
        return new Object[][] {
            {
                new JLabel("Duradel"),
                new JLabel("Nieve")
            },
            {
                new JLabel("Points per task"),
                new JLabel(Float.toString(Math.round(calculator.getPointsPerTaskIncludingSkips() * 100) / 100))
            },
            {
                new JLabel("Skip percentage"),
                new JLabel(
                    Float.toString(Math.round(calculator.getSkipPercentage() * 100) / 100)
                    + "% / "
                    + (Float.toString(Math.round(calculator.getBreakEvenSkipPercentage() * 100) / 100)) + "%")
            },
            {
                new JLabel("XP rate"),
                new JLabel("44.992/h")
            },
            {
                new JLabel("Hours left"),
                new JLabel("1.337")
            }
        };
    }

    public void updateMasterTable() {
        DefaultTableModel model = (DefaultTableModel)this.listMasterTable.getModel();
        Object[][] rows = getMasterTableRows();
        model.setValueAt(rows[1][1], 2, 1);
        model.setValueAt(rows[2][1], 3, 1);
        model.setValueAt(rows[3][1], 4, 1);
        model.setValueAt(rows[4][1],5, 1);
        this.listMasterTable.revalidate();
        this.listMasterTable.repaint();
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
