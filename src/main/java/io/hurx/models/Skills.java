package io.hurx.models;

/**
 * Enum representing all skills available in the application.
 * Each skill has a name for display and an associated icon path.
 */
public enum Skills {
    Agility("Agility", IconPaths.SkillAgility),
    Attack("Attack", IconPaths.SkillAttack),
    Construction("Construction", IconPaths.SkillConstruction),
    Cooking("Cooking", IconPaths.SkillCooking),
    Crafting("Crafting", IconPaths.SkillCrafting),
    Defence("Defence", IconPaths.SkillDefence),
    Farming("Farming", IconPaths.SkillFarming),
    Firemaking("Firemaking", IconPaths.SkillFiremaking),
    Fishing("Fishing", IconPaths.SkillFishing),
    Fletching("Fletching", IconPaths.SkillFletching),
    Herblore("Herblore", IconPaths.SkillHerblore),
    Hitpoints("Hitpoints", IconPaths.SkillHitpoints),
    Hunter("Hunter", IconPaths.SkillHunter),
    Magic("Magic", IconPaths.SkillMagic),
    Mining("Mining", IconPaths.SkillMining),
    Prayer("Prayer", IconPaths.SkillPrayer),
    Ranged("Ranged", IconPaths.SkillRanged),
    Runecraft("Runecraft", IconPaths.SkillRunecraft),
    Slayer("Slayer", IconPaths.SkillSlayer),
    Smithing("Smithing", IconPaths.SkillSmithing),
    Strength("Strength", IconPaths.SkillStrength),
    Thieving("Thieving", IconPaths.SkillThieving),
    Woodcutting("Woodcutting", IconPaths.SkillWoodcutting),
    Melee("Melee", IconPaths.CombatStyleControlled);

    /**
     * The display name of the skill.
     */
    private final String name;

    /**
     * The icon path associated with the skill.
     */
    private final IconPaths iconPath;

    /**
     * Constructor to initialize the skill with its name and associated icon path.
     *
     * @param name     the display name of the skill
     * @param iconPath the icon path associated with the skill
     */
    Skills(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    /**
     * Retrieves the display name of the skill.
     *
     * @return the name of the skill as a String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the icon path associated with the skill.
     *
     * @return the IconPaths enum constant representing the icon path
     */
    public IconPaths getIconPath() {
        return this.iconPath;
    }
}
