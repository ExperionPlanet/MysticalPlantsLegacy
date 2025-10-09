package io.github.experionplanet.init;

import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MPLBlockTags {
    public static final TagKey<Block> ORES = cKey("ores");

    private static TagKey<Block> cKey(String str) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c",str));
    }

    private static TagKey<Block> Key(String str) {
        return TagKey.of(RegistryKeys.BLOCK, ExperionUtils.newId(str));
    }
}
