package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.OreWithSpecialEvents;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Explode implements ISpecialEvent {

    protected float strength_min;
    protected float strength_max;
    protected float strength_mean;
	protected float strength_std;
	protected float damagesTerrain_prob;

	public Explode(float strength_min, float strength_max, float strength_mean, float strength_std, float damagesTerrain_prob)
	{
		this.strength_min = strength_min;
		this.strength_max = strength_max;
		this.strength_mean = strength_mean;
		this.strength_std = strength_std;
		this.damagesTerrain_prob = damagesTerrain_prob;
	}

	public Explode(float strength, boolean damagesTerrain)
	{
		this.strength_min = strength;
		this.strength_max = strength;
		this.strength_mean = strength;
		this.strength_std = 0;
		if (damagesTerrain)
		{
			this.damagesTerrain_prob = 1;
		}
		else
		{
			this.damagesTerrain_prob = 0;
		}
	}

	@Override
	public void Execute(OreWithSpecialEvents block, Entity nullable_relatedEntity,
			EntityLivingBase nullable_relatedLivingEntity, World nullable_world, BlockPos nullable_blockPos) {
		if (nullable_world != null){ 
			if (nullable_blockPos != null)
			{
				
				float strength = (float)RandomHelper.getRandomGaussianInRange(strength_mean, strength_std, strength_min, strength_max);
				boolean damagesTerrain = RandomHelper.getRandomBoolean(damagesTerrain_prob);
				nullable_world.setBlockToAir(nullable_blockPos);
				nullable_world.createExplosion(null, nullable_blockPos.getX(), nullable_blockPos.getY(), nullable_blockPos.getZ(), strength, damagesTerrain);
			}
		}
	}
	
}