package tk.themcbros.interiormod.blocks;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.entity.SeatEntity;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;
import tk.themcbros.interiormod.util.ShapeUtils;

public class ChairBlock extends FurnitureBlock implements IWaterLoggable {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private final VoxelShape[] SEAT_SHAPES;
	public final ImmutableMap<BlockState, VoxelShape> SHAPES;
	
	public ChairBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
		SEAT_SHAPES = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.125, 0.562, 0.125, 0.75, 0.688, 0.875)); // SEAT
		SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}
	
	private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
		final VoxelShape[] legNorthWest = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.125, 0, 0.75, 0.25, 0.562, 0.875)); // LEGNW
		final VoxelShape[] legNorthEast = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.125, 0, 0.125, 0.25, 0.562, 0.25)); // LEGNE
		final VoxelShape[] legSouthWest = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 0, 0.75, 0.875, 0.688, 0.875)); // LEGSW
		final VoxelShape[] legSouthEast = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 0, 0.125, 0.875, 0.688, 0.25)); // LEGSE
		final VoxelShape[] cube1 = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 1.125, 0.25, 0.875, 1.25, 0.75)); // CUBE
		final VoxelShape[] cube2 = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 0.812, 0.25, 0.875, 0.938, 0.75)); // CUBE
		final VoxelShape[] legSouthWest2 = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 0.688, 0.75, 0.875, 1.25, 0.875)); // LEGSW
		final VoxelShape[] legSouthEast2 = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.75, 0.688, 0.125, 0.875, 1.25, 0.25)); // LEGSE
		
		ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
		for (BlockState state : states) {
			Direction direction = state.get(FACING);
			List<VoxelShape> shapes = Lists.newArrayList();
			shapes.add(legNorthWest[direction.getHorizontalIndex()]);
			shapes.add(legNorthEast[direction.getHorizontalIndex()]);
			shapes.add(legSouthWest[direction.getHorizontalIndex()]);
			shapes.add(legSouthEast[direction.getHorizontalIndex()]);
			shapes.add(cube1[direction.getHorizontalIndex()]);
			shapes.add(cube2[direction.getHorizontalIndex()]);
			shapes.add(SEAT_SHAPES[direction.getHorizontalIndex()]);
			shapes.add(legSouthWest2[direction.getHorizontalIndex()]);
			shapes.add(legSouthEast2[direction.getHorizontalIndex()]);
			builder.put(state, ShapeUtils.combineAll(shapes));
		}
		return builder.build();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state);
	}
	
	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return SHAPES.get(state);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		return SHAPES.get(state);
	}
	
	@Override
	public SoundType getSoundType(BlockState state) {
		return SoundType.WOOD;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack != null) {
			if (stack.hasTag() && stack.getTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
				CompoundNBT tag = stack.getTag().getCompound("textures");
	
				TileEntity tile = worldIn.getTileEntity(pos);
	
				if (tile instanceof ChairTileEntity) {
					ChairTileEntity chair = (ChairTileEntity) tile;
					chair.setMaterial(FurnitureRegistry.MATERIALS.get(tag.getString("primary")));
					chair.setSeatMaterial(FurnitureRegistry.MATERIALS.get(tag.getString("secondary")));
					chair.setFacing(state.get(BlockStateProperties.HORIZONTAL_FACING));
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			double minShapeX = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().minX;
			double minShapeY = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().minY - 0.01;
			double minShapeZ = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().minZ;
			double maxShapeX = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().maxX;
			double maxShapeY = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().maxY + 0.01;
			double maxShapeZ = SEAT_SHAPES[state.get(FACING).getHorizontalIndex()].getBoundingBox().maxZ;
			Vec3d click = hit.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
			if(click.x >= minShapeX && click.x <= maxShapeX && click.y >= minShapeY && click.y <= maxShapeY && click.z >= minShapeZ && click.z <= maxShapeZ) {
				SeatEntity.create(worldIn, pos, 0.45d, player);
			} else {
				SeatEntity.create(worldIn, pos, 0.5d, player);
			}
		}
		return true;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState iFluidState = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing())
				.with(WATERLOGGED, Boolean.valueOf(iFluidState.getFluid() == Fluids.WATER));
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		for(IFurnitureMaterial material : FurnitureRegistry.MATERIALS.getKeys()) {
			items.add(FurnitureRegistry.createItemStack(FurnitureType.CHAIR, material, material));
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof ChairTileEntity) {
			ChairTileEntity chair = (ChairTileEntity) tileEntity;
			return FurnitureRegistry.createItemStack(FurnitureType.CHAIR, chair.getMaterial(), chair.getSeatMaterial());
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ChairTileEntity();
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
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
	}
	
}
