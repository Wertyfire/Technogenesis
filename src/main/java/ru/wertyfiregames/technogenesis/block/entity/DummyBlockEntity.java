package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;

public class DummyBlockEntity extends BlockEntity {
    private BlockPos parent = BlockPos.ZERO;

    public DummyBlockEntity(BlockPos pos, BlockState state) {
        super(TechBlockEntities.DUMMY.get(), pos, state);
    }

    public void setParent(BlockPos pos) {
        parent = pos;
        setChanged();
    }

    public BlockPos getParent() {
        return parent;
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("parent_x", parent.getX());
        output.putInt("parent_y", parent.getY());
        output.putInt("parent_z", parent.getZ());
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        parent = new BlockPos(input.getIntOr("parent_x", 0), input.getIntOr("parent_y", 0), input.getIntOr("parent_z", 0));
    }
}