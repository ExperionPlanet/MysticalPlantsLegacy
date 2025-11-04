package io.github.experionplanet.DataGens;

import io.github.experionplanet.blocks.custom.DisguiseOrchidBlock;
import io.github.experionplanet.init.MPLBlockProperties;
import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

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
        registerBlooming(MPLBlocks.EXBISCUS, gen, true);

        registerBlooming(MPLBlocks.GLACIER_PASSION_FLOWER, gen, true);
        registerSingleModel(MPLBlocks.FROST_UMBRELLA_FLOWER, gen);
        registerMultiShroom(MPLBlocks.PERMAFROST_SHROOM, gen, 5, false);
        registerMultiShroom(MPLBlocks.BOGSPORE_CAP, gen, 3, true);

        registerSingleModel(MPLBlocks.SOUL_POSSESSION_IRIS, gen);
        registerBlooming(MPLBlocks.SOUL_PITCHER, gen, true);

        registerBlockShifting(MPLBlocks.DISGUISE_ORCHID, gen, ModelIds.getBlockModelId(Blocks.BLUE_ORCHID), ModelIds.getBlockModelId(MPLBlocks.DISGUISE_ORCHID), DisguiseOrchidBlock.REVEALED);
        registerBlooming(MPLBlocks.HUNGERBALM, gen, false);

        registerSingleModel(MPLBlocks.BINDING_ROCK, gen);
        registerSingleModel(MPLBlocks.DEBUG_TRANSLATE, gen);
        registerSingleModel(MPLBlocks.PEDESTAL, gen);
        gen.registerSimpleCubeAll(MPLBlocks.PERMAFROSTED_LOG);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(MPLBlocks.SMALL_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.MEDIUM_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.LARGE_EXP_MUSHROOMS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.BLEEDING_EXP.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.EXBISCUS.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.PERMAFROST_SHROOM.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.GLACIER_PASSION_FLOWER.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.FROST_UMBRELLA_FLOWER.asItem(), Models.GENERATED);
        gen.register(MPLItems.GUIDE_BOOK, Models.GENERATED);
        gen.register(MPLBlocks.BOGSPORE_CAP.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.DISGUISE_ORCHID.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.HUNGERBALM.asItem(), Models.GENERATED);
        gen.register(MPLBlocks.SOUL_POSSESSION_IRIS.asItem(), Models.GENERATED);
        gen.register(MPLItems.SOUL, Models.GENERATED);
        gen.register(MPLItems.SOUL_POLLEN, Models.GENERATED);
        gen.register(MPLBlocks.SOUL_PITCHER.asItem(), Models.GENERATED);
        gen.register(MPLItems.EXP_SPORE, Models.GENERATED);

    }

    private void registerBlockShifting(Block block, BlockStateModelGenerator gen, Identifier model0, Identifier model1, BooleanProperty prop) {

        gen.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(prop, false), BlockStateVariant.create().put(VariantSettings.MODEL, model0))
                .with(When.create().set(prop, true), BlockStateVariant.create().put(VariantSettings.MODEL, model1))
        );
    }

    private void registerSingleModel(Block block, BlockStateModelGenerator gen) {
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
    }

    private void registerBlooming(Block block, BlockStateModelGenerator gen, boolean rotational) {
        Identifier myModel0 = ModelIds.getBlockSubModelId(block, "0");
        Identifier myModel1 = ModelIds.getBlockSubModelId(block, "1");

        gen.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(MPLBlockProperties.BLOOMING, false), getRotationalOrNot(myModel0, rotational))
                .with(When.create().set(MPLBlockProperties.BLOOMING, true), getRotationalOrNot(myModel1, rotational))
        );

    }

    private void registerVariantRotational(Block block, BlockStateModelGenerator gen) {
        Identifier myModel = ModelIds.getBlockModelId(block);

        gen.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, getRotationalOrNot(myModel, true)));
    }

    private void registerMultiShroom(Block block, BlockStateModelGenerator gen, int capAmount, boolean rotational) {
        Identifier mainModel = ModelIds.getBlockModelId(block);
        final IntProperty prop = MPLBlockProperties.CAP_REMAINING;
        MultipartBlockStateSupplier supply = MultipartBlockStateSupplier.create(block).with(
                When.create().set(prop, capAmount), getRotationalOrNot(mainModel, rotational)
        );

        for (int i = 1; i <= capAmount; i++) {
            Identifier model = ModelIds.getBlockSubModelId(block, "_" + i);
            supply = supply.with(When.create().set(prop, capAmount - i), getRotationalOrNot(model, rotational));
        }

        gen.blockStateCollector.accept(supply);
    }

    private BlockStateVariant[] getRotationalOrNot(Identifier myModel, boolean on) {
        if (!on) {
            return new BlockStateVariant[]{BlockStateVariant.create().put(VariantSettings.MODEL, myModel)};
        }
        return new BlockStateVariant[] {
                BlockStateVariant.create().put(VariantSettings.MODEL, myModel),
                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R90),
                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R180),
                BlockStateVariant.create().put(VariantSettings.MODEL, myModel).put(VariantSettings.Y, VariantSettings.Rotation.R270)
        };
    }
}
