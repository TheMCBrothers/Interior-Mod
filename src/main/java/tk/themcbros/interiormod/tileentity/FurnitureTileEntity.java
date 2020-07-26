package tk.themcbros.interiormod.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author TheMCBrothers
 */
public abstract class FurnitureTileEntity extends TileEntity {

    public static ModelProperty<FurnitureMaterial> PRIMARY_MATERIAL = new ModelProperty<>();
    public static ModelProperty<FurnitureMaterial> SECONDARY_MATERIAL = new ModelProperty<>();

    private Supplier<FurnitureMaterial> primaryMaterial = FurnitureMaterials.OAK_PLANKS;
    private Supplier<FurnitureMaterial> secondaryMaterial = FurnitureMaterials.OAK_PLANKS;

    public FurnitureTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(PRIMARY_MATERIAL, primaryMaterial.get()).withInitial(SECONDARY_MATERIAL, secondaryMaterial.get())
                .build();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("primaryMaterial", String.valueOf(this.primaryMaterial.get().getRegistryName()));
        compound.putString("secondaryMaterial", String.valueOf(this.secondaryMaterial.get().getRegistryName()));
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.primaryMaterial =
                () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(compound.getString("primaryMaterial")));
        this.secondaryMaterial =
                () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(compound.getString("secondaryMaterial")));
    }

    public void setPrimaryMaterial(Supplier<FurnitureMaterial> primaryMaterial) {
        this.primaryMaterial = primaryMaterial;
        if(this.world != null && this.world.isRemote) {
            ModelDataManager.requestModelDataRefresh(this);
            this.world.notifyBlockUpdate(pos, world.getBlockState(pos), this.world.getBlockState(pos), 3);
        }
    }

    public void setSecondaryMaterial(Supplier<FurnitureMaterial> secondaryMaterial) {
        this.secondaryMaterial = secondaryMaterial;
        if(this.world != null && this.world.isRemote) {
            ModelDataManager.requestModelDataRefresh(this);
            this.world.notifyBlockUpdate(pos, world.getBlockState(pos), this.world.getBlockState(pos), 3);
        }
    }

    public FurnitureMaterial getPrimaryMaterial() {
        return this.primaryMaterial.get();
    }

    public FurnitureMaterial getSecondaryMaterial() {
        return this.secondaryMaterial.get();
    }
}
