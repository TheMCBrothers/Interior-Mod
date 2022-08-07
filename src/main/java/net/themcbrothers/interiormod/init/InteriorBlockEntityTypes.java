package net.themcbrothers.interiormod.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.blockentity.ChairBlockEntity;
import net.themcbrothers.interiormod.blockentity.FridgeBlockEntity;
import net.themcbrothers.interiormod.blockentity.NightlightLampBlockEntity;
import net.themcbrothers.interiormod.blockentity.TableBlockEntity;

import static net.themcbrothers.interiormod.init.Registration.BLOCK_ENTITY_TYPES;

/**
 * @author TheMCBrothers
 */
public class InteriorBlockEntityTypes {
    public static final RegistryObject<BlockEntityType<ChairBlockEntity>> CHAIR = BLOCK_ENTITY_TYPES.register("chair", () -> BlockEntityType.Builder.of(ChairBlockEntity::new, InteriorBlocks.CHAIR.get()).build(null));
    public static final RegistryObject<BlockEntityType<TableBlockEntity>> TABLE = BLOCK_ENTITY_TYPES.register("table", () -> BlockEntityType.Builder.of(TableBlockEntity::new, InteriorBlocks.TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FridgeBlockEntity>> FRIDGE = BLOCK_ENTITY_TYPES.register("fridge", () -> BlockEntityType.Builder.of(FridgeBlockEntity::new, InteriorBlocks.FRIDGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<NightlightLampBlockEntity>> LAMP = BLOCK_ENTITY_TYPES.register("lamp", () -> BlockEntityType.Builder.of(NightlightLampBlockEntity::new, InteriorBlocks.LAMP.get(), InteriorBlocks.LAMP_ON_A_STICK.get()).build(null));

    static void init() {
    }
}
