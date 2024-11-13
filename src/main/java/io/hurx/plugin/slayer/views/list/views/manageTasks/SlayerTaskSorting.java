package io.hurx.plugin.slayer.views.list.views.manageTasks;

/** Sorting techniques for slayer tasks */
public enum SlayerTaskSorting {
    KillCount("Amount of kills"),
    XPPerHour("XP per hour"),
    Weight("Task weight");

    /** GET The description */
    final String description() {
        return description;
    }
    /** The description */
    final String description;

    SlayerTaskSorting(String description) {
        this.description = description;
    }
}
