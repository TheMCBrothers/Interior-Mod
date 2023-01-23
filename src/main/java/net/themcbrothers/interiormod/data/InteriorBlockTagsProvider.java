package net.themcbrothers.interiormod.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheMCBrothers
 */
public class InteriorBlockTagsProvider extends BlockTagsProvider {
    public InteriorBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, InteriorAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(InteriorBlocks.FRIDGE.get(), InteriorBlocks.TRASH_CAN.get(), InteriorBlocks.MODERN_DOOR.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(InteriorBlocks.FURNITURE_WORKBENCH.get());
    }

    @Override
    public String getName() {
        return super.getName() + ": " + this.modId;
    }
}
