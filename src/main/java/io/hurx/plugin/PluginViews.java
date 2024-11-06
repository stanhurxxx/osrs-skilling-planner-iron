package io.hurx.plugin;

import io.hurx.models.IconPaths;
import io.hurx.models.views.Views;

/**
 * Represents all the views available in the Ironman Skilling Planner plugin.
 * Each view is associated with a name and an icon path.
 */
public enum PluginViews implements Views {
    Profile(null, null);

    /** 
     * The name of the view.
     */
    private final String name;

    /** 
     * The icon path associated with the view.
     */
    private final IconPaths iconPath;

    /**
     * Constructs a PluginViews enum constant with the specified name and icon path.
     *
     * @param name the name of the view.
     * @param iconPath the icon path associated with the view.
     */
    PluginViews(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    /**
     * Gets the parent view of this view.
     * 
     * @return null, as this implementation does not have parent views.
     */
    public Views getParent() {
        return null;
    }

    /**
     * Gets the name of the view.
     *
     * @return the name of the view.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the icon path associated with the view.
     *
     * @return the icon path of the view.
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    /**
     * Returns a string representation of the view, which is its name.
     *
     * @return the name of the view.
     */
    @Override
    public String toString() {
        return name;
    }
}
