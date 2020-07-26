package tk.themcbros.interiormod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import tk.themcbros.interiormod.entity.SeatEntity;

/**
 * @author TheMCBrothers
 */
public class SeatRenderer extends EntityRenderer<SeatEntity> {

    public SeatRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(SeatEntity entity) {
        return null;
    }

    @Override
    protected void renderName(SeatEntity entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {}

    @Override
    public void render(SeatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {}
}
