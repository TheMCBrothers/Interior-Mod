package tk.themcbros.interiormod.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

import java.util.Objects;

/**
 * @author TheMCBrothers
 */
@Mod.EventBusSubscriber(modid = InteriorAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FurnitureMaterials {

    public static final DeferredRegister<FurnitureMaterial> FURNITURE_MATERIALS = DeferredRegister
            .create(InteriorRegistries.FURNITURE_MATERIALS, "minecraft");

    public static final RegistryObject<FurnitureMaterial> OAK_PLANKS = FURNITURE_MATERIALS.register("oak_planks",
            () -> new FurnitureMaterial(() -> Blocks.OAK_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> SPRUCE_PLANKS = FURNITURE_MATERIALS.register("spruce_planks",
            () -> new FurnitureMaterial(() -> Blocks.SPRUCE_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> BIRCH_PLANKS = FURNITURE_MATERIALS.register("birch_planks",
            () -> new FurnitureMaterial(() -> Blocks.BIRCH_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> JUNGLE_PLANKS = FURNITURE_MATERIALS.register("jungle_planks",
            () -> new FurnitureMaterial(() -> Blocks.JUNGLE_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> ACACIA_PLANKS = FURNITURE_MATERIALS.register("acacia_planks",
            () -> new FurnitureMaterial(() -> Blocks.ACACIA_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> DARK_OAK_PLANKS = FURNITURE_MATERIALS.register("dark_oak_planks",
            () -> new FurnitureMaterial(() -> Blocks.DARK_OAK_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> CRIMSON_PLANKS = FURNITURE_MATERIALS.register("crimson_planks",
            () -> new FurnitureMaterial(() -> Blocks.CRIMSON_PLANKS, null));
    public static final RegistryObject<FurnitureMaterial> WARPED_PLANKS = FURNITURE_MATERIALS.register("warped_planks",
            () -> new FurnitureMaterial(() -> Blocks.WARPED_PLANKS, null));

    @SubscribeEvent
    public static void onMaterialRegistry(final RegistryEvent.Register<FurnitureMaterial> event) {
        for (Block block : ForgeRegistries.BLOCKS) {
            ResourceLocation registryName = block.getRegistryName();
            assert registryName != null;
            if (registryName.getNamespace().equalsIgnoreCase("minecraft")
                    || !registryName.getPath().endsWith("_planks")) continue;

            if (registryName.getNamespace().equalsIgnoreCase("quark") && registryName.getPath().contains("vertical_"))
                continue;

            event.getRegistry().register(new FurnitureMaterial(() -> block, null).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
        }
    }

    public static ItemStack createItemStack(FurnitureType furnitureType, FurnitureMaterial primary, FurnitureMaterial secondary) {
        ItemStack stack = furnitureType.getStack();

        CompoundNBT tag = stack.getOrCreateChildTag("textures");
        tag.putString("primary", String.valueOf(primary.getRegistryName()));
        tag.putString("secondary", String.valueOf(secondary.getRegistryName()));
        return stack;
    }

}
