package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.NeoForgeLootContextParams;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public abstract class BaseMachineBlockEntity extends BlockEntity {
    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    //Ticking

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) clientTick(level, pos, state);
        else serverTick((ServerLevel) level, pos, state);
    }

    protected void clientTick(Level level, BlockPos pos, BlockState state) {}
    protected abstract void serverTick(ServerLevel level, BlockPos pos, BlockState state);

    //Some shit

    protected LootContext getSomeShit(ServerLevel level) {
        return getSomeShit(level, ItemStack.EMPTY);
    }

    protected LootContext getSomeShit(ServerLevel level, ItemStack queriedStack) {
        return (new LootContext.Builder(
                new LootParams.Builder(level)
                        .withParameter(LootContextParams.BLOCK_STATE, getBlockState())
                        .withParameter(LootContextParams.BLOCK_ENTITY, this)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(getBlockPos()))
                        .withOptionalParameter(NeoForgeLootContextParams.QUERIED_STACK, queriedStack.isEmpty() ? null : queriedStack)
                        .create(LootContextParamSets.CONTAINER_PROCESS)))
                .create(Optional.empty());
    }

    //Syncing

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}