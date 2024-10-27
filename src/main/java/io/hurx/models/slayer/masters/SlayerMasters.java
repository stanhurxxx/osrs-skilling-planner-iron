package io.hurx.models.slayer.masters;

public enum SlayerMasters {
    Duradel("Duradel", new Duradel()),
    Nieve("Nieve", new Nieve());

    // A field to store the string value
    private final String displayName;

    private final SlayerMaster master;

    // Constructor for the enum
    SlayerMasters(String displayName, SlayerMaster master) {
        this.displayName = displayName;
        this.master = master;
    }

    /**
     * The slayer master
     * @return
     */
    public SlayerMaster getMaster() {
        return master;
    }

    // Getter method to access the string value
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName; // Override toString to return the string value
    }
}
