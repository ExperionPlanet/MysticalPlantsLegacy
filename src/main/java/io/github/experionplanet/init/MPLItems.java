package io.github.experionplanet.init;

import io.github.experionplanet.items.ExperiencePickaxeItem;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLItems {
    public static final Item EXPERIENCE_ESSENCE = register("experience_essence", new Item(new Item.Settings()));

    // TOOLS
    public static final Item EXPERIENCE_PICKAXE = register("experience_pickaxe", new ExperiencePickaxeItem(ToolMaterials.DIAMOND, new Item.Settings()));

    // BROKEN TOOLS
    public static final Item BROKEN_EXPERIENCE_PICKAXE = register("broken_experience_pickaxe", new Item(new Item.Settings().maxCount(1)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, ExperionUtils.newId(name), item);
    }

    public static void init() {}
}
