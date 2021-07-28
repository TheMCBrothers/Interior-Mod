package tk.themcbros.interiormod.api.furniture;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import tk.themcbros.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
public class InteriorRegistries {

    /**
     * The registry for furniture materials.
     */
    public static final IForgeRegistry<FurnitureMaterial> FURNITURE_MATERIALS = new RegistryBuilder<FurnitureMaterial>()
            .setType(FurnitureMaterial.class)
            .setName(new ResourceLocation(InteriorAPI.MOD_ID, "furniture_material"))
            .create();

}
