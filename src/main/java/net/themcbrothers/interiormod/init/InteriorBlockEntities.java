package net.themcbrothers.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.blockentity.ChairBlockEntity;
import net.themcbrothers.interiormod.blockentity.FridgeBlockEntity;
import net.themcbrothers.interiormod.blockentity.NightlightLampBlockEntity;
import net.themcbrothers.interiormod.blockentity.TableBlockEntity;
import net.themcbrothers.interiormod.InteriorMod;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorBlockEntities {

    private static final List<BlockEntityType<?>> BLOCK_ENTITY_TYPES = Lists.newArrayList();

    public static final BlockEntityType<ChairBlockEntity> CHAIR = registerBlockEntityType("chair", BlockEntityType.Builder.of(ChairBlockEntity::new, InteriorBlocks.CHAIR).build(null));
    public static final BlockEntityType<TableBlockEntity> TABLE = registerBlockEntityType("table", BlockEntityType.Builder.of(TableBlockEntity::new, InteriorBlocks.TABLE).build(null));
    public static final BlockEntityType<FridgeBlockEntity> FRIDGE = registerBlockEntityType("fridge", BlockEntityType.Builder.of(FridgeBlockEntity::new, InteriorBlocks.FRIDGE).build(null));
    public static final BlockEntityType<NightlightLampBlockEntity> LAMP = registerBlockEntityType("lamp", BlockEntityType.Builder.of(NightlightLampBlockEntity::new, InteriorBlocks.LAMP, InteriorBlocks.LAMP_ON_A_STICK).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String registryName, BlockEntityType<T> tileEntityType) {
        tileEntityType.setRegistryName(InteriorMod.getId(registryName));
        BLOCK_ENTITY_TYPES.add(tileEntityType);
        return tileEntityType;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<BlockEntityType<?>> event) {
            BLOCK_ENTITY_TYPES.forEach(event.getRegistry()::register);
        }
    }

}
