package net.littleaura.differentworlds;

import net.fabricmc.api.ModInitializer;

import net.littleaura.differentworlds.block.ModBlocks;
import net.littleaura.differentworlds.block.entity.ModBlockEntities;
import net.littleaura.differentworlds.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DifferentWorlds implements ModInitializer {
	public static final String MOD_ID = "differentworlds";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModBlocks.initialize();
		ModBlockEntities.initialize();
	}
}