package com.github.craftforever.infinitefeatures.util.handler;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler
{
	public static void createFurnaceRecipes()
	{
		for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.blockArray[i]), new ItemStack(ModItems.itemArray[i],1), 20);
		}
		//GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.RANDOM_BLOCK), new ItemStack(ModItems.RANDOM_INGOT1,1), 20);
		//GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.RANDOM_BLOCK2), new ItemStack(ModItems.RANDOM_INGOT2,1), 20);
		//GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.RANDOM_BLOCK3), new ItemStack(ModItems.RANDOM_INGOT3,1), 20);
	}
	public static void createCraftingRecipes()
	{
		for (int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			String nameItem = ModItems.itemArray[i].getRegistryName().toString();
			String nameBlock = ModBlocks.blockArray[i].getRegistryName().toString();
			GameRegistry.addShapedRecipe(new ResourceLocation("infeatures:"+nameBlock),new ResourceLocation("infeatures:"+nameItem.substring(0,nameItem.length()-6)+"_blocks"),new ItemStack(Item.getItemFromBlock(ModBlocks.ingotblockArray[i]),1), new Object[] {"III","III","III",'I',ModItems.itemArray[i]});
			GameRegistry.addShapelessRecipe(new ResourceLocation("infeatures:"+nameItem), new ResourceLocation("infeatures:"+nameItem.substring(0,nameItem.length()-6)+"_items"), new ItemStack(ModItems.itemArray[i],9), new Ingredient[] {Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.ingotblockArray[i]))});
		}
	}
}
