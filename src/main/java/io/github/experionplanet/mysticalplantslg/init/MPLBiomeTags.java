package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class MPLBiomeTags {
    public static final TagKey<Biome> SOUL = key("soul");

    public static final TagKey<Biome> EXPERIENCE_PICKAXE_SPAWNABLE = key("binding_rock/experience_pickaxe");
    public static final TagKey<Biome> FROST_AXE_SPAWNABLE = key("binding_rock/frost_pickaxe");
    public static final TagKey<Biome> BOGGED_SHOVEL_SPAWNABLE = key("binding_rock/bogged_shovel");
    public static final TagKey<Biome> SOUL_HOE_SPAWNABLE = key("binding_rock/soul_hoe");
    public static final TagKey<Biome> VOID_SWORD_SPAWNABLE = key("binding_rock/void_sword");

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
