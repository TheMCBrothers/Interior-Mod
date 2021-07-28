package tk.themcbros.interiormod.compat.waila;

import net.minecraft.resources.ResourceLocation;
import tk.themcbros.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
//@WailaPlugin(InteriorAPI.MOD_ID)
public class WailaCompat /*implements IWailaPlugin*/ {

    static final ResourceLocation CONFIG_DISPLAY_FURNITURE_MATERIALS = new ResourceLocation(InteriorAPI.MOD_ID, "display_furniture_materials");

//    @Override
//    public void register(IRegistrar registrar) {
//        registrar.addConfig(CONFIG_DISPLAY_FURNITURE_MATERIALS, true);
//
//        registrar.registerComponentProvider(HUDHandlerFurniture.INSTANCE, TooltipPosition.BODY, FurnitureTileEntity.class);
//        registrar.registerBlockDataProvider(HUDHandlerFurniture.INSTANCE, FurnitureTileEntity.class);
//    }
}
