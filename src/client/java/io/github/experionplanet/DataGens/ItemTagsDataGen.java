package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlockTags;
import io.github.experionplanet.init.MPLItemTags;
import io.github.experionplanet.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagsDataGen extends FabricTagProvider<Item> {
    public ItemTagsDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MPLItemTags.DIRTS);
        getOrCreateTagBuilder(MPLItemTags.SOIL_FILLING).add(Blocks.DIRT.asItem(), Blocks.SOUL_SOIL.asItem(), Blocks.COARSE_DIRT.asItem(), Blocks.MUD.asItem()).addTag(MPLItemTags.DIRTS);
        getOrCreateTagBuilder(MPLItemTags.SOUL_FILLING).add(MPLItems.SOUL,MPLItems.SOUL_POLLEN,MPLItems.SOUL_ESSENCE);

        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(
                        MPLItems.VOID_SWORD,
                        MPLItems.FROST_AXE,
                        MPLItems.EXPERIENCE_PICKAXE,
                        MPLItems.BOGGED_SHOVEL,
                        MPLItems.SOUL_HOE
                );

        getOrCreateTagBuilder(ItemTags.SWORDS).add(MPLItems.VOID_SWORD);
        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE).add(MPLItems.VOID_SWORD);

        getOrCreateTagBuilder(ItemTags.MINING_LOOT_ENCHANTABLE).add(MPLItems.EXPERIENCE_PICKAXE);
        getOrCreateTagBuilder(ItemTags.MINING_ENCHANTABLE).add(MPLItems.EXPERIENCE_PICKAXE);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(MPLItems.EXPERIENCE_PICKAXE);
        getOrCreateTagBuilder(MPLItemTags.ESSENCES).add(
                MPLItems.EXPERIENCE_ESSENCE,
                MPLItems.SOUL_ESSENCE,
                MPLItems.FROST_ESSENCE,
                MPLItems.VOID_ESSENCE,
                MPLItems.BOGGED_ESSENCE
        );

    }
}
