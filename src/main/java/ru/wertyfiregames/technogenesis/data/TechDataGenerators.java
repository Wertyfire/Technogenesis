package ru.wertyfiregames.technogenesis.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import ru.wertyfiregames.technogenesis.Technogenesis;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Technogenesis.MODID)
public class TechDataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, new LootTableProvider(
                        Set.of(), List.of(new LootTableProvider.SubProviderEntry(TechBlockLootProvider::new, LootContextParamSets.BLOCK))))
                .add(RecipeProvider.asBootstrap(TechRecipeProvider::new))
        );

        event.createBlockAndItemTags(TechBlockTagsProvider::new, TechItemTagsProvider::new);

        event.createProvider(TechModelProvider::new);
    }
}