package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class MPLBiomeTags {
    public static final TagKey<Biome> SOUL = key("soul");

    public static final TagKey<Biome> C_IS_SNOWY = cKey("is_snowy");
    public static final TagKey<Biome> C_IS_SNOWY_PLAINS = cKey("is_snowy_plains");

    public static final TagKey<Biome> C_IS_SWAMP = cKey("is_swamp");

    private static TagKey<Biome> cKey(String str) {
        return TagKey.of(RegistryKeys.BIOME, Identifier.of("c",str));
    }

    private static TagKey<Biome> key(String str) {
        return TagKey.of(RegistryKeys.BIOME, MysticalUtils.newId(str));
    }
}
