package io.hurx.models;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the different combat styles available in the game.
 * <p>
 * Each combat style has a name, an associated icon, and optional base experience points (XP).
 * The enum also provides methods to retrieve lists of combat styles categorized by type,
 * such as melee, ranged, and magic.
 * </p>
 */
public enum CombatStyle {
    None("None", IconPaths.CombatStyleNone),
    DefenceLast("Defence Last", IconPaths.SkillEHP),
    Controlled("Controlled", IconPaths.CombatStyleControlled),
    Attack("Attack", IconPaths.CombatStyleAttack),
    Strength("Strength", IconPaths.CombatStyleStrength),
    Defence("Defence", IconPaths.CombatStyleDefence),
    Ranged("Ranged", IconPaths.CombatStyleRanged),
    RangedDefensive("Ranged (defensive)", IconPaths.CombatStyleRangedDefensive),
    RegChinsShort("Reg. chins (short)", IconPaths.CombatStyleRegChinsShort),
    RegChinsMedium("Reg. chins (medium)", IconPaths.CombatStyleRegChinsMedium),
    RegChinsLong("Reg. chins (long)", IconPaths.CombatStyleRegChinsLong),
    RedChinsShort("Red. chins (short)", IconPaths.CombatStyleRedChinsShort),
    RedChinsMedium("Red. chins (medium)", IconPaths.CombatStyleRedChinsMedium),
    RedChinsLong("Red. chins (long)", IconPaths.CombatStyleRedChinsLong),
    BlackChinsShort("Black. chins (short)", IconPaths.CombatStyleBlackChinsShort),
    BlackChinsMedium("Black. chins (medium)", IconPaths.CombatStyleBlackChinsMedium),
    BlackChinsLong("Black. chins (long)", IconPaths.CombatStyleBlackChinsLong),
    Magic("Magic", IconPaths.CombatStyleMagic, 40),
    IceBurst("Ice burst", IconPaths.CombatStyleIceBurst, 40),
    IceBurstDefensive("Ice burst (defensive)", IconPaths.CombatStyleIceBurstDefensive, 40),
    BloodBurst("Blood burst", IconPaths.CombatStyleBloodBurst, 39),
    BloodBurstDefensive("Blood burst (defensive)", IconPaths.CombatStyleBloodBurstDefensive, 39),
    SmokeBurst("Smoke burst", IconPaths.CombatStyleSmokeBurst, 36),
    SmokeBurstDefensive("Smoke burst (defensive)", IconPaths.CombatStyleSmokeBurstDefensive, 36),
    ShadowBurst("Shadow burst", IconPaths.CombatStyleShadowBurst, 37),
    ShadowBurstDefensive("Shadow burst (defensive)", IconPaths.CombatStyleShadowBurstDefensive, 37),
    IceBarrage("Ice barrage", IconPaths.CombatStyleIceBarrage, 52),
    IceBarrageDefensive("Ice barrage (defensive)", IconPaths.CombatStyleIceBarrageDefensive, 52),
    BloodBarrage("Blood barrage", IconPaths.CombatStyleBloodBarrage, 51),
    BloodBarrageDefensive("Blood barrage (defensive)", IconPaths.CombatStyleBloodBarrageDefensive, 51),
    SmokeBarrage("Smoke barrage", IconPaths.CombatStyleSmokeBarrage, 48),
    SmokeBarrageDefensive("Smoke barrage (defensive)", IconPaths.CombatStyleSmokeBarrageDefensive, 48),
    ShadowBarrage("Shadow barrage", IconPaths.CombatStyleShadowBarrage, 48),
    ShadowBarrageDefensive("Shadow barrage (defensive)", IconPaths.CombatStyleShadowBarrageDefensive, 48);

    /**
     * List of melee combat styles.
     *
     * @return a list containing melee combat styles.
     */
    public static List<CombatStyle> getMeleeStyles() {
        return melee;
    }
    private static List<CombatStyle> melee = Arrays.asList(new CombatStyle[]{
        CombatStyle.Controlled,
        CombatStyle.Attack,
        CombatStyle.Strength,
        CombatStyle.Defence,
        CombatStyle.DefenceLast
    });

