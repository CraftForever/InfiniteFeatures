package com.github.craftforever.infinitefeatures;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = "infeatures", version = "1.0.0", acceptedMinecraftVersions = "1.12.2")
public class InfiniteFeatures
{
	public static final String modID = "infeatures";
	public static final String namespace = modID+":";
	public static World world;
	public static File saveDir;
	public static Logger logger = LogManager.getLogger();
	
	public static CreativeTabs infItemsTab = new CreativeTabs("infeatures_items_tab")
	{
		@Override
		public ItemStack createIcon() {	return new ItemStack(Templates.items()[0]);	}
	};
	
	public static CreativeTabs infBlocksTab = new CreativeTabs("infeatures_blocks_tab")
	{
		@Override
		public ItemStack createIcon() {	return new ItemStack(Templates.blocks()[0]);	}
	};
	

	public InfiniteFeatures()
	{
		MinecraftForge.EVENT_BUS.register(getClass());
	}
	
	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) throws IOException
	{
		world = event.getWorld();
		saveDir = world.getSaveHandler().getWorldDirectory();
		File generationFile = new File(saveDir, "infConfig");
		
		if(!generationFile.exists())
		{
			// TODO place randomization functions here
			// Save them to generationFile before creating.

			generationFile.createNewFile();
			logger.info("File generated here: " + generationFile.toPath().toString());
		}
		else
		{
			logger.info("File already exists: " + generationFile.toPath().toString());
		}
		
		// TODO load objects from file to the registry.
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent<Block> event)
	{
		ForgeRegistries.BLOCKS.registerAll(Templates.blocks());
		ForgeRegistries.ITEMS.registerAll(Templates.blockItems());
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent<Item> event)
	{
		ForgeRegistries.ITEMS.registerAll(Templates.items());
	}
	
}