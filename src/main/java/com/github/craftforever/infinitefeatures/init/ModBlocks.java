package com.github.craftforever.infinitefeatures.init;

import java.util.ArrayList;
import java.util.List;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.blocks.BlockBase;
import com.github.craftforever.infinitefeatures.util.Mineral;
import com.github.craftforever.infinitefeatures.util.Wood;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ModBlocks
{
	
	public static String[] one_consonants = {
			"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "qu", "r", "s", "t", "v", "w", "x", "y", "z"
	};

	public static String[] two_consonants = {
			"bh", "bl", "br", "bw", "ch", "cl", "cr", "cw", "dh", "dr", "dw", "fl", "fr", "fw", "gh", "gl", "gr", "gw",
			"kl", "kr", "kw", "ph", "pl", "pr", "pw", "rh", "sc", "sh", "sk", "sl", "sm", "sn", "sp", "squ", "sr", "st",
			"sv", "sw", "th", "tr", "tw", "v", "vl", "vr", "vw", "zh"
	};

	public static String[] vowels = {
			"a", "e", "i", "o", "u", "ae", "ai", "ao", "au", "ea", "ee", "ei", "eo", "eu", "ia", "ie", "io", "iu", "oa",
			"oe", "oi", "oo", "ou", "ua", "ue", "ui", "uo"
	};

	public static void initarray()
	{

	}

	public static Mineral[] minerals = generatemineralarray();
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static int gemorecount;
	public static int ingotorecount;
	
	public static final Block[] oreArray = generateorearray();
	
	public static final Block[] ingotblockArray = generateingotblockarray();
	//public static final Block RANDOM_BLOCK = new RandomBlock(minerals[0]).setCreativeTab(InfiniteFeatures.InfiniTab);
	//public static final Block RANDOM_BLOCK2 = new RandomBlock(minerals[1]).setCreativeTab(InfiniteFeatures.InfiniTab);
	//public static final Block RANDOM_BLOCK3 = new RandomBlock(minerals[2]).setCreativeTab(InfiniteFeatures.InfiniTab);

	
	public static Wood[] woods = generatewoodarray();
	
	public static final Block[] logArray = generatelogarray();
	
    public static Mineral getRandomMineral(int i) 
    {
    	return RandomFactory.randomMineralFactory(one_consonants, two_consonants, vowels, i);
	}
	
	private static Mineral[] generatemineralarray()
	{
		if(InfiniteFeatures.continueRandomGeneration) {
			Mineral[] mineralarray = new Mineral[InfiniteFeatures.ORE_QTY];
			for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
			{
				mineralarray[i] = getRandomMineral(i);
				if(mineralarray[i].isGem) 
				{
					gemorecount++;
				}
				else 
				{
					ingotorecount++;
				}
			}
			return mineralarray;
		}
		
		else 
		{
			Mineral[] mineralarray = null;
			return mineralarray;
		}	
	}
	
	public static Block[] generateingotblockarray()
	{
		if(InfiniteFeatures.continueRandomGeneration) 
		{
			Block[] ingotblockarray = new Block[InfiniteFeatures.ORE_QTY];
			for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
			{
					ingotblockarray[i] = new BlockBase(minerals[i].name+"_block", Material.IRON,minerals[i]).setCreativeTab(InfiniteFeatures.InfiniTab).setHardness(5).setResistance(30);
			}
			return ingotblockarray;
		}
		else 
		{
			Block[] ingotblockarray = null;
			return ingotblockarray;
		}
	}
	
	public static Block[] generateorearray()
	{
		if(InfiniteFeatures.continueRandomGeneration) 
		{
			Block[] blockarray = new Block[InfiniteFeatures.ORE_QTY];
			for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
			{
				Item drop = ModItems.itemArray[i];
				blockarray[i] = RandomFactory.randomOreFactory(minerals[i], minerals[i].material, drop).setCreativeTab(InfiniteFeatures.InfiniTab);
			}
			return blockarray;
		}
		else 
		{
			Block[] blockarray = null;
			return blockarray;
		}
		
	}
	
	
	public static Wood getRandomWood() 
	{
		return RandomFactory.randomWoodFactory(one_consonants, two_consonants, vowels);
	}
	
	public static Wood[] generatewoodarray() 
	{
		if(InfiniteFeatures.continueRandomGeneration) {
			Wood[] woodarray = new Wood[InfiniteFeatures.WOOD_QTY];
			for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
			{
				woodarray[i] = getRandomWood();
			}
			return woodarray;
		}
		
		else 
		{
			Wood[] woodarray = null;
			return woodarray;
		}	
	}
	
	public static Block[] generatelogarray() 
	{
		if(InfiniteFeatures.continueRandomGeneration) 
		{	
			Block[] logarray = new Block[InfiniteFeatures.WOOD_QTY];
			for(int i = 0; i < InfiniteFeatures.WOOD_QTY; i++) 
			{
				logarray[i] = RandomFactory.randomLogFactory(woods[i]);
			}
			return logarray;
		}
		else 
		{
			Block[] logarray = null;
			return logarray;
		}
	}		
}
	
