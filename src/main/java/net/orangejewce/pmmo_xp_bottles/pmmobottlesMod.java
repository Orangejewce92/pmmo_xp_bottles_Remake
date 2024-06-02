package net.orangejewce.pmmo_xp_bottles;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orangejewce.pmmo_xp_bottles.client.ClientSetup;
import net.orangejewce.pmmo_xp_bottles.datagen.ItemModelGen;
import net.orangejewce.pmmo_xp_bottles.init.ModCreativeModTabs;
import net.orangejewce.pmmo_xp_bottles.init.PmmoXpBottlesModItems;
import org.slf4j.Logger;

@Mod(pmmobottlesMod.MOD_ID)
public class pmmobottlesMod {
    public static final String MOD_ID = "pmmo_xp_bottles";
    private static final Logger LOGGER = LogUtils.getLogger();

    public pmmobottlesMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items and creative tabs
        PmmoXpBottlesModItems.REGISTRY.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ModEventSubscriber.class);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ClientSetup::init);
        modEventBus.addListener(this::genData);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Server starting code
    }

    public void genData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new ItemModelGen(generator, event.getExistingFileHelper()));
    }
}
