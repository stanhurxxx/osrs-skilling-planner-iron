package io.hurx.repository;

import com.fasterxml.jackson.annotation.JacksonInject;
import io.hurx.annotations.OneToOne;
import io.hurx.models.repository.Repository;
import io.hurx.utils.Injects;

public class AccountRepository extends Repository<PluginRepository> {
    /**
     * Gets the profile for the account
     * @return the profile or null when not found.
     */
    @OneToOne
    public ProfileRepository getProfile() {
        if (profileUuid.get() == null) return null;
        for (ProfileRepository repository : getParent().profiles.values()) {
            if (repository == null) continue;
            if (repository.uuid() != null && repository.uuid().equals(profileUuid.get())) {
                return (ProfileRepository) Repository.registered.get(repository.generatePath());
            }
        }
        return null;
    }

    @Override
    public String getDirName() {
        return "accounts";
    }

    /**
     * The selected profile's plugin uuid
     */
    public Repository.Property<String> profileUuid = new Repository.Property<String>(null);

    public AccountRepository(@JacksonInject PluginRepository pluginRepository, String accountHash) {
        super(pluginRepository, (accountHash.equals("-1") ? "development" : accountHash));

        Injects.setInjectable(AccountRepository.class, this);
    }
}
