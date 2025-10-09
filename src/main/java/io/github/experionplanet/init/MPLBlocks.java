package io.github.experionplanet.init;

import io.github.experionplanet.blocks.custom.*;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
    public static final Block LARGE_EXP_MUSHROOMS = register("large_exp_mushroom", (v) -> new ExpMushroomBlock(v, 1, 40), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WET_GRASS).noCollision().luminance((v) -> {if(v.get(ExpMushroomBlock.STEPPED)){return 10;}return 5;}).emissiveLighting(MPLBlocks::emissiveLightning), true);

    public static final Block BLEEDING_EXP = register("bleeding_exp", BleedingExpBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).noCollision().luminance((v) -> 5).emissiveLighting(MPLBlocks::emissiveLightning), true);
    public static final Block EXBISCUS = register("exbiscus", ExbiscusBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).noCollision().luminance((v) -> 5).emissiveLighting(MPLBlocks::emissiveLightning), true);

    public static final Block PERMAFROST_SHROOM = register("permafrost_shroom", PermafrostShroomBlock::new, AbstractBlock.Settings.create().nonOpaque().sounds(BlockSoundGroup.MOSS_CARPET).noCollision(),true);

    public static final Block BINDING_ROCK = register("binding_rock", BindingRockBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).nonOpaque(), true);
    public static final Block PEDESTAL = register("pedestal", PedestalBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).nonOpaque(), true);

    public static final Block DEBUG_TRANSLATE = register("debug_translate", DebugTranslateBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.BASALT).nonOpaque(), true);

    public static void init() {}

    private static Block register(String name, Block block, boolean regItem) {
        ExperionLogger.Print("Registering " + name);

        Block res = Registry.register(Registries.BLOCK, ExperionUtils.newId(name), block);

        if (regItem) {

            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, ExperionUtils.newId(name), blockItem);
        }

        return res;
    }

    // Making it alternate so it will easier upgrade to future version again
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean regItem) {
        Block block = blockFactory.apply(settings);

        return register(name, block, regItem);
    }


}
