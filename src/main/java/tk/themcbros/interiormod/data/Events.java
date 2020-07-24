package tk.themcbros.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import tk.themcbros.interiormod.api.InteriorAPI;

@EventBusSubscriber(modid = InteriorAPI.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Events {

	@SubscribeEvent
	public static void gatherData(final GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(new InteriorLootTables(generator));
		generator.addProvider(new InteriorLanguageProvider(generator, InteriorAPI.MOD_ID));
		generator.addProvider(new RecipeDataProvider(generator));
	}
	
}
