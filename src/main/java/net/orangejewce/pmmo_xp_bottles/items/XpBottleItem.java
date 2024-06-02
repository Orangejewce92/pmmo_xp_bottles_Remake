package net.orangejewce.pmmo_xp_bottles.items;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.orangejewce.pmmo_xp_bottles.init.PmmoXpBottlesModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XpBottleItem extends Item {
    private static final String EMPTY = "empty";
    private static final String AMOUNT = "amount";
    private final String skill;

    public XpBottleItem(String skill) {
        super(new Properties()
                .stacksTo(64)
                .rarity(Rarity.RARE)
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0f)
                        .alwaysEat()
                        .build()));
        this.skill = skill;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack instance = new ItemStack(this);
        instance.getOrCreateTag().putBoolean(EMPTY, false);
        instance.getTag().putLong(AMOUNT, 1500);
        return instance;
    }

    @Override
    public Component getName(ItemStack stack) {
        boolean isEmpty = stack.getOrCreateTag().getBoolean(EMPTY);
        MutableComponent start = isEmpty ? Component.translatable("pmmo_xp_bottles.empty") : Component.empty();
        MutableComponent skillComponent = Component.translatable("pmmo." + this.skill);
        return start.append(skillComponent).append(" ").append(Component.translatable("pmmo_xp_bottles.xp_bottle"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("pmmo_xp_bottles.volume", stack.getOrCreateTag().getLong(AMOUNT)));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return itemstack;
        }

        boolean isEmpty = itemstack.getOrCreateTag().getBoolean(EMPTY);
        long amount = itemstack.getTag().getLong(AMOUNT);
        ItemStack retval = new ItemStack(PmmoXpBottlesModItems.ALL_BOTTLES.get(skill).get());

        if (isEmpty) {
            if (APIUtils.getXp(skill, player) < amount) {
                entity.sendSystemMessage(Component.translatable("pmmo_xp_bottles.not_enough", Component.translatable("pmmo." + skill)));
                return itemstack;
            } else {
                APIUtils.addXp(skill, player, -amount);
                retval.getOrCreateTag().putBoolean(EMPTY, false);
                retval.getTag().putLong(AMOUNT, amount);
            }
        } else {
            APIUtils.addXp(skill, player, amount);
            retval.getOrCreateTag().putBoolean(EMPTY, true);
            retval.getTag().putLong(AMOUNT, amount);
        }

        super.finishUsingItem(itemstack, world, entity);

        if (itemstack.isEmpty()) {
            return retval;
        } else {
            if (!player.getAbilities().instabuild) {
                if (!player.getInventory().add(retval)) {
                    player.drop(retval, false);
                }
            }
            return itemstack;
        }
    }
}
