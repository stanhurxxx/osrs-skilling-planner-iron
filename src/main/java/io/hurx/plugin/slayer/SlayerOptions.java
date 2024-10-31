package io.hurx.plugin.slayer;

import javax.swing.ImageIcon;

import io.hurx.models.IconPaths;
import io.hurx.models.items.Items;
import io.hurx.utils.Resources;

/**
 * Enumeration representing various Slayer options available in the game.
 * <p>
 * Each option has a name, description, and an associated icon representing the 
 * specific Slayer-related benefit or action.
 * </p>
 */
public enum SlayerOptions {
    /**
     * Option to use Piety during all melee tasks.
     */
    Piety("Piety", "Use piety during all melee tasks", Resources.loadImageIcon(IconPaths.PrayerPiety.getPath(), 100, 100)),
    
    /**
     * Option to use Rigour during all ranged tasks.
     */
    Rigour("Rigour", "Use rigour during all ranged tasks", Resources.loadImageIcon(IconPaths.PrayerRigour.getPath(), 100, 100)),
    
    /**
     * Option to use Eagle Eye during all ranged tasks.
     */
    EagleEye("Eagle eye", "Use eagle eye during all ranged tasks", Resources.loadImageIcon(IconPaths.PrayerEagleEye.getPath(), 100, 100)),
    
    /**
     * Option to use Augury during all magic tasks.
     */
    Augury("Augury", "Use augury during all magic tasks", Resources.loadImageIcon(IconPaths.PrayerAugury.getPath(), 100, 100)),
    
    /**
     * Option to use Mystic Might during all magic tasks.
     */
    MysticMight("Mystic Might", "Use mystic might during all magic tasks", Resources.loadImageIcon(IconPaths.PrayerMysticMight.getPath(), 100, 100)),
    
    /**
     * Option to use protection prayers during all tasks.
     */
    Protection("Protection", "Use protection prayers during all tasks", Resources.loadImageIcon(IconPaths.PrayerProtection.getPath(), 100, 100)),
    
    /**
     * Option to use Super Combat Potions for all melee tasks.
     */
    SuperCombatPotions("Super combat potions", "Use super combat potions for all melee tasks", Items.SuperCombatPotion.getItem().getIcon()),
    
    /**
     * Option to use Ranging Potions for all ranged tasks.
     */
    RangingPotions("Ranging potions", "Use ranging potions for all ranged tasks", Items.RangingPotion.getItem().getIcon()),
    
    /**
     * Option to use Super Restores when out of prayer potions.
     */
    SuperRestores("Super restores", "Use super restores when out of prayer potions", Items.SuperRestore.getItem().getIcon()),
    
    /**
     * Option to use Sanfew Serums when out of prayer potions.
     */
    SanfewSerums("Sanfew serums", "Use sanfew serums when out of prayer potions", Items.SanfewSerum.getItem().getIcon()),
    
    /**
     * Option to use Morytania Legs 4 for a 10% Slayer XP bonus in the Slayer Tower.
     */
    MoryLegs4("Morytania Legs 4", "Use morytania legs 4 for 10% slayer xp bonus in the slayer tower", Items.MorytaniaLegs4.getItem().getIcon()),
    
    /**
     * Option to use the Slayer Cape perk to always repeat tasks that are not skipped.
     */
    SlayerCape("Slayer cape perk", "Use the slayer cape perk to always repeat tasks you don't skip", Items.SlayerCape.getItem().getIcon()),
    
    /**
     * Option indicating that the Western Provinces Elite Diary has been completed, 
     * granting more points at Nieve.
     */
    WesternProvincesEliteDiary("Western Provinces Elite Diary", "Western Provinces Elite Diary completed, gives more points at Nieve", Items.WesternBanner4.getItem().getIcon());

    // A field to store the string value for the option name
    private final String name;

    // A field to store the description of the option
    private final String description;

    // A field to store the icon associated with the option
    private final ImageIcon image;

    /**
     * Constructs a SlayerOptions enum instance with a name, description, and associated icon.
     *
     * @param displayName the name of the Slayer option
     * @param description a description of the Slayer option's effects or usage
     * @param iconPath the icon associated with the Slayer option
     */
    SlayerOptions(String displayName, String description, ImageIcon iconPath) {
        this.name = displayName;
        this.description = description;
        this.image = iconPath;
    }

    /**
     * Gets the name of the Slayer option.
     *
     * @return the name of the Slayer option
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the Slayer option.
     *
     * @return the description of the Slayer option
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the icon associated with the Slayer option.
     *
     * @return the icon of the Slayer option
     */
    public ImageIcon getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the name of the option
    }
}
