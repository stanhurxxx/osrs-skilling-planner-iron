package io.hurx.plugin.slayer.views.list;

import io.hurx.models.CombatStyle;
import io.hurx.models.Skills;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.plugin.slayer.repository.SlayerListRepository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Provides calculator tools for Slayer tasks.
 * <p>
 * This class calculates various metrics related to Slayer assignments,
 * including experience rates, kill counts, and task frequency impacts.
 * </p>
 */
public class SlayerListCalculator {
    /**
     * List of all instances of SlayerListCalculator.
     */
    public static List<SlayerListCalculator> instances = new ArrayList<>();
    
    /**
     * The repository associated with this calculator, used to retrieve Slayer list data.
     */
    public final SlayerListRepository repository;

    /**
     * Weight of tasks to be done.
     * <p>
     * This value is calculated based on the active Slayer assignments and their weights.
     * </p>
     */
    private float doWeight = 0;

    /**
     * Weight of tasks that have been skipped.
     * <p>
     * This value is calculated based on the Slayer assignments that have been skipped.
     * </p>
     */
    private float skippedWeight = 0;

    /**
     * A map to hold the impact of task frequency for each Slayer monster.
     * <p>
     * Each Slayer monster maps to another map of its variants and their respective frequency impacts.
     * </p>
     */
    private Map<SlayerMonsters, Map<Monsters, Float>> taskFrequencyImpact = new HashMap<>();

    /**
     * Total impact of task frequency across all Slayer monsters.
     * <p>
     * This value is the sum of all individual task frequency impacts.
     * </p>
     */
    private float totalTaskFrequencyImpact = 0;

    /**
     * A map to hold the calculated kills per hour for each Slayer monster.
     * <p>
     * Each Slayer monster maps to another map of its variants and their kills per hour.
     * </p>
     */
    private Map<SlayerMonsters, Map<Monsters, Float>> killsPerHour = new HashMap<>();

    /**
     * A map to hold the XP rates for each Slayer monster variant and skill.
     * <p>
     * Each Slayer monster maps to another map of its variants, which then maps to another
     * map of skills and their respective XP rates.
     * </p>
     */
    private Map<SlayerMonsters, Map<Monsters, Map<Skills, Float>>> rates = new HashMap<>();


    /**
     * Constructs a SlayerListCalculator for the given SlayerListRepository.
     *
     * @param slayerListRepository the SlayerListRepository to be associated with this calculator
     */
    private SlayerListCalculator(SlayerListRepository slayerListRepository) {
        this.repository = slayerListRepository;
        calculate();
    }

    /**
     * Performs calculations for Slayer metrics based on the current list and assignments.
     * <p>
     * This method calculates the weights, XP rates, kills per hour, and task frequency impacts
     * based on Slayer assignments and monsters.
     * </p>
     */
    public void calculate() {
        SlayerListRepository list = getList();

        if (list == null) return;

        SlayerMasters master = list.master.get();
        taskFrequencyImpact = new HashMap<>();
        totalTaskFrequencyImpact = 0;
        rates = new HashMap<>();

        // Calculate weights
        doWeight = 0;
        skippedWeight = 0;
        for (SlayerAssignment assignment : master.getMaster().getAssignments()) {
            SlayerMonsters monster = assignment.getMonster();
            if (list.blocked.contains(monster)) continue;
            if (list.skipped.contains(monster)) {
                skippedWeight += assignment.getWeight();
                continue;
            } else {
                doWeight += assignment.getWeight();
            }
            
            // Calculate the impact of the task compared to all tasks in this list
            taskFrequencyImpact.put(assignment.getMonster(), taskFrequencyImpact.getOrDefault(assignment.getMonster(), new HashMap<>()));
            for (Monsters variant : monster.getMonsters()) {
                rates.put(monster, rates.getOrDefault(monster, new HashMap<>()));
                rates.get(monster).put(variant, rates.get(monster).getOrDefault(variant, new HashMap<>()));

                int hp = variant.getStats().getHitpoints();
                float ratio = (100 + variant.getStats().getXPBoostPercentage()) / 100;
                float damage = 0;

                CombatStyle meleeStyle = list.meleeStyles.get(variant, CombatStyle.Attack);
                float meleeXpRate = list.meleeHourlyRates.get(variant, 0);
                float hitpointsXpRate = meleeXpRate / 3;
                float defenceXpRate = meleeStyle == CombatStyle.Defence
                    ? meleeXpRate
                    : meleeStyle == CombatStyle.Controlled
                        ? meleeXpRate / 3
                        : 0;
                damage += meleeXpRate / 4;

                CombatStyle rangedStyle = list.rangedStyles.get(variant, CombatStyle.Ranged);
                float rangedXpRate = list.rangedHourlyRates.get(variant, 0);
                defenceXpRate += CombatStyle.getDefensiveRanged().contains(rangedStyle) ? rangedXpRate : 0;
                hitpointsXpRate += (rangedXpRate + defenceXpRate) / 3; 
                damage += (rangedXpRate + defenceXpRate) / 4;

                CombatStyle magicStyle = list.magicStyles.get(variant, CombatStyle.Magic);
                float magicXpRate = list.magicHourlyRates.get(variant, 0);
                float magicHitXp = (((magicXpRate / 1200) - magicStyle.getBaseXp()) * 1200); // one hit xp
                float magicHit = CombatStyle.getDefensiveMagic().contains(magicStyle) ? (magicHitXp / 1.33f) : magicHitXp / 2;
                defenceXpRate += CombatStyle.getDefensiveMagic().contains(magicStyle) ? (magicHitXp / 1.33f) * (1.0f / 1.33f) : 0;

                // This is the damage per hour
                damage += magicHit;

                // Remove the ratio
                damage /= ratio;

                // This is the kills per hour
                float killsPerHour = damage / hp;
                this.killsPerHour.put(monster, this.killsPerHour.getOrDefault(monster, new HashMap<>()));
                this.killsPerHour.get(monster).put(variant, killsPerHour);
                boolean isExtended = list.extendedMonsters.get(monster, false);
                int maxExtended = assignment.getMaxExtended() == null ? 0 : assignment.getMaxExtended();
                int minExtended = assignment.getMinExtended() == null ? 0 : assignment.getMinExtended();
                float variationPercentage = list.variations.get(monster, new Repository.Property.Map<>()).get(variant, 0);
                float amountPerTask = isExtended
                    ? minExtended + ((maxExtended - minExtended) / 2)
                    : assignment.getMin() + ((assignment.getMax() - assignment.getMin()) / 2);

                // XP rates
                rates.get(monster).get(variant).put(Skills.Attack, 
                    meleeStyle == CombatStyle.Attack
                        ? meleeXpRate
                        : meleeStyle == CombatStyle.Controlled
                            ? meleeXpRate / 3
                            : 0
                );
                rates.get(monster).get(variant).put(Skills.Strength, 
                    meleeStyle == CombatStyle.Strength
                        ? meleeXpRate
                        : meleeStyle == CombatStyle.Controlled
                            ? meleeXpRate / 3
                            : 0
                );
                rates.get(monster).get(variant).put(Skills.Defence, defenceXpRate);
                rates.get(monster).get(variant).put(Skills.Hitpoints, hitpointsXpRate);
                rates.get(monster).get(variant).put(Skills.Slayer, damage * ratio);
                rates.get(monster).get(variant).put(Skills.Ranged, rangedXpRate);
                rates.get(monster).get(variant).put(Skills.Magic, magicXpRate);

                // A value indicating the impact and frequency of this variant of slayer monster, which can be compared to all others.
                taskFrequencyImpact.get(monster).put(variant, (amountPerTask / killsPerHour) * assignment.getWeight() / doWeight * (variationPercentage / 100));
                totalTaskFrequencyImpact += taskFrequencyImpact.get(monster).get(variant);
            }
        }
    }

