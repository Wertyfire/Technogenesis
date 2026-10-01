package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.block.entity.DummyBlockEntity;

public class DummyBlock extends Block implements EntityBlock {
    public DummyBlock(Properties properties) {
        super(properties.noOcclusion().noLootTable());
    }

    @Override
    public void spawnDestroyParticles(Level level, BlockPos pos, BlockState state) {
        super.spawnDestroyParticles(level, pos, state);
//        TODO implement
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new DummyBlockEntity(blockPos, blockState);
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

//    @Override TODO finish this
//    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
//        return level.getBlockState(getParentPos(level, pos)).getBlock().defaultBlockState().getShape(level, getParentPos(level, pos)).move();
//    }

    @Override
    protected float getDestroyProgress(@NonNull BlockState state, @NonNull Player player, BlockGetter level, @NonNull BlockPos pos) {
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
    public @NonNull BlockState playerWillDestroy(Level level, @NonNull BlockPos pos, @NonNull BlockState state, Player player) {
        level.destroyBlock(pos, !player.isCreative());
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {//TODO finish
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