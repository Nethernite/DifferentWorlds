package net.littleaura.differentworlds.component;

import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.littleaura.differentworlds.DifferentWorlds;
import net.littleaura.differentworlds.component.custom.EnergyStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModDataComponentTypes {

    public static final DataComponentType<EnergyStorage> ENERGY_STORAGE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(DifferentWorlds.MOD_ID, "energy_storage"),
            DataComponentType.<EnergyStorage>builder().persistent(EnergyStorage.CODEC).build()
    );

    public static void initialize() {
        DifferentWorlds.LOGGER.info("Registering ModDataComponentsTypes for Mod: " + DifferentWorlds.MOD_ID);
        ComponentTooltipAppenderRegistry.addFirst(ModDataComponentTypes.ENERGY_STORAGE);
    }
}
