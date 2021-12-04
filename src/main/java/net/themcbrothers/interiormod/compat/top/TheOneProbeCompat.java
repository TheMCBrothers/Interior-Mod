package net.themcbrothers.interiormod.compat.top;

/**
 * @author TheMCBrothers
 */
public class TheOneProbeCompat/* implements Function<ITheOneProbe, Void> {

    private TheOneProbeCompat() {
    }

    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerProvider(TOPProviderFurniture.INSTANCE);
        return null;
    }

    public static void registerCompat() {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeCompat::new);
    }
}
*/ {
}