package tk.themcbros.interiormod.api;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.IForgeRegistry;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;

/**
 * @author TheMCBrothers
 */
public class InteriorAPI {

    public static final String MOD_ID = "interiormod";

    private static IInteriorAPI instance;

    /**
     * @return The InteriorMod API Instance
     */
    public static IInteriorAPI getInstance() {
        return instance;
    }

    public interface IInteriorAPI {

        /**
         * @return The furniture registry for all materials
         */
        IForgeRegistry<FurnitureMaterial> getFurnitureMaterialRegistry();

    }

    /**
     * Internal use! Do not use this!
     *
     * @param inst instance of api
     */
    public static void init(IInteriorAPI inst) {
        if (instance == null && ModList.get().isLoaded("interiormod")) {
            instance = inst;
        } else {
            throw new IllegalStateException("This method should be called from InteriorMod only!");
        }
    }

}
