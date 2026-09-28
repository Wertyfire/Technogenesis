package ru.wertyfiregames.technogenesis.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;

public class TechCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Technogenesis.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESOURCES = TABS.register("resources", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.technogenesis.resources"))
            .icon(() -> new ItemStack(TechItems.CASSITERITE.asItem()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(TechBlocks.CASSITERITE_ORE);
                output.accept(TechBlocks.DEEPSLATE_CASSITERITE_ORE);
                output.accept(TechBlocks.CASSITERITE_BLOCK);
                output.accept(TechItems.CASSITERITE);
            }).build());
}