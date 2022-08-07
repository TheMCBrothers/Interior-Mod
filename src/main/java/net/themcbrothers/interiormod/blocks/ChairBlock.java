package net.themcbrothers.interiormod.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.entity.SeatEntity;
import net.themcbrothers.interiormod.init.InteriorBlockEntityTypes;
import net.themcbrothers.interiormod.init.InteriorStats;
import net.themcbrothers.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
public class ChairBlock extends FurnitureBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape[] SEAT_SHAPES;
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ChairBlock(Properties properties) {
        super(FurnitureType.CHAIR, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        SEAT_SHAPES = ShapeUtils.getRotatedShapes(Shapes.create(0.125, 0.562, 0.125, 0.75, 0.688, 0.875)); // SEAT
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
        final VoxelShape[] legNorthWest = ShapeUtils.getRotatedShapes(Shapes.create(0.125, 0, 0.75, 0.25, 0.562, 0.875)); // LEGNW
        final VoxelShape[] legNorthEast = ShapeUtils.getRotatedShapes(Shapes.create(0.125, 0, 0.125, 0.25, 0.562, 0.25)); // LEGNE
        final VoxelShape[] legSouthWest = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 0, 0.75, 0.875, 0.688, 0.875)); // LEGSW
        final VoxelShape[] legSouthEast = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 0, 0.125, 0.875, 0.688, 0.25)); // LEGSE
        final VoxelShape[] cube1 = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 1.125, 0.25, 0.875, 1.25, 0.75)); // CUBE
        final VoxelShape[] cube2 = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 0.812, 0.25, 0.875, 0.938, 0.75)); // CUBE
        final VoxelShape[] legSouthWest2 = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 0.688, 0.75, 0.875, 1.25, 0.875)); // LEGSW
        final VoxelShape[] legSouthEast2 = ShapeUtils.getRotatedShapes(Shapes.create(0.75, 0.688, 0.125, 0.875, 1.25, 0.25)); // LEGSE

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            Direction direction = state.getValue(FACING);
            List<VoxelShape> shapes = Lists.newArrayList();
            shapes.add(legNorthWest[direction.get2DDataValue()]);
            shapes.add(legNorthEast[direction.get2DDataValue()]);
            shapes.add(legSouthWest[direction.get2DDataValue()]);
            shapes.add(legSouthEast[direction.get2DDataValue()]);
            shapes.add(cube1[direction.get2DDataValue()]);
            shapes.add(cube2[direction.get2DDataValue()]);
            shapes.add(SEAT_SHAPES[direction.get2DDataValue()]);
            shapes.add(legSouthWest2[direction.get2DDataValue()]);
            shapes.add(legSouthEast2[direction.get2DDataValue()]);
            builder.put(state, ShapeUtils.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext p_60558_) {
        return SHAPES.get(state);
    }

    @Override
    public SoundType getSoundType(BlockState state) {
        return SoundType.WOOD;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        player.awardStat(InteriorStats.SIT_DOWN.get());
        return SeatEntity.create(world, pos, 0.5d, player);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return InteriorBlockEntityTypes.CHAIR.get().create(blockPos, blockState);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
    }

}
