package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLLootables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ChestLootableDataGen extends SimpleFabricLootTableProvider {
    public ChestLootableDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(MPLLootables.SOUL_BELL_LOOT,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(UniformLootNumberProvider.create(1, 5))
                                .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                                .with(ItemEntry.builder(MPLItems.SOUL_ESSENCE).weight(4))
                                .with(ItemEntry.builder(Items.BLAZE_POWDER).weight(10))
                                .with(ItemEntry.builder(Items.LEATHER).weight(15))
                                .with(ItemEntry.builder(Items.QUARTZ).weight(24))
                                .with(ItemEntry.builder(Items.GLOWSTONE_DUST).weight(20))

                        )
        );
        consumer.accept(MPLLootables.SOUL_POSSESSION_IRIS_LOOT,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.GUNPOWDER).weight(10))
                                .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                                .with(ItemEntry.builder(Items.GLOWSTONE_DUST).weight(10))
                                .with(ItemEntry.builder(MPLItems.SOUL_POLLEN).weight(10))
                        )
        );
    }
}
