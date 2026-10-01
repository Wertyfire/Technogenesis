package ru.wertyfiregames.technogenesis.data;

import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
import ru.wertyfiregames.technogenesis.init.TechItems;

import java.util.Set;

public class TechBlockLootProvider extends BlockLootSubProvider {
    public TechBlockLootProvider(LootTableSubProvider.Context output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    protected void generate() {
        dropSelf(TechBlocks.CASSITERITE_BLOCK.get());

        dropSelf(TechBlocks.BURNER_PRESS.get());

        add(TechBlocks.CASSITERITE_ORE.get(), createOreDrop(TechBlocks.CASSITERITE_ORE.get(), TechItems.CASSITERITE.get()));
        add(TechBlocks.DEEPSLATE_CASSITERITE_ORE.get(), createOreDrop(TechBlocks.DEEPSLATE_CASSITERITE_ORE.get(), TechItems.CASSITERITE.get()));
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return TechBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}