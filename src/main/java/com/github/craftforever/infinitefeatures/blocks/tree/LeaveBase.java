package com.github.craftforever.infinitefeatures.blocks.tree;

import java.util.List;
import java.util.Random;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;
import com.github.craftforever.infinitefeatures.util.Plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LeaveBase extends BlockLeaves implements IHasModel{

	public Plant plant;
	protected boolean leavesFancy;
	
	
	public LeaveBase(Plant plant) 
	{
		InfiniteFeatures.proxy.setGraphicsLevel(this, true);
		this.plant = plant;
		setTranslationKey(plant.name+"_leaves");
		setRegistryName(plant.name+"_leaves");
		setSoundType(SoundType.PLANT);
		setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		
		if(!((Boolean)state.getValue(DECAYABLE)).booleanValue()) 
		{
			i |= 2;
		}
		
		if(!((Boolean)state.getValue(CHECK_DECAY)).booleanValue()) 
		{
			i |= 2;
		}
		
		return i;
	}

	@Override
	protected int getSaplingDropChance(IBlockState state) {return RandomHelper.getRandomIntInRange(5, 35);}
		
	@Override
	public EnumType getWoodType(int meta) {return null;}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chances) {return;}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) 
	{
		return NonNullList.withSize(1, new ItemStack(this, 1));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {DECAYABLE, CHECK_DECAY});
	}

	public boolean isOpaqueCube(IBlockState state)
    {
        return !this.leavesFancy;
    }
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }
	
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isTopSolid() && rand.nextInt(15) == 1)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)pos.getY() - 0.05D;
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
	
	@Override
	public void registerModels() 
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}
	
	 public Item getItemDropped(IBlockState state, Random rand, int fortune)
	 {
	     return Item.getItemFromBlock(Block.getBlockFromName(plant.name+"_sapling"));
	 }
	 
	 @SideOnly(Side.CLIENT)
	 public void setGraphicsLevel(boolean fancy)
	 {
	     this.leavesFancy = fancy;
	 }
	 public boolean causesSuffocation(IBlockState state)
	 {
	     return false;
	 }

	 @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
	 @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos){ return true; }

	 @Override
	 public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
	 {
	     if (!(Boolean)state.getValue(CHECK_DECAY))
	     {
	         world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
	     }
	 }
	 
	 @SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	 public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	 {
	     return !this.leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	 }
}
