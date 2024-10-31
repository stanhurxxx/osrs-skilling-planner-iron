package io.hurx.plugin.slayer.repository;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.slayer.SlayerViews;

/**
 * The SlayerRepository class manages all data related to Slayer tasks, including lists of Slayer 
 * tasks and planning information.
 * 
 * This repository keeps track of currently selected lists and plannings, and provides methods 
 * for retrieving and managing Slayer-related data.
 */
public class SlayerRepository extends Repository<PluginRepository> {
    
    /** 
     * Retrieves the SlayerListRepository corresponding to the currently selected list UUID. 
     * 
     * @return the selected SlayerListRepository or null if no valid UUID is set.
     */
    @JsonIgnore()
    public SlayerListRepository getList() {
        if (listUuid == null) return null;
        for (SlayerListRepository list : lists) {
            if (list.getFileName().equals(listUuid)) {
                return list;
            }
        }
        return null;
    }

    /**
     * Gets the UUID of the currently selected Slayer list.
     *
     * @return the UUID of the selected list.
     */
    public String getListUuid() {
        return listUuid;
    }

    /**
     * Sets the UUID of the currently selected Slayer list.
     *
     * @param listIndex the UUID of the list to be set as selected.
     */
    public void setListUuid(String listIndex) {
        this.listUuid = listIndex;
    }
    private String listUuid;

    /**
     * Gets the current sub-view in the Slayer view.
     *
     * @return the current Slayer view name.
     */
    public SlayerViews getView() {
        return view;
    }

    /**
     * Sets the current sub-view in the Slayer view.
     *
     * @param view the Slayer view name to set as current.
     */
    public void setView(SlayerViews view) {
        this.view = view;
    }
    private SlayerViews view;

    /**
     * Gets the list of all Slayer lists.
     *
     * @return a list of SlayerListRepository objects.
     */
    public List<SlayerListRepository> getLists() {
        return lists;
    }

    /**
     * Sets the list of all Slayer lists.
     *
     * @param lists a list of SlayerListRepository objects to set.
     */
    public void setLists(List<SlayerListRepository> lists) {
        this.lists = lists;
    }
    private List<SlayerListRepository> lists;

    /**
     * Gets the list of all planning repositories.
     *
     * @return a list of SlayerPlanningRepository objects.
     */
    public List<SlayerPlanningRepository> getPlannings() {
        return plannings;
    }

    /**
     * Sets the list of all planning repositories.
     *
     * @param plannings a list of SlayerPlanningRepository objects to set.
     */
    public void setPlannings(List<SlayerPlanningRepository> plannings) {
        this.plannings = plannings;
    }
    public List<SlayerPlanningRepository> plannings;

    /**
     * Gets the UUID of the currently selected planning.
     *
     * @return the UUID of the selected planning.
     */
    public String getPlanningUuid() {
        return planningUuid;
    }

    /**
     * Sets the UUID of the currently selected planning.
     *
     * @param planningIndex the UUID of the planning to be set as selected.
     */
    public void setPlanningUuid(String planningIndex) {
        this.planningUuid = planningIndex;
    }
    private String planningUuid;
    
    /**
     * Constructs a new SlayerRepository instance.
     *
     * Initializes the repository with default Slayer lists and planning settings.
     *
     * @param repository the base PluginRepository associated with this Slayer repository.
     */
    public SlayerRepository(PluginRepository repository) {
        super(repository, "slayer");
        
        // Initialize pre-99 Slayer list
        SlayerListRepository pre99 = new SlayerListRepository(this);
        pre99.name.set("Pre 99 list");
        pre99.master.set(SlayerMasters.Nieve);
        
        // Initialize post-99 Slayer list
        SlayerListRepository post99 = new SlayerListRepository(this);
        post99.name.set("Post 99 list");
        
        // Add lists to the repository
        this.lists = new ArrayList<>();
        this.lists.add(pre99);
        this.lists.add(post99);
        
        // Set default view and selected list
        this.view = null;
        this.listUuid = pre99.getFileName();
        
        // Initialize planning repositories
        this.plannings = new ArrayList<>();
        
        // Pre-99 planning
        SlayerPlanningRepository planningPre99 = new SlayerPlanningRepository(this);
        planningPre99.startXP.set(0);
        planningPre99.endXP.set(13_034_431);
        planningPre99.listFileName.set(pre99.getFileName());
        plannings.add(planningPre99);
        
        // Post-99 planning
        SlayerPlanningRepository planningPost99 = new SlayerPlanningRepository(this);
        planningPost99.startXP.set(13_034_431);
        planningPost99.endXP.set(200_000_000);
        planningPost99.listFileName.set(post99.getFileName());
        plannings.add(planningPost99);
        
        // Sort plannings by starting XP
        plannings.sort((m1, m2) -> Integer.compare(m1.startXP.get(), m2.endXP.get()));
    }

    /**
     * Finds a SlayerListRepository by its file name.
     *
     * @param fileName the name of the file to search for.
     * @return the SlayerListRepository matching the file name, or null if not found.
     */
    public SlayerListRepository findListByFileName(String fileName) {
        for (SlayerListRepository list : lists) {
            if (list.getFileName().equals(fileName)) {
                return list;
            }
        }
        return null;
    }

    /**
     * Finds a SlayerPlanningRepository by its file name.
     *
     * @param fileName the name of the file to search for.
     * @return the SlayerPlanningRepository matching the file name, or null if not found.
     */
    public SlayerPlanningRepository findPlanningByFileName(String fileName) {
        for (SlayerPlanningRepository planning : plannings) {
            if (planning.getFileName().equals(fileName)) {
                return planning;
            }
        }
        return null;
    }
}
