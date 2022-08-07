package net.themcbrothers.interiormod.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.entity.SeatEntity;

import static net.themcbrothers.interiormod.init.Registration.ENTITY_TYPES;

/**
 * @author TheMCBrothers
 */
public class InteriorEntityTypes {
    public static final RegistryObject<EntityType<SeatEntity>> SEAT = ENTITY_TYPES.register("seat", () -> EntityType.Builder.<SeatEntity>of((type, world) -> new SeatEntity(world), MobCategory.MISC).sized(0.0F, 0.0F).noSave().noSummon().setCustomClientFactory((spawnEntity, world) -> new SeatEntity(world)).build("seat"));

    static void init() {
    }
}
