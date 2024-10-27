package io.hurx.calculators;

import io.hurx.SkillingPlannerPanel;

/**
 * Calculator tools for slayer
 */
public class SlayerCalculator extends Calculator<SlayerCalculator.Model> {
    private SkillingPlannerPanel panel;

    public SlayerCalculator(SkillingPlannerPanel panel) {
        this.panel = panel;
        this.calculate();
    }

    /**
     * Makes the calculations
     */
    public Model calculate() {
        model = new Model();



        return model;
    }

    public static class Model {
        
    }
}
