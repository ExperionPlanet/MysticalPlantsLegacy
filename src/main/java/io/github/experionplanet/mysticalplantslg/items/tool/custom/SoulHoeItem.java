package io.github.experionplanet.mysticalplantslg.items.tool.custom;

import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static io.github.experionplanet.mysticalplantslg.init.MPLComponentTypes.SOULS;

public class SoulHoeItem extends HoeItem {
    public static final int MAX_SOULS = 50;

    public SoulHoeItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static int getSouls(ItemStack stack) {
        if (stack.contains(SOULS)) {
            return stack.get(SOULS);
        }

        return 0;
    }

    /*
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (!player.isSneaking()) {
            ActionResult ar = super.useOnBlock(context);
            if (!ar.equals(ActionResult.PASS)) {
                return ar;
            }

            World world = context.getWorld();
            ItemStack stack = context.getStack();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (!world.isClient() && state.getBlock() instanceof FarmlandBlock) {
                int remaining = getSouls(stack);
                if (remaining > 0) {
                    if (state.get(SOUL_NOT_ATTUNED)) {
                        world.setBlockState(pos, state.with(SOUL_NOT_ATTUNED, false));
                        if (world.getRandom().nextBoolean()) {
                            stack.set(SOULS, remaining - 1);
                        }
                        return ActionResult.SUCCESS;
                    }
                }

            }
        }

        return ActionResult.PASS;
    }*/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient() && user.isSneaking()) {
            int remaining = getSouls(stack);

            if (remaining > 1) {
                boolean consume = false;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos targPos = user.getBlockPos().add(x,y,z);
                            BlockState state = world.getBlockState(targPos);
                            ExperionLogger.Print(targPos.toString());
                            if (state.getBlock() instanceof CropBlock cropBlock) {
                                if (cropBlock.isFertilizable(world, targPos, state)) {
                                    if (cropBlock.canGrow(world, world.getRandom(), targPos, state)) {
                                        cropBlock.grow((ServerWorld) world, world.getRandom(), targPos, state);
                                        Vec3d v = targPos.toCenterPos();
                                        ((ServerWorld) world).spawnParticles(ParticleTypes.SCULK_SOUL, v.getX(), v.getY(), v.getZ(), 4, 0.25, 0.25, 0.25, 0);
                                        consume = true;
                                    }
                                }
                            }
                        }
                    }
                }
                if (consume) {
                    stack.set(SOULS, remaining - 1);
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), MPLSoundEvents.SOUL_HOE_GROWING, SoundCategory.PLAYERS);
                    ((ServerWorld) world).spawnParticles(MPLParticles.SOUL_BELL_BLASTWAVE, user.getX(), user.getY() + 0.2d, user.getZ(), 1, 0, 0, 0, 0);
                }
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (getSouls(stack) > 0) {
            return true;
        }
        return super.isItemBarVisible(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int curr = getSouls(stack);
        if (curr > 0) {
            return MathHelper.clamp(Math.round(13.0F - (float)(MAX_SOULS - curr) * 13.0F / (float) MAX_SOULS), 0, 13);
        }
        return super.getItemBarStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (getSouls(stack) > 0) {
            return ColorHelper.Argb.getArgb(75,246,242);
        }
        return super.getItemBarColor(stack);
    }

    public static int fillings(ItemStack stack, int fuel) {
        int curr = getSouls(stack);
        if (curr < MAX_SOULS) {

            int emptyFills = MAX_SOULS - curr;
            int total = Math.min(fuel, emptyFills);
            stack.set(SOULS, curr + total);

            return total;
        }
        return 0;
    };

    public static void fillings(ItemStack stack, ItemStack fuel) {
        int totalFuel = fillings(stack, fuel.getCount());

        if (totalFuel > 0) {
            fuel.decrement(totalFuel);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.soul_hoe_1").formatted(Formatting.BOLD).formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.soul_hoe_2"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
