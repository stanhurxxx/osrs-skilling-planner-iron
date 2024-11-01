package io.hurx.plugin.slayer.repository;

import java.util.UUID;

import io.hurx.models.CombatStyle;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.SlayerBracelets;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.plugin.slayer.SlayerOptions;
import io.hurx.plugin.slayer.views.list.SlayerListViews;

/**
 * The SlayerListRepository class manages a list of Slayer-related data.
 * 
 * This class extends the Repository class and provides properties to track
 * various aspects of Slayer tasks, including selected monsters, blocked
 * monsters, variations, combat styles, and other options relevant to Slayer 
 * activities.
 */
public class SlayerListRepository extends Repository<SlayerRepository> {
    
    /** The name of the Slayer list. */
    public final Repository.Property<String> name = new Repository.Property<String>("Untitled");
    
    /** The sub-view of the Slayer list (e.g., ManageTasks). */
    public final Repository.Property<SlayerListViews> subView = new Repository.Property<>(SlayerListViews.ManageTasks);
    
    /** The current Slayer master. */
    public final Repository.Property<SlayerMasters> master = new Repository.Property<>(SlayerMasters.Duradel);
    
    /** The list of blocked Slayer monsters. */
    public final Repository.Property.List<SlayerMonsters> blocked = new Repository.Property.List<>();
    
    /** The list of skipped Slayer monsters. */
    public final Repository.Property.List<SlayerMonsters> skipped = new Repository.Property.List<>();
    
    /** The currently selected Slayer monster. */
    public final Repository.Property<SlayerMonsters> selectedMonster = new Repository.Property<>(null);
    
    /** The currently selected variant of a monster. */
    public final Repository.Property<Monsters> selectedVariant = new Repository.Property<>(null);
    
    /** Variations of Slayer monsters and their respective percentages. */
    public final Repository.Property.Map<SlayerMonsters, Repository.Property.Map<Monsters, Integer>> variations = new Repository.Property.Map<>();
    
    /** Orders of variations for each Slayer monster. */
    public final Repository.Property.Map<SlayerMonsters, Repository.Property.List<Monsters>> variationOrders = new Repository.Property.Map<>();
    
    /** Extended monsters for Slayer tasks. */
    public final Repository.Property.Map<SlayerMonsters, Boolean> extendedMonsters = new Repository.Property.Map<>();
    
    /** Melee combat styles for each monster variant. */
    public final Repository.Property.Map<Monsters, CombatStyle> meleeStyles = new Repository.Property.Map<>();
    
    /** Hourly rates for melee combat styles of monsters. */
    public final Repository.Property.Map<Monsters, Integer> meleeHourlyRates = new Repository.Property.Map<>();
    
    /** Ranged combat styles for each monster variant. */
    public final Repository.Property.Map<Monsters, CombatStyle> rangedStyles = new Repository.Property.Map<>();
    
    /** Hourly rates for ranged combat styles of monsters. */
    public final Repository.Property.Map<Monsters, Integer> rangedHourlyRates = new Repository.Property.Map<>();
    
    /** Magic combat styles for each monster variant. */
    public final Repository.Property.Map<Monsters, CombatStyle> magicStyles = new Repository.Property.Map<>();
    
    /** Hourly rates for magic combat styles of monsters. */
    public final Repository.Property.Map<Monsters, Integer> magicHourlyRates = new Repository.Property.Map<>();
    
    /** Completion times for each monster. */
    public final Repository.Property.Map<Monsters, Integer> monsterCompletionTimes = new Repository.Property.Map<>();
    
    /** The Slayer bracelets associated with each Slayer monster. */
    public final Repository.Property.Map<SlayerMonsters, SlayerBracelets> bracelets = new Repository.Property.Map<>();
    
    /** The current combat style filter applied. */
    public final Repository.Property<CombatStyle> combatStyleFilter = new Repository.Property<>(CombatStyle.DefenceLast);
    
    /** Options available for Slayer tasks. */
    public final Repository.Property.List<SlayerOptions> options = new Repository.Property.List<SlayerOptions>().add(
        SlayerOptions.MoryLegs4,
        SlayerOptions.Piety,
        SlayerOptions.EagleEye,
        SlayerOptions.Protection,
        SlayerOptions.SuperCombatPotions,
        SlayerOptions.RangingPotions,
        SlayerOptions.SuperRestores,
        SlayerOptions.SanfewSerums
    );

    /**
     * Constructs a new SlayerListRepository instance.
     *
     * @param repository the base SlayerRepository associated with this list
     */
    public SlayerListRepository(SlayerRepository repository) {
        super(repository, "lists/" + UUID.randomUUID().toString());

        // Populate initial values for the Slayer monsters
        for (SlayerMonsters monster : SlayerMonsters.values()) {
            Repository.Property.Map<Monsters, Integer> variation = new Repository.Property.Map<Monsters, Integer>();
            
            // Fill the variation orders for the monster
            variationOrders.set(monster, new Repository.Property.List<Monsters>().add(monster.getMonsters()));
            
            // Set the default bracelet for the monster
            bracelets.set(monster, SlayerBracelets.None);

            // Loop through the variants of the monster
            Monsters[] monsters = monster.getMonsters();
            for (int i = 0; i < monsters.length; i++) {
                Monsters variant = monsters[i];
                variation.set(variant, i == 0 ? 100 : 0); // Set variation percentage
                
                if (variant.getStats().getSlayer() == null) {
                    // Set default combat styles and rates for monsters without Slayer stats
                    meleeStyles.set(variant, CombatStyle.DefenceLast);
                    meleeHourlyRates.set(variant, 110_000);
                    rangedStyles.set(variant, CombatStyle.Ranged);
                    rangedHourlyRates.set(variant, 0);
                    magicStyles.set(variant, CombatStyle.Magic);
                    magicHourlyRates.set(variant, 0);
                } else {
                    // Set combat styles and rates for monsters with Slayer stats
                    meleeStyles.set(variant, CombatStyle.None);
                    meleeHourlyRates.set(variant, 0);
                    rangedStyles.set(variant, CombatStyle.Ranged);
                    rangedHourlyRates.set(variant, variant.getStats().getHitpoints() * 4);
                    magicStyles.set(variant, CombatStyle.None);
                    meleeHourlyRates.set(variant, 0);
                    monsterCompletionTimes.set(variant, variant == Monsters.Zuk ? 75 : 30);
                }
            }

            // Set the variation percentages for the monster
            variations.set(monster, variation);
        }
    }
}
