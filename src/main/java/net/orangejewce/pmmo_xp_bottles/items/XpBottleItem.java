package net.orangejewce.pmmo_xp_bottles.items;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    private final String tier;

    public XpBottleItem(String skill, String tier) {
        super(new Properties()
                .stacksTo(64)
                .rarity(getRarityFromTier(tier))
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0f)
                        .alwaysEat()
                        .build()));
        this.skill = skill;
        this.tier = tier;
    }

    private static Rarity getRarityFromTier(String tier) {
        switch (tier) {
            case "common":
                return Rarity.COMMON;
            case "rare":
                return Rarity.UNCOMMON; // Typically green in Minecraft
            case "epic":
                return Rarity.RARE; // Blue
            default:
                return Rarity.COMMON;
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack instance = new ItemStack(this);
        initializeNBT(instance);
        return instance;
    }

    public void initializeNBT(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(EMPTY, false);
        long amount;
        switch (tier) {
            case "common":
                amount = 500;
                break;
            case "rare":
                amount = 3500;
                break;
            case "epic":
                amount = 9000;
                break;
            default:
                amount = 1500; // Default value, should not happen
                break;
        }
        stack.getOrCreateTag().putLong(AMOUNT, amount);
    }

    private void applySkillEffects(Player player) {
        switch (skill) {
            case "mining":
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 1)); // Haste effect for 30 seconds
                break;
            case "combat":
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1)); // Strength effect for 30 seconds
                break;
            case "farming":
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 600, 1)); // Luck effect for 30 seconds
                break;
            case "fishing":
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 600, 1)); // Luck effect for 30 seconds
                break;
            case "endurance":
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1)); // Regeneration effect for 30 seconds
                break;
            case "agility":
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1)); // Speed effect for 30 seconds
                break;
            case "magic":
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 1)); // Night Vision effect for 30 seconds
                break;
            case "swimming":
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 1)); // Water Breathing effect for 30 seconds
                break;
            default:
                break;
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        initializeNBT(stack);
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public Component getName(ItemStack stack) {
        boolean isEmpty = stack.getOrCreateTag().getBoolean(EMPTY);
        MutableComponent start = isEmpty ? Component.translatable("pmmo_xp_bottles.empty") : Component.empty();
        MutableComponent tierComponent = Component.translatable("pmmo_xp_bottles.tier." + this.tier);
        MutableComponent skillComponent = Component.translatable("pmmo." + this.skill);
        return start.append(tierComponent).append(" ").append(skillComponent).append(" ").append(Component.translatable("pmmo_xp_bottles.xp_bottle"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("pmmo_xp_bottles.volume", stack.getOrCreateTag().getLong(AMOUNT)));
        tooltip.add(Component.translatable("pmmo_xp_bottles.skill", Component.translatable("pmmo." + this.skill)));
        tooltip.add(Component.translatable("pmmo_xp_bottles.effect", getSkillEffectDescription(this.skill)));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    private Component getSkillEffectDescription(String skill) {
        switch (skill) {
            case "mining":
                return Component.translatable("pmmo_xp_bottles.effect.haste");
            case "combat":
                return Component.translatable("pmmo_xp_bottles.effect.strength");
            case "farming":
                return Component.translatable("pmmo_xp_bottles.effect.luck");
            case "fishing":
                return Component.translatable("pmmo_xp_bottles.effect.luck");
            case "agility":
                return Component.translatable("pmmo_xp_bottles.effect.speed");
            case "endurance":
                return Component.translatable("pmmo_xp_bottles.effect.regen");
            case "swimming":
                return Component.translatable("pmmo_xp_bottles.effect.water_breathing");
            case "magic":
                return Component.translatable("pmmo_xp_bottles.effect.night_vision");
            default:
                return Component.translatable("pmmo_xp_bottles.effect.none");
        }
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
        ItemStack retval = new ItemStack(PmmoXpBottlesModItems.ALL_BOTTLES.get(skill + "_" + tier + "_bottle").get());

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
            applySkillEffects(player); // Apply skill-based effects
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
