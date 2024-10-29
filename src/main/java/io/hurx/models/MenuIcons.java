package io.hurx.models;

public enum MenuIcons {
    Add("Add new", IconPaths.MenuAdd),
    Back("Go back", IconPaths.MenuBack),
    Filter("Filters", IconPaths.MenuFilter),
    Settings("Settings", IconPaths.MenuSettings),
    Edit("Edit", IconPaths.MenuEdit),
    ;

    public String getName() {
        return this.name;
    }
    private String name;

    public IconPaths getIconPath() {
        return iconPath;
    }
    private IconPaths iconPath;

    MenuIcons(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
