package io.hurx.calculators;

/**
 * A skilling calculator
 */
public abstract class Calculator<Model extends Object> {
    public Model getModel() {
        return model;
    }

    protected Model model;
    
    public abstract Model calculate();
}
