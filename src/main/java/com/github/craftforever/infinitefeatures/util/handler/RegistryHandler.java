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
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
	if (InfiniteFeatures.continueRandomGeneration == true) {
	    event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
	if (InfiniteFeatures.continueRandomGeneration == true) {
	    ModBlocks.initarray();
	    event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}

    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
	if (InfiniteFeatures.continueRandomGeneration == true) {
	    for (Item item : ModItems.ITEMS) {
		if (item instanceof IHasModel)
		    ((IHasModel) item).registerModels();
	    }
	    for (Block block : ModBlocks.BLOCKS) {
		if (block instanceof IHasModel)
		    ((IHasModel) block).registerModels();
	    }
	}
    }

    public static void generateLangFile() throws IOException {
	Charset charset = Charset.forName("UTF-8");
	File langFolder = new File("InfiniCraft/Resources/assets/infeatures/lang");
	langFolder.mkdirs();
	File packMcmeta = new File("InfiniCraft/Resources/pack.mcmeta");
	if (!packMcmeta.exists())
	    packMcmeta.createNewFile();

	File langFile = new File("InfiniCraft/Resources/assets/infeatures/lang/en_us.lang");
	if (!langFile.exists()) {
	    langFile.createNewFile();
	} else {
	    langFile.delete();
	    langFile.createNewFile();
	}
	String packInput = "{\r\n" + "  \"pack\": {\r\n" + "    \"pack_format\": 3,\r\n"
		+ "    \"description\": \"0.0.2\"\r\n" + "  }\r\n" + "}";
	BufferedWriter writer = Files.newBufferedWriter(langFile.toPath(), charset);
	for (Block block : ModBlocks.BLOCKS) {
	    String blockName = block.getTranslationKey().substring(5);
	    blockName = blockName.replace("_", " ");
	    blockName = WordUtils.capitalize(blockName);
	    String langinput = block.getTranslationKey() + ".name=" + blockName + "\n";
	    writer.write(langinput);
	    writer.flush();
	}
	for (Item item : ModItems.ITEMS) {
	    String itemName = item.getTranslationKey().substring(5);
	    itemName = itemName.replace("_", " ");
	    itemName = WordUtils.capitalize(itemName);
	    String langinput = item.getTranslationKey() + ".name=" + itemName + "\n";
	    writer.write(langinput);
	    writer.flush();
	}
	writer.close();
	writer = Files.newBufferedWriter(packMcmeta.toPath(), charset);
	writer.write(packInput);
	writer.flush();
	writer.close();
    }

    public static void generateTextures() throws IOException {
	File blockTextureFolder = new File("InfiniCraft/Resources/assets/infeatures/textures/blocks");
	if (blockTextureFolder.exists())
	    FileUtils.deleteDirectory(blockTextureFolder);
	blockTextureFolder.mkdirs();
	File itemTextureFolder = new File("InfiniCraft/Resources/assets/infeatures/textures/items");
	if (itemTextureFolder.exists())
	    FileUtils.deleteDirectory(itemTextureFolder);
	itemTextureFolder.mkdirs();
	/*
	 * InputStream streambases =
	 * InfiniteFeatures.class.getClassLoader().getResourceAsStream(
	 * "assets/infeatures/textures/block/base"); BufferedReader reader = new
	 * BufferedReader(new InputStreamReader(streambases)); List<String> bases = new
	 * ArrayList<String>(); int baseammount = 0; while(reader.ready()){ baseammount
	 * ++; String line = reader.readLine(); bases.add(line); }
	 */
	// System.out.print(reader.readLine());
	/*
	 * InputStream streamores =
	 * InfiniteFeatures.class.getClassLoader().getResourceAsStream(
	 * "assets/infeatures/textures/block/ore"); reader = new BufferedReader(new
	 * InputStreamReader(streamores)); List<String> ores = new ArrayList<String>();
	 * int oreammount = 0; while( reader.ready() ) { oreammount ++; String line =
	 * reader.readLine(); ores.add(line); }
	 */
	// Creating Textures for the Ores and Ingots
	int i = 0;
	for (Block block : ModBlocks.BLOCKS) {
	    InputStream stream = InfiniteFeatures.class.getClassLoader()
		    .getResourceAsStream("assets/infeatures/textures/block/base/stone.png");
	    BufferedImage baseImg = ImageIO.read(stream);
	    stream = InfiniteFeatures.class.getClassLoader()
		    .getResourceAsStream("assets/infeatures/textures/block/ore/generic.png");
	    BufferedImage oreImg = ImageIO.read(stream);
	    BufferedImage finalImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	    Graphics g = finalImg.getGraphics();
	    g.drawImage(baseImg, 0, 0, null);
	    Color color = ModBlocks.minerals[i].color;
	    dye(oreImg, color);
	    g.drawImage(oreImg, 0, 0, null);
	    stream = InfiniteFeatures.class.getClassLoader()
		    .getResourceAsStream("assets/infeatures/textures/item/ingot/generic.png");
	    BufferedImage ingotImg = ImageIO.read(stream);
	    dye(ingotImg, color);
	    ImageIO.write(ingotImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/items/"
		    + block.getRegistryName().toString().substring(11, block.getRegistryName().toString().length() - 4)
		    + "_ingot".trim() + ".png"));
	    ImageIO.write(finalImg, "PNG", new File("InfiniCraft/Resources/assets/infeatures/textures/blocks/"
		    + block.getTranslationKey().substring(5) + ".png"));
	    i++;
	}
    }

    public static void generateModels() throws IOException {
	Charset charset = Charset.forName("UTF-8");
	File blockModelFolder = new File("InfiniCraft/Resources/assets/infeatures/models/block");
	File itemModelFolder = new File("InfiniCraft/Resources/assets/infeatures/models/item");
	File blockstateFolder = new File("InfiniCraft/Resources/assets/infeatures/blockstates");
	if (blockModelFolder.exists()) {
	    FileUtils.deleteDirectory(blockModelFolder);
	}
	if (itemModelFolder.exists()) {
	    FileUtils.deleteDirectory(itemModelFolder);
	}
	if (blockstateFolder.exists()) {
	    FileUtils.deleteDirectory(blockstateFolder);
	}
	blockModelFolder.mkdirs();
	itemModelFolder.mkdirs();
	blockstateFolder.mkdirs();

	for (Item item : ModItems.ITEMS) {
	    File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"
		    + item.getTranslationKey().substring(5) + ".json");
	    if (!itemModelFile.exists()) {
		itemModelFile.createNewFile();
	    }
	    BufferedWriter writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
	    String modelInput = "{\r\n" + "   \"parent\": \"item/generated" + "\" ,\r\n" + "    \"textures\": { \r\n"
		    + "     \"layer0\": \"" + InfiniteFeatures.modID + ":items/" + item.getTranslationKey().substring(5)
		    + "\"\r\n" + "} \r\n" + "}";
	    writer.write(modelInput);
	    writer.flush();
	    writer.close();
	}

	for (Block block : ModBlocks.BLOCKS) {
	    File blockModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/block/"
		    + block.getTranslationKey().substring(5) + ".json");
	    File itemModelFile = new File("InfiniCraft/Resources/assets/infeatures/models/item/"
		    + block.getTranslationKey().substring(5) + ".json");
	    File BlockstatelFile = new File("InfiniCraft/Resources/assets/infeatures/blockstates/"
		    + block.getTranslationKey().substring(5) + ".json");

	    if (!blockModelFile.exists())
		blockModelFile.createNewFile();
	    if (!itemModelFile.exists())
		itemModelFile.createNewFile();
	    if (!BlockstatelFile.exists())
		BlockstatelFile.createNewFile();

	    BufferedWriter writer = Files.newBufferedWriter(blockModelFile.toPath(), charset);
	    String modelInput = "{\r\n" + "   \"parent\": \"block/cube_all\",\r\n" + "   \"textures\": {\r\n"
		    + "       \"all\": \"" + InfiniteFeatures.modID + ":blocks/"
		    + block.getTranslationKey().substring(5) + "\"\r\n" + "   }\r\n" + "}";
	    writer.write(modelInput);
	    writer.flush();
	    writer.close();
	    writer = Files.newBufferedWriter(itemModelFile.toPath(), charset);
	    modelInput = "{\r\n" + "   \"parent\": \"" + InfiniteFeatures.modID + ":block/"
		    + block.getTranslationKey().substring(5) + "\"\r\n" + "}";
	    writer.write(modelInput);
	    writer.flush();
	    writer.close();
	    writer = Files.newBufferedWriter(BlockstatelFile.toPath(), charset);
	    modelInput = "{\r\n" + "    \"variants\": {\r\n" + "        \"normal\": { \"model\": \""
		    + InfiniteFeatures.modID + ":" + block.getTranslationKey().substring(5) + "\" }\r\n" + "    }\r\n"
		    + "}";
	    writer.write(modelInput);
	    writer.flush();
	    writer.close();

	}
    }

    private static void dye(BufferedImage image, Color color) {
	for (int x = 0; x < image.getWidth(); x++) {
	    for (int y = 0; y < image.getHeight(); y++) {
		Color pixelColor = new Color(image.getRGB(x, y), true);
		int r = (int) ((float) pixelColor.getRed() * color.getRed() / 255);
		int g = (int) ((float) pixelColor.getGreen() * color.getGreen() / 255);
		int b = (int) ((float) pixelColor.getBlue() * color.getBlue() / 255);
		int a = pixelColor.getAlpha();
		int rgba = (a << 24) | (r << 16) | (g << 8) | b;
		image.setRGB(x, y, rgba);
	    }
	}
    }
}