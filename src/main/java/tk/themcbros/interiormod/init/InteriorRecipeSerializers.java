package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.recipe.FurnitureShapedRecipe;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeSerializers {
    private static final List<IRecipeSerializer<?>> SERIALIZERS = Lists.newArrayList();

    public static final IRecipeSerializer<FurnitureShapedRecipe> FURNITURE_SHAPED = register("furniture_shaped", new FurnitureShapedRecipe.Serializer());

    private static <T extends IRecipeSerializer<?>> T register(String registryName, T serializer) {
        serializer.setRegistryName(InteriorMod.getId(registryName));
        SERIALIZERS.add(serializer);
        return serializer;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            SERIALIZERS.forEach(event.getRegistry()::register);
        }
    }
}
