package io.hurx.models.items;

import java.util.concurrent.ExecutionException;

public enum Items {
    // Alchs
    AirBattlestaff(Item.Builder.id(1397).type(ItemType.Alch).build()),
    AncientStaff(Item.Builder.id(4675).type(ItemType.Alch).build()),
    BlackDHideBody(Item.Builder.id(2503).type(ItemType.Alch).build()),
    Dragon2HSword(Item.Builder.id(7158).type(ItemType.Alch).build()),
    DragonBattleaxe(Item.Builder.id(1377).type(ItemType.Alch).build()),
    DragonBoots(Item.Builder.id(11840).type(ItemType.Alch).build()),
    DragonChainbody(Item.Builder.id(3140).type(ItemType.Alch).build()),
    DragonDagger(Item.Builder.id(1215).type(ItemType.Alch).build()),
    DragonLongsword(Item.Builder.id(1305).type(ItemType.Alch).build()),
    DragonMace(Item.Builder.id(1434).type(ItemType.Alch).build()),
    DragonMedHelm(Item.Builder.id(1149).type(ItemType.Alch).build()),
    DragonPlatelegs(Item.Builder.id(4087).type(ItemType.Alch).build()),
    DragonPlateskirt(Item.Builder.id(4585).type(ItemType.Alch).build()),
    DragonScimitar(Item.Builder.id(4587).type(ItemType.Alch).build()),
    DragonSpear(Item.Builder.id(1249).type(ItemType.Alch).build()),
    DragonSword(Item.Builder.id(21009).type(ItemType.Alch).build()),
    DustBattlestaff(Item.Builder.id(20736).type(ItemType.Alch).build()),
    EarthBattlestaff(Item.Builder.id(1399).type(ItemType.Alch).build()),
    FireBattlestaff(Item.Builder.id(1393).type(ItemType.Alch).build()),
    GraniteLegs(Item.Builder.id(6809).type(ItemType.Alch).build()),
    GraniteLongsword(Item.Builder.id(21646).type(ItemType.Alch).build()),
    GraniteShield(Item.Builder.id(3122).type(ItemType.Alch).build()),
    LavaBattlestaff(Item.Builder.id(3053).type(ItemType.Alch).build()),
    MistBattlestaff(Item.Builder.id(20730).type(ItemType.Alch).build()),
    MysticAirStaff(Item.Builder.id(1405).type(ItemType.Alch).build()),
    MysticEarthStaff(Item.Builder.id(1407).type(ItemType.Alch).build()),
    MysticFireStaff(Item.Builder.id(1401).type(ItemType.Alch).build()),
    MysticHatLight(Item.Builder.id(4109).type(ItemType.Alch).build()),
    MysticLavaStaff(Item.Builder.id(3054).type(ItemType.Alch).build()),
    MysticRobeBottom(Item.Builder.id(4093).type(ItemType.Alch).build()),
    MysticRobeBottomLight(Item.Builder.id(4113).type(ItemType.Alch).build()),
    MysticRobeTop(Item.Builder.id(4091).type(ItemType.Alch).build()),
    MysticRobeTopLight(Item.Builder.id(4111).type(ItemType.Alch).build()),
    MysticRobeBottomsDark(Item.Builder.id(4103).type(ItemType.Alch).build()),
    MysticWaterStaff(Item.Builder.id(1403).type(ItemType.Alch).build()),
    Rune2HSword(Item.Builder.id(1319).type(ItemType.Alch).build()),
    RuneBattleaxe(Item.Builder.id(1373).type(ItemType.Alch).build()),
    RuneChainbody(Item.Builder.id(1113).type(ItemType.Alch).build()),
    RuneCrossbow(Item.Builder.id(9185).type(ItemType.Alch).build()),
    RuneFullHelm(Item.Builder.id(1163).type(ItemType.Alch).build()),
    RuneHalberd(Item.Builder.id(3202).type(ItemType.Alch).build()),
    RuneHasta(Item.Builder.id(11377).type(ItemType.Alch).build()),
    RuneMedHelm(Item.Builder.id(1147).type(ItemType.Alch).build()),
    RuneKiteshield(Item.Builder.id(1201).type(ItemType.Alch).build()),
    RuneLongsword(Item.Builder.id(1303).type(ItemType.Alch).build()),
    RuneMace(Item.Builder.id(1432).type(ItemType.Alch).build()),
    RunePickaxe(Item.Builder.id(1275).type(ItemType.Alch).build()),
    RunePlatebody(Item.Builder.id(1127).type(ItemType.Alch).build()),
    RunePlatelegs(Item.Builder.id(1079).type(ItemType.Alch).build()),
    RunePlateskirt(Item.Builder.id(1093).type(ItemType.Alch).build()),
    RuneScimitar(Item.Builder.id(1333).type(ItemType.Alch).build()),
    RuneSpear(Item.Builder.id(1247).type(ItemType.Alch).build()),
    RuneSqShield(Item.Builder.id(1185).type(ItemType.Alch).build()),
    RuneSword(Item.Builder.id(1289).type(ItemType.Alch).build()),
    RuneWarhammer(Item.Builder.id(1347).type(ItemType.Alch).build()),
    RuniteBar(Item.Builder.id(2363).type(ItemType.Alch).build()),
    RuniteLimbs(Item.Builder.id(9431).type(ItemType.Alch).build()),
    RuniteOre(Item.Builder.id(451).type(ItemType.Alch).build()),
    SmokeBattlestaff(Item.Builder.id(11998).type(ItemType.Alch).build()),
    SteamBattlestaff(Item.Builder.id(11787).type(ItemType.Alch).build()),
    OnyxBoltTips(Item.Builder.id(9194).type(ItemType.Alch).build()),
    WaterBattlestaff(Item.Builder.id(1395).type(ItemType.Alch).build()),
    
