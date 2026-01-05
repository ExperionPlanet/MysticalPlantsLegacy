package io.github.experionplanet.init;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class MPLBiomeTags {
    public static final TagKey<Biome> SOUL = key("soul");

    private static TagKey<Biome> cKey(String str) {
        return TagKey.of(RegistryKeys.BIOME, Identifier.of("c",str));
    }

    private static TagKey<Biome> key(String str) {
        return TagKey.of(RegistryKeys.BIOME, MysticalUtils.newId(str));
    }
}
