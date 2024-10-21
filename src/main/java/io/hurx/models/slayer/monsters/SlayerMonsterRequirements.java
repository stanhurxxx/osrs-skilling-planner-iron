package io.hurx.models.slayer.monsters;

public enum SlayerMonsterRequirements {
    PriestInPeril("Priest in Peril"),
    PriestInPerilOrFairyTaleII("Priest in Peril or Fairy Tale II - Cure a Queen"),
    FairytaleII("Fairytale II - Cure of a Queen"),
    DragonSlayerI("Dragon Slayer I"),
    DragonSlayerII("Dragon Slayer II"),
    WatchTheBirdy("Watch the birdie ability"),
    Basilocked("Basilocked ability"),
    CabinFever("Cabin Fever"),
    HorrorFromTheDeep("Horror from the Deep"),
    MourningsEndPartIIStarted("Mournings End Part II started"),
    Regicide("Regicide"),
    BoneVoyage("Bone Voyage"),
    ElementalWorkshop("Elemental Workshop"),
    ReptileGotRipped("Reptile got ripped ability"),
    IHopeYouMithMe("I hope you mith me ability"),
    LostCity("Lost City"),
    DeathPlateau("Death Plateau"),
    SeeingRed("Seeing red ability"),
    LunarDiplomacy("Lunar diplomacy"),
    DesertTreasureI("Desert Treasure I"),
    HotStuff("Hot stuff ability"),
    ActualVampyreSlayer("Actual Vampyre Slayer ability"),
    WarpedReality("Warped Reality ability"),
    OlafsQuest("Olafs quest"),
    Contact("Contact!"),
    NotStopTheWyvern("Not unlocked Stop the Wyvern ability"),
    
    Magic50("Level 50 magic"),

    StrengthOrAgility60("Level 60 strength or agility"),
    
    Combat15("Level 15 combat"),
    Combat40("Level 40 combat"),
    Combat45("Level 45 combat"),
    Combat50("Level 50 combat"),
    Combat60("Level 60 combat"),
    Combat65("Level 65 combat"),
    Combat68("Level 68 combat"),
    Combat70("Level 70 combat"),
    Combat75("Level 75 combat"),
    Combat80("Level 80 combat"),
    Combat85("Level 85 combat"),
    Combat90("Level 90 combat"),
    
    Slayer40("Level 40 slayer"),
    Slayer47("Level 47 slayer"),
    Slayer50("Level 50 slayer"),
    Slayer55("Level 55 slayer"),
    Slayer57("Level 57 slayer"),
    Slayer58("Level 58 slayer"),
    Slayer60("Level 60 slayer"),
    Slayer62("Level 62 slayer"),
    Slayer63("Level 63 slayer"),
    Slayer65("Level 65 slayer"),
    Slayer66("Level 66 slayer"),
    Slayer70("Level 70 slayer"),
    Slayer72("Level 72 slayer"),
    Slayer75("Level 75 slayer"),
    Slayer80("Level 80 slayer"),
    Slayer84("Level 84 slayer"),
    Slayer85("Level 85 slayer"),
    Slayer87("Level 87 slayer"),
    Slayer90("Level 90 slayer"),
    Slayer92("Level 92 slayer"),
    Slayer93("Level 93 slayer");

    // A field to store the string value
    private final String displayName;

    // Constructor for the enum
    SlayerMonsterRequirements(String displayName) {
        this.displayName = displayName;
    }

    // Getter method to access the string value
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName; // Override toString to return the string value
    }
}
