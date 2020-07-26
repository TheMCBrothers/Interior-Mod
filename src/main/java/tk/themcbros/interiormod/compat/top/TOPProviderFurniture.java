package tk.themcbros.interiormod.compat.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

/**
 * @author TheMCBrothers
 */
public class TOPProviderFurniture implements IProbeInfoProvider {

    static final TOPProviderFurniture INSTANCE = new TOPProviderFurniture();

    @Override
    public String getID() {
        return InteriorAPI.MOD_ID + ":furniture";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData probeHitData) {
        BlockPos pos = probeHitData.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof FurnitureTileEntity) {
            FurnitureTileEntity furnitureTileEntity = (FurnitureTileEntity) tileEntity;
            FurnitureMaterial primary = furnitureTileEntity.getPrimaryMaterial();
            FurnitureMaterial secondary = furnitureTileEntity.getSecondaryMaterial();

            probeInfo.text(primary.getDisplayName().applyTextStyle(TextFormatting.GRAY));
            if (secondary != primary)
                probeInfo.text(secondary.getDisplayName().applyTextStyle(TextFormatting.GRAY));
        }
    }
}
