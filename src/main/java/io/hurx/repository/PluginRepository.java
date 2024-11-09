package io.hurx.repository;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hurx.annotations.ManyToOne;
import io.hurx.annotations.SerializationIgnore;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;
import io.hurx.plugin.PluginViews;
import io.hurx.repository.slayer.SlayerRepository;
import io.hurx.utils.Injects;

public class PluginRepository extends Repository<PluginRepository> {
    @Override
    public String generateUuid() {
        return "plugin";
    }

    /**
     * The selected profile's 
     */
    @ManyToOne(type = ProfileRepository.class)
    public Repository.Property.List<ProfileRepository> profiles = new Repository.Property.List<ProfileRepository>();

    /**
     * The selected account
     */
    @SerializationIgnore
    public AccountRepository account;

    /**
     * The view (always Profile)
     */
    public Repository.Property<PluginViews> view = new Repository.Property<PluginViews>(PluginViews.Overview);

    public PluginRepository(@JacksonInject Plugin plugin, String accountHash) {
        super(plugin, "plugin");

        account = new AccountRepository(this, accountHash);

        // Register the repository in the jackson object mapper injectables
        Injects.setInjectable(PluginRepository.class, this);
    }

    @Override
    public PluginRepository initialize() {
        try {
            PluginRepository repository = (PluginRepository)this.load();
            if (repository == this) {
                throw new Exception();
            }
            repository.account = repository.account.initialize();
            return repository;
        }
        catch (Exception ex) {
            save();
            return this;
        }
    }

    @Override
    public void save() {
        account.save();
        super.save();
    }
}
