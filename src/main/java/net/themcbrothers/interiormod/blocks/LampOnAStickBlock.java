package net.themcbrothers.interiormod.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.themcbrothers.interiormod.init.InteriorBlockEntities;
import net.themcbrothers.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class LampOnAStickBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape SHAPE;

    public LampOnAStickBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, Part.BOTTOM).setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
        this.SHAPE = this.generateShape();
    }

    private VoxelShape generateShape() {
        List<VoxelShape> shapes = Lists.newArrayList();
        shapes.add(Block.box(6, 0, 6, 10, 32, 10));
        shapes.add(Block.box(0, 32, 0, 16, 48, 16));
        return ShapeUtils.combineAll(shapes);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Part part = state.getValue(PART);
        if (part == Part.MIDDLE) {
            BlockState bottomState = worldIn.getBlockState(pos.below());
            return bottomState.getBlock() == this && bottomState.getValue(PART) == Part.BOTTOM;
        } else if (part == Part.TOP) {
            BlockState middleState = worldIn.getBlockState(pos.below());
            return middleState.getBlock() == this && middleState.getValue(PART) == Part.MIDDLE;
        } else {
            return super.canSurvive(state, worldIn, pos);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState newState, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, stack);
    }

    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        Part part = state.getValue(PART);
        BlockPos bottomPos = part == Part.BOTTOM ? pos : part == Part.MIDDLE ? pos.below() : pos.below(2);
        BlockPos middlePos = part == Part.MIDDLE ? pos : part == Part.BOTTOM ? pos.above() : pos.below();
        BlockPos topPos = part == Part.TOP ? pos : part == Part.MIDDLE ? pos.above() : pos.above(2);
        BlockState bottomState = worldIn.getBlockState(bottomPos);
        BlockState middleState = worldIn.getBlockState(middlePos);
        BlockState topState = worldIn.getBlockState(topPos);
        if (bottomState.getBlock() == this && middleState.getBlock() == this && topState.getBlock() == this) {
            worldIn.setBlock(bottomPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.setBlock(middlePos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(player, 2001, bottomPos, Block.getId(bottomState));
            worldIn.levelEvent(player, 2001, middlePos, Block.getId(middleState));
            worldIn.levelEvent(player, 2001, topPos, Block.getId(topState));
            if (!worldIn.isClientSide && !player.isCreative()) {
                dropResources(bottomState, worldIn, bottomPos, null, player, player.getMainHandItem());
                dropResources(middleState, worldIn, middlePos, null, player, player.getMainHandItem());
                dropResources(topState, worldIn, topPos, null, player, player.getMainHandItem());
            }
        }
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        this.placeRestAt(worldIn, pos, 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        return blockpos.getY() < context.getLevel().getMaxBuildHeight() - 2
                && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)
                && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) ? this.defaultBlockState()
                : null;
    }

    public void placeRestAt(Level worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(PART, Part.MIDDLE), flags);
        worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(PART, Part.TOP), flags);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, LIT, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Part part = state.getValue(PART);
        return part == Part.BOTTOM ? SHAPE
                : part == Part.MIDDLE ? SHAPE.move(0, -1d, 0)
                : part == Part.TOP ? SHAPE.move(0, -2d, 0) : Shapes.block();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide && level.dimensionType().hasSkyLight() ? createTickerHelper(blockEntityType, InteriorBlockEntities.LAMP, NightlightLampBlock::tickEntity) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return InteriorBlockEntities.LAMP.create(blockPos, blockState);
    }

    public enum Part implements StringRepresentable {
        BOTTOM, MIDDLE, TOP;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

}