    // Crafting
    Battlestaff(Item.Builder.id(1391).build()),
    BlueDragonhide(Item.Builder.id(1751).build()),
    BlackDragonhide(Item.Builder.id(1747).build()),
    GreenDragonhide(Item.Builder.id(1753).build()),
    RedDragonhide(Item.Builder.id(1749).build()),
    AirOrb(Item.Builder.id(573).build()),
    EarthOrb(Item.Builder.id(575).build()),
    FireOrb(Item.Builder.id(569).build()),
    WaterOrb(Item.Builder.id(571).build()),

    // Agility / smithing
    AdamantiteBar(Item.Builder.id(2361).build()),

    // Smithing
    AdamantPlatebody(Item.Builder.id(1123).build()),
    BucketOfSand(Item.Builder.id(1783).build()),
    GoldOre(Item.Builder.id(444).build()),

    // Slayer
    BraceletOfSlaughter(Item.Builder.id(21183).build()),
    ExpeditiousBracelet(Item.Builder.id(21177).build()),

    // Cooking
    Grapes(Item.Builder.id(1987).build()),

    // Construction
    MahoganyPlank(Item.Builder.id(8782).build()),

    // Woodcutting
    DragonAxe(Item.Builder.id(6739).build()),

    // Supplies
    PrayerPotion(Item.Builder.id(2434).build()),
    SuperRestore(Item.Builder.id(3024).build()),
    SanfewSerum(Item.Builder.id(10925).build()),
    SuperCombatPotion(Item.Builder.id(12695).build()),
    RangingPotion(Item.Builder.id(2444).build()),

    // Seeds
    AvantoeSeed(Item.Builder.id(5298).build()),
    CadantineSeed(Item.Builder.id(5301).build()),
    DwarfWeedSeed(Item.Builder.id(5303).build()),
    HarralanderSeed(Item.Builder.id(5293).build()),
    IritSeed(Item.Builder.id(5297).build()),
    KwuarmSeed(Item.Builder.id(5299).build()),
    LantadymeSeed(Item.Builder.id(5302).build()),
    RanarrSeed(Item.Builder.id(5295).build()),
    SnapdragonSeed(Item.Builder.id(5300).build()),
    ToadflaxSeed(Item.Builder.id(5296).build()),
    TorstolSeed(Item.Builder.id(5304).build()),

    // Grimy's
    GrimyAvantoe(Item.Builder.id(211).build()),
    GrimyCadantine(Item.Builder.id(215).build()),
    GrimyDwarfWeed(Item.Builder.id(217).build()),
    GrimyHarralander(Item.Builder.id(205).build()),
    GrimyIritLeaf(Item.Builder.id(209).build()),
    GrimyKwuarm(Item.Builder.id(213).build()),
    GrimyLantadyme(Item.Builder.id(2485).build()),
    GrimyRanarrWeed(Item.Builder.id(207).build()),
    GrimySnapdragon(Item.Builder.id(3051).build()),
    GrimyToadflax(Item.Builder.id(3049).build()),
    GrimyTorstol(Item.Builder.id(219).build()),

    // Secondaries
    BlueDragonScale(Item.Builder.id(243).build()),
    CactusSpine(Item.Builder.id(6016).build()),
    PotatoCactus(Item.Builder.id(3138).build()),
    WhiteBerries(Item.Builder.id(239).build()),
    WineOfZamorak(Item.Builder.id(245).build()),

