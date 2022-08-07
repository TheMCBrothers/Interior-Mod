package net.themcbrothers.interiormod.api.furniture;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.themcbrothers.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
@Mod.EventBusSubscriber(modid = InteriorAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InteriorRegistries {
    private static final RegistryBuilder<FurnitureMaterial> BUILDER = new RegistryBuilder<FurnitureMaterial>()
            .setName(new ResourceLocation(InteriorAPI.MOD_ID, "furniture_material"))
            .setDefaultKey(new ResourceLocation("minecraft:oak_planks"));

    @SubscribeEvent
    static void onRegistryCreate(final NewRegistryEvent event) {
        event.create(BUILDER);
    }
}
