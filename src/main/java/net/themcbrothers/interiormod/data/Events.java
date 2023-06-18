package net.themcbrothers.interiormod.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.api.InteriorAPI;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author TheMCBrothers
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorAPI.MOD_ID)
public class Events {
    @SubscribeEvent
    static void gatherData(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput packOutput = generator.getPackOutput();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new InteriorLanguageProvider(packOutput, InteriorAPI.MOD_ID));

        generator.addProvider(event.includeServer(), InteriorLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new RecipeDataProvider(packOutput));
        generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(packOutput, lookupProvider, existingFileHelper, List.of(new InteriorAdvancementGenerator())));
        generator.addProvider(event.includeServer(), new InteriorBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
    }
}
