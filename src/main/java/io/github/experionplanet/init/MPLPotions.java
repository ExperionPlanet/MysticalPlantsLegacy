package io.github.experionplanet.init;

import io.github.experionplanet.items.tool.PotionPack;
import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import static io.github.experionplanet.items.tool.PotionPack.EffectSettings;

public class MPLPotions {
    public static final PotionPack PERMAFROST = new PotionPack("permafrost", MPLStatusEffects.PERMAFROST,
            new EffectSettings()
                    .setMain(c(10), 0)
                    .setLong(c(30), 0)
                    .setStrong(c(10), 1)
    );

    public static final PotionPack FROST_RESISTANCE = new PotionPack("frost_resistance", MPLStatusEffects.PERMAFROST,
            new EffectSettings()
                    .setMain(c(120), 0)
                    .setLong(c(360), 0)
                    .disableStrong()
    );

    public static final PotionPack POSSESSED = new PotionPack("possessed", MPLStatusEffects.POSSESSED,
            new EffectSettings()
                    .setMain(c(15), 0)
                    .setLong(c(45), 0)
                    .setStrong(c(10), 2)
    );

    public static final PotionPack PROSPERITY = new PotionPack("prosperity", MPLStatusEffects.PROSPERITY,
            new EffectSettings()
                    .setMain(c(120), 0)
                    .setLong(c(360), 0)
                    .setStrong(c(60), 3)
    );

    private static int c(int i) {
        return i * 20;
    }

    private static Potion register(String id, StatusEffectInstance instr) {

        return Registry.register(Registries.POTION, ExperionUtils.newId(id), new Potion(instr));
    }

    public static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(b -> {
            // PERMAFROST
            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    MPLBlocks.PERMAFROST_SHROOM.asItem(),
                    Registries.POTION.getEntry(PERMAFROST.MAIN)
            );
            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PERMAFROST.MAIN),
                    Items.ICE,
                    Registries.POTION.getEntry(PERMAFROST.LONG)
            );
            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PERMAFROST.MAIN),
                    MPLItems.FROST_ESSENCE,
                    Registries.POTION.getEntry(PERMAFROST.STRONG)
            );

            // FROST RESISTANCE
            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    MPLBlocks.GLACIER_PASSION_FLOWER.asItem(), // CHANGE THIS TO Glacier Passion Flower
                    Registries.POTION.getEntry(FROST_RESISTANCE.MAIN)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(FROST_RESISTANCE.MAIN),
                    MPLItems.FROST_ESSENCE,
                    Registries.POTION.getEntry(FROST_RESISTANCE.LONG)
            );

            // POSSESSED
            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    MPLItems.SOUL_POLLEN,
                    Registries.POTION.getEntry(POSSESSED.MAIN)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(POSSESSED.MAIN),
                    MPLItems.SOUL,
                    Registries.POTION.getEntry(POSSESSED.LONG)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(POSSESSED.MAIN),
                    MPLItems.SOUL_ESSENCE,
                    Registries.POTION.getEntry(POSSESSED.STRONG)
            );

            // PROSPERITY
            b.registerPotionRecipe(
                    Potions.AWKWARD,
                    MPLItems.EXP_SPORE,
                    Registries.POTION.getEntry(PROSPERITY.MAIN)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PROSPERITY.MAIN),
                    Items.EXPERIENCE_BOTTLE,
                    Registries.POTION.getEntry(PROSPERITY.LONG)
            );

            b.registerPotionRecipe(
                    Registries.POTION.getEntry(PROSPERITY.MAIN),
                    MPLItems.EXPERIENCE_ESSENCE,
                    Registries.POTION.getEntry(PROSPERITY.STRONG)
            );

        });
    }
}
