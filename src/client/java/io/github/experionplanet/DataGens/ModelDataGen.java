package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

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
        registerSingleModel(MPLBlocks.BLEEDING_EXP, gen);

    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(MPLBlocks.SMALL_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.MEDIUM_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.LARGE_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.BLEEDING_EXP.asItem(), Models.GENERATED);
    }

    private void registerSingleModel(Block block, BlockStateModelGenerator gen) {
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
    }

    private void registerVariantRotational(Block block, BlockStateModelGenerator gen) {
        Identifier myModel = ModelIds.getBlockModelId(block);

        gen.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, new BlockStateVariant[]
                        {
                                BlockStateVariant.create().put(VariantSettings.MODEL, myModel),
                                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R90),
                                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R180),
                                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R270),

                        }
                )
        );
    }
}
