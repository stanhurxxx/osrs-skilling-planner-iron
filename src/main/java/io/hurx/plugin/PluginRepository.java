package io.hurx.plugin;

import io.hurx.annotations.ManyToOne;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.profile.ProfileRepository;
import io.hurx.plugin.profile.slayer.repository.SlayerRepository;
import io.hurx.utils.Injects;

public class PluginRepository extends Repository<PluginRepository> {
    @Override
    public String getDirName() {
        return "profiles";
    }

    /**
     * The selected profile's plugin uuid
     */
    public Repository.Property<String> profile = new Repository.Property<String>(null);

    /**
     * The selected profile's 
     */
    @ManyToOne(type = ProfileRepository.class)
    public Repository.Property.List<ProfileRepository> profiles = new Repository.Property.List<ProfileRepository>();
    
    /**
     * The view (always Profile)
     */
    public Repository.Property<PluginViews> view = new Repository.Property<PluginViews>(PluginViews.Profile);

    public PluginRepository(Plugin plugin, String accountHash) {
        super(plugin, (accountHash.equals("-1") ? "development" : accountHash));

        // Register the repository in the jackson object mapper injectables
        Injects.setInjectable(PluginRepository.class, this);
    }

    @Override
    public PluginRepository initialize() {
        try {
            PluginRepository repository = (PluginRepository)this.load();
            for (ProfileRepository plugin : profiles.values()) {
                if (plugin.getFileName() != null && this.profile.get() != null && plugin.getFileName().equals(this.profile.get())) {
                    Injects.setInjectable(ProfileRepository.class, plugin);
                    Injects.setInjectable(SlayerRepository.class, plugin.slayer);
                }
            }
            return repository;
        }
        catch (Exception ex) {
            save();
            return this;
        }
    }
}
