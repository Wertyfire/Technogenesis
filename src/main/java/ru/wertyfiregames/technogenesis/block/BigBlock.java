package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
//TODO add rotation checks!!!
public abstract class BigBlock extends BaseEntityBlock {
    protected BlockPos[] dummyPositions = new BlockPos[0];

    public BigBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        for (BlockPos dummy : dummyPositions) {
            if (!context.getLevel().getBlockState(blockPosWithOffset(context.getClickedPos(), dummy)).isAir()) {
                if (context.getPlayer() != null)
                    context.getPlayer().sendOverlayMessage(Component.translatable("technogenesis.multiblock.not_enough_space"));
                return null;
            }
//            if (context.getLevel().getEntities(null, dummy).isEmpty()) TODO implement entity check
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable LivingEntity by, @NonNull ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, by, itemStack);
        placeDummies(level, pos);
    }

    @Override
    public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        removeDummies(level, pos);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void spawnDestroyParticles(Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, getId(state));
    }

    @Override
    public void onBlockExploded(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull Explosion explosion) {
        removeDummies(level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }

    protected void placeDummies(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        for (BlockPos dummy : dummyPositions) {
            BlockPos withOffset = blockPosWithOffset(pos, dummy);
            level.setBlock(withOffset, TechBlocks.DUMMY.get().defaultBlockState(), 3);
            ((DummyBlock) level.getBlockState(withOffset).getBlock()).setParentPos(level, withOffset, pos);
        }
    }

    protected void removeDummies(Level level, BlockPos pos) {
        for (BlockPos dummy : dummyPositions) {
            if (level.getBlockState(blockPosWithOffset(pos, dummy)).is(TechBlocks.DUMMY)) {
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPosWithOffset(pos, dummy), getId(level.getBlockState(blockPosWithOffset(pos, dummy))));
                level.removeBlock(blockPosWithOffset(pos, dummy), false);
            }
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