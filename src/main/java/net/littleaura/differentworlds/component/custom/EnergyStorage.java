package net.littleaura.differentworlds.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Objects;
import java.util.function.Consumer;

public record EnergyStorage(int max_energy, int stored_energy, String energy_type) implements TooltipProvider {

    public static final Codec<EnergyStorage> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.INT.fieldOf("max_energy").forGetter(EnergyStorage::max_energy),
                Codec.INT.optionalFieldOf("stored_energy", 0).forGetter(EnergyStorage::stored_energy),
                Codec.STRING.optionalFieldOf("energy_type", "").forGetter(EnergyStorage::energy_type)
        ).apply(builder, EnergyStorage::new);
    });
    public static final EnergyStorage EMPTY = new EnergyStorage(0,0,"");

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(Component.translatable("container.differentworlds.energy_storage.values", this.stored_energy, this.max_energy));
        if (!Objects.equals(this.energy_type, "")) {
            consumer.accept(Component.translatable("container.differentworlds.energy_storage.type", this.energy_type));
        }
    }
}