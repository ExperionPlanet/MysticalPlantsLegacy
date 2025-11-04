package io.github.experionplanet.init;

import io.github.experionplanet.MPLMain;
import io.github.experionplanet.utils.ExperionLogger;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MPLKeybindings {

    private static KeyBinding showInfo;
    private static final String category = "category." + MPLMain.MOD_ID + ".keys";
    public static void bind() {
        showInfo = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key." + MPLMain.MOD_ID + ".showinfo", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, category)
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MPLMain.showInfo = showInfo.wasPressed();


        });
    }
}
