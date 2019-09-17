package com.github.craftforever.infinitefeatures.blocks;

import java.util.HashMap;
import java.util.List;

import com.github.craftforever.infinitefeatures.InfiniteFeatures;
import com.github.craftforever.infinitefeatures.blocks.specialevents.ISpecialEvent;
import com.github.craftforever.infinitefeatures.util.Mineral;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class OreWithSpecialEvents extends BlockBase {

    public HashMap<com.github.craftforever.infinitefeatures.blocks.RandomGemOre.SpecialEventTrigger, List<ISpecialEvent>> UniqueActions;
    public Mineral mineral;
	public String toolType;
	public Material material;
	public float lightlevel, hardness, resistance;
	public int harvestLevel;
	public SoundType sound;

    public OreWithSpecialEvents(Mineral imineral, Material imaterial, float ilightLevel, String itoolType, int iharvestLevel,
    float ihardness, float iresistance, SoundType isound,
    HashMap<com.github.craftforever.infinitefeatures.blocks.RandomGemOre.SpecialEventTrigger, List<ISpecialEvent>> randomUniqueActions) {
        super(imineral.name + "_ore",imaterial,imineral);

        setSoundType(isound);
		setHardness(ihardness);
		setResistance(iresistance);
		setHarvestLevel(itoolType, iharvestLevel);
		setLightLevel(ilightLevel);

		this.UniqueActions = randomUniqueActions;
		this.mineral = imineral;
		this.toolType = itoolType;
		this.material = imaterial;
		this.lightlevel = ilightLevel;
		this.hardness = ihardness;
		this.resistance = iresistance;
		this.harvestLevel = iharvestLevel;
		this.sound = isound;
    }

    public enum SpecialEventTrigger 
	{
		ONDESTROY, ONEXPLODEDESTROY, ONACTIVATED, ONWALKEDON, ONCLICKED, ONCOLLIDED, ONPLACED, ONFALLENON, ONLANDED,
		ONHARVESTED, ONEXPLODED, ONPLANTGROW, ONNEIGHBOURCHANGE, ONREMOVEDBYPLAYER, ONBLOCKADDED
    }
    
    private void invokeSpecialEvents(List<ISpecialEvent> events, Entity relatedEntity, EntityLivingBase relatedLivingEntity, World world, BlockPos blockPos) 
	{
		events.forEach(event -> event.Execute(this, relatedEntity, relatedLivingEntity, world, blockPos));
	}

    // #region Potential function overrides
	@Override
	public void onPlayerDestroy(World p_onPlayerDestroy_1_,BlockPos p_onPlayerDestroy_2_,IBlockState p_onPlayerDestroy_3_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONDESTROY;

		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), null, null, p_onPlayerDestroy_1_, p_onPlayerDestroy_2_);
		}
		super.onPlayerDestroy(p_onPlayerDestroy_1_, p_onPlayerDestroy_2_, p_onPlayerDestroy_3_);
	}

	@Override
	public boolean removedByPlayer(IBlockState p_removedByPlayer_1_, World p_removedByPlayer_2_,
			BlockPos p_removedByPlayer_3_, EntityPlayer p_removedByPlayer_4_, boolean p_removedByPlayer_5_) 
	{

		SpecialEventTrigger triggerName = SpecialEventTrigger.ONREMOVEDBYPLAYER;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_removedByPlayer_4_, p_removedByPlayer_4_, p_removedByPlayer_2_, p_removedByPlayer_3_);
		}
		return super.removedByPlayer(p_removedByPlayer_1_, p_removedByPlayer_2_, p_removedByPlayer_3_, p_removedByPlayer_4_,
				p_removedByPlayer_5_);
	}

	@Override
	public void onExplosionDestroy(World p_onExplosionDestroy_1_,BlockPos p_onExplosionDestroy_2_, Explosion p_onExplosionDestroy_3_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONEXPLODEDESTROY;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onExplosionDestroy_3_.getExplosivePlacedBy(), p_onExplosionDestroy_3_.getExplosivePlacedBy(), p_onExplosionDestroy_1_, p_onExplosionDestroy_2_);
		}
		super.onExplosionDestroy(p_onExplosionDestroy_1_, p_onExplosionDestroy_2_, p_onExplosionDestroy_3_);
	}

	@Override
	public boolean onBlockActivated(World p_onBlockActivated_1_,BlockPos p_onBlockActivated_2_, IBlockState p_onBlockActivated_3_, EntityPlayer p_onBlockActivated_4_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_,
			float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONACTIVATED;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onBlockActivated_4_, p_onBlockActivated_4_, p_onBlockActivated_1_, p_onBlockActivated_2_);
		}
		return super.onBlockActivated(p_onBlockActivated_1_, p_onBlockActivated_2_, p_onBlockActivated_3_, p_onBlockActivated_4_, p_onBlockActivated_5_, p_onBlockActivated_6_, p_onBlockActivated_7_, p_onBlockActivated_8_, p_onBlockActivated_9_);
	}

	@Override
	public void onEntityWalk(World p_onEntityWalk_1_, BlockPos p_onEntityWalk_2_, Entity p_onEntityWalk_3_) 
	{

		SpecialEventTrigger triggerName = SpecialEventTrigger.ONWALKEDON;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onEntityWalk_3_, null, p_onEntityWalk_1_, p_onEntityWalk_2_);
		}
		super.onEntityWalk(p_onEntityWalk_1_, p_onEntityWalk_2_, p_onEntityWalk_3_);
	}

	@Override
	public void onBlockClicked(World p_onBlockClicked_1_,BlockPos p_onBlockClicked_2_,EntityPlayer p_onBlockClicked_3_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONCLICKED;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onBlockClicked_3_, p_onBlockClicked_3_, p_onBlockClicked_1_, p_onBlockClicked_2_);   
		}
		super.onBlockClicked(p_onBlockClicked_1_, p_onBlockClicked_2_, p_onBlockClicked_3_);
	}

	@Override
	public void onEntityCollision(World p_onEntityCollision_1_,BlockPos p_onEntityCollision_2_,IBlockState p_onEntityCollision_3_,
			Entity p_onEntityCollision_4_) 
	{
        
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONCOLLIDED;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onEntityCollision_4_, null, p_onEntityCollision_1_, p_onEntityCollision_2_);
		}
		super.onEntityCollision(p_onEntityCollision_1_,p_onEntityCollision_2_,p_onEntityCollision_3_,p_onEntityCollision_4_);
	}

	@Override
	public void onBlockPlacedBy(World p_onBlockPlacedBy_1_,BlockPos p_onBlockPlacedBy_2_,
			IBlockState p_onBlockPlacedBy_3_, EntityLivingBase p_onBlockPlacedBy_4_, ItemStack p_onBlockPlacedBy_5_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONPLACED;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onBlockPlacedBy_4_, p_onBlockPlacedBy_4_, p_onBlockPlacedBy_1_, p_onBlockPlacedBy_2_);
		}
		super.onBlockPlacedBy(p_onBlockPlacedBy_1_,p_onBlockPlacedBy_2_,p_onBlockPlacedBy_3_,p_onBlockPlacedBy_4_,p_onBlockPlacedBy_5_);
	}

	@Override
	public void onFallenUpon(World p_onFallenUpon_1_, BlockPos p_onFallenUpon_2_, Entity p_onFallenUpon_3_,
			float p_onFallenUpon_4_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONFALLENON;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onFallenUpon_3_, null, p_onFallenUpon_1_, p_onFallenUpon_2_);
		}
		super.onFallenUpon(p_onFallenUpon_1_, p_onFallenUpon_2_, p_onFallenUpon_3_,p_onFallenUpon_4_);
	}

	@Override
	public void onLanded(World p_onLanded_1_, Entity p_onLanded_2_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONLANDED;
		p_onLanded_2_.motionY = 0.0D;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onLanded_2_, null, p_onLanded_1_, null);
		}
		super.onLanded(p_onLanded_1_, p_onLanded_2_);
	}

	@Override
	public void onBlockHarvested(World p_onBlockHarvested_1_,BlockPos p_onBlockHarvested_2_,
			IBlockState p_onBlockHarvested_3_,EntityPlayer p_onBlockHarvested_4_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONHARVESTED;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onBlockHarvested_4_, p_onBlockHarvested_4_, p_onBlockHarvested_1_, p_onBlockHarvested_2_);
		}
		super.onBlockHarvested(p_onBlockHarvested_1_, p_onBlockHarvested_2_, p_onBlockHarvested_3_, p_onBlockHarvested_4_);
	}

	@Override
	public void onBlockExploded(World p_onBlockExploded_1_,BlockPos p_onBlockExploded_2_, Explosion p_onBlockExploded_3_) {

		SpecialEventTrigger triggerName = SpecialEventTrigger.ONEXPLODED;
		if (UniqueActions.containsKey(triggerName)) {
			invokeSpecialEvents(UniqueActions.get(triggerName), p_onBlockExploded_3_.getExplosivePlacedBy(), p_onBlockExploded_3_.getExplosivePlacedBy(), p_onBlockExploded_1_, p_onBlockExploded_2_);
		}
		super.onBlockExploded(p_onBlockExploded_1_, p_onBlockExploded_2_,p_onBlockExploded_3_);
	}

	@Override
	public void onPlantGrow(IBlockState p_onPlantGrow_1_, World p_onPlantGrow_2_, BlockPos p_onPlantGrow_3_,
			BlockPos p_onPlantGrow_4_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONPLANTGROW;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), null, null, p_onPlantGrow_2_, p_onPlantGrow_4_);
		}
		super.onPlantGrow(p_onPlantGrow_1_,p_onPlantGrow_2_,p_onPlantGrow_3_,p_onPlantGrow_4_);
	}

	@Override
	public void onNeighborChange(IBlockAccess p_onNeighborChange_1_, BlockPos p_onNeighborChange_2_, BlockPos p_onNeighborChange_3_) 
	{
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONNEIGHBOURCHANGE;
		if (UniqueActions.containsKey(triggerName)) 
		{
			invokeSpecialEvents(UniqueActions.get(triggerName), null, null, null, p_onNeighborChange_2_);
		}
		super.onNeighborChange(p_onNeighborChange_1_, p_onNeighborChange_2_, p_onNeighborChange_3_);
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		SpecialEventTrigger triggerName = SpecialEventTrigger.ONBLOCKADDED;
		
			invokeSpecialEvents(UniqueActions.get(triggerName), null, null, worldIn, pos);
        
        super.onBlockAdded(worldIn, pos, state);
    }
    // #endregion
    
   

    @Override
	public void registerModels() 
	{
		InfiniteFeatures.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}