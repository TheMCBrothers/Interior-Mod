package tk.themcbros.interiormod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

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
        if (stack.hasTag() && stack.getOrCreateTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag tag = stack.getOrCreateTag().getCompound("textures");
            FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("primary")));
            FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("secondary")));

            if (primary != null) {
                tooltip.add(primary.getDisplayName().withStyle(ChatFormatting.GREEN));
            }
            if (secondary != null && !secondary.equals(primary)) {
                tooltip.add(secondary.getDisplayName().withStyle(ChatFormatting.GREEN));
            }
        }
    }
}
