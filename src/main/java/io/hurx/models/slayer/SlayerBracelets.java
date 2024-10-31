package io.hurx.models.slayer;

import io.hurx.models.items.Items;

/**
 * Enum representing different Slayer bracelets and their effects.
 * Each bracelet may provide specific benefits during Slayer assignments.
 */
public enum SlayerBracelets {
    
    /** No bracelet equipped. */
    None(),
    
    /**
     * Bracelet of Slaughter, which allows for extra monsters per task.
     */
    Slaughter(Items.BraceletOfSlaughter),
    
    /**
     * Expeditious Bracelet, which reduces the number of monsters in a task.
     */
    Expeditious(Items.ExpeditiousBracelet);

    /** The item associated with this bracelet. */
    private Items item;

    /**
     * Default constructor for the enum constant without an associated item.
     */
    SlayerBracelets() {}

    /**
     * Constructor for the enum constant with an associated item.
     *
     * @param item The item associated with this bracelet.
     */
    SlayerBracelets(Items item) {
        this.item = item;
    }

    /**
     * Gets the item associated with this bracelet.
     *
     * @return The associated item, or null if no item is associated.
     */
    public Items getItem() {
        return item;
    }

    /**
     * Returns a string representation of the bracelet.
     *
     * @return The name of the associated item if present, otherwise "None".
     */
    @Override
    public String toString() {
        return item == null ? "None" : item.getItem().getName();
    }
}
