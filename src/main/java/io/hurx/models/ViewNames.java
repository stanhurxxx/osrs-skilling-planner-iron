package io.hurx.models;

/**
 * All the views
 */
public enum ViewNames {
    Overview("Overview"),
    Slayer("Slayer"),
    Skills("Skills"),
    ProfitLoss("Profit/loss"),
    Hourlies("Hourlies"),
    LoggedOut("Logged out");

    // A field to store the string value
    private final String displayName;

    // Constructor for the enum
    ViewNames(String displayName) {
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
