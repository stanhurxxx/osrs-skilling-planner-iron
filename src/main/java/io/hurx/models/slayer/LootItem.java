package io.hurx.models.slayer;

import io.hurx.models.items.Items;

public class LootItem {
    public Items getItem() {
        return item;
    }

    private final Items item;

    public int getMinAmount() {
        return minAmount;
    }

    private final int minAmount;

    public int getMaxAmount() {
        return maxAmount;
    }

    private final int maxAmount;

    private Float dropRateQuantifier;

    private Float dropRateRate;

    public LootItem(Items item, int amount) {
        this.item = item;
        this.minAmount = amount;
        this.maxAmount = amount;
    }

    public LootItem(Items item, int minAmount, int maxAmount) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public static class Builder {
        private LootItem lootItem;

        private Builder(LootItem lootItem) {
            this.lootItem = lootItem;
        }

        public static Builder item(Items item, int amount) {
            Builder builder = new Builder(new LootItem(item, amount));
            return builder;
        }

        public static Builder item(Items item, int minAmount, int maxAmount) {
            Builder builder = new Builder(new LootItem(item, minAmount, maxAmount));
            return builder;
        }
        
        public Builder dropRate(float quantifier, float rate) {
            lootItem.dropRateQuantifier = quantifier;
            lootItem.dropRateRate = rate;
            return this;
        }

        public Builder dropRate(float rate) {
            lootItem.dropRateQuantifier = 1f;
            lootItem.dropRateRate = rate;
            return this;
        }

        public LootItem build() {
            return lootItem;
        }
    }
}
