package io.hurx.plugin.slayer.repository;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.plugin.PluginRepository;
import io.hurx.plugin.slayer.SlayerViewNames;

public class SlayerRepository extends Repository<PluginRepository> {
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
     * The currently selected list
     */
    public String getListUuid() {
        return listUuid;
    }
    public void setListUuid(String listIndex) {
        this.listUuid = listIndex;
    }
    private String listUuid;

    /**
     * The current sub-view in the slayer view
     */
    public SlayerViewNames getView() {
        return view;
    }
    public void setView(SlayerViewNames view) {
        this.view = view;
    }
    private SlayerViewNames view;

    /**
     * All lists
     */
    public List<SlayerListRepository> getLists() {
        return lists;
    }
    public void setLists(List<SlayerListRepository> lists) {
        this.lists = lists;
    }
    private List<SlayerListRepository> lists;

    /**
     * All plannings
     */
    public List<SlayerPlanningRepository> getPlannings() {
        return plannings;
    }
    public void setPlannings(List<SlayerPlanningRepository> plannings) {
        this.plannings = plannings;
    }
    public List<SlayerPlanningRepository> plannings;

    /**
     * The currently selected planning
     */
    public String getPlanningUuid() {
        return planningUuid;
    }
    public void setPlanningUuid(String planningIndex) {
        this.planningUuid = planningIndex;
    }
    private String planningUuid;
    
    public SlayerRepository(PluginRepository repository) {
        super(repository, "slayer");
        SlayerListRepository pre99 = new SlayerListRepository(this);
        pre99.name.set("Pre 99 list");
        pre99.master.set(SlayerMasters.Nieve);
        SlayerListRepository post99 = new SlayerListRepository(this);
        post99.name.set("Post 99 list");
        this.lists = new ArrayList<>();
        this.lists.add(pre99);
        this.lists.add(post99);
        this.view = null;
        this.listUuid = pre99.getFileName();
        this.plannings = new ArrayList<>();
        SlayerPlanningRepository planningPre99 = new SlayerPlanningRepository(this);
        planningPre99.startXP.set(0);
        planningPre99.endXP.set(13_034_431);
        planningPre99.listFileName.set(pre99.getFileName());
        plannings.add(planningPre99);
        SlayerPlanningRepository planningPost99 = new SlayerPlanningRepository(this);
        planningPost99.startXP.set(13_034_431);
        planningPost99.endXP.set(200_000_000);
        planningPost99.listFileName.set(post99.getFileName());
        plannings.add(planningPost99);
        plannings.sort((m1, m2) -> Integer.compare(m1.startXP.get(), m2.endXP.get()));
    }

    public SlayerListRepository findListByFileName(String fileName) {
        for (SlayerListRepository list : lists) {
            if (list.getFileName().equals(fileName)) {
                return list;
            }
        }
        return null;
    }

    public SlayerPlanningRepository findPlanningByFileName(String fileName) {
        for (SlayerPlanningRepository planning : plannings) {
            if (planning.getFileName().equals(fileName)) {
                return planning;
            }
        }
        return null;
    }
}
