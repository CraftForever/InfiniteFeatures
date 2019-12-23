package com.github.craftforever.infinitefeatures.util.handler;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.ModBlocks;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler 
{
	public static void RegisterOreDictionary()
	{
		for(int i = 0; i < InfiniteFeatures.PLANT_QTY; i++) 
		{
			OreDictionary.registerOre("plankWood", ModBlocks.plankArray[i]);
		}
	}
}
