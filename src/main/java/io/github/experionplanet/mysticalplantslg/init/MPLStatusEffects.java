package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.status_effects.BasicStatusEffect;
import io.github.experionplanet.mysticalplantslg.status_effects.PermafrostStatusEffect;
import io.github.experionplanet.mysticalplantslg.status_effects.PossessedStatusEffect;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ColorHelper;

public class MPLStatusEffects {
    public static final RegistryEntry<StatusEffect> PERMAFROST = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("permafrost"), new PermafrostStatusEffect());
    public static final RegistryEntry<StatusEffect> FROST_RESISTANCE = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("frost_resistance"), new BasicStatusEffect(StatusEffectCategory.BENEFICIAL, ColorHelper.Argb.getArgb(255, 65, 106, 166)));
    public static final RegistryEntry<StatusEffect> POSSESSED = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("possessed"), new PossessedStatusEffect());
    public static final RegistryEntry<StatusEffect> PROSPERITY = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("prosperity"), new BasicStatusEffect(StatusEffectCategory.BENEFICIAL, ColorHelper.Argb.getArgb(135, 221, 35)));
    public static final RegistryEntry<StatusEffect> VOID = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("void"), new BasicStatusEffect(StatusEffectCategory.HARMFUL, ColorHelper.Argb.getArgb(125, 23, 159)));
    public static final RegistryEntry<StatusEffect> ROOTED = Registry.registerReference(Registries.STATUS_EFFECT, MysticalUtils.newId("rooted"), new BasicStatusEffect(StatusEffectCategory.HARMFUL, ColorHelper.Argb.getArgb(152, 116, 8)));

    public static void init() {}
}
