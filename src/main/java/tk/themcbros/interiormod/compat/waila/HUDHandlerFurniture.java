package tk.themcbros.interiormod.compat.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class HUDHandlerFurniture implements IComponentProvider, IServerDataProvider<TileEntity> {

    static final HUDHandlerFurniture INSTANCE = new HUDHandlerFurniture();

    @Override
    public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {
        if (tileEntity instanceof FurnitureTileEntity) {
            FurnitureTileEntity furnitureTileEntity = (FurnitureTileEntity) tileEntity;
            compoundNBT.putString("primaryMaterial", String.valueOf(furnitureTileEntity.getPrimaryMaterial().getRegistryName()));
            compoundNBT.putString("secondaryMaterial", String.valueOf(furnitureTileEntity.getSecondaryMaterial().getRegistryName()));
        }
    }

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (!config.get(WailaCompat.CONFIG_DISPLAY_FURNITURE_MATERIALS))
            return;

        CompoundNBT tag = accessor.getServerData();
        FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primaryMaterial")));
        FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondaryMaterial")));

        if (primary != null)
            tooltip.add(primary.getDisplayName().applyTextStyle(TextFormatting.GRAY));
        if (secondary != null && secondary != primary)
            tooltip.add(secondary.getDisplayName().applyTextStyle(TextFormatting.GRAY));
    }
}
