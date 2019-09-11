package com.github.craftforever.infinitefeatures.proxy;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
	/*
	 * try { registerResources(); } catch (NoSuchFieldException | SecurityException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 */
	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @SuppressWarnings("unchecked")
    public static void registerResources() throws NoSuchFieldException, SecurityException {
	List<IResourcePack> resourcePacks = null;
	// replace "defaultResourcePacks" with "field_110449_ao" when you want to build
	// it
	// replace "defaultResourcePacks" with "field_110449_ao" when you want to build
	// it
	Field field = Minecraft.class.getDeclaredField("defaultResourcePacks");
	// replace "defaultResourcePacks" with "field_110449_ao" when you want to build
	// it
	// replace "defaultResourcePacks" with "field_110449_ao" when you want to build
	// it
	field.setAccessible(true);
	try {
	    resourcePacks = (List<IResourcePack>) field.get(Minecraft.getMinecraft());
	} catch (IllegalArgumentException | IllegalAccessException e) {
	}
	// defaultResourcePacks =
	// ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,Minecraft.getMinecraft(),"defaultResourcePacks");
	FolderResourcePack boby = new FolderResourcePack(new File("InfiniCraft/Resources"));
	resourcePacks.add(boby);
	try {
	    field.set(Minecraft.getMinecraft(), resourcePacks);
	} catch (IllegalArgumentException | IllegalAccessException e) {
	}
	FMLClientHandler.instance().refreshResources();
    }
}