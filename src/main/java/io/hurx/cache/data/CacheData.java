package io.hurx.cache.data;

import java.util.HashMap;
import java.util.Map;

import io.hurx.models.ViewNames;
import io.hurx.models.items.Items;

import io.hurx.models.Skills;

/**
 * Data in cache
 */
public class CacheData {
     /**
     * The current view
     */
    private ViewNames view = ViewNames.Overview;
    public ViewNames getView() {
        return view;
    }
    public void setView(ViewNames view) {
        this.view = view;
    }

    /**
     * All player XP
     */
    private Map<Skills, Float> xp;
    public Map<Skills, Float> getXp() {
        return xp;
    }
    public void setXp(Map<Skills, Float> xp) {
        this.xp = xp;
    }

    /**
     * Player's bank items
     */
    private Map<Items, Float> bank;
    public Map<Items, Float> getBank() {
        return bank;
    }
    public void setBank(Map<Items, Float> bank) {
        this.bank = bank;
    }

    /**
     * Player's inventory items
     */
    private Map<Items, Float> inventory;
    public Map<Items, Float> getInventory() {
        return inventory;
    }
    public void setInventory(Map<Items, Float> inventory) {
        this.inventory = inventory;
    }

    /**
     * Slayer data
     */
    private SlayerData slayer = new SlayerData();
    public SlayerData getSlayer() {
        return slayer;
    }
    public void setSlayer(SlayerData slayer) {
        this.slayer = slayer;
    }

    public CacheData() {
        xp = new HashMap<>();
        bank = new HashMap<>();
        inventory = new HashMap<>();

        // TODO: dummy;
        for (Skills skill : Skills.values()) {
            xp.put(skill, 13_034_431f);
        }

        // TODO: dummy
        for (Items item : Items.values()) {
            bank.put(item, 0f);
            inventory.put(item, 0f);
        }
    }
}
