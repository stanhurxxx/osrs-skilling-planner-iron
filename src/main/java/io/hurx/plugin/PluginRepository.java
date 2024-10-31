package io.hurx.plugin;

import io.hurx.models.Skills;
import io.hurx.models.items.Items;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.slayer.repository.SlayerRepository;

/**
 * The PluginRepository class serves as the data repository for the Ironman Skilling Planner plugin.
 * It manages the state and properties related to the plugin, including experience points (XP),
 * inventory, bank items, and the active view.
 */
public class PluginRepository extends Repository<PluginRepository> {

    /** 
     * Indicates whether the repository is currently processing.
     * This property can be used to prevent concurrent operations.
     */
    public static final Repository.Property<Boolean> isProcessing = new Repository.Property<>(true);
    
    /** 
     * The current view of the plugin, represented as a PluginViews property.
     * It determines which view is currently active in the user interface.
     */
    public final Repository.Property<PluginViews> view = new Repository.Property<>(PluginViews.Overview);

    /** 
     * A mapping of skills to their corresponding experience points (XP).
     * Each skill is initialized to 0 XP.
     */
    public final Repository.Property.Map<Skills, Float> xp = new Repository.Property.Map<>();

    /** 
     * A mapping of items to their corresponding quantities in the bank.
     * Each item is initialized to 0 quantity.
     */
    public final Repository.Property.Map<Items, Float> bank = new Repository.Property.Map<>();

    /** 
     * A mapping of items to their corresponding quantities in the inventory.
     * Each item is initialized to 0 quantity.
     */
    public final Repository.Property.Map<Items, Float> inventory = new Repository.Property.Map<>();

    /** 
     * An instance of SlayerRepository, which manages slayer-specific data.
     */
    public final SlayerRepository slayer = new SlayerRepository(this);

    /**
     * Constructs a PluginRepository for the specified plugin instance.
     * Initializes all skills and items with their default values.
     *
     * @param plugin the instance of the plugin that this repository is associated with.
     */
    public PluginRepository(Plugin plugin) {
        super(plugin, "repository");
        
        // Initialize experience points for all skills to 0
        for (Skills skill : Skills.values()) {
            xp.set(skill, 0f);
        }

        // Initialize bank and inventory quantities for all items to 0
        for (Items item : Items.values()) {
            bank.set(item, 0f);
            inventory.set(item, 0f);
        }
    }
}
