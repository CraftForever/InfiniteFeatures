package com.github.craftforever.infinitefeatures.blocks.tree;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;
import com.github.craftforever.infinitefeatures.util.Plant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class plankBase extends Block implements IHasModel
{
	public Plant plant;
	public String name;
	
	public plankBase(Plant plant) 
	{
		super(Material.WOOD);
		this.plant = plant;
		this.name = plant.name+"_planks";
		
		setSoundType(SoundType.WOOD);
		setTranslationKey(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() 
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
