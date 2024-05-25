package net.orangejewce.pmmo_xp_bottles.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.orangejewce.pmmo_xp_bottles.items.XpBottleItem;

import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

import java.util.HashMap;
import java.util.Map;

public class PmmoXpBottlesModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, pmmobottlesMod.MOD_ID);

    public static final Map<String, RegistryObject<Item>> ALL_BOTTLES = new HashMap<>();
    public static final RegistryObject<Item> ARCHERY_BOTTLE = make("archery");

    static {
        make("woodcutting");
        make("mining");
        make("building");
        make("excavation");
        make("farming");
        make("agility");
        make("endurance");
        make("combat");
        make("gunslinging");
        make("smithing");
        make("crafting");
        make("magic");
        make("slayer");
        make("hunter");
        make("taming");
        make("cooking");
        make("alchemy");
        make("engineering");
        make("fishing");
        make("sailing");
        make("swimming");
    }

    private static RegistryObject<Item> make(String skill) {
        RegistryObject<Item> obj = REGISTRY.register(skill, () -> new XpBottleItem(skill));
        ALL_BOTTLES.put(skill, obj);
        return obj;
    }
}
