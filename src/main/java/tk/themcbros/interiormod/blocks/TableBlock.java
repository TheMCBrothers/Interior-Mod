package tk.themcbros.interiormod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.FurnitureType;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.tileentity.TableTileEntity;

public class TableBlock extends FurnitureBlock implements IWaterLoggable {

	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public TableBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(NORTH, Boolean.FALSE)
				.with(EAST, Boolean.FALSE)
				.with(SOUTH, Boolean.FALSE)
				.with(WEST, Boolean.FALSE)
				.with(WATERLOGGED, Boolean.FALSE));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Boolean north = worldIn.getBlockState(currentPos.north()).getBlock() == this;
		Boolean east = worldIn.getBlockState(currentPos.east()).getBlock() == this;
		Boolean south = worldIn.getBlockState(currentPos.south()).getBlock() == this;
		Boolean west = worldIn.getBlockState(currentPos.west()).getBlock() == this;
		return stateIn.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getPos();
		IFluidState fluidState = world.getFluidState(blockPos);
		Boolean north = world.getBlockState(blockPos.north()).getBlock() == this;
		Boolean east = world.getBlockState(blockPos.east()).getBlock() == this;
		Boolean south = world.getBlockState(blockPos.south()).getBlock() == this;
		Boolean west = world.getBlockState(blockPos.west()).getBlock() == this;
		return this.getDefaultState().with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack != null) {
			if (stack.hasTag() && stack.getTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
				CompoundNBT tag = stack.getTag().getCompound("textures");
	
				TileEntity tile = worldIn.getTileEntity(pos);
	
				if (tile instanceof TableTileEntity) {
					TableTileEntity chair = (TableTileEntity) tile;
					chair.setMaterial(FurnitureRegistry.MATERIALS.get(tag.getString("primary")));
					chair.setLegMaterial(FurnitureRegistry.MATERIALS.get(tag.getString("secondary")));
				}
			}
		}
	}
	
	public boolean isTableBlock(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.getBlock() == this.getBlock();
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		for(IFurnitureMaterial material : FurnitureRegistry.MATERIALS.getKeys()) {
			items.add(FurnitureRegistry.createItemStack(FurnitureType.TABLE, material, material));
		}
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TableTileEntity) {
			TableTileEntity chair = (TableTileEntity) tileEntity;
			return FurnitureRegistry.createItemStack(FurnitureType.TABLE, chair.getMaterial(), chair.getLegMaterial());
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TableTileEntity();
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
	}

}
