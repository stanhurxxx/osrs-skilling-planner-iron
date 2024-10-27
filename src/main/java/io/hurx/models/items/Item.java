package io.hurx.models.items;

import javax.swing.ImageIcon;

public class Item {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    } 

    private String name = "To be loaded";

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    private int price = 0;

    private ImageIcon icon;

    public Integer getPriceItemId() {
        return this.priceItemId;
    }

    private Integer priceItemId = null;

    public int[] getPriceItemComponentIds() {
        return priceItemComponentIds;
    }

    private int[] priceItemComponentIds = new int[] {};

    public Integer getNotedId() {
        return this.notedId;
    }

    public void setNotedId(Integer notedId) {
        this.notedId = notedId;
    }

    private Integer notedId;

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
