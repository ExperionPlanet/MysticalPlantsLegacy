package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.custom.BindingRockBlockEntity;
import io.github.experionplanet.registry.MysticIdMapping;
import io.github.experionplanet.init.MysticalContents;
import io.github.experionplanet.registry.TrxContent;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class BindingRockBlockEntityRenderer implements BlockEntityRenderer<BindingRockBlockEntity> {
    private final ItemRenderer itemRenderer;
    private final BlockRenderManager rendManager;
    private Identifier currentID = MysticIdMapping.DEFAULT_ID;
    private TrxContent trx = MysticalContents.BINDING_ROCK.get(MysticIdMapping.DEFAULT_ID);

    public BindingRockBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.rendManager = context.getRenderManager();
    }

    @Override
    public void render(BindingRockBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        //ExperionLogger.Print("CLIENT TICK: " + entity.tickProgress + " STG: " + entity.dat.getInt("stage"));

        if (!entity.getCurrentStack().isEmpty()) {
            ItemStack stack = entity.getCurrentStack();

            Identifier itemID = Registries.ITEM.getId(stack.getItem());

            matrices.push();

            if (!itemID.equals(currentID)) {
                currentID = itemID;
                trx = MysticalContents.BINDING_ROCK.get(currentID);
            }

            matrices.translate(trx.translate.x, trx.translate.y, trx.translate.z);

            Random rand = Random.create(entity.getPos().asLong());


            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(trx.ry + ExperionUtils.floatInRange(rand, -15f, 15f)));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(trx.rx + ExperionUtils.floatInRange(rand, -15f, 15f)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(trx.rz + ExperionUtils.floatInRange(rand, -15f, 15f)));


            this.itemRenderer.renderItem(entity.getCurrentStack(), ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());


            matrices.pop();

        }


    }
}
