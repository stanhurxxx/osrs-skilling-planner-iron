package io.hurx.views;

import io.hurx.Resources;
import io.hurx.SkillingPlannerPanel;
import io.hurx.Theme;
import io.hurx.components.Table;
import io.hurx.components.TitleLabel;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import javax.swing.*;

/**
 * The overview view
 */
public class OverviewView extends View {
    public OverviewView(SkillingPlannerPanel panel) {
        super(ViewNames.Overview, panel);        
    }

    @Override
    public void load() {
        panel.add(panel.createSelect());

        panel.add(new TitleLabel("Hourlies"));
        panel.add(new Table(
            panel,
            new Object[][] {
                {
                    Resources.loadImageIcon("icons/herb.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Herb runs"),
                    new JLabel("12.019 left")
                },
                {
                    Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Contracts"),
                    new JLabel("12.019 left")
                },
                {
                    Resources.loadImageIcon("icons/birdhouse.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Birdhouses"),
                    new JLabel("12.019 left")
                },
                {
                    Resources.loadImageIcon("icons/seaweed.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Seaweed"),
                    new JLabel("12.019 left")
                }
            }
        ));

        panel.add(new TitleLabel("Money"));
        panel.add(new Table(
            panel,
            new Object[][] {
                {
                    Resources.loadImageIcon("icons/herb.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Profit"),
                    new JLabel("5.01B")
                },
                {
                    Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Profit (drop trade)"),
                    new JLabel("1.5B")
                },
                {
                    Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Costs"),
                    new JLabel("3.5B")
                },
                {
                    Resources.loadImageIcon("icons/seed-pack.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Spare money"),
                    new JLabel("3.01B")
                }
            }
        ));

        panel.add(new TitleLabel("Stats"));
        panel.add(new Table(
            panel,
            new Object[][] {
                {
                    Resources.loadImageIcon("icons/slayer.png", Theme.ICON_SIZE, Theme.ICON_SIZE),
                    new JLabel("Slayer"),
                    new JLabel("983.05 EHP")
                }
            }
        ));
    }
}