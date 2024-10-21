package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.monsters.SlayerMonster;

/**
 * Represents a slayer master
 */
public abstract class SlayerMaster {
    public SlayerMonster[] getAssignments() {
        return assignments;
    }

    protected SlayerMonster[] assignments;

    public SlayerMaster(SlayerMonster[] assignments) {
        this.assignments = assignments;
    }
}
