package io.hurx.repository.slayer;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hurx.annotations.ManyToOne;
import io.hurx.models.repository.Repository;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.repository.AccountRepository;
import io.hurx.repository.PluginRepository;
import io.hurx.repository.ProfileRepository;
import io.hurx.plugin.slayer.SlayerViews;

import java.util.HashMap;
import java.util.Map;

/**
 * The SlayerRepository class manages all data related to Slayer tasks, including lists of Slayer 
 * tasks and planning information.
 * 
 * This repository keeps track of currently selected lists and plannings, and provides methods 
 * for retrieving and managing Slayer-related data.
 */
public class SlayerRepository extends Repository<ProfileRepository> {    
    @Override
    public String generateUuid() {
        return "slayer";
    }

    @Override
    public String getDirName() {
        return "slayer";
    }

    /**
     * Retrieves the SlayerListRepository corresponding to the currently selected list UUID. 
     * 
     * @return the selected SlayerListRepository or null if no valid UUID is set.
     */
    // TODO: make property
    @JsonIgnore()
    public SlayerListRepository getList() {
        String listUuid = this.listUuid.get();
        if (listUuid == null) return null;
        for (Repository.Property<SlayerListRepository> list : lists.properties()) {
            if (list.get().getUuid().equals(listUuid)) {
                return list.get();
            }
        }
        return null;
    }

    /**
     * Gets the UUID of the currently selected Slayer list.
     *
     * @return the UUID of the selected list.
     */
    public Repository.Property<String> listUuid = new Repository.Property<String>(null);

    /**
     * The slayer sub view
     */
    public Repository.Property<SlayerViews> view = new Repository.Property<SlayerViews>(SlayerViews.Overview);

    /**
     * All the slayer lists
     */
    @ManyToOne(type = SlayerListRepository.class)
    public Repository.Property.List<SlayerListRepository> lists = new Repository.Property.List<SlayerListRepository>();

    /**
     * All the slayer planning records
     */
    @ManyToOne(type = SlayerPlanningRepository.class)
    public Repository.Property.List<SlayerPlanningRepository> plannings = new Repository.Property.List<SlayerPlanningRepository>();

    /**
     * The selected planning's uuid.
     */
    public Repository.Property<String> planningUuid = new Repository.Property<String>(null);
    
    /**
     * Constructs a new SlayerRepository instance.
     *
     * Initializes the repository with default Slayer lists and planning settings.
     *
     * @param repository the base PluginRepository associated with this Slayer repository.
     */
    public SlayerRepository(@JacksonInject ProfileRepository repository) {
        super(repository, "slayer");
    }

    @Override
    public SlayerRepository initialize() {
        try {
            return (SlayerRepository)load();
        }
        catch (Exception ex) {
            System.out.println("Iniitalize slayer..");

            if (!isInitialized) {
                plannings.clear();
                lists.clear();
                listUuid.replace(null);
                planningUuid.replace(null);

                // Initialize pre-99 Slayer list
                SlayerListRepository pre99 = new SlayerListRepository(this);
                pre99.name.replace("Pre 99 list");
                pre99.master.replace(SlayerMasters.Nieve);

                // Initialize post-99 Slayer list
                SlayerListRepository post99 = new SlayerListRepository(this);
                post99.name.replace("Post 99 list");

                // Add lists to the repository
                this.lists.add(pre99);
                this.lists.add(post99);

                // Pre-99 planning
                SlayerPlanningRepository planningPre99 = new SlayerPlanningRepository(this);
                planningPre99.startXP.replace(0);
                planningPre99.endXP.replace(13_034_431);
                planningPre99.listUuid.replace(pre99.getUuid());
                plannings.add(planningPre99);

                // Post-99 planning
                SlayerPlanningRepository planningPost99 = new SlayerPlanningRepository(this);
                planningPost99.startXP.replace(13_034_431);
                planningPost99.endXP.replace(200_000_000);
                planningPost99.listUuid.replace(post99.getUuid());
                plannings.add(planningPost99);
                plannings.properties().sort((m1, m2) -> Integer.compare(m1.get().startXP.get(), m2.get().endXP.get()));

                isInitialized(true);

                try {
                    pre99.save();
                    post99.save();
                    planningPre99.save();
                    planningPost99.save();
                    save();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }

    @Override
    public SlayerRepository copy() {
        Map<String, SlayerListRepository> slayerListsByUuid = new HashMap<>();
        Map<String, SlayerPlanningRepository> slayerPlanningsByUuid = new HashMap<>();
        for (SlayerListRepository list : lists.values()) {
            slayerListsByUuid.put(list.getUuid(), list);
        }
        for (SlayerPlanningRepository planning : plannings.values()) {
            slayerPlanningsByUuid.put(planning.getUuid(), planning);
        }
        super.copy();
        if (listUuid.get() != null) {
            listUuid.replace(slayerPlanningsByUuid.get(listUuid.get()).getUuid());
        }
        if (planningUuid.get() != null) {
            planningUuid.replace(slayerPlanningsByUuid.get(planningUuid.get()).getUuid());
        }
        for (SlayerPlanningRepository planning : plannings.values()) {
            if (planning.listUuid.get() == null) continue;
            SlayerListRepository repository = slayerListsByUuid.get(planning.listUuid.get());
            if (repository == null) continue;
            planning.listUuid.replace(repository.getUuid());
        }
        save();
        return this;
    }

    @Override
    public void save() {
        for (SlayerListRepository list : lists.values()) {
            list.save();
        }
        for (SlayerPlanningRepository planning : plannings.values()) {
            planning.save();
        }
        super.save();
    }

    /**
     * Finds a SlayerListRepository by its file name.
     *
     * @param fileName the name of the file to search for.
     * @return the SlayerListRepository matching the file name, or null if not found.
     */
    // TODO: make property
    public SlayerListRepository findListByFileName(String fileName) {
        for (Repository.Property<SlayerListRepository> list : lists.properties()) {
            if (list.get().getUuid().equals(fileName)) {
                return list.get();
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
    // TODO: make property
    public SlayerPlanningRepository findPlanningByFileName(String fileName) {
        for (Repository.Property<SlayerPlanningRepository> planning : plannings.properties()) {
            if (planning.get().getUuid().equals(fileName)) {
                return planning.get();
            }
        }
        return null;
    }
}