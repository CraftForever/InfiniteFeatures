package com.github.craftforever.infinitefeatures.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.github.craftforever.infinitefeatures.blocks.specialevents.ISpecialEvent;
import com.github.craftforever.infinitefeatures.util.Mineral;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
public class RandomGemOre extends OreWithSpecialEvents{
	public Item dropitem;

	public RandomGemOre(Mineral imineral, Material imaterial, float ilightLevel, String itoolType, int iharvestLevel,
			float ihardness, float iresistance, SoundType isound,
			HashMap<SpecialEventTrigger, List<ISpecialEvent>> randomUniqueActions, Item drop) 
	{
		super(imineral, imaterial, ilightLevel, itoolType, iharvestLevel, ihardness, iresistance, isound, randomUniqueActions);
		this.dropitem = drop;
	}

	public static int fortuneLvl;
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		fortuneLvl = fortune;
		return dropitem;
		
	}
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) 
	{
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
	}
	
}
