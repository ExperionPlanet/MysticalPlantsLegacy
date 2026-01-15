package io.github.experionplanet.mysticalplantslg.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.MysticalHoldableContentClient;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)

public abstract class ItemModelRendererMixin {
    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        Identifier id = Registries.ITEM.getId(stack.getItem());
        if (MysticalHoldableContentClient.ITEM_3D_ABLE.containsKey(id)) {
            /*
            if (MPLConfig.tool_model_type == MPLConfig.TOOL_MODEL_TYPE.DEFAULT) {
                if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND) {
                    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(id));
                }else if (renderMode == ModelTransformationMode.FIXED) {
                    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(MysticalHoldableContentClient.ITEM_3D_ABLE.get(id)));
                }
            }else {
                if (MPLConfig.tool_model_type == MPLConfig.TOOL_MODEL_TYPE.OPTION2) {
                    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(id));
                } else if (MPLConfig.tool_model_type == MPLConfig.TOOL_MODEL_TYPE.OPTION3) {

                }return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(MysticalHoldableContentClient.ITEM_3D_ABLE.get(id)));
            }*/
            if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(id));
            }else if (renderMode == ModelTransformationMode.FIXED) {
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(MysticalHoldableContentClient.ITEM_3D_ABLE.get(id)));
            }
        }
        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        Identifier mainID = Registries.ITEM.getId(stack.getItem());

        if (MysticalHoldableContentClient.ITEM_3D_ABLE.containsKey(mainID)) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(MysticalHoldableContentClient.ITEM_3D_ABLE.get(mainID)));
        }

        return bakedModel;
    }
}
