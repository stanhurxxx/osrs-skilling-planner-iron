package io.hurx.views.slayer;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.PlainLabel;
import io.hurx.components.Table;
import io.hurx.components.TitleLabel;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import io.hurx.models.slayer.masters.Duradel;
import io.hurx.models.slayer.masters.Nieve;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.views.slayer.components.SlayerMasterTable;
import io.hurx.views.slayer.components.SlayerTasksTable;
import io.hurx.views.slayer.components.SlayerTasksTitleTable;
import io.hurx.views.slayer.components.SlayerVariantTable;
import io.hurx.views.slayer.components.SlayerVariantCombatStyleTable;
import net.runelite.api.annotations.Component;
import io.hurx.components.JNumberSlider;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The slayer view
 */
public class SlayerView extends View {
    public SkillingPlannerPanel getPanel() {
        return panel;
    }

    /**
     * Get the slayer monsters
     * @return
     */
    public SlayerMonster[] getSlayerMonsters() {
        SlayerMasters master = panel.getCache().getData().slayerMaster;
        return master == SlayerMasters.Duradel ? new Duradel().getAssignments() : new Nieve().getAssignments();
    }

    /**
     * Ref to the tasks title table
     */
    private SlayerTasksTitleTable tasksTitleTable;

    /**
     * Ref to the tasks table
     */
    private SlayerTasksTable tasksTable;

    private SlayerVariantTable variantTable;
    private PlainLabel variantCombatStyle = new PlainLabel("Combat style");
    private SlayerVariantCombatStyleTable variantCombatStyleTable;
    
    private TitleLabel xpDistribution = new TitleLabel("XP distribution");

    private TitleLabel loot = new TitleLabel("Loot"); 

    public SlayerView(SkillingPlannerPanel panel) {
        super(ViewNames.Slayer, panel);        
        tasksTitleTable = new SlayerTasksTitleTable(this, panel);
        tasksTable = new SlayerTasksTable(this, panel);
        variantTable = new SlayerVariantTable(this, panel);
        variantCombatStyleTable = new SlayerVariantCombatStyleTable(this, panel);
        panel.setOpaque(true);
        panel.setBackground(Theme.BG_COLOR);
        panel.setDoubleBuffered(true);
    }

    @Override
    public void load() {
        panel.removeAll();

        panel.add(panel.createSelect());
        
        panel.add(new TitleLabel("Slayer master"));
        panel.add(new SlayerMasterTable(panel));

        panel.add(tasksTitleTable);

        SlayerMonsters monster = panel.getCache().getData().slayerSelectedMonster;
        if (monster == null) {
            panel.add(tasksTable);
        }
        else {
            panel.add(variantTable);
        }

        for (int i = 0; i < panel.getComponentCount(); i ++) {
            panel.setComponentZOrder(panel.getComponent(i), i);
        }

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Sets the selected slayer monster
     * @param monster the monster
     */
    public void setSelectedMonster(SlayerMonsters monster) {
        panel.getCache().getData().slayerSelectedMonster = monster;
        panel.getCache().getData().slayerSelectedVariant = monster;
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
                panel.add(variantCombatStyle);
                panel.add(variantCombatStyleTable);
                variantTable.fillTableModel();
                variantCombatStyleTable.fillTableModel();
            }
            panel.add(xpDistribution);
            panel.add(loot);
            for (int i = 4; i < panel.getComponentCount(); i ++) {
                panel.setComponentZOrder(panel.getComponent(i), i);
            }
            panel.revalidate();
            panel.repaint();
        }
    }
}
