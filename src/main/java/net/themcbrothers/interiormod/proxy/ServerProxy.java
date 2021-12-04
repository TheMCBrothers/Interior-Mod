package net.themcbrothers.interiormod.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.themcbrothers.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class ServerProxy extends CommonProxy {

    public ServerProxy() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
    }

    private void serverSetup(FMLDedicatedServerSetupEvent event) {
        InteriorMod.LOGGER.info("ServerProxy serverSetup");
    }

}
