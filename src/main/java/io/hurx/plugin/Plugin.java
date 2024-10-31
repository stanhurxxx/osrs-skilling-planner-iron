package io.hurx.plugin;

import io.hurx.models.items.Item;
import io.hurx.models.items.Items;

import javax.inject.Inject;
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
import java.io.IOException;
import javax.swing.ImageIcon;
import io.hurx.models.repository.exceptions.PlayerNotLoggedInException;
import io.hurx.models.repository.exceptions.RepositoryFileCorruptedException;

/**
 * The main plugin class for the Ironman Skilling Planner.
 * This plugin manages the skilling planning features for Ironman accounts in the game.
 */
@Slf4j
@PluginDescriptor(
    name = "Ironman Skilling Planner",
    description = "Plans your skilling for max or max xp on ironman."
)
public class Plugin extends net.runelite.client.plugins.Plugin {

    /**
     * The home directory of the plugin.
     */
    public final static File HOME_DIR = new File(RuneLite.RUNELITE_DIR, "Ironman Skilling Planner");

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
    
    private PluginMaster master = new PluginMaster(this, new PluginRepository(this));

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
                    if (itemsLoading.size() == 0) {
                        try {
                            log.info("Skilling planner plugin started.");
                            this.panel = new PluginPanel(this);

                            NavigationButton button = NavigationButton.builder()
                                .tooltip("Skilling planner")
                                .icon(ImageIO.read(getClass().getResourceAsStream("/icons/panel-icon.png")))
                                .panel(panel)
                                .build();

                            clientToolbar.addNavigation(button);
                            clientToolbar.openPanel(button);
                            client.getItemDefinition(5298).getInventoryModel();

                            loadCache();
                        } catch (Exception ex) {
                            log.info("Loading iron skiller plugin failed.");
                            ex.printStackTrace();
                        }
                    }
                });
            }        
        });
    }

    /**
     * Performs cleanup and finalization tasks when the plugin is shut down.
     *
     * @throws Exception if an error occurs during shutdown.
     */
    @Override
    protected void shutDown() throws Exception {
        log.info("Skilling planner plugin stopped.");
    }

    /**
     * Handles the game state change events and updates the plugin state accordingly.
     *
     * @param gameStateChanged the event containing the new game state.
     * @throws Exception if an error occurs during the state change handling.
     */
    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged) throws Exception {
        if (this.panel == null) {
            return;
        }
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
            clientThread.invoke(() -> {
                updateItems();
            });
            loadCache();
        } else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN) {
            clientThread.invoke(() -> {
                updateItems();
            });
            loadCache();
        } else if (gameStateChanged.getGameState() == GameState.CONNECTION_LOST) {
            clientThread.invoke(() -> {
                updateItems();
            });
            loadCache();
        }
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
                notedId == definition.getId() || notedId < 0 ? null : notedId
            );

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

    /**
     * Loads the plugin's cache and initializes the repository.
     *
     * @throws PlayerNotLoggedInException if the player is not logged in.
     */
    private void loadCache() throws PlayerNotLoggedInException {
        try {
            master.getRepository().load();
            master.navigate(master.getRepository().view.get());
        } catch (PlayerNotLoggedInException e) {
            master.navigate(PluginViews.LoggedOut);
        } catch (RepositoryFileCorruptedException e) {
            master.navigate(PluginViews.LoggedOut);
        } catch (IOException e) {
            master.navigate(PluginViews.LoggedOut);
        }
    }
}
