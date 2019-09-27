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
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import tk.themcbros.interiormod.entity.SeatEntity;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;
import tk.themcbros.interiormod.util.ShapeUtils;

public class ChairBlock extends Block implements IWaterLoggable {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public final ImmutableMap<BlockState, VoxelShape> SHAPES;
	
	public ChairBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
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
		final VoxelShape[] seat = ShapeUtils.getRotatedShapes(VoxelShapes.create(0.125, 0.562, 0.125, 0.75, 0.688, 0.875)); // SEAT
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
			shapes.add(seat[direction.getHorizontalIndex()]);
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
	public SoundType getSoundType(BlockState state) {
		return SoundType.WOOD;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack != null) {
			if (stack.hasTag() && stack.getTag().contains("textures")) {
				CompoundNBT tag = stack.getTag().getCompound("textures");
	
				TileEntity tile = worldIn.getTileEntity(pos);
	
				if (tile instanceof ChairTileEntity) {
					ChairTileEntity chair = (ChairTileEntity) tile;
					chair.setTexture(new ResourceLocation(tag.getString("texture")));
					chair.setSeatTexture(new ResourceLocation(tag.getString("seatTexture")));
					chair.setFacing(state.get(BlockStateProperties.HORIZONTAL_FACING));
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			SeatEntity.create(worldIn, pos, 0.4d, player);
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
		for(Item item : ItemTags.PLANKS.getAllElements()) {
			ItemStack stack = new ItemStack(this);
			ResourceLocation location = item.getRegistryName();
			String tex = location.getNamespace() + ":block/" + location.getPath();
			CompoundNBT tag = new CompoundNBT();
			tag.putString("texture", tex);
			tag.putString("seatTexture", tex);
			stack.setTag(new CompoundNBT());
			stack.getTag().put("textures", tag);
			items.add(stack);
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		ItemStack stack = new ItemStack(this);
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof ChairTileEntity) {
			ChairTileEntity chair = (ChairTileEntity) tileEntity;
			CompoundNBT tag = new CompoundNBT();
			CompoundNBT texTag = new CompoundNBT();
			texTag.putString("texture", chair.getTexture().toString());
			texTag.putString("seatTexture", chair.getSeatTexture().toString());
			tag.put("textures", texTag);
			stack.setTag(tag);
		}
		return stack;
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
