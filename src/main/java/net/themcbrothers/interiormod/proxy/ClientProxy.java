package net.themcbrothers.interiormod.proxy;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.client.models.block.furniture.FurnitureModel;
import net.themcbrothers.interiormod.client.models.block.furniture.NewFurnitureModel;
import net.themcbrothers.interiormod.client.renderer.SeatRenderer;
import net.themcbrothers.interiormod.client.screen.FurnitureWorkbenchScreen;
import net.themcbrothers.interiormod.init.*;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        bus.addListener(this::registerRecipeBook);
        bus.addListener(this::modelLoading);
        bus.addListener(this::entityRender);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(InteriorMenuTypes.FURNITURE_WORKBENCH.get(), FurnitureWorkbenchScreen::new);

        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.FRIDGE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.TRASH_CAN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(InteriorBlocks.MODERN_DOOR.get(), RenderType.cutout());

        InteriorMod.LOGGER.info("ClientProxy clientSetup");
    }

    private void registerRecipeBook(final RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(InteriorRecipeBookExtensions.FURNITURE_WORKBENCH_TYPE, List.of(InteriorRecipeBookExtensions.FURNITURE_WORKBENCH_CATEGORY));
        event.registerRecipeCategoryFinder(InteriorRecipeTypes.FURNITURE_CRAFTING.get(), recipe -> InteriorRecipeBookExtensions.FURNITURE_WORKBENCH_CATEGORY);
    }

    private void modelLoading(final ModelEvent.RegisterGeometryLoaders event) {
        event.register("furniture", FurnitureModel.Loader.INSTANCE);
    }

    private void entityRender(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InteriorEntityTypes.SEAT.get(), SeatRenderer::new);
    }

}
