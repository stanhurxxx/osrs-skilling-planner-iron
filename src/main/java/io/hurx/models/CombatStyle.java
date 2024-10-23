package io.hurx.models;

public enum CombatStyle {
    None("None", "icons/combat-styles/none.png"),
    Controlled("Controlled", "icons/combat-styles/controlled.png"),
    Attack("Attack", "icons/skills/attack.png"),
    Strength("Strength", "icons/skills/strength.png"),
    Defence("Defence", "icons/skills/defence.png"),
    Ranged("Ranged", "icons/skills/ranged.png"),
    RangedDefensive("Ranged (defensive)", "icons/skills/ranged.png"),
    RegChinsShort("Reg. chins (short)", "icons/combat-styles/chinchompa.png"),
    RegChinsMedium("Reg. chins (medium)", "icons/combat-styles/chinchompa.png"),
    RegChinsLong("Reg. chins (long)", "icons/combat-styles/chinchompa.png"),
    RedChinsShort("Red. chins (short)", "icons/combat-styles/red-chinchompa.png"),
    RedChinsMedium("Red. chins (medium)", "icons/combat-styles/red-chinchompa.png"),
    RedChinsLong("Red. chins (long)", "icons/combat-styles/red-chinchompa.png"),
    BlackChinsShort("Black. chins (short)", "icons/combat-styles/black-chinchompa.png"),
    BlackChinsMedium("Black. chins (medium)", "icons/combat-styles/black-chinchompa.png"),
    BlackChinsLong("Black. chins (long)", "icons/combat-styles/black-chinchompa.png"),
    Magic("Magic", "icons/skills/magic.png"),
    IceBurst("Ice burst", "icons/combat-styles/ice-burst.png"),
    IceBurstDefensive("Ice burst (defensive)", "icons/combat-styles/ice-burst.png"),
    BloodBurst("Blood burst", "icons/combat-styles/blood-burst.png"),
    BloodBurstDefensive("Blood burst (defensive)", "icons/combat-styles/blood-burst.png"),
    SmokeBurst("Smoke burst", "icons/combat-styles/smoke-burst.png"),
    SmokeBurstDefensive("Smoke burst (defensive)", "icons/combat-styles/smoke-burst.png"),
    ShadowBurst("Shadow burst", "icons/combat-styles/shadow-burst.png"),
    ShadowBurstDefensive("Shadow burst (defensive)", "icons/combat-styles/shadow-burst.png"),
    IceBarrage("Ice barrage", "icons/combat-styles/ice-barrage.png"),
    IceBarrageDefensive("Ice barrage (defensive)", "icons/combat-styles/ice-barrage.png"),
    BloodBarrage("Blood barrage", "icons/combat-styles/blood-barrage.png"),
    BloodBarrageDefensive("Blood barrage (defensive)", "icons/combat-styles/blood-barrage.png"),
    SmokeBarrage("Smoke barrage", "icons/combat-styles/smoke-barrage.png"),
    SmokeBarrageDefensive("Smoke barrage (defensive)", "icons/combat-styles/smoke-barrage.png"),
    ShadowBarrage("Shadow barrage", "icons/combat-styles/shadow-barrage.png"),
    ShadowBarrageDefensive("Shadow barrage (defensive)", "icons/combat-styles/shadow-barrage.png");

    public String getDisplayName() {
        return displayName;
    }

    private final String displayName;

    public String getIconPath() {
        return iconPath;
    }

    private final String iconPath;

    // Constructor for the enum
    CombatStyle(String displayName, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return displayName; // Override toString to return the string value
    }
}
