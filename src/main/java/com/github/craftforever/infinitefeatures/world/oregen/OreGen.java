package com.github.craftforever.infinitefeatures.world.oregen;

import java.util.Random;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.blocks.FallingBase;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.util.Mineral;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator 
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) 
	{
		if(world.provider.getDimension()==0) 
		{
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
		else if(world.provider.getDimension()==-1) 
		{
			generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
		else if(world.provider.getDimension()==1) 
		{
			generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) 
	{
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
		{
			Mineral blockmineral = ((FallingBase)ModBlocks.oreArray[i]).mineral;
			if(blockmineral.underlay.equals("stone")||blockmineral.underlay.equals("sand")||blockmineral.underlay.equals("dirt")) 
			{
				generateOre(ModBlocks.oreArray[i].getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, blockmineral.genmaxy, random.nextInt(blockmineral.gensize+1) + blockmineral.gensize+1, blockmineral.genrarity+1);
			}
			//System.out.print(ModBlocks.blockArray[i].getRegistryName()+"\n");
		}
	}
	
	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) 
	{
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
		{
			if(ModBlocks.minerals[i].underlay.equals("netherrack")) 
			{
				Mineral blockmineral = ((FallingBase)ModBlocks.oreArray[i]).mineral;
				generateHellOre(ModBlocks.oreArray[i].getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 255, random.nextInt(blockmineral.gensize+1) + blockmineral.gensize+1, blockmineral.genrarity+1*4);
			}
			//System.out.print(ModBlocks.blockArray[i].getRegistryName()+"\n");
		}
	}
	
	private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,IChunkProvider chunkProvider) 
	{
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
		{

			if(ModBlocks.minerals[i].underlay.equals("endstone")) 
			{
				Mineral blockmineral = ((FallingBase)ModBlocks.oreArray[i]).mineral;
				generateEndOre(ModBlocks.oreArray[i].getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 255, random.nextInt(blockmineral.gensize+1) + blockmineral.gensize+1, blockmineral.genrarity+1);
			}
			//System.out.print(ModBlocks.blockArray[i].getRegistryName()+"\n");
		}
	}
	
	private void generateOre(IBlockState ore,World world,Random random,int x,int z,int minY,int maxY, int size,int chances) 
	{
		int deltaY = maxY-minY;
		
		for(int i = 0;i < chances ;i++) 
		{
			BlockPos pos = new BlockPos(x + random.nextInt(16),minY + random.nextInt(deltaY),z + random.nextInt(16));
			if(!(ore.getMaterial()==Material.ROCK)) 
			{
				pos = new BlockPos(x + random.nextInt(16),random.nextInt(255),z + random.nextInt(16));
			}
			OrePredicate.block = ore;
			WorldGenMinable generator = new WorldGenMinable(ore,size,new OrePredicate());
			generator.generate(world, random, pos);
		}
	}

	private void generateHellOre(IBlockState ore,World world,Random random,int x,int z,int minY,int maxY, int size,int chances) 
	{
		int deltaY = maxY-minY;
		
		for(int i = 0;i < chances ;i++) 
		{
			BlockPos pos = new BlockPos(x + random.nextInt(16),minY + random.nextInt(deltaY),z + random.nextInt(16));
			if(!(ore.getMaterial()==Material.ROCK)) 
			{
				pos = new BlockPos(x + random.nextInt(16),random.nextInt(255),z + random.nextInt(16));
			}
			OrePredicate.block = ore;
			WorldGenMinable generator = new WorldGenMinable(ore,size,new NetherPredicate());
			generator.generate(world, random, pos);
		}
	}
	
	private void generateEndOre(IBlockState ore,World world,Random random,int x,int z,int minY,int maxY, int size,int chances) 
	{
		int deltaY = maxY-minY;
		
		for(int i = 0;i < chances ;i++) 
		{
			BlockPos pos = new BlockPos(x + random.nextInt(16),minY + random.nextInt(deltaY),z + random.nextInt(16));
			if(!(ore.getMaterial()==Material.ROCK)) 
			{
				pos = new BlockPos(x + random.nextInt(16),random.nextInt(255),z + random.nextInt(16));
			}
			OrePredicate.block = ore;
			WorldGenMinable generator = new WorldGenMinable(ore,size,new EndPredicate());
			generator.generate(world, random, pos);
		}
	}
}
