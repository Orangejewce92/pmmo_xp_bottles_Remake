package net.orangejewce.pmmo_xp_bottles.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

@Mod.EventBusSubscriber(modid = pmmobottlesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, pmmobottlesMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NEW_TAB = CREATIVE_MODE_TABS.register("bottles_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(PmmoXpBottlesModItems.ARCHERY_BOTTLE.get()))
                    .title(Component.translatable("itemGroup.pmmo_xp_bottles"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
        eventBus.addListener(ModCreativeModTabs::addItemsToTabs);
    }

    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == NEW_TAB.get()) {
            PmmoXpBottlesModItems.ALL_BOTTLES.values().forEach(item -> event.accept(item.get()));
        }
    }
}
