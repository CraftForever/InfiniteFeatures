package com.github.craftforever.infinitefeatures.world;

import java.util.Random;

import com.github.craftforever.infinitefeatures.blocks.tree.SaplingBase;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenRandomTree extends WorldGenAbstractTree
{
	
	private int minheight;
	public IBlockState log;
	public IBlockState leaves;
	public IBlockState sapling;
	
	public WorldGenRandomTree(String name) 
	{
		super(false);
		this.minheight = RandomHelper.getRandomIntInRange(5, 15);
		log = Block.getBlockFromName(name+"_log").getDefaultState();
		leaves = Block.getBlockFromName(name+"_leaves").getDefaultState();
		sapling = Block.getBlockFromName(name+"_sapling").getDefaultState();
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) 
	{
		int height = this.minheight + rand.nextInt(3);
		boolean flag = true;
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		for(int yPos = y; yPos <= y + 1 + height; yPos++) 
		{
			int b0 = 2;
			if(yPos == y) b0 = 1;
			if(yPos >= y + 1 + height - 2) b0 = 2;
			
			for(int xPos = x - b0; x <= x + b0 && flag; xPos++) 
			{
				for(int zPos = z - b0;zPos <= b0 && flag; zPos++) 
				{
					if(yPos > 0 && yPos < world.getHeight()) 
					{
						if(!this.isReplaceable(world, new BlockPos(xPos, yPos, zPos))) 
						{
							flag = false;
						}
					}
					else 
					{
						flag = true;
					}
				}
			}
		}
		if(!flag) 
		{
			return false;
		}
		else 
		{
			BlockPos down = pos.down();
			IBlockState state = world.getBlockState(down);
			boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (SaplingBase)sapling);
			
			if(isSoil && y < world.getHeight() - height - 1) 
			{
				state.getBlock().onPlantGrow(state, world, down, pos);
				
				for(int yPos = y - 3 + height; yPos <= y + height; yPos++) 
				{
					int b1 = yPos - y + height;
					int b2 = 1 - b1 / 2;
					
					for(int xPos = x - b2; xPos <= x + b2; xPos++) 
					{
						int b3 = xPos - x;
						for(int zPos = z - b2; zPos <= z + b2; zPos++) 
						{
							int b4 = zPos - z;
							if(Math.abs(b3) != b2 || Math.abs(b4) != b2 || rand.nextInt(2) != 0 && b1 != 0) 
							{
								BlockPos treepos = new BlockPos(xPos, yPos, zPos);
								IBlockState treestate = world.getBlockState(treepos);
								if(treestate.getBlock().isAir(treestate, world, treepos) || treestate.getBlock().isAir(treestate, world, treepos)) 
								{
									this.setBlockAndNotifyAdequately(world, treepos, leaves);
									this.setBlockAndNotifyAdequately(world, treepos.add(0, -0.25 * height, 0), leaves);
									this.setBlockAndNotifyAdequately(world, treepos.add(0, -0.5 * height, 0), leaves);
								}			
							}
						}
					}
				}
				for(int logheight = 0; logheight < height; logheight++) 
				{
					BlockPos up = pos.up(logheight);
					IBlockState logstate = world.getBlockState(up);
					
					if(logstate.getBlock().isAir(logstate, world, up) || logstate.getBlock().isLeaves(logstate, world, up)) 
					{
						this.setBlockAndNotifyAdequately(world, pos.up(logheight), log);
					}
				}
				
				return true;
			}
		}
		return true;
	}
}
