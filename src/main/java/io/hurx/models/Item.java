package io.hurx.models;

public enum Item {
    RunePlatebody("Rune platebody");

    private final String displayName;

    Item(String displayName) {
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
