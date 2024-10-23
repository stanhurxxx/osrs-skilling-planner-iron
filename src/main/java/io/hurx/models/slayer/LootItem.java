package io.hurx.models.slayer;

import io.hurx.models.Item;

public class LootItem {
    public Item getItem() {
        return item;
    }

    private final Item item;

    public int getMinAmount() {
        return minAmount;
    }

    private final int minAmount;

    public int getMaxAmount() {
        return maxAmount;
    }

    private final int maxAmount;

    LootItem(Item item, int amount) {
        this.item = item;
        this.minAmount = amount;
        this.maxAmount = amount;
    }

    LootItem(Item item, int minAmount, int maxAmount) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
