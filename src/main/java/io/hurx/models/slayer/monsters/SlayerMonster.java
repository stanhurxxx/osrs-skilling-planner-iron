package io.hurx.models.slayer.monsters;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a slayer monster
 */
public class SlayerMonster {
    public final SlayerMonsters name;
    public final String iconSrc;
    public final int minAmount;
    public final int maxAmount;
    public final Integer minAmountExtended;
    public final Integer maxAmountExtended;
    public final SlayerMonsterRequirements[] requirements;
    public final int weight;
    public final SlayerMonsters[] variants;

    // Constructor
    public SlayerMonster(
        SlayerMonsters name,
        String iconSrc,
        int minAmount,
        int maxAmount,
        Integer minAmountExtended,
        Integer maxAmountExtended,
        int weight,
        SlayerMonsterRequirements[] requirements,
        SlayerMonsters[] variants
    ) {
        this.name = name;
        this.iconSrc = iconSrc;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.minAmountExtended = minAmountExtended;
        this.weight = weight;
        this.maxAmountExtended = maxAmountExtended;
        this.requirements = requirements;
        if (variants == null) {
            this.variants = new SlayerMonsters[] { name };
        }
        else {
            List<SlayerMonsters> list = new ArrayList<>();
            list.add(name);
            for (int i = 0; i < variants.length; i ++) {
                list.add(variants[i]);
            }
            this.variants = list.toArray(new SlayerMonsters[list.size()]);
        }
    }

    public SlayerMonster(
        SlayerMonsters name,
        String iconSrc,
        int minAmount,
        int maxAmount,
        Integer minAmountExtended,
        Integer maxAmountExtended,
        int weight,
        SlayerMonsterRequirements[] requirements
    ) {
        this.name = name;
        this.iconSrc = iconSrc;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.minAmountExtended = minAmountExtended;
        this.weight = weight;
        this.maxAmountExtended = maxAmountExtended;
        this.requirements = requirements;
        this.variants = new SlayerMonsters[] {
            name
        };
    }
}