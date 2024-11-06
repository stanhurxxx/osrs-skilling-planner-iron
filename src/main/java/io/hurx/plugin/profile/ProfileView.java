package io.hurx.plugin.profile;

import io.hurx.models.views.ViewManagement;

public class ProfileView extends ViewManagement.Entity.View<ProfileMaster, ProfileRepository, ProfileViews> {
    public ProfileView(ProfileMaster master) {
        super(master, ProfileViews.Profile);
    }
}
