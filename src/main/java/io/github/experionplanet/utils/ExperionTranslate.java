package io.github.experionplanet.utils;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ExperionTranslate {
    private final FabricLanguageProvider.TranslationBuilder gen;
    private final String baseName;

    public ExperionTranslate(FabricLanguageProvider.TranslationBuilder gen) {
        this.gen = gen;
        this.baseName = "";
    }

    public ExperionTranslate(String base, FabricLanguageProvider.TranslationBuilder gen) {
        this.gen = gen;
        this.baseName = base;
    }

    public void trBase(String name, String translate) {
        gen.add(baseName + "." + name, translate);
    }

    public void trRaw(String name, String translate) {
        gen.add(name, translate);
    }

    public void trBase(String name) {
        trBase(name, quickTranslate(name));
    }

    public void trItem(Item item) {
        gen.add(item, quickTranslate(MysticalUtils.getItemID(item).getPath()));
    }

    public void trBlock(Block block) {
        gen.add(block, quickTranslate(MysticalUtils.getBlockID(block).getPath()));
    }

    public static String quickTranslate(String name) {
        String[] var = name.split("_");

        String res = "";
        boolean first = false;

        for (String a : var) {
            String firstChar = a.substring(0,1).toUpperCase();
            String mySTR = firstChar + a.substring(1);

            if (first) {
                res = res + " " + mySTR;
            }else {
                res = res + mySTR;
                first = true;
            }
        }

        return res;

    }
}
