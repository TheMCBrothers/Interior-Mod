package net.themcbrothers.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.themcbrothers.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
public class Events {
    @SubscribeEvent
    public void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {
            generator.addProvider(new InteriorLanguageProvider(generator, InteriorAPI.MOD_ID));
        }
        if (event.includeServer()) {
            generator.addProvider(new InteriorLootTableProvider(generator));
            generator.addProvider(new RecipeDataProvider(generator));
            generator.addProvider(new InteriorAdvancementProvider(generator));
        }
    }
}