package io.hurx.plugin;

import io.hurx.models.items.Item;
import io.hurx.models.items.Items;

import javax.inject.Inject;

import io.hurx.models.views.ViewManagement;
import io.hurx.repository.PluginRepository;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
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

import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;

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
                        try {
                            initialize();

                            // Post initialization, add panel to toolbar
                            NavigationButton button = NavigationButton.builder()
                                    .tooltip("Skilling planner")
                                    .icon(ImageIO.read(getClass().getResourceAsStream("/icons/panel-icon.png")))
                                    .panel(panel)
                                    .build();
                            clientToolbar.addNavigation(button);
                            clientToolbar.openPanel(button);
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
        try {
            Injects.reset();
            Injects.setInjectable(Plugin.class, this);

            accountHash = Long.toString(client.getAccountHash());

            this.panel = new PluginPanel(this);
            this.master = new PluginMaster(this.panel, new PluginRepository(this, accountHash).initialize());

            // Ready
            this.panel.setReady(true);

            // Send onReady event to master
            for (Runnable runnable : master.onReadyRunnables()) {
                runnable.run();
            }

            // Render
            this.panel.render();
        }
        catch (Exception e) {
            System.out.println("Couldn't initialize PluginPanel: " + e.getMessage());
            e.printStackTrace();
        }
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
            if (!Long.toString(client.getAccountHash()).equals(accountHash)) {
                accountHash = Long.toString(client.getAccountHash());
                this.master = new PluginMaster(this.panel, new PluginRepository(this, accountHash));
                this.panel.render();
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

            // Update item name
            definition.setName(itemManager.getItemComposition(definition.getId()).getName());

            // Update noted id
            int notedId = itemManager.getItemComposition(definition.getId()).getLinkedNoteId();
            definition.setNotedId(
                    notedId == definition.getId() || notedId < 0 ? null : notedId);

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
