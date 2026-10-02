package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.block.entity.DummyBlockEntity;

public class DummyBlock extends Block implements EntityBlock {
    public DummyBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.IMMOVEABLE).noOcclusion().noLootTable());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new DummyBlockEntity(blockPos, blockState);
    }

    //Logic start

    @Override
    public void spawnDestroyByEntityParticles(Level level, @Nullable Entity entity, @NonNull BlockPos pos, @NonNull BlockState state) {
        BlockState parent = level.getBlockState(getParentPos(level, pos));
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, getId(parent));
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

//    @Override TODO finish this
//    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
//        return level.getBlockState(getParentPos(level, pos)).getBlock().defaultBlockState().getShape(level, getParentPos(level, pos)).move(pos.getX() - getParentPos(level, pos).getX(), pos.getY() - getParentPos(level, pos).getY(), pos.getZ() - getParentPos(level, pos).getZ());
//    }

    @Override
    protected float getDestroyProgress(@NonNull BlockState state, @NonNull Player player, BlockGetter level, @NonNull BlockPos pos) {//TODO implement
        return level.getBlockState(getParentPos(level, pos)).getDestroyProgress(player, level, getParentPos(level, pos));
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, BlockHitResult hitResult) {
        return level.getBlockState(getParentPos(level, pos)).useWithoutItem(level, player, hitResult.withPosition(getParentPos(level, pos)));
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack itemStack, @NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, BlockHitResult hitResult) {
        return level.getBlockState(getParentPos(level, pos)).useItemOn(itemStack, level, player, hand, hitResult.withPosition(getParentPos(level, pos)));
    }

    @Override
    public boolean onDestroyedByPlayer(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull ItemStack toolStack, boolean willHarvest, @NonNull FluidState fluid) {
        BlockPos parentPos = getParentPos(level, pos);
        if (level.getBlockState(parentPos).getBlock() instanceof BigBlock) {
            level.getBlockState(parentPos).getBlock().playerWillDestroy(level, parentPos, state, player);
            level.destroyBlock(parentPos, !player.isCreative());
        }
        level.removeBlock(pos, false);
        return false;
    }

    @Override
    public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(@NonNull BlockState state, ServerLevel level, @NonNull BlockPos pos, @NonNull Explosion explosion) {//TODO finish
        BigBlock.ExplosionResult result = ((BigBlock) level.getBlockState(getParentPos(level, pos)).getBlock()).dummyExploded(level, pos, explosion);
        if (result == BigBlock.ExplosionResult.DESTROY) level.getBlockState(getParentPos(level, pos)).onBlockExploded(level, pos, explosion);
    }

    public void setParentPos(BlockGetter level, BlockPos pos, BlockPos parentPos) {
        DummyBlockEntity dummyEntity = (DummyBlockEntity) level.getBlockEntity(pos);
        dummyEntity.setParent(parentPos);
    }

    public BlockPos getParentPos(BlockGetter level, BlockPos sourcePos) {
        DummyBlockEntity dummyEntity = (DummyBlockEntity) level.getBlockEntity(sourcePos);
        return dummyEntity.getParent();
    }
}