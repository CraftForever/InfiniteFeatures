package com.github.craftforever.infinitefeatures.items;

import com.github.craftforever.infinitefeatures.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InfiniTab extends CreativeTabs
{	 
	public InfiniTab(String label)
	{
	super(label);
	}
	
	@SideOnly(Side.CLIENT)
	
	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(ItemBlock.getItemFromBlock((Block) ModBlocks.oreArray[0]));
	}
}
