package io.hurx.models.slayer;

import io.hurx.models.slayer.monsters.SlayerMonsters;

public class SlayerAssignment {
    public SlayerMonsters getMonster() {
        return monster;
    }
    private SlayerMonsters monster;

    public int getMin() {
        return min;
    }
    private int min;

    public int getMax() {
        return max;
    }
    private int max;

    public int getWeight() {
        return weight;
    }
    private int weight;

    public Integer getMinExtended() {
        return minExtended;
    }
    private Integer minExtended = null;

    public Integer getMaxExtended() {
        return maxExtended;
    }
    private Integer maxExtended = null;

    private SlayerAssignment(SlayerMonsters monster, int min, int max, int weight) {
        this.monster = monster;
        this.min = min;
        this.max = max;
        this.weight = weight;
    }

    /**
     * Iniializes the assignment
     * @param monster the monster
     * @param min the minimum amount of assignment
     * @param max the maximum amount of assignment
     * @param weight the weight of the assignment
     */
    public static Builder builder(SlayerMonsters monster, int min, int max, int weight) {
        return new Builder(new SlayerAssignment(monster, min, max, weight));
    }

    public static class Builder {
        private SlayerAssignment assignment;

        private Builder(SlayerAssignment assignment) {
            this.assignment = assignment;
        }

        public Builder extend(int min, int max) {
            assignment.minExtended = min;
            assignment.maxExtended = max;
            return this;
        }

        public SlayerAssignment build() {
            return this.assignment;
        }
    }
}
