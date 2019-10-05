package com.github.craftforever.infinitefeatures;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.craftforever.infinitefeatures.gui.*;
import com.github.craftforever.infinitefeatures.proxy.*;
import com.github.craftforever.infinitefeatures.util.handler.*;
import com.github.craftforever.infinitefeatures.world.OreGen;
import com.github.craftforever.infinitefeatures.items.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.*;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "infeatures")
public class InfiniteFeatures
{
	public static final String modID = "infeatures";
	public static final String CLIENT_PROXY_CLASS = "com.github.craftforever.infinitefeatures.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.github.craftforever.infinitefeatures.proxy.CommonProxy";
	public static World world;
	public static File saveDir;
	public static long seed;
	public static long currentWorldSeed;
	public static Random seededRandom;
	public static String currentWorldFolder;
	public static String currentWorldFolderName;
	public static GuiScreen parentscreen;
	public static Logger logger = LogManager.getLogger();
	public static boolean continueRandomGeneration = true;
	public static boolean fastStart = false;
	public static boolean fastLoad = false;
	
	//fast start variables
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
	public static int WOOD_QTY = 10;
	public static int ORE_QTY = 10;
	
	public static final CreativeTabs InfiniTab = new InfiniTab("InfiniteTab");
	
	@Instance
	public static InfiniteFeatures instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		if(continueRandomGeneration) {
			try 
			{
				RegistryHandler.generateLangFile();
			}
			catch (IOException e) {}
		
			try
			{
				RegistryHandler.generateTextures();
			}
			catch (IOException e1) {}
			
			try
			{
				RegistryHandler.generateModels();
			}
			catch (IOException e1) {}
		
			
			try
			{
				ClientProxy.registerResources();
			}
			catch (NoSuchFieldException | SecurityException e) {}
			
			GameRegistry.registerWorldGenerator(new OreGen(), 3);
		}
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		if(continueRandomGeneration) {
			RecipeHandler.createFurnaceRecipes();
			RecipeHandler.createCraftingRecipes();
		}
		
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		
	}

	public InfiniteFeatures() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		
		File fastStartFile = new File("InfiniCraft/fastStartFlag");
		File fastLoadFile = new File("InfiniCraft/fastLoadFlag");
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
				currentWorldSeed = ByteToLong(Files.readAllBytes(Paths.get(currentWorldFolder+"/infConfig/infFile")));
				seededRandom = new Random(currentWorldSeed);
			}
			catch (IOException e)
			{
				continueRandomGeneration = false;
				System.out.print("SELECTED WORLD DOESN'T EXIST\n");
				System.out.print("SELECTED WORLD: "+currentWorldFolder+"/infConfig/infFile" );
			}
		}
		MinecraftForge.EVENT_BUS.register(getClass());
	}
	
	public static void saveInfFile(long Iseed, String saveDir, String worldName, Minecraft mc, WorldSettings worldSettings, int selectedIndex,String chunkProviderSettings) throws IOException
	{
		boolean shutItDown = false;
		seed = Iseed;
		File generationFolder = new File("saves/"+saveDir+"/infConfig");
		File infinicraftFolder = new File("InfiniCraft");
		File generationFile = new File(generationFolder,"infFile");
		File currentWorldFile = new File(infinicraftFolder,"currentworld");
		generationFolder.mkdirs();
		infinicraftFolder.mkdirs();
		
		if(!currentWorldFile.exists())
		{
			currentWorldFile.createNewFile();
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
			shutItDown = true;
		}
		else
		{
		String a = saveDir;
		String b = new String(Files.readAllBytes(currentWorldFile.toPath()));
		boolean isEqual = a.length() == b.length();
		
		for (int i = 0; isEqual && i < a.length(); i++)
		{
			if (b.charAt(i) != a.charAt(i))
			{
			isEqual = false;
			break;
			}
		}
			if (!isEqual)
			{
				byte[] saveBytes = saveDir.getBytes();
				Files.write(currentWorldFile.toPath(), saveBytes);
				shutItDown = true;
			}
		}
		
		if(!generationFile.exists())
		{
			generationFile.createNewFile();
			Files.write(generationFile.toPath(), longToByte(seed));
			logger.info("File generated here: " + generationFile.toPath().toString()+"\n");
			shutItDown = true;
		}
		
		if(shutItDown)
		{
			File fastStartFlag = new File(infinicraftFolder,"fastStartFlag");
			if (fastStartFlag.exists())
				fastStartFlag.delete();
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
		File generationFolder = new File("saves/"+saveDir+"/infConfig");
		File infinicraftFolder = new File("InfiniCraft");
		File generationFile = new File(generationFolder,"infFile");
		File currentWorldFile = new File(infinicraftFolder,"currentworld");
		String saveDir = summary.getFileName();
		NBTTagCompound leveldat;
		leveldat = CompressedStreamTools.readCompressed(new FileInputStream(new File(dir, "level.dat")));
		boolean shutItDown = false;
		long iseed = leveldat.getLong("RandomSeed");
		generationFolder.mkdirs();
		infinicraftFolder.mkdirs();
		
		if(!currentWorldFile.exists())
		{
			currentWorldFile.createNewFile();
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
			shutItDown = true;
		}
		else
		{
			String a = saveDir;
			String b = new String(Files.readAllBytes(currentWorldFile.toPath()));
			boolean isEqual = a.length() == b.length();
			for (int i = 0; isEqual && i < a.length(); i++)
			{
				if (b.charAt(i) != a.charAt(i))
				{
					isEqual = false;
					break;
				}
			}
			if (!isEqual)
			{
				byte[] saveBytes = saveDir.getBytes();
				Files.write(currentWorldFile.toPath(), saveBytes);
				shutItDown = true;
			}
		}
		
		if(!generationFile.exists())
		{
			generationFile.createNewFile();
			Files.write(generationFile.toPath(), longToByte(iseed));
			logger.info("File generated here: " + generationFile.toPath().toString()+"\n");
			shutItDown = true;
		}
		
		if(shutItDown)
		{
			File fastLoadFlag = new File(infinicraftFolder,"fastLoadFlag");
			if (fastLoadFlag.exists())
				fastLoadFlag.delete();
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
}