package io.github.experionplanet.init;

import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLItems {
    public static final Item EXP_ORB = register("exp_orb", new Item(new Item.Settings()));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, ExperionUtils.newId(name), item);
    }

    public static void init() {}
}
