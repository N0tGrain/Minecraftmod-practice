package com.grain;

import com.grain.commands.Commands;
import com.grain.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Practice implements ModInitializer {
	public static final String MOD_ID = "practice";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private final Commands commandsClass = new Commands();
	private final ModItems modItemsClass = new ModItems();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		commandsClass.registerCommands();
	}
}