package io.github.experionplanet.mysticalplantslg.init;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class MPLBlockProperties {
    public static final BooleanProperty BLOOMING = BooleanProperty.of("blooming");
    public static final IntProperty CAP_REMAINING = IntProperty.of("cap_remaining", 0, 8);
    public static final BooleanProperty SOUL_NOT_ATTUNED = BooleanProperty.of("soul_not_attuned");
    public static final BooleanProperty ON_CRAFTING = BooleanProperty.of("on_crafting");
    public static final BooleanProperty SNOW = BooleanProperty.of("snow");
}
