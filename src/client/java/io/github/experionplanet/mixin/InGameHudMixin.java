package io.github.experionplanet.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.experionplanet.init.MPLStatusEffects;
import io.github.experionplanet.mysticalcontents.HeartGetTexture;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(
            method = "drawHeart",
            at = @At("HEAD")
    )
    private void drawHeart(DrawContext context, Object object, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo cit) {
        RenderSystem.enableBlend();
        Boolean bool = MinecraftClient.getInstance().player.hasStatusEffect(MPLStatusEffects.PERMAFROST);

        if (bool) {
            ExperionLogger.Print("HEL YE");

        }else {
            context.drawGuiTexture(((HeartGetTexture) object).getTexture(hardcore, half, blinking), x, y, 9, 9);
        }



        RenderSystem.disableBlend();
    }
}
