package io.hurx.plugin.profile;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hurx.models.Skills;
import io.hurx.models.items.Items;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;
import io.hurx.plugin.profile.slayer.repository.SlayerRepository;

/**
 * The PluginRepository class serves as the data repository for the Ironman Skilling Planner plugin.
 * It manages the state and properties related to the plugin, including experience points (XP),
 * inventory, bank items, and the active view.
 */
public class ProfileRepository extends Repository<ProfileRepository> {
    @Override
    public String getDirName() {
        return "profiles";
    }

    /**
     * Get the profile name
     */
    public Repository.Property<String> name = new Repository.Property<String>("Default profile");

    /** 
     * The current view of the plugin, represented as a PluginViews property.
     * It determines which view is currently active in the user interface.
     */
    public Repository.Property<ProfileViews> view = new Repository.Property<ProfileViews>(ProfileViews.Overview);

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
    @JsonIgnore
    public SlayerRepository slayer = new SlayerRepository(this);

    /**
     * Constructs a PluginRepository for the specified plugin instance.
     * Initializes all skills and items with their default values.
     *
     * @param plugin the instance of the plugin that this repository is associated with.
     */
    public ProfileRepository(@JacksonInject Plugin plugin) {
        super(plugin, UUID.randomUUID().toString());
    }

    /**
     * Constructs a PluginRepository for the specified plugin instance.
     * Initializes all skills and items with their default values.
     *
     * @param plugin the instance of the plugin that this repository is associated with.
     * @param fileName the filename / uuid
     */
    public ProfileRepository(@JacksonInject Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public ProfileRepository initialize() {
        try {
            return (ProfileRepository)load();
        }
        catch (Exception ex) {
            try {
                // Initialize experience points for all skills to 0
                for (Skills skill : Skills.values()) {
                    xp.set(skill, 0f);
                }

                // Initialize bank and inventory quantities for all items to 0
                for (Items item : Items.values()) {
                    bank.set(item, 0f);
                    inventory.set(item, 0f);
                }
                
                save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
