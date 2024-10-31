package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;

/**
 * Represents a slayer master
 */
public abstract class SlayerMaster {
    public static int POINTS_PER_SKIP = 30;

    public SlayerAssignment[] getAssignments() {
        return assignments;
    }
    
    protected SlayerAssignment[] assignments;
    
    public SlayerMaster(SlayerAssignment[] assignments) {
        this.assignments = assignments;
    }

    public abstract float calculateAveragePointsPerTask();
}
