package io.github.experionplanet.init;

import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MPLItemGroup {
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), ExperionUtils.newId("mystical_plants_tab"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MPLBlocks.SMALL_EXP_MUSHROOMS))
            .displayName(Text.literal("Mystical Plants ").append(Text.literal("(Legacy)").formatted(Formatting.BOLD).formatted(Formatting.DARK_PURPLE)))
            .build();


    public static void init() {
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(MPLBlocks.SMALL_EXP_MUSHROOMS);
            itemGroup.add(MPLBlocks.MEDIUM_EXP_MUSHROOMS);
            itemGroup.add(MPLBlocks.LARGE_EXP_MUSHROOMS);
            itemGroup.add(MPLBlocks.BLEEDING_EXP);
            itemGroup.add(MPLBlocks.EXBISCUS);
            itemGroup.add(MPLBlocks.PERMAFROST_SHROOM);
            itemGroup.add(MPLBlocks.GLACIER_PASSION_FLOWER);
            itemGroup.add(MPLBlocks.FROST_UMBRELLA_FLOWER);
            itemGroup.add(MPLBlocks.DISGUISE_ORCHID);
            itemGroup.add(MPLBlocks.BOGSPORE_CAP);
            itemGroup.add(MPLBlocks.HUNGERBALM);

            itemGroup.add(MPLItems.EXPERIENCE_ESSENCE);
            itemGroup.add(MPLItems.FROST_ESSENCE);
            itemGroup.add(MPLItems.BOGGED_ESSENCE);

            itemGroup.add(MPLItems.EXPERIENCE_PICKAXE);
            itemGroup.add(MPLItems.FROST_AXE);
            itemGroup.add(MPLItems.BOGGED_SHOVEL);

            itemGroup.add(MPLBlocks.BINDING_ROCK);
            itemGroup.add(MPLBlocks.PEDESTAL);

            itemGroup.add(MPLBlocks.PERMAFROSTED_LOG);

            itemGroup.add(MPLItems.BROKEN_EXPERIENCE_PICKAXE);
            itemGroup.add(MPLItems.BROKEN_FROST_AXE);
            itemGroup.add(MPLItems.GUIDE_BOOK);
        });
    }
}
