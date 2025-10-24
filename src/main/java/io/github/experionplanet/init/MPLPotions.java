package io.github.experionplanet.init;

import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MPLPotions {
    public static final Potion PERMAFROST = register("permafrost",  new StatusEffectInstance(MPLStatusEffects.PERMAFROST, c(10), 0));
    public static final Potion LONG_PERMAFROST = register("long_permafrost",  new StatusEffectInstance(MPLStatusEffects.PERMAFROST, c(30), 0));
    public static final Potion STRONG_PERMAFROST = register("strong_permafrost",  new StatusEffectInstance(MPLStatusEffects.PERMAFROST, c(10), 1));

    public static final Potion FROST_RESISTANCE = register("frost_resistance",  new StatusEffectInstance(MPLStatusEffects.FROST_RESISTANCE, c(120), 0));
    public static final Potion LONG_FROST_RESISTANCE = register("long_frost_resistance",  new StatusEffectInstance(MPLStatusEffects.FROST_RESISTANCE, c(360), 0));
    private static int c(int i) {
        return i * 20;
    }

    private static Potion register(String id, StatusEffectInstance instr) {

        return Registry.register(Registries.POTION, ExperionUtils.newId(id), new Potion(instr));
    }

    public static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(b -> {
            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    MPLBlocks.PERMAFROST_SHROOM.asItem(),
                    Registries.POTION.getEntry(PERMAFROST)
            );
            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PERMAFROST),
                    Items.ICE,
                    Registries.POTION.getEntry(LONG_PERMAFROST)
            );
            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PERMAFROST),
                    MPLItems.FROST_ESSENCE,
                    Registries.POTION.getEntry(STRONG_PERMAFROST)
            );

            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    Items.BLUE_ICE, // CHANGE THIS TO Glacier Passion Flower
                    Registries.POTION.getEntry(FROST_RESISTANCE)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(FROST_RESISTANCE),
                    MPLItems.FROST_ESSENCE,
                    Registries.POTION.getEntry(LONG_FROST_RESISTANCE)
            );
        });
    }
}
