package com.github.craftforever.infinitefeatures.init;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.blocks.RandomBlock;
import com.github.craftforever.infinitefeatures.blocks.RandomBlock.SpecialEvent;
import com.github.craftforever.infinitefeatures.blocks.RandomBlock.SpecialEventTrigger;
import com.github.craftforever.infinitefeatures.util.Mineral;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import scala.tools.nsc.backend.icode.analysis.CopyPropagation.Const;

import java.awt.*;

import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.*;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomIntInRange;


public class RandomFactory {

    private static final int RGB_MAX = 255;

    // TODO: allow users to customise min/max values via config file
    private static final int LIGHTLEVEL_MAX = 15;
    private static final int LIGHTLEVEL_MIN = 1;
    private static final double LIGHTLEVEL_GLOW_PROBABILITY = 0.1D;
    private static final double SLIPPERINESS_PROBABILITY = 0.1D;
    private static final int SLIPPERINESS_MAX = 4;
    private static final int SLIPPERINESS_MIN = 0;
    private static final int HARDNESS_MIN = 1;
    private static final int HARDNESS_MAX = 10;
    private static final double BLAST_RESISTANCE_MEAN = 15.0D;
    private static final double BLAST_RESISTANCE_STD = 5.0D;
    private static final double BLAST_RESISTANCE_MIN = 0.0D;
    private static final double BLAST_RESISTANCE_MAX = 6000.0D;

    private static final int HARVEST_LEVEL_MIN = 0;
    private static final int HARVEST_LEVEL_MAX = 3;

    public static RandomBlock randomBlockFactory(Mineral imineral)
    {
        // TODO: randomly pick a material
        Material randomMaterial = Material.ROCK;
        // ...

        float randomLightLevel = 0F;
        if (getRandomBoolean((float)LIGHTLEVEL_GLOW_PROBABILITY))
        {
            // The ore will glow
            randomLightLevel = (float)getRandomIntInRange(LIGHTLEVEL_MIN,LIGHTLEVEL_MAX) / 15;
        }
        else {
            // The ore won't glow
            randomLightLevel = 0F;
        }
        
        // TODO: pick tool type based off the base texture, (sand/dirt base textures probably makes sense to use a shovel)
        // Depending on the direction/extent you want to take the randomisation this could be generated randomly although that would make for poor experiences
        String randomToolType = "pickaxe";
        // ...
        
        int randomHarvestLevel = getRandomIntInRange(
        		HARVEST_LEVEL_MIN,
                HARVEST_LEVEL_MAX);

        // How long it takes to mine
        float randomHardness = (float)getRandomIntInRange(
                HARDNESS_MIN,
                HARDNESS_MAX);

        // Blast resistance
        float randomBlastResistance = (float)getRandomGaussianInRange(
        		BLAST_RESISTANCE_MEAN,
        		BLAST_RESISTANCE_STD,
        		BLAST_RESISTANCE_MIN,
        		BLAST_RESISTANCE_MAX);
        
        
        // TODO: pick a sound type randomly or based on something
        SoundType randomSoundType = SoundType.STONE;
        //...

        float randomSlipperiness = 0F;
        if (getRandomBoolean((float)SLIPPERINESS_PROBABILITY))
        {
            randomSlipperiness = (float)getRandomIntInRange(SLIPPERINESS_MIN,SLIPPERINESS_MAX);
        }
        else {
            randomSlipperiness = 0F;
        }
                
        SpecialEvent randomEvent = SpecialEvent.RANDOM_LIGHTLEVEL;

        SpecialEventTrigger randomTrigger = SpecialEventTrigger.ONCOLLIDED;
        
        RandomBlock randomBlock = new RandomBlock(
                imineral,
                randomMaterial,
        		randomLightLevel,
        		randomToolType,
        		randomHarvestLevel,
        		randomHardness,
        		randomBlastResistance,
                randomSoundType,
                randomSlipperiness,
                randomEvent,
                randomTrigger);
        
        return randomBlock;
    }

    public static Mineral randomMineralFactory(String[] textpartarray)
    {
    	String randomName =
        		textpartarray[getRandomIntInRange(0,textpartarray.length)]
                + textpartarray[getRandomIntInRange(0,textpartarray.length)]
                + textpartarray[getRandomIntInRange(0,textpartarray.length)]
                + textpartarray[getRandomIntInRange(0,textpartarray.length)];
          
        Color randomColor = new Color(
            InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
            InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
            InfiniteFeatures.seededRandom.nextInt(RGB_MAX));

        Mineral randomMineral = new Mineral(
            randomName, 
            randomColor);
        
        return randomMineral;
    }


}
