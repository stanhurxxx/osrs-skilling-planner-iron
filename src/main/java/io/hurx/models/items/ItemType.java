package io.hurx.models.items;

/**
 * Enumeration representing the different types of items in the game.
 * Each type corresponds to specific behaviors or uses of the item.
 */
public enum ItemType {
    
    /**
     * Represents a resource item type, typically used for crafting or other 
     * resource-related purposes.
     */
    Resource,

    /**
     * Represents an item that can be alchemized for gold, converting it into 
     * a monetary value when used.
     */
    Alch,

    /**
     * Represents an item that is dropped to the main inventory during gameplay,
     * allowing the player to collect it.
     */
    DropToMain,

    /**
     * Represents a meta item type, which may have special characteristics 
     * or uses beyond normal item interactions.
     */
    Meta
}
