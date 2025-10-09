package io.github.experionplanet;

import io.github.experionplanet.init.*;
import io.github.experionplanet.particle.BasicGlowingParticle;
import io.github.experionplanet.particle.SporeParticle;
import io.github.experionplanet.renderer.blockentity.*;
import io.github.experionplanet.utils.ExperionLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MPLClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MysticalContentsClient.bootstrap();
		MysticalContentsClient.build();

		MPLEntityModelLayers.init();
		MPLProgressHud.boot();

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				MPLBlocks.PEDESTAL,
				MPLBlocks.SMALL_EXP_MUSHROOMS,
				MPLBlocks.MEDIUM_EXP_MUSHROOMS,
				MPLBlocks.LARGE_EXP_MUSHROOMS,
				MPLBlocks.BLEEDING_EXP,
				MPLBlocks.EXBISCUS,
				MPLBlocks.PERMAFROST_SHROOM
		);

		BlockEntityRendererFactories.register(MPLBlockEntities.EXP_MUSHROOMS, ExpMushroomBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.EXBISCUS, ExbiscusBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.PERMAFROST_SHROOM, PermafrostShroomBlockEntityRenderer::new);

		BlockEntityRendererFactories.register(MPLBlockEntities.BINDING_ROCK, BindingRockBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.DEBUG_TRANSLATE, DebugTranslateBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1.5f));
		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_ENCHANT_LETTER, (v1) -> new BasicGlowingParticle.Factory(v1, 0.3f, 40, 120));
		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_PIECES, (v1) -> new BasicGlowingParticle.Factory(v1, 0.25f, 20, 40));


		ExperionLogger.Print("Client Initiated");

	}
}