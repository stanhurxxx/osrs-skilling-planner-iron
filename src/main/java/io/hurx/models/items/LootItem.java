package io.hurx.models.items;

/**
 * Represents a loot item that can be dropped in the game.
 * A loot item consists of an item type, an amount range, and drop rate information.
 */
public class LootItem {

    private final Items item; // The item represented by this loot item.
    private final int minAmount; // The minimum amount of this item that can be dropped.
    private final int maxAmount; // The maximum amount of this item that can be dropped.
    private Float dropRateQuantifier; // The quantifier for the drop rate, representing its modifier.
    private Float dropRateRate; // The actual drop rate for the item.

    /**
     * Gets the item associated with this loot item.
     * 
     * @return the item associated with this loot item.
     */
    public Items getItem() {
        return item;
    }

    /**
     * Gets the minimum amount of this item that can be dropped.
     * 
     * @return the minimum amount of the item.
     */
    public int getMinAmount() {
        return minAmount;
    }

    /**
     * Gets the maximum amount of this item that can be dropped.
     * 
     * @return the maximum amount of the item.
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Gets the quantifier for the drop rate.
     * 
     * @return the drop rate quantifier, which modifies the base drop rate.
     */
    public Float getDropRateQuantifier() {
        return dropRateQuantifier;
    }

    /**
     * Gets the drop rate for this loot item.
     * 
     * @return the drop rate of the item.
     */
    public float getDropRateRate() {
        return dropRateRate;
    }

    /**
     * Constructs a LootItem with a specified item and fixed amount.
     * 
     * @param item the item associated with this loot item.
     * @param amount the fixed amount of the item to drop.
     */
    public LootItem(Items item, int amount) {
        this.item = item;
        this.minAmount = amount;
        this.maxAmount = amount;
    }

    /**
     * Constructs a LootItem with a specified item and a range of amounts.
     * 
     * @param item the item associated with this loot item.
     * @param minAmount the minimum amount of the item to drop.
     * @param maxAmount the maximum amount of the item to drop.
     */
    public LootItem(Items item, int minAmount, int maxAmount) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    /**
     * Creates a builder for a LootItem with a specified item and fixed amount.
     * 
     * @param item the item associated with the loot item.
     * @param amount the fixed amount of the item to drop.
     * @return a Builder instance for the LootItem.
     */
    public static Builder builder(Items item, int amount) {
        return new Builder(new LootItem(item, amount));
    }

    /**
     * Creates a builder for a LootItem with a specified item and a range of amounts.
     * 
     * @param item the item associated with the loot item.
     * @param minAmount the minimum amount of the item to drop.
     * @param maxAmount the maximum amount of the item to drop.
     * @return a Builder instance for the LootItem.
     */
    public static Builder builder(Items item, int minAmount, int maxAmount) {
        return new Builder(new LootItem(item, minAmount, maxAmount));
    }

    /**
     * Builder class for constructing LootItem instances with optional parameters.
     */
    public static class Builder {
        private LootItem lootItem;

        private Builder(LootItem lootItem) {
            this.lootItem = lootItem;
        }

        /**
         * Sets the drop rate quantifier and the drop rate for this loot item.
         * 
         * @param quantifier the quantifier for the drop rate.
         * @param rate the drop rate for the item.
         * @return the current Builder instance for chaining.
         */
        public Builder dropRate(float quantifier, float rate) {
            lootItem.dropRateQuantifier = quantifier;
            lootItem.dropRateRate = rate;
            return this;
        }

        /**
         * Sets the drop rate for this loot item with a default quantifier of 1.
         * 
         * @param rate the drop rate for the item.
         * @return the current Builder instance for chaining.
         */
        public Builder dropRate(float rate) {
            lootItem.dropRateQuantifier = 1f;
            lootItem.dropRateRate = rate;
            return this;
        }

        /**
         * Builds and returns the LootItem instance.
         * 
         * @return the constructed LootItem.
         */
        public LootItem build() {
            return lootItem;
        }
    }
}
