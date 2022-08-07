package net.themcbrothers.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorAPI.MOD_ID)
public class Events {
    @SubscribeEvent
    static void gatherData(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(true, new InteriorLanguageProvider(generator, InteriorAPI.MOD_ID));
        }
        if (event.includeServer()) {
            generator.addProvider(true, new InteriorLootTableProvider(generator));
            generator.addProvider(true, new RecipeDataProvider(generator));
            generator.addProvider(true, new InteriorAdvancementProvider(generator, fileHelper));
            generator.addProvider(true, new InteriorBlockTagsProvider(generator, fileHelper));
        }
    }
}
