package io.github.experionplanet.init;

import io.github.experionplanet.status_effects.BasicStatusEffect;
import io.github.experionplanet.status_effects.PermafrostStatusEffect;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ColorHelper;

public class MPLStatusEffects {
    public static final RegistryEntry<StatusEffect> PERMAFROST = Registry.registerReference(Registries.STATUS_EFFECT, ExperionUtils.newId("permafrost"), new PermafrostStatusEffect());
    public static final RegistryEntry<StatusEffect> FROST_RESISTANCE = Registry.registerReference(Registries.STATUS_EFFECT, ExperionUtils.newId("frost_resistance"), new BasicStatusEffect(StatusEffectCategory.BENEFICIAL, ColorHelper.Argb.getArgb(255, 65, 106, 166)));

    public static void init() {}
}
