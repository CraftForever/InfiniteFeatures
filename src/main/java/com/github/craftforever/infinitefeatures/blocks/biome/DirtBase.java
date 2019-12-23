package com.github.craftforever.infinitefeatures.blocks.biome;

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

public class DirtBase extends Block implements IHasModel
{

	public Plant plant;
	
	public DirtBase(Plant plant) 
	{
		super(Material.GROUND);
		this.plant = plant;
		setTranslationKey(plant.name+"_dirt");
		setRegistryName(plant.name+"_dirt");
		setSoundType(SoundType.GROUND);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() 
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}

}
