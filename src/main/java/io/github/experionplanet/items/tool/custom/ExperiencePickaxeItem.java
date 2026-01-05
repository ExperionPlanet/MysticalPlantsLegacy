package io.github.experionplanet.items.tool.custom;

import io.github.experionplanet.MPLMain;
import io.github.experionplanet.init.MPLComponentTypes;
import io.github.experionplanet.init.MPLBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ExperiencePickaxeItem extends PickaxeItem {
    public static final int MAX_FILLS = 64;
    public static final ComponentType<Integer> EXP_FILLS = MPLComponentTypes.EXP_FILLS;

    public ExperiencePickaxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public static int getEXP_FILLS(ItemStack stack) {
        if (stack.contains(EXP_FILLS)) {
            return stack.get(EXP_FILLS);
        }
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (MPLMain.showInfo) {
            tooltip.add(Text.literal("YEEEEEHAAAAAWWWWWW"));
        }else {
            int totalBars = getEXP_FILLS(stack) / 10;
            int whiteBars = 10 - totalBars;
            String str = "";

            if (totalBars > 0) {
                for (int i = 1; i <= totalBars; i++) {
                    str = str + "▮";
                }
            }

            Text greenBar = Text.literal(str).formatted(Formatting.GREEN).formatted(Formatting.BOLD);
            str = "";

            for (int i = 1; i <= whiteBars; i++) {
                str = str + "▮";
            }

            Text whiteBar = Text.literal(str).formatted(Formatting.GRAY).formatted(Formatting.BOLD);
            Text ResText = Text.literal("EXP: ").append(greenBar).append(whiteBar);
            tooltip.add(ResText);
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean succ = super.postMine(stack, world, state, pos, miner);

        if (succ) {
            if (EnchantmentHelper.getLevel(world.getRegistryManager().getWrapperOrThrow(Enchantments.SILK_TOUCH.getRegistryRef()).getOrThrow(Enchantments.SILK_TOUCH), stack) == 0 && getEXP_FILLS(stack) < MAX_FILLS) {
                if (state.isIn(MPLBlockTags.ORES)) {
                    stack.set(EXP_FILLS, getEXP_FILLS(stack) + 1);
                }
            }
        }

        return succ;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            if (getEXP_FILLS(stack) >= MAX_FILLS) {
                stack.set(EXP_FILLS, 0);
                ExperienceOrbEntity.spawn((ServerWorld) world, user.getPos().add(0, 0.5d, 0), world.getRandom().nextBetween(74, 122));

                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }
}
