package io.hurx.plugin.profile.slayer.repository;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JacksonInject;

import io.hurx.models.repository.Repository;

/**
 * The SlayerPlanningRepository class manages planning-related data for Slayer tasks.
 * 
 * This repository holds information such as the experience (XP) range for Slayer tasks,
 * the file name for saving the list, and the creation timestamp. It extends the base 
 * Repository class to provide structure for managing planning-related properties.
 */
public class SlayerPlanningRepository extends Repository<SlayerRepository> {
    @Override
    public String getDirName() {
        return "plannings";
    }

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
    public SlayerPlanningRepository(@JacksonInject SlayerRepository repository) {
        super(repository, UUID.randomUUID().toString());
    }

    /**
     * Constructs a new SlayerPlanningRepository instance, and specifies a fileName (uuid).
     * @param repository repository
     * @param fileName uuid
     */
    public SlayerPlanningRepository(@JacksonInject SlayerRepository repository, String fileName) {
        super(repository, fileName);
    }

    @Override
    public SlayerPlanningRepository initialize() {
        try {
            return (SlayerPlanningRepository)load();
        }
        catch (Exception ex) {
            try {
                save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
