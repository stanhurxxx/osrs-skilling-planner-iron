package io.hurx.models.items;

import net.runelite.client.game.ItemManager;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Item {
    public static ItemManager manager = null;

    public int getId() {
        return id;
    }

    private int id;

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public int getPrice() {
        if (Item.manager == null) return 0;
        if (priceItemId != null) {
            int price = Item.manager.getItemPrice(priceItemId); 
            for (int subtractId : priceItemComponentIds) {
                price -= Item.manager.getItemPrice(subtractId);
            }
            return price;
        }
        return Item.manager.getItemPrice(id);
    }

    private ImageIcon icon;

    private Integer priceItemId = null;

    private int[] priceItemComponentIds = new int[] {};

    public Integer getNotedId() {
        if (Item.manager == null) return null;
        int notedId = Item.manager.getItemComposition(id).getLinkedNoteId();
        return notedId == id || notedId < 0
            ? null
            : notedId;
    }

    public ItemType getType() {
        return type;
    }

    private ItemType type = ItemType.Default;

    /**
     * The amount of kills go down from the slayer task from one kill.
     */
    public int getKCMultiplier() {
        return kcMultiplier;
    }

    /**
     * The amount of kills go down from the slayer task from one kill.
     */
    private int kcMultiplier = 1;

    private Item(int id) {
        this.id = id;
    }

    public static class Builder {
        private Item item;

        private Builder(Item item) {
            this.item = item;
        }

        public static Builder id(int id) {
            Builder builder = new Builder(new Item(id));
            return builder;
        }

        public Builder type(ItemType type) {
            this.item.type = type;
            return this;
        }

        public Builder priceItemId(int priceItemId) {
            this.item.priceItemId = priceItemId;
            return this;
        }

        public Builder priceItemComponentIds(int[] priceItemComponentIds) {
            this.item.priceItemComponentIds = priceItemComponentIds;
            return this;
        }

        public Builder kcMultiplier(int kcMultiplier) {
            this.item.kcMultiplier = kcMultiplier;
            return this;
        }

        public Item build() {
            return item;
        }
    }
}
