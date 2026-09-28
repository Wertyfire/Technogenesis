package ru.wertyfiregames.technogenesis.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
import ru.wertyfiregames.technogenesis.init.TechItems;

public class TechRecipeProvider extends RecipeProvider {

    public TechRecipeProvider(BootstrapContext<Recipe<?>> recipeContext, BootstrapContext<Advancement> advancementContext) {
        super(recipeContext, advancementContext);
    }

    @Override
    protected void buildRecipes() {
        resourceToBlockWithUnpack(TechItems.CASSITERITE, TechBlocks.CASSITERITE_BLOCK, "cassiterite_from_cassiterite_block", "cassiterite");
    }

    private static Identifier id(String path) {
        return Technogenesis.createIdentifier(path);
    }

    private void resourceToBlockWithUnpack(ItemLike resource, ItemLike block, String unpackingId, String unpackingGroup) {
        shapeless(RecipeCategory.MISC, resource, 9).requires(block).group(unpackingGroup).unlockedBy(getHasName(block), has(block)).save(output, ResourceKey.create(Registries.RECIPE, id(unpackingId)));
        shaped(RecipeCategory.BUILDING_BLOCKS, block).define('#', resource).pattern("###").pattern("###").pattern("###").group(null).unlockedBy(getHasName(resource), has(resource)).save(output, ResourceKey.create(Registries.RECIPE, id(getSimpleRecipeName(block))));
    }
}