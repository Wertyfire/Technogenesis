package ru.wertyfiregames.technogenesis.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.recipe.pressing.PressingRecipe;

public class TechRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Technogenesis.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Technogenesis.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PressingRecipe>> PRESSING_SERIALIZER =
            SERIALIZERS.register("pressing", () -> new RecipeSerializer<>(PressingRecipe.CODEC, PressingRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<PressingRecipe>> PRESSING_TYPE =
            TYPES.register("pressing", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "pressing";
                }});
}