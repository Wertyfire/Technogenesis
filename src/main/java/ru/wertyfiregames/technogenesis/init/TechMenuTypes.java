package ru.wertyfiregames.technogenesis.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.wertyfiregames.technogenesis.Technogenesis;

public class TechMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Technogenesis.MODID);
}