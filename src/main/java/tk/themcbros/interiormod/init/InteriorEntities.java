package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.entity.SeatEntity;

public class InteriorEntities {
	
	private static final List<EntityType<?>> ENTITY_TYPES = Lists.newArrayList();

	public static final EntityType<SeatEntity> SEAT = registerEntityType("seat", EntityType.Builder.<SeatEntity>create((type, world) -> new SeatEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new SeatEntity(world)));
	
	private static <T extends Entity> EntityType<T> registerEntityType(String registryName, EntityType.Builder<T> builder) {
		EntityType<T> entityType = builder.build(registryName);
		entityType.setRegistryName(InteriorMod.getId(registryName));
		ENTITY_TYPES.add(entityType);
		return entityType;
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
	public static class Registration {
		@SubscribeEvent
		public static void onBlockRegistry(final RegistryEvent.Register<EntityType<?>> event) {
			ENTITY_TYPES.forEach(event.getRegistry()::register);
		}
	}
	
}
