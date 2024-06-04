package net.orangejewce.pmmo_xp_bottles;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = pmmobottlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();

        // Inject bottles into entity loot tables
        if (name.equals(new ResourceLocation("minecraft", "entities/zombie")) ||
                name.equals(new ResourceLocation("minecraft", "entities/skeleton"))) {
            injectLoot(event.getTable(), "entities/combat_bottle");
        }
        if (name.equals(new ResourceLocation("minecraft", "entities/creeper")) ||
                name.equals(new ResourceLocation("minecraft", "entities/spider"))) {
            injectLoot(event.getTable(), "entities/endurance_bottle");
        }

        // Inject bottles into chest loot tables
        if (List.of(BuiltInLootTables.SPAWN_BONUS_CHEST,
                BuiltInLootTables.SIMPLE_DUNGEON,
                BuiltInLootTables.STRONGHOLD_CORRIDOR,
                BuiltInLootTables.STRONGHOLD_CROSSING,
                BuiltInLootTables.STRONGHOLD_LIBRARY,
                BuiltInLootTables.RUINED_PORTAL,
                BuiltInLootTables.VILLAGE_BUTCHER,
                BuiltInLootTables.VILLAGE_DESERT_HOUSE,
                BuiltInLootTables.VILLAGE_MASON,
                BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
                BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
                BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
                BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
                BuiltInLootTables.VILLAGE_TEMPLE,
                BuiltInLootTables.ABANDONED_MINESHAFT,
                BuiltInLootTables.SHIPWRECK_MAP,
                BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
                BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
                BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY,
                BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,
                BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON,
                BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,
                BuiltInLootTables.JUNGLE_TEMPLE,
                BuiltInLootTables.NETHER_BRIDGE,
                BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                BuiltInLootTables.UNDERWATER_RUIN_BIG,
                BuiltInLootTables.VILLAGE_TOOLSMITH,
                BuiltInLootTables.VILLAGE_WEAPONSMITH).contains(name))  {
            injectLoot(event.getTable(), "chests/bonus_chest");
        }
    }

    private static void injectLoot(LootTable table, String injectName) {
        LootPool pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootTableReference.lootTableReference(new ResourceLocation(pmmobottlesMod.MOD_ID, injectName)))
                .build();
        table.addPool(pool);
    }
}
