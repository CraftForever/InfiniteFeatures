package com.github.craftforever.infinitefeatures;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class Templates
{
	// For now, we need to register a bunch of generic objects on startup, and then modify them later.
	private static int itemLimit = 200;
	public static int getItemLimit() {	return itemLimit;	}
	public static void setItemLimit(int itemLimit) {	Templates.itemLimit = itemLimit;	}

	private static int blockLimit = 200;
	public static int getBlockLimit() {	return blockLimit;	}
	public static void setBlockLimit(int blockLimit) {	Templates.blockLimit = blockLimit;	}
	
	Templates(int limit)
	{
		itemLimit = limit;
	}
	
	public static Item[] items()
	{
		Item[] all = new Item[] {};
		for (int i = 0; i < itemLimit; i++)
		{
			Item template = new Item().setRegistryName("inf_item_template_" + String.valueOf(i)).setCreativeTab(InfiniteFeatures.infItemsTab);
			all = ArrayUtils.add(all, template);
		}
		return all;
	}
	
	public static Block[] blocks()
	{
		Block[] all = new Block[] {};
		for (int i = 0; i < blockLimit; i++)
		{
			Block template = new Block(Material.AIR).setRegistryName("inf_block_template_" + String.valueOf(i)).setCreativeTab(InfiniteFeatures.infBlocksTab);
			all = ArrayUtils.add(all, template);
		}
		return all;
	}
	
	public static Item[] blockItems()
	{
		ItemBlock[] all = new ItemBlock[] {};
		Item template = new Item();
		for (Block block:blocks())
		{
			template = new ItemBlock(block).setRegistryName(block.getRegistryName()).setCreativeTab(InfiniteFeatures.infBlocksTab);
			ArrayUtils.add(all, template);
		}
		return all;
	}
}