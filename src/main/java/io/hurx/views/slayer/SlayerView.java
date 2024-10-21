package io.hurx.views.slayer;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.PlainLabel;
import io.hurx.components.Table;
import io.hurx.components.TitleLabel;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import io.hurx.views.slayer.components.SlayerMasterTable;
import io.hurx.views.slayer.components.SlayerTasksTable;
import net.runelite.api.annotations.Component;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The slayer view
 */
public class SlayerView extends View {
    public SlayerView(SkillingPlannerPanel panel) {
        super(ViewNames.Slayer, panel);        
    }

    @Override
    public void load() {
        panel.add(panel.createSelect());
        
        panel.add(new TitleLabel("Slayer master"));
        panel.add(new SlayerMasterTable(panel));

        panel.add(new TitleLabel("Tasks"));
        panel.add(new SlayerTasksTable(panel));

        panel.add(new TitleLabel("Loot"));

        panel.revalidate();
        panel.repaint();
    }
}
