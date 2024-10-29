package io.hurx.models.slayer.masters;

import io.hurx.models.IconPaths;

public enum SlayerMasters {
    Duradel("Duradel", new Duradel(), IconPaths.NPCDuradel),
    Nieve("Nieve", new Nieve(), IconPaths.NPCNieve);

    // Getter method to access the string value
    public String getName() {
        return name;
    }

    // A field to store the string value
    private final String name;

    /**
     * The slayer master
     * @return
     */
    public SlayerMaster getMaster() {
        return master;
    }

    private final SlayerMaster master;

    public IconPaths getIconPath() {
        return iconPath;
    }

    private final IconPaths iconPath;

    // Constructor for the enum
    SlayerMasters(String displayName, SlayerMaster master, IconPaths iconPath) {
        this.name = displayName;
        this.master = master;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
