package io.github.experionplanet.blocks;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.baseclass.PlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.ExpMushroomBlockEntity;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExpMushroomBlock extends PlantBlockWithEntity {
    public static final BooleanProperty STEPPED = BooleanProperty.of("stepped");
    private static final VoxelShape SHAPE_2 = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
    private final int SHAPE_NUM;
    private final int AMOUNT_EXP;
    private final VoxelShape CHOSEN_SHAPE;

    public ExpMushroomBlock(Settings settings, int shapenum, int amountexp) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(STEPPED, false));

        if (shapenum == 1) {
            this.CHOSEN_SHAPE = PLANT_SHAPE;
        }else {
            this.CHOSEN_SHAPE = SHAPE_2;
        }

        this.SHAPE_NUM = shapenum;
        this.AMOUNT_EXP = amountexp;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(STEPPED);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec((v) -> new ExpMushroomBlock(settings, this.SHAPE_NUM, this.AMOUNT_EXP));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ExpMushroomBlockEntity(pos, state);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            if (!state.get(STEPPED) && entity instanceof LivingEntity) {
                world.scheduleBlockTick(pos, this, 0);
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ExpMushroomBlockEntity blockEntity = (ExpMushroomBlockEntity) world.getBlockEntity(pos);

        if (!state.get(STEPPED)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_PLACE, SoundCategory.BLOCKS);
            world.setBlockState(pos, state.with(STEPPED, true));
            blockEntity.Stepped(world, pos);

        }

        boolean stillPressed = this.hasEntityOnTop(world, pos);

        if (stillPressed) {
            world.scheduleBlockTick(pos, this, 5);
        }else {
            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS);
            world.setBlockState(pos, state.with(STEPPED, false));
            blockEntity.Unstepped(world);
        }


    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    private boolean hasEntityOnTop(World world, BlockPos pos) {
        Box box = new Box(pos).contract(0, 1, 0).expand(0, this.CHOSEN_SHAPE.getMax(Direction.Axis.Y) / 16, 0);
        return !world.getNonSpectatingEntities(LivingEntity.class, box).isEmpty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return this.CHOSEN_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            world.breakBlock(pos, false, player);
            ExperienceOrbEntity.spawn((ServerWorld) world, ExperionUtils.v3dConvert(pos, true), this.AMOUNT_EXP);
        }
        return ActionResult.SUCCESS;
    }
}
