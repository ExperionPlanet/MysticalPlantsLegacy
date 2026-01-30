package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBlockTags;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
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
        for (TagKey<Block> key : List.of(
                BlockTags.INCORRECT_FOR_IRON_TOOL,
                BlockTags.INCORRECT_FOR_STONE_TOOL,
                BlockTags.INCORRECT_FOR_GOLD_TOOL,
                BlockTags.INCORRECT_FOR_WOODEN_TOOL,
                BlockTags.PICKAXE_MINEABLE
        )) {
            getOrCreateTagBuilder(key).add(MPLBlocks.MYSTICAL_ORE).add(MPLBlocks.DEEPSLATE_MYSTICAL_ORE);
        }
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(MPLBlocks.PEDESTAL).add(MPLBlocks.BINDING_ROCK);
    }
}
