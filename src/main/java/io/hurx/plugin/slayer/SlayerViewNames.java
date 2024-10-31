package io.hurx.plugin.slayer;

import io.hurx.models.IconPaths;

/**
 * All the views
 */
public enum SlayerViewNames {
    Overview("Overview", IconPaths.SkillOverall),
    Planner("Planner", IconPaths.SkillEHP),
    ;

    // Getter method to access the string value
    public String getName() {
        return name;
    }

    // A field to store the string value
    private final String name;

    public IconPaths getIconPath() {
        return iconPath;
    }

    private IconPaths iconPath;

    // Constructor for the enum
    SlayerViewNames(String displayName, IconPaths iconPath) {
        this.name = displayName;
        this.iconPath = iconPath;
    }


    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
