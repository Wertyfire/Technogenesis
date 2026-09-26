package ru.wertyfiregames.technogenesis;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
import ru.wertyfiregames.technogenesis.init.TechCreativeTabs;
import ru.wertyfiregames.technogenesis.init.TechItems;

@Mod(Technogenesis.MODID)
public class Technogenesis {
    public static final String MODID = "technogenesis";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Technogenesis(IEventBus modEventBus, ModContainer modContainer) {
        TechItems.ITEMS.register(modEventBus);
        TechBlocks.BLOCKS.register(modEventBus);
        TechCreativeTabs.TABS.register(modEventBus);
    }
}