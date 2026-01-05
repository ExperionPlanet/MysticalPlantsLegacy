package io.github.experionplanet.blocks.custom;

import io.github.experionplanet.blocks.MysticalPlantBlock;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.List;

public class ShulkuraBlock extends MysticalPlantBlock {
    public static final DirectionProperty FACING;
    public static final IntProperty FLOWER_AMOUNT;

    private static final List<VoxelShape> SHAPE_LIST = List.of(
            Block.createCuboidShape((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)16.0F, (double)11.0F),
            Block.createCuboidShape((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)16.0F, (double)13.0F),
            Block.createCuboidShape((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F)
    );

    static {
        FACING = Properties.HORIZONTAL_FACING;
        FLOWER_AMOUNT = IntProperty.of("shulkura_amount", 1, 3);
    }

    public ShulkuraBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(FLOWER_AMOUNT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FLOWER_AMOUNT);
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && (Integer)state.get(FLOWER_AMOUNT) < 3 ? true : super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return blockState.isOf(this) ? (BlockState)blockState.with(FLOWER_AMOUNT, Math.min(3, (Integer)blockState.get(FLOWER_AMOUNT) + 1)) : (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.1f) {

            int r = 3;

            Vec3d v = MysticalUtils.v3dConvert(pos, true).add(new Vec3d(MysticalUtils.doubleInRange(random,-0.5d, 0.5d),0, MysticalUtils.doubleInRange(random,-0.5d, 0.5d)));

            /*
            Box box = new Box(v.x - r, v.y - r, v.z - r, v.x + r, v.y + r, v.z + r);

            List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, a -> true);

            if (!list.isEmpty()) {

            }*/

            ShulkerBulletEntity bullet = new ShulkerBulletEntity(EntityType.SHULKER_BULLET, world);
            bullet.refreshPositionAndAngles(v.x, v.y, v.z, bullet.getYaw(), bullet.getPitch());
            bullet.setVelocity(new Vec3d(0, 1, 0).multiply(0.5));
            world.spawnEntity(bullet);        }
    }

    private static VoxelShape getShape(BlockState state) {
        int amount = state.get(FLOWER_AMOUNT);
        return SHAPE_LIST.get(amount - 1);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }
}
