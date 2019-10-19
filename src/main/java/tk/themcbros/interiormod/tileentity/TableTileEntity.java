package tk.themcbros.interiormod.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class TableTileEntity extends TileEntity {

	public static ModelProperty<IFurnitureMaterial> MATERIAL = new ModelProperty<IFurnitureMaterial>();
	public static ModelProperty<IFurnitureMaterial> LEG_MATERIAL = new ModelProperty<IFurnitureMaterial>();
	
	private IFurnitureMaterial material = FurnitureRegistry.MATERIALS.getKeys().get(0);
	private IFurnitureMaterial legMaterial = FurnitureRegistry.MATERIALS.getKeys().get(0);
	
	public TableTileEntity() {
		super(InteriorTileEntities.TABLE);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("material", this.material.getSaveId());
		compound.putString("legMaterial", this.legMaterial.getSaveId());
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		this.material = FurnitureRegistry.MATERIALS.get(compound.getString("material"));
		this.legMaterial = FurnitureRegistry.MATERIALS.get(compound.getString("legMaterial"));
		super.read(compound);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		super.handleUpdateTag(tag);
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}
	
	@Override
	public IModelData getModelData() {
		return new ModelDataMap.Builder().withInitial(MATERIAL, material).withInitial(LEG_MATERIAL, legMaterial).build();
	}
	
	public IFurnitureMaterial getLegMaterial() {
		return legMaterial;
	}
	
	public IFurnitureMaterial getMaterial() {
		return material;
	}
	
	public void setLegMaterial(IFurnitureMaterial seatMaterial) {
		this.legMaterial = seatMaterial;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	public void setMaterial(IFurnitureMaterial material) {
		this.material = material;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}

}
