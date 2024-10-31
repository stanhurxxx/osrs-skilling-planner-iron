package io.hurx.plugin;

import io.hurx.models.Skills;
import io.hurx.models.items.Items;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.slayer.repository.SlayerRepository;

public class PluginRepository extends Repository<PluginRepository> {
    public static final Repository.Property<Boolean> isProcessing = new Repository.Property<Boolean>(true);
    
    public final Repository.Property<PluginViews> view = new Repository.Property<>(PluginViews.Overview);
    public final Repository.Property.Map<Skills, Float> xp = new Repository.Property.Map<Skills, Float>();
    public final Repository.Property.Map<Items, Float> bank = new Repository.Property.Map<Items, Float>();
    public final Repository.Property.Map<Items, Float> inventory = new Repository.Property.Map<Items, Float>();

    public final SlayerRepository slayer = new SlayerRepository(this);

    public PluginRepository(Plugin plugin) {
        super(plugin, "repository");
        for (Skills skill : Skills.values()) {
            xp.set(skill, 0f);
        }
        for (Items item : Items.values()) {
            bank.set(item, 0f);
            inventory.set(item, 0f);
        }
    }
}