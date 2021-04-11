package tk.themcbros.interiormod.proxy;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.client.models.block.furniture.FurnitureModel;
import tk.themcbros.interiormod.client.renderer.SeatRenderer;
import tk.themcbros.interiormod.client.screen.FurnitureWorkbenchScreen;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorContainers;
import tk.themcbros.interiormod.init.InteriorEntities;

/**
 * @author TheMCBrothers
 */
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modelLoading);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(InteriorEntities.SEAT, SeatRenderer::new);

        ScreenManager.registerFactory(InteriorContainers.FURNITURE_WORKBENCH, FurnitureWorkbenchScreen::new);

        RenderTypeLookup.setRenderLayer(InteriorBlocks.FRIDGE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(InteriorBlocks.TRASH_CAN, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(InteriorBlocks.MODERN_DOOR, RenderType.getCutout());

        InteriorMod.LOGGER.info("ClientProxy clientSetup");
    }

    private void modelLoading(final ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(InteriorMod.getId("furniture"), FurnitureModel.Loader.INSTANCE);
    }

}
