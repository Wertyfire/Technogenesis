package ru.wertyfiregames.technogenesis.inventory.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.api.system.pressure.Pressure;
import ru.wertyfiregames.technogenesis.inventory.menu.PressMenu;

public class PressScreen extends AbstractContainerScreen<PressMenu> {
    private static final Identifier TEXTURE = Technogenesis.createIdentifier("textures/gui/press/burner_press.png");
    private static final Identifier BURN_PROGRESS = Technogenesis.createIdentifier("textures/gui/press/burner/burn_progress.png");
    private static final Identifier PRESS_PROGRESS = Technogenesis.createIdentifier("textures/gui/press/burner/press_progress.png");

    public PressScreen(PressMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 182);
        inventoryLabelY = getImageHeight() - 90;
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderBurnProgress(graphics, x, y);
        renderPressingProgress(graphics, x, y);
    }

    private void renderBurnProgress(@NonNull GuiGraphicsExtractor graphics, int x, int y) {
        if (menu.isBurning())
            graphics.blit(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS, x + 26, y + 52 + 14 - menu.getScaledBurnProgress(), 0, 14 - menu.getScaledBurnProgress(), 14, menu.getScaledBurnProgress(), 14, 14);
    }

    private void renderPressingProgress(@NonNull GuiGraphicsExtractor graphics, int x, int y) {
        if (menu.isPressing())
            graphics.blit(RenderPipelines.GUI_TEXTURED, PRESS_PROGRESS, x + 77, y + 37, 0, 0, 22, menu.getScaledPressProgress(), 22, 24);
    }

    @Override
    protected void extractTooltip(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);

        if (isHovering(23, 32, 22, 18, mouseX, mouseY))
            graphics.setTooltipForNextFrame(this.font, Pressure.getPressureForDisplay(menu.getPressureLevel()), mouseX, mouseY);
    }
}