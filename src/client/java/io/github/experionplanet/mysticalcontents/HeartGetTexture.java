package io.github.experionplanet.mysticalcontents;

import net.minecraft.util.Identifier;

public interface HeartGetTexture {
    Identifier getTexture(boolean hardcore, boolean blinking, boolean half);
}
