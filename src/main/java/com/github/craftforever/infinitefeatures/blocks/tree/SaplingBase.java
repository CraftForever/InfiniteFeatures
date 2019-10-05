package com.github.craftforever.infinitefeatures.blocks.tree;

import java.util.Random;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;
import com.github.craftforever.infinitefeatures.util.Wood;
import com.github.craftforever.infinitefeatures.world.WorldGenRandomTree;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class SaplingBase extends BlockBush implements IGrowable,IHasModel
{

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
	
    public SaplingBase(Wood wood) 
    {
    	setTranslationKey(wood.name+"_sapling");
    	setRegistryName(wood.name+"_sapling");
    	this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
    	
    	ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
    {
    	return SAPLING_AABB;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) 
    {
    	return NULL_AABB;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) 
    {
    	return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) 
    {
    	return false;
    }
    
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >>> 3));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {STAGE});
	}
	
	//tree code
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		if(((Integer)state.getValue(STAGE)).intValue() == 0) 
		{
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		}
		else 
		{
			this.generateTree(worldIn, rand, pos, state);
		}
	}
	
	public void generateTree(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		if(TerrainGen.saplingGrowTree(worldIn, rand, pos)) {return;}
		WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false));
		int i = 0,j = 0;
		boolean flag = false;
		
		gen = new WorldGenRandomTree();
		
		IBlockState iblockstate = Blocks.AIR.getDefaultState();
		if(flag) 
		{
			worldIn.setBlockState(pos.add(0, 0, 0), iblockstate, 4);
			worldIn.setBlockState(pos.add(1, 0, 0), iblockstate, 4);
			worldIn.setBlockState(pos.add(0, 0, 1), iblockstate, 4);
			worldIn.setBlockState(pos.add(1, 0, 1), iblockstate, 4);
		}
		else 
		{
			worldIn.setBlockState(pos, iblockstate, 4);
		}
		
		if(!gen.generate(worldIn, rand, pos)) 
		{
			if(flag) 
			{
				worldIn.setBlockState(pos.add(0, 0, 0), iblockstate, 4);
				worldIn.setBlockState(pos.add(1, 0, 0), iblockstate, 4);
				worldIn.setBlockState(pos.add(0, 0, 1), iblockstate, 4);
				worldIn.setBlockState(pos.add(1, 0, 1), iblockstate, 4);
			}
			else 
			{
				worldIn.setBlockState(pos, iblockstate, 4);
			}
		}
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) 
	{
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		return (double)worldIn.rand.nextFloat() < 0.450;
	}
	
	@Override
	protected boolean canSustainBush(IBlockState state) 
	{
		return super.canSustainBush(state);
	}
	
	@Override
	public void registerModels()
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
