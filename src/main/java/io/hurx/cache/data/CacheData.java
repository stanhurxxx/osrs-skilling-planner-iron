package io.hurx.cache.data;

import io.hurx.models.ViewNames;
import io.hurx.models.slayer.masters.SlayerMasters;
import io.hurx.models.slayer.monsters.SlayerMonsters;

/**
 * Data in cache
 */
public class CacheData {
    /**
     * The current view
     */
    public ViewNames view = ViewNames.Overview;

    /**
     * The players slayer master choice
     */
    public SlayerMasters slayerMaster = SlayerMasters.Duradel;

    /**
     * Slayer blocks
     */
    public SlayerMonsters[] slayerBlocked = new SlayerMonsters[] {};

    /**
     * Slayer skips
     */
    public SlayerMonsters[] slayerSkipped = new SlayerMonsters[] {};
}
