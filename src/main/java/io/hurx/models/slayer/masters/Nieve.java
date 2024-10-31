package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;

/**
 * Represents the Slayer Master Nieve, which is responsible for assigning Slayer tasks.
 * This class initializes a set of Slayer assignments with varying requirements and rewards.
 */
public class Nieve extends SlayerMaster {

    /**
     * Constructs a new instance of Nieve and initializes the Slayer assignments.
     * Each assignment consists of a monster, a level range, a weight, and possible extensions.
     * 
     * The assignments are set up with specific parameters for various Slayer monsters.
     */
    public Nieve() {
        super(
            new SlayerAssignment[] {
                SlayerAssignment.builder(SlayerMonsters.AberrantSpectres, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.AbyssalDemon, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.AdamantDragon, 3, 7, 2).extend(20, 30).build(),
                SlayerAssignment.builder(SlayerMonsters.Ankou, 50, 90, 5).extend(91, 150).build(),
                SlayerAssignment.builder(SlayerMonsters.Araxyte, 40, 60, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Aviansie, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Basilisk, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlackDemon, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlackDragon, 10, 20, 6).extend(40, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.Bloodveld, 120, 185, 9).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlueDragon, 120, 185, 4).build(),
                SlayerAssignment.builder(SlayerMonsters.BrineRat, 120, 185, 3).build(),
                SlayerAssignment.builder(SlayerMonsters.CaveHorror, 120, 180, 5).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.CaveKraken, 100, 120, 6).extend(150, 200).build(),
                SlayerAssignment.builder(SlayerMonsters.Dagannoth, 120, 185, 8).build(),
                SlayerAssignment.builder(SlayerMonsters.DarkBeast, 10, 20, 5).extend(110, 135).build(),
                SlayerAssignment.builder(SlayerMonsters.Drake, 30, 95, 7).build(),
                SlayerAssignment.builder(SlayerMonsters.DustDevil, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Elf, 60, 90, 4).build(),
                SlayerAssignment.builder(SlayerMonsters.FireGiant, 120, 185, 9).build(),
                SlayerAssignment.builder(SlayerMonsters.FossilIslandWyvern, 5, 25, 5).extend(55, 75).build(),
                SlayerAssignment.builder(SlayerMonsters.Gargoyle, 120, 185, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.GreaterDemon, 120, 185, 7).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Hellhound, 120, 185, 8).build(),
                SlayerAssignment.builder(SlayerMonsters.IronDragon, 30, 60, 5).extend(60, 100).build(),
                SlayerAssignment.builder(SlayerMonsters.Kalphite, 120, 185, 9).build(),
                SlayerAssignment.builder(SlayerMonsters.Kurask, 120, 185, 3).build(),
                SlayerAssignment.builder(SlayerMonsters.Lizardman, 90, 120, 8).build(),
                SlayerAssignment.builder(SlayerMonsters.MithrilDragon, 4, 8, 5).extend(25, 35).build(),
                SlayerAssignment.builder(SlayerMonsters.MutatedZygomite, 10, 25, 2).build(),
                SlayerAssignment.builder(SlayerMonsters.Nechryael, 110, 170, 7).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.RedDragon, 30, 80, 5).build(),
                SlayerAssignment.builder(SlayerMonsters.RuneDragon, 3, 8, 2).extend(30, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.Scarab, 30, 60, 4).extend(130, 170).build(),
                SlayerAssignment.builder(SlayerMonsters.SkeletalWyvern, 5, 15, 5).extend(50, 70).build(),
                SlayerAssignment.builder(SlayerMonsters.SmokeDevil, 120, 185, 7).build(),
                SlayerAssignment.builder(SlayerMonsters.SpiritualCreatures, 120, 185, 6).extend(181, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.SteelDragon, 30, 60, 5).extend(40, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.Suqah, 120, 185, 8).extend(186, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Troll, 120, 185, 6).build(),
                SlayerAssignment.builder(SlayerMonsters.Turoth, 120, 185, 3).build(),
                SlayerAssignment.builder(SlayerMonsters.Tzhaar, 110, 180, 10).build(),
                SlayerAssignment.builder(SlayerMonsters.Vampyre, 110, 170, 6).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.WarpedCreature, 120, 185, 6).build(),
                SlayerAssignment.builder(SlayerMonsters.Wyrm, 80, 145, 7).build()
            }
        );
    }

    /**
     * Calculates the average points earned per task based on predefined task thresholds.
     * 
     * The calculation is based on the number of tasks completed, with different point values 
     * awarded for completing tasks at different milestones (e.g., every 10, 50, 100, etc.).
     * 
     * @return The average points earned per task as a float value.
     */
    @Override
    public float calculateAveragePointsPerTask() {
        float points = 0;
        for (int i = 1; i <= 1000; i++) {
            if (i % 1000 == 0) {
                points += 600;
            } else if (i % 250 == 0) {
                points += 420;
            } else if (i % 100 == 0) {
                points += 300;
            } else if (i % 50 == 0) {
                points += 180;
            } else if (i % 10 == 0) {
                points += 60;
            } else {
                points += 12;
            }
        }
        return points / 1000;
    }
}
