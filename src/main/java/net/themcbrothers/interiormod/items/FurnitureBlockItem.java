package net.themcbrothers.interiormod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.api.furniture.InteriorRegistries;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
public class FurnitureBlockItem extends BlockItem {

    public FurnitureBlockItem(FurnitureType furniture, Properties builder) {
        super(furniture.getBlock(), builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (stack.hasTag() && stack.getOrCreateTag().contains("BlockEntityTag", Tag.TAG_COMPOUND)) {
            CompoundTag tag = stack.getOrCreateTag().getCompound("BlockEntityTag");
            FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("primaryMaterial")));
            FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("secondaryMaterial")));

            if (primary != null) {
                tooltip.add(primary.getDisplayName().withStyle(ChatFormatting.GREEN));
            }
            if (secondary != null && !secondary.equals(primary)) {
                tooltip.add(secondary.getDisplayName().withStyle(ChatFormatting.GREEN));
            }
        }
    }
}
