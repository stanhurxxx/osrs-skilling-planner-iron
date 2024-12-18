package io.hurx.models;

/**
 * Enum representing various icon paths used within the application.
 * This includes paths for locations, NPCs, monsters, skills, combat styles, menus, and prayers.
 */
public enum IconPaths {
    LocationSlayerTower("icons/locations/slayer-tower.png"),
    LocationNievesCave("icons/locations/nieve-cave.png"),
    LocationKourendCatacombs("icons/locations/catacombs.png"),
    LocationWilderness("icons/locations/wilderness.png"),
    
    NPCDuradel("icons/npcs/duradel.png"),
    NPCNieve("icons/npcs/nieve.png"),

    MonsterAberrantSpectre("icons/monsters/aberrant-spectre.png"),
    MonsterAbyssalDemon("icons/monsters/abyssal-demon.png"),
    MonsterAnkou("icons/monsters/ankou.png"),
    MonsterAraxyte("icons/monsters/araxyte.png"),
    MonsterAbyssalSire("icons/monsters/abyssal-sire.png"),
    MonsterAdamantDragon("icons/monsters/adamant-dragon.png"),
    MonsterBloodveld("icons/monsters/bloodveld.png"),
    MonsterAraxyteLevel96("icons/monsters/araxyte-96.png"),
    MonsterAraxyteLevel146("icons/monsters/araxyte-146.png"),
    MonsterAraxxor("icons/monsters/araxxor.png"),
    MonsterAviansie("icons/monsters/aviansie.png"),
    MonsterKreeArra("icons/monsters/kreearra.png"),
    MonsterBasilisk("icons/monsters/basilisk.png"),
    MonsterBasiliskKnight("icons/monsters/basilisk-knight.png"),
    MonsterBlackDemon("icons/monsters/black-demon.png"),
    MonsterDemonicGorilla("icons/monsters/demonic-gorilla.png"),
    MonsterSkotizo("icons/monsters/skotizo.png"),
    MonsterBlackDragons("icons/monsters/black-dragon.png"),
    MonsterBabyBlackDragons("icons/monsters/baby-black-dragon.png"),
    MonsterKingBlackDragons("icons/monsters/king-black-dragon.png"),
    MonsterBabyBlueDragons("icons/monsters/baby-blue-dragon.png"),
    MonsterBlueDragons("icons/monsters/blue-dragon.png"),
    MonsterVorkath("icons/monsters/vorkath.png"),
    MonsterBrineRat("icons/monsters/brine-rat.png"),
    MonsterCaveHorror("icons/monsters/cave-horror.png"),
    MonsterCaveKraken("icons/monsters/cave-kraken.png"),
    MonsterKraken("icons/monsters/kraken.png"),
    MonsterDagannoth("icons/monsters/dagannoth.png"),
    MonsterDagannothKings("icons/monsters/dagannoth-kings.png"),
    MonsterDarkBeast("icons/monsters/dark-beast.png"),
    MonsterDrake("icons/monsters/drake.png"),
    MonsterDustDevil("icons/monsters/dust-devil.png"),
    MonsterHellhound("icons/monsters/hellhound.png"),
    MonsterFossilIslandWyverns("icons/monsters/fossil-island-wyvern.png"),
    MonsterNechryael("icons/monsters/nechryael.png"),
    MonsterVampyres("icons/monsters/vampyre.png"),
    MonsterWarpedCreature("icons/monsters/warped-creature.png"),
    MonsterSpiritualCreatures("icons/monsters/spiritual-creatures.png"),
    MonsterScarab("icons/monsters/scarab.png"),
    MonsterElf("icons/monsters/elf.png"),
    MonsterElfIorwerth("icons/monsters/iorwerth-warrior.png"),
    MonsterElfWarrior("icons/monsters/elf-warrior.png"),
    MonsterPrifGuard("icons/monsters/prif-guard.png"),
    MonsterFireGiant("icons/monsters/fire-giant.png"),
    MonsterSpittingWyvern("icons/monsters/spitting-wyvern.png"),
    MonsterTalonedWyvern("icons/monsters/taloned-wyvern.png"),
    MonsterLongTailedWyvern("icons/monsters/long-tailed-wyvern.png"),
    MonsterAncientWyvern("icons/monsters/ancient-wyvern.png"),
    MonsterGargoyle("icons/monsters/gargoyle.png"),
    MonsterGrotesqueGuardian("icons/monsters/grotesque-guardian.png"),
    MonsterGreaterDemon("icons/monsters/greater-demon.png"),
    MonsterTormentedDemon("icons/monsters/tormented-demon.png"),
    MonsterKril("icons/monsters/kril.png"),
    MonsterCerberus("icons/monsters/cerberus.png"),
    MonsterIronDragon("icons/monsters/iron-dragon.png"),
    MonsterKalphites("icons/monsters/kalphite.png"),
    MonsterKalphiteWorker("icons/monsters/kalphite-worker.png"),
    MonsterKalphiteSoldier("icons/monsters/kalphite-soldier.png"),
    MonsterKalphiteGuardian("icons/monsters/kalphite-guardian.png"),
    MonsterKalphiteQueen("icons/monsters/kalphite-queen.png"),
    MonsterKurask("icons/monsters/kurask.png"),
    MonsterLizardman("icons/monsters/lizardman.png"),
    MonsterLizardmanBrute("icons/monsters/lizardman-brute.png"),
    MonsterLizardmanShaman("icons/monsters/lizardman-shaman.png"),
    MonsterMithrilDragon("icons/monsters/mithril-dragon.png"),
    MonsterMutatedZygomite("icons/monsters/mutated-zygomite.png"),
    MonsterAncientZygomite("icons/monsters/ancient-zygomite.png"),
    MonsterRedDragon("icons/monsters/red-dragon.png"),
    MonsterBabyRedDragon("icons/monsters/baby-red-dragon.png"),
    MonsterBrutalRedDragon("icons/monsters/brutal-red-dragon.png"),
    MonsterRuneDragon("icons/monsters/rune-dragon.png"),
    MonsterLocustRiders("icons/monsters/locust-rider.png"),
    MonsterScarabMages("icons/monsters/scarab-mage.png"),
    MonsterSkeletalWyverns("icons/monsters/skeletal-wyvern.png"),
    MonsterSmokeDevils("icons/monsters/smoke-devil.png"),
    MonsterThermonuclearSmokeDevils("icons/monsters/thermy.png"),
    MonsterSpiritualRangers("icons/monsters/spiritual-ranger.png"),
    MonsterSpiritualWarriors("icons/monsters/spiritual-warrior.png"),
    MonsterSpiritualMages("icons/monsters/spiritual-mage.png"),
    MonsterSteelDragons("icons/monsters/steel-dragon.png"),
    MonsterSuqahs("icons/monsters/suqah.png"),
    MonsterTrolls("icons/monsters/troll.png"),
    MonsterIceTrolls("icons/monsters/ice-troll.png"),
    MonsterTuroths("icons/monsters/turoth.png"),
    MonsterTzHaars("icons/monsters/tzhaar.png"),
    MonsterJads("icons/monsters/jad.png"),
    MonsterZuks("icons/monsters/zuk.png"),
    MonsterFeralVampyres("icons/monsters/feral-vampyre.png"),
    MonsterVampyreJuvinates("icons/monsters/vampyre-juvinate.png"),
    MonsterVyrewatches("icons/monsters/vyrewatch.png"),
    MonsterVyrewatchSentinels("icons/monsters/vyrewatch-sentinel.png"),
    MonsterWarpedTerrorbirds("icons/monsters/warped-terrorbird.png"),
    MonsterWarpedTortoises("icons/monsters/warped-tortoise.png"),
    MonsterWaterfiends("icons/monsters/waterfiend.png"),
    MonsterWyrms("icons/monsters/wyrm.png"),
    MonsterWyrmlings("icons/monsters/wyrmling.png"),

