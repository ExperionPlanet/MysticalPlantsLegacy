package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.render.model.json.WeightedVariant;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        registerSingleModel(MPLBlocks.SMALL_EXP_MUSHROOMS, gen);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(MPLBlocks.SMALL_EXP_MUSHROOMS.asItem(), Models.GENERATED);
    }

    public void registerSingleModel(Block block, BlockStateModelGenerator gen) {
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block))));
    }
}
