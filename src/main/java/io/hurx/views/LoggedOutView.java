package io.hurx.views;

import io.hurx.SkillingPlannerPanel;
import io.hurx.components.PlainLabel;
import io.hurx.components.TitleLabel;
import io.hurx.models.View;
import io.hurx.models.ViewNames;

/**
 * The overview view
 */
public class LoggedOutView extends View {
    public LoggedOutView(SkillingPlannerPanel panel) {
        super(ViewNames.LoggedOut, panel);        
    }

    @Override
    public void load() {
        panel.add(new TitleLabel("Login"));
        panel.add(new PlainLabel("Login to use this plugin."));
    }
}
