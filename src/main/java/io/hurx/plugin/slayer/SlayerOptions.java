package io.hurx.plugin.slayer;

import javax.swing.ImageIcon;

import io.hurx.models.IconPaths;
import io.hurx.models.items.Items;
import io.hurx.utils.Resources;

/**
 * All the views
 */
public enum SlayerOptions {
    Piety("Piety", "Use piety during all melee tasks", Resources.loadImageIcon(IconPaths.PrayerPiety.getPath(), 100, 100)),
    Rigour("Rigour", "Use rigour during all ranged tasks", Resources.loadImageIcon(IconPaths.PrayerRigour.getPath(), 100, 100)),
    EagleEye("Eagle eye", "Use eagle eye during all ranged tasks", Resources.loadImageIcon(IconPaths.PrayerEagleEye.getPath(), 100, 100)),
    Augury("Augury", "Use augury during all magic tasks", Resources.loadImageIcon(IconPaths.PrayerAugury.getPath(), 100, 100)),
    MysticMight("Mystic Might", "Use mystic might during all magic tasks", Resources.loadImageIcon(IconPaths.PrayerMysticMight.getPath(), 100, 100)),
    Protection("Protection", "Use protection prayers during all tasks", Resources.loadImageIcon(IconPaths.PrayerProtection.getPath(), 100, 100)),
    SuperCombatPotions("Super combat potions", "Use super combat potions for all melee tasks", Items.SuperCombatPotion.getItem().getIcon()),
    RangingPotions("Ranging potions", "Use ranging potions for all ranged tasks", Items.RangingPotion.getItem().getIcon()),
    SuperRestores("Super restores", "Use super restores when out of prayer potions", Items.SuperRestore.getItem().getIcon()),
    SanfewSerums("Sanfew serums", "Use sanfew serums when out of prayer potions", Items.SanfewSerum.getItem().getIcon()),
    MoryLegs4("Morytania Legs 4", "Use morytania legs 4 for 10% slayer xp bonus in the slayer tower", Items.MorytaniaLegs4.getItem().getIcon()),
    SlayerCape("Slayer cape perk", "Use the slayer cape perk to always repeat tasks you don't skip", Items.SlayerCape.getItem().getIcon()),
    WesternProvincesEliteDiary("Western Provinces Elite Diary", "Western Provinces Elite Diary completed, gives more points at Nieve", Items.WesternBanner4.getItem().getIcon()),
    ;

    // Getter method to access the string value
    public String getName() {
        return name;
    }

    // A field to store the string value
    private final String name;

    public String getDescription() {
        return description;
    }

    private final String description;

    public ImageIcon getImage() {
        return image;
    }

    private final ImageIcon image;

    // Constructor for the enum
    SlayerOptions(String displayName, String description, ImageIcon iconPath) {
        this.name = displayName;
        this.description = description;
        this.image = iconPath;
    }

    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
