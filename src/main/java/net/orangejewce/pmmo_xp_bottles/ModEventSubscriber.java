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
        if (name.equals(BuiltInLootTables.SPAWN_BONUS_CHEST) ||
                name.equals(new ResourceLocation("minecraft", "chests/simple_dungeon")) ||
                name.equals(new ResourceLocation("minecraft", "chests/stronghold_corridor")) ||
                name.equals(new ResourceLocation("minecraft", "chests/stronghold_crossing")) ||
                name.equals(new ResourceLocation("minecraft", "chests/stronghold_library")) ||
                name.equals(new ResourceLocation("minecraft", "chests/spawn_bonus_chest")) ||
                name.equals(new ResourceLocation("minecraft", "chests/ruined_portal")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_butcher")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_desert_house")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_mason")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_plains_house")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_savanna_house")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_snowy_house")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_taiga_house")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_temple")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_toolsmith")) ||
                name.equals(new ResourceLocation("minecraft", "chests/village/village_weaponsmith"))) {
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
