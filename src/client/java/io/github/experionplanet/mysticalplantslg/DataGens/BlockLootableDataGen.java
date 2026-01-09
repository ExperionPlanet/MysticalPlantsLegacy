package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
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

        addDrop(MPLBlocks.MYSTICAL_ORE, ore(MPLBlocks.MYSTICAL_ORE, MPLItems.MYSTICAL_DUST, 1, 1));
        addDrop(MPLBlocks.DEEPSLATE_MYSTICAL_ORE, ore(MPLBlocks.DEEPSLATE_MYSTICAL_ORE, MPLItems.MYSTICAL_DUST, 1, 1));
    }

    private LootTable.Builder ore(Block block, Item drop, int min, int max) {
        RegistryWrapper.Impl<Enchantment> implEnchant = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return dropsWithSilkTouch(
                block,
                this.applyExplosionDecay(
                        block,
                        ItemEntry.builder(drop).apply(
                                        SetCountLootFunction.builder(UniformLootNumberProvider.create(min,max)))
                                .apply(ApplyBonusLootFunction.uniformBonusCount(implEnchant.getOrThrow(Enchantments.FORTUNE)))));
    }
}
