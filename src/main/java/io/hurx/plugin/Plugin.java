package io.hurx.plugin;

import io.hurx.models.items.Item;
import io.hurx.models.items.Items;

import javax.inject.Inject;

import io.hurx.models.repository.Repository;
import io.hurx.models.views.ViewManagement;
import io.hurx.repository.AccountRepository;
import io.hurx.repository.PluginRepository;
import io.hurx.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.AsyncBufferedImage;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;

import io.hurx.utils.Injects;

/**
 * The main plugin class for the Ironman Skilling Planner.
 * This plugin manages the skilling planning features for Ironman accounts in
 * the game.
 */
@Slf4j
@PluginDescriptor(name = "Ironman Skilling Planner", description = "Plans your skilling for max or max xp on ironman.")
public class Plugin extends net.runelite.client.plugins.Plugin {
    /**
     * The home directory of the plugin.
     */
    public final static File HOME_DIR = new File(RuneLite.RUNELITE_DIR, "Ironman Skilling Planner");

    /** GET The current account hash of the logged in user */
    public static String accountHash() {
        return accountHash;
    }
    /** The current account hash of the logged in user */
    private static String accountHash;

    /**
     * The client instance.
     * 
     * @return the current Client instance.
     */
    public final Client getClient() {
        return client;
    }

    @Inject
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ItemManager itemManager;

    /**
     * Gets the master of the root views of the plugin.
     * 
     * @return the PluginMaster instance associated with this plugin.
     */
    public PluginMaster getMaster() {
        return master;
    }

    private PluginMaster master;

    /**
     * The info panel of the plugin.
     * 
     * @return the PluginPanel instance associated with this plugin.
     */
    public PluginPanel getPanel() {
        return panel;
    }

    private PluginPanel panel;

    /**
     * Initializes the plugin and loads the necessary resources on startup.
     *
     * @throws Exception if an error occurs during startup.
     */
    @Override
    protected void startUp() throws Exception {
        // Load stuff on the client thread
        clientThread.invoke(() -> {
            updateItems();
            List<Items> itemsLoading = new ArrayList<>();
            for (Items item : Items.values()) {
                itemsLoading.add(item);
            }
            for (Items item : Items.values()) {
                AsyncBufferedImage image = itemManager.getImage(item.getItem().getId());
                image.onLoaded(() -> {
                    BufferedImage bufferedImage = (BufferedImage) image;
                    item.getItem().setIcon(new ImageIcon(bufferedImage));

                    itemsLoading.remove(item);
                    if (itemsLoading.isEmpty()) {
                        // Get the account hash
                        accountHash = Long.toString(client.getAccountHash());

                        // Draw the UI
                        try {
                            // Set the panel
                            panel = new PluginPanel(this);

                            // Initialize the UI
                            this.initialize();

                            // Post initialization, add panel to toolbar
                            NavigationButton button = NavigationButton.builder()
                                .tooltip("Skilling planner")
                                .icon(ImageIO.read(getClass().getResourceAsStream("/icons/panel-icon.png")))
                                .panel(panel)
                                .build();

                            clientToolbar.addNavigation(button);
                            SwingUtilities.invokeLater(() -> {
                                clientToolbar.openPanel(button);
                            });
                        } catch (Exception ex) {
                            System.out.println("Loading iron skiller plugin failed.");
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * Initializes the panel and renders it.
     */
    public void initialize() {
        Injects.reset();
        Injects.setInjectable(Plugin.class, this);
        // Initialize the master
        master = new PluginMaster(panel, new PluginRepository(this, accountHash).initialize());
        // Send onReady event to master
        for (Runnable runnable : master.onReadyRunnables()) {
            runnable.run();
        }
        panel.render();
    }

    /**
     * Performs cleanup and finalization tasks when the plugin is shut down.
     *
     * @throws Exception if an error occurs during shutdown.
     */
    @Override
    protected void shutDown() throws Exception {
        System.out.println("Skilling planner plugin stopped.");
    }

    /**
     * Handles the game state change events and updates the plugin state
     * accordingly.
     *
     * @param gameStateChanged the event containing the new game state.
     * @throws Exception if an error occurs during the state change handling.
     */
    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged) throws Exception {
        clientThread.invoke(() -> {
            if (this.panel == null) {
                return;
            }
            String accountHash = Long.toString(client.getAccountHash());
            if (!accountHash.equals(accountHash)) {
                accountHash = accountHash;
                initialize();
            }
            if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
                updateItems();
            } else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN) {
                updateItems();
            } else if (gameStateChanged.getGameState() == GameState.CONNECTION_LOST) {
                updateItems();
            }
        });
    }

    /**
     * Updates all items by fetching the latest item definitions and prices.
     */
    private void updateItems() {
        for (Items item : Items.values()) {
            Item definition = item.getItem();

            ItemComposition composition = itemManager.getItemComposition(definition.getId());

            // Update item name
            definition.setName(composition.getName());

            // Update noted id
            int notedId = composition.getLinkedNoteId();
            definition.setNotedId(notedId == definition.getId() || notedId < 0 ? null : notedId);

            // Update item price
            if (definition.getPriceItemId() != null) {
                int price = itemManager.getItemPrice(definition.getPriceItemId());
                for (int subtractId : definition.getPriceItemComponentIds()) {
                    price -= itemManager.getItemPrice(subtractId);
                }
                definition.setPrice(price);
            } else {
                definition.setPrice(itemManager.getItemPrice(definition.getId()));
            }
        }
    }
}
