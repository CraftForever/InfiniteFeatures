package com.github.craftforever.infinitefeatures.blocks.specialevents;

import javax.annotation.Nullable;

import com.github.craftforever.infinitefeatures.blocks.OreWithSpecialEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpecialEvent 
{
	/**
	 * Interface to implement when adding a random functionaility to a block.
	 * <p>
	 * You will regret not checking for nulls
	 * </p>
	 *
	 * @param  block instance of the block that called Execute.
	 * @param  nullable_relatedEntity if the event fired has reference to an Entity it will pass this, else null.
	 * @param  nullable_relatedLivingEntity if the event fired has reference to an LivingEntity it will pass this, else null.
	 * @param  nullable_world if the event fired has reference to the world it will pass this, else null.
	 */
	public void Execute(OreWithSpecialEvents block, @Nullable Entity relatedEntity, @Nullable EntityLivingBase relatedLivingEntity, @Nullable World world, 
	@Nullable BlockPos blockPos);
}
