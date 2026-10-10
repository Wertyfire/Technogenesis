package ru.wertyfiregames.technogenesis.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;
import ru.wertyfiregames.technogenesis.init.TechRecipeTypes;
import ru.wertyfiregames.technogenesis.inventory.menu.PressMenu;
import ru.wertyfiregames.technogenesis.recipe.pressing.PressingRecipe;
import ru.wertyfiregames.technogenesis.recipe.pressing.PressingRecipeInput;

import java.util.Optional;

public class PressBlockEntity extends BaseMachineBlockEntity implements MenuProvider {//TODO whistling
    private static final int FUEL_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int STAMP_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    public static final int DATA_FUEL_BURN_REMAINING = 0;
    public static final int DATA_FUEL_BURN_DURATION = 1;
    public static final int DATA_PRESSURE_LEVEL = 2;
    public static final int DATA_PRESSING_PROGRESS = 3;
    public static final int DATA_PRESSING_TOTAL_TIME = 4;
    public static final int NUM_DATA_VALUES = 5;

    public static final int MIN_PRESSURE_CAPACITY = 0;
    public static final int MIN_WORKING_PRESSURE = 200;
    public static final int PRESSURE_WITH_WHISTLING = 2000;
    public static final int STANDALONE_MAX_PRESSURE = 3000; //end of yellow zone
    public static final int MAX_PRESSURE_CAPACITY = 4000;

    private static final int DEFAULT_BURN_TIME_REMAINING = 0;
    private static final int DEFAULT_BURN_TOTAL_TIME = 0;
    private static final int DEFAULT_PRESSURE_LEVEL = 0;
    private static final int DEFAULT_PRESSING_TIMER = 0;
    private static final int DEFAULT_PRESSING_TOTAL_TIME = 0;

    protected ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(4) {
        @Override
        protected void onContentsChanged(int index, @NonNull ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            setChanged();
        }
    };

    private int burnTimeRemaining;
    private int burnTotalTime;
    private int pressureLevel;
    private int pressingTimer;
    private int pressingTotalTime;
    private boolean goingUp;

    private static final int PRESSING_TOTAL_TIME = 20;

    private final ContainerData data;

    private static final Component DEFAULT_NAME = Component.translatable("block.technogenesis.burner_press");

    public PressBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TechBlockEntities.BURNER_PRESS.get(), blockPos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int dataId) {
                return switch (dataId) {
                    case DATA_FUEL_BURN_REMAINING -> burnTimeRemaining;
                    case DATA_FUEL_BURN_DURATION -> burnTotalTime;
                    case DATA_PRESSURE_LEVEL -> pressureLevel;
                    case DATA_PRESSING_PROGRESS -> pressingTimer;
                    case DATA_PRESSING_TOTAL_TIME -> pressingTotalTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case DATA_FUEL_BURN_REMAINING -> burnTimeRemaining = value;
                    case DATA_FUEL_BURN_DURATION -> burnTotalTime = value;
                    case DATA_PRESSURE_LEVEL -> pressureLevel = value;
                    case DATA_PRESSING_PROGRESS -> pressingTimer = value;
                    case DATA_PRESSING_TOTAL_TIME -> pressingTotalTime = value;
                }
            }

            @Override
            public int getCount() {
                return NUM_DATA_VALUES;
            }
        };
    }

    @Override
    public @NonNull Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containedId, @NonNull Inventory inventory, @NonNull Player player) {
        return new PressMenu(containedId, inventory, this, this.inventory, this.data);
    }

    //Saving

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inventory", inventory);
        output.putInt("pressing_time_spent", pressingTimer);
        output.putInt("pressing_total_time", pressingTotalTime);
        output.putInt("burn_time_remaining", burnTimeRemaining);
        output.putInt("burn_total_time", burnTotalTime);
        output.putInt("pressure_level", pressureLevel);
        output.putBoolean("press_going_up", goingUp);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        input.child("inventory").ifPresent(inventory::deserialize);
        pressingTimer = input.getIntOr("pressing_time_spent", 0);
        pressingTotalTime = input.getIntOr("pressing_total_time", 0);
        burnTimeRemaining = input.getIntOr("burn_time_remaining", 0);
        burnTotalTime = input.getIntOr("burn_total_time", 0);
        pressureLevel = input.getIntOr("pressure_level", 0);
        goingUp = input.getBooleanOr("press_going_up", false);
    }

    public void dropItems() {//FIXME fix nullability bug
        SimpleContainer inv = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, i);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }
        Containers.dropContents(level, worldPosition, inv);
    }

    public void serverTick(ServerLevel level, BlockPos pos, BlockState state) {
        boolean changed = false;

        if (isFuelBurning()) {
            decreaseBurnTime();
            increasePressureLevel();

            changed = true;
        } else if (hasAnyPressure() && !hasBurnableFuel(level)) {
            decreasePressureLevel();

            changed = true;
        } else if (hasBurnableFuel(level)) {
            consumeFuel(level);

            changed = true;
        }

        if (hasRecipe() && hasPressure() && !goingUp) {
            trySetProgress();

            increasePressingProgress();

            if (hasPressingFinished()) {
                consumePressureOnPress();
                pressItem();
                goingUp = true;
            }

            changed = true;
        } else if (!hasRecipe() && !goingUp) {
            goingUp = true;
            changed = true;
        } else if (hasAnyPressure() && goingUp) {
            increasePressingProgress();

            if (hasPressReturned()) resetProgress();

            changed = true;
        }

        if (changed) setChanged(level, pos, state);
    }

    private void pressItem() {
        Optional<RecipeHolder<PressingRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().assemble(new PressingRecipeInput(
                inventory.getResource(INPUT_SLOT).toStack(),
                inventory.getResource(STAMP_SLOT).toStack()));

        try (Transaction transaction = Transaction.openRoot()) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, OUTPUT_SLOT);

            inventory.extract(INPUT_SLOT, inventory.getResource(INPUT_SLOT), 1, transaction);
