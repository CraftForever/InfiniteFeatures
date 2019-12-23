package com.github.craftforever.infinitefeatures.world;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;

public class BiomeBase extends Biome
{
//	public WorldGenAbstractTree TREE;
	
	public BiomeBase(BiomeProperties properties, Block top, Block fill, String name) 
	{
		super(properties);
		this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
//        this.TREE = new WorldGenRandomTree(name);
        
        topBlock = top.getDefaultState();
        fillerBlock = fill.getDefaultState();
	}
//
//	public WorldGenAbstractTree getRandomTreeFeature(Random rand) 
//	{
//		return TREE;	
//	}
	
}
