package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
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
        output.putInt("dummy_parent_x", parent.getX());
        output.putInt("dummy_parent_y", parent.getY());
        output.putInt("dummy_parent_z", parent.getZ());
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        parent = new BlockPos(input.getIntOr("dummy_parent_x", 0), input.getIntOr("dummy_parent_y", 0), input.getIntOr("dummy_parent_z", 0));
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        CompoundTag output = super.getUpdateTag(registries);
        output.putInt("dummy_parent_x", parent.getX());
        output.putInt("dummy_parent_y", parent.getY());
        output.putInt("dummy_parent_z", parent.getZ());
        return output;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}