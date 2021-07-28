package tk.themcbros.interiormod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import tk.themcbros.interiormod.entity.SeatEntity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author TheMCBrothers
 */
@ParametersAreNonnullByDefault
public class SeatRenderer extends EntityRenderer<SeatEntity> {

    public SeatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull SeatEntity entity) {
        return new ResourceLocation("");
    }

    @Override
    protected void renderNameTag(SeatEntity seatEntity, Component component, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
    }

    @Override
    public void render(SeatEntity seatEntity, float v, float v1, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
    }
}
