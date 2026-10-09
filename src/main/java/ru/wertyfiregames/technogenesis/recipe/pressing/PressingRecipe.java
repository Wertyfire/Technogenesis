package ru.wertyfiregames.technogenesis.recipe.pressing;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.init.TechRecipeTypes;

public record PressingRecipe(Ingredient input, Ingredient stamp, ItemStackTemplate output) implements Recipe<PressingRecipeInput> {
    public static final MapCodec<PressingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Ingredient.CODEC.fieldOf("input").forGetter(PressingRecipe::input),
                    Ingredient.CODEC.fieldOf("stamp").forGetter(PressingRecipe::stamp),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(PressingRecipe::output)
            ).apply(instance, PressingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PressingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    PressingRecipe::input,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    PressingRecipe::stamp,

                    ItemStackTemplate.STREAM_CODEC,
                    PressingRecipe::output,

                    PressingRecipe::new);

    @Override
    public boolean matches(@NonNull PressingRecipeInput inputRecipe, @NonNull Level level) {
        return input.test(inputRecipe.getItem(0)) && stamp.test(inputRecipe.getItem(1));
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull PressingRecipeInput pressingRecipeInput) {
        return output.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "";
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<PressingRecipeInput>> getSerializer() {
        return TechRecipeTypes.PRESSING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<PressingRecipeInput>> getType() {
        return TechRecipeTypes.PRESSING_TYPE.get();
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_BLOCKS;
    }
}