    /**
     * Retrieves the SlayerListRepository associated with this calculator.
     *
     * @return the SlayerListRepository, or null if it cannot be found
     */
    public SlayerListRepository getList() {
        SlayerListRepository list = repository.getParent().findListByFileName(repository.getFileName());
        return list;
    }

    /**
     * Calculates the overall XP earned per hour for Slayer tasks.
     *
     * @return the XP per hour for all skills
     */
    public float getXpPerHour() {
        return this.getXpPerHour(null);
    }

    /**
     * Calculates the XP earned per hour for a specific skill.
     *
     * @param skill the skill to calculate XP for, or null for all skills
     * @return the XP per hour for the specified skill
     */
    public float getXpPerHour(Skills skill) {
        float xpPerHour = 0;
        for (SlayerMonsters monster : this.rates.keySet()) {
            float effectiveXpPerHour = 0;

            Map<Monsters, Map<Skills, Float>> monsterRates = this.rates.getOrDefault(monster, new HashMap<>());
            for (Monsters variant : monsterRates.keySet()) {
                float taskFrequencyImpactVariant = this.taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).getOrDefault(variant, 0f);
                
                Map<Skills, Float> variantRates = monsterRates.getOrDefault(variant, new HashMap<>());
                for (Skills variantSkill : variantRates.keySet()) {
                    if (skill == null || skill == variantSkill) {
                        float xpRate = variantRates.get(variantSkill);
                        effectiveXpPerHour += xpRate * taskFrequencyImpactVariant;
                    }
                }
            }
            xpPerHour += effectiveXpPerHour;
        }
        return xpPerHour;
    }

    /**
     * Returns the total weight for active tasks.
     *
     * @return the weight of tasks to be done
     */
    public float getDoWeight() {
        return doWeight;
    }

    /**
     * Returns the total weight for skipped tasks.
     *
     * @return the weight of skipped tasks
     */
    public float getSkippedWeight() {
        return skippedWeight;
    }

    /**
     * Retrieves the kill count for a specific monster variant.
     *
     * @param monster the monster variant to get the kill count for
     * @return the kill count, or 0 if not found
     */
    public float getKillCount(Monsters monster) {
        for (Map<Monsters, Float> value : this.killsPerHour.values()) {
            if (value.containsKey(monster)) {
                return value.get(monster);
            }
        }
        return 0;
    }

    /**
     * Retrieves the calculated task frequency impact for a specific monster.
     *
     * @param monster the monster to get the task frequency impact for
     * @return the task frequency impact, or 0 if not found
     */
    public float getTaskFrequencyImpact(SlayerMonsters monster) {
        return taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).values().stream().reduce(0f, Float::sum);
    }

    /**
     * Retrieves the total task frequency impact across all monsters.
     *
     * @return the total task frequency impact
     */
    public float getTotalTaskFrequencyImpact() {
        return totalTaskFrequencyImpact;
    }
}
