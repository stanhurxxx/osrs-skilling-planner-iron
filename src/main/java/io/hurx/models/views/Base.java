package io.hurx.models.views;

import io.hurx.plugin.Plugin;

public class Base {
    /**
     * Retrieves the root of the view hierarchy.
     *
     * @return the root panel.
     */
    public final Plugin getRoot() {
        return root;
    }

    /** The root panel of the view hierarchy. */
    protected final Plugin root;

    /**
     * Constructs an Element with the specified root.
     *
     * @param root the root panel for this element.
     */
    public Base(Plugin root) {
        this.root = root;
    }
}