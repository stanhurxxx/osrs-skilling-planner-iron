package io.hurx.models.slayer.masters;

public enum SlayerMasters {
    Duradel("Duradel"),
    Nieve("Nieve");

    // A field to store the string value
    private final String displayName;

    // Constructor for the enum
    SlayerMasters(String displayName) {
        this.displayName = displayName;
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