    SkillAgility("icons/skills/agility.png"),
    SkillAttack("icons/skills/attack.png"),
    SkillConstruction("icons/skills/construction.png"),
    SkillCooking("icons/skills/cooking.png"),
    SkillCrafting("icons/skills/crafting.png"),
    SkillDefence("icons/skills/defence.png"),
    SkillFarming("icons/skills/farming.png"),
    SkillFiremaking("icons/skills/firemaking.png"),
    SkillFishing("icons/skills/fishing.png"),
    SkillFletching("icons/skills/fletching.png"),
    SkillHerblore("icons/skills/herblore.png"),
    SkillHitpoints("icons/skills/hitpoints.png"),
    SkillHunter("icons/skills/hunter.png"),
    SkillMagic("icons/skills/magic.png"),
    SkillMining("icons/skills/mining.png"),
    SkillPrayer("icons/skills/prayer.png"),
    SkillRanged("icons/skills/ranged.png"),
    SkillRunecraft("icons/skills/runecraft.png"),
    SkillSlayer("icons/skills/slayer.png"),
    SkillSmithing("icons/skills/smithing.png"),
    SkillStrength("icons/skills/strength.png"),
    SkillThieving("icons/skills/thieving.png"),
    SkillWoodcutting("icons/skills/woodcutting.png"),
    SkillEHP("icons/panel-icon.png"),
    SkillOverall("icons/skills/overall.png"),