    /**
     * List of defensive ranged combat styles.
     *
     * @return a list containing defensive ranged combat styles.
     */
    public static List<CombatStyle> getDefensiveRanged() {
        return defensiveRanged;
    }
    private static List<CombatStyle> defensiveRanged = Arrays.asList(new CombatStyle[]{
        CombatStyle.RegChinsLong,
        CombatStyle.RedChinsLong,
        CombatStyle.BlackChinsLong,
    });

    /**
     * List of ranged combat styles.
     *
     * @return a list containing ranged combat styles.
     */
    public static List<CombatStyle> getRangedStyles() {
        return ranged;
    }
    private static List<CombatStyle> ranged = Arrays.asList(new CombatStyle[]{
        CombatStyle.Ranged,
        CombatStyle.RangedDefensive,
        CombatStyle.RegChinsShort,
        CombatStyle.RegChinsMedium,
        CombatStyle.RegChinsLong,
        CombatStyle.RedChinsShort,
        CombatStyle.RedChinsMedium,
        CombatStyle.RedChinsLong,
        CombatStyle.BlackChinsShort,
        CombatStyle.BlackChinsMedium,
        CombatStyle.BlackChinsLong,
    });

    /**
     * List of magic combat styles.
     *
     * @return a list containing magic combat styles.
     */
    public static List<CombatStyle> getMagicStyles() {
        return magic;
    }
    private static List<CombatStyle> magic = Arrays.asList(new CombatStyle[]{
        CombatStyle.Magic,
        CombatStyle.IceBurst,
        CombatStyle.IceBurstDefensive,
        CombatStyle.BloodBurst,
        CombatStyle.BloodBurstDefensive,
        CombatStyle.SmokeBurst,
        CombatStyle.SmokeBurstDefensive,
        CombatStyle.ShadowBurst,
        CombatStyle.ShadowBurstDefensive,
        CombatStyle.IceBarrage,
        CombatStyle.IceBarrageDefensive,
        CombatStyle.BloodBarrage,
        CombatStyle.BloodBarrageDefensive,
        CombatStyle.SmokeBarrage,
        CombatStyle.SmokeBarrageDefensive,
        CombatStyle.ShadowBarrage,
        CombatStyle.ShadowBarrageDefensive,
    });

    /**
     * List of defensive magic combat styles.
     *
     * @return a list containing defensive magic combat styles.
     */
    public static List<CombatStyle> getDefensiveMagic() {
        return defensiveMagic;
    }
    private static List<CombatStyle> defensiveMagic = Arrays.asList(new CombatStyle[]{
        CombatStyle.IceBurstDefensive,
        CombatStyle.BloodBurstDefensive,
        CombatStyle.SmokeBurstDefensive,
        CombatStyle.ShadowBurstDefensive,
        CombatStyle.IceBarrageDefensive,
        CombatStyle.BloodBarrageDefensive,
        CombatStyle.SmokeBarrageDefensive,
        CombatStyle.ShadowBarrageDefensive
    });

    /**
     * Retrieves the name of the combat style.
     *
     * @return the name of the combat style.
     */
    public String getName() {
        return name;
    }

    private final String name;

    /**
     * Retrieves the icon path associated with the combat style.
     *
     * @return an {@link IconPaths} object representing the icon for this combat style.
     */
    public IconPaths getIconPath() {
        return iconPath;
    }

    private final IconPaths iconPath;

    /**
     * Retrieves the base experience points (XP) for this combat style.
     *
     * @return the base XP for the combat style.
     */
    public int getBaseXp() {
        return baseXp;
    }

    private int baseXp = 0;

    /**
     * Constructor for the enum, initializing the combat style with a name and icon path.
     *
     * @param name     the name of the combat style.
     * @param iconPath the icon path associated with the combat style.
     */
    CombatStyle(String name, IconPaths iconPath) {
        this.name = name;
        this.iconPath = iconPath;
        this.baseXp = 0;
    }

    /**
     * Constructor for the enum, initializing the combat style with a name, icon path, and base experience points.
     *
     * @param name     the name of the combat style.
     * @param iconPath the icon path associated with the combat style.
     * @param baseXp   the base experience points for the combat style.
     */
    CombatStyle(String name, IconPaths iconPath, int baseXp) {
        this.name = name;
        this.iconPath = iconPath;
        this.baseXp = baseXp;
    }

    /**
     * Returns the string representation of the combat style, which is its name.
     *
     * @return the name of the combat style as a string.
     */
    @Override
    public String toString() {
        return name; // Override toString to return the string value
    }
}
