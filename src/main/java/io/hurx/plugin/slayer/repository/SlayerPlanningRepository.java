package io.hurx.plugin.slayer.repository;

import java.util.UUID;

import io.hurx.models.repository.Repository;

public class SlayerPlanningRepository extends Repository<SlayerRepository> {
    public Repository.Property<String> listFileName = new Repository.Property<String>(null);
    public Repository.Property<Integer> startXP = new Repository.Property<>(200_000_000);
    public Repository.Property<Integer> endXP = new Repository.Property<>(200_000_000);
    public Repository.Property<Long> createdAt = new Repository.Property<>(System.currentTimeMillis());
    public SlayerPlanningRepository(SlayerRepository repository) {
        super(repository, "plannings/" + UUID.randomUUID().toString());
    }
}
