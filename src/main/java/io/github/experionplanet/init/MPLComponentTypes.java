package io.github.experionplanet.init;

import com.mojang.serialization.Codec;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLComponentTypes {
    public static final ComponentType<Integer> EXP_FILLS = register("exp_fills", Codec.intRange(0, 128));

    private static <T> ComponentType<T> register(String name, Codec<T> cod) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ExperionUtils.newId(name), ComponentType.<T>builder().codec(cod).build());
    }

    public static void init() {}
}
