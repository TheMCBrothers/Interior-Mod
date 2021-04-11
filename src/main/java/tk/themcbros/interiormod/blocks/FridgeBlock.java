package tk.themcbros.interiormod.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.EmptyHandler;
import tk.themcbros.interiormod.init.InteriorTileEntities;
import tk.themcbros.interiormod.tileentity.FridgeTileEntity;
import tk.themcbros.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
@SuppressWarnings("deprecation")
public class FridgeBlock extends Block {
	
	private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
	public final ImmutableMap<BlockState, VoxelShape> SHAPES;

	public FridgeBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
		SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
	}
	
	private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> validStates) {
		
		ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
		
		for (BlockState blockState : validStates) {
			List<VoxelShape> shapes = Lists.newArrayList();
			Direction facing = blockState.get(FACING);
			// Fridge Top
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 19, 9, 14, 20, 10))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 16, 5, 14, 17, 6))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(14, 28, 10, 15, 29, 11))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 16, 0, 13, 32, 16))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 24, 8, 14, 29, 13))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 26, 2, 15, 27, 3))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 19, 2, 15, 20, 3))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(15, 19, 2, 16, 27, 3))[facing.getHorizontalIndex()]);
			
			// Fridge Bottom
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 6, 2, 2, 7, 14))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 13, 2, 7, 14))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 2, 2, 7, 3))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(2, 0, 3, 2, 6, 13))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 1, 3, 2, 2, 13))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 5, 3, 2, 6, 13))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 3, 3, 2, 4, 13))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 2, 2, 0, 14))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 0, 13, 16, 16))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 12, 11, 14, 13, 12))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 15, 3, 14, 16, 4))[facing.getHorizontalIndex()]);
			shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(13, 11, 6, 14, 12, 7))[facing.getHorizontalIndex()]);
			
			builder.put(blockState, ShapeUtils.combineAll(shapes));
		}
		
		return builder.build();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(HALF) == DoubleBlockHalf.LOWER ? SHAPES.get(state) : SHAPES.get(state).withOffset(0d, -1d, 0d);
	}
	
	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return VoxelShapes.empty();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF);
	}
	
	/**
	 * Update the provided state given the provided neighbor facing and neighbor
	 * state, returning a new state. For example, fences make their connections to
	 * the passed in state if possible, and wet concrete powder immediately returns
	 * its solidified counterpart. Note that this method should ideally consider
	 * only the specific face passed in.
	 */
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
										  BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleBlockHalf = stateIn.get(HALF);
		if (facing.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
			return facingState.matchesBlock(this) && facingState.get(HALF) != doubleBlockHalf ? stateIn.with(FACING, facingState.get(FACING)) : Blocks.AIR.getDefaultState();
		} else {
			return doubleBlockHalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos)
					? Blocks.AIR.getDefaultState()
					: super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockPos = context.getPos();
		if (blockPos.getY() < 255 && context.getWorld().getBlockState(blockPos.up()).isReplaceable(context)) {
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HALF, DoubleBlockHalf.LOWER);
		} else {
			return null;
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place
	 * logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 2 | 1);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.get(HALF) == DoubleBlockHalf.LOWER ? super.isValidPosition(state, worldIn, pos) : blockstate.matchesBlock(this);
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if
	 * the player's tool can actually collect this block
	 */
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote && player.isCreative()) {
			DoubleBlockHalf doubleblockhalf = state.get(HALF);
			if (doubleblockhalf == DoubleBlockHalf.UPPER) {
				BlockPos blockpos = pos.down();
				BlockState blockstate = worldIn.getBlockState(blockpos);
				if (blockstate.getBlock() == state.getBlock() && blockstate.get(HALF) == DoubleBlockHalf.LOWER) {
					worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 32 | 2 | 1);
					worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
				}
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (state.get(HALF) == DoubleBlockHalf.UPPER) {
			pos = pos.down();
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof FridgeTileEntity && player instanceof ServerPlayerEntity) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity);
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public int getComparatorInputOverride(BlockState state, World worldIn, BlockPos pos) {
		if (state.get(HALF) == DoubleBlockHalf.UPPER) {
			pos = pos.down();
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof FridgeTileEntity && tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
			return ItemHandlerHelper.calcRedstoneFromInventory(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					.orElse(EmptyHandler.INSTANCE));
		}
		return 0;
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return state.get(HALF) == DoubleBlockHalf.LOWER ? InteriorTileEntities.FRIDGE.create() : null;
	}

}
