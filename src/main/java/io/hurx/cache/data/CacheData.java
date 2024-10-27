package io.hurx.cache.data;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import io.hurx.models.ViewNames;
import io.hurx.models.items.Items;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.models.CombatStyle;
import io.hurx.models.Skills;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.*;

/**
 * Data in cache
 */
public class CacheData {
    /**
     * The current view
     */
    public ViewNames view = ViewNames.Overview;

    public Map<Skills, Float> xp;
    public Map<Items, Float> bank;
    public Map<Items, Float> inventory;

    public SlayerMasters slayerMaster = SlayerMasters.Duradel;
    public SlayerMonsters[] slayerBlocked = new SlayerMonsters[] {};
    public SlayerMonsters[] slayerSkipped = new SlayerMonsters[] {};
    public SlayerMonsters slayerSelectedMonster = null;
    public Monsters slayerSelectedVariant = null;
    public Map<SlayerMonsters, Map<Monsters, Integer>> slayerMonsterVariation;
    public Map<SlayerMonsters, Monsters[]> slayerMonsterVariationOrder;
    public Map<Monsters, Boolean> slayerMonsterExtended;
    public Map<Monsters, CombatStyle> slayerMonsterCombatStyleMelee;
    public Map<Monsters, Integer> slayerMonsterCombatStyleMeleeHourlyRate;
    public Map<Monsters, CombatStyle> slayerMonsterCombatStyleRanged;
    public Map<Monsters, Integer> slayerMonsterCombatStyleRangedHourlyRate;
    public Map<Monsters, CombatStyle> slayerMonsterCombatStyleMagic;
    public Map<Monsters, Integer> slayerMonsterCombatStyleMagicHourlyRate;
    public Map<Monsters, Integer> slayerMonsterCompletionTime;

    public CacheData() {
        slayerMonsterVariation = new HashMap<>();
        slayerMonsterExtended = new HashMap<>();
        slayerMonsterCombatStyleMelee = new HashMap<>();
        slayerMonsterCombatStyleMeleeHourlyRate = new HashMap<>();
        slayerMonsterCombatStyleRanged = new HashMap<>();
        slayerMonsterCombatStyleRangedHourlyRate = new HashMap<>();
        slayerMonsterCombatStyleMagic = new HashMap<>();
        slayerMonsterCombatStyleMagicHourlyRate = new HashMap<>();
        slayerMonsterVariationOrder = new HashMap<>();
        slayerMonsterCompletionTime = new HashMap<>();

        xp = new HashMap<>();
        bank = new HashMap<>();
        inventory = new HashMap<>();

        // TODO: dummy;
        for (Skills skill : Skills.values()) {
            xp.put(skill, 13_034_431f);
        }

        // TODO: dummy
        for (Items item : Items.values()) {
            bank.put(item, 0f);
            inventory.put(item, 0f);
        }

        // TODO: dummy
        for (SlayerMonsters monster : SlayerMonsters.values()) {
            Map<Monsters, Integer> variation = new HashMap<>();
            slayerMonsterVariationOrder.put(monster, monster.getMonsters());

            Monsters[] monsters = monster.getMonsters();
            for (int i = 0; i < monsters.length; i ++) {
                Monsters variant = monsters[i];
                variation.put(variant, i == 0 ? 100 : 0);

                if (variant.getStats().getSlayer() == null) {
                    slayerMonsterCombatStyleMelee.put(variant, CombatStyle.Attack);
                    slayerMonsterCombatStyleMeleeHourlyRate.put(variant, 110_000);
                    slayerMonsterCombatStyleRanged.put(variant, CombatStyle.Ranged);
                    slayerMonsterCombatStyleRangedHourlyRate.put(variant, 0);
                    slayerMonsterCombatStyleMagic.put(variant, CombatStyle.Magic);
                    slayerMonsterCombatStyleMagicHourlyRate.put(variant, 0);
                }
                else {
                    slayerMonsterCombatStyleMelee.put(variant, CombatStyle.None);
                    slayerMonsterCombatStyleMeleeHourlyRate.put(variant, 0);
                    slayerMonsterCombatStyleRanged.put(variant, CombatStyle.Ranged);
                    slayerMonsterCombatStyleRangedHourlyRate.put(variant, variant.getStats().getHitpoints() * 4);
                    slayerMonsterCombatStyleMagic.put(variant, CombatStyle.None);
                    slayerMonsterCombatStyleMeleeHourlyRate.put(variant, 0);
                    slayerMonsterCompletionTime.put(variant, variant == Monsters.Zuk ? 75 : 30);
                }
            }

            slayerMonsterVariation.put(monster, variation);
        }
    }
}
