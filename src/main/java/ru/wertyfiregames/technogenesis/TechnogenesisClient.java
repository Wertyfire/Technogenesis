package ru.wertyfiregames.technogenesis;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import ru.wertyfiregames.technogenesis.block.entity.renderer.PressBlockEntityRenderer;
import ru.wertyfiregames.technogenesis.init.TechBlockEntities;

@Mod(value = Technogenesis.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Technogenesis.MODID, value = Dist.CLIENT)
public class TechnogenesisClient {
    public TechnogenesisClient(ModContainer container) {}

    @SubscribeEvent
    public static void registerBlockEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TechBlockEntities.PRESS.get(), PressBlockEntityRenderer::new);
    }
}