    CombatStyleNone("icons/combat-styles/none.png"),
    CombatStyleControlled("icons/combat-styles/controlled.png"),
    CombatStyleAttack("icons/skills/attack.png"),
    CombatStyleStrength("icons/skills/strength.png"),
    CombatStyleDefence("icons/skills/defence.png"),
    CombatStyleRanged("icons/skills/ranged.png"),
    CombatStyleRangedDefensive("icons/skills/ranged.png"),
    CombatStyleRegChinsShort("icons/combat-styles/chinchompa.png"),
    CombatStyleRegChinsMedium("icons/combat-styles/chinchompa.png"),
    CombatStyleRegChinsLong("icons/combat-styles/chinchompa.png"),
    CombatStyleRedChinsShort("icons/combat-styles/red-chinchompa.png"),
    CombatStyleRedChinsMedium("icons/combat-styles/red-chinchompa.png"),
    CombatStyleRedChinsLong("icons/combat-styles/red-chinchompa.png"),
    CombatStyleBlackChinsShort("icons/combat-styles/black-chinchompa.png"),
    CombatStyleBlackChinsMedium("icons/combat-styles/black-chinchompa.png"),
    CombatStyleBlackChinsLong("icons/combat-styles/black-chinchompa.png"),
    CombatStyleMagic("icons/skills/magic.png"),
    CombatStyleIceBurst("icons/combat-styles/ice-burst.png"),
    CombatStyleIceBurstDefensive("icons/combat-styles/ice-burst.png"),
    CombatStyleBloodBurst("icons/combat-styles/blood-burst.png"),
    CombatStyleBloodBurstDefensive("icons/combat-styles/blood-burst.png"),
    CombatStyleSmokeBurst("icons/combat-styles/smoke-burst.png"),
    CombatStyleSmokeBurstDefensive("icons/combat-styles/smoke-burst.png"),
    CombatStyleShadowBurst("icons/combat-styles/shadow-burst.png"),
    CombatStyleShadowBurstDefensive("icons/combat-styles/shadow-burst.png"),
    CombatStyleIceBarrage("icons/combat-styles/ice-barrage.png"),
    CombatStyleIceBarrageDefensive("icons/combat-styles/ice-barrage.png"),
    CombatStyleBloodBarrage("icons/combat-styles/blood-barrage.png"),
    CombatStyleBloodBarrageDefensive("icons/combat-styles/blood-barrage.png"),
    CombatStyleSmokeBarrage("icons/combat-styles/smoke-barrage.png"),
    CombatStyleSmokeBarrageDefensive("icons/combat-styles/smoke-barrage.png"),
    CombatStyleShadowBarrage("icons/combat-styles/shadow-barrage.png"),
    CombatStyleShadowBarrageDefensive("icons/combat-styles/shadow-barrage.png"),

    MenuAdd("icons/menu/add.png"),
    MenuBack("icons/menu/back.png"),
    MenuFilter("icons/menu/filter.png"),
    MenuSettings("icons/menu/settings.png"),
    MenuEdit("icons/menu/edit.png"),
    MenuUpload("icons/menu/upload.png"),
    MenuDownload("icons/menu/download.png"),
    MenuReset("icons/menu/reload.png"),
    MenuProfiles("icons/menu/profiles.png"),
    MenuDelete("icons/menu/delete.png"),
    MenuDuplicate("icons/menu/duplicate.png"),
    MenuSave("icons/menu/save.png"),
    MenuSort("icons/menu/sort.png"),

    PrayerAugury("icons/prayers/augury.png"),
    PrayerEagleEye("icons/prayers/eagle-eye.png"),
    PrayerMysticMight("icons/prayers/mystic-might.png"),
    PrayerPiety("icons/prayers/piety.png"),
    PrayerRigour("icons/prayers/rigour.png"),
    PrayerProtection("icons/prayers/protection.png");

    /**
     * The file path for the icon.
     */
    private final String path;

    /**
     * Constructor to initialize the enum with the corresponding icon path.
     *
     * @param path the path of the icon
     */
    IconPaths(String path) {
        this.path = path;
    }

    /**
     * Retrieves the path of the icon.
     *
     * @return the icon path as a String
     */
    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return path;
    }
}