    // Runes
    BloodRune(Item.Builder.id(565).build()),
    ChaosRune(Item.Builder.id(562).build()),
    DeathRune(Item.Builder.id(560).build()),
    LawRune(Item.Builder.id(563).build()),
    NatureRune(Item.Builder.id(561).build()),
    SoulRune(Item.Builder.id(566).build()),
    WrathRune(Item.Builder.id(21880).build()),

    // Ashes
    AbyssalAshes(Item.Builder.id(25775).build()),
    InfernalAshes(Item.Builder.id(25778).build()),
    MaliciousAshes(Item.Builder.id(25772).build()),
    VileAshes(Item.Builder.id(25769).build()),
    
    // Heads
    EnsouledAbyssalHead(Item.Builder.id(25775).build()),
    EnsouledBloodveldHead(Item.Builder.id(13496).build()),
    EnsouledDagannothHead(Item.Builder.id(13493).build()),
    EnsouledDemonHead(Item.Builder.id(13502).build()),
    EnsouledDragonHead(Item.Builder.id(13511).build()),
    EnsouledGiantHead(Item.Builder.id(13511).build()),
    EnsouledHellhoundHead(Item.Builder.id(26997).build()),
    EnsouledHorrorHead(Item.Builder.id(13487).build()),
    EnsouledKalphiteHead(Item.Builder.id(13490).build()),

    // Bones
    BabydragonBones(Item.Builder.id(534).build()),
    DagannothBones(Item.Builder.id(6729).build()),
    DragonBones(Item.Builder.id(536).build()),
    DrakeBones(Item.Builder.id(22783).build()),
    SuperiorDragonBones(Item.Builder.id(22124).build()),
    WyvernBones(Item.Builder.id(6812).build()),
    WyrmBones(Item.Builder.id(22780).build()),
    WyrmlingBones(Item.Builder.id(28899).build()),

