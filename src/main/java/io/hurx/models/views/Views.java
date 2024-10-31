package io.hurx.models.views;

import io.hurx.models.IconPaths;

/**
 * Represents a view within a {@link View.Master} class in the application.
 * <p>
 * This interface defines the essential characteristics of a view,
 * including its name and associated icon. Implementing classes should
 * provide specific implementations that represent various views in the
 * application's user interface.
 * </p>
 */
public interface Views {
    /**
     * Retrieves the name of the view.
     *
     * @return a string representing the name of the view.
     * This name is typically displayed in user interface components,
     * such as navigation menus or tabs.
     */
    public String getName();

    /**
     * Retrieves the icon path associated with the view.
     *
     * @return an {@link IconPaths} object representing the icon
     * associated with this view. The icon is used for visual
     * representation within the user interface, providing a
     * quick visual reference to users.
     */
    public IconPaths getIconPath();
}
