package com.github.craftforever.infinitefeatures.blocks;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;
import com.github.craftforever.infinitefeatures.util.Mineral;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class RandomBlock extends Block implements IHasModel {
    public Mineral mineral;

    public RandomBlock(Mineral imineral) {
	super(imineral.material);
	setTranslationKey(imineral.name + "_ore");
	setRegistryName(imineral.name + "_ore");
	ModBlocks.BLOCKS.add(this);
	ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

	setSoundType(imineral.sound);
	setHardness(imineral.hardness);
	setResistance(imineral.resistance);
	setHarvestLevel(imineral.toolType, imineral.harvestLevel);
	setLightLevel(imineral.lightlevel);
	mineral = imineral;
    }

    @Override
    public void registerModels() {
	InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}