    // Drop over to main
    AbyssalBludgeon(Item.Builder.id(13263).type(ItemType.DropToMain).build()),
    AbyssalDagger(Item.Builder.id(13265).type(ItemType.DropToMain).build()),
    AbyssalWhip(Item.Builder.id(4151).type(ItemType.DropToMain).build()),
    AraneaBoots(Item.Builder.id(29806).type(ItemType.DropToMain).build()),
    AraxyteFang(Item.Builder.id(29806).type(ItemType.DropToMain)    
        .priceItemId(29801)
        .priceItemComponentIds(new int[] { 19553 }).build()
    ),
    ArchersRing(Item.Builder.id(6733).type(ItemType.DropToMain).build()),
    ArmadylHelmet(Item.Builder.id(11826).type(ItemType.DropToMain).build()),
    ArmadylHilt(Item.Builder.id(11810).type(ItemType.DropToMain).build()),
    ArmadylChainskirt(Item.Builder.id(11830).type(ItemType.DropToMain).build()),
    ArmadylChestplate(Item.Builder.id(11828).type(ItemType.DropToMain).build()),
    BallistaLimbs(Item.Builder.id(19592).type(ItemType.DropToMain).build()),
    BallistaSpring(Item.Builder.id(19601).type(ItemType.DropToMain).build()),
    BerserkerRing(Item.Builder.id(6737).type(ItemType.DropToMain).build()),
    BlackMask10(Item.Builder.id(8901).type(ItemType.DropToMain).build()),
    BlackTourmalineCore(Item.Builder.id(21730).type(ItemType.DropToMain).build()),
    BloodShard(Item.Builder.id(24777).type(ItemType.DropToMain).build()),
    BrineSabre(Item.Builder.id(11037).type(ItemType.DropToMain).build()),
    BurningClaw(Item.Builder.id(29574).type(ItemType.DropToMain).build()),
    DarkBow(Item.Builder.id(11235).type(ItemType.DropToMain).build()),
    DragonFullHelm(Item.Builder.id(11335).type(ItemType.DropToMain).build()),
    DragonHarpoon(Item.Builder.id(21028).type(ItemType.DropToMain).build()),
    DragonLimbs(Item.Builder.id(21918).type(ItemType.DropToMain).build()),
    DragonMetalLump(Item.Builder.id(22103).type(ItemType.DropToMain).build()),
    DragonMetalSlice(Item.Builder.id(22100).type(ItemType.DropToMain).build()),
    DragonPickaxe(Item.Builder.id(11920).type(ItemType.DropToMain).build()),
    DragonWarhammer(Item.Builder.id(13576).type(ItemType.DropToMain).build()),
    DraconicVisage(Item.Builder.id(11286).type(ItemType.DropToMain).build()),
    EternalCrystal(Item.Builder.id(13227).type(ItemType.DropToMain).build()),
    GodswordShard1(Item.Builder.id(11818).type(ItemType.DropToMain).build()),
    GodswordShard2(Item.Builder.id(11820).type(ItemType.DropToMain).build()),
    GodswordShard3(Item.Builder.id(11822).type(ItemType.DropToMain).build()),
    GraniteBoots(Item.Builder.id(21643).type(ItemType.DropToMain).build()),
    GraniteHammer(Item.Builder.id(21742).type(ItemType.DropToMain).build()),
    GraniteMaul(Item.Builder.id(4153).type(ItemType.DropToMain).build()),
    HeavyFrame(Item.Builder.id(19589).type(ItemType.DropToMain).build()),
    JarOfDarkness(Item.Builder.id(19701).type(ItemType.DropToMain).build()),
    KrakenTentacle(Item.Builder.id(12004).type(ItemType.DropToMain).build()),
    LeafBladedBattleaxe(Item.Builder.id(20727).type(ItemType.DropToMain).build()),
    LeafBladedSword(Item.Builder.id(11902).type(ItemType.DropToMain).build()),
    LightFrame(Item.Builder.id(19586).type(ItemType.DropToMain).build()),
    MonkeyTail(Item.Builder.id(19610).type(ItemType.DropToMain).build()),
    NoxiousHalberd(Item.Builder.id(29796).type(ItemType.DropToMain).build()),
    OccultNecklace(Item.Builder.id(12002).type(ItemType.DropToMain).build()),
    PegasianCrystal(Item.Builder.id(13229).type(ItemType.DropToMain).build()),
    PrimordialCrystal(Item.Builder.id(13231).type(ItemType.DropToMain).build()),
    SeersRing(Item.Builder.id(6731).type(ItemType.DropToMain).build()),
    SkeletalVisage(Item.Builder.id(22006).type(ItemType.DropToMain).build()),
    SmoulderingStone(Item.Builder.id(13233).type(ItemType.DropToMain).build()),
    StaffOfTheDead(Item.Builder.id(11791).type(ItemType.DropToMain).build()),
    TormentedSynapse(Item.Builder.id(29580).type(ItemType.DropToMain).build()),
    TridentOfTheSeasFull(Item.Builder.id(11905).type(ItemType.DropToMain).build()),
    UncutOnyx(Item.Builder.id(6571).type(ItemType.DropToMain).build()),
    WarpedSceptreUncharged(Item.Builder.id(28583).type(ItemType.DropToMain).build()),
    WarriorRing(Item.Builder.id(6735).type(ItemType.DropToMain).build()),
    WyvernVisage(Item.Builder.id(21637).type(ItemType.DropToMain).build()),
    ZamorakHilt(Item.Builder.id(11816).type(ItemType.DropToMain).build()),
    ZamorakianSpear(Item.Builder.id(11824).type(ItemType.DropToMain).build()),
    ZenyteShard(Item.Builder.id(19529).type(ItemType.DropToMain).build()),

    // Kourend
    AncientShard(Item.Builder.id(19677).build()),
    DarkTotemBase(Item.Builder.id(19679).build()),
    DarkTotemMiddle(Item.Builder.id(19681).build()),
    DarkTotemTop(Item.Builder.id(19683).build()),

    // Misc
    Coins(Item.Builder.id(995).build()),
    ShieldLeftHalf(Item.Builder.id(2366).build()),
    ClueScrollBeginner(Item.Builder.id(23182).build()),
    ClueScrollEasy(Item.Builder.id(2677).build()),
    ClueScrollMedium(Item.Builder.id(2801).build()),
    ClueScrollHard(Item.Builder.id(2722).build()),
    ClueScrollElite(Item.Builder.id(12073).build()),
    ClueScrollMaster(Item.Builder.id(19835).build()),
    CrystalShard(Item.Builder.id(23962).build()),

    // Meta
    MorytaniaLegs4(Item.Builder.id(13115).type(ItemType.Meta).build())
    ;

    /**
     * Get the item instance
     * @return
     */
    public Item getItem() {
        return item;
    }

    private final Item item;

    Items(Item item) {
        // this.displayName = displayName;
        this.item = item;
    }

    // Getter method to access the string value
    // public String getDisplayName() {
    //     return displayName;
    // }

    @Override
    public String toString() {
        return item.getName();
    }
}
