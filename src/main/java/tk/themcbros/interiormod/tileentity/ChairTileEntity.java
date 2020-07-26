package tk.themcbros.interiormod.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class ChairTileEntity extends FurnitureTileEntity {

	public static ModelProperty<Direction> FACING = new ModelProperty<>();

	private Direction facing = Direction.NORTH;
	
	public ChairTileEntity() {
		super(InteriorTileEntities.CHAIR);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("facing", this.facing != null ? this.facing.getName() : "north");
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
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
		return new ModelDataMap.Builder().withInitial(PRIMARY_MATERIAL, this.getPrimaryMaterial()).withInitial(SECONDARY_MATERIAL, this.getSecondaryMaterial()).withInitial(FACING, facing).build();
	}
	
	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction direction) {
		this.facing = direction;
		if(this.world != null && this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}

}
