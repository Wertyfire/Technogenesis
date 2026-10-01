package ru.wertyfiregames.technogenesis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.block.entity.PressBlockEntity;

/*After I saw NTM's burner press I can't imagine another model for it*/
public class PressBlock extends BigBlock {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 29, 16);

    public PressBlock(Properties properties) {
        super(properties);
        setDummyPositions(new BlockPos(0, 1, 0));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PressBlockEntity(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ExplosionResult dummyExploded(Level level, BlockPos dummyPos, Explosion explosion) {
        return ExplosionResult.DESTROY;
    }
}