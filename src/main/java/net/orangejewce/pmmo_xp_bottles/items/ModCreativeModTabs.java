package net.orangejewce.pmmo_xp_bottles.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, pmmobottlesMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NEW_TAB = CREATIVE_MODE_TABS.register("bottles_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.DIAMOND))
                    .title(Component.nullToEmpty("PMMO XP Bottles"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(Items.DIAMOND);
            })

        .build());
public static void register(IEventBus eventBus) { CREATIVE_MODE_TABS.register(eventBus);
        }
}
