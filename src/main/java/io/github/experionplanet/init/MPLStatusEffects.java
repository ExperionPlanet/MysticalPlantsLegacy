package io.github.experionplanet.init;

import io.github.experionplanet.StatusEffects.PermafrostStatusEffect;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class MPLStatusEffects {
    public static final RegistryEntry<StatusEffect> PERMAFROST = Registry.registerReference(Registries.STATUS_EFFECT, ExperionUtils.newId("permafrost"), new PermafrostStatusEffect());

    public static void init() {}
}
