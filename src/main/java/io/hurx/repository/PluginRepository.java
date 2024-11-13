package io.hurx.repository;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hurx.annotations.ManyToOne;
import io.hurx.annotations.OneToOne;
import io.hurx.models.repository.Repository;
import io.hurx.plugin.Plugin;
import io.hurx.plugin.PluginViews;
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

    @JsonIgnore
    /** Gets the account */
    public AccountRepository getAccount() {
        return (AccountRepository) Repository.registered.get(account.generatePath());
    }

    /**
     * The selected account
     */
    @OneToOne
    public AccountRepository account;

    public PluginRepository(@JacksonInject Plugin plugin, String accountHash) {
        super(plugin, "plugin");

        AccountRepository accountRepository = new AccountRepository(this, accountHash);
        account = (AccountRepository) Repository.registered.get(accountRepository.generatePath());

        // Register the repository in the jackson object mapper injectables
        Injects.setInjectable(PluginRepository.class, this);
    }
}
