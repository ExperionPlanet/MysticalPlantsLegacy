package io.github.experionplanet.init;

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
    public static final BlockEntityType<ExbiscusBlockEntity> EXBISCUS = register("exbiscus", ExbiscusBlockEntity::new, MPLBlocks.EXBISCUS);

    public static final BlockEntityType<PermafrostShroomBlockEntity> PERMAFROST_SHROOM = register("permafrost_shroom", PermafrostShroomBlockEntity::new, MPLBlocks.PERMAFROST_SHROOM);

    public static final BlockEntityType<BindingRockBlockEntity> BINDING_ROCK = register("binding_rock", BindingRockBlockEntity::new, MPLBlocks.BINDING_ROCK);
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = register("pedestal", PedestalBlockEntity::new, MPLBlocks.PEDESTAL);

    public static final BlockEntityType<DebugTranslateBlockEntity> DEBUG_TRANSLATE = register("debug_translate", DebugTranslateBlockEntity::new, MPLBlocks.DEBUG_TRANSLATE);

    public static void init() {}

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            BlockEntityType.BlockEntityFactory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = ExperionUtils.newId(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }
}
