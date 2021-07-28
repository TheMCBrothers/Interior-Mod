package tk.themcbros.interiormod.compat.top;

/**
 * @author TheMCBrothers
 */
public class TOPProviderFurniture/* implements IProbeInfoProvider {

    static final TOPProviderFurniture INSTANCE = new TOPProviderFurniture();

    @Override
    public String getID() {
        return InteriorAPI.MOD_ID + ":furniture";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData probeHitData) {
        BlockPos pos = probeHitData.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof FurnitureBlockEntity) {
            FurnitureBlockEntity furnitureTileEntity = (FurnitureBlockEntity) tileEntity;
            FurnitureMaterial primaryMaterial = furnitureTileEntity.getPrimaryMaterial();
            FurnitureMaterial secondaryMaterial = furnitureTileEntity.getSecondaryMaterial();

            probeInfo.text(primaryMaterial.getDisplayName().mergeStyle(TextFormatting.GRAY));
            if (secondaryMaterial != primaryMaterial)
                probeInfo.text(secondaryMaterial.getDisplayName().mergeStyle(TextFormatting.GRAY));
        }
    }
}
*/ {
}