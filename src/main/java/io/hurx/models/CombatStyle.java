package io.hurx.models;

import java.util.ArrayList;
import java.util.List;

public enum CombatStyle {
    None("None", IconPaths.CombatStyleNone),
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
    Magic("Magic", IconPaths.CombatStyleMagic),
    IceBurst("Ice burst", IconPaths.CombatStyleIceBurst),
    IceBurstDefensive("Ice burst (defensive)", IconPaths.CombatStyleIceBurstDefensive),
    BloodBurst("Blood burst", IconPaths.CombatStyleBloodBurst),
    BloodBurstDefensive("Blood burst (defensive)", IconPaths.CombatStyleBloodBurstDefensive),
    SmokeBurst("Smoke burst", IconPaths.CombatStyleSmokeBurst),
    SmokeBurstDefensive("Smoke burst (defensive)", IconPaths.CombatStyleSmokeBurstDefensive),
    ShadowBurst("Shadow burst", IconPaths.CombatStyleShadowBurst),
    ShadowBurstDefensive("Shadow burst (defensive)", IconPaths.CombatStyleShadowBurstDefensive),
    IceBarrage("Ice barrage", IconPaths.CombatStyleIceBarrage),
    IceBarrageDefensive("Ice barrage (defensive)", IconPaths.CombatStyleIceBarrageDefensive),
    BloodBarrage("Blood barrage", IconPaths.CombatStyleBloodBarrage),
    BloodBarrageDefensive("Blood barrage (defensive)", IconPaths.CombatStyleBloodBarrageDefensive),
    SmokeBarrage("Smoke barrage", IconPaths.CombatStyleSmokeBarrage),
    SmokeBarrageDefensive("Smoke barrage (defensive)", IconPaths.CombatStyleSmokeBarrageDefensive),
    ShadowBarrage("Shadow barrage", IconPaths.CombatStyleShadowBarrage),
    ShadowBarrageDefensive("Shadow barrage (defensive)", IconPaths.CombatStyleShadowBarrageDefensive);

    public static List<CombatStyle> getMeleeStyles() {
        return melee;
    }
    private static List<CombatStyle> melee = List.of(new CombatStyle[] {
        CombatStyle.Controlled,
        CombatStyle.Attack,
        CombatStyle.Strength,
        CombatStyle.Defence
    });

    public static List<CombatStyle> getRangedStyles() {
        return ranged;
    }
    private static List<CombatStyle> ranged = List.of(new CombatStyle[] {
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

    public static List<CombatStyle> getMagicStyles() {
        return magic;
    }
    private static List<CombatStyle> magic = List.of(new CombatStyle[] {
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

    public String getDisplayName() {
        return displayName;
    }

    private final String displayName;

    public IconPaths getIconPath() {
        return iconPath;
    }

    private final IconPaths iconPath;

    // Constructor for the enum
    CombatStyle(String displayName, IconPaths iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return displayName; // Override toString to return the string value
    }
}
