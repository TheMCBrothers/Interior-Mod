package tk.themcbros.interiormod.compat.waila;

/**
 * @author TheMCBrothers
 */
public class HUDHandlerFurniture /*implements IComponentProvider, IServerDataProvider<TileEntity> {

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
        FurnitureMaterial primaryMaterial = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primaryMaterial")));
        FurnitureMaterial secondaryMaterial = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondaryMaterial")));

        if (primaryMaterial != null) {
            ITextComponent primary = primaryMaterial.getDisplayName();
            primary.getStyle().applyFormatting(TextFormatting.GRAY);
            tooltip.add(primary);
        }
        if (secondaryMaterial != null && secondaryMaterial != primaryMaterial) {
            ITextComponent secondary = secondaryMaterial.getDisplayName();
            secondary.getStyle().applyFormatting(TextFormatting.GRAY);
            tooltip.add(secondary);
        }
    }*/
{}
