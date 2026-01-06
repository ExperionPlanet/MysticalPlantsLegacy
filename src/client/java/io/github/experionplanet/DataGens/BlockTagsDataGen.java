package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockTagsDataGen extends FabricTagProvider<Block> {

    public BlockTagsDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MPLBlockTags.DIRTS);
        getOrCreateTagBuilder(MPLBlockTags.SOIL_CHANGING).addTag(MPLBlockTags.DIRTS).add(Blocks.DIRT).add(Blocks.COARSE_DIRT).add(Blocks.ROOTED_DIRT).add(Blocks.MUD).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(MPLBlockTags.SOUL_PLANT_SOIL).add(Blocks.SOUL_SOIL, Blocks.SOUL_SAND);
    }
}
