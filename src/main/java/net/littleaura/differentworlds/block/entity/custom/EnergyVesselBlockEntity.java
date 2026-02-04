package net.littleaura.differentworlds.block.entity.custom;

import net.littleaura.differentworlds.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyVesselBlockEntity extends BlockEntity {

    public EnergyVesselBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ENERGY_VESSEL_BLOCK_ENTITY, blockPos, blockState);
    }

}
