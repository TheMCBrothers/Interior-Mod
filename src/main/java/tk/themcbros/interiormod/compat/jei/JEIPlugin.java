package tk.themcbros.interiormod.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.MethodsReturnNonnullByDefault;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.init.InteriorItems;

/**
 * @author TheMCBrothers
 */
@JeiPlugin
@MethodsReturnNonnullByDefault
public class JEIPlugin implements IModPlugin {

    private final ResourceLocation PLUGIN_UID = new ResourceLocation(InteriorMod.MOD_ID, "jeiplugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(InteriorItems.CHAIR, InteriorItems.TABLE);
    }

}
