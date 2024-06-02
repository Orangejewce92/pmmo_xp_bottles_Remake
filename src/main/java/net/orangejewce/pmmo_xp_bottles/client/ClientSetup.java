package net.orangejewce.pmmo_xp_bottles.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.init.PmmoXpBottlesModItems;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

@Mod.EventBusSubscriber(modid = pmmobottlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    private static final ResourceLocation EMPTY = new ResourceLocation(pmmobottlesMod.MOD_ID, "empty");

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<Item> item : PmmoXpBottlesModItems.ALL_BOTTLES.values()) {
                ItemProperties.register(item.get(), EMPTY, ClientSetup::isEmpty);
            }
        });
    }

    private static float isEmpty(ItemStack stack, ClientLevel level, LivingEntity entity, int e) {
        return stack.getOrCreateTag().getBoolean("empty") ? 1f : 0f;
    }
}
