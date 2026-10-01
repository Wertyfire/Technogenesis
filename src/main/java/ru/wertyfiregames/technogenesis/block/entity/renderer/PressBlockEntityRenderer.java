package ru.wertyfiregames.technogenesis.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.wertyfiregames.technogenesis.block.entity.PressBlockEntity;

public class PressBlockEntityRenderer implements BlockEntityRenderer<PressBlockEntity, PressBlockEntityRenderState> {
    public PressBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public @NonNull PressBlockEntityRenderState createRenderState() {
        return new PressBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull PressBlockEntity blockEntity, @NonNull PressBlockEntityRenderState state, float partialTicks, @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
    }

    @Override
    public void submit(@NonNull PressBlockEntityRenderState state, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.popPose();
    }
}