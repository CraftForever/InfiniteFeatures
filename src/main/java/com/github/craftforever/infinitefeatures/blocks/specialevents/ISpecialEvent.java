package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.RandomOre;
import com.github.craftforever.infinitefeatures.blocks.tree.RandomLog;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface ISpecialEvent 
{
	public void Execute(RandomOre block, boolean livingEntity, Entity relatedEntity, EntityLivingBase relatedLivingEntity);
	public void ExecuteLog(RandomLog block, boolean livingEntity, Entity relatedEntity, EntityLivingBase relatedLivingEntity);
}
