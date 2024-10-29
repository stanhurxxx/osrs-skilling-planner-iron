package io.hurx.views.slayer;

import io.hurx.models.IconPaths;

/**
 * All the views
 */
public enum SlayerSubViewNames {
    ManageTasks("Tasks", "Manage your slayer tasks", IconPaths.SkillEHP),
    ManageXPRates("XP rates", "Change XP rates from monster variants all at once", IconPaths.SkillOverall),
    ManageOptions("Options", "Manage your slayer list options", IconPaths.MenuSettings),
    ;

    // Getter method to access the string value
    public String getName() {
        return name;
    }

    // A field to store the string value
    private final String name;

    public String getDescription() {
        return description;
    }

    private final String description;

    public IconPaths getIconPath() {
        return iconPath;
    }

    private final IconPaths iconPath;

    // Constructor for the enum
    SlayerSubViewNames(String displayName, String description, IconPaths iconPath) {
        this.name = displayName;
        this.description = description;
        this.iconPath = iconPath;
    }


    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
