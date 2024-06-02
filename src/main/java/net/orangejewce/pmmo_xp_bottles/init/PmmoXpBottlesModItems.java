package net.orangejewce.pmmo_xp_bottles.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.items.XpBottleItem;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

import java.util.HashMap;
import java.util.Map;

public class PmmoXpBottlesModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, pmmobottlesMod.MOD_ID);

    public static final Map<String, RegistryObject<Item>> ALL_BOTTLES = new HashMap<>();

    private static final String[] SKILLS = {
            "archery", "woodcutting", "mining", "building", "excavation", "farming",
            "agility", "endurance", "combat", "gunslinging", "smithing", "crafting",
            "magic", "slayer", "hunter", "taming", "cooking", "alchemy",
            "engineering", "fishing", "sailing", "swimming"
    };

    private static final String[] TIERS = {"common", "rare", "epic"};

    static {
        for (String skill : SKILLS) {
            for (String tier : TIERS) {
                make(skill, tier);
            }
        }
    }

    private static RegistryObject<Item> make(String skill, String tier) {
        String registryName = skill + "_" + tier + "_bottle";
        RegistryObject<Item> obj = REGISTRY.register(registryName, () -> new XpBottleItem(skill, tier));
        ALL_BOTTLES.put(registryName, obj);
        return obj;
    }
}
