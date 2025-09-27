package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.AxisRotation;

import java.util.List;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        registerVariantRotational(MPLBlocks.SMALL_EXP_MUSHROOMS, gen);
        registerSingleModel(MPLBlocks.MEDIUM_EXP_MUSHROOMS, gen);
        registerSingleModel(MPLBlocks.LARGE_EXP_MUSHROOMS, gen);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(MPLBlocks.SMALL_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.MEDIUM_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.LARGE_EXP_MUSHROOMS.asItem(), Models.GENERATED);
    }

    private WeightedVariant WV_fromBlock(Block block) {
        return BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block));
    }

    private void registerSingleModel(Block block, BlockStateModelGenerator gen) {
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, WV_fromBlock(block)));
    }

    private void registerVariantRotational(Block block, BlockStateModelGenerator gen) {
        Identifier myModel = ModelIds.getBlockModelId(block);

        ModelVariant rot1 = BlockStateModelGenerator.createModelVariant(myModel).withRotationY(AxisRotation.R0);
        ModelVariant rot2 = BlockStateModelGenerator.createModelVariant(myModel).withRotationY(AxisRotation.R90);
        ModelVariant rot3 = BlockStateModelGenerator.createModelVariant(myModel).withRotationY(AxisRotation.R180);
        ModelVariant rot4 = BlockStateModelGenerator.createModelVariant(myModel).withRotationY(AxisRotation.R270);



        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(rot1,rot2,rot3,rot4)));
    }
}
