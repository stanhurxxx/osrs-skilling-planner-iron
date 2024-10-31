package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;

/**
 * Represents a slayer master in the game.
 * <p>
 * A SlayerMaster is responsible for providing slayer assignments to players, 
 * which they must complete to earn points and rewards. Each SlayerMaster may 
 * have different characteristics and methods for calculating points based on 
 * the assignments they provide.
 * </p>
 */
public abstract class SlayerMaster {
    
    /**
     * The number of points awarded for skipping a slayer assignment.
     */
    public static final int POINTS_PER_SKIP = 30;

    /**
     * Gets the array of slayer assignments available for this master.
     *
     * @return An array of {@link SlayerAssignment} objects representing 
     *         the assignments assigned by this slayer master.
     */
    public SlayerAssignment[] getAssignments() {
        return assignments;
    }
    
    /**
     * An array of slayer assignments associated with this slayer master.
     */
    protected SlayerAssignment[] assignments;
    
    /**
     * Constructs a SlayerMaster with the specified slayer assignments.
     *
     * @param assignments An array of {@link SlayerAssignment} objects 
     *                    that this slayer master can assign to players.
     */
    public SlayerMaster(SlayerAssignment[] assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the average points awarded per task for this slayer master.
     *
     * @return A float value representing the average points per task.
     */
    public abstract float calculateAveragePointsPerTask();
}
