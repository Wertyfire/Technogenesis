package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.block.entity.PressBlockEntity;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;

/*After I saw NTM's burner press I can't imagine another model for it*/
public class PressBlock extends BigBlock {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 29, 16);

    public PressBlock(Properties properties) {
        super(properties);
        setDummyPositions(new BlockPos(0, 1, 0));
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof PressBlockEntity pressBlockEntity) {
                player.openMenu(new SimpleMenuProvider(pressBlockEntity, Component.translatable("block.technogenesis.burner_press")), pos);
                //TODO add interaction stat
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ExplosionResult dummyExploded(Level level, BlockPos dummyPos, Explosion explosion) {
        return ExplosionResult.DESTROY;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new PressBlockEntity(blockPos, blockState);
    }

    @Override
    public boolean onDestroyedByPlayer(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull ItemStack toolStack, boolean willHarvest, @NonNull FluidState fluid) {
        if (level.getBlockEntity(pos) instanceof PressBlockEntity pressBlockEntity) {
            pressBlockEntity.dropItems();
        }
        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        return createTickerHelper(type, TechBlockEntities.BURNER_PRESS.get(), level.isClientSide() ?
                (level1, pos, state, entity) ->
                        entity.clientTick(level1, pos, state) :
                (level1, pos, state, entity) ->
                        entity.serverTick((ServerLevel) level1, pos, state));
    }
}