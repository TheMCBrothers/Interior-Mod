package net.themcbrothers.interiormod.data;

import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.api.InteriorAPI;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput))
                .add(PackMetadataSection.TYPE,
                        new PackMetadataSection(Component.literal("TheMCBrothers Interior Mod"),
                                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                                Arrays.stream(PackType.values()).collect(Collectors.toMap(Function.identity(), DetectedVersion.BUILT_IN::getPackVersion))));
    }
}
