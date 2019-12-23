package com.github.craftforever.infinitefeatures;

import java.io.*;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.craftforever.infinitefeatures.proxy.*;
import com.github.craftforever.infinitefeatures.util.handler.*;
import com.github.craftforever.infinitefeatures.world.oregen.OreGen;
import com.github.craftforever.infinitefeatures.items.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "infeatures")
public class InfiniteFeatures
{
	public static final String modID = "infeatures";
	public static final String CLIENT_PROXY_CLASS = "com.github.craftforever.infinitefeatures.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.github.craftforever.infinitefeatures.proxy.CommonProxy";
	public static File saveDir;
	public static long currentWorldSeed = 0;
	public static Random seededRandom = new Random(currentWorldSeed);
	public static Random seededRandomClient = new Random(currentWorldSeed+123456);
	public static Logger logger = LogManager.getLogger();
	public static boolean continueRandomGeneration = true;

	//fast start variables
	public static int PLANT_QTY = 20;
	public static int ORE_QTY = 20;
	
	public static final CreativeTabs InfiniTab = new InfiniTab("InfiniteTab");
	
	@Instance
	public static InfiniteFeatures instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
			//File dir = new File("World");
			File leveldatfile = new File("World/level.dat");
				if (leveldatfile.exists())
				{
					NBTTagCompound leveldat = null;
					try {
						leveldat = CompressedStreamTools.readCompressed(new FileInputStream(leveldatfile));
					} catch (IOException e) {
						System.out.print("\nNO WORLD GENERATED\n");
					}
					currentWorldSeed = leveldat.getCompoundTag("Data").getLong("RandomSeed");
					seededRandom = new Random(currentWorldSeed);
				}else {
					PropertyManager settings = new PropertyManager(new File("server.properties"));
					String s = settings.getStringProperty("level-seed", "");
					long k = 0;
					if (!s.isEmpty())
	                {
						try
	                    {
							long l = Long.parseLong(s);
							if (l != 0L)
	                        {
	                            k = l;
	                        }
	                    }
						catch (NumberFormatException var16)
	                    {
	                        k = (long)s.hashCode();
	                    }
						currentWorldSeed = k;
	                }else {
	                	System.out.print("\nPUT A SEED IN server.properties OR RESTARD AFTER WORLD CREATION\n");
	                	System.out.print("\nPUT A SEED IN server.properties OR RESTARD AFTER WORLD CREATION\n");
	                	System.out.print("\nPUT A SEED IN server.properties OR RESTARD AFTER WORLD CREATION\n");
	                }
				}
		}
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
		
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT){
				try
				{
					ClientProxy.registerResources();
				}
				catch (NoSuchFieldException | SecurityException e) {}
				}
			GameRegistry.registerWorldGenerator(new OreGen(), 3);
		}
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		if(continueRandomGeneration) {
			RecipeHandler.createFurnaceRecipes();
			RecipeHandler.createCraftingRecipes();
			BiomeHandler.registerBiomes();
			OreDictionaryHandler.RegisterOreDictionary();
		}
		
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		
	}

	public InfiniteFeatures() throws FileNotFoundException, ClassNotFoundException, IOException 
	{
		MinecraftForge.EVENT_BUS.register(getClass());	
	}
}