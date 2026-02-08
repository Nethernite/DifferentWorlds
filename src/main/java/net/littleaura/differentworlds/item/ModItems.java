package net.littleaura.differentworlds.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.littleaura.differentworlds.DifferentWorlds;
import net.littleaura.differentworlds.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class ModItems {

    public static final Item MOONLIT_JADE = register("moonlit_jade", Item::new, new Item.Properties());

    public static final ResourceKey<CreativeModeTab> DIFFERENT_WORLDS_CREATIVE_TAB_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(DifferentWorlds.MOD_ID, "creative_tab"));
    public static final CreativeModeTab DIFFERENT_WORLDS_CREATIVE_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.MOONLIT_JADE))
            .title(Component.translatable("itemGroup.differentworlds.all"))
            .displayItems((params, output) -> {

                output.accept(ModItems.MOONLIT_JADE);

                // The tab builder also accepts Blocks
                output.accept(ModBlocks.ENERGY_VESSEL);

                // And custom ItemStacks
                /*ItemStack stack = new ItemStack(Items.SEA_PICKLE);
                stack.set(DataComponents.ITEM_NAME, Component.literal("Pickle Rick"));
                stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal("I'm pickle riiick!!"))));
                output.accept(stack);*/
            })
            .build();

    public static <GenericItem extends Item> GenericItem register(String name, Function<Item.Properties, GenericItem> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DifferentWorlds.MOD_ID, name));

        // Create the item instance.
        GenericItem item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        DifferentWorlds.LOGGER.info("Registering ModItems for Mod: " + DifferentWorlds.MOD_ID);

        // Register the group.
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DIFFERENT_WORLDS_CREATIVE_TAB_KEY, DIFFERENT_WORLDS_CREATIVE_TAB);
    }
}