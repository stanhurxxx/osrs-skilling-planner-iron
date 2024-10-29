package io.hurx.models;

/**
 * All the views
 */
public enum ViewNames {
    Overview("Overview", IconPaths.SkillOverall),
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
    LoggedOut("Woodcutting", IconPaths.SkillEHP),
    ;

    public String getName() {
        return name;
    }
    private final String name;

    public IconPaths getIconPath() {
        return iconPath;
    }
    private final IconPaths iconPath;

    ViewNames(String displayName, IconPaths iconPath) {
        this.name = displayName;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
