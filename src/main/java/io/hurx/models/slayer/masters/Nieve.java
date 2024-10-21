package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.monsters.SlayerMonster;
import io.hurx.models.slayer.monsters.SlayerMonsterRequirements;
import io.hurx.models.slayer.monsters.SlayerMonsters;

public class Nieve extends SlayerMaster {
    public static final SlayerMonster AberrantSpectres = new SlayerMonster(
        SlayerMonsters.AberrantSpectres,
        "icons/monsters/aberrant-spectre.png",
        120,
        185,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer60,
            SlayerMonsterRequirements.Combat65,
            SlayerMonsterRequirements.PriestInPeril
        }
    );
    public static final SlayerMonster AbyssalDemon = new SlayerMonster(
        SlayerMonsters.AbyssalDemon,
        "icons/monsters/abyssal-demon.png",
        120,
        185,
        200,
        250,
        9,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer85,
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.PriestInPerilOrFairyTaleII
        }
    );
    public static final SlayerMonster AdamantDragon = new SlayerMonster(
        SlayerMonsters.AdamantDragon,
        "icons/monsters/adamant-dragon.png",
        3,
        7,
        20,
        30,
        2,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.DragonSlayerII
        }
    );
    public static final SlayerMonster Ankou = new SlayerMonster(
        SlayerMonsters.Ankou,
        "icons/monsters/ankou.png",
        50,
        90,
        91,
        150,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat40
        }
    );
    public static final SlayerMonster Araxyte = new SlayerMonster(
        SlayerMonsters.Araxyte,
        "icons/monsters/araxyte.png",
        40,
        60,
        200,
        250,
        8,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer92,
            SlayerMonsterRequirements.PriestInPeril
        }
    );
    public static final SlayerMonster Aviansie = new SlayerMonster(
        SlayerMonsters.Aviansie,
        "icons/monsters/aviansie.png",
        120,
        185,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.WatchTheBirdy
        }
    );
    public static final SlayerMonster Basilisk = new SlayerMonster(
        SlayerMonsters.Basilisk,
        "icons/monsters/basilisk.png",
        120,
        185,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Basilocked,
            SlayerMonsterRequirements.Slayer40
        }
    );
    public static final SlayerMonster BlackDemon = new SlayerMonster(
        SlayerMonsters.BlackDemon,
        "icons/monsters/black-demon.png",
        120,
        185,
        200,
        250,
        9,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat80
        }
    );
    public static final SlayerMonster BlackDragon = new SlayerMonster(
        SlayerMonsters.BlackDragon,
        "icons/monsters/black-dragon.png",
        10,
        20,
        40,
        60,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat80,
            SlayerMonsterRequirements.DragonSlayerI
        }
    );
    public static final SlayerMonster Bloodveld = new SlayerMonster(
        SlayerMonsters.Bloodveld,
        "icons/monsters/bloodveld.png",
        120,
        185,
        200,
        250,
        9,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer50,
            SlayerMonsterRequirements.Combat50,
            SlayerMonsterRequirements.PriestInPeril
        }
    );
    public static final SlayerMonster BlueDragons = new SlayerMonster(
        SlayerMonsters.BlueDragon,
        "icons/monsters/blue-dragon.png",
        120,
        185,
        null,
        null,
        4,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat65,
            SlayerMonsterRequirements.DragonSlayerI
        }
    );
    public static final SlayerMonster BrineRats = new SlayerMonster(
        SlayerMonsters.BrineRat,
        "icons/monsters/brine-rat.png",
        120,
        185,
        null,
        null,
        3,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer47,
            SlayerMonsterRequirements.Combat45,
            SlayerMonsterRequirements.OlafsQuest
        }
    );
    public static final SlayerMonster CaveHorrors = new SlayerMonster(
        SlayerMonsters.CaveHorror,
        "icons/monsters/cave-horror.png",
        120,
        180,
        200,
        250,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer58,
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.CabinFever
        }
    );
    public static final SlayerMonster CaveKrakens = new SlayerMonster(
        SlayerMonsters.CaveKraken,
        "icons/monsters/cave-kraken.png",
        100,
        120,
        150,
        200,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer87,
            SlayerMonsterRequirements.Combat80,
            SlayerMonsterRequirements.Magic50
        }
    );
    public static final SlayerMonster Dagannoths = new SlayerMonster(
        SlayerMonsters.Dagannoth,
        "icons/monsters/dagannoth.png",
        120,
        185,
        null,
        null,
        8,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat75,
            SlayerMonsterRequirements.HorrorFromTheDeep
        }
    );
    public static final SlayerMonster DarkBeasts = new SlayerMonster(
        SlayerMonsters.DarkBeast,
        "icons/monsters/dark-beast.png",
        10,
        20,
        110,
        135,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer90,
            SlayerMonsterRequirements.Combat90,
            SlayerMonsterRequirements.MourningsEndPartIIStarted
        }
    );
    public static final SlayerMonster Drakes = new SlayerMonster(
        SlayerMonsters.Drake,
        "icons/monsters/drake.png",
        30,
        95,
        null,
        null,
        7,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer84
        }
    );
    public static final SlayerMonster DustDevils = new SlayerMonster(
        SlayerMonsters.DustDevil,
        "icons/monsters/dust-devil.png",
        120,
        185,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer65,
            SlayerMonsterRequirements.Combat70,
            SlayerMonsterRequirements.DesertTreasureI,
        }
    );
    public static final SlayerMonster Elves = new SlayerMonster(
        SlayerMonsters.Elf,
        "icons/monsters/elf.png",
        60,
        90,
        null,
        null,
        4,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat70,
            SlayerMonsterRequirements.Regicide
        }
    );
    public static final SlayerMonster FireGiants = new SlayerMonster(
        SlayerMonsters.FireGiant,
        "icons/monsters/fire-giant.png",
        120,
        185,
        null,
        null,
        9,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat65
        }
    );
    public static final SlayerMonster FossilIslandWyverns = new SlayerMonster(
        SlayerMonsters.FossilIslandWyvern,
        "icons/monsters/fossil-island-wyvern.png",
        5,
        25,
        55,
        75,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer66,
            SlayerMonsterRequirements.BoneVoyage,
            SlayerMonsterRequirements.ElementalWorkshop,
            SlayerMonsterRequirements.Combat60
        }
    );
    public static final SlayerMonster Gargoyles = new SlayerMonster(
        SlayerMonsters.Gargoyle,
        "icons/monsters/gargoyle.png",
        120,
        185,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer75,
            SlayerMonsterRequirements.Combat80,
            SlayerMonsterRequirements.PriestInPeril
        }
    );
    public static final SlayerMonster GreaterDemons = new SlayerMonster(
        SlayerMonsters.GreaterDemon,
        "icons/monsters/greater-demon.png",
        120,
        185,
        200,
        250,
        7,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat75
        }
    );
    public static final SlayerMonster Hellhounds = new SlayerMonster(
        SlayerMonsters.Hellhound,
        "icons/monsters/hellhound.png",
        120,
        185,
        null,
        null,
        8,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat75
        }
    );
    public static final SlayerMonster IronDragons = new SlayerMonster(
        SlayerMonsters.IronDragon,
        "icons/monsters/iron-dragon.png",
        30,
        60,
        60,
        100,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat80,
            SlayerMonsterRequirements.DragonSlayerI,
        }
    );
    public static final SlayerMonster Kalphites = new SlayerMonster(
        SlayerMonsters.Kalphite,
        "icons/monsters/kalphite.png",
        120,
        185,
        null,
        null,
        9,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat15
        }
    );
    public static final SlayerMonster Kurasks = new SlayerMonster(
        SlayerMonsters.Kurask,
        "icons/monsters/kurask.png",
        120,
        185,
        null,
        null,
        3,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer70,
            SlayerMonsterRequirements.Combat65,
        }
    );
    public static final SlayerMonster Lizardmen = new SlayerMonster(
        SlayerMonsters.Lizardman,
        "icons/monsters/lizardman.png",
        90,
        120,
        null,
        null,
        8,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.ReptileGotRipped
        }
    );
    public static final SlayerMonster MithrilDragons = new SlayerMonster(
        SlayerMonsters.MithrilDragon,
        "icons/monsters/mithril-dragon.png",
        4,
        8,
        25,
        35,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.IHopeYouMithMe
        }
    );
    public static final SlayerMonster MutatedZygomites = new SlayerMonster(
        SlayerMonsters.MutatedZygomite,
        "icons/monsters/mutated-zygomite.png",
        10,
        25,
        null,
        null,
        2,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer57,
            SlayerMonsterRequirements.Combat60,
            SlayerMonsterRequirements.LostCity,
        }
    );
    public static final SlayerMonster Nechryaels = new SlayerMonster(
        SlayerMonsters.Nechryael,
        "icons/monsters/nechryael.png",
        110,
        170,
        200,
        250,
        7,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer80,
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.PriestInPeril,
        }
    );
    public static final SlayerMonster RedDragons = new SlayerMonster(
        SlayerMonsters.RedDragon,
        "icons/monsters/red-dragon.png",
        30,
        80,
        null,
        null,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat68,
            SlayerMonsterRequirements.SeeingRed,
        }
    );
    public static final SlayerMonster RuneDragons = new SlayerMonster(
        SlayerMonsters.RuneDragon,
        "icons/monsters/rune-dragon.png",
        3,
        8,
        30,
        60,
        2,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.DragonSlayerII
        }
    );
    public static final SlayerMonster Scarabs = new SlayerMonster(
        SlayerMonsters.Scarab,
        "icons/monsters/scarab.png",
        30,
        60,
        130,
        170,
        4,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.Contact,
        }
    );
    public static final SlayerMonster SkeletalWyverns = new SlayerMonster(
        SlayerMonsters.SkeletalWyvern,
        "icons/monsters/skeletal-wyvern.png",
        5,
        15,
        50,
        70,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer72,
            SlayerMonsterRequirements.Combat70,
            SlayerMonsterRequirements.ElementalWorkshop,
        }
    );
    public static final SlayerMonster SmokeDevils = new SlayerMonster(
        SlayerMonsters.SmokeDevil,
        "icons/monsters/smoke-devil.png",
        120,
        185,
        null,
        null,
        7,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer93,
            SlayerMonsterRequirements.Combat85,
        }
    );
    public static final SlayerMonster SpiritualCreatures = new SlayerMonster(
        SlayerMonsters.SpiritualCreatures,
        "icons/monsters/spiritual-creatures.png",
        120,
        185,
        181,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer63,
            SlayerMonsterRequirements.Combat60,
            SlayerMonsterRequirements.DeathPlateau,
            SlayerMonsterRequirements.StrengthOrAgility60
        }
    );
    public static final SlayerMonster SteelDragons = new SlayerMonster(
        SlayerMonsters.SteelDragon,
        "icons/monsters/steel-dragon.png",
        30,
        60,
        40,
        60,
        5,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.DragonSlayerI
        }
    );
    public static final SlayerMonster Suqahs = new SlayerMonster(
        SlayerMonsters.Suqah,
        "icons/monsters/suqah.png",
        120,
        185,
        186,
        250,
        8,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat85,
            SlayerMonsterRequirements.LunarDiplomacy
        }
    );
    public static final SlayerMonster Trolls = new SlayerMonster(
        SlayerMonsters.Troll,
        "icons/monsters/troll.png",
        120,
        185,
        null,
        null,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Combat60
        }
    );
    public static final SlayerMonster Turoths = new SlayerMonster(
        SlayerMonsters.Turoth,
        "icons/monsters/turoth.png",
        120,
        185,
        null,
        null,
        3,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer55,
            SlayerMonsterRequirements.Combat60,
        }
    );
    public static final SlayerMonster Tzhaars = new SlayerMonster(
        SlayerMonsters.Tzhaar,
        "icons/monsters/tzhaar.png",
        110,
        180,
        null,
        null,
        10,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.HotStuff
        }
    );
    public static final SlayerMonster Vampyres = new SlayerMonster(
        SlayerMonsters.Vampyre,
        "icons/monsters/vampyre.png",
        110,
        170,
        200,
        250,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.ActualVampyreSlayer
        }
    );
    public static final SlayerMonster WarpedCreatures = new SlayerMonster(
        SlayerMonsters.WarpedCreature,
        "icons/monsters/warped-creature.png",
        120,
        185,
        null,
        null,
        6,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.WarpedReality
        }
    );
    public static final SlayerMonster Wyrms = new SlayerMonster(
        SlayerMonsters.Wyrm,
        "icons/monsters/wyrm.png",
        80,
        145,
        null,
        null,
        7,
        new SlayerMonsterRequirements[] {
            SlayerMonsterRequirements.Slayer62
        }
    );

    public Nieve() {
        super(
            new SlayerMonster[] {
                AberrantSpectres,
                AbyssalDemon,
                AdamantDragon,
                Ankou,
                Araxyte,
                Aviansie,
                Basilisk,
                BlackDemon,
                BlackDragon,
                Bloodveld,
                BlueDragons,
                BrineRats,
                CaveHorrors,
                CaveKrakens,
                Dagannoths,
                DarkBeasts,
                Drakes,
                DustDevils,
                Elves,
                FireGiants,
                FossilIslandWyverns,
                Gargoyles,
                GreaterDemons,
                Hellhounds,
                IronDragons,
                Kalphites,
                Kurasks,
                Lizardmen,
                MithrilDragons,
                MutatedZygomites,
                Nechryaels,
                RedDragons,
                RuneDragons,
                Scarabs,
                SkeletalWyverns,
                SmokeDevils,
                SpiritualCreatures,
                SteelDragons,
                Suqahs,
                Trolls,
                Turoths,
                Tzhaars,
                Vampyres,
                WarpedCreatures,
                Wyrms
            }
        );
    }
}