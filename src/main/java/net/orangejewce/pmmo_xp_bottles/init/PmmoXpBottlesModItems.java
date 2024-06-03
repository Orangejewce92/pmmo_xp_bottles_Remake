package net.orangejewce.pmmo_xp_bottles.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.items.XpBottleItem;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PmmoXpBottlesModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, pmmobottlesMod.MOD_ID);

    public static final Map<String, RegistryObject<Item>> ALL_BOTTLES = new HashMap<>();

    static {
        for (XpBottleItem.Skill skill : XpBottleItem.Skill.values()) {
            for (Rarity tier : Arrays.stream(Rarity.values()).filter(rarity -> rarity != Rarity.UNCOMMON).toList()) {
                make(skill, tier);
            }
        }
    }



    private static RegistryObject<Item> make(XpBottleItem.Skill skill, Rarity tier) {
        String registryName = skill.skill() + "_" + tier.name().toLowerCase() + "_bottle";
        RegistryObject<Item> obj = REGISTRY.register(registryName, () -> new XpBottleItem(skill, tier));
        ALL_BOTTLES.put(registryName, obj);
        return obj;
    }
}
