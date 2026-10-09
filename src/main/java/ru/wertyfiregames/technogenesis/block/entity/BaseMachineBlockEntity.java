package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
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

    public void clientTick(Level level, BlockPos pos, BlockState state) {}
    public void serverTick(ServerLevel level, BlockPos pos, BlockState state) {}

    public boolean hasClientTicker() {
        return false;
    }
    public boolean hasServerTicker() {
        return true;
    }

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
                        .withParameter(LootContextParams.CONTAINER, new Container() {
                            @Override
                            public int getContainerSize() {
                                return 0;
                            }

                            @Override
                            public boolean isEmpty() {
                                return false;
                            }

                            @Override
                            public ItemStack getItem(int i) {
                                return null;
                            }

                            @Override
                            public ItemStack removeItem(int i, int i1) {
                                return null;
                            }

                            @Override
                            public ItemStack removeItemNoUpdate(int i) {
                                return null;
                            }

                            @Override
                            public void setItem(int i, ItemStack itemStack) {

                            }

                            @Override
                            public void setChanged() {

                            }

                            @Override
                            public boolean stillValid(Player player) {
                                return false;
                            }

                            @Override
                            public void clearContent() {

                            }
                        })
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