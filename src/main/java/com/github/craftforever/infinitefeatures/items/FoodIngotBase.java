package com.github.craftforever.infinitefeatures.items;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModItems;

import net.minecraft.item.ItemFood;

public class FoodIngotBase extends ItemFood implements IHasModel{

	public FoodIngotBase(String name, int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		setTranslationKey(name);
		setRegistryName(name);

		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		InfiniteFeatures.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
