package tk.themcbros.interiormod.blocks;

import java.util.List;

import javax.annotation.Nullable;

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
import net.minecraft.state.properties.Half;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import tk.themcbros.interiormod.tileentity.FridgeTileEntity;
import tk.themcbros.interiormod.util.ShapeUtils;

public class FridgeBlock extends Block {
	
	private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	
	public final ImmutableMap<BlockState, VoxelShape> SHAPES;

	public FridgeBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(HALF, Half.BOTTOM));
		SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
	}
	
	private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> validStates) {
		
		ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
		
		for (BlockState blockState : validStates) {
			List<VoxelShape> shapes = Lists.newArrayList();
			Direction facing = blockState.get(FACING);
			if (blockState.get(HALF) == Half.TOP) {
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(14, 5, 2, 15, 6, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(14, 10, 2, 15, 11, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(15, 5, 2, 16, 11, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, -16, 0, 14, 0, 16))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 0, 14, 16, 16))[facing.getHorizontalIndex()]);
			} else {
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(14, 21, 2, 15, 22, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(14, 26, 2, 15, 27, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(15, 21, 2, 16, 27, 3))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 0, 0, 14, 16, 16))[facing.getHorizontalIndex()]);
				shapes.add(ShapeUtils.getRotatedShapes(Block.makeCuboidShape(1, 16, 0, 14, 32, 16))[facing.getHorizontalIndex()]);
			}
			builder.put(blockState, ShapeUtils.combineAll(shapes));
		}
		
		return builder.build();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state);
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
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		Half half = stateIn.get(HALF);
		if (facing.getAxis() != Direction.Axis.Y || half == Half.BOTTOM != (facing == Direction.UP)
				|| facingState.getBlock() == this && facingState.get(HALF) != half) {
			return half == Half.BOTTOM && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos)
					? Blocks.AIR.getDefaultState()
					: super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		} else {
			return Blocks.AIR.getDefaultState();
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1
				&& context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)
						? this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())
						: null;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place
	 * logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, Half.TOP).with(FACING, placer.getHorizontalFacing().getOpposite()), 3);
	}

	@SuppressWarnings("deprecation")
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if (state.get(HALF) != Half.TOP) {
			return super.isValidPosition(state, worldIn, pos);
		} else {
			BlockState blockstate = worldIn.getBlockState(pos.down());
			if (state.getBlock() != this)
				return super.isValidPosition(state, worldIn, pos);
			return blockstate.getBlock() == this && blockstate.get(HALF) == Half.BOTTOM;
		}
	}

	public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
		worldIn.setBlockState(pos, this.getDefaultState().with(HALF, Half.BOTTOM), flags);
		worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, Half.TOP), flags);
	}

	/**
	 * Spawns the block's drops in the world. By the time this is called the Block
	 * has possibly been set to air via Block.removedByPlayer
	 */
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
			@Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if
	 * the player's tool can actually collect this block
	 */
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Half half = state.get(HALF);
		BlockPos blockpos = half == Half.BOTTOM ? pos.up() : pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.getBlock() == this && blockstate.get(HALF) != half) {
			worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			if (!worldIn.isRemote && !player.isCreative()) {
				spawnDrops(state, worldIn, pos, (TileEntity) null, player, player.getHeldItemMainhand());
				spawnDrops(blockstate, worldIn, blockpos, (TileEntity) null, player, player.getHeldItemMainhand());
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
		if (state.get(HALF) == Half.TOP) {
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
		if (state.get(HALF) == Half.TOP) {
			pos = pos.down();
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof FridgeTileEntity) {
			return ItemHandlerHelper.calcRedstoneFromInventory((IItemHandler) tileEntity);
		}
		return 0;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return state.get(HALF) == Half.BOTTOM ? new FridgeTileEntity() : null;
	}

}
