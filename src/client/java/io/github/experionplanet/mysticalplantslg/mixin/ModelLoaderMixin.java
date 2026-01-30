package io.github.experionplanet.mysticalplantslg.mixin;

import io.github.experionplanet.mysticalplantslg.mysticalcontents.MysticalHoldableContentClient;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    private static boolean INITIATED_CONTENT = false;
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Shadow
    @Final
    private Set<Identifier> modelsToLoad;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        ExperionLogger.Print("HMMMMMM");
        if (!INITIATED_CONTENT) {
            INITIATED_CONTENT = true;
            MysticalHoldableContentClient.bootstrap();
            MysticalHoldableContentClient.build();
        }


        for (Identifier v : MysticalHoldableContentClient.ITEM_3D_ABLE.valueColl()) {
            loadItemModel(ModelIdentifier.ofInventoryVariant(v));
        }

    }
}
