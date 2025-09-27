package io.github.experionplanet;

import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.renderer.blockentity.ExpMushroomBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MPLClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MPLBlocks.SMALL_EXP_MUSHROOMS, MPLBlocks.MEDIUM_EXP_MUSHROOMS, MPLBlocks.LARGE_EXP_MUSHROOMS, MPLBlocks.BLEEDING_EXP);

		BlockEntityRendererFactories.register(MPLBlockEntities.EXP_MUSHROOMS, ExpMushroomBlockEntityRenderer::new);
	}
}