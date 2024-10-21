package io.hurx;

import javax.swing.*;

import io.hurx.cache.Cache;
import io.hurx.cache.exceptions.CacheCorruptedException;
import io.hurx.cache.exceptions.PlayerNotFoundException;
import io.hurx.components.Router;
import io.hurx.models.View;
import io.hurx.models.ViewNames;
import net.runelite.client.ui.PluginPanel;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import io.hurx.SkillingPlannerPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.*;

/**
 * The main panel
 */
public class SkillingPlannerPanel extends PluginPanel {  
    /**
     * Runelite client instance
     */
    public final Client client;

    /**
     * Runelite client thread instance
     */
    private final ClientThread clientThread;

    /**
     * Gets the cache
     * @return
     */
    public Cache getCache() {
        return cache;
    }

    /**
     * The cache manager
     */
    private Cache cache;

    /**
     * Get the router component
     */
    public Router getRouter() {
        return router;
    }

    /**
     * The views
     */
    private Router router;

    public SkillingPlannerPanel(Client client, ClientThread clientThread) throws Exception
    {
        this.client = client;
        this.clientThread = clientThread;
        cache = new Cache(this);
        router = new Router(this);

        try {
            cache.load();
            router.navigate(cache.getData().view);
        }
        catch (PlayerNotFoundException e) {
            router.navigate(ViewNames.LoggedOut);
        }
        catch (CacheCorruptedException e) {
            router.navigate(ViewNames.LoggedOut);
        }
        catch (IOException e) {
            router.navigate(ViewNames.LoggedOut);
        }
    }

    /**
     * Creates a select
     * @return
     */
    public JComboBox createSelect() {
        SkillingPlannerPanel panel = this;
        ArrayList<String> labels = new ArrayList();
        for (View view : getRouter().getViews()) {
            labels.add(view.viewName.toString());
        }
        JComboBox cb = new JComboBox<>(new String[] {
            "Overview",
            "Slayer"
        });
        cb.setSelectedItem(router.getView().viewName.toString());
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                String selectedOption = (String) cb.getSelectedItem();

                // Perform an action based on the selected item
                System.out.println("Selected option: " + selectedOption);
                
                // You can add logic here to respond to the selected option
                if (selectedOption.equals("Overview")) {
                    // Perform some action for "Overview"
                    router.navigate(ViewNames.Overview);
                } else if (selectedOption.equals("Slayer")) {
                    router.navigate(ViewNames.Slayer);
                    // Perform some action for "Skills"
                    System.out.println("Skills selected.");
                }
                panel.repaint();
                panel.revalidate();
            }
        });
        return cb;
    }
}
