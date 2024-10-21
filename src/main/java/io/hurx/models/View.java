package io.hurx.models;

import io.hurx.SkillingPlannerPanel;

/**
 * A view in the panel
 */
public abstract class View {
    /**
     * The view
     */
    public final ViewNames viewName;

    /**
     * Ref to the panel
     */
    protected SkillingPlannerPanel panel;

    public View(ViewNames viewName, SkillingPlannerPanel panel) {
        this.viewName = viewName;
        this.panel = panel;
    }

    /**
     * Loads the view
     */
    public abstract void load();

    /**
     * Cleans up the view
     */
    public void cleanUp() {
        panel.removeAll();
        panel.revalidate();
    }
}
