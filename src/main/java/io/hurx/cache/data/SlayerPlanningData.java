package io.hurx.cache.data;

import java.util.UUID;

public class SlayerPlanningData {
    private String uuid = UUID.randomUUID().toString(); 

    private String listUuid;

    private int startXP;

    private int endXP;

    private long createdAt = System.currentTimeMillis();

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    // Getter for listIndex
    public String getListUuid() {
        return listUuid;
    }

    // Setter for listIndex
    public void setListUuid(String listIndex) {
        this.listUuid = listIndex;
    }

    // Getter for startXP
    public int getStartXP() {
        return startXP;
    }

    // Setter for startXP
    public void setStartXP(int startXP) {
        this.startXP = startXP;
    }

    // Getter for endXP
    public int getEndXP() {
        return endXP;
    }

    // Setter for endXP
    public void setEndXP(int endXP) {
        this.endXP = endXP;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public SlayerPlanningData() {}
}
