package io.hurx.old.components;

public enum SlayerViewComponents {
    Overview("Overview"),
    ManageList("Manage List"),
    ManageOptions("Manage Options"),
    ManageXpRates("Manage XP Rates"),
    Planner("Planner"),
    TargetXP("Target XP");

    public String getName() {
        return name;
    }

    private String name;

    SlayerViewComponents(String name) {
        this.name = name;
    }
}
