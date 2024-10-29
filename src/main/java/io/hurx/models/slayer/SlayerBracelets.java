package io.hurx.models.slayer;

import io.hurx.models.items.Items;

public enum SlayerBracelets {
    None(),
    Slaughter(Items.BraceletOfSlaughter),
    Expeditious(Items.ExpeditiousBracelet);

    public Items getItem() {
        return item;
    }
    private Items item;

    SlayerBracelets() {}

    SlayerBracelets(Items item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item == null ? "None" : item.getItem().getName();
    }
}
