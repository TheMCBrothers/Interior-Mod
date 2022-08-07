package net.themcbrothers.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import org.jetbrains.annotations.Nullable;

/**
 * @author TheMCBrothers
 */
public class InteriorBlockTagsProvider extends BlockTagsProvider {
    public InteriorBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, InteriorAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(InteriorBlocks.FRIDGE.get(), InteriorBlocks.TRASH_CAN.get(), InteriorBlocks.MODERN_DOOR.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(InteriorBlocks.FURNITURE_WORKBENCH.get());
    }

    @Override
    public String getName() {
        return super.getName() + ": " + this.modId;
    }
}
