package io.github.experionplanet.init;

import io.github.experionplanet.registry.MysticMapping;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.util.Identifier;

public class MysticalHoldableContentClient {
    public static final MysticMapping<Identifier, Identifier> ITEM_3D_ABLE = new MysticMapping<>();

    public static void bootstrap() {
        // Item 3D-able
        ITEM_3D_ABLE.register(ExperionUtils.newId("experience_pickaxe"), ExperionUtils.newId("experience_pickaxe_world"));
    }

    public static void build() {
        ITEM_3D_ABLE.build();
    }
}
