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

    public static final Block SMALL_EXP_MUSHROOMS = register("small_exp_mushrooms", (v) -> new ExpMushroomBlock(v, 2, 5), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WET_GRASS).noCollision().luminance((v) -> {if(v.get(ExpMushroomBlock.STEPPED)){return 10;}return 5;}).emissiveLighting((v1, v2, v3) -> true), true);

    public static void init() {}

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean regItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (regItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, ExperionUtils.newId(name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, ExperionUtils.newId(name));
    }
}
