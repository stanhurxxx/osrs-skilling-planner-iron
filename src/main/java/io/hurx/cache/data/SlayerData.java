package io.hurx.cache.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.views.slayer.SlayerViewNames;

import java.util.ArrayList;
import java.util.UUID;

public class SlayerData {
    @JsonIgnore()
    public SlayerListData getList() {
        if (listUuid == null) return null;
        for (SlayerListData list : lists) {
            if (list.getUuid().equals(listUuid)) {
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
    public List<SlayerListData> getLists() {
        return lists;
    }
    public void setLists(List<SlayerListData> lists) {
        this.lists = lists;
    }
    private List<SlayerListData> lists;

    /**
     * All plannings
     */
    public List<SlayerPlanningData> getPlannings() {
        return plannings;
    }
    public void setPlannings(List<SlayerPlanningData> plannings) {
        this.plannings = plannings;
    }
    public List<SlayerPlanningData> plannings;

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
    
    public SlayerData() {
        SlayerListData pre99 = new SlayerListData();
        pre99.setName("Pre 99 list");
        pre99.setMaster(SlayerMasters.Nieve);
        SlayerListData post99 = new SlayerListData();
        post99.setName("Post 99 list");
        this.lists = new ArrayList<>();
        this.lists.add(pre99);
        this.lists.add(post99);
        this.view = null;
        this.listUuid = pre99.getUuid();
        this.plannings = new ArrayList<>();
        SlayerPlanningData planningPre99 = new SlayerPlanningData();
        planningPre99.setStartXP(0);
        planningPre99.setEndXP(13_034_431);
        planningPre99.setListUuid(pre99.getUuid());
        plannings.add(planningPre99);
        SlayerPlanningData planningPost99 = new SlayerPlanningData();
        planningPost99.setStartXP(13_034_431);
        planningPost99.setEndXP(200_000_000);
        planningPost99.setListUuid(post99.getUuid());
        plannings.add(planningPost99);
        plannings.sort((m1, m2) -> Integer.compare(m1.getStartXP(), m2.getStartXP()));
    }

    public SlayerListData findListByUuid(String uuid) {
        for (SlayerListData list : lists) {
            if (list.getUuid().equals(uuid)) {
                return list;
            }
        }
        return null;
    }

    public SlayerPlanningData findPlanningByUuid(String uuid) {
        for (SlayerPlanningData planning : plannings) {
            if (planning.getUuid().equals(uuid)) {
                return planning;
            }
        }
        return null;
    }
}
