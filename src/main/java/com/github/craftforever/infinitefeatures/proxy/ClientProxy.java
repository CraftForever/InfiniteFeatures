package com.github.craftforever.infinitefeatures.proxy;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.gui.GuiCustomCreateWorld;
import com.github.craftforever.infinitefeatures.gui.GuiCustomWorldSelection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy{
	
	public static GuiScreen parentscreen;
	public static long currentSeedlong;
	public static boolean continueRandomGeneration = true;
	public static String currentWorldFolder;
	public static String currentWorldFolderName;
	public static File saveDir;
	public static Logger logger = LogManager.getLogger();
	
	
	public static boolean fastStart = false;
	public static boolean fastLoad = false;
	public static WorldSettings fastWorldSettings;
	public static String fastWorldName;
	public static long fastSeed;
	public static GameType fastGameType;
	public static boolean fastEnabledFeatures;
	public static boolean fastHardcoreMode;
	public static boolean fastEnableBonusChest;
	public static boolean fastEnableCommands;
	public static int fastWorldTypeIndex;
	public static String fastchunkProviderSettings;
	public static int fastIndex;
	
	public ClientProxy(){
		MinecraftForge.EVENT_BUS.register(getClass());
		try {
			setupVariables();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void setupVariables() throws FileNotFoundException, IOException, ClassNotFoundException{
		File fastStartFile = new File("InfiniCraft/fastStartFlag");
		File fastLoadFile = new File("InfiniCraft/fastLoadFlag");
		try
		{
			currentSeedlong = ByteToLong(Files.readAllBytes(Paths.get("InfiniCraft/currentseed")));
		}
		catch (IOException e)
		{
			continueRandomGeneration = false;
			System.out.print("NO SEED SELECTED\n");
		}
		
		if (continueRandomGeneration)
		{
			try
			{
				currentWorldFolder = "saves/"+new String(Files.readAllBytes(Paths.get("InfiniCraft/currentworld")));
				currentWorldFolderName = new String(Files.readAllBytes(Paths.get("InfiniCraft/currentworld")));
			}
			catch (IOException e)
			{
				continueRandomGeneration = false;
				System.out.print("NO WORLD SELECTED\n");
			}
		}
		if(fastStartFile.exists()&&continueRandomGeneration)
		{
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(fastStartFile));
			fastStart = true;
			fastWorldName =(String)in.readObject();
			fastSeed =(long)in.readObject();
			fastGameType=(GameType)in.readObject();
			fastEnabledFeatures=(boolean)in.readObject();
			fastHardcoreMode=(boolean)in.readObject();
			fastEnableBonusChest=(boolean)in.readObject();
			fastEnableCommands=(boolean)in.readObject();
			fastWorldTypeIndex=(int)in.readObject();
			fastchunkProviderSettings=(String)in.readObject();
			in.close();
			fastStartFile.delete();
			fastWorldSettings = new WorldSettings(fastSeed, fastGameType, fastEnabledFeatures, fastHardcoreMode, WorldType.WORLD_TYPES[fastWorldTypeIndex]);
			fastWorldSettings.setGeneratorOptions(fastchunkProviderSettings);
			if(fastEnableBonusChest)
				fastWorldSettings.enableBonusChest();
			if(fastEnableCommands)
				fastWorldSettings.enableCommands();
		}
		if(fastLoadFile.exists()&&continueRandomGeneration)
		{
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(fastLoadFile));
			fastLoad = true;
			fastWorldName =(String)in.readObject();
			fastSeed =(long)in.readObject();
			fastIndex =(int)in.readObject();
			in.close();
			fastLoadFile.delete();
		}
		
		if (continueRandomGeneration)
		{
			try
			{
				InfiniteFeatures.currentWorldSeed = ByteToLong(Files.readAllBytes(Paths.get("InfiniCraft/currentseed")));
				System.out.print("\n\n\n\n\n"+InfiniteFeatures.currentWorldSeed+"\n\n\n\n\n");
				InfiniteFeatures.seededRandom = new Random(InfiniteFeatures.currentWorldSeed);
			}
			catch (IOException e)
			{
				continueRandomGeneration = false;
				System.out.print("SELECTED WORLD DOESN'T EXIST\n");
				System.out.print("SELECTED WORLD: "+currentWorldFolder+"/infConfig/infFile" );
			}
		}

	}
	
	
	
	@SubscribeEvent
	public static void onOpenGui(GuiOpenEvent event)
	{
		if(event.getGui() != null && event.getGui().getClass() == GuiMainMenu.class)
		{
			parentscreen = event.getGui();
			if(fastStart) {
				event.setGui(new GuiCustomCreateWorld(parentscreen));
				parentscreen = event.getGui();
			}
			if(fastLoad) {
				event.setGui(new GuiWorldSelection(parentscreen));
				GuiWorldSelection p = (GuiWorldSelection) event.getGui();
				event.setGui(new GuiCustomWorldSelection(parentscreen,p));
			}
		}
		if(event.getGui() != null && event.getGui().getClass() == GuiWorldSelection.class)
		{
			GuiWorldSelection p = (GuiWorldSelection) event.getGui();
			event.setGui(new GuiCustomWorldSelection(parentscreen,p));
			parentscreen = event.getGui();
		}
		if(event.getGui() != null && event.getGui().getClass() == GuiCreateWorld.class)
		{
			event.setGui(new GuiCustomCreateWorld(parentscreen));
		}
	}	
	
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		/*
		try {
			registerResources();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		*/
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@SuppressWarnings("unchecked")
	public static void registerResources() throws NoSuchFieldException, SecurityException
	{
		List<IResourcePack> resourcePacks = null;
		//replace "defaultResourcePacks" with "field_110449_ao" when you want to build it
		//replace "defaultResourcePacks" with "field_110449_ao" when you want to build it
		Field field = Minecraft.class.getDeclaredField("field_110449_ao");
		//replace "defaultResourcePacks" with "field_110449_ao" when you want to build it
		//replace "defaultResourcePacks" with "field_110449_ao" when you want to build it
		field.setAccessible(true);
		try {resourcePacks = (List<IResourcePack>) field.get(Minecraft.getMinecraft());}
		catch (IllegalArgumentException | IllegalAccessException e) {}
		//defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,Minecraft.getMinecraft(),"defaultResourcePacks");
		FolderResourcePack boby = new FolderResourcePack(new File("InfiniCraft/Resources"));
		resourcePacks.add(boby);
		try {field.set(Minecraft.getMinecraft(), resourcePacks);} 
		catch (IllegalArgumentException | IllegalAccessException e) {}
		FMLClientHandler.instance().refreshResources();
	}
	
	
	public static void saveInfFile(long Iseed, String saveDir, String worldName, Minecraft mc, WorldSettings worldSettings, int selectedIndex,String chunkProviderSettings) throws IOException
	{
		boolean shutItDown = false;
		File infinicraftFolder = new File("InfiniCraft");
		File currentWorldFile = new File(infinicraftFolder,"currentworld");
		File currentSeedFile = new File(infinicraftFolder,"currentseed");
		infinicraftFolder.mkdirs();
		
		if(!currentWorldFile.exists())
		{
			currentWorldFile.createNewFile();
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
			//shutItDown = true;
		}
		else
		{
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
		}
		
		if(!currentSeedFile.exists())
		{
			currentSeedFile.createNewFile();
			Files.write(currentSeedFile.toPath(), longToByte(Iseed));
			shutItDown = true;
		}else {
			if(ByteToLong(Files.readAllBytes(currentSeedFile.toPath())) != Iseed) {
				Files.write(currentSeedFile.toPath(), longToByte(Iseed));
				shutItDown = true;
			}
		}
		if(shutItDown)
		{
			File fastStartFlag = new File(infinicraftFolder,"fastStartFlag");
			if (fastStartFlag.exists())fastStartFlag.delete();
			fastStartFlag.createNewFile();
			FileOutputStream fout=new FileOutputStream(fastStartFlag.toString());
			ObjectOutputStream out=new ObjectOutputStream(fout);
			out.writeObject(worldName);
			out.flush();
			out.writeObject(worldSettings.getSeed());
			out.flush();
			out.writeObject(worldSettings.getGameType());
			out.flush();
			out.writeObject(worldSettings.isMapFeaturesEnabled());
			out.flush();
			out.writeObject(worldSettings.getHardcoreEnabled());
			out.flush();
			out.writeObject(worldSettings.isBonusChestEnabled());
			out.flush();
			out.writeObject(worldSettings.areCommandsAllowed());
			out.flush();
			out.writeObject(selectedIndex);
			out.flush();
			out.writeObject(chunkProviderSettings);
			out.flush();
			out.close();
			mc.shutdown();
		}
	}
	public static void saveInfFileAtLoad(WorldSummary summary,int index) throws IOException
	{
		File dir = new File("saves/"+summary.getFileName());
		File infinicraftFolder = new File("InfiniCraft");
		File currentWorldFile = new File(infinicraftFolder,"currentworld");
		File currentSeedFile = new File(infinicraftFolder,"currentseed");
		String saveDir = summary.getFileName();
		NBTTagCompound leveldat;
		leveldat = CompressedStreamTools.readCompressed(new FileInputStream(new File(dir, "level.dat")));
		boolean shutItDown = false;
		long iseed = leveldat.getCompoundTag("Data").getLong("RandomSeed");
		
		System.out.print("\n\nWORLD SEED: "+iseed+"\n\n");
		infinicraftFolder.mkdirs();
		
		if(!currentWorldFile.exists())
		{
			System.out.print("\n\nCURRENT WORLD FILE DOESN'T EXIST\n\n");
			currentWorldFile.createNewFile();
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
			//shutItDown = true;
		}
		else
		{
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
		}
		
		
		if(!currentSeedFile.exists())
		{
			System.out.print("\n\nCURRENT SEED FILE DOESN'T EXIST\n\n");
			currentSeedFile.createNewFile();
			Files.write(currentSeedFile.toPath(), longToByte(iseed));
			shutItDown = true;
		}else {
			if(ByteToLong(Files.readAllBytes(currentSeedFile.toPath())) != iseed) {
				System.out.print("\n\nWRONG SEED\n\n");
				System.out.print("INPUTED SEED: "+iseed+"\nEXPECTED SEED: "+ByteToLong(Files.readAllBytes(currentSeedFile.toPath()))+"\n\n");
				//currentSeedFile.delete();
				//currentSeedFile.createNewFile();
				byte[] ibytes = longToByte(iseed);
				Files.write(currentSeedFile.toPath(), ibytes);
				System.out.print(ibytes);
				System.out.print(Files.readAllBytes(currentSeedFile.toPath()));
				shutItDown = true;
			}
		}
		if(shutItDown)
		{
			System.out.print("\n\nSHUTTING DOWN\n\n");
			File fastLoadFlag = new File(infinicraftFolder,"fastLoadFlag");
			if (fastLoadFlag.exists())fastLoadFlag.delete();
			fastLoadFlag.createNewFile();
			FileOutputStream fout=new FileOutputStream(fastLoadFlag.toString());
			ObjectOutputStream out=new ObjectOutputStream(fout);
			out.writeObject(summary.getFileName());
			out.flush();
			out.writeObject(iseed);
			out.flush();
			out.writeObject(index);
			out.flush();
			out.close();
			Minecraft.getMinecraft().shutdown();
		}
	}
	
	
	
	public static byte[] longToByte(long l)
	{
		byte[] b = new byte[]
				{
			(byte) l,
			(byte) (l >> 8),
			(byte) (l >> 16),
			(byte) (l >> 24),
			(byte) (l >> 32),
			(byte) (l >> 40),
			(byte) (l >> 48),
			(byte) (l >> 56)
			};
		return b;
	}
	public static long ByteToLong(byte[] b)
	{
		long l = ((long) b[7] << 56)
			| ((long) b[6] & 0xff) << 48
			| ((long) b[5] & 0xff) << 40
			| ((long) b[4] & 0xff) << 32
			| ((long) b[3] & 0xff) << 24
			| ((long) b[2] & 0xff) << 16
			| ((long) b[1] & 0xff) << 8
			| ((long) b[0] & 0xff);
			return l;
	}
}