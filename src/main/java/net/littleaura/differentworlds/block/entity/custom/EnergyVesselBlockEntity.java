package net.littleaura.differentworlds.block.entity.custom;

import net.littleaura.differentworlds.block.custom.EnergyVesselBlock;
import net.littleaura.differentworlds.block.entity.ModBlockEntities;
import net.littleaura.differentworlds.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EnergyVesselBlockEntity extends BlockEntity {

    //do NOT make final
    private EnergyStorage stored = new EnergyVesselBlockEntity.EnergyStorage(0,0,"");

    private int ticksSinceLast = 0;

    public EnergyVesselBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ENERGY_VESSEL_BLOCK_ENTITY, blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EnergyVesselBlockEntity entity) {
        boolean activated = blockState.getValue(EnergyVesselBlock.ACTIVATED);
        if (activated) {
            entity.ticksSinceLast++;
        }
        if (entity.ticksSinceLast >= 20) {

            entity.ticksSinceLast = 0;

            if (entity.stored.stored_energy > 0) {
                entity.stored.stored_energy--;
            } else {
                entity.stored.stored_energy = 0;
                level.setBlockAndUpdate(blockPos, blockState.setValue(EnergyVesselBlock.ACTIVATED, false));
            }
        }
    }

    public void resetTSL() {
        this.ticksSinceLast = 0;
    }

    static class EnergyStorage {
        public int max_energy;
        public int stored_energy;
        public String energy_type;

        EnergyStorage(int max_energy, int stored_energy, String energy_type) {
            this.max_energy = max_energy;
            this.stored_energy = stored_energy;
            this.energy_type = energy_type;
        }

        public void clear() {
            max_energy = 0;
            stored_energy = 0;
            energy_type = "";
        }
    }

    public boolean isEmpty() {
        return this.stored.stored_energy == 0;
    }

    public boolean isFull() {
        return this.stored.stored_energy == this.stored.max_energy;
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.stored.clear();
        ticksSinceLast = 0;
        net.littleaura.differentworlds.component.custom.EnergyStorage storage = valueInput.read("energy_storage", net.littleaura.differentworlds.component.custom.EnergyStorage.CODEC).orElse(net.littleaura.differentworlds.component.custom.EnergyStorage.EMPTY);
        this.stored.max_energy = storage.max_energy();
        this.stored.stored_energy = storage.stored_energy();
        this.stored.energy_type = storage.energy_type();
        ticksSinceLast = valueInput.getIntOr("tsl", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.store("energy_storage", net.littleaura.differentworlds.component.custom.EnergyStorage.CODEC, new net.littleaura.differentworlds.component.custom.EnergyStorage(this.stored.max_energy, this.stored.stored_energy, this.stored.energy_type));
        valueOutput.putInt("tsl", ticksSinceLast);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        this.stored.clear();
        net.littleaura.differentworlds.component.custom.EnergyStorage storage = dataComponentGetter.getOrDefault(ModDataComponentTypes.ENERGY_STORAGE, net.littleaura.differentworlds.component.custom.EnergyStorage.EMPTY);
        this.stored.max_energy = storage.max_energy();
        this.stored.stored_energy = storage.stored_energy();
        this.stored.energy_type = storage.energy_type();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponentTypes.ENERGY_STORAGE, new net.littleaura.differentworlds.component.custom.EnergyStorage(stored.max_energy, stored.stored_energy, stored.energy_type));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
