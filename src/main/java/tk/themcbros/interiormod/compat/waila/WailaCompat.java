package tk.themcbros.interiormod.compat.waila;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import tk.themcbros.interiormod.blocks.FurnitureBlock;

@WailaPlugin
public class WailaCompat implements IWailaPlugin {

	@Override
	public void register(IRegistrar registrar) {
		registrar.registerComponentProvider(HUDHandlerFurniture.INSTANCE, TooltipPosition.HEAD, FurnitureBlock.class);
	}

}
