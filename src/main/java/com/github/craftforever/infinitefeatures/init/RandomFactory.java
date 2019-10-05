package com.github.craftforever.infinitefeatures.init;

import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomBoolean;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomFloatInRange;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomGaussianInRange;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomIntInRange;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.getRandomItem;
import static com.github.craftforever.infinitefeatures.helpers.RandomHelper.randomEnum;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.blocks.RandomOre;
import com.github.craftforever.infinitefeatures.blocks.RandomOre.SpecialEventTrigger;
import com.github.craftforever.infinitefeatures.blocks.specialevents.ApplyPotionEffectRange;
import com.github.craftforever.infinitefeatures.blocks.specialevents.ApplyPotionEffectRangeRandomly;
import com.github.craftforever.infinitefeatures.blocks.specialevents.ISpecialEvent;
import com.github.craftforever.infinitefeatures.blocks.tree.RandomLog;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;
import com.github.craftforever.infinitefeatures.util.Mineral;
import com.github.craftforever.infinitefeatures.util.Wood;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class RandomFactory 
{

    private static final int RGB_MAX = 255;
    
    
    //QUALITY_LEVEL_AMMOUNT is better if it can divide InfiniteFeatures.ORE_QTY 
    private static final int QUALITY_LEVEL_AMMOUNT = 5;

    // TODO: allow users to customise min/max values via config file
    private static final int LIGHTLEVEL_MAX = 15;
    private static final int LIGHTLEVEL_MIN = 1;
    private static final double LIGHTLEVEL_GLOW_PROBABILITY = 0.1D;
    private static final int HARDNESS_MIN = 1;
    private static final int HARDNESS_MAX = 10;
    private static final double BLAST_RESISTANCE_MEAN = 15.0D;
    private static final double BLAST_RESISTANCE_STD = 5.0D;
    private static final double BLAST_RESISTANCE_MIN = 0.0D;
    private static final double BLAST_RESISTANCE_MAX = 6000.0D;

    private static final int HARVEST_LEVEL_MIN = 0;
    private static final int HARVEST_LEVEL_MAX = 3;

    // ALL_POSSIBLE_EVENT_PARAMETERS (THIS SHOULD ABSOLUTLY BE MOVED SOMEWHERE ELSE)
    private static final int POT_ID_MIN = 1;
    private static final int POT_ID_MAX = 32;
    private static final int POT_DURATION_MIN = 0;
    private static final int POT_DURATION_MAX = 600;
    private static final int POT_LEVEL_MIN = 1;
    private static final int POT_LEVEL_MAX = 2;
    private static final float POT_AMBIENT_PROBABILITY = 0.2f;
    private static final float POT_PARTICLES_PROBABILITY = 0.9f;
    private static final float POT_TRIGGER_PROBABILITY_MAX = 1f;
    private static final float POT_TRIGGER_PROBABILITY_MIN = 0f;

    private static List<ISpecialEvent> GenerateAllPossibleEvents()
    {
        List<ISpecialEvent> allEvents = new ArrayList<ISpecialEvent>();

        // the block will exhibit a potion effect with all of its stats fixed and the effect will proc every time
        allEvents.add(new ApplyPotionEffectRange(getRandomIntInRange(POT_ID_MIN, POT_ID_MAX),
                getRandomIntInRange(POT_DURATION_MIN, POT_DURATION_MAX),
                getRandomIntInRange(POT_LEVEL_MIN, POT_LEVEL_MAX), 
                getRandomBoolean(POT_AMBIENT_PROBABILITY),
                getRandomBoolean(POT_PARTICLES_PROBABILITY)));
        // the block will exhibit a potion effect with all of its stats fixed and the effect will proc some of the time 
        allEvents.add(new ApplyPotionEffectRangeRandomly(getRandomIntInRange(POT_ID_MIN, POT_ID_MAX),
                getRandomIntInRange(POT_DURATION_MIN, POT_DURATION_MAX),
                getRandomIntInRange(POT_LEVEL_MIN, POT_LEVEL_MAX),
                getRandomBoolean(POT_AMBIENT_PROBABILITY),
                getRandomBoolean(POT_PARTICLES_PROBABILITY),
                getRandomFloatInRange(POT_TRIGGER_PROBABILITY_MIN, POT_TRIGGER_PROBABILITY_MAX)));
        // the block will exhibit a potion effect with all of its stats randomised (different lengths, etc) and the effect will proc every time
        allEvents.add(new ApplyPotionEffectRange(
                getRandomIntInRange(POT_ID_MIN, POT_ID_MAX), 
                POT_DURATION_MIN, 
                POT_DURATION_MAX, 
                getRandomFloatInRange(POT_DURATION_MIN, POT_DURATION_MAX), 
                getRandomFloatInRange(0, POT_DURATION_MAX), 
                POT_LEVEL_MIN, 
                POT_LEVEL_MAX, 
                getRandomFloatInRange(POT_LEVEL_MIN, POT_LEVEL_MAX), 
                getRandomFloatInRange(0, POT_DURATION_MAX), 
                getRandomBoolean(POT_AMBIENT_PROBABILITY),
                getRandomBoolean(POT_PARTICLES_PROBABILITY)));
        // the block will exhibit a potion effect with all of its stats randomised (different lengths, etc) and the effect will proc some of the time
        allEvents.add(new ApplyPotionEffectRangeRandomly(
            getRandomIntInRange(POT_ID_MIN, POT_ID_MAX), 
            POT_DURATION_MIN, 
            POT_DURATION_MAX, 
            getRandomFloatInRange(POT_DURATION_MIN, POT_DURATION_MAX), 
            getRandomFloatInRange(0, POT_DURATION_MAX), 
            POT_LEVEL_MIN, 
            POT_LEVEL_MAX, 
            getRandomFloatInRange(POT_LEVEL_MIN, POT_LEVEL_MAX), 
            getRandomFloatInRange(0, POT_DURATION_MAX), 
            getRandomBoolean(POT_AMBIENT_PROBABILITY),
            getRandomBoolean(POT_PARTICLES_PROBABILITY),
            getRandomFloatInRange(POT_TRIGGER_PROBABILITY_MIN, POT_TRIGGER_PROBABILITY_MAX)));

        return allEvents;
    }

    public static RandomOre randomOreFactory(Mineral imineral, Material imaterial, Item itemdrop) {
        // TODO: randomly pick a material
        // ...

        float randomLightLevel = 0F;
        if (getRandomBoolean((float) LIGHTLEVEL_GLOW_PROBABILITY)) {
            // The ore will glow
            randomLightLevel = (float)getRandomIntInRange(LIGHTLEVEL_MIN,LIGHTLEVEL_MAX) / 15;
        }
        else 
        {
            // The ore won't glow
            randomLightLevel = 0F;
        }

        // TODO: pick tool type based off the base texture, (sand/dirt base textures
        // probably makes sense to use a shovel)
        // Depending on the direction/extent you want to take the randomisation this
        // could be generated randomly although that would make for poor experiences
        String randomToolType;
        if(imineral.material==Material.ROCK) 
        {
        	randomToolType = "pickaxe";
        }
        else 
        {
        	randomToolType = "shovel";
        }
        // ...

        int randomHarvestLevel = getRandomIntInRange(HARVEST_LEVEL_MIN, HARVEST_LEVEL_MAX);

        // How long it takes to mine
        float randomHardness = (float) getRandomIntInRange(HARDNESS_MIN, HARDNESS_MAX);

        // Blast resistance
        float randomBlastResistance = (float) getRandomGaussianInRange(BLAST_RESISTANCE_MEAN, BLAST_RESISTANCE_STD,
                BLAST_RESISTANCE_MIN, BLAST_RESISTANCE_MAX);

        SoundType randomSoundType = null;
        if(imineral.material==Material.GROUND) 
        {
        	randomSoundType = SoundType.GROUND;
        }
        else if(imineral.material==Material.SAND) 
        {
        	randomSoundType = SoundType.SAND;
        }
        else if(imineral.material==Material.ROCK) 
        {
        	randomSoundType = SoundType.STONE;
        }
        // ...

        // Initialize the mappings between event triggers and events
        HashMap<SpecialEventTrigger, List<ISpecialEvent>> randomUniqueActions = new HashMap<SpecialEventTrigger, List<ISpecialEvent>>();

        for (SpecialEventTrigger trigger : SpecialEventTrigger.values())
        {
            List<ISpecialEvent> events = new ArrayList<ISpecialEvent>();
            randomUniqueActions.put(trigger, events);
        }

        // Assign the TestEvent to a random trigger
        SpecialEventTrigger randomTrigger = randomEnum(SpecialEventTrigger.class);

        List<ISpecialEvent> allPossibleEvents = GenerateAllPossibleEvents();

        ISpecialEvent selectedEvent = RandomHelper.getRandomItem(allPossibleEvents);

        randomUniqueActions.get(randomTrigger).add(selectedEvent);
        
        RandomOre randomBlock = new RandomOre(imineral, imaterial, randomLightLevel, randomToolType,
                randomHarvestLevel, randomHardness, randomBlastResistance, randomSoundType, randomUniqueActions, itemdrop);

        return randomBlock;
    }

    public static RandomLog randomLogFactory(Wood iwood) 
    {
    	float randomLightLevel = 0F;
        if (getRandomBoolean((float) LIGHTLEVEL_GLOW_PROBABILITY)) 
        {
            // The ore will glow
            randomLightLevel = (float)getRandomIntInRange(LIGHTLEVEL_MIN,LIGHTLEVEL_MAX) / 15;
        }
        else 
        {
            // The ore won't glow
            randomLightLevel = 0F;
        }

        // TODO: pick tool type based off the base texture, (sand/dirt base textures
        // probably makes sense to use a shovel)
        // Depending on the direction/extent you want to take the randomisation this
        // could be generated randomly although that would make for poor experiences
        int randomHarvestLevel = getRandomIntInRange(HARVEST_LEVEL_MIN, HARVEST_LEVEL_MAX);

        // How long it takes to mine
        float randomHardness = (float) getRandomIntInRange(HARDNESS_MIN, HARDNESS_MAX);

        // Initialize the mappings between event triggers and events
        HashMap<com.github.craftforever.infinitefeatures.blocks.RandomOre.SpecialEventTrigger, List<ISpecialEvent>> randomUniqueActions = new HashMap<com.github.craftforever.infinitefeatures.blocks.RandomOre.SpecialEventTrigger, List<ISpecialEvent>>();

        for (SpecialEventTrigger trigger : SpecialEventTrigger.values())
        {
            List<ISpecialEvent> events = new ArrayList<ISpecialEvent>();
            randomUniqueActions.put(trigger, events);
        }

        // Assign the TestEvent to a random trigger
        SpecialEventTrigger randomTrigger = randomEnum(SpecialEventTrigger.class);

        List<ISpecialEvent> allPossibleEvents = GenerateAllPossibleEvents();

        ISpecialEvent selectedEvent = RandomHelper.getRandomItem(allPossibleEvents);

        randomUniqueActions.get(randomTrigger).add(selectedEvent);
    	
    	RandomLog randomBlock = new RandomLog(iwood, randomLightLevel, randomHarvestLevel, randomHardness, randomUniqueActions);
    	
    	
    	
    	return randomBlock;
    }
    
    public static Mineral randomMineralFactory(String[] one_consonants, String[] two_consonants, String[] vowels,
                                               int quality) {
        String randomName;

        String firstSyllable;
        String secondSyllable;
        String thirdSyllable;
        String fourthSyllable;
        String fifthSyllable;
        String ending;

        boolean fourthSyllableChance = getRandomBoolean(0.5F);
        boolean fifthSyllableChance = getRandomBoolean(0.5F);
        boolean endingChance = getRandomBoolean(0.5F);

        List<String> one_consonants_list = Arrays.asList(one_consonants);
        List<String> two_consonants_list = Arrays.asList(two_consonants);
        List<String> vowels_list = Arrays.asList(vowels);

        // First syllable
        if (getRandomBoolean(0.5F)) {
            firstSyllable = "";
        } else {
            if (getRandomBoolean(0.5F)) {
                firstSyllable = getRandomItem(one_consonants_list);
            } else {
                firstSyllable = getRandomItem(two_consonants_list);
            }
        }

        // Second syllable
        secondSyllable = getRandomItem(vowels_list);

        // Third syllable
        if (getRandomBoolean(0.5F)) {
            thirdSyllable = getRandomItem(one_consonants_list);
        } else {
            thirdSyllable = getRandomItem(two_consonants_list);
        }

        // Fourth syllable
        if (fourthSyllableChance) {
            fourthSyllable = "";
        } else {
            fourthSyllable = getRandomItem(vowels_list);
        }

        // Fifth syllable
        if (fifthSyllableChance) {
            fifthSyllable = "";
        } else {
            if (getRandomBoolean(0.5F)) {
                fifthSyllable = getRandomItem(one_consonants_list);
            } else {
                fifthSyllable = getRandomItem(two_consonants_list);
            }
        }

        // Ending
        if (endingChance) {
            ending = "";
        } else {
            if (getRandomBoolean(0.5F)) {
                ending = "ite";
            } else {
                ending = "ium";
            }
        }

        randomName = firstSyllable + secondSyllable + thirdSyllable + fourthSyllable + fifthSyllable + ending;

        Color randomColor = new Color(InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
                InfiniteFeatures.seededRandom.nextInt(RGB_MAX), InfiniteFeatures.seededRandom.nextInt(RGB_MAX));
        Mineral randomMineral = new Mineral(randomName, randomColor,quality/(InfiniteFeatures.ORE_QTY/QUALITY_LEVEL_AMMOUNT)+1);

        return randomMineral;
    }

    public static Wood randomWoodFactory(String[] one_consonants, String[] two_consonants, String[] vowels) 
    {
    	String randomName;

        String firstSyllable;
        String secondSyllable;
        String thirdSyllable;
        String fourthSyllable;
        String fifthSyllable;

        boolean fourthSyllableChance = getRandomBoolean(0.5F);
        boolean fifthSyllableChance = getRandomBoolean(0.5F);

        List<String> one_consonants_list = Arrays.asList(one_consonants);
        List<String> two_consonants_list = Arrays.asList(two_consonants);
        List<String> vowels_list = Arrays.asList(vowels);

        // First syllable
        if (getRandomBoolean(0.5F)) {
            firstSyllable = "";
        } else {
            if (getRandomBoolean(0.5F)) {
                firstSyllable = getRandomItem(one_consonants_list);
            } else {
                firstSyllable = getRandomItem(two_consonants_list);
            }
        }

        // Second syllable
        secondSyllable = getRandomItem(vowels_list);

        // Third syllable
        if (getRandomBoolean(0.5F)) {
            thirdSyllable = getRandomItem(one_consonants_list);
        } else {
            thirdSyllable = getRandomItem(two_consonants_list);
        }

        // Fourth syllable
        if (fourthSyllableChance) {
            fourthSyllable = "";
        } else {
            fourthSyllable = getRandomItem(vowels_list);
        }

        // Fifth syllable
        if (fifthSyllableChance) {
            fifthSyllable = "";
        } else {
            if (getRandomBoolean(0.5F)) {
                fifthSyllable = getRandomItem(one_consonants_list);
            } else {
                fifthSyllable = getRandomItem(two_consonants_list);
            }
        }
        
        randomName = firstSyllable + secondSyllable + thirdSyllable + fourthSyllable + fifthSyllable;
        
        Color randomColor = new Color(InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
                InfiniteFeatures.seededRandom.nextInt(RGB_MAX), InfiniteFeatures.seededRandom.nextInt(RGB_MAX));
        Color leaveColor = new Color(InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
                InfiniteFeatures.seededRandom.nextInt(RGB_MAX), InfiniteFeatures.seededRandom.nextInt(RGB_MAX));
        Color plankColor = new Color(InfiniteFeatures.seededRandom.nextInt(RGB_MAX),
                InfiniteFeatures.seededRandom.nextInt(RGB_MAX), InfiniteFeatures.seededRandom.nextInt(RGB_MAX));
        Wood randomWood = new Wood(randomName, randomColor, leaveColor, plankColor);

        return randomWood;
    }
}
    
