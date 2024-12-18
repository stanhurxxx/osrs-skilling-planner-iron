package io.hurx.plugin.slayer.views.list;

import io.hurx.models.CombatStyle;
import io.hurx.models.Skills;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;
import io.hurx.plugin.slayer.SlayerOptions;
import io.hurx.repository.slayer.SlayerListRepository;
import io.hurx.repository.slayer.SlayerPlanningRepository;

import java.util.Map;
import java.util.HashMap;

/**
 * Calculator tools for slayer
 */
public class SlayerListCalculator {
    public final SlayerListRepository repository;

    private float doWeight = 0;
    private float skippedWeight = 0;

    private Map<SlayerMonsters, Map<Monsters, Float>> taskFrequencyImpact = new HashMap<>();
    private float totalTaskFrequencyImpact = 0f;

    private Map<SlayerMonsters, Map<Monsters, Float>> killsPerHour = new HashMap<>();

    private Map<SlayerMonsters, Map<Monsters, Map<Skills, Float>>> rates = new HashMap<>();

    public SlayerListCalculator(SlayerListRepository slayerListRepository) {
        this.repository = slayerListRepository;
        this.calculate();
    }

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
            if (list.blocked.get().contains(monster)) continue;
            if (list.skipped.get().contains(monster)) {
                skippedWeight += assignment.getWeight();
                continue;
            }
            else {
                doWeight += assignment.getWeight();
            }
        }

        // Calculate the impact of the task compared to all tasks in this list
        for (SlayerAssignment assignment : master.getMaster().getAssignments()) {
            SlayerMonsters monster = assignment.getMonster();
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
                float magicHitXp = Math.max(0, (((magicXpRate / 1200) - magicStyle.getBaseXp()) * 1200)); // one hit xp
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
                float variationPercentage = list.variations.get(monster, new HashMap<>()).getOrDefault(variant, 0);
                float amountPerTask = isExtended
                        ? minExtended + ((maxExtended - minExtended) / 2)
                        : assignment.getMin() + ((assignment.getMax() - assignment.getMin()) / 2);

                // Xp rates
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

    public SlayerListRepository getList() {
        SlayerListRepository list = repository.getParent().findListByFileName(repository.uuid());
        return list;
    }

    public float getXpPerHour() {
        return this.getXpPerHour((Skills)null);
    }

    /** Get the slayer xp per hour for a slayer monster */
    public float getXpPerHour(SlayerMonsters monster) {
        float taskFrequencyImpact = 0f;
        float totalXpPerHour = 0f;
        for (Monsters v : monster.getMonsters()) {
            float impact = this.taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).getOrDefault(v, 0f);
            taskFrequencyImpact += impact;
            float xpPerHour = this.rates.getOrDefault(monster, new HashMap<>()).getOrDefault(v, new HashMap<>()).getOrDefault(Skills.Slayer, 0f);
            totalXpPerHour += impact * xpPerHour;
        }
        return totalXpPerHour / taskFrequencyImpact;
    }

    public float getXpPerHour(Skills skill) {
        float xpPerHour = 0f;
        for (SlayerMonsters monster : this.rates.keySet()) {
            float effectiveXpPerHour = 0f;

            Map<Monsters, Map<Skills, Float>> monsterRates = this.rates.getOrDefault(monster, new HashMap<>());
            for (Monsters variant : monsterRates.keySet()) {
                float taskFrequencyImpactVariant = this.taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).getOrDefault(variant, 0f);

                Map<Skills, Float> variantRates = monsterRates.getOrDefault(variant, new HashMap<>());
                for (Skills variantSkill : variantRates.keySet()) {
                    if (skill == null || skill == variantSkill) {
                        float xpRate = variantRates.getOrDefault(variantSkill, 0f);
                        if (xpRate == 0f) continue;
                        effectiveXpPerHour += xpRate * taskFrequencyImpactVariant;
                    }
                }
            }

            xpPerHour += effectiveXpPerHour / this.totalTaskFrequencyImpact;
        }
        return xpPerHour;
    }

    public float getKillCount(Monsters variant) {
        return getKillCount(null, null, variant);
    }

    public float getKillCount(SlayerMonsters monster) {
        return getKillCount(null, monster, null);
    }

    public float getKillCount(SlayerMonsters monster, Monsters variant) {
        return getKillCount(null, monster, variant);
    }

    public float getKillCount(String planningUuid) {
        return getKillCount(planningUuid, null, null);
    }

    public float getKillCount(String planningUuid, Monsters variant) {
        return getKillCount(planningUuid, null, variant);
    }

    public float getKillCount(String planningUuid, SlayerMonsters monster) {
        return getKillCount(planningUuid, monster, null);
    }

    public float getKillCount(String planningUuid, SlayerMonsters monster, Monsters variant) {
        SlayerListRepository list = getList();
        if (list == null) return 0;

        // Calculate the remaining xp for the list
        float currentXP = repository.getParent().getParent().xp.get(Skills.Slayer, 0f);
        float remainingXP = 0f;
        for (SlayerPlanningRepository planning : repository.getParent().plannings.values()) {
            if (planningUuid == null) {
                if (planning.listUuid.isNull() || !planning.listUuid.isEqualTo(repository.uuid())) {
                    continue;
                }
            }
            else {
                if (planning.listUuid.isNull() || !planning.listUuid.isEqualTo(repository.uuid())) {
                    continue;
                }
                if (planning.uuid() == null || !planning.uuid().equals(planningUuid)) {
                    continue;
                }
            }
            if (!planning.listUuid.isEqualTo(repository.uuid())) continue;
            float planningStartXP = Math.max(0, planning.startXP.get() - currentXP);
            float planningEndXP = Math.max(0, planning.endXP.get() - currentXP);
            remainingXP += planningEndXP - planningStartXP;
        }

        // Calculate the effective xp per monster
        float taskFrequencyImpact = 0f;
        for (SlayerAssignment assignment : repository.master.get().getMaster().getAssignments()) {
            if (monster != null && assignment.getMonster() != monster) continue;
            float monsterTaskFrequencyImpact = 0;

            for (Monsters v : assignment.getMonster().getMonsters()) {
                if (variant != null && v != variant) continue;

                float variantTaskFrequencyImpact = getTaskFrequencyImpact(v);

                monsterTaskFrequencyImpact += variantTaskFrequencyImpact;
            }

            taskFrequencyImpact += monsterTaskFrequencyImpact;
        }

        // Only calculate when the task is done
        if (taskFrequencyImpact == 0f) return 0f;

        // Calculate the kills per hour
        float effectiveKillsPerHour = 0f;
        for (SlayerAssignment assignment : repository.master.get().getMaster().getAssignments()) {
            if (monster != null && assignment.getMonster() != monster) continue;

            for (Monsters v : assignment.getMonster().getMonsters()) {
                if (variant != null && v != variant) continue;

                float variantTaskFrequencyImpact = getTaskFrequencyImpact(v);
                float killsPerHour = this.killsPerHour.getOrDefault(assignment.getMonster(), new HashMap<>()).getOrDefault(v, 0f);

                effectiveKillsPerHour += (variantTaskFrequencyImpact / taskFrequencyImpact) * killsPerHour;
            }
        }

        // Set the remaining xp only for the monster/variant selection
        remainingXP *= (taskFrequencyImpact / this.totalTaskFrequencyImpact);

        // Calculate the hours
        float xpPerHour = this.getXpPerHour(Skills.Slayer);
        if (xpPerHour == 0) return 0f;
        float hours = remainingXP / this.getXpPerHour(Skills.Slayer);
        if (hours == 0f) return 0f;

        // The kill count
        return effectiveKillsPerHour * hours;
    }

    public float getTaskFrequencyImpact(SlayerMonsters monster, Monsters variant) {
        return this.taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).getOrDefault(variant, 0f);
    }

    public float getTaskFrequencyImpact(SlayerMonsters monster) {
        float taskFrequencyImpact = 0;
        for (Monsters monsterVariant : this.taskFrequencyImpact.getOrDefault(monster, new HashMap<>()).keySet()) {
            taskFrequencyImpact += this.taskFrequencyImpact.get(monster).getOrDefault(monsterVariant, 0f);
        }
        return taskFrequencyImpact;
    }

    public float getTaskFrequencyImpact(Monsters variant) {
        float taskFrequencyImpact = 0;
        for (SlayerMonsters monster : this.taskFrequencyImpact.keySet()) {
            for (Monsters monsterVariant : this.taskFrequencyImpact.get(monster).keySet()) {
                if (monsterVariant == variant) {
                    taskFrequencyImpact += this.taskFrequencyImpact.get(monster).getOrDefault(monsterVariant, 0f);
                }
            }
        }
        return taskFrequencyImpact;
    }

    public float getBreakEvenSkipPercentage() {
        if (getList() == null) return 0f;
        float doWeight = getList().options.contains(SlayerOptions.SlayerCape)
                ? this.doWeight * 1.1f
                : this.doWeight;
        float skipWeight = (getPointsPerTaskExcludingSkips() * doWeight / 30);
        float totalWeight = skipWeight + doWeight;
        return skipWeight / totalWeight * 100;
    }

    public float getPointsPerTaskIncludingSkips() {
        return getPointsPerTaskExcludingSkips() - (getSkipPercentage() / getBreakEvenSkipPercentage() * getPointsPerTaskExcludingSkips());
    }

    public float getPointsPerTaskExcludingSkips() {
        SlayerListRepository list = getList();
        if (list == null) return 0;
        if (list.options.contains(SlayerOptions.WesternProvincesEliteDiary) && list.master.get() == SlayerMasters.Nieve) {
            return SlayerMasters.Duradel.getMaster().calculateAveragePointsPerTask();
        }
        return list.master.get().getMaster().calculateAveragePointsPerTask();
    }

    public float getSkipPercentage() {
        if (getList() == null) return 0f;
        float doWeight = getList().options.contains(SlayerOptions.SlayerCape)
                ? this.doWeight * 1.1f
                : this.doWeight;
        float skipsPerTask = getList().options.contains(SlayerOptions.SlayerCape)
                // 10% more do tasks once slayer cape
                ? skippedWeight / (doWeight * 1.1f + skippedWeight)
                : skippedWeight / (doWeight + skippedWeight);
        return skipsPerTask * 100;
    }
}