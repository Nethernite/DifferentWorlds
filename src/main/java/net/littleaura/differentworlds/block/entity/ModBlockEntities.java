package net.littleaura.differentworlds.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.littleaura.differentworlds.DifferentWorlds;
import net.littleaura.differentworlds.block.ModBlocks;
import net.littleaura.differentworlds.block.entity.custom.EnergyVesselBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final BlockEntityType<EnergyVesselBlockEntity> ENERGY_VESSEL_BLOCK_ENTITY =
            register("energy_vessel", EnergyVesselBlockEntity::new, ModBlocks.ENERGY_VESSEL);



    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(DifferentWorlds.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {

    }
}
