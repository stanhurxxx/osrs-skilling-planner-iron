package io.hurx.models.slayer.masters;

import io.hurx.models.IconPaths;

/**
 * Enum representing different Slayer Masters in the game.
 * Each Slayer Master has a name, a corresponding instance of a SlayerMaster, 
 * and an icon path for visual representation.
 */
public enum SlayerMasters {
    /** 
     * Slayer Master Duradel with its name, master instance, and icon path.
     */
    Duradel("Duradel", new Duradel(), IconPaths.NPCDuradel),

    /** 
     * Slayer Master Nieve with its name, master instance, and icon path.
     */
    Nieve("Nieve", new Nieve(), IconPaths.NPCNieve);

    /** 
     * The name of the Slayer Master. 
     */
    private final String name;

    /** 
     * The corresponding SlayerMaster instance.
     */
    private final SlayerMaster master;

    /** 
     * The icon path for the Slayer Master.
     */
    private final IconPaths iconPath;

    /**
     * Constructor for the SlayerMasters enum.
     *
     * @param displayName The name of the Slayer Master to be displayed.
     * @param master The corresponding SlayerMaster instance.
     * @param iconPath The icon path associated with the Slayer Master.
     */
    SlayerMasters(String displayName, SlayerMaster master, IconPaths iconPath) {
        this.name = displayName;
        this.master = master;
        this.iconPath = iconPath;
    }

    /**
     * Gets the name of the Slayer Master.
     *
     * @return The name of the Slayer Master as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the corresponding SlayerMaster instance.
     *
     * @return The SlayerMaster associated with this enum constant.
     */
    public SlayerMaster getMaster() {
        return master;
    }

    /**
     * Gets the icon path for the Slayer Master.
     *
     * @return The icon path associated with the Slayer Master.
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the Slayer Master's name
    }
}