//            inventory.extract(STAMP_SLOT, inventory.getResource(STAMP_SLOT), 1, transaction);TODO decrease durability
            inventory.set(OUTPUT_SLOT, ItemResource.of(output), itemAccess.getAmount() + output.getCount());

            transaction.commit();
        }
    }

    private void consumeFuel(ServerLevel level) {
        ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, FUEL_SLOT);
        int burnDuration = getBurnDuration(level, itemAccess.getResource().toStack());
        try (Transaction transaction = Transaction.openRoot()) {
            inventory.extract(FUEL_SLOT, inventory.getResource(FUEL_SLOT), 1, transaction);
            transaction.commit();
            burnTotalTime = burnTimeRemaining = burnDuration;
        }
    }

    private void consumePressureOnPress() {
        pressureLevel -= 30; //TODO replace with value from recipe
        validatePressure();
    }

    private void validatePressure() {
        if (pressureLevel < 0) pressureLevel = 0;
        else if (pressureLevel > MAX_PRESSURE_CAPACITY) pressureLevel = MAX_PRESSURE_CAPACITY;
    }

    private void trySetProgress() {
        if (pressingTotalTime == 0) {
            pressingTimer = DEFAULT_PRESSING_TIMER;
            pressingTotalTime = PRESSING_TOTAL_TIME;
        }
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<PressingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = recipe.get().value().assemble(new PressingRecipeInput(inventory.getResource(INPUT_SLOT).toStack(), inventory.getResource(STAMP_SLOT).toStack()));

        boolean itemMatches = canInsertStackIntoOutputSlot(output);
        boolean amountCorrect = canInsertAmountIntoOutputSlot(output.getCount());

        return itemMatches && amountCorrect;
    }

    private boolean canInsertStackIntoOutputSlot(ItemStack output) {
        ItemResource resource = inventory.getResource(OUTPUT_SLOT);
        return resource.isEmpty() || resource.is(output.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int amount) {
        ItemResource resource = inventory.getResource(OUTPUT_SLOT);
        int maxCount = resource.isEmpty() ? 64 : resource.getMaxStackSize();
        int currentCount = inventory.getAmountAsInt(OUTPUT_SLOT);

        return maxCount >= currentCount + amount;
    }

    private Optional<RecipeHolder<PressingRecipe>> getCurrentRecipe() {
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(TechRecipeTypes.PRESSING_TYPE.get(),
                        new PressingRecipeInput(inventory.getResource(INPUT_SLOT).toStack(), inventory.getResource(STAMP_SLOT).toStack()), level);
    }

    private boolean hasBurnableFuel(ServerLevel level) {
        ItemStack fuel = inventory.getResource(FUEL_SLOT).toStack();
        return getBurnDuration(level, fuel) > 0;
    }

    private boolean isFuelBurning() {
        return burnTimeRemaining > 0;
    }

    private int getBurnDuration(ServerLevel level, ItemStack fuel) {
        return ResolvableInt.getFromItem(fuel, DataComponents.COOKING_FUEL, CookingFuel::burnTime, getSomeShit(level, fuel), 0);
    }

    private void decreaseBurnTime() {
        if (burnTimeRemaining > 0) burnTimeRemaining--;
    }

    private boolean hasPressure() {
        return pressureLevel > MIN_WORKING_PRESSURE;
    }

    private boolean hasAnyPressure() {
        return pressureLevel > MIN_PRESSURE_CAPACITY;
    }

    private boolean maxPressureReached() {
        return pressureLevel >= STANDALONE_MAX_PRESSURE;
    }

    private void increasePressureLevel() {
        if (!maxPressureReached()) pressureLevel++;
    }

    private void decreasePressureLevel() {
        if (hasAnyPressure()) pressureLevel--;
    }

    private boolean hasPressingFinished() {
        return pressingTimer >= pressingTotalTime;
    }

    private boolean hasPressReturned() {
        return goingUp && pressingTimer <= 0;
    }

    private void increasePressingProgress() {//TODO multiply by pressure
        if (goingUp) pressingTimer--;
        else pressingTimer++;
    }

    private void resetProgress() {
        pressingTimer = DEFAULT_PRESSING_TIMER;
        pressingTotalTime = DEFAULT_PRESSING_TOTAL_TIME;
        goingUp = false;
    }

    public int getPressingProgress() {
        return pressingTimer;
    }
    public int getTotalPressingTime() {
        return pressingTotalTime;
    }
}