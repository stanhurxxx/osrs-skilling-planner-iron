package io.hurx.models.slayer;

import io.hurx.models.slayer.monsters.SlayerMonsters;

/**
 * Represents a Slayer Assignment, which includes details about a specific Slayer monster,
 * the range of tasks assigned, and the weight of the assignment.
 */
public class SlayerAssignment {
    
    /** The Slayer monster associated with this assignment. */
    private SlayerMonsters monster;

    /** The minimum amount of the assignment. */
    private int min;

    /** The maximum amount of the assignment. */
    private int max;

    /** The weight of the assignment, affecting its selection probability. */
    private int weight;

    /** The minimum extended value for the assignment, if applicable. */
    private Integer minExtended = null;

    /** The maximum extended value for the assignment, if applicable. */
    private Integer maxExtended = null;

    /**
     * Private constructor for creating a SlayerAssignment.
     *
     * @param monster The Slayer monster for this assignment.
     * @param min The minimum amount of tasks for this assignment.
     * @param max The maximum amount of tasks for this assignment.
     * @param weight The weight of this assignment, affecting its selection probability.
     */
    private SlayerAssignment(SlayerMonsters monster, int min, int max, int weight) {
        this.monster = monster;
        this.min = min;
        this.max = max;
        this.weight = weight;
    }

    /**
     * Gets the Slayer monster associated with this assignment.
     *
     * @return The Slayer monster for this assignment.
     */
    public SlayerMonsters getMonster() {
        return monster;
    }

    /**
     * Gets the minimum amount of tasks for this assignment.
     *
     * @return The minimum amount of the assignment.
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum amount of tasks for this assignment.
     *
     * @return The maximum amount of the assignment.
     */
    public int getMax() {
        return max;
    }

    /**
     * Gets the weight of this assignment, affecting its selection probability.
     *
     * @return The weight of the assignment.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the minimum extended amount for this assignment, if specified.
     *
     * @return The minimum extended amount, or null if not specified.
     */
    public Integer getMinExtended() {
        return minExtended;
    }

    /**
     * Gets the maximum extended amount for this assignment, if specified.
     *
     * @return The maximum extended amount, or null if not specified.
     */
    public Integer getMaxExtended() {
        return maxExtended;
    }

    /**
     * Initializes the SlayerAssignment using a builder pattern.
     *
     * @param monster The Slayer monster for this assignment.
     * @param min The minimum amount of tasks for this assignment.
     * @param max The maximum amount of tasks for this assignment.
     * @param weight The weight of this assignment, affecting its selection probability.
     * @return A Builder object for creating a SlayerAssignment.
     */
    public static Builder builder(SlayerMonsters monster, int min, int max, int weight) {
        return new Builder(new SlayerAssignment(monster, min, max, weight));
    }

    /**
     * Builder class for creating a SlayerAssignment instance.
     */
    public static class Builder {
        /** The SlayerAssignment being constructed. */
        private SlayerAssignment assignment;

        /**
         * Private constructor for Builder.
         *
         * @param assignment The SlayerAssignment to be built.
         */
        private Builder(SlayerAssignment assignment) {
            this.assignment = assignment;
        }

        /**
         * Extends the assignment with minimum and maximum values.
         *
         * @param min The minimum extended amount.
         * @param max The maximum extended amount.
         * @return The Builder instance for chaining.
         */
        public Builder extend(int min, int max) {
            assignment.minExtended = min;
            assignment.maxExtended = max;
            return this;
        }

        /**
         * Builds and returns the SlayerAssignment instance.
         *
         * @return The constructed SlayerAssignment.
         */
        public SlayerAssignment build() {
            return this.assignment;
        }
    }
}
