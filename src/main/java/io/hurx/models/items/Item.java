package io.hurx.models.items;

import javax.swing.ImageIcon;

/**
 * The Item class represents an item with an ID, name, price, icon, and additional attributes.
 * It provides methods to get and set various properties of the item, including its type
 * and how it interacts with a slayer task.
 */
public class Item {
    /**
     * The unique identifier for the item.
     */
    private int id;

    /**
     * The name of the item.
     */
    private String name = "To be loaded";

    /**
     * The price of the item.
     */
    private int price = 0;

    /**
     * The icon representing the item.
     */
    private ImageIcon icon;

    /**
     * The ID used for pricing the item.
     */
    private Integer priceItemId = null;

    /**
     * An array of component IDs related to the item's pricing.
     */
    private int[] priceItemComponentIds = new int[] {};

    /**
     * The noted ID of the item, if applicable.
     */
    private Integer notedId;

    /**
     * The type of the item.
     */
    private ItemType type = ItemType.Resource;

    /**
     * Constructs an Item with the specified ID.
     *
     * @param id The unique identifier for the item.
     */
    private Item(int id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier for the item.
     *
     * @return The item's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the icon representing the item.
     *
     * @param icon The icon to be set for the item.
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    /**
     * Gets the icon representing the item.
     *
     * @return The item's icon.
     */
    public ImageIcon getIcon() {
        return this.icon;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The name to be set for the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the item.
     *
     * @return The item's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the price of the item.
     *
     * @param price The price to be set for the item.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the price of the item.
     *
     * @return The item's price.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Gets the price item ID related to the item.
     *
     * @return The price item ID, or null if not set.
     */
    public Integer getPriceItemId() {
        return this.priceItemId;
    }

    /**
     * Gets the array of component IDs related to the item's pricing.
     *
     * @return An array of component IDs.
     */
    public int[] getPriceItemComponentIds() {
        return priceItemComponentIds;
    }

    /**
     * Gets the noted ID of the item.
     *
     * @return The noted ID, or null if not set.
     */
    public Integer getNotedId() {
        return this.notedId;
    }

    /**
     * Sets the noted ID for the item.
     *
     * @param notedId The noted ID to be set.
     */
    public void setNotedId(Integer notedId) {
        this.notedId = notedId;
    }

    /**
     * Gets the type of the item.
     *
     * @return The item's type.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * A builder class for constructing Item instances.
     */
    public static class Builder {
        private Item item;

        /**
         * Private constructor for the Builder class.
         *
         * @param item The Item instance to be built.
         */
        private Builder(Item item) {
            this.item = item;
        }

        /**
         * Starts the building process with the specified item ID.
         *
         * @param id The unique identifier for the item.
         * @return A Builder instance for chaining.
         */
        public static Builder id(int id) {
            Builder builder = new Builder(new Item(id));
            return builder;
        }

        /**
         * Sets the type of the item in the builder.
         *
         * @param type The type to be set.
         * @return The current Builder instance for method chaining.
         */
        public Builder type(ItemType type) {
            this.item.type = type;
            return this;
        }

        /**
         * Sets the price item ID in the builder.
         *
         * @param priceItemId The price item ID to be set.
         * @return The current Builder instance for method chaining.
         */
        public Builder priceItemId(int priceItemId) {
            this.item.priceItemId = priceItemId;
            return this;
        }

        /**
         * Sets the array of price item component IDs in the builder.
         *
         * @param priceItemComponentIds The component IDs to be set.
         * @return The current Builder instance for method chaining.
         */
        public Builder priceItemComponentIds(int[] priceItemComponentIds) {
            this.item.priceItemComponentIds = priceItemComponentIds;
            return this;
        }

        /**
         * Builds and returns the constructed Item instance.
         *
         * @return The constructed Item instance.
         */
        public Item build() {
            return item;
        }
    }
}
