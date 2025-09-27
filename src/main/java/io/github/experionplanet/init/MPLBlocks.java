package io.github.experionplanet.init;

import io.github.experionplanet.blocks.ExpMushroomBlock;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Function;

public class MPLBlocks {
    private static boolean emissiveLightning(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public static final Block SMALL_EXP_MUSHROOMS = register("small_exp_mushrooms", (v) -> new ExpMushroomBlock(v, 2, 5), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WET_GRASS).noCollision().luminance((v) -> {if(v.get(ExpMushroomBlock.STEPPED)){return 10;}return 5;}).emissiveLighting(MPLBlocks::emissiveLightning), true);
    public static final Block MEDIUM_EXP_MUSHROOMS = register("medium_exp_mushroom", (v) -> new ExpMushroomBlock(v, 1, 15), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WET_GRASS).noCollision().luminance((v) -> {if(v.get(ExpMushroomBlock.STEPPED)){return 10;}return 5;}).emissiveLighting(MPLBlocks::emissiveLightning).offset(AbstractBlock.OffsetType.XZ), true);
    public static final Block LARGE_EXP_MUSHROOMS = register("large_exp_mushroom", (v) -> new ExpMushroomBlock(v, 1, 40), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WET_GRASS).noCollision().luminance((v) -> {if(v.get(ExpMushroomBlock.STEPPED)){return 10;}return 5;}).emissiveLighting(MPLBlocks::emissiveLightning).offset(AbstractBlock.OffsetType.XZ), true);


    public static void init() {}

    // Making it alternate so it will easier upgrade to future version again
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean regItem) {
        Block block = blockFactory.apply(settings);

        return register(name, block, regItem);
    }

    private static Block register(String name, Block block, boolean regItem) {
        Block res = Registry.register(Registries.BLOCK, ExperionUtils.newId(name), block);

        if (regItem) {

            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, ExperionUtils.newId(name), blockItem);
        }

        return res;
    }
}
