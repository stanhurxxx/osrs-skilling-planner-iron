package io.hurx.plugin;

import java.util.List;

import io.hurx.models.IconPaths;
import io.hurx.models.views.View;
import io.hurx.models.views.Views;

/**
 * All the views
 */
public enum PluginViews implements Views {
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

    public Views getParent() {
        return null;
    }

    public String getName() {
        return name;
    }
    private final String name;

    public IconPaths getIconPath() {
        return iconPath;
    }
    private final IconPaths iconPath;

    PluginViews(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    PluginViews(String name, IconPaths iconPath, List<View.Element> elements) {
        this.name = name;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
