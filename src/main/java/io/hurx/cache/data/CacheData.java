package io.hurx.cache.data;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import io.hurx.models.ViewNames;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.CombatStyle;
import io.hurx.models.slayer.masters.*;

/**
 * Data in cache
 */
public class CacheData {
    /**
     * The current view
     */
    public ViewNames view = ViewNames.Overview;

    /**
     * The players slayer master choice
     */
    public SlayerMasters slayerMaster = SlayerMasters.Duradel;

    /**
     * Slayer blocks
     */
    public SlayerMonsters[] slayerBlocked = new SlayerMonsters[] {};

    /**
     * Slayer skips
     */
    public SlayerMonsters[] slayerSkipped = new SlayerMonsters[] {};

    /**
     * The selected slayer monster
     */
    public SlayerMonsters slayerSelectedMonster = null;

    /**
     * Slayer variants
     */
    public SlayerMonsters slayerSelectedVariant = null;
    public Map<SlayerMonsters, Map<SlayerMonsters, Integer>> slayerMonsterVariation;
    public Map<SlayerMonsters, Boolean> slayerMonsterExtended;
    public Map<SlayerMonsters, CombatStyle> slayerMonsterCombatStyleMelee;
    public Map<SlayerMonsters, Integer> slayerMonsterCombatStyleMeleeHourlyRate;
    public Map<SlayerMonsters, CombatStyle> slayerMonsterCombatStyleRanged;
    public Map<SlayerMonsters, Integer> slayerMonsterCombatStyleRangedHourlyRate;
    public Map<SlayerMonsters, CombatStyle> slayerMonsterCombatStyleMagic;
    public Map<SlayerMonsters, Integer> slayerMonsterCombatStyleMagicHourlyRate;

    public CacheData() {
        slayerMonsterVariation = new HashMap<>();
        slayerMonsterExtended = new HashMap<>();
        slayerMonsterCombatStyleMelee = new HashMap<>();
        slayerMonsterCombatStyleMeleeHourlyRate = new HashMap<>();
        slayerMonsterCombatStyleRanged = new HashMap<>();
        slayerMonsterCombatStyleRangedHourlyRate = new HashMap<>();
        slayerMonsterCombatStyleMagic = new HashMap<>();
        slayerMonsterCombatStyleMagicHourlyRate = new HashMap<>();

        Duradel duradel = new Duradel();
        Nieve nieve = new Nieve();
        
        loop: for (SlayerMonsters monster : SlayerMonsters.values()) {
            Map<SlayerMonsters, Integer> variation = new HashMap<>();
            SlayerMonster m = null;
            for (SlayerMaster master : new SlayerMaster[] { duradel, nieve }) {
                for (int i = 0; i < master.getAssignments().length; i ++) {
                    SlayerMonster a = master.getAssignments()[i];
                    // TODO: Dummy data
                    variation.put(a.variants[0], a.maxAmountExtended == null ? a.maxAmount : a.maxAmountExtended);
                    for (int j = 1; j < a.variants.length; j ++) {
                        // TODO: Dummy data
                        variation.put(a.variants[i], 0);
                        // TODO: Dummy data
                        if (a.minAmountExtended != null && a.maxAmountExtended != null) {
                            slayerMonsterExtended.put(a.variants[i], true);
                        }
                        else {
                            slayerMonsterExtended.put(a.variants[i], false);
                        }
                        // TODO: Dummy data
                        slayerMonsterCombatStyleMelee.put(a.variants[i], CombatStyle.Attack);
                        slayerMonsterCombatStyleMeleeHourlyRate.put(a.variants[i], 110_000);
                        slayerMonsterCombatStyleRanged.put(a.variants[i], CombatStyle.Ranged);
                        slayerMonsterCombatStyleRangedHourlyRate.put(a.variants[i], 0);
                        slayerMonsterCombatStyleMagic.put(a.variants[i], CombatStyle.Magic);
                        slayerMonsterCombatStyleMagicHourlyRate.put(a.variants[i], 0);
                    }
                }
            }
        }
    }
}
