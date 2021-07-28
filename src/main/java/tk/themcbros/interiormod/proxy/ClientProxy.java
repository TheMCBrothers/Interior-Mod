package tk.themcbros.interiormod.proxy;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityRender);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(InteriorContainers.FURNITURE_WORKBENCH, FurnitureWorkbenchScreen::new);

        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.FRIDGE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.TRASH_CAN, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.MODERN_DOOR, RenderType.cutout());

        InteriorMod.LOGGER.info("ClientProxy clientSetup");
    }

    private void modelLoading(final ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(InteriorMod.getId("furniture"), FurnitureModel.Loader.INSTANCE);
    }

    private void entityRender(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InteriorEntities.SEAT, SeatRenderer::new);
    }

}
