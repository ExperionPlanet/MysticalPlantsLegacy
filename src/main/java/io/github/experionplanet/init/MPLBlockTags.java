package io.github.experionplanet.init;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MPLBlockTags {
    public static final TagKey<Block> ORES = cKey("ores");
    public static final TagKey<Block> DIRTS = cKey("dirts");

    public static final TagKey<Block> SOIL_CHANGING = key("soil_changing");

    private static TagKey<Block> cKey(String str) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c",str));
    }

    private static TagKey<Block> key(String str) {
        return TagKey.of(RegistryKeys.BLOCK, MysticalUtils.newId(str));
    }
}
