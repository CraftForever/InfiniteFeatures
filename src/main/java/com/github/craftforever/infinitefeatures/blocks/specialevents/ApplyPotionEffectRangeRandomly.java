
package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.RandomOre;
import com.github.craftforever.infinitefeatures.blocks.tree.RandomLog;

import com.github.craftforever.infinitefeatures.helpers.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ApplyPotionEffectRangeRandomly extends ApplyPotionEffectRange implements ISpecialEvent {

    protected float probability;

    public ApplyPotionEffectRangeRandomly(int ipotionID, int idurationTicks, int ipotionLevel, boolean iisAmbient, boolean ihasParticles, float iprobability)
    {
        super(ipotionID, idurationTicks, ipotionLevel, iisAmbient, ihasParticles);
        this.probability = iprobability;
    }

    public ApplyPotionEffectRangeRandomly(int ipotionID, int idurationTicks_min, int idurationTicks_max,
            float idurationTicks_mean, float idurationTicks_std, int iPotionLevel_min, int iPotionLevel_max,
            float iPotionLevel_mean, float iPotionLevel_std, boolean iisAmbient, boolean ihasParticles,
            float iprobability) {
        super(ipotionID, idurationTicks_min, idurationTicks_max, idurationTicks_mean, idurationTicks_std,
                iPotionLevel_min, iPotionLevel_max, iPotionLevel_mean, iPotionLevel_std, iisAmbient, ihasParticles);
        this.probability = iprobability;
    }

    

    @Override
    public void Execute(RandomOre block, boolean livingEntity, Entity relatedEntity,
            EntityLivingBase relatedLivingEntity) {
        if (RandomHelper.getRandomBoolean(probability)) {
            super.Execute(block, livingEntity, relatedEntity, relatedLivingEntity);
        }
    }
    
    @Override
    public void ExecuteLog(RandomLog block, boolean livingEntity, Entity relatedEntity,
            EntityLivingBase relatedLivingEntity) {
        if (RandomHelper.getRandomBoolean(probability)) {
            super.ExecuteLog(block, livingEntity, relatedEntity, relatedLivingEntity);
        }
    }
}