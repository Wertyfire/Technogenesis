package ru.wertyfiregames.technogenesis.block.entity.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.level.Level;

public class PressBlockEntityRenderState extends BlockEntityRenderState {
    public Level level;

    public final ItemStackRenderState stackRenderState = new ItemStackRenderState();
}