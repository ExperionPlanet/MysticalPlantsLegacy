package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.init.MPLPotions;
import io.github.experionplanet.utils.ExperionTranslate;
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

        mainTr.trBlock(MPLBlocks.PERMAFROSTED_LOG);

        mainTr.trBlock(MPLBlocks.GLACIER_PASSION_FLOWER);

        mainTr.trBlock(MPLBlocks.BINDING_ROCK);
        mainTr.trBlock(MPLBlocks.PEDESTAL);

        gen.add(MPLBlocks.DEBUG_TRANSLATE, "D3bug Tr4nsl4t3!1");

        ExperionTranslate effectsTr = new ExperionTranslate("effect.mysticalplantslg", gen);
        effectsTr.trBase("permafrost");
        effectsTr.trBase("frost_resistance");
        effectsTr.trBase("possessed");
        effectsTr.trBase("prosperity");

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
    }
}
