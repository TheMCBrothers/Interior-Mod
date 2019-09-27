package tk.themcbros.interiormod.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class ChairTileEntity extends TileEntity {

	public static ModelProperty<ResourceLocation> TEXTURE = new ModelProperty<ResourceLocation>();
	public static ModelProperty<ResourceLocation> SEAT_TEXTURE = new ModelProperty<ResourceLocation>();
	public static ModelProperty<Direction> FACING = new ModelProperty<Direction>();
	
	private ResourceLocation texture = new ResourceLocation("block/oak_planks");
	private ResourceLocation seatTexture = new ResourceLocation("block/oak_log");
	private Direction facing = Direction.NORTH;
	
	public ChairTileEntity() {
		super(InteriorTileEntities.CHAIR);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("texture", this.texture.toString());
		compound.putString("seatTexture", this.seatTexture.toString());
		compound.putString("facing", this.facing.getName());
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		this.texture = new ResourceLocation(compound.getString("texture"));
		this.seatTexture = new ResourceLocation(compound.getString("seatTexture"));
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
		return new ModelDataMap.Builder().withInitial(TEXTURE, texture).withInitial(SEAT_TEXTURE, seatTexture).withInitial(FACING, facing).build();
	}
	
	public Direction getFacing() {
		return facing;
	}
	
	public ResourceLocation getSeatTexture() {
		return seatTexture;
	}
	
	public ResourceLocation getTexture() {
		return texture;
	}

	public void setFacing(Direction direction) {
		this.facing = direction;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	public void setSeatTexture(ResourceLocation seatTexture) {
		this.seatTexture = seatTexture;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
		if(this.world.isRemote) {
			ModelDataManager.requestModelDataRefresh(this);
			this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}

}
