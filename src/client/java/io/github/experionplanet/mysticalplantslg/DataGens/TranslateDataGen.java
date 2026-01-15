package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLPotions;
import io.github.experionplanet.mysticalplantslg.utils.ExperionTranslate;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TranslateDataGen extends FabricLanguageProvider {

    public TranslateDataGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder gen) {
        ExperionTranslate mainTr = new ExperionTranslate(gen);

        mainTr.trBlock(MPLBlocks.SMALL_EXP_MUSHROOMS);
        mainTr.trBlock(MPLBlocks.MEDIUM_EXP_MUSHROOMS);
        mainTr.trBlock(MPLBlocks.LARGE_EXP_MUSHROOMS);
        mainTr.trBlock(MPLBlocks.BLEEDING_EXP);
        mainTr.trBlock(MPLBlocks.EXBISCUS);
        mainTr.trItem(MPLItems.EXPERIENCE_ESSENCE);
        mainTr.trItem(MPLItems.FROST_AXE);
        mainTr.trItem(MPLItems.FROST_ESSENCE);
        mainTr.trItem(MPLItems.EXPERIENCE_PICKAXE);
        mainTr.trItem(MPLItems.BOGGED_ESSENCE);
        mainTr.trItem(MPLItems.BOGGED_SHOVEL);
        mainTr.trItem(MPLItems.BROKEN_EXPERIENCE_PICKAXE);
        mainTr.trItem(MPLItems.BROKEN_FROST_AXE);
        mainTr.trItem(MPLBlocks.BOGSPORE_CAP.asItem());
        mainTr.trBlock(MPLBlocks.PERMAFROST_SHROOM);
        mainTr.trBlock(MPLBlocks.FROST_UMBRELLA_FLOWER);
        mainTr.trBlock(MPLBlocks.DISGUISE_ORCHID);
        mainTr.trBlock(MPLBlocks.HUNGERBALM);
        mainTr.trItem(MPLItems.SOUL_ESSENCE);
        mainTr.trItem(MPLItems.SOUL_HOE);
        mainTr.trBlock(MPLBlocks.SOUL_POSSESSION_IRIS);
        mainTr.trItem(MPLItems.SOUL_POLLEN);
        mainTr.trItem(MPLItems.SOUL);
        mainTr.trItem(MPLItems.VOID_SWORD);
        mainTr.trBlock(MPLBlocks.SOUL_PITCHER);
        mainTr.trItem(MPLItems.EXP_SPORE);
        mainTr.trItem(MPLItems.VOID_ESSENCE);
        mainTr.trBlock(MPLBlocks.VOID_MUSHROOM);
        mainTr.trBlock(MPLBlocks.SHULKURA);
        mainTr.trBlock(MPLBlocks.VOID_STRAWFLOWER);
        mainTr.trBlock(MPLBlocks.SOUL_BELL);
        mainTr.trItem(MPLItems.MYSTICAL_DUST);
        mainTr.trItem(MPLItems.MYSTICAL_INGOT);
        mainTr.trItem(MPLItems.RAW_MYSTICAL);
        mainTr.trItem(MPLItems.MYSTICAL_STAR_ESSENCE);
        mainTr.trBlock(MPLBlocks.MYSTICAL_ORE);
        mainTr.trBlock(MPLBlocks.DEEPSLATE_MYSTICAL_ORE);
        mainTr.trItem(MPLItems.PERMAFROST_SNOWFLAKE);
        mainTr.trItem(MPLItems.SOUL_ZOMBIE_SPAWN_EGG);
        mainTr.trItem(MPLItems.VOID_SPLASH);

        mainTr.trBlock(MPLBlocks.PERMAFROSTED_LOG);

        mainTr.trBlock(MPLBlocks.GLACIER_PASSION_FLOWER);

        mainTr.trBlock(MPLBlocks.BINDING_ROCK);
        mainTr.trBlock(MPLBlocks.PEDESTAL);

        mainTr.trItem(MPLItems.BROKEN_BOGGED_SHOVEL);
        mainTr.trItem(MPLItems.BROKEN_SOUL_HOE);
        mainTr.trItem(MPLItems.BROKEN_VOID_SWORD);

        mainTr.trItem(MPLItems.PERMAFROST_SNOWBALL);
        mainTr.trBlock(MPLBlocks.PERMAFROST_SNOW_BLOCK);

        mainTr.trItem(MPLItems.PROSPERITY_POTION);
        mainTr.trCopyItem(MPLItems.PROSPERITY_POTION, MPLItems.PROSPERITY_POTION_LONG);

        mainTr.trItem(MPLItems.FROST_RESISTANCE_POTION);
        mainTr.trItem(MPLItems.MYSTICAL_BOTTLE);
        mainTr.trItem(MPLItems.MYSTICAL_SPLASH);
        mainTr.trItem(MPLItems.ROOTED_SPLASH);
        mainTr.trItem(MPLItems.PERMAFROST_SPLASH);
        mainTr.trItem(MPLItems.POSSESSION_SPLASH);
        mainTr.trItem(MPLItems.BOG_CAP);
        mainTr.trItem(MPLItems.BOG_FERTILIZER);
        mainTr.trItem(MPLItems.VOID_CAP);
        mainTr.trItem(MPLItems.VOID_ROOT);

        gen.add("mysticalplantslg.tooltip.experience_pickaxe_1", "Exp Bursts");
        gen.add("mysticalplantslg.tooltip.experience_pickaxe_2", "When bar is full, Right-Click to burst out the exp");

        gen.add("mysticalplantslg.tooltip.frost_axe_1", "Permafrost Timber");
        gen.add("mysticalplantslg.tooltip.frost_axe_2", "20% chance to frost nearby logs and instantly break them");

        gen.add("mysticalplantslg.tooltip.bogged_shovel_1", "Bog Excavator");
        gen.add("mysticalplantslg.tooltip.bogged_shovel_2", "Feed dirt into the shovel so it can dig 1x3 blocks");
        gen.add("mysticalplantslg.tooltip.bogged_shovel_3", "Dirt Changer");
        gen.add("mysticalplantslg.tooltip.bogged_shovel_4", "Can change target block to target dirt on mode");

        gen.add("mysticalplantslg.tooltip.void_sword_1", "Void Stacking");
        gen.add("mysticalplantslg.tooltip.void_sword_2", "35% chance applies Void effect onto mobs");
        gen.add("mysticalplantslg.tooltip.void_sword_3", "(stacks up to 3) which deal more damage with any attack");

        gen.add("mysticalplantslg.tooltip.soul_hoe_1", "Soul Growing");
        gen.add("mysticalplantslg.tooltip.soul_hoe_2", "Right click + Sneak to grow nearby crops");


        gen.add(MPLBlocks.DEBUG_TRANSLATE, "D3bug Tr4nsl4t3!1");

        ExperionTranslate effectsTr = new ExperionTranslate("effect.mysticalplantslg", gen);
        effectsTr.trBase("permafrost");
        effectsTr.trBase("frost_resistance");
        effectsTr.trBase("possessed");
        effectsTr.trBase("prosperity");
        effectsTr.trBase("void");
        effectsTr.trBase("rooted");

        ExperionTranslate soundsTr = new ExperionTranslate("sound.mysticalplantslg", gen);
        soundsTr.trBase("permafrost_shroom_explode", "Shroom Explodes");
        soundsTr.trBase("permafrost_shroom_explode_last", "Shroom Explodes Last");
        soundsTr.trBase("exbiscus_blooming_pick", "Blooming Pick");
        soundsTr.trBase("exbiscus_blooming_pick_essence", "Essence Pick");
        soundsTr.trBase("frost_umbrella_flower_boink", "BOINK");

        ExperionTranslate configTr = new ExperionTranslate("mysticalplantslg.midnightconfig", gen);
        configTr.trBase("show_flower_rings", "Showing the flower rings (ex: Exbiscus), Turn off this might increase the performance");
        configTr.trBase("enum.TOOL_MODEL_TYPE.DEFAULT", "Default");
        configTr.trBase("enum.TOOL_MODEL_TYPE.OPTION2", "2D");
        configTr.trBase("enum.TOOL_MODEL_TYPE.OPTION3", "3D");
        gen.add("mysticalplantslg.midnightconfigsss", "");
        gen.add("mysticalplantslg.midnightconfig.animated_rings", "Animate flower rings (ex: Exbiscus's ring is floating), Turn off this might increase the performance");
        gen.add("mysticalplantslg.midnightconfig.shulkura_shoots", "Makes the shulkura shoot Shulker Bullets randomly");
        gen.add("mysticalplantslg.midnightconfig.show_debug_item", "Adds the debug item into item tab");

        MPLPotions.PERMAFROST.translate(mainTr);
        MPLPotions.FROST_RESISTANCE.translate(mainTr);
        MPLPotions.POSSESSED.translate(mainTr);
        MPLPotions.PROSPERITY.translate(mainTr);

        // Advancement
        ExperionTranslate advTr = new ExperionTranslate("advancements.mysticalplantslg", gen);
        advTr.trBase("mystical_plants_beginning", "Mystical Plants Beginning");
        advTr.trBase("mystical_plants_beginning.desc", "You just found first mystical plant!");

        advTr.trBase("experience_plants", "Free exp??!");
        advTr.trBase("experience_plants.desc", "Find experience plants in Dark Forest");

        advTr.trBase("frost_plants", "Too cold here...");
        advTr.trBase("frost_plants.desc", "Find frost plants in snow biomes");

        advTr.trBase("bogged_plants", "Swamp plant");
        advTr.trBase("bogged_plants.desc", "Find bogged plants in swamp");

        advTr.trBase("soul_plants", "Made with souls??!");
        advTr.trBase("soul_plants.desc", "Find soul plants in Soul Sand Valley");

        advTr.trBase("void_plants", "Void...");
        advTr.trBase("void_plants.desc", "Find void plants in the end");

        advTr.trBase("mystical_dust", "Dust");
        advTr.trBase("mystical_dust.desc", "Find mystical dust ore in caves");

    }
}
