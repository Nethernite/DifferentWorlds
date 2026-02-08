package net.littleaura.differentworlds.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnergyStorage(int max_energy, int stored_energy, String energy_type) {

    public static final Codec<EnergyStorage> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.INT.fieldOf("max_energy").forGetter(EnergyStorage::max_energy),
                Codec.INT.optionalFieldOf("stored_energy", 0).forGetter(EnergyStorage::stored_energy),
                Codec.STRING.optionalFieldOf("energy_type", "").forGetter(EnergyStorage::energy_type)
        ).apply(builder, EnergyStorage::new);
    });
}