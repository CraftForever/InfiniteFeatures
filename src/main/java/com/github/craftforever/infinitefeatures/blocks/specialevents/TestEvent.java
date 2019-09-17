package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.OreWithSpecialEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestEvent implements ISpecialEvent {
	@Override
	public void Execute(OreWithSpecialEvents block, Entity relatedEntity,
			EntityLivingBase relatedLivingEntity, World world, BlockPos nullable_blockPos) {
				block.setLightLevel(1f);

	}
}