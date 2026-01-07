package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootableDataGen extends FabricBlockLootTableProvider {
    public BlockLootableDataGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(MPLBlocks.SMALL_EXP_MUSHROOMS);
        addDrop(MPLBlocks.MEDIUM_EXP_MUSHROOMS);
        addDrop(MPLBlocks.LARGE_EXP_MUSHROOMS);
        addDrop(MPLBlocks.BLEEDING_EXP);
        addDrop(MPLBlocks.EXBISCUS);
        addDrop(MPLBlocks.PERMAFROST_SHROOM);
        addDrop(MPLBlocks.GLACIER_PASSION_FLOWER);
        addDrop(MPLBlocks.FROST_UMBRELLA_FLOWER);
        addDrop(MPLBlocks.BOGSPORE_CAP);
        addDrop(MPLBlocks.DISGUISE_ORCHID);
        addDrop(MPLBlocks.HUNGERBALM);
        addDrop(MPLBlocks.SOUL_PITCHER);
        addDrop(MPLBlocks.VOID_CAP);
        addDrop(MPLBlocks.PEDESTAL);


    }
}
