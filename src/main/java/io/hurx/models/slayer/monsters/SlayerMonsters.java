package io.hurx.models.slayer.monsters;

import io.hurx.models.slayer.LootItem;

public enum SlayerMonsters {
    AberrantSpectres("Aberrant Spectres", "icons/monsters/aberrant-spectre.png"),
    AbyssalDemon("Abyssal Demons", "icons/monsters/abyssal-demon.png"),
    AdamantDragon("Adamant Dragons", "icons/monsters/adamant-dragon.png"),
    Ankou("Ankous", "icons/monsters/ankou.png"),
    Araxyte("Araxytes", "icons/monsters/araxyte.png"),
    Aviansie("Aviansies", "icons/monsters/aviansie.png"),
    Basilisk("Basilisks", "icons/monsters/basilisk.png"),
    BlackDemon("Black Demons", "icons/monsters/black-demon.png"),
    BlackDragon("Black Dragons", "icons/monsters/black-dragon.png"),
    Bloodveld("Bloodvelds", "icons/monsters/bloodveld.png"),
    BlueDragon("Blue Dragons", "icons/monsters/blue-dragon.png"),
    BrineRat("Brine Rats", "icons/monsters/brine-rat.png"),
    CaveHorror("Cave Horrors", "icons/monsters/cave-horror.png"),
    CaveKraken("Cave Krakens", "icons/monsters/cave-kraken.png"),
    Dagannoth("Dagannoths", "icons/monsters/dagannoth.png"),
    DarkBeast("Dark Beasts", "icons/monsters/dark-beast.png"),
    Drake("Drakes", "icons/monsters/drake.png"),
    DustDevil("Dust Devils", "icons/monsters/dust-devil.png"),
    Elf("Elves", "icons/monsters/elf.png"),
    FireGiant("Fire Giants", "icons/monsters/fire-giant.png"),
    FossilIslandWyvern("Fossil Island Wyverns", "icons/monsters/fossil-island-wyvern.png"),
    Gargoyle("Gargoyles", "icons/monsters/gargoyle.png"),
    GreaterDemon("Greater Demons", "icons/monsters/greater-demon.png"),
    Hellhound("Hellhounds", "icons/monsters/hellhound.png"),
    IronDragon("IronDragons", "icons/monsters/iron-dragon.png"),
    Kalphite("Kalphites", "icons/monsters/kalphite.png"),
    Kurask("Kurasks", "icons/monsters/kurask.png"),
    Lizardman("Lizardmen", "icons/monsters/lizardman.png"),
    MithrilDragon("Mithril Dragons", "icons/monsters/mithril-dragon.png"),
    MutatedZygomite("Mutated Zygomites", "icons/monsters/mutated-zygomite.png"),
    Nechryael("Nechryaels", "icons/monsters/nechryael.png"),
    RedDragon("Red Dragons", "icons/monsters/red-dragon.png"),
    RuneDragon("Rune Dragons", "icons/monsters/rune-dragon.png"),
    Scarab("Scarabs", "icons/monsters/scarab.png"),
    SkeletalWyvern("Skeletal Wyverns", "icons/monsters/skeletal-wyvern.png"),
    SmokeDevil("Smoke Devils", "icons/monsters/smoke-devil.png"),
    SpiritualCreatures("Spiritual Creatures", "icons/monsters/spiritual-creatures.png"),
    SteelDragon("Steel Dragons", "icons/monsters/steel-dragon.png"),
    Suqah("Suqahs", "icons/monsters/suqah.png"),
    Troll("Trolls", "icons/monsters/troll.png"),
    Turoth("Turoths", "icons/monsters/turoth.png"),
    Tzhaar("Tzhaars", "icons/monsters/tzhaar.png"),
    Vampyre("Vampyres", "icons/monsters/vampyre.png"),
    WarpedCreature("Warped Creatures", "icons/monsters/warped-creature.png"),
    Waterfiend("Waterfiends", "icons/monsters/waterfiend.png"),
    Wyrm("Wyrms", "icons/monsters/wyrm.png"),
    DeviantSpectre("Deviant Spectres", "icons/monsters/deviant-spectre.png");

    public String getDisplayName() {
        return displayName;
    }

    private final String displayName;

    public String getIconPath() {
        return iconPath;
    }

    private final String iconPath;

    public LootItem[] getLootItems() {
        return lootItems;
    }

    private final LootItem[] lootItems;

    // Constructor for the enum
    SlayerMonsters(String displayName, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
        this.lootItems = new LootItem[] {};
    }

    SlayerMonsters(String displayName, String iconPath, LootItem[] lootItems) {
        this.displayName = displayName;
        this.iconPath = null;
        this.lootItems = lootItems;
    }

    @Override
    public String toString() {
        return displayName; // Override toString to return the string value
    }
}
