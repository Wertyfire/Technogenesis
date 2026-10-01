package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
//TODO add rotation checks!!!
public abstract class BigBlock extends BaseEntityBlock {
    public BlockPos[] dummyPositions = new BlockPos[0];

    public BigBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        for (BlockPos dummy : dummyPositions) {
            if (context.getLevel().getBlockState(blockPosWithOffset(context.getClickedPos(), dummy)).isAir()) return null;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        for (BlockPos dummy : dummyPositions) {
            BlockPos withOffset = blockPosWithOffset(pos, dummy);
            level.setBlock(withOffset, TechBlocks.DUMMY.get().defaultBlockState(), 3);
            ((DummyBlock) level.getBlockState(withOffset).getBlock()).setParentPos(level, withOffset, pos);
            //TODO remove check
            System.out.println("onPlace called, blockentity placed: " + level.getBlockEntity(blockPosWithOffset(pos, dummy)) != null);
        }
    }

    @Override
    public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        removeDummies(level, pos);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull Explosion explosion) {
        removeDummies(level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }

    protected void removeDummies(Level level, BlockPos pos) {
        for (BlockPos dummy : dummyPositions) {
            if (level.getBlockState(blockPosWithOffset(pos, dummy)).getBlock() == TechBlocks.DUMMY.get())
                level.removeBlock(blockPosWithOffset(pos, dummy), false);
            //TODO remove
            System.out.println("playerWillDestroy called, blockentity placed: " + level.getBlockEntity(dummy) != null);
        }
    }

    protected void setDummyPositions(BlockPos... positions) {
        dummyPositions = new BlockPos[positions.length];
        System.arraycopy(positions, 0, dummyPositions, 0, positions.length);
    }

    public abstract ExplosionResult dummyExploded(Level level, BlockPos dummyPos, Explosion explosion);

    protected BlockPos blockPosWithOffset(BlockPos pos, BlockPos offset) {
        return new BlockPos(pos.getX() + offset.getX(), pos.getY() + offset.getY(), pos.getZ() + offset.getZ());
    }

    public enum ExplosionResult {
        DESTROY,
        CHANGE_MODEL
    }
}