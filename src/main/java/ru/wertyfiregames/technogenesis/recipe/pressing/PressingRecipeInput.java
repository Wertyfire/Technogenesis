package ru.wertyfiregames.technogenesis.recipe.pressing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jspecify.annotations.NonNull;

public record PressingRecipeInput(ItemStack input, ItemStack stamp) implements RecipeInput {
    @Override
    public @NonNull ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> input;
            case 1 -> stamp;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 2;
    }
}