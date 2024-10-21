package io.hurx.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.hurx.SkillingPlannerPanel;
import io.hurx.SkillingPlannerPlugin;
import io.hurx.cache.data.CacheData;
import io.hurx.cache.exceptions.CacheCorruptedException;
import io.hurx.cache.exceptions.PlayerNotFoundException;
import net.runelite.api.Player;
import net.runelite.client.RuneLite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cache {
    /**
     * The directory of the cache
     */
    public final static File CACHE_DIR = new File(SkillingPlannerPlugin.HOME_DIR, "players");

    /**
     * The main panel
     */
    public final SkillingPlannerPanel panel;

    /**
     * Gets the cache data
     * @return
     */
    public CacheData getData() {
        return data;
    }

    /**
     * The data stored in cache
     */
    private CacheData data = new CacheData();

    /**
     * Get the file name of the cache for the current player.
     * @return null if offline
     */
    public String getFileName() {
        long accountHash = panel.client.getAccountHash();
        return accountHash == -1 ? null : Long.toString(accountHash);
    }

    /**
     * Constructs the cache module.
     * @param panel the main panel
     */
    public Cache(SkillingPlannerPanel panel) {
        this.panel = panel;
    }

    /**
     * Saves the cache
     * @throws PlayerNotFoundException When the player is not found
     */
    public void save() throws PlayerNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = getFileName();
        if (fileName == null) {
            throw new PlayerNotFoundException("Could not save player, player is offline.");
        }

        try {
            String jsonString = objectMapper.writeValueAsString(data);
            CACHE_DIR.mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CACHE_DIR, fileName + ".json")))) {
                writer.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Cache saved.");
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the cache
     * @throws PlayerNotFoundException When the player is not found
     * @throws CacheCorruptedException When the cache is corrupted
     * @throws IOException When the cache couldn't be opened
     */
    public void load() throws PlayerNotFoundException, CacheCorruptedException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = getFileName();
        if (fileName == null) {
            System.out.println("Could not load player, player is offline.");
            throw new PlayerNotFoundException("Could not load player, player is offline.");
        }

        if (new File(CACHE_DIR, fileName + ".json").exists()) {
            String input = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(CACHE_DIR, fileName + ".json")))) {
                input += reader.readLine() + "\n";
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            try {
                data = objectMapper.readValue(input, CacheData.class);
                System.out.println("Cache loaded.");
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new CacheCorruptedException();
            }
        } else {
            System.out.println("No previous user input found.");
        }
    }
}
