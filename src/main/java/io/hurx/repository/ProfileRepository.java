package io.hurx.repository;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JacksonInject;

import io.hurx.annotations.OneToOne;
import io.hurx.models.Skills;
import io.hurx.models.items.Items;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.PluginViews;
import io.hurx.repository.slayer.SlayerRepository;
import io.hurx.utils.Injects;

/**
 * The PluginRepository class serves as the data repository for the Ironman Skilling Planner plugin.
 * It manages the state and properties related to the plugin, including experience points (XP),
 * inventory, bank items, and the active view.
 */
public class ProfileRepository extends Repository<PluginRepository> {
    @Override
    public String getDirName() {
        return "profiles";
    }

    @Override
    public String getSavedFileName() {
        return "profile";
    }

    /**
     * Get the profile name
     */
    public Repository.Property<String> name = new Repository.Property<String>("Default profile");

    /** 
     * The current view of the plugin, represented as a PluginViews property.
     * It determines which view is currently active in the user interface.
     */
    public Repository.Property<PluginViews> view = new Repository.Property<PluginViews>(PluginViews.Overview);

    /** 
     * A mapping of skills to their corresponding experience points (XP).
     * Each skill is initialized to 0 XP.
     */
    public Repository.Property.Map<Skills, Float> xp = new Repository.Property.Map<Skills, Float>();

    /** 
     * A mapping of items to their corresponding quantities in the bank.
     * Each item is initialized to 0 quantity.
     */
    public Repository.Property.Map<Items, Float> bank = new Repository.Property.Map<Items, Float>();

    /** 
     * A mapping of items to their corresponding quantities in the inventory.
     * Each item is initialized to 0 quantity.
     */
    public Repository.Property.Map<Items, Float> inventory = new Repository.Property.Map<Items, Float>();

    /** 
     * An instance of SlayerRepository, which manages slayer-specific data.
     */
    @OneToOne
    public SlayerRepository slayer = new SlayerRepository(this);

    /**
     * Constructs a PluginRepository for the specified plugin instance.
     * Initializes all skills and items with their default values.
     */
    public ProfileRepository(@JacksonInject PluginRepository pluginRepository) {
        super(pluginRepository, UUID.randomUUID().toString());
        Injects.setInjectable(ProfileRepository.class, this);
        slayer = (SlayerRepository) Repository.registered.get(slayer.generatePath());
    }

    /**
     * Constructs a PluginRepository for the specified pluginRepository instance.
     * Initializes all skills and items with their default values.
     *
     * @param pluginRepository the instance of the pluginRepository that this repository is associated with.
     * @param uuid the filename / uuid
     */
    public ProfileRepository(@JacksonInject PluginRepository pluginRepository, String uuid) {
        super(pluginRepository, uuid);
        Injects.setInjectable(ProfileRepository.class, this);
        slayer = (SlayerRepository) Repository.registered.get(slayer.generatePath());
    }
}
