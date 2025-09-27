package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLBlocks;
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
        gen.add(MPLBlocks.SMALL_EXP_MUSHROOMS, "Small Exp Mushrooms");
        gen.add(MPLBlocks.MEDIUM_EXP_MUSHROOMS, "Medium Exp Mushrooms");
        gen.add(MPLBlocks.LARGE_EXP_MUSHROOMS, "Large Exp Mushrooms");
        gen.add(MPLBlocks.BLEEDING_EXP, "Bleeding Exp");
    }
}
