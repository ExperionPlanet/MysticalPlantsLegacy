package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class MPLLootables {
    public static final RegistryKey<LootTable> SOUL_BELL_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, MysticalUtils.newId("mpl/soul_bell_loot"));
    public static final RegistryKey<LootTable> SOUL_POSSESSION_IRIS_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, MysticalUtils.newId("mpl/soul_possession_iris_loot"));

    public static void init() {}
}
