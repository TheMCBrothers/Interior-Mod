package tk.themcbros.interiormod.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.api.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class ChairTileEntity extends FurnitureTileEntity {

	public static ModelProperty<IFurnitureMaterial> MATERIAL = new ModelProperty<IFurnitureMaterial>();
	public static ModelProperty<IFurnitureMaterial> SEAT_MATERIAL = new ModelProperty<IFurnitureMaterial>();
	public static ModelProperty<Direction> FACING = new ModelProperty<Direction>();
	
	private IFurnitureMaterial material = FurnitureRegistry.MATERIALS.getKeys().get(0);
	private IFurnitureMaterial seatMaterial = FurnitureRegistry.MATERIALS.getKeys().get(0);
	private Direction facing = Direction.NORTH;
	
	public ChairTileEntity() {
		super(InteriorTileEntities.CHAIR);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("material", this.material != null ? this.material.getSaveId() : "null");
		compound.putString("seatMaterial", this.seatMaterial != null ? this.seatMaterial.getSaveId() : "null");
		compound.putString("facing", this.facing != null ? this.facing.getName() : "north");
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		this.material = FurnitureRegistry.MATERIALS.get(compound.getString("material"));
		this.seatMaterial = FurnitureRegistry.MATERIALS.get(compound.getString("seatMaterial"));
		this.facing = Direction.byName(compound.getString("facing"));
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
		return new ModelDataMap.Builder().withInitial(MATERIAL, material).withInitial(SEAT_MATERIAL, seatMaterial).withInitial(FACING, facing).build();
	}
	
	public Direction getFacing() {
		return facing;
	}
	
	public IFurnitureMaterial getSeatMaterial() {
		return seatMaterial;
	}
	
	public IFurnitureMaterial getMaterial() {
		return material;
	}

	public void setFacing(Direction direction) {
		this.facing = direction;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	public void setSeatMaterial(IFurnitureMaterial seatMaterial) {
		this.seatMaterial = seatMaterial;
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
