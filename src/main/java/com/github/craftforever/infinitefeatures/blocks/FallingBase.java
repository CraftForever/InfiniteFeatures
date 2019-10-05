package com.github.craftforever.infinitefeatures.blocks;

import java.util.Random;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;
import com.github.craftforever.infinitefeatures.util.Mineral;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingBase extends BlockFalling implements IHasModel{
public Mineral mineral;
	
	public FallingBase(String name, Material material, Mineral imineral)
	{
		super(material);
		mineral = imineral;
		setTranslationKey(name);
		setRegistryName(name);
		setSoundType(SoundType.WOOD);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels()
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!this.getMaterial(state).equals(Material.SAND))
        {
        	return;
        }
        super.updateTick(worldIn, pos, state, rand);
    }
}
