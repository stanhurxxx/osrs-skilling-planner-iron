package io.hurx.models.slayer.monsters;

import io.hurx.models.IconPaths;
import io.hurx.models.items.Items;
import io.hurx.models.items.LootItem;

public enum Monsters {
    AberrantSpectresNieve("Aberrant Spectres (Nieve's Cave)", IconPaths.LocationNievesCave, MonsterStats.Builder
        .hitpoints(90)
        
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1).dropRate(33.8f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1).dropRate(49.7f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1).dropRate(72.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1).dropRate(106f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1).dropRate(159f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1).dropRate(227.1f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1).dropRate(318f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1).dropRate(530f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1).dropRate(794.9f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 3).dropRate(15f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(19.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 3).dropRate(26.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(35f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(42f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(52.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(70f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(70f).build())
        
        .loot(LootItem.Builder.item(Items.LavaBattlestaff, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    AberrantSpectresSlayerTower("Aberrant Spectres (Slayer Tower)", IconPaths.LocationSlayerTower, Monsters.AberrantSpectresNieve.getStats()
        .clone()
        .slayerTower(true)
        .build()
    ),
    AberrantSpectresKourend("Deviant Spectres (Catacombs)", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(190)
        
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 3).dropRate(25.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(32.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 3).dropRate(44.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(59.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(71.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(89f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(118.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(118.7f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(40.2f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(59f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(85.8f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(125.9f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(188.8f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(269.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(377.6f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(629.3f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(944f).build())

        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.LavaBattlestaff, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(128f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        
        .build()
    ),
    AbyssalDemonsSlayerTower("Abyssal demons (Slayer Tower)", IconPaths.LocationSlayerTower, MonsterStats.Builder
        .hitpoints(150)
        .slayerTower(true)

        .loot(LootItem.Builder.item(Items.AbyssalAshes, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.AbyssalWhip, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.AbyssalDagger, 1, 1).dropRate(32000f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(61.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(78.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(107.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(143.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(172.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(215.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(287.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(287.4f).build())
        .loot(LootItem.Builder.item(Items.EnsouledAbyssalHead, 1, 1).dropRate(25f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(1200f).build())
        
        .build()
    ),
    AbyssalDemonsCatacombs("Abyssal demons (Catacombs)", IconPaths.LocationKourendCatacombs, Monsters.AbyssalDemonsSlayerTower.getStats()
        .clone()

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(233f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(350f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(350f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(350f).build())
        
        .build()
    ),
    AbyssalDemonsWilderness("Abyssal demons (Wilderness)", IconPaths.LocationWilderness, AbyssalDemonsSlayerTower.getStats().clone()
        .build()
    ),
    AbyssalDemonsSire("Abyssal sire", IconPaths.MonsterAbyssalSire, MonsterStats.Builder
        .hitpoints(400)
        .xpBoostPercentage(12.5f)

        .loot(LootItem.Builder.item(Items.AbyssalAshes, 1, 1).dropRate(1f).build())

        .loot(LootItem.Builder.item(Items.Battlestaff, 10, 1).dropRate(23.17f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 3, 1).dropRate(34.75f).build())
        .loot(LootItem.Builder.item(Items.MysticLavaStaff, 2, 1).dropRate(34.75f).build())
        .loot(LootItem.Builder.item(Items.RuneSword, 3, 1).dropRate(34.75f).build())
        .loot(LootItem.Builder.item(Items.RunePlatebody, 2, 1).dropRate(34.75f).build())
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 2, 1).dropRate(46.33f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 2, 1).dropRate(69.5f).build())
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 6, 1).dropRate(69.5f).build())

        .loot(LootItem.Builder.item(Items.BloodRune, 190, 1).dropRate(27.8f).build())
        .loot(LootItem.Builder.item(Items.DeathRune, 330, 1).dropRate(27.8f).build())
        .loot(LootItem.Builder.item(Items.LawRune, 250, 1).dropRate(27.8f).build())
        .loot(LootItem.Builder.item(Items.SoulRune, 225, 1).dropRate(27.8f).build())

        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 25, 1).dropRate(97.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 25, 1).dropRate(108.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 25, 1).dropRate(108.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 25, 1).dropRate(139f).build())

        .loot(LootItem.Builder.item(Items.RanarrSeed, 2, 1).dropRate(231.7f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 2, 1).dropRate(248.2f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 2, 1).dropRate(315.9f).build())

        .loot(LootItem.Builder.item(Items.RuniteOre, 6, 1).dropRate(34.75f).build())
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 10, 1).dropRate(46.33f).build())
        .loot(LootItem.Builder.item(Items.RuniteBar, 5, 1).dropRate(69.5f).build())

        .loot(LootItem.Builder.item(Items.Coins, 48000, 1).dropRate(12.64f).build())
        .loot(LootItem.Builder.item(Items.SuperRestore, 4, 1).dropRate(27.8f).build())
        .loot(LootItem.Builder.item(Items.AbyssalBludgeon, 1, 1).dropRate(100f * 2.065f * 3f).build())
        .loot(LootItem.Builder.item(Items.AbyssalWhip, 1, 1).dropRate(100f * 10.67f).build())
        .loot(LootItem.Builder.item(Items.AbyssalDagger, 1, 1).dropRate(100f * 4.923f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(180f).build())

        .build()
    ),
    AdamantDragons("Adamant dragons", IconPaths.MonsterAdamantDragon, MonsterStats.Builder
        .hitpoints(295)
        .xpBoostPercentage(7.5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1f).build())
        
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 2, 1).dropRate(1f).build())

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(12.22f).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(15.71f).build())
        .loot(LootItem.Builder.item(Items.RuneScimitar, 1, 1).dropRate(15.71f).build())
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1, 1).dropRate(110f).build())
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1, 1).dropRate(110f).build())
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1, 1).dropRate(110f).build())

        .loot(LootItem.Builder.item(Items.WrathRune, 10, 1).dropRate(13.75f).build())
        .loot(LootItem.Builder.item(Items.ChaosRune, 60, 1).dropRate(15.71f).build())
        .loot(LootItem.Builder.item(Items.DeathRune, 30, 1).dropRate(15.71f).build())

        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(44f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(55f).build())
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 1, 1).dropRate(55f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(73.33f).build())

        .loot(LootItem.Builder.item(Items.DragonLimbs, 1, 1).dropRate(1000f).build())
        .loot(LootItem.Builder.item(Items.DragonMetalSlice, 1, 1).dropRate(5000f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(9000f).build())
       
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(320f).build())
    
        .build()    
    ),
    AnkousNieve("Ankous (Nieve's Cave)", IconPaths.LocationNievesCave, MonsterStats.Builder
        .hitpoints(new int[] { 60, 65, 70 })
        
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(304.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(387.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(533.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(711.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(853.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(1067f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(1422f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(1422f).build())

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(1871.4f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(2687.2f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(3881.5f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(5822.2f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(8733.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(11644.4f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(20960f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(26200f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(34933.3f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(52400f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(104800f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(512f).build())
    
        .build()
    ),
    AnkousCatacombs("Ankous (Catacombs)", IconPaths.LocationKourendCatacombs, AnkousNieve.getStats()
        .clone(60)
        .build()
    ),
    AnkousWilderness("Ankous (Wilderness)", IconPaths.LocationWilderness, AnkousNieve.getStats()
        .clone(100)
        .build()
    ),
    AraxytesLevel96("Araxytes (level 96)", IconPaths.MonsterAraxyteLevel96, MonsterStats.Builder
        .hitpoints(60)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.AraneaBoots, 1, 1).dropRate(4000f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(116.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(147.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(203.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(270.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(325.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(406.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(541.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(541.9f).build())

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(237.7f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(341.3f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(492.9f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(739.4f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(1109.1f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(1478.8f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(2661.9f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(3327.4f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(4436.5f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(6654.8f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(13309.6f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    AraxytesLevel146("Araxytes (level 146)", IconPaths.MonsterAraxyteLevel146, MonsterStats.Builder
        .hitpoints(100)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 2, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 2, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.AraneaBoots, 1, 1).dropRate(2000f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 2, 1).dropRate(116.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 2, 1).dropRate(147.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 2, 1).dropRate(203.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 2, 1).dropRate(270.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 2, 1).dropRate(325.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 2, 1).dropRate(406.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 2, 1).dropRate(541.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 2, 1).dropRate(541.9f).build())

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 2, 1).dropRate(237.7f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 2, 1).dropRate(341.3f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 2, 1).dropRate(492.9f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 2, 1).dropRate(739.4f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 2, 1).dropRate(1109.1f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 2, 1).dropRate(1478.8f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 2, 1).dropRate(2661.9f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 2, 1).dropRate(3327.4f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 2, 1).dropRate(4436.5f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 2, 1).dropRate(6654.8f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 2, 1).dropRate(13309.6f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    Araxxor("Araxxor", IconPaths.MonsterAraxxor, MonsterStats.Builder
        .hitpoints(1020)
        .xpBoostPercentage(67.5f)

        .loot(LootItem.Builder.item(Items.NoxiousHalberd, 1, 1).dropRate(200f).build())
        .loot(LootItem.Builder.item(Items.AraxyteFang, 1, 1).dropRate(600f).build())

        .loot(LootItem.Builder.item(Items.RuneKiteshield, 2, 1).dropRate(14.38f).build())
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 2, 1).dropRate(14.38f).build())
        .loot(LootItem.Builder.item(Items.DragonMace, 2, 1).dropRate(19.17f).build())
        .loot(LootItem.Builder.item(Items.Rune2HSword, 5, 1).dropRate(115f).build())
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 2, 1).dropRate(115f).build())

        .loot(LootItem.Builder.item(Items.DeathRune, 250, 1).dropRate(23f).build())
        .loot(LootItem.Builder.item(Items.NatureRune, 80, 1).dropRate(57.5f).build())
        .loot(LootItem.Builder.item(Items.BloodRune, 180, 1).dropRate(115f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 4, 1).dropRate(38.33f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 3, 1).dropRate(115f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 3, 1).dropRate(115f).build())

        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(958.3f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1026.8f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1306.8f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(50f).build())

        .build()
    ),
    Aviansie("Aviansie", IconPaths.MonsterAviansie, MonsterStats.Builder
        .hitpoints(new int[] {
            70,
            63,
            67,
            83,
            86,
            86,
            69,
            95,
            75,
            98,
            115,
            124,
            139
        })

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 4, 1).dropRate(4.267f).build())
        .loot(LootItem.Builder.item(Items.RuniteLimbs, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    AviansieWilderness("Aviansie (wilderness)", IconPaths.LocationWilderness, Aviansie.getStats()
        .clone(new int[] {
            70,
            63,
            83,
            86,
            75,
            98,
            115,
            124
        })
        .build()
    ),
    KreeArra("Kree'arra", IconPaths.MonsterKreeArra, MonsterStats.Builder
        .hitpoints(255)
        .taskDecrementPerKill(4)

        .loot(LootItem.Builder.item(Items.ArmadylHelmet, 1, 1).dropRate(381f).build())
        .loot(LootItem.Builder.item(Items.ArmadylChestplate, 1, 1).dropRate(381f).build())
        .loot(LootItem.Builder.item(Items.ArmadylChainskirt, 1, 1).dropRate(381f).build())
        .loot(LootItem.Builder.item(Items.ArmadylHilt, 1, 1).dropRate(508f).build())
        .loot(LootItem.Builder.item(Items.GodswordShard1, 1, 1).dropRate(762f).build())
        .loot(LootItem.Builder.item(Items.GodswordShard2, 1, 1).dropRate(762f).build())
        .loot(LootItem.Builder.item(Items.GodswordShard3, 1, 1).dropRate(762f).build())

        .loot(LootItem.Builder.item(Items.BlackDHideBody, 1, 1).dropRate(15.88f).build())
        .loot(LootItem.Builder.item(Items.RuneCrossbow, 1, 1).dropRate(15.88f).build())

        .loot(LootItem.Builder.item(Items.Coins, 19500, 1).dropRate(2.906f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(250f).build())

        .build()
    ),
    Basilisk("Basilisk", IconPaths.MonsterBasilisk, MonsterStats.Builder
        .hitpoints(75)

        .loot(LootItem.Builder.item(Items.MysticHatLight, 1, 1).dropRate(512f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(33.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(42.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(58.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(93.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(117f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(156f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(156f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 1, 1).dropRate(42.67f).build())

        .build()
    ),
    BasiliskKnight("Basilisk Knight", IconPaths.MonsterBasiliskKnight, MonsterStats.Builder
        .hitpoints(300)

        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(52f).build())
        .loot(LootItem.Builder.item(Items.RuneScimitar, 1, 1).dropRate(52f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(52f).build())

        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(55.47f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(69.33f).build())
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 1, 1).dropRate(69.33f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(92.44f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 1, 2).dropRate(52f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(192f).build())
        .loot(LootItem.Builder.item(Items.MysticHatLight, 1, 1).dropRate(256f).build())

        .build()
    ),
    BlackDemonTaverley("Black Demon (Taverley Dungeon)", IconPaths.MonsterBlackDemon, MonsterStats.Builder
        .hitpoints(157)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1).dropRate(50.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1).dropRate(64.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1).dropRate(89f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1).dropRate(118.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1).dropRate(142.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1).dropRate(178.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1).dropRate(237.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1).dropRate(237.4f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.MaliciousAshes, 1).dropRate(1f).build())

        .build()
    ),
    BlackDemonCatacombs("Black Demon (Catacombs)", IconPaths.LocationKourendCatacombs, BlackDemonTaverley.getStats()
        .clone(new int[] { 160, 170 })

        .loot(LootItem.Builder.item(Items.AncientShard, 1).dropRate(226f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1).dropRate(340f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1).dropRate(340f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1).dropRate(340f).build())

        .build()
    ),
    BlackDemonWilderness("Black Demon (Wilderness)", IconPaths.LocationWilderness, BlackDemonTaverley.getStats()
        .clone()
        .build()
    ),
    DemonicGorillas("Demonic Gorilla", IconPaths.MonsterDemonicGorilla, MonsterStats.Builder
        .hitpoints(380)
        .xpBoostPercentage(7.5f)

        .loot(LootItem.Builder.item(Items.MaliciousAshes, 1).dropRate(1f).build()) // "Always" interpreted as 1 drop per kill
        .loot(LootItem.Builder.item(Items.ZenyteShard, 1).dropRate(300f).build())
        .loot(LootItem.Builder.item(Items.BallistaLimbs, 1).dropRate(500f).build())
        .loot(LootItem.Builder.item(Items.BallistaSpring, 1).dropRate(500f).build())
        .loot(LootItem.Builder.item(Items.LightFrame, 1).dropRate(750f).build())
        .loot(LootItem.Builder.item(Items.HeavyFrame, 1).dropRate(1500f).build())
        .loot(LootItem.Builder.item(Items.MonkeyTail, 1).dropRate(1500f).build())
        
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1).dropRate(14.29f).build())
        .loot(LootItem.Builder.item(Items.RunePlateskirt, 1).dropRate(14.29f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1).dropRate(25f).build())
        .loot(LootItem.Builder.item(Items.DragonScimitar, 1).dropRate(50f).build())

        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 7, 13).dropRate(88.9f).build()) // Min-max quantity
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 7, 13).dropRate(111.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 7, 13).dropRate(111.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 7, 13).dropRate(148.1f).build())

        .loot(LootItem.Builder.item(Items.RanarrSeed, 2).dropRate(166.7f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 2).dropRate(178.6f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 2).dropRate(227.3f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 6).dropRate(25f).build())
        .loot(LootItem.Builder.item(Items.RuniteBar, 3).dropRate(33.33f).build()) 

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(100f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1).dropRate(500f).build())

        .build()
    ),
    Skotizo("Skotizo", IconPaths.MonsterSkotizo, MonsterStats.Builder
        .hitpoints(450)
        .xpBoostPercentage(37.5f)

        .loot(LootItem.Builder.item(Items.InfernalAshes, 1).dropRate(1f).build()) // "Always" interpreted as 1 drop per kill
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(1f).build()) // "Always" interpreted as 1 drop per kill

        .loot(LootItem.Builder.item(Items.AncientShard, 1).dropRate(1.333f).build()) // 3/4 rate
        .loot(LootItem.Builder.item(Items.AncientShard, 2).dropRate(6.667f).build()) // 3/20
        .loot(LootItem.Builder.item(Items.AncientShard, 3).dropRate(20f).build()) // 1/20
        .loot(LootItem.Builder.item(Items.AncientShard, 4).dropRate(25f).build()) // 1/25
        .loot(LootItem.Builder.item(Items.AncientShard, 5).dropRate(100f).build()) // 1/100

        .loot(LootItem.Builder.item(Items.UncutOnyx, 1).dropRate(1000f).build())

        .loot(LootItem.Builder.item(Items.RunePlatebody, 3).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 3).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.RunePlateskirt, 3).dropRate(14.29f).build()) // Noted quantity

        .loot(LootItem.Builder.item(Items.DeathRune, 500).dropRate(14.29f).build())
        .loot(LootItem.Builder.item(Items.SoulRune, 450).dropRate(14.29f).build())
        .loot(LootItem.Builder.item(Items.BloodRune, 450).dropRate(14.29f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 75).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 20).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 20).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.RuniteBar, 20).dropRate(14.29f).build()) // Noted quantity

        .loot(LootItem.Builder.item(Items.MahoganyPlank, 150).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.Battlestaff, 25).dropRate(14.29f).build()) // Noted quantity
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 40).dropRate(14.29f).build()) // Quantity 40
        .loot(LootItem.Builder.item(Items.ShieldLeftHalf, 1).dropRate(100f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1).dropRate(5f).build())
        .loot(LootItem.Builder.item(Items.EnsouledDemonHead, 1).dropRate(9f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.JarOfDarkness, 1).dropRate(200f).build())

        .build()
    ),
    BabyBlackDragons("Baby Black Dragons", IconPaths.MonsterBabyBlackDragons, MonsterStats.Builder
        .hitpoints(80)

        .loot(LootItem.Builder.item(Items.BabydragonBones, 1).dropRate(1f).build()) // "Always" interpreted as 1 drop per kill

        .build()
    ),
    BlackDragons("Black Dragons", IconPaths.MonsterBlackDragons, MonsterStats.Builder
        .hitpoints(190)

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1).dropRate(128).build()) // Adamant platebody, 1/128 rarity
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1).dropRate(128).build()) // Rune longsword, 1/128 rarity
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 1).dropRate(42.67f).build()) // Adamantite bar, 3/128 rarity
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(128).build()) // Clue scroll (hard), 1/128 rarity
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1).dropRate(500).build()) // Clue scroll (elite), 1/500 rarity
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1).dropRate(10000).build()) // Draconic visage, 1/10,000 rarity
        .loot(LootItem.Builder.item(Items.DragonBones, 1).dropRate(1).build()) // Dragon bones, Always drop
        .loot(LootItem.Builder.item(Items.BlackDragonhide, 1).dropRate(1).build()) // Black dragonhide, Always drop

        .build()
    ),
    BlackDragonsWilderness("Black Dragons (wilderness)", IconPaths.LocationWilderness, BlackDragons.getStats()
        .clone(250)
        .build()
    ),
    BrutalBlackDragons("Brutal Black Dragons", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(315)
        .xpBoostPercentage(5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1).dropRate(1F).build()) // Dragon bones, Always drop
        .loot(LootItem.Builder.item(Items.BlackDragonhide, 2).dropRate(1F).build()) // Black dragonhide, Always drop

        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1).dropRate(512F).build()) // Dragon platelegs, 1/512 rarity
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1).dropRate(512F).build()) // Dragon plateskirt, 1/512 rarity
        .loot(LootItem.Builder.item(Items.DragonSpear, 1).dropRate(512F).build()) // Dragon spear, 1/512 rarity

        .loot(LootItem.Builder.item(Items.RuneHasta, 1).dropRate(12.8F).build()) // Rune hasta, 10/128 rarity
        .loot(LootItem.Builder.item(Items.RuneSpear, 1).dropRate(12.8F).build()) // Rune spear, 10/128 rarity
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1).dropRate(18.29F).build()) // Rune platelegs, 7/128 rarity
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 2).dropRate(21.33F).build()) // Rune full helm, 6/128 rarity

        .loot(LootItem.Builder.item(Items.RuneLongsword, 1).dropRate(25.6F).build()) // Rune longsword, 5/128 rarity
        .loot(LootItem.Builder.item(Items.BlackDHideBody, 1).dropRate(64F).build()) // Black d'hide body, 2/128 rarity
        .loot(LootItem.Builder.item(Items.RunePlatebody, 1).dropRate(128F).build()) // Rune platebody, 1/128 rarity
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1).dropRate(128F).build()) // Dragon med helm, 1/128 rarity
        .loot(LootItem.Builder.item(Items.DragonLongsword, 1).dropRate(128F).build()) // Dragon longsword, 1/128 rarity
        .loot(LootItem.Builder.item(Items.DragonDagger, 1).dropRate(128F).build()) // Dragon dagger, 1/128 rarity

        .loot(LootItem.Builder.item(Items.RuniteOre, 3).dropRate(64F).build()) // Runite ore, 2/128 rarity (noted)

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(128F).build()) // Clue scroll (hard), 1/128 rarity
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1).dropRate(250F).build()) // Clue scroll (elite), 1/250 rarity
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1).dropRate(10000F).build()) // Draconic visage, 1/10,000 rarity

        .loot(LootItem.Builder.item(Items.AncientShard, 1).dropRate(123F).build()) // Ancient shard, 1/123 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1).dropRate(185F).build()) // Dark totem base, 1/185 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1).dropRate(185F).build()) // Dark totem middle, 1/185 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1).dropRate(185F).build()) // Dark totem top, 1/185 rarity

        .build()
    ),
    KingBlackDragons("King Black Dragons", IconPaths.MonsterKingBlackDragons, MonsterStats.Builder
        .hitpoints(240)
        .xpBoostPercentage(7.5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1).dropRate(1F).build()) // Dragon bones, Always drop
        .loot(LootItem.Builder.item(Items.BlackDragonhide, 2).dropRate(1F).build()) // Black dragonhide, Always drop

        .loot(LootItem.Builder.item(Items.DragonPickaxe, 1).dropRate(1000F).build()) // Dragon pickaxe, 1/1,000 rarity
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1).dropRate(128F).build()) // Dragon med helm, 1/128 rarity

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 3).dropRate(25.6F).build()) // Adamantite bar, 5/128 rarity
        .loot(LootItem.Builder.item(Items.RuniteBar, 1).dropRate(42.67F).build()) // Runite bar, 3/128 rarity

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1).dropRate(450F).build()) // Clue scroll (elite), 1/450 rarity
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1).dropRate(5000F).build()) // Draconic visage, 1/5,000 rarity

        .build()
    ),
    BloodveldsSlayerTower("Bloodvelds (Slayer tower)", IconPaths.LocationSlayerTower, MonsterStats.Builder
        .hitpoints(120)
        .slayerTower(true)

        .loot(LootItem.Builder.item(Items.VileAshes, 1).dropRate(1F).build()) // Vile ashes, Always drop
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1).dropRate(128F).build()) // Rune med helm, 1/128 rarity

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1).dropRate(1170.3F).build()) // Grimy harralander, 1/1,170.3 rarity
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1).dropRate(1490F).build()) // Grimy ranarr weed, 1/1,489.5 rarity
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1).dropRate(2048F).build()) // Grimy irit leaf, 1/2,048 rarity
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1).dropRate(2730.7F).build()) // Grimy avantoe, 1/2,730.7 rarity
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1).dropRate(3276.8F).build()) // Grimy kwuarm, 1/3,276.8 rarity
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1).dropRate(4096F).build()) // Grimy cadantine, 1/4,096 rarity
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1).dropRate(5461.3F).build()) // Grimy lantadyme, 1/5,461.3 rarity
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1).dropRate(5461.3F).build()) // Grimy dwarf weed, 1/5,461.3 rarity

        .loot(LootItem.Builder.item(Items.EnsouledBloodveldHead, 1).dropRate(35F).build()) // Ensouled bloodveld head, 1/35 rarity
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(256F).build()) // Clue scroll (hard), 1/256 rarity

        .build()
    ),
    BloodveldsNieve("Bloodvelds (Nieve's Cave)", IconPaths.LocationNievesCave, BloodveldsSlayerTower.getStats()
        .clone()
        .build()
    ),
    BloodveldsCatacombs("Mutated Bloodvelds (Catacombs)", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(170)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1).dropRate(64F).build()) // Rune med helm, 2/128 rarity
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1).dropRate(128F).build()) // Rune battleaxe, 1/128 rarity

        .loot(LootItem.Builder.item(Items.EnsouledBloodveldHead, 1).dropRate(20F).build()) // Ensouled bloodveld head, 1/20 rarity
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1).dropRate(128F).build()) // Clue scroll (hard), 1/128 rarity

        .loot(LootItem.Builder.item(Items.AncientShard, 1).dropRate(220F).build()) // Ancient shard, 1/220 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1).dropRate(330F).build()) // Dark totem base, 1/330 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1).dropRate(330F).build()) // Dark totem middle, 1/330 rarity
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1).dropRate(330F).build()) // Dark totem top, 1/330 rarity

        .build()
    ),
    BloodveldsWilderness("Bloodvelds (Wilderness)", IconPaths.LocationWilderness, BloodveldsSlayerTower.getStats()
        .clone(134)
        .build()
    ),
    Vorkath("Vorkath", IconPaths.MonsterVorkath, MonsterStats.Builder
        .hitpoints(750)

        .loot(LootItem.Builder.item(Items.SuperiorDragonBones, 2).dropRate(1F).build()) // Superior dragon bones, Always drop
        .loot(LootItem.Builder.item(Items.BlueDragonhide, 2).dropRate(1F).build()) // Blue dragonhide, Always drop

        .loot(LootItem.Builder.item(Items.RuneLongsword, 2).dropRate(30F).build()) // Rune longsword, 2 × 5/150
        .loot(LootItem.Builder.item(Items.RuneLongsword, 3).dropRate(30F).build()) // Rune longsword, 2 × 5/150
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 2).dropRate(30F).build()) // Rune kiteshield, 2 × 5/150
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 3).dropRate(30F).build()) // Rune kiteshield, 2 × 5/150
        .loot(LootItem.Builder.item(Items.Battlestaff, 5).dropRate(37.5F).build()) // Battlestaff, 2 × 4/150 (noted)
        .loot(LootItem.Builder.item(Items.Battlestaff, 15).dropRate(37.5F).build()) // Battlestaff, 2 × 4/150 (noted)
        .loot(LootItem.Builder.item(Items.DragonBattleaxe, 1).dropRate(75F).build()) // Dragon battleaxe, 2 × 2/150
        .loot(LootItem.Builder.item(Items.DragonLongsword, 1).dropRate(75F).build()) // Dragon longsword, 2 × 2/150
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1).dropRate(75F).build()) // Dragon platelegs, 2 × 2/150
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1).dropRate(75F).build()) // Dragon plateskirt, 2 × 2/150

        .loot(LootItem.Builder.item(Items.ChaosRune, 650).dropRate(25F).build()) // Chaos rune, 2 × 6/150
        .loot(LootItem.Builder.item(Items.DeathRune, 300).dropRate(25F).build()) // Death rune, 2 × 6/150
        .loot(LootItem.Builder.item(Items.WrathRune, 30).dropRate(50F).build()) // Wrath rune, 2 × 3/150

        .loot(LootItem.Builder.item(Items.BlueDragonhide, 25).dropRate(18.75F).build()) // Blue dragonhide, 2 × 8/150 (noted)
        .loot(LootItem.Builder.item(Items.BlueDragonhide, 30).dropRate(18.75F).build()) // Blue dragonhide, 2 × 8/150 (noted)
        .loot(LootItem.Builder.item(Items.GreenDragonhide, 25).dropRate(21.43F).build()) // Green dragonhide, 2 × 7/150 (noted)
        .loot(LootItem.Builder.item(Items.GreenDragonhide, 30).dropRate(21.43F).build()) // Green dragonhide, 2 × 7/150 (noted)
        .loot(LootItem.Builder.item(Items.RedDragonhide, 20).dropRate(21.43F).build()) // Red dragonhide, 2 × 7/150 (noted)
        .loot(LootItem.Builder.item(Items.RedDragonhide, 25).dropRate(21.43F).build()) // Red dragonhide, 2 × 7/150 (noted)
        .loot(LootItem.Builder.item(Items.BlackDragonhide, 15).dropRate(21.43F).build()) // Black dragonhide, 2 × 7/150 (noted)
        .loot(LootItem.Builder.item(Items.BlackDragonhide, 25).dropRate(21.43F).build()) // Black dragonhide, 2 × 7/150 (noted)

        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1).dropRate(112.3F).build()) // Snapdragon seed, 2 × 1/112.3
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1).dropRate(118.7F).build()) // Torstol seed, 2 × 1/118.7
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1).dropRate(416.7F).build()) // Ranarr seed, 2 × 1/416.7
        
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 10).dropRate(21.43F).build()) // Adamantite ore, 2 × 7/150
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 30).dropRate(21.43F).build()) // Adamantite ore, 2 × 7/150
        .loot(LootItem.Builder.item(Items.Coins, 20_000).dropRate(30F).build()) // Coins, 2 × 5/150
        .loot(LootItem.Builder.item(Items.Coins, 81_000).dropRate(30F).build()) // Coins, 2 × 5/150
        .loot(LootItem.Builder.item(Items.DragonBones, 15).dropRate(37.5F).build()) // Dragon bones, 2 × 4/150
        .loot(LootItem.Builder.item(Items.DragonBones, 20).dropRate(37.5F).build()) // Dragon bones, 2 × 4/150

        .loot(LootItem.Builder.item(Items.BlueDragonScale, 50).dropRate(10f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(65f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(5000f).build())
        .loot(LootItem.Builder.item(Items.SkeletalVisage, 1, 1).dropRate(5000f).build())

        .build()
    ),
    BlueDragon("Blue dragons", IconPaths.MonsterBlueDragons, MonsterStats.Builder
        .hitpoints(105)

        .loot(LootItem.Builder.item(Items.BlueDragonhide, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())

        .loot(LootItem.Builder.item(Items.EnsouledDragonHead, 1, 1).dropRate(50f).build())
        .loot(LootItem.Builder.item(Items.BlueDragonScale, 50).dropRate(50f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    BabyBlueDragon("Baby Blue Dragons", IconPaths.MonsterBabyBlueDragons, MonsterStats.Builder
        .hitpoints(50)

        .loot(LootItem.Builder.item(Items.BabydragonBones, 1, 1).dropRate(0f).build()) // Always item has a drop rate of 0
        .loot(LootItem.Builder.item(Items.BlueDragonScale, 50).dropRate(100f).build())

        .build()
    ),
    BrutalBlueDragon("Brutal Blue Dragons", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(245)
        .xpBoostPercentage(5f)

        .loot(LootItem.Builder.item(Items.DragonDagger, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonLongsword, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RunePlatebody, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.BlueDragonScale, 5, 5).dropRate(32f).build())

        .loot(LootItem.Builder.item(Items.EnsouledDragonHead, 1, 1).dropRate(20f).build())
        .loot(LootItem.Builder.item(Items.BlueDragonScale, 50).dropRate(33f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(750f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10000f).build())

        .build()
    ),
    BrineRat("Brine Rats", IconPaths.MonsterBrineRat, MonsterStats.Builder
        .hitpoints(50)

        .loot(LootItem.Builder.item(Items.BrineSabre, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollMedium, 1, 1).dropRate(128f).build())

        .build()
    ),
    CaveHorror("Cave Horrors", IconPaths.MonsterCaveHorror, MonsterStats.Builder
        .hitpoints(55)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(90f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(114.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(157.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(210.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(252.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(315.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(420.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(420.1f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(35.7f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(52.4f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(76.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(111.9f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(167.8f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(239.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(335.6f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(559.4f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(839.1f).build())
        .loot(LootItem.Builder.item(Items.BlackMask10, 1, 1).dropRate(512f).build())

        .loot(LootItem.Builder.item(Items.EnsouledHorrorHead, 1, 1).dropRate(30f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    CaveKraken("Cave Kraken", IconPaths.MonsterCaveKraken, MonsterStats.Builder
        .hitpoints(125)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(30.77f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(50f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 1, 1).dropRate(50f).build())
        .loot(LootItem.Builder.item(Items.MysticWaterStaff, 1, 1).dropRate(100f).build())
        
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(304.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(387.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(533.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(711.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(853.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(1067f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(1422.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(1422.2f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(167.4f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(245.8f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(357.6f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(524.4f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(786.7f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1124f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1573.3f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(2622f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(3933.3f).build())

        .loot(LootItem.Builder.item(Items.KrakenTentacle, 1, 1).dropRate(1200f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(100f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(1200f).build())

        .build()
    ),
    Kraken("Kraken", IconPaths.MonsterKraken, MonsterStats.Builder
        .hitpoints(255)

        .loot(LootItem.Builder.item(Items.MysticWaterStaff, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.MysticRobeTop, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.MysticRobeBottom, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.TridentOfTheSeasFull, 1, 1).dropRate(512f).build())

        .loot(LootItem.Builder.item(Items.TorstolSeed, 2, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.Battlestaff, 10, 1).dropRate(32f).build())

        .loot(LootItem.Builder.item(Items.Coins, 10000, 19999).dropRate(8.533f).build())
        .loot(LootItem.Builder.item(Items.KrakenTentacle, 1, 1).dropRate(400f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(500f).build())

        .build()
    ),
    Dagannoth("Dagannoths", IconPaths.MonsterDagannoth, MonsterStats.Builder
        .hitpoints(new int[] { 70, 120 })
        .build()
    ),
    DagannothCatacombs("Dagannoths (catacombs)", IconPaths.LocationKourendCatacombs, Dagannoth.getStats()
        .clone()

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(286f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(430f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(430f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(430f).build())

        .build()
    ),
    DagannothKings("Dagannoth Kings", IconPaths.MonsterDagannothKings, MonsterStats.Builder
        .hitpoints(255 + 255 + 255)
        .xpBoostPercentage(30f)
        .taskDecrementPerKill(3)

        .loot(LootItem.Builder.item(Items.DagannothBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.BerserkerRing, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.WarriorRing, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(18.29f).build())
        .loot(LootItem.Builder.item(Items.EnsouledDagannothHead, 1, 1).dropRate(20f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(42f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(750f).build())
        .loot(LootItem.Builder.item(Items.ArchersRing, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonAxe, 3, 3).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(91.8f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(134.9f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(196.2f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(287.7f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(431.5f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(616.5f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(863.1f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(1438.5f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(2157.7f).build())
        .loot(LootItem.Builder.item(Items.EnsouledDagannothHead, 1, 1).dropRate(20f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(42f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(750f).build())
        
        .loot(LootItem.Builder.item(Items.EarthBattlestaff, 1, 1).dropRate(12.8f).build())
        .loot(LootItem.Builder.item(Items.WaterBattlestaff, 1, 1).dropRate(25.6f).build())
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 1, 1).dropRate(128f).build()) // Noted, drop rate handled separately
        .loot(LootItem.Builder.item(Items.SeersRing, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(91.8f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(134.9f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(196.2f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(287.7f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(431.5f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(616.5f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(863.1f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(1438.5f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(2157.7f).build())
        .loot(LootItem.Builder.item(Items.EnsouledDagannothHead, 1, 1).dropRate(20f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(42f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(750f).build())

        .build()
    ),
    DarkBeast("Dark Beasts", IconPaths.MonsterDarkBeast, MonsterStats.Builder
        .hitpoints(220)

        // Rare drops
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.Rune2HSword, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DarkBow, 1, 1).dropRate(512f).build())

        // Alchables and herbs
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 2).dropRate(48.8f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 2).dropRate(62.1f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 2).dropRate(85.3f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 2).dropRate(113.8f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 2).dropRate(136.5f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 2).dropRate(170.7f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 2).dropRate(227.6f).build()) // 1-2
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 2).dropRate(227.6f).build()) // 1-2

        // Seeds
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(160.7f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(236f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(343.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(503.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(755.2f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1078.9f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1510.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(2517.3f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(3776f).build())

        // Notable drops
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 3, 3).dropRate(64f).build()) // 2/128
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 5, 5).dropRate(128f).build()) // 1/128

        // Other drops
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(1200f).build())

        .build()
    ),
    Drakes("Drakes", IconPaths.MonsterDrake, MonsterStats.Builder
        .hitpoints(250)
        
        .loot(LootItem.Builder.item(Items.DrakeBones, 1, 1).dropRate(1).build()) // Always drop

        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(28.33f).build())
        .loot(LootItem.Builder.item(Items.MysticEarthStaff, 1, 1).dropRate(85f).build())
        .loot(LootItem.Builder.item(Items.DragonMace, 1, 1).dropRate(85f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 3).dropRate(155.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(197.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 3).dropRate(272f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(362.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(435.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(544f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(725.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(725.3f).build())

        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(90.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(90.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(113.3f).build())
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 1, 3).dropRate(113.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(113.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(113.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(151.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 3).dropRate(151.1f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(426.8f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(626.9f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(911.8f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(1337.3f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(2006f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(2865.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(4012f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(6686.7f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(10030f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    DustDevils("Dust Devils", IconPaths.MonsterDustDevil, MonsterStats.Builder
        .hitpoints(105)
        
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.EarthBattlestaff, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.MysticEarthStaff, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonDagger, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DustBattlestaff, 1, 1).dropRate(4000f).build())
        .loot(LootItem.Builder.item(Items.DragonChainbody, 1, 1).dropRate(32768f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(61.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(78.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(107.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(143.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(172.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(215.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(287.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(287.4f).build())

        .loot(LootItem.Builder.item(Items.Coins, 2000, 4000).dropRate(4.571f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 4, 4).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.ChaosRune, 80, 80).dropRate(18f).build())
        .loot(LootItem.Builder.item(Items.SoulRune, 20, 20).dropRate(31.5f).build())
        .loot(LootItem.Builder.item(Items.SoulRune, 50, 50).dropRate(126f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(60.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(77.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(106.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(141.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(169.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(212.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(282.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(282.9f).build())

        .loot(LootItem.Builder.item(Items.Coins, 2000, 4000).dropRate(4.5f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 4, 4).dropRate(126f).build())

        .build()
    ),
    DustDevilsCatacombs("Dust Devils (Catacombs)", IconPaths.LocationKourendCatacombs, DustDevils.getStats()
        .clone(130)

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(246f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(370f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(370f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(370f).build())

        .build()
    ),
    DustDevilsWilderness("Dust Devils (Wilderness)", IconPaths.LocationWilderness, DustDevils.getStats()
        .clone()
        .build()
    ),
    ElvesIorwerthWarrior("Iorwerth Warriors", IconPaths.MonsterElfIorwerth, MonsterStats.Builder
        .hitpoints(105)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(37.8f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(55.5f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(80.8f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(118.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(177.7f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(253.8f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(355.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(592.3f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(888.5f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    ElvesElfWarrior("Elf Warrior", IconPaths.MonsterElfWarrior, MonsterStats.Builder
        .hitpoints(105)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    ElvesPrifGuard("Prifddinas Guard", IconPaths.MonsterPrifGuard, MonsterStats.Builder
        .hitpoints(105)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.CrystalShard, 3, 5).dropRate(24f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    FireGiantsNieve("Fire Giants", IconPaths.MonsterFireGiant, MonsterStats.Builder
        .hitpoints(111)

        .loot(LootItem.Builder.item(Items.RuneScimitar, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(61.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(78.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(107.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(143.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(172.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(215.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(287.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(287.4f).build())
        .loot(LootItem.Builder.item(Items.EnsouledGiantHead, 1, 1).dropRate(20f).build())

        .build()
    ),
    FireGiantsCatacombs("Fire Giants (Catacombs)", IconPaths.LocationKourendCatacombs, FireGiantsNieve.getStats()
        .clone(new int[] { 130, 150 })
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(246f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(370f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(370f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(370f).build())

        .build()
    ),
    SpittingWyvern("Spitting Wyvern", IconPaths.MonsterSpittingWyvern, MonsterStats.Builder
        .hitpoints(200)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.WyvernBones, 1, 1).dropRate(Float.POSITIVE_INFINITY).build())
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(30.75f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 3, 5).dropRate(41f).build())
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GraniteLongsword, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.GraniteBoots, 1, 1).dropRate(2560f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 2, 2).dropRate(43.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 2, 2).dropRate(72.9f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(58f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1098.2f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1397.7f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 2, 4).dropRate(20.5f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 1, 2).dropRate(41f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(118f).build())
        .loot(LootItem.Builder.item(Items.WyvernVisage, 1, 1).dropRate(12000f).build())

        .build()
    ),
    TalonedWyvern("Taloned Wyvern", IconPaths.MonsterTalonedWyvern, MonsterStats.Builder
        .hitpoints(200)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.WyvernBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(30.75f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 3, 5).dropRate(41f).build())
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GraniteLongsword, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.GraniteBoots, 1, 1).dropRate(2560f).build())

        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 2, 2).dropRate(43.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 2, 2).dropRate(72.9f).build())

        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(58f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1098f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1397.7f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 2, 4).dropRate(20.5f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 1, 2).dropRate(41f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(118f).build())
        .loot(LootItem.Builder.item(Items.WyvernVisage, 1, 1).dropRate(12000f).build())

        .build()
    ),
    LongTailedWyvern("Long-tailed Wyvern", IconPaths.MonsterLongTailedWyvern, MonsterStats.Builder
        .hitpoints(200)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.WyvernBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(30.75f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 3, 5).dropRate(41f).build())
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GraniteLongsword, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.GraniteBoots, 1, 1).dropRate(2560f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 2, 2).dropRate(43.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 2, 2).dropRate(54.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(61.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 2, 2).dropRate(72.9f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(58f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1098.2f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1397.7f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 2, 4).dropRate(20.5f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 1, 2).dropRate(41f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(118f).build())
        .loot(LootItem.Builder.item(Items.WyvernVisage, 1, 1).dropRate(12000f).build())

        .build()
    ),
    AncientWyvern("Ancient Wyvern", IconPaths.MonsterAncientWyvern, MonsterStats.Builder
        .hitpoints(300)
        .xpBoostPercentage(5f)

        .loot(LootItem.Builder.item(Items.WyvernBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.AirBattlestaff, 2, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 6, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 1, 1).dropRate(3f).build())
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(3f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(2f).build())
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(2f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(2f).build())
        .loot(LootItem.Builder.item(Items.GraniteLongsword, 1, 1).dropRate(0.17f).build())
        .loot(LootItem.Builder.item(Items.GraniteBoots, 1, 1).dropRate(0.17f).build())

        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 2, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 4, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 3, 1).dropRate(5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 3, 1).dropRate(2f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 3, 1).dropRate(2f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 3, 1).dropRate(3f).build())

        .loot(LootItem.Builder.item(Items.RanarrSeed, 2, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 3, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 3, 1).dropRate(6f).build())
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 10, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 15, 1).dropRate(4f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 2, 1).dropRate(3f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 3, 1).dropRate(3f).build())
        .loot(LootItem.Builder.item(Items.RuneCrossbow, 1, 1).dropRate(3f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.WyvernVisage, 1, 1).dropRate(0.01f).build())

        .build()
    ),
    Gargoyles("Gargoyles", IconPaths.MonsterGargoyle, MonsterStats.Builder
        .hitpoints(105)
        .slayerTower(true)

        .build()
    ),
    GrotesqueGuardians("Grotesque Guardians", IconPaths.MonsterGrotesqueGuardian, MonsterStats.Builder
        .hitpoints(450 + 450)
        .slayerTower(true)

        .loot(LootItem.Builder.item(Items.GraniteMaul, 1, 1).dropRate(125.0f).build()) // 1 / 0.008
        .loot(LootItem.Builder.item(Items.GraniteHammer, 1, 1).dropRate(375.0f).build()) // 1 / 0.00267
        .loot(LootItem.Builder.item(Items.BlackTourmalineCore, 1, 1).dropRate(500.0f).build()) // 1 / 0.002

        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(11.49f).build()) // 1 / 0.087
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(13.70f).build()) // 1 / 0.073
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1, 1).dropRate(13.70f).build()) // 1 / 0.073
        .loot(LootItem.Builder.item(Items.Rune2HSword, 1, 1).dropRate(17.24f).build()) // 1 / 0.058
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(22.93f).build()) // 1 / 0.0437
        .loot(LootItem.Builder.item(Items.DragonLongsword, 1, 1).dropRate(68.97f).build()) // 1 / 0.0145
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1, 1).dropRate(68.97f).build()) // 1 / 0.0145

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 25, 40).dropRate(11.49f).build()) // 1 / 0.087
        .loot(LootItem.Builder.item(Items.RuniteOre, 3, 6).dropRate(17.24f).build()) // 1 / 0.058
        .loot(LootItem.Builder.item(Items.RuniteBar, 3, 5).dropRate(22.93f).build()) // 1 / 0.0437

        .loot(LootItem.Builder.item(Items.Coins, 10000, 20000).dropRate(6.90f).build()) // 1 / 0.145
        .loot(LootItem.Builder.item(Items.ChaosRune, 100, 150).dropRate(6.90f).build()) // 1 / 0.145
        .loot(LootItem.Builder.item(Items.Coins, 25000, 25000).dropRate(27.40f).build()) // 1 / 0.0365
        .loot(LootItem.Builder.item(Items.DeathRune, 60, 100).dropRate(27.40f).build()) // 1 / 0.0365
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 5, 10).dropRate(68.49f).build()) // 1 / 0.0146

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(229.90f).build()) // 1 / 0.00435

        .build()
    ),
    GreaterDemons("Greater demons", IconPaths.MonsterGreaterDemon, MonsterStats.Builder
        .hitpoints(87)

        .loot(LootItem.Builder.item(Items.VileAshes, 1, 1).dropRate(1.0f).build()) // 1 / 1.0
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128.0f).build()) // 1 / 0.0078125

        .loot(LootItem.Builder.item(Items.EnsouledDemonHead, 1, 1).dropRate(40.0f).build()) // 1 / 0.025
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128.0f).build()) // 1 / 0.0078125

        .build()
    ),
    GreaterDemonsCatacombs("Greater demons (Catacombs)", IconPaths.LocationKourendCatacombs, GreaterDemons.getStats()
        .clone(new int[] { 115, 120, 130 })

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(256.0f).build()) // 1 / 0.00390625
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(385.0f).build()) // 1 / 0.002597
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(385.0f).build()) // 1 / 0.002597
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(385.0f).build()) // 1 / 0.002597

        .build()
    ),
    GreaterDemonsWilderness("Greater demons (Wilderness)", IconPaths.LocationWilderness, GreaterDemons.getStats()
        .clone()
        .build()
    ),
    TormentedDemon("Tormented demons", IconPaths.MonsterTormentedDemon, MonsterStats.Builder
        .hitpoints(600)
        .xpBoostPercentage(77.5f)

        .loot(LootItem.Builder.item(Items.InfernalAshes, 1, 1).dropRate(1.0f).build()) // 1 / 1.0

        .loot(LootItem.Builder.item(Items.TormentedSynapse, 1, 1).dropRate(500.0f).build()) // 1 / 0.002
        .loot(LootItem.Builder.item(Items.BurningClaw, 1, 1).dropRate(250.0f).build()) // 1 / 0.003996

        .loot(LootItem.Builder.item(Items.RunePlatebody, 1, 1).dropRate(12.75f).build()) // 1 / 0.07843
        .loot(LootItem.Builder.item(Items.DragonDagger, 1, 1).dropRate(17.00f).build()) // 1 / 0.05882
        .loot(LootItem.Builder.item(Items.Battlestaff, 1, 1).dropRate(17.00f).build()) // 1 / 0.05882
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(25.5f).build()) // 1 / 0.03922

        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(40.8f).build()) // 1 / 0.0245
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(51.0f).build()) // 1 / 0.0196
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(51.0f).build()) // 1 / 0.0196
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(68.0f).build()) // 1 / 0.0147
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(81.5f).build()) // 1 / 0.01227
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(102.0f).build()) // 1 / 0.0098
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 1, 1).dropRate(102.0f).build()) // 1 / 0.0098
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(136.0f).build()) // 1 / 0.00735

        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(425.0f).build()) // 1 / 0.002353
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(456.0f).build()) // 1 / 0.002195
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(579.0f).build()) // 1 / 0.001724

        .loot(LootItem.Builder.item(Items.MaliciousAshes, 2, 3).dropRate(25.5f).build()) // 1 / 0.03922
        .loot(LootItem.Builder.item(Items.FireOrb, 5, 7).dropRate(25.5f).build()) // 1 / 0.03922

        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(128.0f).build()) // 1 / 0.0078125

        .build()
    ),
    Kril("K'ril Tsutsaroth", IconPaths.MonsterKril, MonsterStats.Builder
        .hitpoints(255)
        .taskDecrementPerKill(2)
        .xpBoostPercentage(37.5f)

        .loot(LootItem.Builder.item(Items.InfernalAshes, 1, 1).dropRate(1.0f).build()) // 1 / 1.0

        .loot(LootItem.Builder.item(Items.SteamBattlestaff, 1, 1).dropRate(126.0f).build()) // 1 / 0.007874
        .loot(LootItem.Builder.item(Items.ZamorakianSpear, 1, 1).dropRate(126.0f).build()) // 1 / 0.007874
        .loot(LootItem.Builder.item(Items.StaffOfTheDead, 1, 1).dropRate(508.0f).build()) // 1 / 0.001968
        .loot(LootItem.Builder.item(Items.ZamorakHilt, 1, 1).dropRate(508.0f).build()) // 1 / 0.001968

        .build()
    ),
    Hellhounds("Hellhounds (Nieve's Cave)", IconPaths.LocationNievesCave, MonsterStats.Builder
        .hitpoints(116)

        .loot(LootItem.Builder.item(Items.VileAshes, 1, 1).dropRate(1.0f).build()) // Always
        .loot(LootItem.Builder.item(Items.SmoulderingStone, 1, 1).dropRate(32768.0f).build()) // 1/32768
        .loot(LootItem.Builder.item(Items.EnsouledHellhoundHead, 1, 1).dropRate(40.0f).build()) // 1/40
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(64.0f).build()) // 1/64

        .build()
    ),
    HellhoundsCatacombs("Hellhounds (Catacombs)", IconPaths.LocationKourendCatacombs, Hellhounds.getStats()
        .clone()

        // Hellhound Loot Table
        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(256f).build()) // 1/256
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(384f).build()) // 1/384
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(384f).build()) // 1/384
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(384f).build()) // 1/384

        .build()
    ),
    HellhoundsWilderness("Hellhounds (Wilderness)", IconPaths.LocationWilderness, Hellhounds.getStats()
        .clone(150)
        .build()
    ),
    Cerberus("Cerberus", IconPaths.MonsterCerberus, MonsterStats.Builder
        .hitpoints(600)
        .xpBoostPercentage(15f)

        // Cerberus Loot Table
        .loot(LootItem.Builder.item(Items.InfernalAshes, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.PrimordialCrystal, 1, 1).dropRate(512f).build()) // 1/512
        .loot(LootItem.Builder.item(Items.PegasianCrystal, 1, 1).dropRate(512f).build()) // 1/512
        .loot(LootItem.Builder.item(Items.EternalCrystal, 1, 1).dropRate(512f).build()) // 1/512
        .loot(LootItem.Builder.item(Items.SmoulderingStone, 1, 1).dropRate(512f).build()) // 1/512
        .loot(LootItem.Builder.item(Items.RunePlatebody, 1, 1).dropRate(25.6f).build()) // 5/128
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(32f).build()) // 4/128
        .loot(LootItem.Builder.item(Items.Rune2HSword, 1, 1).dropRate(32f).build()) // 4/128
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(42.67f).build()) // 3/128
        .loot(LootItem.Builder.item(Items.Battlestaff, 6, 6).dropRate(42.67f).build()) // 3/128 (noted)
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(42.67f).build()) // 3/128
        .loot(LootItem.Builder.item(Items.LavaBattlestaff, 1, 1).dropRate(64f).build()) // 2/128
        .loot(LootItem.Builder.item(Items.RuneHalberd, 1, 1).dropRate(64f).build()) // 2/128
        .loot(LootItem.Builder.item(Items.SuperRestore, 2, 2).dropRate(21.33f).build()) // 6/128
        .loot(LootItem.Builder.item(Items.Coins, 10000, 10000).dropRate(25.6f).build()) // 5/128
        .loot(LootItem.Builder.item(Items.DragonBones, 20, 20).dropRate(25.6f).build()) // 5/128 (noted)
        .loot(LootItem.Builder.item(Items.WineOfZamorak, 15, 15).dropRate(25.6f).build()) // 5/128 (noted)
        .loot(LootItem.Builder.item(Items.FireOrb, 20, 20).dropRate(32f).build()) // 4/128 (noted)
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 6, 6).dropRate(32f).build()) // 4/128 (noted)
        .loot(LootItem.Builder.item(Items.RuniteOre, 5, 5).dropRate(42.67f).build()) // 3/128 (noted)
        .loot(LootItem.Builder.item(Items.TorstolSeed, 3, 3).dropRate(64f).build()) // 2/128
        .loot(LootItem.Builder.item(Items.EnsouledHellhoundHead, 1, 1).dropRate(15f).build()) // 1/15
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(100f).build()) // 1/100

        .build()
    ),
    IronDragons("Iron Dragons", IconPaths.MonsterIronDragon, MonsterStats.Builder
        .hitpoints(165)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1, 1).dropRate(1024f).build()) // 1/1,024
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1, 1).dropRate(1024f).build()) // 1/1,024
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(128f).build()) // 1/128
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(128f).build()) // 1/128
        .loot(LootItem.Builder.item(Items.RuniteLimbs, 1, 1).dropRate(25.6f).build()) // 5/128
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 2, 2).dropRate(42.67f).build()) // 3/128
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build()) // 1/128
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10000f).build()) // 1/10,000

        .build()
    ),
    IronDragonsCatacombs("Iron Dragons", IconPaths.LocationKourendCatacombs, IronDragons.getStats()
        .clone(195)
        .xpBoostPercentage(2.5f)
        
        // Iron Dragon Additional Loot Table
        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(203f).build()) // 1/203
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(305f).build()) // 1/305
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(305f).build()) // 1/305
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(305f).build()) // 1/305

        .build()
    ),
    KalphiteWorkers("Kalphite Workers", IconPaths.MonsterKalphiteWorker, MonsterStats.Builder
        .hitpoints(40)

        // Kalphite Worker Loot Table
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(167.2f).build()) // 1/167.2
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(212.8f).build()) // 1/212.8
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(292.6f).build()) // 1/292.6
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(390.1f).build()) // 1/390.1
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(468.1f).build()) // 1/468.1
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(585.1f).build()) // 1/585.1
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(780.2f).build()) // 1/780.2
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(780.2f).build()) // 1/780.2
        .loot(LootItem.Builder.item(Items.EnsouledKalphiteHead, 1, 1).dropRate(250f).build()) // 1/250

        .build()
    ),
    KalphiteSoldiers("Kalphite Soldiers", IconPaths.MonsterKalphiteSoldier, MonsterStats.Builder
        .hitpoints(90)

        // Kalphite Soldier Loot Table
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(1170.3f).build()) // 1/1170.3
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(1489.5f).build()) // 1/1489.5
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(2048f).build()) // 1/2048
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(2730.7f).build()) // 1/2730.7
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(3276.8f).build()) // 1/3276.8
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(4096f).build()) // 1/4096
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(5461.3f).build()) // 1/5461.3
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(5461.3f).build()) // 1/5461.3
        .loot(LootItem.Builder.item(Items.EnsouledKalphiteHead, 1, 1).dropRate(90f).build()) // 1/90

        .build()
    ),
    KalphiteGuardians("Kalphite Guardians", IconPaths.MonsterKalphiteGuardian, MonsterStats.Builder
        .hitpoints(170)

        // Kalphite Guardian Loot Table
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(50.9f).build()) // 1/50.9
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(64.8f).build()) // 1/64.8
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(89f).build()) // 1/89
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(118.7f).build()) // 1/118.7
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(142.5f).build()) // 1/142.5
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(178.1f).build()) // 1/178.1
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(237.4f).build()) // 1/237.4
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(237.4f).build()) // 1/237.4
        .loot(LootItem.Builder.item(Items.EnsouledKalphiteHead, 1, 1).dropRate(35f).build()) // 1/35

        .build()
    ),
    KalphiteQueens("Kalphite Queens", IconPaths.MonsterKalphiteQueen, MonsterStats.Builder
        .hitpoints(255 + 255)
        .xpBoostPercentage(5f)

        // Kalphite Queen Loot Table
        .loot(LootItem.Builder.item(Items.Battlestaff, 10, 1).dropRate(25.2f).build()) // 1/25.2
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(31.5f).build()) // 1/31.5
        .loot(LootItem.Builder.item(Items.LavaBattlestaff, 1, 1).dropRate(63f).build()) // 1/63

        .loot(LootItem.Builder.item(Items.DeathRune, 150, 1).dropRate(21f).build()) // 1/21
        .loot(LootItem.Builder.item(Items.BloodRune, 100, 1).dropRate(21f).build()) // 1/21

        .loot(LootItem.Builder.item(Items.GrimyToadflax, 25, 1).dropRate(63f).build()) // 1/63
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 25, 1).dropRate(63f).build()) // 1/63
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 25, 1).dropRate(63f).build()) // 1/63
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 25, 1).dropRate(63f).build()) // 1/63

        .loot(LootItem.Builder.item(Items.RuniteBar, 3, 1).dropRate(25.2f).build()) // 1/25.2
        .loot(LootItem.Builder.item(Items.BucketOfSand, 100, 1).dropRate(31.5f).build()) // 1/31.5
        .loot(LootItem.Builder.item(Items.GoldOre, 250, 1).dropRate(31.5f).build()) // 1/31.5

        .loot(LootItem.Builder.item(Items.WineOfZamorak, 60, 1).dropRate(12.6f).build()) // 1/12.6
        .loot(LootItem.Builder.item(Items.PotatoCactus, 100, 1).dropRate(15.75f).build()) // 1/15.75
        .loot(LootItem.Builder.item(Items.Coins, 15000, 1).dropRate(25.2f).build()) // 1/25.2
        .loot(LootItem.Builder.item(Items.Grapes, 100, 1).dropRate(25.2f).build()) // 1/25.2
        .loot(LootItem.Builder.item(Items.CactusSpine, 10, 1).dropRate(42f).build()) // 1/42

        .loot(LootItem.Builder.item(Items.EnsouledKalphiteHead, 1, 1).dropRate(20f).build()) // 1/20
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(100f).build()) // 1/100
        .loot(LootItem.Builder.item(Items.DragonChainbody, 1, 1).dropRate(128f).build()) // 1/128
        .loot(LootItem.Builder.item(Items.Dragon2HSword, 1, 1).dropRate(256f).build()) // 1/256
        .loot(LootItem.Builder.item(Items.DragonPickaxe, 1, 1).dropRate(400f).build()) // 1/400

        .build()
    ),
    Kurasks("Kurasks", IconPaths.MonsterKurask, MonsterStats.Builder
        .hitpoints(97)

        // Kurask Loot Table
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1, 1).dropRate(41.33f).build()) // 1/41.33
        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(41.33f).build()) // 1/41.33
        .loot(LootItem.Builder.item(Items.LeafBladedSword, 1, 1).dropRate(384f).build()) // 1/384
        .loot(LootItem.Builder.item(Items.MysticRobeTopLight, 1, 1).dropRate(512f).build()) // 1/512
        .loot(LootItem.Builder.item(Items.LeafBladedBattleaxe, 1, 1).dropRate(1026f).build()) // 1/1,026

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(63f).build()) // 1/63
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(80.2f).build()) // 1/80.2
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(110.2f).build()) // 1/110.2
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(147f).build()) // 1/147
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(176.4f).build()) // 1/176.4
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(220.4f).build()) // 1/220.4
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(293.9f).build()) // 1/293.9
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(293.9f).build()) // 1/293.9

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(41.5f).build()) // 1/41.5
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(61f).build()) // 1/61
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(88.7f).build()) // 1/88.7
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(130.1f).build()) // 1/130.1
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(195.1f).build()) // 1/195.1
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(278.7f).build()) // 1/278.7
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(390.2f).build()) // 1/390.2
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(650.3f).build()) // 1/650.3
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(975.5f).build()) // 1/975.5

        .loot(LootItem.Builder.item(Items.Coins, 2000, 1).dropRate(7.75f).build()) // 1/7.75
        .loot(LootItem.Builder.item(Items.WhiteBerries, 12, 1).dropRate(20.67f).build()) // 1/20.67
        .loot(LootItem.Builder.item(Items.Coins, 10000, 1).dropRate(24.8f).build()) // 1/24.8

        .loot(LootItem.Builder.item(Items.CrystalShard, 3, 1).dropRate(24f).build()) // 1/24
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build()) // 1/128

        .build()
    ),
    Lizardmen("Lizardmen", IconPaths.MonsterLizardman, MonsterStats.Builder
        .hitpoints(60)

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(108.5f).build()) // Lizardman drops Harralander seed
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(155.9f).build()) // Lizardman drops Ranarr seed
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(225.1f).build()) // Lizardman drops Toadflax seed
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(337.7f).build()) // Lizardman drops Irit seed
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(506.5f).build()) // Lizardman drops Avantoe seed
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(675.4f).build()) // Lizardman drops Kwuarm seed
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1215.7f).build()) // Lizardman drops Snapdragon seed
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1519.6f).build()) // Lizardman drops Cadantine seed
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(2026.1f).build()) // Lizardman drops Lantadyme seed
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(3039.2f).build()) // Lizardman drops Dwarf weed seed
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(6078.4f).build()) // Lizardman drops Torstol seed

        .build()
    ),
    LizardmanBrutes("Lizardman Brutes", IconPaths.MonsterLizardmanBrute, MonsterStats.Builder
        .hitpoints(60)

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(83.6f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(120f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(173.4f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(260.1f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(390.1f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(520.1f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(936.2f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1170.3f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1560.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(2340.5f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(4681.1f).build())

        .build()
    ),
    LizardmanShamans("Lizardman Shamans", IconPaths.MonsterLizardmanShaman, MonsterStats.Builder
        .hitpoints(150)
        .xpBoostPercentage(5f)

        .loot(LootItem.Builder.item(Items.DragonWarhammer, 1, 1).dropRate(3000f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(27.78f).build())
        .loot(LootItem.Builder.item(Items.EarthBattlestaff, 1, 1).dropRate(29.41f).build())
        .loot(LootItem.Builder.item(Items.MysticEarthStaff, 1, 1).dropRate(29.41f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(31.25f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(41.67f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 2, 3).dropRate(35.56f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 2, 3).dropRate(44.44f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 2, 3).dropRate(44.44f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 2, 3).dropRate(59.26f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(416.7f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(446.4f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(568.2f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(200f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(1200f).build())

        .build()
    ),
    MithrilDragons("Mithril Dragons", IconPaths.MonsterMithrilDragon, MonsterStats.Builder
        .hitpoints(254)
        .xpBoostPercentage(7.5f)

        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(16f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.RuneSpear, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonFullHelm, 1, 1).dropRate(32768f).build())
        .loot(LootItem.Builder.item(Items.Coins, 600, 600).dropRate(7.529f).build())
        .loot(LootItem.Builder.item(Items.Coins, 876, 876).dropRate(18.29f).build())
        .loot(LootItem.Builder.item(Items.DragonFullHelm, 1, 1).dropRate(42.67f * 256f).build())
        .loot(LootItem.Builder.item(Items.RuniteBar, 2, 2).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(350f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10000f).build())

        .build()
    ),
    MutatedZygomites("Mutated Zygomites", IconPaths.MonsterMutatedZygomite, MonsterStats.Builder
        .hitpoints(65)
        .build()
    ),
    AncientZygomites("Ancient Zygomites", IconPaths.MonsterAncientZygomite, MonsterStats.Builder
        .hitpoints(150)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(19.67f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(31.47f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(39.33f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(39.33f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(52.44f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(983.3f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1054f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1341f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollMedium, 1, 1).dropRate(128f).build())

        .build()
    ),
    NechryaelsSlayerTower("Nechryaels (Slayer Tower)", IconPaths.LocationSlayerTower, MonsterStats.Builder
        .hitpoints(105)

        .loot(LootItem.Builder.item(Items.MaliciousAshes, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.Rune2HSword, 1, 1).dropRate(29f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(38.67f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(32.4f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(47.5f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(69.1f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(101.4f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(152.1f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(217.3f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(304.2f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(507f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(760.4f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    NechryaelsCatacombs("Greater Nechryaels (Catacombs)", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(205)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(167.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(212.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(292.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(390.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(468.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(585.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(780.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(780.2f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(128.5f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(188.8f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(274.6f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(402.8f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(604.2f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(863.1f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1208.3f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(2014f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(3020.8f).build())
        .loot(LootItem.Builder.item(Items.CrystalShard, 3, 5).dropRate(24f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(196f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(295f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(295f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(295f).build())

        .build()
    ),
    NechryaelsWilderness("Greater Nechryaels (Wilderness)", IconPaths.LocationWilderness, MonsterStats.Builder
        .hitpoints(205)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(28f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(37.33f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 1, 1).dropRate(56f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(112f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(146.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(186.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(256f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(341.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(409.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(682.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(682.7f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 2).dropRate(112.5f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 2).dropRate(165.2f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 2).dropRate(240.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 2).dropRate(352.4f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 2).dropRate(528.6f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 2).dropRate(755.2f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 2).dropRate(1_057.3f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 2).dropRate(1_762.1f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 2).dropRate(2_643.2f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    RedDragons("Red Dragons", IconPaths.MonsterRedDragon, MonsterStats.Builder
        .hitpoints(140)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1f).build()) // Always drop
        .loot(LootItem.Builder.item(Items.RedDragonhide, 1, 1).dropRate(1f).build())

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(585.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(744.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(1_024f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(1_365.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(1_638.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(2_048f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(2_730.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(2_730.7f).build())

        .loot(LootItem.Builder.item(Items.AdamantiteBar, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.EnsouledDragonHead, 1, 1).dropRate(40f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    BabyRedDragons("Baby Red Dragons", IconPaths.MonsterBabyRedDragon, MonsterStats.Builder
        .hitpoints(50)

        .loot(LootItem.Builder.item(Items.BabydragonBones, 1, 1).dropRate(1f).build()) // Always drop

        .build()
    ),
    BrutalRedDragons("Brutal Red Dragons", IconPaths.LocationKourendCatacombs, MonsterStats.Builder
        .hitpoints(285)
        .xpBoostPercentage(5f)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1f).build()) // Always drop
        .loot(LootItem.Builder.item(Items.RedDragonhide, 2, 2).dropRate(1f).build()) // Always drop
        .loot(LootItem.Builder.item(Items.RuneHasta, 1, 1).dropRate(12.8f).build())
        .loot(LootItem.Builder.item(Items.RuneSpear, 1, 1).dropRate(12.8f).build())
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1, 1).dropRate(25.6f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 2, 2).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.DragonDagger, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonLongsword, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RunePlatebody, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.EnsouledDragonHead, 1, 1).dropRate(20f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(500f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10_000f).build())
        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(143f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(215f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(215f).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(215f).build())

        .build()
    ),
    RuneDragons("Rune Dragons", IconPaths.MonsterRuneDragon, MonsterStats.Builder
        .hitpoints(330)
        .xpBoostPercentage(10)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1f).build()) // Always drop
        .loot(LootItem.Builder.item(Items.RuniteBar, 1, 1).dropRate(1f).build()) // Always drop
        .loot(LootItem.Builder.item(Items.RunePlatebody, 1, 1).dropRate(14.11f).build())
        .loot(LootItem.Builder.item(Items.RuneLongsword, 1, 1).dropRate(15.88f).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(18.14f).build())
        .loot(LootItem.Builder.item(Items.RuneScimitar, 1, 1).dropRate(18.14f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(18.14f).build())
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1, 1).dropRate(21.17f).build())
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1, 1).dropRate(127f).build())
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1, 1).dropRate(127f).build())
        .loot(LootItem.Builder.item(Items.DragonMedHelm, 1, 1).dropRate(127f).build())
        .loot(LootItem.Builder.item(Items.WrathRune, 30, 50).dropRate(15.88f).build())
        .loot(LootItem.Builder.item(Items.ChaosRune, 75, 150).dropRate(18.14f).build())
        .loot(LootItem.Builder.item(Items.DeathRune, 50, 100).dropRate(18.14f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(50.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.GrimySnapdragon, 1, 1).dropRate(63.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyTorstol, 1, 1).dropRate(84.67f).build())
        .loot(LootItem.Builder.item(Items.RuniteOre, 2, 5).dropRate(21.17f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(300f).build())
        .loot(LootItem.Builder.item(Items.DragonLimbs, 1, 1).dropRate(800f).build())
        .loot(LootItem.Builder.item(Items.DragonMetalLump, 1, 1).dropRate(5000f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(8000f).build())

        .build()
    ),
    LocustRiders("LocustRiders", IconPaths.MonsterLocustRiders, MonsterStats.Builder
        .hitpoints(90)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.Battlestaff, 3, 3).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(58.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(74.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(102.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(163.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(204.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(321.4f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(472f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(686.5f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(1006.9f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1510.4f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(2157.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(3020.8f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(5034.7f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(7552f).build())

        .build()
    ),
    ScarabMages("Scarab Mages", IconPaths.MonsterScarabMages, MonsterStats.Builder
        .hitpoints(50)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.Battlestaff, 3, 3).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneSqShield, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(58.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(74.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(102.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(163.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(204.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(321.4f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(472f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(686.5f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(1006.9f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1510.4f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(2157.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(3020.8f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(5034.7f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(7552f).build())

        .build()
    ),
    SkeletalWyverns("Skeletal Wyverns", IconPaths.MonsterSkeletalWyverns, MonsterStats.Builder
        .hitpoints(200)
        .xpBoostPercentage(5)

        .loot(LootItem.Builder.item(Items.WyvernBones, 1, 1).dropRate(1.0f).build())
        .loot(LootItem.Builder.item(Items.GraniteLegs, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.EarthBattlestaff, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.Battlestaff, 10, 10).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 3).dropRate(167.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(212.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 3).dropRate(292.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(390.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(468.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(585.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(780.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(780.2f).build())
        .loot(LootItem.Builder.item(Items.AdamantiteBar, 10, 10).dropRate(21.33f).build())
        .loot(LootItem.Builder.item(Items.Coins, 300, 300).dropRate(10.67f).build())
        .loot(LootItem.Builder.item(Items.RuneCrossbow, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 3, 3).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(350f).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10000f).build())

        .build()
    ),
    SmokeDevils("Smoke Devils", IconPaths.MonsterSmokeDevils, MonsterStats.Builder
        .hitpoints(185)

        .loot(LootItem.Builder.item(Items.AirBattlestaff, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.FireBattlestaff, 1, 1).dropRate(42.67f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.OccultNecklace, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.DragonChainbody, 1, 1).dropRate(32768f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 2).dropRate(65f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 2).dropRate(82.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 2).dropRate(113.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 2).dropRate(151.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 2).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 2).dropRate(227.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 2).dropRate(303.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 2).dropRate(303.4f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(750f).build())

        .build()
    ),
    Thermy("Thermonuclear Smoke Devils", IconPaths.MonsterThermonuclearSmokeDevils, MonsterStats.Builder
        .hitpoints(240)

        .loot(LootItem.Builder.item(Items.RuneChainbody, 1, 1).dropRate(26.75f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(35.67f).build())
        .loot(LootItem.Builder.item(Items.MysticAirStaff, 1, 1).dropRate(35.67f).build())
        .loot(LootItem.Builder.item(Items.MysticFireStaff, 1, 1).dropRate(35.67f).build())
        .loot(LootItem.Builder.item(Items.RuneScimitar, 1, 1).dropRate(53.5f).build())
        .loot(LootItem.Builder.item(Items.DragonScimitar, 1, 1).dropRate(107f).build())
        .loot(LootItem.Builder.item(Items.AncientStaff, 1, 1).dropRate(107f).build())
        .loot(LootItem.Builder.item(Items.OccultNecklace, 1, 1).dropRate(350f).build())
        .loot(LootItem.Builder.item(Items.SmokeBattlestaff, 1, 1).dropRate(512f).build())
        .loot(LootItem.Builder.item(Items.DragonChainbody, 1, 1).dropRate(2000f).build())
        
        .loot(LootItem.Builder.item(Items.Coins, 10000, 1).dropRate(7.133f).build())
        .loot(LootItem.Builder.item(Items.GrimyToadflax, 15, 1).dropRate(53.5f).build())
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 12, 1).dropRate(53.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 2, 1).dropRate(53.5f).build())
        .loot(LootItem.Builder.item(Items.Grapes, 100, 1).dropRate(107f).build())
        
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(96f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(500f).build())

        .build()
    ),
    SpiritualRangers("Spiritual Rangers", IconPaths.MonsterSpiritualRangers, MonsterStats.Builder
        .hitpoints(120)

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128).build())

        .build()
    ),
    SpiritualWarriors("Spiritual Warriors", IconPaths.MonsterSpiritualWarriors, MonsterStats.Builder
        .hitpoints(100)

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128).build())

        .build()
    ),
    SpiritualMages("Spiritual Mages", IconPaths.MonsterSpiritualMages, MonsterStats.Builder
        .hitpoints(75)

        .loot(LootItem.Builder.item(Items.DragonBoots, 1, 1).dropRate(128).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128).build())

        .build()
    ),
    SteelDragons("Steel Dragons", IconPaths.MonsterSteelDragons, MonsterStats.Builder
        .hitpoints(210)

        .loot(LootItem.Builder.item(Items.DragonBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.DragonPlateskirt, 1, 1).dropRate(512).build())
        .loot(LootItem.Builder.item(Items.DragonPlatelegs, 1, 1).dropRate(512).build())
        .loot(LootItem.Builder.item(Items.RuneMace, 1, 1).dropRate(32).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(64).build())
        .loot(LootItem.Builder.item(Items.ClueScrollElite, 1, 1).dropRate(500).build())
        .loot(LootItem.Builder.item(Items.DraconicVisage, 1, 1).dropRate(10000).build())

        .build()
    ),
    SteelDragonsCatacombs("Steel Dragons (Catacombs)", IconPaths.LocationKourendCatacombs, SteelDragons.getStats()
        .clone(250)

        .loot(LootItem.Builder.item(Items.AncientShard, 1, 1).dropRate(166).build())
        .loot(LootItem.Builder.item(Items.DarkTotemBase, 1, 1).dropRate(250).build())
        .loot(LootItem.Builder.item(Items.DarkTotemMiddle, 1, 1).dropRate(250).build())
        .loot(LootItem.Builder.item(Items.DarkTotemTop, 1, 1).dropRate(250).build())

        .build()
    ),
    Suqahs("Suqahs", IconPaths.MonsterSuqahs, MonsterStats.Builder
        .hitpoints(105)

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(129).build())

        .build()
    ),
    Trolls("Mountain trolls", IconPaths.MonsterTrolls, MonsterStats.Builder
        .hitpoints(90)

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(78f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(99.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(136.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(182f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(218.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(126.1f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(181f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(261.5f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(392.2f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(588.4f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(784.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1412f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1765.1f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(2353.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(3530.1f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(7060.2f).build())

        .build()
    ),
    IceTrolls("Ice Trolls", IconPaths.MonsterIceTrolls, MonsterStats.Builder
        .hitpoints(80)

        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.GraniteShield, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(128f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(585.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(744.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(1024f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(1365.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(1638.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(2048f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(2730.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(2730.7f).build())

        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(217.8f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(312.7f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(451.7f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(677.5f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(1016.2f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(1355f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(2439f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(3048.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(4065f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(6097.5f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(12194.9f).build())

        .build()
    ),
    Turoths("Turoths", IconPaths.MonsterTuroths, MonsterStats.Builder
        .hitpoints(new int[] { 76, 77, 79, 81 })

        .loot(LootItem.Builder.item(Items.LeafBladedSword, 1, 1).dropRate(500f).build())
        .loot(LootItem.Builder.item(Items.MysticRobeBottomLight, 1, 1).dropRate(512f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 3).dropRate(37.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 3).dropRate(48f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 3).dropRate(66.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 3).dropRate(88.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 3).dropRate(105.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 3).dropRate(132.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 3).dropRate(176.2f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 3).dropRate(176.2f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(35.7f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(52.4f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(76.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(111.9f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(167.8f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(239.7f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(335.6f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(559.4f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(839.1f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build()) // Not sold

        .build()
    ),
    TzHaars("TzHaars", IconPaths.MonsterTzHaars, MonsterStats.Builder
        .hitpoints(new int[] { 80, 100, 120, 140 })
        .build()
    ),
    Jad("TzTok-Jad", IconPaths.MonsterJads, MonsterStats.Builder
        .hitpoints(12_010)
        .slayer(37_010)
        .build()
    ),
    Zuk("TzKal-Zuk", IconPaths.MonsterZuks, MonsterStats.Builder
        .hitpoints(25_000)
        .slayer(125_000)
        .build()
    ),
    FeralVampyres("Feral Vampyres", IconPaths.MonsterFeralVampyres, MonsterStats.Builder
        .hitpoints(40)
        
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(117f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(148.9f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(204.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(273.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(327.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(409.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(546.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(546.1f).build())
        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(126.1f).build())
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(181f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(261.5f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(392.2f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(588.4f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(784.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(1412f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1765.1f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(2353.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(3530.1f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(7060.2f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollMedium, 1, 1).dropRate(128f).build())

        .build()
    ),
    VampyreJuvinate("Vampyre Juvinates", IconPaths.MonsterVampyreJuvinates, MonsterStats.Builder
        .hitpoints(85)
        .build()
    ),
    Vyrewatches("Vyrewatches", IconPaths.MonsterVyrewatches, MonsterStats.Builder
        .hitpoints(90)

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(16f).build()) // Rarity 1/16
        .loot(LootItem.Builder.item(Items.RunePlatelegs, 1, 1).dropRate(48f).build()) // Rarity 1/48
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(96f).build()) // Rarity 1/96
        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(20792f).build()) // Rarity 1/20792
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(30434f).build()) // Rarity 1/30434
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(44680f).build()) // Rarity 1/44680
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(65625f).build()) // Rarity 1/65625
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(95454f).build()) // Rarity 1/95454
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(140000f).build()) // Rarity 1/140000
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(209999f).build()) // Rarity 1/209999
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(300000f).build()) // Rarity 1/300000
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(419999f).build()) // Rarity 1/419999
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(700000f).build()) // Rarity 1/700000
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(1050000f).build()) // Rarity 1/1050000
        .loot(LootItem.Builder.item(Items.RuniteBar, 1, 1).dropRate(48f).build()) // Rarity 1/48
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build()) // Rarity 1/128

        .build()
    ),
    VyrewatchSentinels("Vyrewatch Sentinels", IconPaths.MonsterVyrewatchSentinels, MonsterStats.Builder
        .hitpoints(150)

        .loot(LootItem.Builder.item(Items.BloodShard, 1, 1).dropRate(1500f).build()) // Rarity 1/1500
        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(23.25f).build()) // Rarity 1/23.25
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(93f).build()) // Rarity 1/93
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(93f).build()) // Rarity 1/93
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(850.3f).build()) // Rarity 1/850.3
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(1082.2f).build()) // Rarity 1/1082
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(1488f).build()) // Rarity 1/1488
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(1984f).build()) // Rarity 1/1984
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(2380.8f).build()) // Rarity 1/2381
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(2976f).build()) // Rarity 1/2976
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(3968f).build()) // Rarity 1/3968
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(3968f).build()) // Rarity 1/3968
        .loot(LootItem.Builder.item(Items.HarralanderSeed, 1, 1).dropRate(1740.4f).build()) // Rarity 1/1740
        .loot(LootItem.Builder.item(Items.RanarrSeed, 1, 1).dropRate(2499.1f).build()) // Rarity 1/2499
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(3609.8f).build()) // Rarity 1/3610
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(5414.7f).build()) // Rarity 1/5415
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(8122f).build()) // Rarity 1/8122
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(10829.3f).build()) // Rarity 1/10829
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(19492.8f).build()) // Rarity 1/19493
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(24366f).build()) // Rarity 1/24366
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(32488f).build()) // Rarity 1/32488
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(48732f).build()) // Rarity 1/48732
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(97464f).build()) // Rarity 1/97464
        .loot(LootItem.Builder.item(Items.OnyxBoltTips, 6, 14).dropRate(1736f).build()) // Rarity 1/1736
        .loot(LootItem.Builder.item(Items.RuniteBar, 1, 1).dropRate(46.5f).build()) // Rarity 1/46.5
        .loot(LootItem.Builder.item(Items.RuniteOre, 1, 1).dropRate(46.5f).build()) // Rarity 1/46.5
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(100f).build()) // Rarity 1/100

        .build()
    ),
    WarpedTerrorbird("Warped Terrorbirds", IconPaths.MonsterWarpedTerrorbirds, MonsterStats.Builder
        .hitpoints(150)

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.Coins, 600, 800).dropRate(8f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.WarpedSceptreUncharged, 1, 1).dropRate(320f).build())

        .build()
    ),
    WarpedTortoises("Warped Tortoises", IconPaths.MonsterWarpedTortoises, MonsterStats.Builder
        .hitpoints(200)

        .loot(LootItem.Builder.item(Items.AdamantPlatebody, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.RunePickaxe, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneKiteshield, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.RuneWarhammer, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(1f).build())
        .loot(LootItem.Builder.item(Items.WarpedSceptreUncharged, 1, 1).dropRate(320f).build())
        
        .build()
    ),
    Waterfiends("Waterfiends", IconPaths.MonsterWaterfiends, MonsterStats.Builder
        .hitpoints(128)

        .loot(LootItem.Builder.item(Items.MistBattlestaff, 1, 1).dropRate(3000f).build())
        .loot(LootItem.Builder.item(Items.WaterBattlestaff, 1, 1).dropRate(32f).build())
        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(64f).build())
        .loot(LootItem.Builder.item(Items.MysticWaterStaff, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.RuneFullHelm, 1, 1).dropRate(128f).build())
        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 1).dropRate(130f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 1).dropRate(165.5f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 1).dropRate(227.6f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 1).dropRate(303.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 1).dropRate(364.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 1).dropRate(455.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 1).dropRate(606.8f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 1).dropRate(606.8f).build())
        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(160.7f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(236f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(343.3f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(503.5f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(755.2f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(1078.9f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1510.4f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(2517.3f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(3776f).build())
        .loot(LootItem.Builder.item(Items.Coins, 2000, 3000).dropRate(8.533f).build())
        .loot(LootItem.Builder.item(Items.WaterOrb, 6, 10).dropRate(16f).build())
        .loot(LootItem.Builder.item(Items.CrystalShard, 3, 5).dropRate(24f).build())
        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(128f).build())

        .build()
    ),
    Wyrms("Wyrms", IconPaths.MonsterWyrms, MonsterStats.Builder
        .hitpoints(130)
        .xpBoostPercentage(2.5f)

        .loot(LootItem.Builder.item(Items.WyrmBones, 1, 1).dropRate(1).build()) // Always drop
        .loot(LootItem.Builder.item(Items.DragonSword, 1, 1).dropRate(10000).build())
        .loot(LootItem.Builder.item(Items.DragonHarpoon, 1, 1).dropRate(10000).build())

        .loot(LootItem.Builder.item(Items.RuneMedHelm, 1, 1).dropRate(38f).build())
        .loot(LootItem.Builder.item(Items.EarthBattlestaff, 1, 1).dropRate(76f).build())
        .loot(LootItem.Builder.item(Items.RuneBattleaxe, 1, 1).dropRate(76f).build())
        .loot(LootItem.Builder.item(Items.DragonDagger, 1, 1).dropRate(76f).build())

        .loot(LootItem.Builder.item(Items.GrimyHarralander, 1, 2).dropRate(173.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyRanarrWeed, 1, 2).dropRate(221.1f).build())
        .loot(LootItem.Builder.item(Items.GrimyIritLeaf, 1, 2).dropRate(304f).build())
        .loot(LootItem.Builder.item(Items.GrimyAvantoe, 1, 2).dropRate(405.3f).build())
        .loot(LootItem.Builder.item(Items.GrimyKwuarm, 1, 2).dropRate(486.4f).build())
        .loot(LootItem.Builder.item(Items.GrimyCadantine, 1, 2).dropRate(608f).build())
        .loot(LootItem.Builder.item(Items.GrimyLantadyme, 1, 2).dropRate(810.7f).build())
        .loot(LootItem.Builder.item(Items.GrimyDwarfWeed, 1, 2).dropRate(810.7f).build())

        .loot(LootItem.Builder.item(Items.ToadflaxSeed, 1, 1).dropRate(127.2f).build())
        .loot(LootItem.Builder.item(Items.IritSeed, 1, 1).dropRate(186.8f).build())
        .loot(LootItem.Builder.item(Items.AvantoeSeed, 1, 1).dropRate(271.8f).build())
        .loot(LootItem.Builder.item(Items.KwuarmSeed, 1, 1).dropRate(398.6f).build())
        .loot(LootItem.Builder.item(Items.SnapdragonSeed, 1, 1).dropRate(597.9f).build())
        .loot(LootItem.Builder.item(Items.CadantineSeed, 1, 1).dropRate(854.1f).build())
        .loot(LootItem.Builder.item(Items.LantadymeSeed, 1, 1).dropRate(1195.7f).build())
        .loot(LootItem.Builder.item(Items.DwarfWeedSeed, 1, 1).dropRate(1992.9f).build())
        .loot(LootItem.Builder.item(Items.TorstolSeed, 1, 1).dropRate(2989.3f).build())

        .loot(LootItem.Builder.item(Items.ClueScrollHard, 1, 1).dropRate(256f).build()) // Not sold

        .build()
    ),
    Wyrmlings("Wyrmlings", IconPaths.MonsterWyrmlings, MonsterStats.Builder
        .hitpoints(65)
        
        .loot(LootItem.Builder.item(Items.WyrmlingBones, 1, 1).dropRate(1).build()) // Always drop
        
        .build()
    ),
    ;

    public String getDescription() {
        return description;
    }

    private String description;

    public MonsterStats getStats() {
        return stats;
    }

    private MonsterStats stats;

    public IconPaths getIconPath() {
        return iconPath;
    }

    private IconPaths iconPath;

    Monsters(String description, IconPaths iconPath, MonsterStats stats) {
        this.description = description;
        this.iconPath = iconPath;
        this.stats = stats;
    }

    @Override
    public String toString() {
        return description; // Override toString to return the string value
    }
}
