package io.hurx.calculators.slayer;

import io.hurx.SkillingPlannerPanel;
import io.hurx.cache.data.SlayerPlanningData;
import io.hurx.models.Skills;
import io.hurx.models.items.Items;
import io.hurx.models.slayer.monsters.Monsters;
import io.hurx.models.slayer.monsters.SlayerMonsters;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Calculator tools for slayer
 */
public class SlayerCalculator {
    public static void calculate() {}

    public static abstract class SlayerListData {
        protected Map<SlayerMonsters, Map<Monsters, Float>> assignmentsPerHour;

        protected Map<SlayerMonsters, Map<Monsters, Map<Skills, Float>>> gainsPerHour;

        protected Map<SlayerMonsters, Map<Items, Float>> itemsPerHour;

        protected Map<SlayerMonsters, Map<Items, Float>> suppliesPerHour;

        public SlayerPlanningData getPlanning() {
            return planning;
        }
        protected SlayerPlanningData planning;

        public SlayerListData() {
            this.calculate();
        }

        public abstract void calculate();

        public abstract Map<SlayerMonsters, Map<Items, Float>> getAlchItems();
        public abstract Map<Items, Float> getAlchItems(SlayerMonsters monsters);
        public abstract Map<Items, Float> getAlchItems(Monsters monsters);

        public abstract Map<SlayerMonsters, Map<Items, Float>> getDropTradeItems();
        public abstract Map<Items, Float> getDropTradeItems(SlayerMonsters monster);
        public abstract Map<Items, Float> getDropTradeItems(Monsters monster);

        public abstract Map<Monsters, Map<Items, Float>> getSupplyCost();
        public abstract Map<Items, Float> getSupplyCost(SlayerMonsters monster);
        public abstract Map<Items, Float> getSupplyCost(Monsters monster);

        public abstract Map<Monsters, Map<Items, Float>> getSupplyGains();
        public abstract Map<Items, Float> getSupplyGains(SlayerMonsters monster);
        public abstract Map<Items, Float> getSupplyGains(Monsters monster);
    }
}
