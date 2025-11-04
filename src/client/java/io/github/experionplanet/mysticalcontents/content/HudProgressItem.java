package io.github.experionplanet.mysticalcontents.content;

import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.component.ComponentType;
import net.minecraft.util.Identifier;

public class HudProgressItem {
    public final Identifier base;
    public final Identifier fluid;
    public final ComponentType<Integer> prop;
    public final int max;
    public final int frames;

    public HudProgressItem(String base, String fluid, ComponentType<Integer> property, int maximum, int maxFrame) {
        this.base = ExperionUtils.newId("textures/gui/" + base + ".png");
        this.fluid = ExperionUtils.newId("textures/gui/" + fluid + ".png");
        this.prop = property;
        this.max = maximum;
        this.frames = maxFrame;
    }
}
