package io.hurx;

import javax.swing.*;

import io.hurx.cache.Cache;
import io.hurx.cache.exceptions.CacheCorruptedException;
import io.hurx.cache.exceptions.PlayerNotFoundException;
import io.hurx.components.Router;
import io.hurx.models.ViewNames;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.PluginPanel;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import io.hurx.SkillingPlannerPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Color;

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
        setLayout(new DynamicGridLayout(0, 1, 0, 0));
        setOpaque(true);
        setBackground(Theme.BG_COLOR);
        setDoubleBuffered(true);
        this.client = client;
        this.clientThread = clientThread;
        cache = new Cache(this);
        router = new Router(this);
        try {
            cache.load();
            router.navigate(cache.getData().getView());
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
    public JComboBox<?> createPagesComboBox() {
        SkillingPlannerPanel panel = this;
        List<ViewNames> comboBoxValues = new ArrayList<>();
        ViewNames[] views = new ViewNames[] {
            ViewNames.Overview,
            ViewNames.Slayer
        };
        for (ViewNames view : views) {
            comboBoxValues.add(view);
        }
        JComboBox<ViewNames> cb = new JComboBox<>(comboBoxValues.toArray(new ViewNames[comboBoxValues.size()]));
        cb.setSelectedItem(router.getView().getViewName());
        cb.setRenderer(new ListCellRenderer<ViewNames>() {
            @Override
            public Component getListCellRendererComponent(
                JList<? extends ViewNames> list,
                ViewNames value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

                JLabel label = new JLabel();
                
                label.setOpaque(true);
                label.setText(value.getName());
                label.setIcon(Resources.loadImageIcon(value.getIconPath().getPath(), 18, 18));

                if (value == getCache().getData().getView()) {
                    label.setBackground(Theme.TABLE_BG_COLOR_SELECTED);
                    label.setForeground(Color.white);
                }
                else if (isSelected) {
                    label.setBackground(Theme.TABLE_BG_COLOR_HOVER);
                    label.setForeground(Color.white);
                }
                else {
                    label.setBackground(Theme.TABLE_BG_COLOR);
                    label.setForeground(Color.white);
                }

                return label;
            }
        });
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewNames selectedOption = (ViewNames) cb.getSelectedItem();
                
                if (selectedOption == cache.getData().getView()) {
                    return;
                }
                if (selectedOption == ViewNames.Overview) {
                    router.navigate(ViewNames.Overview);
                }
                else if (selectedOption == ViewNames.Slayer) {
                    router.navigate(ViewNames.Slayer);
                }
                
                panel.repaint();
                panel.revalidate();
            }
        });
        return cb;
    }
}
