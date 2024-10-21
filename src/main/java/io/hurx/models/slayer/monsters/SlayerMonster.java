package io.hurx.models.slayer.monsters;

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

    // Constructor
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
    }
}