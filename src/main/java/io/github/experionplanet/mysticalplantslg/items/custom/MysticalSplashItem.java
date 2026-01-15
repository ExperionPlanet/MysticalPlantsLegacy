package io.github.experionplanet.mysticalplantslg.items.custom;

import io.github.experionplanet.mysticalplantslg.entities.MysticalSplashEntity;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticalContents;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import java.util.List;

public class MysticalSplashItem extends Item implements ProjectileItem {
    private final Identifier sporeID;

    public MysticalSplashItem(Item.Settings settings, Identifier SporeID) {
        super(settings);
        this.sporeID = SporeID;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            MysticalSplashEntity splashEntity = new MysticalSplashEntity(world, user, sporeID);
            splashEntity.setItem(itemStack);
            splashEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);

            world.spawnEntity(splashEntity);

        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }
    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new MysticalSplashEntity(world, pos.getX(), pos.getY(), pos.getZ(), sporeID);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        PotionContentsComponent.buildTooltip(List.of(MysticalContents.SPORE_CONTENT.get(sporeID).statusEffect()), tooltip::add, 1.0F, context.getUpdateTickRate());


    }
}
