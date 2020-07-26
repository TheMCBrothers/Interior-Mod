package tk.themcbros.interiormod.compat.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraftforge.fml.InterModComms;

import java.util.function.Function;

/**
 * @author TheMCBrothers
 */
public class TheOneProbeCompat implements Function<ITheOneProbe, Void> {

    private TheOneProbeCompat() {
    }

    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(TOPProviderFurniture.INSTANCE);
        theOneProbe.registerBlockDisplayOverride(TOPProviderFurniture.INSTANCE);
        return null;
    }

    public static void registerCompat() {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeCompat::new);
    }
}
