package io.hurx.plugin.profile.slayer.views.list;

import io.hurx.models.IconPaths;
import io.hurx.models.views.Views;

/**
 * Enumeration representing all the views in the SlayerListView.
 * <p>
 * Each view provides a name, description, and an associated icon
 * that represents different functionalities related to managing Slayer tasks.
 * </p>
 */
public enum SlayerListViews implements Views {
    /**
     * View for managing Slayer tasks.
     */
    ManageTasks("Tasks", "Manage your slayer tasks", IconPaths.SkillEHP),
    
    /**
     * View for changing XP rates from monster variants all at once.
     */
    ManageXPRates("XP rates", "Change XP rates from monster variants all at once", IconPaths.SkillOverall),
    
    /**
     * View for managing options related to the Slayer list.
     */
    ManageOptions("Options", "Manage your slayer list options", IconPaths.MenuSettings);

    // A field to store the string value for the view name
    private final String name;

    // A field to store the description of the view
    private final String description;

    // A field to store the icon path associated with the view
    private final IconPaths iconPath;

    /**
     * Constructs a SlayerListViews enum instance with a name, description, and associated icon path.
     *
     * @param displayName the name of the Slayer view
     * @param description a description of the view's functionality
     * @param iconPath the icon path associated with the view
     */
    SlayerListViews(String displayName, String description, IconPaths iconPath) {
        this.name = displayName;
        this.description = description;
        this.iconPath = iconPath;
    }

    /**
     * Gets the name of the Slayer view.
     *
     * @return the name of the Slayer view
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the Slayer view.
     *
     * @return the description of the Slayer view
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the icon path associated with the Slayer view.
     *
     * @return the icon path of the Slayer view
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the name of the view
    }
}
