package net.orangejewce.pmmo_xp_bottles.items;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
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
    public enum Skill{
        ARCHERY(null, "none"),
        WOODCUTTING(null, "none"),
        BUILDING(null, "none"),
        EXCAVATION(null, "none"),
        GUNSLINGING(null, "none"),
        SMITHING(null, "none"),
        CRAFTING(null, "none"),
        SLAYER(null, "none"),
        HUNTER(null, "none"),
        TAMING(null, "none"),
        COOKING(null, "none"),
        ALCHEMY(null, "none"),
        ENGINEERING(null, "none"),
        SAILING(null, "none"),
        MINING(MobEffects.DIG_SPEED, "haste"),
        COMBAT(MobEffects.DAMAGE_BOOST, "strength"),
        FARMING(MobEffects.LUCK, "luck"),
        FISHING(MobEffects.LUCK, "luck"),
        ENDURANCE(MobEffects.REGENERATION, "speed"),
        AGILITY(MobEffects.MOVEMENT_SPEED, "regen"),
        MAGIC(MobEffects.NIGHT_VISION, "water_breathing"),
        SWIMMING(MobEffects.WATER_BREATHING, "night_vision");

        MobEffect effect;
        String key;
        Skill(MobEffect effect, String key) {
            this.effect = effect;
            this.key = key;
        }

        public String skill() {return name().toLowerCase();}

        public Component getTranslation() {
            return Component.translatable("pmmo_xp_bottles.effect."+key);
        }

        public MobEffectInstance getEffectInstance() {
            return new MobEffectInstance(effect, 600, 1);
        }
    }
    private static final String EMPTY = "empty";
    private static final String AMOUNT = "amount";
    private final Skill skill;
    private final Rarity tier;

    public XpBottleItem(Skill skill, Rarity tier) {
        super(new Properties()
                .stacksTo(64)
                .rarity(tier)
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0f)
                        .alwaysEat()
                        .build()));
        this.skill = skill;
        this.tier = tier;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack instance = new ItemStack(this);
        initializeNBT(instance);
        return instance;
    }

    public void initializeNBT(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(EMPTY, false);
        long amount = switch (tier) {
            case COMMON -> 500;
            case UNCOMMON -> 1500;
            case RARE -> 3500;
            case EPIC -> 9000;
        };
        stack.getOrCreateTag().putLong(AMOUNT, amount);
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
        MutableComponent tierComponent = Component.translatable("pmmo_xp_bottles.tier." + this.tier.name().toLowerCase());
        MutableComponent skillComponent = Component.translatable("pmmo." + this.skill.skill());
        return start.append(tierComponent).append(" ").append(skillComponent).append(" ").append(Component.translatable("pmmo_xp_bottles.xp_bottle"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("pmmo_xp_bottles.volume", stack.getOrCreateTag().getLong(AMOUNT)));
        tooltip.add(Component.translatable("pmmo_xp_bottles.skill", Component.translatable("pmmo." + this.skill.skill())));
        tooltip.add(Component.translatable("pmmo_xp_bottles.effect", skill.getTranslation()));
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
        ItemStack retval = new ItemStack(this);

        if (isEmpty) {
            if (APIUtils.getXp(skill.skill(), player) < amount) {
                entity.sendSystemMessage(Component.translatable("pmmo_xp_bottles.not_enough", Component.translatable("pmmo." + skill)));
                return itemstack;
            } else {
                APIUtils.addXp(skill.skill(), player, -amount);
                retval.getOrCreateTag().putBoolean(EMPTY, false);
                retval.getTag().putLong(AMOUNT, amount);
            }
        } else {
            APIUtils.addXp(skill.skill(), player, amount);
            if (skill.effect != null)
                player.addEffect(skill.getEffectInstance()); // Apply skill-based effects
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
