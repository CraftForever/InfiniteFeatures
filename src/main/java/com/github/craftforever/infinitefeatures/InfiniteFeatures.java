package com.github.craftforever.infinitefeatures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.craftforever.infinitefeatures.gui.GuiCustomCreateWorld;
import com.github.craftforever.infinitefeatures.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	public static String currentWorldFolder;
	public static GuiScreen parentscreen;
	public static Logger logger = LogManager.getLogger();
	public static boolean continueRandomGeneration = true;
	

	public static CreativeTabs infItemsTab = new CreativeTabs("infeatures_items_tab")
	{
		@Override
		public ItemStack createIcon() {	return new ItemStack(Templates.items()[0]);	}
	};
	
	public static CreativeTabs infBlocksTab = new CreativeTabs("infeatures_blocks_tab")
	{
		@Override
		public ItemStack createIcon() {	return new ItemStack(Templates.blocks()[0]);	}
	};
	
	
	
	@Instance
	public static InfiniteFeatures instance;
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{

	}
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{

	}
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		
	}
	
	

	public InfiniteFeatures()
	{
		if (continueRandomGeneration) {
			try {
				currentWorldFolder = "saves/"+new String(Files.readAllBytes(Paths.get("InfiniCraft/currentworld")));
			} catch (IOException e) {
				continueRandomGeneration = false;
				System.out.print("\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");
				System.out.print("NO WORLD SELECTED\n");
			}
		}
		
		if (continueRandomGeneration) {
			try {
				currentWorldSeed = ByteToLong(Files.readAllBytes(Paths.get(currentWorldFolder+"/infConfig/infFile")));
			} catch (IOException e) {
				continueRandomGeneration = false;
				System.out.print("\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");
				System.out.print("SELECTED WORLD DOESN'T EXIST\n");
				System.out.print("SELECTED WORLD: "+currentWorldFolder+"/infConfig/infFile" );
			}
		}
		
		MinecraftForge.EVENT_BUS.register(getClass());
	}
	
	public static void saveInfFile(long Iseed, String saveDir, String worldName,Minecraft mc) throws IOException
	{
		boolean shutItDown = false;
		seed = Iseed;
		File generationFolder = new File("saves/"+saveDir+"/infConfig");
		File infinicraftFolder = new File("InfiniCraft");
		generationFolder.mkdirs();
		infinicraftFolder.mkdirs();
		File generationFile = new File(generationFolder,"infFile");
		File currentWorldFile = new File(infinicraftFolder,"currentworld");
		
		if(!currentWorldFile.exists()){
			currentWorldFile.createNewFile();
			byte[] saveBytes = saveDir.getBytes();
			Files.write(currentWorldFile.toPath(), saveBytes);
		}else {
			
			
			String a = saveDir;
	        String b = new String(Files.readAllBytes(currentWorldFile.toPath()));
	        boolean isEqual = a.length() == b.length();
	        for (int i = 0; isEqual && i < a.length(); i++) {
	            if (b.charAt(i) != a.charAt(i)) {
	                isEqual = false;
	                break;
	            }
	        }
			if (!isEqual) {
				byte[] saveBytes = saveDir.getBytes();
				Files.write(currentWorldFile.toPath(), saveBytes);
				shutItDown = true;
			}
		}
		if(!generationFile.exists()){
			generationFile.createNewFile();
			Files.write(generationFile.toPath(), longToByte(seed));
			logger.info("File generated here: " + generationFile.toPath().toString()+"\n");
		}
		if(shutItDown) {
			mc.shutdown();
		}
		
	}
	public static byte[] longToByte(long l) {
		byte[] b = new byte[] {
			       (byte) l,
			       (byte) (l >> 8),
			       (byte) (l >> 16),
			       (byte) (l >> 24),
			       (byte) (l >> 32),
			       (byte) (l >> 40),
			       (byte) (l >> 48),
			       (byte) (l >> 56)};
		return b;
	}
	public static long ByteToLong(byte[] b) {
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
    	if(event.getGui() != null && event.getGui().getClass() == GuiWorldSelection.class)
        {
			parentscreen = event.getGui();
        }
        if(event.getGui() != null && event.getGui().getClass() == GuiCreateWorld.class)
        {
            event.setGui(new GuiCustomCreateWorld(parentscreen));
        }
    }
}