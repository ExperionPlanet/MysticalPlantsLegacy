package io.github.experionplanet.init;

import io.github.experionplanet.blocks.entity.SoulPossessionIrisBlockEntity;
import io.github.experionplanet.blocks.entity.custom.*;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MPLBlockEntities {
    public static final BlockEntityType<ExpMushroomBlockEntity> EXP_MUSHROOMS = register("exp_mushrooms", ExpMushroomBlockEntity::new, MPLBlocks.SMALL_EXP_MUSHROOMS, MPLBlocks.MEDIUM_EXP_MUSHROOMS, MPLBlocks.LARGE_EXP_MUSHROOMS);
    public static final BlockEntityType<BloomingFlowerBlockEntity> BLOOMING_FLOWER = register("blooming_flower", BloomingFlowerBlockEntity::new, MPLBlocks.EXBISCUS, MPLBlocks.GLACIER_PASSION_FLOWER, MPLBlocks.HUNGERBALM);

    public static final BlockEntityType<BouncingPlantBlockEntity> BOUNCING_PLANT = register("permafrost_shroom", BouncingPlantBlockEntity::new, MPLBlocks.PERMAFROST_SHROOM,MPLBlocks.FROST_UMBRELLA_FLOWER,MPLBlocks.BOGSPORE_CAP);
    public static final BlockEntityType<PermafrostLogBlockEntity> PERMAFROST_LOG = register("permafrost_log",PermafrostLogBlockEntity::new, MPLBlocks.PERMAFROSTED_LOG);

    public static final BlockEntityType<BindingRockBlockEntity> BINDING_ROCK = register("binding_rock", BindingRockBlockEntity::new, MPLBlocks.BINDING_ROCK);

    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = register("pedestal", PedestalBlockEntity::new, MPLBlocks.PEDESTAL);

    public static final BlockEntityType<SoulPossessionIrisBlockEntity> SOUL_POSSESSION_IRIS = register("soul_possesion_iris", SoulPossessionIrisBlockEntity::new, MPLBlocks.SOUL_POSSESSION_IRIS);

    public static final BlockEntityType<DebugTranslateBlockEntity> DEBUG_TRANSLATE = register("debug_translate", DebugTranslateBlockEntity::new, MPLBlocks.DEBUG_TRANSLATE);

    public static void init() {

    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            BlockEntityType.BlockEntityFactory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = ExperionUtils.newId(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }
}
