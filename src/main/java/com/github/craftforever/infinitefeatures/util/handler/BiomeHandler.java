package com.github.craftforever.infinitefeatures.util.handler;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.world.BiomeBase;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeHandler 
{
	public static final Biome[] biome = new Biome[InfiniteFeatures.PLANT_QTY];
	
	public static void registerBiomes() 
	{
		for(int i = 0; i < InfiniteFeatures.PLANT_QTY; i++) 
		{
			String name = ModBlocks.plants[i].name;
			biome[i] = new BiomeBase(new Biome.BiomeProperties("Forest").setTemperature(0.7F).setRainfall(0.8F).setWaterColor(RandomHelper.getRandomIntInRange(0x000000, 0xffffff)), ModBlocks.grassblockArray[i], ModBlocks.dirtArray[i], name);
			biome[i].setRegistryName(name);
			ForgeRegistries.BIOMES.register(biome[i]);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biome[i], 10));
			BiomeManager.addSpawnBiome(biome[i]);
		}
	}
	
}
