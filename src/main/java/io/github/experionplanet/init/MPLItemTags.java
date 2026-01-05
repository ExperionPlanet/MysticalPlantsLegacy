package io.github.experionplanet.init;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MPLItemTags {
    public static final TagKey<Item> DIRTS = cKey("dirts");

    public static final TagKey<Item> SOIL_FILLING = key("soil_filling");
    public static final TagKey<Item> SOUL_FILLING = key("soul_filling");

    private static TagKey<Item> cKey(String str) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c",str));
    }

    private static TagKey<Item> key(String str) {
        return TagKey.of(RegistryKeys.ITEM, MysticalUtils.newId(str));
    }
}
