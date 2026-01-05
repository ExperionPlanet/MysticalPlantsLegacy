package io.github.experionplanet.init;

import io.github.experionplanet.items.tool.MysticalToolMats;
import io.github.experionplanet.items.tool.custom.*;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.ColorHelper;

public class MPLItems {
    // ESSENCES
    public static final Item EXPERIENCE_ESSENCE = registerBasic("experience_essence");
    public static final Item FROST_ESSENCE = registerBasic("frost_essence");
    public static final Item BOGGED_ESSENCE = registerBasic("bogged_essence");
    public static final Item SOUL_ESSENCE = registerBasic("soul_essence");
    public static final Item VOID_ESSENCE = registerBasic("void_essence");

    // TOOLS
    public static final Item EXPERIENCE_PICKAXE = register("experience_pickaxe", new ExperiencePickaxeItem(MysticalToolMats.EXPERIENCE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(MysticalToolMats.EXPERIENCE, 1.0F, -2.8F))));
    public static final Item FROST_AXE = register("frost_axe", new FrostAxeItem(MysticalToolMats.FROST, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(MysticalToolMats.FROST,5.0F, -3.0F))));
    public static final Item BOGGED_SHOVEL = register("bogged_shovel", new BoggedShovelItem(MysticalToolMats.BOGGED, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(MysticalToolMats.BOGGED, 1.5F, -3.0F))));
    public static final Item SOUL_HOE = register("soul_hoe", new SoulHoeItem(MysticalToolMats.SOUL, new Item.Settings().fireproof().attributeModifiers(HoeItem.createAttributeModifiers(MysticalToolMats.SOUL, -4.0F, 0.0F))));
    public static final Item VOID_SWORD = register("void_sword", new VoidSwordItem(MysticalToolMats.VOID, new Item.Settings().fireproof().attributeModifiers(SwordItem.createAttributeModifiers(MysticalToolMats.VOID, 2, -2.4F))));

    // BROKEN TOOLS
    public static final Item BROKEN_EXPERIENCE_PICKAXE = registerBasic("broken_experience_pickaxe", 1);
    public static final Item BROKEN_FROST_AXE = registerBasic("broken_frost_axe", 1);

    // MATS
    public static final Item EXP_SPORE = registerBasic("exp_spore");
    public static final Item SOUL = registerBasic("soul");
    public static final Item SOUL_POLLEN = registerBasic("soul_pollen");

    // MISC
    public static final Item SOUL_ZOMBIE_SPAWN_EGG = register("soul_zombie_spawn_egg", new SpawnEggItem(MPLEntities.SOUL_ZOMBIE, ColorHelper.Argb.getArgb(105, 229, 255), ColorHelper.Argb.getArgb(22, 105, 198), new Item.Settings()));
    public static final Item GUIDE_BOOK = register("guide_book", new Item(new Item.Settings().maxCount(1)));

    private static Item registerBasic(String name) {
        return registerBasic(name, 64);
    }

    private static Item registerBasic(String name, int maxCount) {
        return register(name, new Item(new Item.Settings().maxCount(maxCount)));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, MysticalUtils.newId(name), item);
    }

    public static void init() {}
}
