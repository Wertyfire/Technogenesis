package ru.wertyfiregames.technogenesis.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;

public class TechRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, Technogenesis.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> METALS = RECIPE_BOOK_CATEGORIES.register("pressing_metals", RecipeBookCategory::new);
}