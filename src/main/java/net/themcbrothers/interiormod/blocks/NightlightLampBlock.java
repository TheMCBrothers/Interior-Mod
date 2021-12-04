package net.themcbrothers.interiormod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.themcbrothers.interiormod.blockentity.NightlightLampBlockEntity;
import net.themcbrothers.interiormod.init.InteriorBlockEntities;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class NightlightLampBlock extends BaseEntityBlock {

    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public NightlightLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
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

    private static void updateSignalStrength(BlockState state, Level level, BlockPos pos) {
        int value = level.getBrightness(LightLayer.SKY, pos.above()) - level.getSkyDarken();
        value = Mth.clamp(15 - value, 0, 15);

        boolean lit = value > 0;
        if (state.getValue(LIT) != lit) {
            level.setBlock(pos, state.setValue(LIT, lit), 3);
        }

    }

    static void tickEntity(Level level, BlockPos pos, BlockState state, NightlightLampBlockEntity blockEntity) {
        if (level.getGameTime() % 20L == 0L) {
            updateSignalStrength(state, level, pos);
        }

    }
}
