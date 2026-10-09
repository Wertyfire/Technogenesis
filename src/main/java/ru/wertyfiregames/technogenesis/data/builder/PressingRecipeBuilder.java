package ru.wertyfiregames.technogenesis.data.builder;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.recipe.pressing.PressingRecipe;

public class PressingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final Ingredient input;
    private final Ingredient stamp;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private PressingRecipeBuilder(RecipeCategory category, Ingredient input, Ingredient stamp, ItemStackTemplate result) {
        this.category = category;
        this.input = input;
        this.stamp = stamp;
        this.result = result;
    }

    public static PressingRecipeBuilder pressing(Ingredient input, Ingredient stamp, ItemLike result) {
        return new PressingRecipeBuilder(RecipeCategory.MISC, input, stamp, new ItemStackTemplate(result.asItem()));
    }

    @Override
    public @NonNull RecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public @NonNull RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(@NonNull RecipeOutput output, @NonNull ResourceKey<Recipe<?>> id) {
        PressingRecipe recipe = new PressingRecipe(this.input, this.stamp, this.result);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}