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

    }
}
