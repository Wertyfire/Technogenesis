package ru.wertyfiregames.technogenesis.init;

import net.minecraft.core.registries.Registries;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.block.entity.DummyBlockEntity;
import ru.wertyfiregames.technogenesis.block.entity.PressBlockEntity;

public class TechBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Technogenesis.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DummyBlockEntity>> DUMMY = BLOCK_ENTITIES.register("dummy", () -> new BlockEntityType<>(DummyBlockEntity::new, TechBlocks.DUMMY.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PressBlockEntity>> PRESS = BLOCK_ENTITIES.register("press", () -> new BlockEntityType<>(PressBlockEntity::new, TechBlocks.BURNER_PRESS.get()));
}