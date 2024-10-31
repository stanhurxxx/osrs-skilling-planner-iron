package io.hurx.plugin.slayer.repository;

import java.util.UUID;

import io.hurx.models.CombatStyle;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.SlayerBracelets;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.plugin.slayer.SlayerOptions;
import io.hurx.plugin.slayer.SlayerSubViewNames;

public class SlayerListRepository extends Repository<SlayerRepository> {
    public final Repository.Property<String> name = new Repository.Property<String>("Untitled");
    public final Repository.Property<SlayerSubViewNames> subView = new Repository.Property<>(SlayerSubViewNames.ManageTasks);
    public final Repository.Property<SlayerMasters> master = new Repository.Property<>(SlayerMasters.Duradel);
    public final Repository.Property.List<SlayerMonsters> blocked = new Repository.Property.List<>();
    public final Repository.Property.List<SlayerMonsters> skipped = new Repository.Property.List<>();
    public final Repository.Property<SlayerMonsters> selectedMonster = new Repository.Property<>(null);
    public final Repository.Property<Monsters> selectedVariant = new Repository.Property<>(null);
    public final Repository.Property.Map<SlayerMonsters, Repository.Property.Map<Monsters, Integer>> variations = new Repository.Property.Map<>();
    public final Repository.Property.Map<SlayerMonsters, Repository.Property.List<Monsters>> variationOrders = new Repository.Property.Map<>();
    public final Repository.Property.Map<SlayerMonsters, Boolean> extendedMonsters = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, CombatStyle> meleeStyles = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, Integer> meleeHourlyRates = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, CombatStyle> rangedStyles = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, Integer> rangedHourlyRates = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, CombatStyle> magicStyles = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, Integer> magicHourlyRates = new Repository.Property.Map<>();
    public final Repository.Property.Map<Monsters, Integer> monsterCompletionTimes = new Repository.Property.Map<>();
    public final Repository.Property.Map<SlayerMonsters, SlayerBracelets> bracelets = new Repository.Property.Map<>();
    public final Repository.Property<CombatStyle> combatStyleFilter = new Repository.Property<>(CombatStyle.DefenceLast);
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

    public SlayerListRepository(SlayerRepository repository) {
        super(repository, "lists/" + UUID.randomUUID().toString());

        // TODO: dummy
        for (SlayerMonsters monster : SlayerMonsters.values()) {
            Repository.Property.Map<Monsters, Integer> variation = new Repository.Property.Map<Monsters, Integer>();
            
            // Fill the variation orders
            variationOrders.set(monster, new Repository.Property.List<Monsters>().add(monster.getMonsters()));
            
            // Fill the bracelets
            bracelets.set(monster, SlayerBracelets.None);

            // Loop through the variants
            Monsters[] monsters = monster.getMonsters();
            for (int i = 0; i < monsters.length; i ++) {
                Monsters variant = monsters[i];
                variation.set(variant, i == 0 ? 100 : 0);

                if (variant.getStats().getSlayer() == null) {
                    meleeStyles.set(variant, CombatStyle.DefenceLast);
                    meleeHourlyRates.set(variant, 110_000);
                    rangedStyles.set(variant, CombatStyle.Ranged);
                    rangedHourlyRates.set(variant, 0);
                    magicStyles.set(variant, CombatStyle.Magic);
                    magicHourlyRates.set(variant, 0);
                }
                else {
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
