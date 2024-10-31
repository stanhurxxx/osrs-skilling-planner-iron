package io.hurx;

import io.hurx.plugin.Plugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SkillingPlannerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(Plugin.class);
		RuneLite.main(args);
	}
}