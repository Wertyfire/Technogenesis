package ru.wertyfiregames.technogenesis.inventory.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.block.entity.PressBlockEntity;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
import ru.wertyfiregames.technogenesis.init.TechMenuTypes;

public class PressMenu extends AbstractContainerMenu {
    public final PressBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public PressMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
                new ItemStacksResourceHandler(4), new SimpleContainerData(PressBlockEntity.NUM_DATA_VALUES));
    }

    public PressMenu(int containerId, Inventory inv, BlockEntity entity, ItemStacksResourceHandler handler, ContainerData data) {
        super(TechMenuTypes.BURNER_PRESS.get(), containerId);

        blockEntity = (PressBlockEntity) entity;
        level = inv.player.level();
        this.data = data;

        addStandardInventorySlots(inv, 8, 100);

        addSlot(new ResourceHandlerSlot(handler, handler::set, 0, 26, 69));
        addSlot(new ResourceHandlerSlot(handler, handler::set, 1, 80, 17));
        addSlot(new ResourceHandlerSlot(handler, handler::set, 2, 106, 41));
        addSlot(new ResourceHandlerSlot(handler, handler::set, 3, 80, 69) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return false;
            }
        });

        addDataSlots(data);
    }

    public boolean isBurning() {
        return data.get(PressBlockEntity.DATA_FUEL_BURN_REMAINING) > 0;
    }

    public boolean isPressing() {
        return data.get(PressBlockEntity.DATA_PRESSING_PROGRESS) > 0;
    }

    public int getPressureLevel() {
        return data.get(PressBlockEntity.DATA_PRESSURE_LEVEL);
    }

    public int getScaledBurnProgress() {
        int burnt = data.get(PressBlockEntity.DATA_FUEL_BURN_REMAINING);
        int maxBurnTime = data.get(PressBlockEntity.DATA_FUEL_BURN_DURATION);
        int sizePixels = 14;

        return maxBurnTime != 0 && burnt != 0 ? burnt * sizePixels / maxBurnTime : 0;
    }

    public int getScaledPressProgress() {
        int progress = data.get(PressBlockEntity.DATA_PRESSING_PROGRESS);
        int maxProgress = data.get(PressBlockEntity.DATA_PRESSING_TOTAL_TIME);
        int sizePixels = 24;

        return progress != 0 && maxProgress != 0 ? progress * sizePixels / maxProgress : 0;
    }

    public float getPressureArrowRotationPercents() {//TODO
        return 50;
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, TechBlocks.BURNER_PRESS.get());
    }
}