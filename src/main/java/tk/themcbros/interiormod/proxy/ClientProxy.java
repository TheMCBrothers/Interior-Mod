package tk.themcbros.interiormod.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.client.renderer.SeatRenderer;
import tk.themcbros.interiormod.init.InteriorEntities;

/**
 * @author TheMCBrothers
 */
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(InteriorEntities.SEAT, SeatRenderer::new);

        InteriorMod.LOGGER.info("ClientProxy clientSetup");
    }

}
