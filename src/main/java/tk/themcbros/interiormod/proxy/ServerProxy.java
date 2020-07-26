package tk.themcbros.interiormod.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;

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
