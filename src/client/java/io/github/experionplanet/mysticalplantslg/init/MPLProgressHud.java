package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.mysticalcontents.content.HudProgressItem;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.MysticalContentsClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MPLProgressHud {
    private static final int FrameMaxWidth = 29;
    private static final int FrameHeight = 6;
    private static final int FrameTime = 3;
    public static void render(DrawContext drawContext, RenderTickCounter delta) {
        ItemStack holdingStack = MinecraftClient.getInstance().player.getMainHandStack();

        if (!holdingStack.isEmpty()) {
            HudProgressItem progressHud = isHoldingElementalTool(holdingStack);

            if (progressHud != null) {
                MatrixStack matrice = drawContext.getMatrices();

                matrice.push();

                int width = 39;
                int height = 13;

                float posX = ((float)drawContext.getScaledWindowWidth()/2f) + 91f + 10f;
                float posY = ((float)drawContext.getScaledWindowHeight() - ((height * 2))) - 1f;

                matrice.translate(posX, posY, 0f);
                matrice.scale(2f, 2f, 2f);

                drawContext.drawTexture(progressHud.base, 0,0, 0,0,width,height, 39,13);
                // Fluid Anim
                int num = 0;

                if (holdingStack.contains(progressHud.prop)) {
                    num = holdingStack.get(progressHud.prop);
                }

                int FrameWidth = (int) (((double)num/(double) progressHud.max) * (double) FrameMaxWidth);


                long totalTicks = MinecraftClient.getInstance().world.getTime();
                int frame = (int) ((totalTicks / FrameTime) % progressHud.frames);

                drawContext.drawTexture(
                        progressHud.fluid,
                        5,
                        3,
                        0,
                        frame * FrameHeight,
                        FrameWidth,
                        FrameHeight,
                        FrameMaxWidth,
                        FrameHeight * progressHud.frames
                );

                matrice.pop();
            }
        }
    }

    public static void boot() {
        HudRenderCallback.EVENT.register(MPLProgressHud::render);
    }

    private static HudProgressItem isHoldingElementalTool(ItemStack stack) {
        Item item = stack.getItem();

        if (MysticalContentsClient.HUD_PROGRESS_ITEM.containsItem(item)) {
            return MysticalContentsClient.HUD_PROGRESS_ITEM.getAsItem(item);
        }

        return null;
    }
}
