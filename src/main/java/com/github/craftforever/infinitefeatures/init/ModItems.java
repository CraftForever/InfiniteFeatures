package com.github.craftforever.infinitefeatures.init;

import java.util.ArrayList;
import java.util.List;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.items.ItemIngotBase;

import net.minecraft.item.Item;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static Item[] itemArray = generateitemarray();
    // public static Item RANDOM_INGOT1 = new
    // ItemIngotBase(ModBlocks.minerals[0].name+"_ingot").setCreativeTab(InfiniteFeatures.InfiniTab);
    // public static Item RANDOM_INGOT2 = new
    // ItemIngotBase(ModBlocks.minerals[1].name+"_ingot").setCreativeTab(InfiniteFeatures.InfiniTab);
    // public static Item RANDOM_INGOT3 = new
    // ItemIngotBase(ModBlocks.minerals[2].name+"_ingot").setCreativeTab(InfiniteFeatures.InfiniTab);

    private static Item[] generateitemarray() {
	Item[] itemarray = new Item[InfiniteFeatures.ORE_QTY];
	for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++) {
	    itemarray[i] = new ItemIngotBase(ModBlocks.minerals[i].name + "_ingot")
		    .setCreativeTab(InfiniteFeatures.InfiniTab);
	}
	return itemarray;
    }
}