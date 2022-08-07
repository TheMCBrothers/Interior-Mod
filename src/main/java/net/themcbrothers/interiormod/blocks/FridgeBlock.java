package net.themcbrothers.interiormod.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.network.NetworkHooks;
import net.themcbrothers.interiormod.blockentity.FridgeBlockEntity;
import net.themcbrothers.interiormod.init.InteriorBlockEntityTypes;
import net.themcbrothers.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
@SuppressWarnings("deprecation")
public class FridgeBlock extends Block implements EntityBlock {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public FridgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> validStates) {

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();

        for (BlockState blockState : validStates) {
            List<VoxelShape> shapes = Lists.newArrayList();
            Direction facing = blockState.getValue(FACING);
            // Fridge Top
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 19, 9, 14, 20, 10))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 16, 5, 14, 17, 6))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(14, 28, 10, 15, 29, 11))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 16, 0, 13, 32, 16))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 24, 8, 14, 29, 13))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 26, 2, 15, 27, 3))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 19, 2, 15, 20, 3))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(15, 19, 2, 16, 27, 3))[facing.get2DDataValue()]);

            // Fridge Bottom
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 6, 2, 2, 7, 14))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 0, 13, 2, 7, 14))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 0, 2, 2, 7, 3))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(2, 0, 3, 2, 6, 13))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 1, 3, 2, 2, 13))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 5, 3, 2, 6, 13))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 3, 3, 2, 4, 13))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 0, 2, 2, 0, 14))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(1, 0, 0, 13, 16, 16))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 12, 11, 14, 13, 12))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 15, 3, 14, 16, 4))[facing.get2DDataValue()]);
            shapes.add(ShapeUtils.getRotatedShapes(Block.box(13, 11, 6, 14, 12, 7))[facing.get2DDataValue()]);

            builder.put(blockState, ShapeUtils.combineAll(shapes));
        }

        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPES.get(state) : SHAPES.get(state).move(0d, -1d, 0d);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleBlockHalf = stateIn.getValue(HALF);
        if (facing.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
            return facingState.is(this) && facingState.getValue(HALF) != doubleBlockHalf ? stateIn.setValue(FACING, facingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState state, LivingEntity entity, ItemStack stack) {
        level.setBlock(blockPos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockState.isFaceSturdy(world, blockPos, Direction.UP) : blockState.is(this);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide && player.isCreative()) {
            DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
            if (doubleblockhalf == DoubleBlockHalf.UPPER) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    worldIn.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    worldIn.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
                                 InteractionHand handIn, BlockHitResult hitResult) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
        }
        BlockEntity blockEntity = worldIn.getBlockEntity(pos);
        if (blockEntity instanceof FridgeBlockEntity && player instanceof ServerPlayer) {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) blockEntity);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FridgeBlockEntity && blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
            return ItemHandlerHelper.calcRedstoneFromInventory(blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .orElse(EmptyHandler.INSTANCE));
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? InteriorBlockEntityTypes.FRIDGE.get().create(blockPos, blockState) : null;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Container) {
                Containers.dropContents(world, pos, (Container) blockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }
}
