package net.littleaura.differentworlds.block.entity.custom;

import net.littleaura.differentworlds.block.entity.ModBlockEntities;
import net.littleaura.differentworlds.component.ModDataComponentTypes;
import net.littleaura.differentworlds.component.custom.EnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class EnergyVesselBlockEntity extends BlockEntity {

    private final EnergyStorage stored = new EnergyStorage(0,0,"");

    public EnergyVesselBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ENERGY_VESSEL_BLOCK_ENTITY, blockPos, blockState);
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

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.stored.clear();
        Optional<net.littleaura.differentworlds.component.custom.EnergyStorage> storage = valueInput.read("energy_storage", net.littleaura.differentworlds.component.custom.EnergyStorage.CODEC);
        this.stored.max_energy = storage.get().max_energy();
        this.stored.stored_energy = storage.get().stored_energy();
        this.stored.energy_type = storage.get().energy_type();
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.store("energy_storage", net.littleaura.differentworlds.component.custom.EnergyStorage.CODEC, new net.littleaura.differentworlds.component.custom.EnergyStorage(this.stored.max_energy, this.stored.stored_energy, this.stored.energy_type));
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
}
