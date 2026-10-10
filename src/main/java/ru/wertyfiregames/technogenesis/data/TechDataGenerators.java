package ru.wertyfiregames.technogenesis.data;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import ru.wertyfiregames.technogenesis.Technogenesis;

@EventBusSubscriber(modid = Technogenesis.MODID)
public class TechDataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createBlockAndItemTags(TechBlockTagsProvider::new, TechItemTagsProvider::new);

        event.createProvider(TechModelProvider::new);
    }

    @SubscribeEvent
    public static void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.gatherFor(Technogenesis.MODID)
                .recipe(TechRecipeProvider::new)
                .lootTable(new LootTableProvider.SubProviderEntry(TechBlockLootProvider::new, LootContextParamSets.BLOCK));
    }
}