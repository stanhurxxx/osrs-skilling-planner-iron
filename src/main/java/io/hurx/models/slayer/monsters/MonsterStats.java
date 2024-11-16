package io.hurx.models.slayer.monsters;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import io.hurx.models.CombatStyle;
import io.hurx.models.items.LootItem;

/**
 * Represents the statistics of a monster in the game.
 * This includes hitpoints, loot items, Slayer tower status, 
 * experience boost percentage, and the number of monsters removed from 
 * the Slayer assignment per kill.
 */
public class MonsterStats {
    
    /** The hitpoints of the monster. */
    private int hitpoints;

    /** The loot items dropped by the monster. */
    private List<LootItem> lootItems = new ArrayList<>();

    /** The xp rates for the monster **/
    private Map<CombatStyle, Integer> xpRates = new HashMap<>();

    /** Indicates if the monster is in the Slayer Tower. */
    private boolean slayerTower = false;

    /** The experience boost percentage when fighting this monster. */
    private float xpBoostPercentage = 0;

    /**
     * The number of monsters removed from the Slayer assignment 
     * for each kill of this monster during a Slayer task.
     */
    private int taskDecrementPerKill = 1;

    /** Whether or not the monster is a demon */
    private boolean isDemon = false;

    /** Is the task in the wildy? */
    private boolean isWilderness = false;

    /** Is the task barrage? */
    private boolean isBarrage = false;

    /** The total Slayer experience gained from defeating this monster. */
    private Integer slayer;

    /**
     * Constructs a MonsterStats object with the specified hitpoints.
     *
     * @param hitpoints The hitpoints of the monster.
     */
    public MonsterStats(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    /**
     * Gets the hitpoints of the monster.
     *
     * @return The hitpoints of the monster.
     */
    public int getHitpoints() {
        return hitpoints;
    }

    /**
     * Gets the loot items dropped by the monster.
     *
     * @return A list of LootItem objects representing the monster's loot.
     */
    public List<LootItem> getLootItems() {
        return lootItems;
    }

    /**
     * Checks if the monster is located in the Slayer Tower.
     *
     * @return True if the monster is in the Slayer Tower; otherwise, false.
     */
    public boolean getSlayerTower() {
        return slayerTower;
    }

    /**
     * Gets the experience boost percentage associated with the monster.
     *
     * @return The XP boost percentage.
     */
    public float getXPBoostPercentage() {
        return xpBoostPercentage;
    }

    /**
     * Gets the number of monsters removed from the Slayer assignment per kill.
     *
     * @return The task decrement per kill.
     */
    public int getTaskDecrementPerKill() {
        return taskDecrementPerKill;
    }

    /**
     * Gets the total Slayer experience associated with this monster.
     *
     * @return The Slayer experience as an Integer.
     */
    public Integer getSlayer() {
        return this.slayer;
    }

    /**
     * Clones the MonsterStats object and returns a Builder for further customization.
     *
     * @return A Builder instance to clone the MonsterStats.
     */
    public Builder clone() {
        return this.clone(this.hitpoints);
    }

    /**
     * Clones the MonsterStats object with specified hitpoints and returns a Builder.
     *
     * @param hitpoints An array of hitpoints to use for cloning.
     * @return A Builder instance for the cloned MonsterStats.
     */
    public Builder clone(int[] hitpoints) {
        MonsterStats stats = Builder.hitpoints(hitpoints).stats;
        List<LootItem> items = new ArrayList<>(lootItems);
        stats.lootItems = items;
        stats.xpRates = xpRates;
        stats.isDemon = isDemon;
        return new Builder(stats);
    }

    /**
     * Clones the MonsterStats object with specified hitpoints.
     *
     * @param hitpoints The hitpoints to use for cloning, or null to use existing hitpoints.
     * @return A Builder instance for the cloned MonsterStats.
     */
    public Builder clone(Integer hitpoints) {
        MonsterStats stats = new MonsterStats(hitpoints == null ? this.hitpoints : hitpoints);
        List<LootItem> items = new ArrayList<>(lootItems);
        stats.lootItems = items;
        stats.xpRates = xpRates;
        return new Builder(stats);
    }

    /**
     * A Builder class for creating and customizing MonsterStats objects.
     */
    public static class Builder {
        private MonsterStats stats;

        private Builder(MonsterStats stats) {
            this.stats = stats;
        }

        /**
         * Creates a Builder instance with average hitpoints calculated from an array of hitpoints.
         *
         * @param hitpoints An array of hitpoints to calculate the average.
         * @return A Builder instance with the calculated average hitpoints.
         */
        public static Builder hitpoints(int[] hitpoints) {
            int average = 0;
            for (int hp : hitpoints) {
                average += hp;
            }
            average = Math.round(average / hitpoints.length);
            MonsterStats stats = new MonsterStats(average);
            return new Builder(stats);
        }

        /**
         * Creates a Builder instance with the specified hitpoints.
         *
         * @param hitpoints The hitpoints to assign to the MonsterStats.
         * @return A Builder instance with the specified hitpoints.
         */
        public static Builder hitpoints(int hitpoints) {
            MonsterStats stats = new MonsterStats(hitpoints);
            return new Builder(stats);
        }

        /**
         * Adds a loot item to the monster's loot.
         *
         * @param lootItem The LootItem to add.
         * @return The current Builder instance for method chaining.
         */
        public Builder loot(LootItem lootItem) {
            stats.lootItems.add(lootItem);
            return this;
        }

        /** Is a demon */
        public Builder isDemon(boolean isDemon) {
            stats.isDemon = isDemon;
            return this;
        }

        /** Is in wilderness */
        public Builder isWilderness(boolean isWilderness) {
            stats.isWilderness = isWilderness;
            return this;
        }

        /** Is barrage task */
        public Builder isBarrage(boolean isBarrage) {
            stats.isBarrage = isBarrage;
            return this;
        }

        /**
         * Sets the Slayer tower status for the monster.
         *
         * @param slayerTower True if the monster is in the Slayer Tower, otherwise false.
         * @return The current Builder instance for method chaining.
         */
        public Builder slayerTower(boolean slayerTower) {
            stats.slayerTower = slayerTower;
            return this;
        }

        /**
         * Sets the experience boost percentage for the monster.
         *
         * @param xpBoostPercentage The experience boost percentage to set.
         * @return The current Builder instance for method chaining.
         */
        public Builder xpBoostPercentage(float xpBoostPercentage) {
            stats.xpBoostPercentage = xpBoostPercentage;
            return this;
        }

        /**
         * Sets the number of monsters removed from the Slayer assignment per kill.
         *
         * @param taskDecrementPerKill The number of monsters to remove from the Slayer assignment per kill.
         * @return The current Builder instance for method chaining.
         */
        public Builder taskDecrementPerKill(int taskDecrementPerKill) {
            stats.taskDecrementPerKill = taskDecrementPerKill;
            return this;
        }

        /**
         * Sets the total Slayer experience associated with this monster.
         *
         * @param slayer The Slayer experience to assign.
         * @return The current Builder instance for method chaining.
         */
        public Builder slayer(int slayer) {
            stats.slayer = slayer;
            return this;
        }

        /**
         * Builds and returns the MonsterStats object.
         *
         * @return The constructed MonsterStats object.
         */
        public MonsterStats build() {
            return this.stats;
        }
    }
}
