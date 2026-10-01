package ru.wertyfiregames.technogenesis.init;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.block.DummyBlock;
import ru.wertyfiregames.technogenesis.block.PressBlock;

import java.util.function.Function;

public class TechBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Technogenesis.MODID);

    public static final DeferredBlock<Block> CASSITERITE_ORE = registerWithItem("cassiterite_ore",
            properties -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops().strength(4.0f, 4.0f)));
    public static final DeferredBlock<Block> DEEPSLATE_CASSITERITE_ORE = registerWithItem("deepslate_cassiterite_ore",
            properties -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops().strength(5.0f, 4.0f)));
    public static final DeferredBlock<Block> CASSITERITE_BLOCK = registerWithItem("cassiterite_block",
            properties -> new Block(properties.mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.IRON_XYLOPHONE).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops().strength(6f, 6f)));

    public static final DeferredBlock<Block> BURNER_PRESS = registerWithItem("burner_press",
            properties -> new PressBlock(properties.mapColor(MapColor.TERRACOTTA_GRAY).sound(SoundType.IRON)
                    .requiresCorrectToolForDrops().strength(5f, 6f).noOcclusion().isViewBlocking((_, _, _, _) -> false)));

    public static final DeferredBlock<Block> DUMMY = register("dummy", DummyBlock::new);

    public static <T extends Block> DeferredBlock<T> registerWithItem(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, block);
        return block;
    }

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> function) {
        return BLOCKS.registerBlock(name, function);
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        TechItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }
}