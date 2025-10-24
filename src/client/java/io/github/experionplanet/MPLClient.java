package io.github.experionplanet;

import eu.midnightdust.lib.config.MidnightConfig;
import io.github.experionplanet.init.*;
import io.github.experionplanet.mysticalcontents.MysticalContentsClient;
import io.github.experionplanet.particle.BasicGlowingParticle;
import io.github.experionplanet.particle.SporeParticle;
import io.github.experionplanet.renderer.blockentity.*;
import io.github.experionplanet.utils.ExperionLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

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
				MPLBlocks.PERMAFROST_SHROOM,
				MPLBlocks.GLACIER_PASSION_FLOWER,
				MPLBlocks.FROST_UMBRELLA_FLOWER,
				MPLBlocks.DISGUISE_ORCHID,
				MPLBlocks.BOGSPORE_CAP,
				MPLBlocks.HUNGERBALM
		);

		renderer();
		particle();
		entities();

		ExperionLogger.Print("Client Initiated");

	}

	private void renderer() {
		BlockEntityRendererFactories.register(MPLBlockEntities.EXP_MUSHROOMS, ExpMushroomBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.BLOOMING_FLOWER, RingedBloomingBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.BOUNCING_PLANT, BouncingPlantBlockEntityRenderer::new);

		BlockEntityRendererFactories.register(MPLBlockEntities.PERMAFROST_LOG, PermafrostLogBlockEntityRenderer::new);

		BlockEntityRendererFactories.register(MPLBlockEntities.BINDING_ROCK, BindingRockBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.DEBUG_TRANSLATE, DebugTranslateBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);

	}

	private void particle() {
		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1.5f));
		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_ENCHANT_LETTER, (v1) -> new BasicGlowingParticle.Factory(v1, 0.3f, 40, 120));
		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_PIECES, (v1) -> new BasicGlowingParticle.Factory(v1, 0.25f, 20, 40));

		ParticleFactoryRegistry.getInstance().register(MPLParticles.EXP_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1.5f));
		ParticleFactoryRegistry.getInstance().register(MPLParticles.BOG_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1f));

	}

	private void entities() {
		EntityRendererRegistry.register(MPLEntities.SPORES, EmptyEntityRenderer::new);
	}
}