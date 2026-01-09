package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class MPLDamageTypes {
    public static final RegistryKey<DamageType> PERMAFROST = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, MysticalUtils.newId("permafrost"));

    public static void init () {}
}
