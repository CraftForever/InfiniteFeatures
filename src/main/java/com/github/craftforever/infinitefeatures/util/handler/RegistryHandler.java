package com.github.craftforever.infinitefeatures.util.handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;

import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomIntInRange;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomFloatInRange;
import com.github.craftforever.infinitefeatures.init.IHasModel;
import com.github.craftforever.infinitefeatures.init.ModBlocks;
import com.github.craftforever.infinitefeatures.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		if (InfiniteFeatures.continueRandomGeneration == true)
		{
			event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
		}
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		if (InfiniteFeatures.continueRandomGeneration == true)
		{
			ModBlocks.initarray();
			event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		}
		
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		if (InfiniteFeatures.continueRandomGeneration == true)
		{
			for(Item item : ModItems.ITEMS)
			{
				if(item instanceof IHasModel)
					((IHasModel)item).registerModels();
			}
			for(Block block : ModBlocks.BLOCKS)
			{
				if(block instanceof IHasModel)
					((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void generateLangFile() throws IOException
	{
		Charset charset = Charset.forName("UTF-8");
		File langFolder = new File("InfiniCraft/Resources/assets/infeatures/lang");
		langFolder.mkdirs();
		File packMcmeta = new File("InfiniCraft/Resources/pack.mcmeta");
		if(!packMcmeta.exists())
			packMcmeta.createNewFile();
		
		File langFile = new File("InfiniCraft/Resources/assets/infeatures/lang/en_us.lang");
		if (!langFile.exists())
		{
			langFile.createNewFile();
		}
		else
		{
			langFile.delete();
			langFile.createNewFile();
		}
		String packInput = "{\r\n" + 
				"  \"pack\": {\r\n" + 
				"    \"pack_format\": 3,\r\n" + 
				"    \"description\": \"0.0.2\"\r\n" + 
				"  }\r\n" + 
				"}";
		BufferedWriter writer = Files.newBufferedWriter(langFile.toPath(), charset);
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			Block block = ModBlocks.oreArray[i];
			String blockName = block.getTranslationKey().substring(5);
			blockName = blockName.replace("_", " ");
			blockName = WordUtils.capitalize(blockName);
			String langinput = block.getTranslationKey()+".name="+blockName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++) 
		{
			Block block = ModBlocks.ingotblockArray[i];
			String blockName = block.getTranslationKey().substring(5,block.getTranslationKey().length()-6);
			blockName = WordUtils.capitalize(blockName);
			blockName = "Block of " + blockName;
			String langinput = block.getTranslationKey()+".name="+blockName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(int i = 0; i < ModItems.ToolOres * 5; i++)
		{	
			Item item = ModItems.toolArray[i];
			String itemName = item.getTranslationKey().substring(5);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = item.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(int i = 0; i < ModItems.ArmorCount * 4; i++) 
		{
			Item item = ModItems.armorArray[i];
			String itemName = item.getTranslationKey().substring(5);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = item.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(int i = 0; i < ModBlocks.ingotorecount; i++)
		{
			Item item = ModItems.ingotArray[i];
			String itemName = item.getTranslationKey().substring(5);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = item.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(int i = 0; i < ModBlocks.gemorecount; i++)
		{
			Item item = ModItems.gemArray[i];
			String itemName = item.getTranslationKey().substring(5, item.getTranslationKey().toString().length()-4);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = item.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(Block block : ModBlocks.logArray)
		{
			String itemName = block.getTranslationKey().substring(5, block.getTranslationKey().toString().length()-4);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = block.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(Block block : ModBlocks.leaveArray)
		{
			String itemName = block.getTranslationKey().substring(5, block.getTranslationKey().toString().length()-4);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = block.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		
		for(Block block : ModBlocks.saplingArray)
		{
			String itemName = block.getTranslationKey().substring(5);
			itemName = itemName.replace("_", " ");
			itemName = WordUtils.capitalize(itemName);
			String langinput = block.getTranslationKey()+".name="+itemName+"\n";
			writer.write(langinput);
			writer.flush();
		}
		writer.close();
		writer = Files.newBufferedWriter(packMcmeta.toPath(), charset);
		writer.write(packInput);
		writer.flush();
		writer.close();
	}

	public static void generateTextures() throws IOException
	{
		File blockTextureFolder = new File("InfiniCraft/Resources/assets/infeatures/textures/blocks");
		if(blockTextureFolder.exists())
			FileUtils.deleteDirectory(blockTextureFolder);
		blockTextureFolder.mkdirs();
		File itemTextureFolder = new File("InfiniCraft/Resources/assets/infeatures/textures/items");
		if(itemTextureFolder.exists())
			FileUtils.deleteDirectory(itemTextureFolder);
		itemTextureFolder.mkdirs();
		File armorModelsFolder = new File("InfiniCraft/Resources/assets/infeatures/textures/models/armor");
		if(armorModelsFolder.exists())
			FileUtils.deleteDirectory(armorModelsFolder);
		armorModelsFolder.mkdirs();
		/*
		InputStream streambases = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/base");
		BufferedReader reader = new BufferedReader(new InputStreamReader(streambases));
		List<String> bases = new ArrayList<String>();
		int baseammount = 0;
		while(reader.ready()){
			baseammount ++;
			String line = reader.readLine();
			bases.add(line);
		}
		*/
		//System.out.print(reader.readLine());
		/*
		InputStream streamores = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/ore");
		reader = new BufferedReader(new InputStreamReader(streamores));
		List<String> ores = new ArrayList<String>();
		int oreammount = 0;
		while( reader.ready() ) {
			oreammount ++;
			String line = reader.readLine();
			ores.add(line);
		}
		*/
		//Creating Textures for the Ores and Ingots
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			int num = getRandomIntInRange(0, 13);
			InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/base/"+ModBlocks.minerals[i].underlay+".png");
			BufferedImage baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/ore/ore_"+num+".png");
			BufferedImage oreImg = ImageIO.read(stream);
			BufferedImage finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Graphics g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			Color color = ModBlocks.minerals[i].color;
			dye(oreImg,color);
			g.drawImage(oreImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+ModBlocks.oreArray[i].getTranslationKey().substring(5)+".png"));
			
		}
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			Color color = ModBlocks.minerals[i].color;
			BufferedImage Img;
			if(ModBlocks.minerals[i].isGem) 
			{
				int rand = getRandomIntInRange(1, 28);
				InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/gem/generic"+rand+".png");
				Img = ImageIO.read(stream);
			}
			else 
			{
				InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/ingot/generic.png");
				Img = ImageIO.read(stream);
			}
			dye(Img, color);
			ImageIO.write(Img, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.itemArray[i].getTranslationKey().substring(5)+".png"));
		}
		for(int i = 0; i < InfiniteFeatures.ORE_QTY; i++)
		{
			int a = getRandomIntInRange(10,22);
			int b1 = getRandomIntInRange(13,27);
			int c = getRandomIntInRange(13,27);
			
			
			InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/decorativebase/base_"+a+".png");
			BufferedImage BaseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/decorativetransparent/overlay_"+b1+".png");
			BufferedImage transparentImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/decorativetransparent/overlay_"+c+".png");
			BufferedImage transparentImg2 = ImageIO.read(stream);
			
			Color color = ModBlocks.minerals[i].color;
			float r = getRandomFloatInRange(0.0F,1.0F);
			float g1 = getRandomFloatInRange(0.0F,1.0F);
			float b = getRandomFloatInRange(0.0F,1.0F);
			
			Color randomColor = new Color(r, g1, b);

			dye(BaseImg, color);
			dye(transparentImg, randomColor);
			dye(transparentImg, color);
			
			
			dye(transparentImg2, randomColor);
			dye(transparentImg2, color);
			
			int w = Math.max(transparentImg2.getWidth(), transparentImg.getWidth());
			int h = Math.max(transparentImg2.getHeight(), transparentImg.getHeight());
			BufferedImage combined1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
			Graphics gr = combined1.getGraphics();
			gr.drawImage(transparentImg2, 0, 0, null);
			gr.drawImage(transparentImg, 0, 0, null);
			
			
			w = Math.max(BaseImg.getWidth(), combined1.getWidth());
			h = Math.max(BaseImg.getHeight(), combined1.getHeight());
			BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
		    gr = combined.getGraphics();
			gr.drawImage(BaseImg, 0, 0, null);
			gr.drawImage(combined1, 0, 0, null);
			
			
			
			ImageIO.write(combined, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+ModBlocks.ingotblockArray[i].getTranslationKey().substring(5)+".png"));
			
		}
		for(int i = 0; i < ModItems.ToolOres; i++)
		{
			int axenum = getRandomIntInRange(0, 3);
			InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/axe/axe_handle.png");
			BufferedImage baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/axe/axehead"+axenum+".png");
			BufferedImage headImg = ImageIO.read(stream);
			BufferedImage finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Graphics g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			Color color = ModBlocks.minerals[i].color;
			dye(headImg,color);
			g.drawImage(headImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.axeArray[i].getTranslationKey().substring(5)+".png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/hoe/hoe_handle.png");
			baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/hoe/hoe_blade.png");
			headImg = ImageIO.read(stream);
			finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			dye(headImg,color);
			g.drawImage(headImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.hoeArray[i].getTranslationKey().substring(5)+".png"));
			
			int picknum = getRandomIntInRange(0, 11);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/pickaxe/pickaxe_handle.png");
			baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/pickaxe/pickhead"+picknum+".png");
			headImg = ImageIO.read(stream);
			finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			dye(headImg,color);
			g.drawImage(headImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.pickaxeArray[i].getTranslationKey().substring(5)+".png"));
			
			int swordnum = getRandomIntInRange(0, 9);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/sword/sword_handle.png");
			baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/sword/sword"+swordnum+".png");
			headImg = ImageIO.read(stream);
			finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			dye(headImg,color);
			g.drawImage(headImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.swordArray[i].getTranslationKey().substring(5)+".png"));
		
			int shovelnum = getRandomIntInRange(0, 11);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/shovel/shovel_handle.png");
			baseImg = ImageIO.read(stream);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/tool/shovel/shovel_head_"+shovelnum+".png");
			headImg = ImageIO.read(stream);
			finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			g = finalImg.getGraphics();
			g.drawImage(baseImg, 0, 0, null);
			dye(headImg,color);
			g.drawImage(headImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.shovelArray[i].getTranslationKey().substring(5)+".png"));
		
		}
		
		for(int i = 0; i < ModItems.ArmorCount; i++) 
		{
			String name = ModBlocks.minerals[i].name;
			
			InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/boots.png");
			BufferedImage itemImg = ImageIO.read(stream);
			Color color = ModBlocks.minerals[i].color;
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.bootsArray[i].getTranslationKey().substring(5)+".png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/leggings.png");
			itemImg = ImageIO.read(stream);
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.leggingsArray[i].getTranslationKey().substring(5)+".png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/chestplate.png");
			itemImg = ImageIO.read(stream);
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.chestplateArray[i].getTranslationKey().substring(5)+".png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/helmet.png");
			itemImg = ImageIO.read(stream);
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"+ModItems.helmetArray[i].getTranslationKey().substring(5)+".png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/armor_layer_1.png");
			itemImg = ImageIO.read(stream);
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/models/armor/"+name+"_layer_1.png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/item/armor/armor_layer_2.png");
			itemImg = ImageIO.read(stream);
			dye(itemImg, color);
			ImageIO.write(itemImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/models/armor/"+name+"_layer_2.png"));
		}
		
		for(int i = 0; i < InfiniteFeatures.WOOD_QTY; i++) 
		{
			String name = ModBlocks.logArray[i].getTranslationKey().substring(5);
			int randNum = getRandomIntInRange(1, 4);
			
			InputStream stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/logs/log_top_"+randNum+".png");
			BufferedImage topImg = ImageIO.read(stream);		
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/logs/log_end.png");
			BufferedImage endImg = ImageIO.read(stream);
			BufferedImage finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Graphics g;
			g = finalImg.getGraphics();
			dye(topImg, ModBlocks.woods[i].woodcolor);
			dye(endImg, ModBlocks.woods[i].plankcolor);
			g.drawImage(topImg, 0, 0, null);
			g.drawImage(endImg, 0, 0, null);
			ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+name+"_top.png"));
			
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/logs/log_"+randNum+".png");
			BufferedImage sideImg = ImageIO.read(stream);
			dye(sideImg, ModBlocks.woods[i].woodcolor);
			ImageIO.write(sideImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+name+"_side.png"));
			
			
			String leavename = ModBlocks.leaveArray[i].getTranslationKey().substring(5);
			randNum = getRandomIntInRange(1, 3);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/leaves/leaves_"+randNum+".png");
			BufferedImage leaveImg = ImageIO.read(stream);
			dye(leaveImg, ModBlocks.woods[i].leavecolor);
			ImageIO.write(leaveImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+leavename+".png"));
			
			
			String saplingname = ModBlocks.saplingArray[i].getTranslationKey().substring(5);
			randNum = getRandomIntInRange(1, 5);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/saplings/sapling_overlay_"+randNum+".png");
			BufferedImage overlay = ImageIO.read(stream);
			dye(overlay, ModBlocks.woods[i].leavecolor);
			stream = InfiniteFeatures.class.getClassLoader().getResourceAsStream("assets/infeatures/textures/block/tree/saplings/sapling_underlay_"+randNum+".png");
			BufferedImage underlay = ImageIO.read(stream);
			dye(underlay, ModBlocks.woods[i].woodcolor);
			BufferedImage sapling = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			g = sapling.getGraphics();
			g.drawImage(underlay, 0, 0, null);
			g.drawImage(overlay, 0, 0, null);
			ImageIO.write(sapling, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"+saplingname+".png"));
		}
	}
	
	public static void generateModels() throws IOException
	{
		Charset charset = Charset.forName("UTF-8");
		File blockModelFolder = new File("InfiniCraft/Resources/assets/infeatures/models/block");
		File itemModelFolder = new File("InfiniCraft/Resources/assets/infeatures/models/item");
		File blockstateFolder = new File("InfiniCraft/Resources/assets/infeatures/blockstates");
		if(blockModelFolder.exists())
		{
			FileUtils.deleteDirectory(blockModelFolder);
		}
		if(itemModelFolder.exists())
		{
			FileUtils.deleteDirectory(itemModelFolder);
		}
		if(blockstateFolder.exists())
		{
			FileUtils.deleteDirectory(blockstateFolder);
		}
		blockModelFolder.mkdirs();
		itemModelFolder.mkdirs();
		blockstateFolder.mkdirs();
		
		for(Item item : ModItems.ITEMS)
		{
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+item.getTranslationKey().substring(5)+".json");
			if (!itemModelFile.exists())
			{
				itemModelFile.createNewFile();
			}
			BufferedWriter writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			String modelInput ="{\r\n" + 
					"   \"parent\": \"item/generated"+"\" ,\r\n"
							+ "    \"textures\": { \r\n" +
							"     \"layer0\": \""+InfiniteFeatures.modID+":items/"+item.getTranslationKey().substring(5)+"\"\r\n" + 
							"} \r\n" +
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
		}
		
		for(Item item : ModItems.toolArray) 
		{
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+item.getTranslationKey().substring(5)+".json");
			BufferedWriter writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			String modelInput ="{\r\n" + 
					"   \"parent\": \"item/handheld"+"\" ,\r\n"
							+ "    \"textures\": { \r\n" +
							"     \"layer0\": \""+InfiniteFeatures.modID+":items/"+item.getTranslationKey().substring(5)+"\"\r\n" + 
							"} \r\n" +
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
		}
		
		for(Block block : ModBlocks.oreArray)
		{
			File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+".json");
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+block.getTranslationKey().substring(5)+".json");
			File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"+block.getTranslationKey().substring(5)+".json");
			
			if (!blockModelFile.exists())
				blockModelFile.createNewFile();
			if (!itemModelFile.exists())
				itemModelFile.createNewFile();
			if (!BlockstatelFile.exists())
				BlockstatelFile.createNewFile();
			
			BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
			String modelInput = "{\r\n" + 
					"   \"parent\": \"block/cube_all\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"all\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"   \"parent\": \""+InfiniteFeatures.modID+":block/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"variants\": {\r\n" + 
					"        \"normal\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" }\r\n" + 
							"    }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			
		}
		
		for(Block block : ModBlocks.leaveArray)
		{
			File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+".json");
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+block.getTranslationKey().substring(5)+".json");
			File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"+block.getTranslationKey().substring(5)+".json");
			
			if (!blockModelFile.exists())
				blockModelFile.createNewFile();
			if (!itemModelFile.exists())
				itemModelFile.createNewFile();
			if (!BlockstatelFile.exists())
				BlockstatelFile.createNewFile();
			
			BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
			String modelInput = "{\r\n" + 
					"   \"parent\": \"block/cube_all\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"all\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"   \"parent\": \""+InfiniteFeatures.modID+":block/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"variants\": {\r\n" + 
					"        \"check_decay=true,decayable=true\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" },\r\n" + 
					"        \"check_decay=true,decayable=false\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" },\r\n" + 
					"        \"check_decay=false,decayable=true\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" },\r\n" +
					"        \"check_decay=false,decayable=false\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" }\r\n" + 
							"    }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			
		}
		
		for(Block block : ModBlocks.saplingArray)
		{
			File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+".json");
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+block.getTranslationKey().substring(5)+".json");
			File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"+block.getTranslationKey().substring(5)+".json");
			
			if (!blockModelFile.exists())
				blockModelFile.createNewFile();
			if (!itemModelFile.exists())
				itemModelFile.createNewFile();
			if (!BlockstatelFile.exists())
				BlockstatelFile.createNewFile();
			
			BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
			String modelInput = "{\r\n" + 
					"   \"parent\": \"block/cross\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"cross\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"parent\": \"item/generated\",\r\n" + 
					"    \"textures\": {\r\n"+
					"		\"layer0\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"\"\r\n" +
					"     }\r\n"+		
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"variants\": {\r\n" + 
					"        \"stage=0\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" },\r\n" + 
					"        \"stage=1\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" }\r\n" + 
							"    }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			
		}
		
		for(Block block : ModBlocks.logArray)
		{
			File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+".json");
			File sideModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+"_side.json");
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+block.getTranslationKey().substring(5)+".json");
			File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"+block.getTranslationKey().substring(5)+".json");
			
			if (!blockModelFile.exists())
				blockModelFile.createNewFile();
			if (!itemModelFile.exists())
				itemModelFile.createNewFile();
			if (!BlockstatelFile.exists())
				BlockstatelFile.createNewFile();
			
			BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
			String modelInput = "{\r\n" + 
					"   \"parent\": \"block/cube_column\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"end\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"_top\",\r\n" + 
					"	    \"side\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"_side\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(sideModelFile.toPath(), charset);
			modelInput = "{\r\n" + 
					"   \"parent\": \"block/column_side\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"end\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"_top\",\r\n" + 
					"	    \"side\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"_side\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"   \"parent\": \""+InfiniteFeatures.modID+":block/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"variants\": {\r\n" + 
					"        \"axis=x\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\", \"x\": 90, \"y\": 90},\r\n"+
					"        \"axis=y\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\"},\r\n"+
					"        \"axis=z\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\", \"x\": 90},\r\n"+
   				    "        \"axis=none\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\"}\r\n"+
							"    }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			
		}
		
		for(Block block : ModBlocks.ingotblockArray)
		{
			File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"+block.getTranslationKey().substring(5)+".json");
			File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"+block.getTranslationKey().substring(5)+".json");
			File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"+block.getTranslationKey().substring(5)+".json");
			
			if (!blockModelFile.exists())
				blockModelFile.createNewFile();
			if (!itemModelFile.exists())
				itemModelFile.createNewFile();
			if (!BlockstatelFile.exists())
				BlockstatelFile.createNewFile();
			
			BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
			String modelInput = "{\r\n" + 
					"   \"parent\": \"block/cube_all\",\r\n" + 
					"   \"textures\": {\r\n" + 
					"       \"all\": \""+InfiniteFeatures.modID+":blocks/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"   }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"   \"parent\": \""+InfiniteFeatures.modID+":block/"+block.getTranslationKey().substring(5)+"\"\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
			modelInput ="{\r\n" + 
					"    \"variants\": {\r\n" + 
					"        \"normal\": { \"model\": \""+InfiniteFeatures.modID+":"+block.getTranslationKey().substring(5)+"\" }\r\n" + 
							"    }\r\n" + 
							"}";
			writer.write(modelInput);
			writer.flush();
			writer.close();
			
		}
	}
	
	private static void dye(BufferedImage image, Color color)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				Color pixelColor = new Color(image.getRGB(x, y), true);
				int r =(int)((float)pixelColor.getRed() * color.getRed() / 255);
				int g =(int)((float)pixelColor.getGreen() * color.getGreen() / 255);
				int b =(int)((float)pixelColor.getBlue() * color.getBlue() / 255);
				int a = pixelColor.getAlpha();
				int rgba = (a << 24) | (r << 16) | (g << 8) | b;
				image.setRGB(x, y, rgba);
			}
		}
	}
}