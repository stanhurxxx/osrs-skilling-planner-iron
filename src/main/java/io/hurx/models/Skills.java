package io.hurx.models;

/**
 * All skills
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
    Melee("Melee", IconPaths.CombatStyleControlled),
    ;

    public String getName() {
        return this.name;
    }

    private String name;

    public IconPaths getIconPath() {
        return this.iconPath;
    }

    private IconPaths iconPath;

    Skills(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }
}
