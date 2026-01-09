package io.github.experionplanet.mysticalplantslg.init;

import com.mojang.serialization.Codec;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLComponentTypes {
    public static final ComponentType<Integer> EXP_FILLS = register("exp_fills", Codec.intRange(0, 128));
    public static final ComponentType<Integer> SOULS = register("souls", Codec.intRange(0, 50));
    public static final ComponentType<Integer> SOIL_FILLINGS = register("soil_fillings", Codec.intRange(0, 128));
    public static final ComponentType<Integer> SOIL_MODE = register("soil_mode", Codec.intRange(1, 4));

    private static <T> ComponentType<T> register(String name, Codec<T> cod) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, MysticalUtils.newId(name), ComponentType.<T>builder().codec(cod).build());
    }

    public static void init() {}
}
