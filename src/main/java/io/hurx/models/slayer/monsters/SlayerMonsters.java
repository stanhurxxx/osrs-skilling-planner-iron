package io.hurx.models.slayer.monsters;

public enum SlayerMonsters {
    AberrantSpectres("Aberrant Spectres"),
    AbyssalDemon("Abyssal Demons"),
    AdamantDragon("Adamant Dragons"),
    Ankou("Ankous"),
    Araxyte("Araxytes"),
    Aviansie("Aviansies"),
    Basilisk("Basilisks"),
    BlackDemon("Black Demons"),
    BlackDragon("Black Dragons"),
    Bloodveld("Bloodvelds"),
    BlueDragon("Blue Dragons"),
    BrineRat("Brine Rats"),
    CaveHorror("Cave Horrors"),
    CaveKraken("Cave Krakens"),
    Dagannoth("Dagannoths"),
    DarkBeast("Dark Beasts"),
    Drake("Drakes"),
    DustDevil("Dust Devils"),
    Elf("Elves"),
    FireGiant("Fire Giants"),
    FossilIslandWyvern("Fossil Island Wyverns"),
    Gargoyle("Gargoyles"),
    GreaterDemon("Greater Demons"),
    Hellhound("Hellhounds"),
    IronDragon("IronDragons"),
    Kalphite("Kalphites"),
    Kurask("Kurasks"),
    Lizardman("Lizardmen"),
    MithrilDragon("Mithril Dragons"),
    MutatedZygomite("Mutated Zygomites"),
    Nechryael("Nechryaels"),
    RedDragon("Red Dragons"),
    RuneDragon("Rune Dragons"),
    Scarab("Scarabs"),
    SkeletalWyvern("Skeletal Wyverns"),
    SmokeDevil("Smoke Devils"),
    SpiritualCreatures("Spiritual Creatures"),
    SteelDragon("Steel Dragons"),
    Suqah("Suqahs"),
    Troll("Trolls"),
    Turoth("Turoths"),
    Tzhaar("Tzhaars"),
    Vampyre("Vampyres"),
    WarpedCreature("Warped Creatures"),
    Waterfiend("Waterfiends"),
    Wyrm("Wyrms");

    // A field to store the string value
    private final String displayName;

    // Constructor for the enum
    SlayerMonsters(String displayName) {
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
