package io.hurx.models;

import io.hurx.SkillingPlannerPanel;

/**
 * A view in the panel
 */
public abstract class View {
    public ViewNames getViewName() {
        return viewName;
    }
    
    /**
     * The view
     */
    private final ViewNames viewName;

    /**
     * The skilling planner panel
     * @return
     */
    public SkillingPlannerPanel getPanel() {
        return panel;
    }

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
