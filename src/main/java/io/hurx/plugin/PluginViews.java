package io.hurx.plugin;

import java.util.List;

import io.hurx.models.IconPaths;
import io.hurx.models.views.View;
import io.hurx.models.views.Views;

/**
 * Represents all the views available in the Ironman Skilling Planner plugin.
 * Each view is associated with a name and an icon path.
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
    LoggedOut("LoggedOut", IconPaths.SkillEHP);

    /** 
     * The name of the view.
     */
    private final String name;

    /** 
     * The icon path associated with the view.
     */
    private final IconPaths iconPath;

    /**
     * Constructs a PluginViews enum constant with the specified name and icon path.
     *
     * @param name the name of the view.
     * @param iconPath the icon path associated with the view.
     */
    PluginViews(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    /**
     * Constructs a PluginViews enum constant with the specified name, icon path, 
     * and additional elements. This constructor is currently unused and can be
     * removed or implemented as needed.
     *
     * @param name the name of the view.
     * @param iconPath the icon path associated with the view.
     * @param elements additional elements associated with the view.
     */
    PluginViews(String name, IconPaths iconPath, List<View.Element> elements) {
        this.name = name;
        this.iconPath = iconPath;
        // Note: elements are not stored; consider implementing this if needed.
    }

    /**
     * Gets the parent view of this view.
     * 
     * @return null, as this implementation does not have parent views.
     */
    public Views getParent() {
        return null;
    }

    /**
     * Gets the name of the view.
     *
     * @return the name of the view.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the icon path associated with the view.
     *
     * @return the icon path of the view.
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    /**
     * Returns a string representation of the view, which is its name.
     *
     * @return the name of the view.
     */
    @Override
    public String toString() {
        return name;
    }
}
