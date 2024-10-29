package io.hurx.cache.data;

import io.hurx.models.CombatStyle;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A slayer list
 */
public class SlayerListData {
    private String uuid = UUID.randomUUID().toString(); 
    private String name = "Default slayer list";
    private SlayerMasters master = SlayerMasters.Duradel;
    private SlayerMonsters[] blocked = new SlayerMonsters[] {};
    private SlayerMonsters[] skipped = new SlayerMonsters[] {};
    private SlayerMonsters selectedMonster = null;
    private Monsters selectedVariant = null;
    private Map<SlayerMonsters, Map<Monsters, Integer>> variations;
    private Map<SlayerMonsters, Monsters[]> variationOrders;
    private Map<Monsters, Boolean> extendedMonsers;
    private Map<Monsters, CombatStyle> meleeStyles;
    private Map<Monsters, Integer> meleeHourlyRates;
    private Map<Monsters, CombatStyle> rangedStyles;
    private Map<Monsters, Integer> rangedHourlyRates;
    private Map<Monsters, CombatStyle> magicStyles;
    private Map<Monsters, Integer> magicHourlyRates;
    private Map<Monsters, Integer> monsterCompletionTimes;

    // Getters and setters for each field

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public SlayerMasters getMaster() {
        return master;
    }
    public void setMaster(SlayerMasters master) {
        this.master = master;
    }

    public SlayerMonsters[] getBlocked() {
        return blocked;
    }
    public void setBlocked(SlayerMonsters[] blocked) {
        this.blocked = blocked;
    }

    public SlayerMonsters[] getSkipped() {
        return skipped;
    }
    public void setSkipped(SlayerMonsters[] skipped) {
        this.skipped = skipped;
    }

    public SlayerMonsters getSelectedMonster() {
        return selectedMonster;
    }
    public void setSelectedMonster(SlayerMonsters selectedMonster) {
        this.selectedMonster = selectedMonster;
    }

    public Monsters getSelectedVariant() {
        return selectedVariant;
    }
    public void setSelectedVariant(Monsters selectedVariant) {
        this.selectedVariant = selectedVariant;
    }

    public Map<SlayerMonsters, Map<Monsters, Integer>> getVariations() {
        return variations;
    }
    public void setVariations(Map<SlayerMonsters, Map<Monsters, Integer>> variations) {
        this.variations = variations;
    }

    public Map<SlayerMonsters, Monsters[]> getVariationOrders() {
        return variationOrders;
    }
    public void setVariationOrders(Map<SlayerMonsters, Monsters[]> variationOrders) {
        this.variationOrders = variationOrders;
    }

    public Map<Monsters, Boolean> getExtendedMonsers() {
        return extendedMonsers;
    }
    public void setExtendedMonsers(Map<Monsters, Boolean> extendedMonsers) {
        this.extendedMonsers = extendedMonsers;
    }

    public Map<Monsters, CombatStyle> getMeleeStyles() {
        return meleeStyles;
    }
    public void setMeleeStyles(Map<Monsters, CombatStyle> meleeStyles) {
        this.meleeStyles = meleeStyles;
    }

    public Map<Monsters, Integer> getMeleeHourlyRates() {
        return meleeHourlyRates;
    }
    public void setMeleeHourlyRates(Map<Monsters, Integer> meleeHourlyRates) {
        this.meleeHourlyRates = meleeHourlyRates;
    }

    public Map<Monsters, CombatStyle> getRangedStyles() {
        return rangedStyles;
    }
    public void setRangedStyles(Map<Monsters, CombatStyle> rangedStyles) {
        this.rangedStyles = rangedStyles;
    }

    public Map<Monsters, Integer> getRangedHourlyRates() {
        return rangedHourlyRates;
    }
    public void setRangedHourlyRates(Map<Monsters, Integer> rangedHourlyRates) {
        this.rangedHourlyRates = rangedHourlyRates;
    }

    public Map<Monsters, CombatStyle> getMagicStyles() {
        return magicStyles;
    }
    public void setMagicStyles(Map<Monsters, CombatStyle> magicStyles) {
        this.magicStyles = magicStyles;
    }

    public Map<Monsters, Integer> getMagicHourlyRates() {
        return magicHourlyRates;
    }
    public void setMagicHourlyRates(Map<Monsters, Integer> magicHourlyRates) {
        this.magicHourlyRates = magicHourlyRates;
    }

    public Map<Monsters, Integer> getMonsterCompletionTimes() {
        return monsterCompletionTimes;
    }
    public void setMonsterCompletionTimes(Map<Monsters, Integer> monsterCompletionTimes) {
        this.monsterCompletionTimes = monsterCompletionTimes;
    }

    public SlayerListData() {
        variations = new HashMap<>();
        extendedMonsers = new HashMap<>();
        meleeStyles = new HashMap<>();
        meleeHourlyRates = new HashMap<>();
        rangedStyles = new HashMap<>();
        rangedHourlyRates = new HashMap<>();
        magicStyles = new HashMap<>();
        magicHourlyRates = new HashMap<>();
        variationOrders = new HashMap<>();
        monsterCompletionTimes = new HashMap<>();

        // TODO: dummy
        for (SlayerMonsters monster : SlayerMonsters.values()) {
            Map<Monsters, Integer> variation = new HashMap<>();
            variationOrders.put(monster, monster.getMonsters());

            Monsters[] monsters = monster.getMonsters();
            for (int i = 0; i < monsters.length; i ++) {
                Monsters variant = monsters[i];
                variation.put(variant, i == 0 ? 100 : 0);

                if (variant.getStats().getSlayer() == null) {
                    meleeStyles.put(variant, CombatStyle.Attack);
                    meleeHourlyRates.put(variant, 110_000);
                    rangedStyles.put(variant, CombatStyle.Ranged);
                    rangedHourlyRates.put(variant, 0);
                    magicStyles.put(variant, CombatStyle.Magic);
                    magicHourlyRates.put(variant, 0);
                }
                else {
                    meleeStyles.put(variant, CombatStyle.None);
                    meleeHourlyRates.put(variant, 0);
                    rangedStyles.put(variant, CombatStyle.Ranged);
                    rangedHourlyRates.put(variant, variant.getStats().getHitpoints() * 4);
                    magicStyles.put(variant, CombatStyle.None);
                    meleeHourlyRates.put(variant, 0);
                    monsterCompletionTimes.put(variant, variant == Monsters.Zuk ? 75 : 30);
                }
            }

            variations.put(monster, variation);
        }
    }
}
