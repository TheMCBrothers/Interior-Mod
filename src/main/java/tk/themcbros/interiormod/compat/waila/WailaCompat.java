package tk.themcbros.interiormod.compat.waila;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.util.ResourceLocation;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

/**
 * @author TheMCBrothers
 */
@WailaPlugin(InteriorAPI.MOD_ID)
public class WailaCompat implements IWailaPlugin {

    static final ResourceLocation CONFIG_DISPLAY_FURNITURE_MATERIALS = new ResourceLocation(InteriorAPI.MOD_ID, "display_furniture_materials");

    @Override
    public void register(IRegistrar registrar) {
        registrar.addConfig(CONFIG_DISPLAY_FURNITURE_MATERIALS, true);

        registrar.registerComponentProvider(HUDHandlerFurniture.INSTANCE, TooltipPosition.BODY, FurnitureTileEntity.class);
        registrar.registerBlockDataProvider(HUDHandlerFurniture.INSTANCE, FurnitureTileEntity.class);
    }
}
