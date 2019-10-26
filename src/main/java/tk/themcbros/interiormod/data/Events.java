package tk.themcbros.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import tk.themcbros.interiormod.api.InteriorAPI;

@EventBusSubscriber(modid = InteriorAPI.MOD_ID)
public class Events {

	public static void gatherData(final GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(new InteriorLootTables(generator));
	}
	
}
