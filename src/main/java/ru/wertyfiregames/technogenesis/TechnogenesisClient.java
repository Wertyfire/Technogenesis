package ru.wertyfiregames.technogenesis;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import ru.wertyfiregames.technogenesis.block.entity.renderer.PressBlockEntityRenderer;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;
import ru.wertyfiregames.technogenesis.init.TechMenuTypes;
import ru.wertyfiregames.technogenesis.inventory.gui.PressScreen;

@Mod(value = Technogenesis.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Technogenesis.MODID, value = Dist.CLIENT)
public class TechnogenesisClient {
    public TechnogenesisClient(ModContainer container) {}

    @SubscribeEvent
    public static void registerBlockEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TechBlockEntities.BURNER_PRESS.get(), PressBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(TechMenuTypes.BURNER_PRESS.get(), PressScreen::new);
    }
}