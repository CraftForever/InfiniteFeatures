package com.github.craftforever.infinitefeatures.world.oregen;

import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class OrePredicate implements Predicate<IBlockState>
{
	public static IBlockState block;
	
	@Override
	public boolean apply(IBlockState input) {
		if(input != null&&block.getMaterial()==Material.ROCK&&input.getBlock()==Blocks.STONE) 
		{
			return true;
		}
		else if(input != null&&block.getMaterial()==Material.SAND&&input.getBlock()==Blocks.SAND) 
		{
			return true;
		}
		else if(input != null&&block.getMaterial()==Material.GROUND&&input.getBlock()==Blocks.DIRT) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
