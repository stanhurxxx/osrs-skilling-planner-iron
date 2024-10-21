package io.hurx.components;

import javax.swing.*;
import io.hurx.SkillingPlannerPanel;
import io.hurx.cache.exceptions.PlayerNotFoundException;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import io.hurx.views.LoggedOutView;
import io.hurx.views.OverviewView;
import io.hurx.views.slayer.SlayerView;

public class Router extends JPanel {
    /**
     * Ref to the panel
     */
    private SkillingPlannerPanel panel;

    /**
     * Gets all the views
     * @return
     */
    public View[] getViews() {
        return this.views;
    }

    /**
     * The views
     */
    private View[] views;

    /**
     * Get the current view.
     * @return
     */
    public View getView() {
        return view;
    }

    /**
     * The view
     */
    private View view = null;

    public Router(SkillingPlannerPanel panel) {
        super();
        this.panel = panel;
        views = new View[] {
            new OverviewView(panel),
            new LoggedOutView(panel),
            new SlayerView(panel)
        };
    }

    /**
     * Navigates to a view.
     * @param view
     * @throws PlayerNotFoundException
     */
    public void navigate(ViewNames view) {
        System.out.println(view);
        try {
            View v = null;
            if (this.view != null) {
                this.view.cleanUp();
            }
            for (View vv : views) {
                if (vv.viewName == view) {
                    this.view = vv;
                    vv.load();
                    v = vv;
                    panel.revalidate();
                    panel.repaint();
                }
            }
            if (this.view.viewName != ViewNames.LoggedOut) {
                panel.getCache().getData().view = this.view.viewName;
                if (panel.getCache().getFileName() != null) {
                    panel.getCache().save();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
