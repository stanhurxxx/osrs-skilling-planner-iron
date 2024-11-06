package io.hurx.models;

/**
 * Enum representing the various menu buttons used in the application.
 * Each button has a name for display and an associated icon path.
 */
public enum MenuButtons {
    Add("Add new", IconPaths.MenuAdd),
    Back("Go back", IconPaths.MenuBack),
    Filter("Filters", IconPaths.MenuFilter),
    Settings("Settings", IconPaths.MenuSettings),
    Edit("Edit", IconPaths.MenuEdit),
    Upload("Import", IconPaths.MenuUpload),
    Download("Export", IconPaths.MenuDownload),
    Reset("Reset defaults", IconPaths.MenuReset),
    Duplicate("Duplicate", IconPaths.MenuDuplicate),
    Delete("Delete", IconPaths.MenuDelete),
    Save("Save", IconPaths.MenuSave),
    ;

    /**
     * The display name of the menu button.
     */
    private final String name;

    /**
     * The icon path associated with the menu button.
     */
    private final IconPaths iconPath;

    /**
     * Constructor to initialize the menu button with its name and associated icon path.
     *
     * @param name     the display name of the button
     * @param iconPath the icon path associated with the button
     */
    MenuButtons(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    /**
     * Retrieves the display name of the menu button.
     *
     * @return the name of the menu button as a String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the icon path associated with the menu button.
     *
     * @return the IconPaths enum constant representing the icon path
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
