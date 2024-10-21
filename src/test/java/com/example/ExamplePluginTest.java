package com.example;

import io.hurx.SkillingPlannerPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SkillingPlannerPlugin.class);
		RuneLite.main(args);
	}
}