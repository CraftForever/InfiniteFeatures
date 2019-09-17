package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.OreWithSpecialEvents;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GasCloud implements ISpecialEvent {

	protected float radius;

	protected int 	duration;
	protected float expansion_modifier;

	protected int 	potionID;
	
	protected float effect_duration_min;
    protected float effect_duration_max;
    protected float effect_duration_mean;
	protected float effect_duration_std;
	
    protected int   effect_level_min;
    protected int   effect_level_max;
    protected float effect_level_mean;
	protected float effect_level_std;
	
	public GasCloud(float iradius, int iduration, float iexpansion_modifier, int ipotionid, float effect_duration_min, float effect_duration_max, 
	float effect_duration_mean, float effect_duration_std, 
	int effect_level_min, int effect_level_max, float effect_level_mean, float effect_level_std){
		this.radius = iradius;
		this.duration = iduration;
		this.expansion_modifier = iexpansion_modifier;
		this.potionID = ipotionid;
		this.effect_duration_min = effect_duration_min;
		this.effect_duration_max = effect_duration_max;
		this.effect_duration_mean = effect_duration_mean;
		this.effect_duration_std = effect_duration_std;
		this.effect_level_min = effect_level_min;
		this.effect_level_max = effect_level_max;
		this.effect_level_mean = effect_level_mean;
		this.effect_level_std = effect_level_std;
	}

	public GasCloud(float iradius, int iduration, float iexpansion_modifier, int ipotionid, float effect_duration, int effect_level){
		this.radius = iradius;
		this.duration = iduration;
		this.expansion_modifier = iexpansion_modifier;
		this.potionID = ipotionid;
		this.effect_duration_min = effect_duration;
		this.effect_duration_max = effect_duration;
		this.effect_duration_mean = effect_duration;
		this.effect_duration_std = 0;
		this.effect_level_min = effect_level;
		this.effect_level_max = effect_level;
		this.effect_level_mean = effect_level;
		this.effect_level_std = 0;
	}

	@Override
	public void Execute(OreWithSpecialEvents block, Entity relatedEntity, EntityLivingBase relatedLivingEntity, World world, BlockPos blockPos) 
		{
			if (world != null)
			{
				if (blockPos != null)
				{
					int potDuration = (int)Math.round(RandomHelper.getRandomGaussianInRange((double)effect_duration_mean, (double)effect_duration_std, (double)effect_duration_min, (double)effect_duration_max));
					int potLevel = (int)Math.round(RandomHelper.getRandomGaussianInRange((double)effect_level_mean, (double)effect_level_std, (double)effect_level_min, (double)effect_level_max));


					EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
					entityareaeffectcloud.setOwner(null);
					entityareaeffectcloud.setRadius(radius);
					entityareaeffectcloud.setDuration(duration);
					entityareaeffectcloud.setRadiusPerTick((expansion_modifier - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
					entityareaeffectcloud.addEffect(new PotionEffect(Potion.getPotionById(potionID), potDuration, potLevel));

					world.spawnEntity(entityareaeffectcloud);
				}
			}
	}
}