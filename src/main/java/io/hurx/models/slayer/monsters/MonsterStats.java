package io.hurx.models.slayer.monsters;

import java.util.List;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import io.hurx.models.items.LootItem;

public class MonsterStats {
    public int getHitpoints() {
        return hitpoints;
    }

    private int hitpoints;

    public List<LootItem> getLootItems() {
        return lootItems;
    }

    private List<LootItem> lootItems = new ArrayList();

    public boolean getSlayerTower() {
        return slayerTower;
    }

    private boolean slayerTower = false;

    public float getXPBoostPercentage() {
        return xpBoostPercentage;
    }

    private float xpBoostPercentage = 0;
    
    /**
     * The amount of kills go down from the slayer task each kill
     */
    public int getTaskDecrementPerKill() {
        return taskDecrementPerKill;
    }

    private int taskDecrementPerKill = 1;

    /**
     * Get the total slayer xp from the fight caves/inferno
     */
    public Integer getSlayer() {
        return this.slayer;
    }

    private Integer slayer;

    public MonsterStats(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public Builder clone() {
        return this.clone(this.hitpoints);
    }

    public Builder clone(int[] hitpoints) {
        MonsterStats stats = Builder.hitpoints(hitpoints).stats;
        List<LootItem> items = new ArrayList<>();
        for (int i = 0; i < lootItems.size(); i ++) {
            items.add(lootItems.get(i));
        }
        stats.lootItems = items;
        return new Builder(stats);
    }

    public Builder clone(Integer hitpoints) {
        MonsterStats stats = new MonsterStats(hitpoints == null ? this.hitpoints : hitpoints);
        List<LootItem> items = new ArrayList<>();
        for (int i = 0; i < lootItems.size(); i ++) {
            items.add(lootItems.get(i));
        }
        stats.lootItems = items;
        return new Builder(stats);
    }

    public static class Builder {
        private MonsterStats stats;

        private Builder(MonsterStats stats) {
            this.stats = stats;
        }

        public static Builder hitpoints(int[] hitpoints) {
            int average = 0;
            for (int hp : hitpoints) {
                average += hp;
            }
            average = Math.round(average / hitpoints.length);
            MonsterStats stats = new MonsterStats(average);
            return new Builder(stats);
        }

        public static Builder hitpoints(int hitpoints) {
            MonsterStats stats = new MonsterStats(hitpoints);
            return new Builder(stats);
        }

        public Builder loot(LootItem lootItem) {
            stats.lootItems.add(lootItem);
            return this;
        }

        public Builder slayerTower(boolean slayerTower) {
            stats.slayerTower = slayerTower;
            return this;
        }

        public Builder xpBoostPercentage(float xpBoostPercentage) {
            stats.xpBoostPercentage = xpBoostPercentage;
            return this;
        }

        public Builder taskDecrementPerKill(int taskDecrementPerKill) {
            stats.taskDecrementPerKill = taskDecrementPerKill;
            return this;
        }

        public Builder slayer(int slayer) {
            stats.slayer = slayer;
            return this;
        }

        public MonsterStats build() {
            return this.stats;
        }
    }
}
