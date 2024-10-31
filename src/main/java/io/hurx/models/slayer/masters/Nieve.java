package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;

public class Nieve extends SlayerMaster {
    public Nieve() {
        super(
            new SlayerAssignment[] {
                SlayerAssignment.Builder.monster(SlayerMonsters.AberrantSpectres, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.AbyssalDemon, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.AdamantDragon, 3, 7, 2).extend(20, 30).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Ankou, 50, 90, 5).extend(91, 150).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Araxyte, 40, 60, 8).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Aviansie, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Basilisk, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlackDemon, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlackDragon, 10, 20, 6).extend(40, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Bloodveld, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BlueDragon, 120, 185, 4).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.BrineRat, 120, 185, 3).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.CaveHorror, 120, 180, 5).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.CaveKraken, 100, 120, 6).extend(150, 200).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Dagannoth, 120, 185, 8).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.DarkBeast, 10, 20, 5).extend(110, 135).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Drake, 30, 95, 7).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.DustDevil, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Elf, 60, 90, 4).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.FireGiant, 120, 185, 9).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.FossilIslandWyvern, 5, 25, 5).extend(55, 75).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Gargoyle, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.GreaterDemon, 120, 185, 7).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Hellhound, 120, 185, 8).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.IronDragon, 30, 60, 5).extend(60, 100).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Kalphite, 120, 185, 9).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Kurask, 120, 185, 3).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Lizardman, 90, 120, 8).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.MithrilDragon, 4, 8, 5).extend(25, 35).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.MutatedZygomite, 10, 25, 2).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Nechryael, 110, 170, 7).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.RedDragon, 30, 80, 5).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.RuneDragon, 3, 8, 2).extend(30, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Scarab, 30, 60, 4).extend(130, 170).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SkeletalWyvern, 5, 15, 5).extend(50, 70).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SmokeDevil, 120, 185, 7).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SpiritualCreatures, 120, 185, 6).extend(181, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.SteelDragon, 30, 60, 5).extend(40, 60).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Suqah, 120, 185, 8).extend(186, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Troll, 120, 185, 6).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Turoth, 120, 185, 3).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Tzhaar, 110, 180, 10).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Vampyre, 110, 170, 6).extend(200, 250).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.WarpedCreature, 120, 185, 6).build(),
                SlayerAssignment.Builder.monster(SlayerMonsters.Wyrm, 80, 145, 7).build()
            }
        );
    }

    @Override
    public float calculateAveragePointsPerTask() {
        float points = 0;;
        for (int i = 1; i <= 1000; i ++) {
            if (i % 1000 == 0) {
                points += 600;
            }
            else if (i % 250 == 0) {
                points += 420;
            }
            else if (i % 100 == 0) {
                points += 300;
            }
            else if (i % 50 == 0) {
                points += 180;
            }
            else if (i % 10 == 0) {
                points += 60;
            }
            else {
                points += 12;
            }
        }
        return points / 1000;
    }
}