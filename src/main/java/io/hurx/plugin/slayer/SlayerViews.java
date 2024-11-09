package io.hurx.plugin.slayer;

import io.hurx.models.IconPaths;
import io.hurx.models.views.Views;

/**
 * Represents all the views available in the Slayer plugin.
 * <p>
 * This enum defines various views associated with the Slayer functionality,
 * providing a name and an icon path for each view.
 * </p>
 */
public enum SlayerViews implements Views {
    /**
     * Overview view, displaying a general summary of Slayer tasks.
     */
    Overview("Overview", IconPaths.SkillOverall),
    
    /**
     * Planner view, allowing users to plan their Slayer tasks and strategies.
     */
    Planner("Planner", IconPaths.SkillEHP),

    /** When a slayer list is selected and not a view */
    List(null, null);

    // A field to store the string value of the view name
    private final String name;

    // A field to store the icon path associated with the view
    private IconPaths iconPath;

    /**
     * Retrieves the name of the view.
     *
     * @return the name of the view as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the icon path associated with the view.
     *
     * @return the icon path as an {@link IconPaths} enum
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    /**
     * Constructor for the enum.
     *
     * @param displayName the name of the view
     * @param iconPath    the icon path for the view
     */
    SlayerViews(String displayName, IconPaths iconPath) {
        this.name = displayName;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
