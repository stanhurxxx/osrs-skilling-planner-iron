package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;

public class Duradel extends SlayerMaster {
    public Duradel() {
        super(
            new SlayerAssignment[] {
                SlayerAssignment.Builder.monster(SlayerMonsters.AberrantSpectres, 130, 200, 7).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.AbyssalDemon, 130, 200, 12).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.AdamantDragon, 4, 9, 2).extend(20, 30).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Ankou, 50, 80, 5).extend(91, 150).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Araxyte, 60, 80, 10).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Aviansie, 120, 200, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Basilisk, 130, 200, 7).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlackDemon, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlackDragon, 10, 20, 9).extend(40, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Bloodveld, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlueDragon, 110, 170, 4).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.CaveHorror, 130, 200, 4).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.CaveKraken, 100, 120, 9).extend(150, 200).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Dagannoth, 130, 200, 9).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.DarkBeast, 10, 20, 11).extend(110, 135).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Drake, 50, 110, 8).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.DustDevil, 130, 200, 5).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Elf, 110, 170, 4).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.FireGiant, 130, 200, 7).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.FossilIslandWyvern, 20, 50, 7).extend(55, 75).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Gargoyle, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.GreaterDemon, 130, 200, 9).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Hellhound, 130, 200, 10).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.IronDragon, 40, 60, 5).extend(60, 100).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Kalphite, 130, 200, 9).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Kurask, 130, 200, 4).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Lizardman, 130, 210, 10).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.MithrilDragon, 5, 10, 9).extend(25, 35).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.MutatedZygomite, 20, 30, 2).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Nechryael, 130, 200, 9).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.RedDragon, 30, 65, 8).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.RuneDragon, 3, 8, 2).extend(30, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SkeletalWyvern, 20, 40, 7).extend(50, 70).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SmokeDevil, 130, 200, 9).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.SpiritualCreatures, 130, 200, 7).extend(181, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SteelDragon, 10, 20, 7).extend(40, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Suqah, 60, 90, 8).extend(186, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Troll, 130, 200, 6).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Tzhaar, 130, 199, 10).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Vampyre, 100, 210, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.WarpedCreature, 130, 200, 8).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Waterfiend, 130, 200, 2).build(), 
                SlayerAssignment.Builder.monster(SlayerMonsters.Wyrm, 100, 160, 8).build() 
            }
        );
    }
}