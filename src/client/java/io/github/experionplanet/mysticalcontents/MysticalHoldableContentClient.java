package io.github.experionplanet.mysticalcontents;

import io.github.experionplanet.mysticalcontent.MysticMapping;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.util.Identifier;

public class MysticalHoldableContentClient {
    public static final MysticMapping<Identifier, Identifier> ITEM_3D_ABLE = new MysticMapping<>();

    public static void register(String name) {
        ITEM_3D_ABLE.register(ExperionUtils.newId(name), ExperionUtils.newId(name + "_world"));
    }

    public static void bootstrap() {
        // Item 3D-able
        register("experience_pickaxe");
        register("frost_axe");
        register("bogged_shovel");
        register("soul_hoe");
        register("void_sword");
    }

    public static void build() {
        ITEM_3D_ABLE.build();
    }
}
