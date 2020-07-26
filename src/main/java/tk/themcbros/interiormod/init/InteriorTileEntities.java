package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;
import tk.themcbros.interiormod.tileentity.FridgeTileEntity;
import tk.themcbros.interiormod.tileentity.NightlightLampTileEntity;
import tk.themcbros.interiormod.tileentity.TableTileEntity;

/**
 * @author TheMCBrothers
 */
public class InteriorTileEntities {

    private static final List<TileEntityType<?>> TILE_ENTITY_TYPES = Lists.newArrayList();

    public static final TileEntityType<ChairTileEntity> CHAIR = registerTileEntityType("chair", TileEntityType.Builder.create(ChairTileEntity::new, InteriorBlocks.CHAIR).build(null));
    public static final TileEntityType<TableTileEntity> TABLE = registerTileEntityType("table", TileEntityType.Builder.create(TableTileEntity::new, InteriorBlocks.TABLE).build(null));
    public static final TileEntityType<FridgeTileEntity> FRIDGE = registerTileEntityType("fridge", TileEntityType.Builder.create(FridgeTileEntity::new, InteriorBlocks.FRIDGE).build(null));
    public static final TileEntityType<NightlightLampTileEntity> LAMP = registerTileEntityType("lamp", TileEntityType.Builder.create(NightlightLampTileEntity::new, InteriorBlocks.LAMP, InteriorBlocks.LAMP_ON_A_STICK).build(null));

    private static <T extends TileEntity> TileEntityType<T> registerTileEntityType(String registryName, TileEntityType<T> tileEntityType) {
        tileEntityType.setRegistryName(InteriorMod.getId(registryName));
        TILE_ENTITY_TYPES.add(tileEntityType);
        return tileEntityType;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            TILE_ENTITY_TYPES.forEach(event.getRegistry()::register);
        }
    }

}
