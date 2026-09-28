package ru.wertyfiregames.technogenesis.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.init.TechItems;

import java.util.concurrent.CompletableFuture;

public class TechItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public TechItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Technogenesis.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CASSITERITE)
                .add(TechItems.CASSITERITE.getKey());

        tag(Tags.Items.RAW_MATERIALS)
                .addTag(CASSITERITE);

        copy(TechBlockTagsProvider.CASSITERITE_ORES, CASSITERITE_ORES);
        copy(TechBlockTagsProvider.COMMON_CASSITERITE_ORES, COMMON_CASSITERITE_ORES);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
    }


    public static final TagKey<Item> CASSITERITE = ItemTags.create(Technogenesis.createCommonIdentifier("raw_materials/cassiterite"));

//    Block tags duplicates
    public static final TagKey<Item> CASSITERITE_ORES = ItemTags.create(Technogenesis.createIdentifier("cassiterite_ores"));
    public static final TagKey<Item> COMMON_CASSITERITE_ORES = ItemTags.create(Technogenesis.createCommonIdentifier("ores/cassiterite"));
}