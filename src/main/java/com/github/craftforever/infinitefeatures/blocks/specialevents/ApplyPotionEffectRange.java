package com.github.craftforever.infinitefeatures.blocks.specialevents;

import com.github.craftforever.infinitefeatures.blocks.OreWithSpecialEvents;
import com.github.craftforever.infinitefeatures.helpers.RandomHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ApplyPotionEffectRange implements ISpecialEvent {

    protected int   potionID;
    protected int   duration_min;
    protected int   duration_max;
    protected float duration_mean;
    protected float duration_std;
    protected int   level_min;
    protected int   level_max;
    protected float level_mean;
    protected float level_std;
    protected boolean ambient;
    protected boolean particles;

    public ApplyPotionEffectRange(int ipotionID, int idurationTicks_min, int idurationTicks_max,
            float idurationTicks_mean, float idurationTicks_std, int iPotionLevel_min, int iPotionLevel_max,
            float iPotionLevel_mean, float iPotionLevel_std, boolean iisAmbient, boolean ihasParticles) {
        // isAmbient: Makes potion effect produce more, translucent, particles.
        this.potionID = ipotionID;
        this.duration_min = idurationTicks_min;
        this.duration_max = idurationTicks_max;
        this.duration_mean = idurationTicks_mean;
        this.duration_std = idurationTicks_std;
        this.level_min = iPotionLevel_min;
        this.level_max = iPotionLevel_max;
        this.level_mean = iPotionLevel_mean;
        this.level_std = iPotionLevel_std;
        this.ambient = iisAmbient;
        this.particles = ihasParticles;
    }

    public ApplyPotionEffectRange(int ipotionID, int idurationTicks, int ipotionLevel, boolean iisAmbient, boolean ihasParticles){
        this.potionID = ipotionID;
        this.duration_min = idurationTicks;
        this.duration_max = idurationTicks;
        this.duration_mean = idurationTicks;
        this.duration_std = 0;
        this.level_min = ipotionLevel;
        this.level_max = ipotionLevel;
        this.level_mean = ipotionLevel;
        this.level_std = 0;
        this.ambient = iisAmbient;
        this.particles = ihasParticles;
    }

    @Override
    public void Execute(OreWithSpecialEvents block, Entity relatedEntity,
    EntityLivingBase relatedLivingEntity, World world, BlockPos nullable_blockPos)
    {
        if (relatedLivingEntity != null){
            
            int duration = (int)Math.round(RandomHelper.getRandomGaussianInRange((double)duration_mean, (double)duration_std, (double)duration_min, (double)duration_max));
            int level = (int)Math.round(RandomHelper.getRandomGaussianInRange((double)level_mean, (double)level_std, (double)level_min, (double)level_max));

            PotionEffect effect = new PotionEffect(Potion.getPotionById(potionID), duration, level - 1, ambient, particles);

            relatedLivingEntity.addPotionEffect(effect);
        }

    }
}