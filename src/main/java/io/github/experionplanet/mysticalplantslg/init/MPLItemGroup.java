package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class  MPLItemGroup {
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), MysticalUtils.newId("mystical_plants_tab"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MPLBlocks.SMALL_EXP_MUSHROOMS))
            .displayName(Text.literal("Mystical Plants Legacy"))
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
            itemGroup.add(MPLBlocks.SOUL_POSSESSION_IRIS);
            itemGroup.add(MPLBlocks.SOUL_PITCHER);
            itemGroup.add(MPLBlocks.SOUL_BELL);
            itemGroup.add(MPLBlocks.VOID_MUSHROOM);
            itemGroup.add(MPLBlocks.SHULKURA);
            itemGroup.add(MPLBlocks.VOID_STRAWFLOWER);

            itemGroup.add(MPLItems.EXPERIENCE_ESSENCE);
            itemGroup.add(MPLItems.FROST_ESSENCE);
            itemGroup.add(MPLItems.BOGGED_ESSENCE);
            itemGroup.add(MPLItems.SOUL_ESSENCE);
            itemGroup.add(MPLItems.VOID_ESSENCE);

            itemGroup.add(MPLItems.EXPERIENCE_PICKAXE);
            itemGroup.add(MPLItems.FROST_AXE);
            itemGroup.add(MPLItems.BOGGED_SHOVEL);
            itemGroup.add(MPLItems.SOUL_HOE);
            itemGroup.add(MPLItems.VOID_SWORD);

            itemGroup.add(MPLItems.MYSTICAL_DUST);
            itemGroup.add(MPLItems.RAW_MYSTICAL);
            itemGroup.add(MPLItems.MYSTICAL_INGOT);
            itemGroup.add(MPLItems.MYSTICAL_STAR_ESSENCE);
            itemGroup.add(MPLItems.MYSTICAL_BOTTLE);
            itemGroup.add(MPLItems.MYSTICAL_SPLASH);
            itemGroup.add(MPLBlocks.MYSTICAL_ORE);
            itemGroup.add(MPLBlocks.DEEPSLATE_MYSTICAL_ORE);
            itemGroup.add(MPLItems.EXP_SPORE);
            itemGroup.add(MPLItems.PERMAFROST_SNOWFLAKE);
            itemGroup.add(MPLItems.PERMAFROST_SNOWBALL);
            itemGroup.add(MPLBlocks.PERMAFROST_SNOW_BLOCK);
            itemGroup.add(MPLItems.BOG_CAP);
            itemGroup.add(MPLItems.BOG_FERTILIZER);
            itemGroup.add(MPLItems.SOUL);
            itemGroup.add(MPLItems.SOUL_POLLEN);
            itemGroup.add(MPLItems.VOID_CAP);
            itemGroup.add(MPLItems.VOID_ROOT);
            itemGroup.add(MPLItems.PROSPERITY_POTION);
            itemGroup.add(MPLItems.PROSPERITY_POTION_LONG);
            itemGroup.add(MPLItems.FROST_RESISTANCE_POTION);
            itemGroup.add(MPLItems.PERMAFROST_SPLASH);
            itemGroup.add(MPLItems.POSSESSION_SPLASH);
            itemGroup.add(MPLItems.VOID_SPLASH);
            itemGroup.add(MPLItems.ROOTED_SPLASH);

            itemGroup.add(MPLBlocks.PEDESTAL);

            /*
            if (MPLConfig.show_debug_item) {
                itemGroup.add(MPLBlocks.PERMAFROSTED_LOG);
                itemGroup.add(MPLItems.BROKEN_EXPERIENCE_PICKAXE);
                itemGroup.add(MPLItems.BROKEN_FROST_AXE);
                itemGroup.add(MPLItems.BROKEN_BOGGED_SHOVEL);
                itemGroup.add(MPLItems.BROKEN_SOUL_HOE);
                itemGroup.add(MPLItems.BROKEN_VOID_SWORD);
                itemGroup.add(MPLItems.GUIDE_BOOK);
                itemGroup.add(MPLBlocks.BINDING_ROCK);
            }*/
        });
    }
}
