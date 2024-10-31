package io.hurx.models.slayer.masters;

import io.hurx.models.slayer.SlayerAssignment;
import io.hurx.models.slayer.monsters.SlayerMonsters;

/**
 * Represents Duradel, a specific slayer master in the game.
 * <p>
 * Duradel provides a set of slayer assignments to players, 
 * each associated with specific monsters and their respective 
 * parameters such as minimum and maximum task amounts and 
 * experience points. This class extends {@link SlayerMaster} 
 * and overrides the method to calculate average points per task.
 * </p>
 */
public class Duradel extends SlayerMaster {
    
    /**
     * Constructs a new Duradel instance with predefined slayer assignments.
     * The assignments include various monsters and their corresponding 
     * task amounts and experience points.
     */
    public Duradel() {
        super(
            new SlayerAssignment[] {
                SlayerAssignment.builder(SlayerMonsters.AberrantSpectres, 130, 200, 7).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.AbyssalDemon, 130, 200, 12).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.AdamantDragon, 4, 9, 2).extend(20, 30).build(),
                SlayerAssignment.builder(SlayerMonsters.Ankou, 50, 80, 5).extend(91, 150).build(),
                SlayerAssignment.builder(SlayerMonsters.Araxyte, 60, 80, 10).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Aviansie, 120, 200, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Basilisk, 130, 200, 7).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlackDemon, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlackDragon, 10, 20, 9).extend(40, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.Bloodveld, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.BlueDragon, 110, 170, 4).build(), 
                SlayerAssignment.builder(SlayerMonsters.CaveHorror, 130, 200, 4).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.CaveKraken, 100, 120, 9).extend(150, 200).build(),
                SlayerAssignment.builder(SlayerMonsters.Dagannoth, 130, 200, 9).build(), 
                SlayerAssignment.builder(SlayerMonsters.DarkBeast, 10, 20, 11).extend(110, 135).build(),
                SlayerAssignment.builder(SlayerMonsters.Drake, 50, 110, 8).build(), 
                SlayerAssignment.builder(SlayerMonsters.DustDevil, 130, 200, 5).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Elf, 110, 170, 4).build(), 
                SlayerAssignment.builder(SlayerMonsters.FireGiant, 130, 200, 7).build(), 
                SlayerAssignment.builder(SlayerMonsters.FossilIslandWyvern, 20, 50, 7).extend(55, 75).build(),
                SlayerAssignment.builder(SlayerMonsters.Gargoyle, 130, 200, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.GreaterDemon, 130, 200, 9).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Hellhound, 130, 200, 10).build(), 
                SlayerAssignment.builder(SlayerMonsters.IronDragon, 40, 60, 5).extend(60, 100).build(),
                SlayerAssignment.builder(SlayerMonsters.Kalphite, 130, 200, 9).build(), 
                SlayerAssignment.builder(SlayerMonsters.Kurask, 130, 200, 4).build(), 
                SlayerAssignment.builder(SlayerMonsters.Lizardman, 130, 210, 10).build(), 
                SlayerAssignment.builder(SlayerMonsters.MithrilDragon, 5, 10, 9).extend(25, 35).build(),
                SlayerAssignment.builder(SlayerMonsters.MutatedZygomite, 20, 30, 2).build(), 
                SlayerAssignment.builder(SlayerMonsters.Nechryael, 130, 200, 9).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.RedDragon, 30, 65, 8).build(), 
                SlayerAssignment.builder(SlayerMonsters.RuneDragon, 3, 8, 2).extend(30, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.SkeletalWyvern, 20, 40, 7).extend(50, 70).build(),
                SlayerAssignment.builder(SlayerMonsters.SmokeDevil, 130, 200, 9).build(), 
                SlayerAssignment.builder(SlayerMonsters.SpiritualCreatures, 130, 200, 7).extend(181, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.SteelDragon, 10, 20, 7).extend(40, 60).build(),
                SlayerAssignment.builder(SlayerMonsters.Suqah, 60, 90, 8).extend(186, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.Troll, 130, 200, 6).build(), 
                SlayerAssignment.builder(SlayerMonsters.Tzhaar, 130, 199, 10).build(), 
                SlayerAssignment.builder(SlayerMonsters.Vampyre, 100, 210, 8).extend(200, 250).build(),
                SlayerAssignment.builder(SlayerMonsters.WarpedCreature, 130, 200, 8).build(), 
                SlayerAssignment.builder(SlayerMonsters.Waterfiend, 130, 200, 2).build(), 
                SlayerAssignment.builder(SlayerMonsters.Wyrm, 100, 160, 8).build() 
            }
        );
    }

    /**
     * Calculates the average points awarded per task for Duradel.
     * <p>
     * This method iterates through a predefined number of tasks 
     * (1 to 1000) and assigns points based on the task number.
     * The points awarded vary depending on milestones reached 
     * (e.g., every 10th task, every 50th task, etc.).
     * </p>
     *
     * @return The average points awarded per task, calculated 
     *         over 1000 tasks.
     */
    @Override
    public float calculateAveragePointsPerTask() {
        float points = 0;
        for (int i = 1; i <= 1000; i++) {
            if (i % 1000 == 0) {
                points += 750;
            } else if (i % 250 == 0) {
                points += 525;
            } else if (i % 100 == 0) {
                points += 375;
            } else if (i % 50 == 0) {
                points += 225;
            } else if (i % 10 == 0) {
                points += 75;
            } else {
                points += 15;
            }
        }
        return points / 1000;
    }
}
