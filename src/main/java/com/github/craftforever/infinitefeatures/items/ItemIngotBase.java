package com.github.craftforever.infinitefeatures.items;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModItems;

import net.minecraft.item.Item;

public class ItemIngotBase extends Item implements IHasModel {
    public ItemIngotBase(String name) {
	setTranslationKey(name);
	setRegistryName(name);

	ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
	InfiniteFeatures.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
