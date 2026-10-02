package ru.wertyfiregames.technogenesis.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.init.TechBlocks;

import java.util.concurrent.CompletableFuture;

public class TechBlockTagsProvider extends BlockTagsProvider {
    public TechBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Technogenesis.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(CASSITERITE_ORES)
                .add(TechBlocks.CASSITERITE_ORE.getKey())
                .add(TechBlocks.DEEPSLATE_CASSITERITE_ORE.getKey());
        tag(COMMON_CASSITERITE_ORES).addTag(CASSITERITE_ORES);

        tag(BlockTags.ORES)
                .addTag(CASSITERITE_ORES);
        tag(Tags.Blocks.ORES)
                .addTag(COMMON_CASSITERITE_ORES);

        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(TechBlocks.DEEPSLATE_CASSITERITE_ORE.getKey());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TechBlocks.CASSITERITE_ORE.getKey())
                .add(TechBlocks.DEEPSLATE_CASSITERITE_ORE.getKey())
                .add(TechBlocks.CASSITERITE_BLOCK.getKey())
                .add(TechBlocks.BURNER_PRESS.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(TechBlocks.CASSITERITE_ORE.getKey())
                .add(TechBlocks.DEEPSLATE_CASSITERITE_ORE.getKey())
                .add(TechBlocks.CASSITERITE_BLOCK.getKey())
                .add(TechBlocks.BURNER_PRESS.getKey());

        tag(BlockTags.BLOCKS_MOTION_NO_LEAVES)
                .add(TechBlocks.CASSITERITE_BLOCK.getKey());
    }

    public static final TagKey<Block> CASSITERITE_ORES = BlockTags.create(Technogenesis.createIdentifier("cassiterite_ores"));

    //Common tags
    public static final TagKey<Block> COMMON_CASSITERITE_ORES = BlockTags.create(Technogenesis.createCommonIdentifier("ores/cassiterite"));
}