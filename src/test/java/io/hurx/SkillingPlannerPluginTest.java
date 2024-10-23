package io.hurx;

import io.hurx.SkillingPlannerPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SkillingPlannerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SkillingPlannerPlugin.class);
		RuneLite.main(args);
	}
}