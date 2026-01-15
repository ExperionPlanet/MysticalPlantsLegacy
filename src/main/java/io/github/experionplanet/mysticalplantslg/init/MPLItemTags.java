package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MPLItemTags {
    public static final TagKey<Item> DIRTS = cKey("dirts");

    public static final TagKey<Item> SOIL_FILLING = key("soil_filling");
    public static final TagKey<Item> SOUL_FILLING = key("soul_filling");
    public static final TagKey<Item> ESSENCES = key("essences");

    public static final TagKey<Item> MYSTICAL_PLANTS = key("mystical_plants");
    public static final TagKey<Item> EXPERIENCE_PLANTS = key("experience_plants");
    public static final TagKey<Item> FROST_PLANTS = key("frost_plants");
    public static final TagKey<Item> BOGGED_PLANTS = key("bogged_plants");
    public static final TagKey<Item> SOUL_PLANTS = key("soul_plants");
    public static final TagKey<Item> VOID_PLANTS = key("void_plants");

    private static TagKey<Item> cKey(String str) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c",str));
    }

    private static TagKey<Item> key(String str) {
        return TagKey.of(RegistryKeys.ITEM, MysticalUtils.newId(str));
    }
}
