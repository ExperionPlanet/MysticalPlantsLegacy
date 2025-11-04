package io.github.experionplanet.init;

import io.github.experionplanet.items.tool.MysticalToolMats;
import io.github.experionplanet.items.tool.custom.*;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLItems {
    // ESSENCES
    public static final Item EXPERIENCE_ESSENCE = registerBasic("experience_essence");
    public static final Item FROST_ESSENCE = registerBasic("frost_essence");
    public static final Item BOGGED_ESSENCE = registerBasic("bogged_essence");
    public static final Item SOUL_ESSENCE = registerBasic("soul_essence");
    public static final Item VOID_ESSENCE = registerBasic("void_essence");

    // TOOLS
    public static final Item EXPERIENCE_PICKAXE = register("experience_pickaxe", new ExperiencePickaxeItem(MysticalToolMats.EXPERIENCE, new Item.Settings()));
    public static final Item FROST_AXE = register("frost_axe", new FrostAxeItem(MysticalToolMats.FROST, new Item.Settings()));
    public static final Item BOGGED_SHOVEL = register("bogged_shovel", new BoggedShovelItem(MysticalToolMats.BOGGED, new Item.Settings()));
    public static final Item SOUL_HOE = register("soul_hoe", new SoulHoeItem(MysticalToolMats.SOUL, new Item.Settings().fireproof()));
    public static final Item VOID_SWORD = register("void_sword", new VoidSwordItem(MysticalToolMats.VOID, new Item.Settings().fireproof()));

    // BROKEN TOOLS
    public static final Item BROKEN_EXPERIENCE_PICKAXE = registerBasic("broken_experience_pickaxe", 1);
    public static final Item BROKEN_FROST_AXE = registerBasic("broken_frost_axe", 1);

    // MATS
    public static final Item EXP_SPORE = registerBasic("exp_spore");
    public static final Item SOUL = registerBasic("soul");
    public static final Item SOUL_POLLEN = registerBasic("soul_pollen");

    // MISC
    public static final Item GUIDE_BOOK = register("guide_book", new Item(new Item.Settings().maxCount(1)));

    private static Item registerBasic(String name) {
        return registerBasic(name, 64);
    }

    private static Item registerBasic(String name, int maxCount) {
        return register(name, new Item(new Item.Settings().maxCount(maxCount)));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, ExperionUtils.newId(name), item);
    }

    public static void init() {}
}
