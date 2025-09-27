package io.github.experionplanet.init;

import io.github.experionplanet.blocks.entity.ExpMushroomBlockEntity;
import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MPLBlockEntities {
    public static final BlockEntityType<ExpMushroomBlockEntity> EXP_MUSHROOMS = register("exp_mushrooms", ExpMushroomBlockEntity::new, MPLBlocks.SMALL_EXP_MUSHROOMS, MPLBlocks.MEDIUM_EXP_MUSHROOMS, MPLBlocks.LARGE_EXP_MUSHROOMS);

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
