package io.github.experionplanet;

import io.github.experionplanet.init.*;
import io.github.experionplanet.mysticalcontents.MysticalContentsClient;
import io.github.experionplanet.particle.BasicMysticalParticle;
import io.github.experionplanet.particle.ParticleCreator;
import io.github.experionplanet.particle.PlaneParticle;
import io.github.experionplanet.particle.SporeParticle;
import io.github.experionplanet.renderer.blockentity.custom.*;
import io.github.experionplanet.soul_zombie.SoulZombieEntityRenderer;
import io.github.experionplanet.utils.ExperionLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;

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
				MPLBlocks.HUNGERBALM,
				MPLBlocks.SOUL_POSSESSION_IRIS,
				MPLBlocks.SOUL_PITCHER,
				MPLBlocks.VOID_CAP,
				MPLBlocks.SHULKURA

		);

		renderer();
		particle();
		entities();

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {

		});

		ExperionLogger.Print("Client Initiated");

	}

	private void renderer() {
		BlockEntityRendererFactories.register(MPLBlockEntities.EXP_MUSHROOMS, ExpMushroomBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.BLOOMING_FLOWER, RingedBloomingBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.BOUNCING_PLANT, BouncingPlantBlockEntityRenderer::new);

		BlockEntityRendererFactories.register(MPLBlockEntities.PERMAFROST_LOG, PermafrostLogBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.SOUL_BELL, SoulBellBlockEntityRenderer::new);

		BlockEntityRendererFactories.register(MPLBlockEntities.BINDING_ROCK, BindingRockBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.DEBUG_TRANSLATE, DebugTranslateBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.SOUL_POSSESSION_IRIS, SoulPossessionIrisBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MPLBlockEntities.VOID_STRAWFLOWER, BouncingPlantBlockEntityRenderer::new);

	}

	private void particle() {
		ParticleFactoryRegistry factory = ParticleFactoryRegistry.getInstance();
		factory.register(MPLParticles.EXP_ASH, (v1) -> new BasicMysticalParticle.Factory(v1,
				new ParticleCreator(20, 40, 0.1f, 0.3f, ParticleTextureSheet.PARTICLE_SHEET_LIT)
						.targetScale(0,0)
						.targetAngle(-20f, 20f)
						.gravityStrength(0.5f)
						.veloX(-0.1f, 0.1f)
						.veloZ(-0.1f, 0.1f)
						.export()
		));

		factory.register(MPLParticles.EXP_DRIP_YELLOW, (v1) -> new BasicMysticalParticle.Factory(v1,
				new ParticleCreator(5, 10, 0.1f, 0.3f, ParticleTextureSheet.PARTICLE_SHEET_LIT)
						.veloX(0,0)
						.veloZ(0,0)
						.veloY(0.1f, 0.5f)
						.gravityStrength(0.4f)
						.export()
		));
		factory.register(MPLParticles.EXP_DRIP_GREEN, (v1) -> new BasicMysticalParticle.Factory(v1,
				new ParticleCreator(5, 10, 0.1f, 0.3f, ParticleTextureSheet.PARTICLE_SHEET_LIT)
						.veloX(0,0)
						.veloZ(0,0)
						.veloY(0.1f, 0.5f)
						.gravityStrength(0.4f)
						.export()
		));

		factory.register(MPLParticles.EXP_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1.5f, true));
		factory.register(MPLParticles.BOG_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1f, false));
		factory.register(MPLParticles.VOID_SPORE, (v1) -> new SporeParticle.Factory<>(v1, 1f, false));
		factory.register(MPLParticles.ENDER_WARP, (v1) -> new PlaneParticle.Factory<>(v1, 10, 10, 2.5f, true));
	}

	private void entities() {
		EntityRendererRegistry.register(MPLEntities.SPORES, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(MPLEntities.SOUL_ZOMBIE, SoulZombieEntityRenderer::new);
	}


}