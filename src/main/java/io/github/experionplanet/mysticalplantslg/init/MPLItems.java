package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.items.custom.MysticalPotionItem;
import io.github.experionplanet.mysticalplantslg.items.custom.MysticalSplashItem;
import io.github.experionplanet.mysticalplantslg.items.custom.PermafrostSnowballItem;
import io.github.experionplanet.mysticalplantslg.items.tool.MysticalToolMats;
import io.github.experionplanet.mysticalplantslg.items.tool.custom.*;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.ColorHelper;

public class MPLItems {
    // ESSENCES
    public static final Item EXPERIENCE_ESSENCE = registerBasic("experience_essence", 16, Rarity.RARE);
    public static final Item FROST_ESSENCE = registerBasic("frost_essence", 16, Rarity.RARE);
    public static final Item BOGGED_ESSENCE = registerBasic("bogged_essence", 16, Rarity.RARE);
    public static final Item SOUL_ESSENCE = registerBasic("soul_essence", 16, Rarity.RARE);
    public static final Item VOID_ESSENCE = registerBasic("void_essence", 16, Rarity.RARE);

    // TOOLS
    public static final Item EXPERIENCE_PICKAXE = register("experience_pickaxe", new ExperiencePickaxeItem(MysticalToolMats.EXPERIENCE, new Item.Settings().rarity(Rarity.EPIC).attributeModifiers(PickaxeItem.createAttributeModifiers(MysticalToolMats.EXPERIENCE, 1.0F, -2.8F))));
    public static final Item FROST_AXE = register("frost_axe", new FrostAxeItem(MysticalToolMats.FROST, new Item.Settings().rarity(Rarity.EPIC).attributeModifiers(AxeItem.createAttributeModifiers(MysticalToolMats.FROST,5.0F, -3.0F))));
    public static final Item BOGGED_SHOVEL = register("bogged_shovel", new BoggedShovelItem(MysticalToolMats.BOGGED, new Item.Settings().rarity(Rarity.EPIC).attributeModifiers(ShovelItem.createAttributeModifiers(MysticalToolMats.BOGGED, 1.5F, -3.0F))));
    public static final Item SOUL_HOE = register("soul_hoe", new SoulHoeItem(MysticalToolMats.SOUL, new Item.Settings().rarity(Rarity.EPIC).fireproof().attributeModifiers(HoeItem.createAttributeModifiers(MysticalToolMats.SOUL, -4.0F, 0.0F))));
    public static final Item VOID_SWORD = register("void_sword", new VoidSwordItem(MysticalToolMats.VOID, new Item.Settings().rarity(Rarity.EPIC).fireproof().attributeModifiers(SwordItem.createAttributeModifiers(MysticalToolMats.VOID, 2, -2.4F))));

    // BROKEN TOOLS
    public static final Item BROKEN_EXPERIENCE_PICKAXE = registerBasic("broken_experience_pickaxe", 1);
    public static final Item BROKEN_FROST_AXE = registerBasic("broken_frost_axe", 1);
    public static final Item BROKEN_BOGGED_SHOVEL = registerBasic("broken_bogged_shovel", 1);
    public static final Item BROKEN_SOUL_HOE = registerBasic("broken_soul_hoe", 1);
    public static final Item BROKEN_VOID_SWORD = registerBasic("broken_void_sword", 1);

    // MATS
    public static final Item EXP_SPORE = registerBasic("exp_spore");
    public static final Item PERMAFROST_SNOWFLAKE = registerBasic("permafrost_snowflake");
    public static final Item PERMAFROST_SNOWBALL = register("permafrost_snowball", new PermafrostSnowballItem(new Item.Settings()));
    public static final Item BOG_CAP = registerBasic("bog_cap");
    public static final Item BOG_FERTILIZER = registerBasic("bog_fertilizer");
    public static final Item SOUL = registerBasic("soul");
    public static final Item SOUL_POLLEN = registerBasic("soul_pollen");
    public static final Item VOID_CAP = registerBasic("void_cap");
    public static final Item VOID_ROOT = registerBasic("void_root");

    public static final Item MYSTICAL_DUST = registerBasic("mystical_dust");
    public static final Item MYSTICAL_INGOT = registerBasic("mystical_ingot");
    public static final Item RAW_MYSTICAL = registerBasic("raw_mystical");
    public static final Item MYSTICAL_STAR_ESSENCE = registerBasic("mystical_star_essence", 16, Rarity.RARE);

    // HELL YEAH POTIONS
    public static final Item MYSTICAL_BOTTLE = registerBasic("mystical_bottle");
    public static final Item MYSTICAL_SPLASH = registerBasic("mystical_splash");
    public static final Item PROSPERITY_POTION = register("prosperity_potion", new MysticalPotionItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON).food(MPLFoodComponents.MYSTICAL_POTION), new StatusEffectInstance(MPLStatusEffects.PROSPERITY, 3600)));
    public static final Item PROSPERITY_POTION_LONG = register("prosperity_potion_long", new MysticalPotionItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON).food(MPLFoodComponents.MYSTICAL_POTION), new StatusEffectInstance(MPLStatusEffects.PROSPERITY, 7200)));
    public static final Item FROST_RESISTANCE_POTION = register("frost_resistance_potion", new MysticalPotionItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON).food(MPLFoodComponents.MYSTICAL_POTION), new StatusEffectInstance(MPLStatusEffects.FROST_RESISTANCE, 9600)));

    public static final Item VOID_SPLASH = register("void_splash", new MysticalSplashItem(new Item.Settings().maxCount(4).rarity(Rarity.RARE), MysticalUtils.newId("void")));
    public static final Item ROOTED_SPLASH = register("rooted_splash", new MysticalSplashItem(new Item.Settings().maxCount(2).rarity(Rarity.EPIC), MysticalUtils.newId("rooted")));
    public static final Item PERMAFROST_SPLASH = register("permafrost_splash", new MysticalSplashItem(new Item.Settings().maxCount(4).rarity(Rarity.RARE), MysticalUtils.newId("permafrost")));
    public static final Item POSSESSION_SPLASH = register("possession_splash", new MysticalSplashItem(new Item.Settings().maxCount(2).rarity(Rarity.EPIC), MysticalUtils.newId("possession")));

    // MISC
    public static final Item SOUL_ZOMBIE_SPAWN_EGG = register("soul_zombie_spawn_egg", new SpawnEggItem(MPLEntities.SOUL_ZOMBIE, ColorHelper.Argb.getArgb(105, 229, 255), ColorHelper.Argb.getArgb(22, 105, 198), new Item.Settings()));
    public static final Item GUIDE_BOOK = register("guide_book", new Item(new Item.Settings().maxCount(1)));

    private static Item registerBasic(String name) {
        return registerBasic(name, 64, Rarity.COMMON);
    }

    private static Item registerBasic(String name, int maxCount) {
        return registerBasic(name, maxCount, Rarity.COMMON);
    }

    private static Item registerBasic(String name, int maxCount, Rarity rarity) {
        return register(name, new Item(new Item.Settings().maxCount(maxCount).rarity(rarity)));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, MysticalUtils.newId(name), item);
    }

    public static void init() {}
}
