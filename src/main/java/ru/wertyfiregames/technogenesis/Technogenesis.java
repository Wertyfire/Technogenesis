package ru.wertyfiregames.technogenesis;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import ru.wertyfiregames.technogenesis.init.*;

@Mod(Technogenesis.MODID)
public class Technogenesis {
    public static final String MODID = "technogenesis";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Technogenesis(IEventBus modEventBus, ModContainer modContainer) {
        TechItems.ITEMS.register(modEventBus);
        TechBlocks.BLOCKS.register(modEventBus);
        TechCreativeTabs.TABS.register(modEventBus);
        TechBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        TechMenuTypes.MENU_TYPES.register(modEventBus);

        TechRecipeTypes.SERIALIZERS.register(modEventBus);
        TechRecipeTypes.TYPES.register(modEventBus);
        TechRecipeBookCategories.RECIPE_BOOK_CATEGORIES.register(modEventBus);
    }

    public static Identifier createIdentifier(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
    public static Identifier createCommonIdentifier(String path) {
        return Identifier.fromNamespaceAndPath("c", path);
    }
}