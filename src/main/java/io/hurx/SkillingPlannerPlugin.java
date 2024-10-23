package io.hurx;

import io.hurx.cache.exceptions.CacheCorruptedException;
import io.hurx.cache.exceptions.PlayerNotFoundException;
import io.hurx.models.ViewNames;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Slf4j
@PluginDescriptor(
	name = "Skilling Planner Plugin",
	description = "Plans your skilling for max or max xp on ironman."
)
public class SkillingPlannerPlugin extends Plugin
{
	/**
     * The home dir of the plugin
     */
    public final static File HOME_DIR = new File(RuneLite.RUNELITE_DIR, "hurx.io/skilling-planner-iron");

	/**
	 * The client instance
	 */
	@Inject
	private Client client;

	/**
	 * Runelite client toolbar instance
	 */
	@Inject
	private ClientToolbar clientToolbar;

	/**
	 * The info panel of the plugin
	 */
	private SkillingPlannerPanel panel;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Skilling planenr plugin started.");
		this.panel = new SkillingPlannerPanel(client, null);
		
		NavigationButton button = NavigationButton.builder()
			.tooltip("Skilling planner")
			.icon(ImageIO.read(getClass().getResourceAsStream("/icons/panel-icon.png")))
			.panel(panel)
			.build();
			
		clientToolbar.addNavigation(button);
		clientToolbar.openPanel(button);

		loadCache();
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Skilling planner plugin stopped.");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) throws Exception
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			loadCache();
    	}
		else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN) {
			loadCache();
		}
		else if (gameStateChanged.getGameState() == GameState.CONNECTION_LOST) {
			loadCache();
		}
	}

	/**
	 * Loads the cache
	 */
	private void loadCache() throws PlayerNotFoundException {
		try {
			panel.getCache().load();
			panel.getRouter().navigate(panel.getCache().getData().view);
		}
		catch (PlayerNotFoundException e) {
			panel.getRouter().navigate(ViewNames.LoggedOut);
		}
		catch (CacheCorruptedException e) {
			panel.getRouter().navigate(ViewNames.LoggedOut);
		}
		catch (IOException e) {
			panel.getRouter().navigate(ViewNames.LoggedOut);
		}
	}
}
