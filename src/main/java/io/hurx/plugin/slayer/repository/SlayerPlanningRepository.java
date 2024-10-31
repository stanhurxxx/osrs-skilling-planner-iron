package io.hurx.plugin.slayer.repository;

import java.util.UUID;

import io.hurx.models.repository.Repository;

/**
 * The SlayerPlanningRepository class manages planning-related data for Slayer tasks.
 * 
 * This repository holds information such as the experience (XP) range for Slayer tasks,
 * the file name for saving the list, and the creation timestamp. It extends the base 
 * Repository class to provide structure for managing planning-related properties.
 */
public class SlayerPlanningRepository extends Repository<SlayerRepository> {

    /** The file name for the list of Slayer planning data. */
    public Repository.Property<String> listFileName = new Repository.Property<String>(null);
    
    /** The starting experience (XP) value for the planning. Default is set to 200 million. */
    public Repository.Property<Integer> startXP = new Repository.Property<>(200_000_000);
    
    /** The ending experience (XP) value for the planning. Default is set to 200 million. */
    public Repository.Property<Integer> endXP = new Repository.Property<>(200_000_000);
    
    /** The timestamp indicating when the planning was created. Default is set to the current system time. */
    public Repository.Property<Long> createdAt = new Repository.Property<>(System.currentTimeMillis());

    /**
     * Constructs a new SlayerPlanningRepository instance.
     *
     * @param repository the base SlayerRepository associated with this planning
     */
    public SlayerPlanningRepository(SlayerRepository repository) {
        super(repository, "plannings/" + UUID.randomUUID().toString());
    }
}
