package io.hurx.models;

public enum IconPaths {
    LocationSlayerTower("icons/locations/slayer-tower.png"),
    LocationNievesCave("icons/locations/nieve-cave.png"),
    LocationKourendCatacombs("icons/locations/catacombs.png"),
    LocationWilderness("icons/locations/wilderness.png"),

    MonsterAbyssalSire("icons/monsters/abyssal-sire.png"),
    MonsterAdamantDragon("icons/monsters/adamant-dragon.png"),
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
    ;

    public String getPath() {
        return this.path;
    }

    private String path;

    IconPaths(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}