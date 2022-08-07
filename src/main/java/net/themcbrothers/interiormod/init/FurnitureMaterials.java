package net.themcbrothers.interiormod.init;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;

/**
 * @author TheMCBrothers
 */
@Mod.EventBusSubscriber(modid = InteriorAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FurnitureMaterials {
    public static final DeferredRegister<FurnitureMaterial> FURNITURE_MATERIALS = DeferredRegister
            .create(InteriorAPI.FURNITURE_KEY, "minecraft");

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
    public static void onMaterialRegistry(final RegisterEvent event) {
        if (event.getRegistryKey() == InteriorAPI.FURNITURE_KEY) {
            for (Block block : ForgeRegistries.BLOCKS) {
                ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
                assert registryName != null;
                if (registryName.getNamespace().equalsIgnoreCase("minecraft")
                        || !registryName.getPath().endsWith("_planks")) continue;

                if (registryName.getNamespace().equalsIgnoreCase("quark") && registryName.getPath().contains("vertical_"))
                    continue;

                event.register(InteriorAPI.FURNITURE_KEY, registryName, () -> new FurnitureMaterial(() -> block, null));
            }
        }
    }

    public static ItemStack createItemStack(FurnitureType furnitureType, FurnitureMaterial primary, FurnitureMaterial secondary) {
        ItemStack stack = furnitureType.getStack();

        CompoundTag tag = stack.getOrCreateTagElement("BlockEntityTag");
        tag.putString("primaryMaterial", String.valueOf(InteriorAPI.furnitureRegistry().getKey(primary)));
        tag.putString("secondaryMaterial", String.valueOf(InteriorAPI.furnitureRegistry().getKey(secondary)));
        return stack;
    }

    static void init() {
    }
}
