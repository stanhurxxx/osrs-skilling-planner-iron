package io.hurx.old.containers.list.options.components;

import javax.swing.JLabel;

import io.hurx.models.views.ViewComponent;

public class SlayerListOptionsLabel extends ViewComponent<JLabel> {
    public SlayerListOptionsLabel() {
        super(new JLabel());
    }
}

/**
 *  components
 *      views
 *          ViewComboBoxComponent (optional: title)
 *  slayer
 *      SlayerViewContainer
 *          components
 *          containers
 *              list
 *                  tasks
 *                  options
 *                  xpRates
 *              overview
 *              planner
 *          master
 *              SlayerViewMasterContainer
 *              components
 *                  SlayerViewNavigationComponent
 *          views
 *              list
 *                  SlayerListView
 *                  master
 *                      SlayerListMasterContainer
 *                          SlayerListNameTable
 *                          SlayerListSlayerMasterTable
 *                  containers
 *                      tasks
 *                          SlayerListTasksContainer
 *                              views
 *                                  list
 *                                      SlayerListTasksListView
 *                                      SlayerListTasksTable
 *                                  detail
 *                      xpGained
 *                          SlayerListXpGainedContainer
 *                              components
 *                                  SlayerListXpGainedTable
 *                      loot
 *                          SlayerListLootContainer
 *                              components
 *                                  SlayerListAlchLoot
 *                                  SlayerListDropTradeLoot
 *                              containers
 *                                  SlayerListSuppliesContainer
 *                                      components
 *                                          SlayerListSuppliesCost
 *                                          SlayerListSuppliesGained
 *              overview
 *                  SlayerOverviewView
 *                  
 *              planner
 */