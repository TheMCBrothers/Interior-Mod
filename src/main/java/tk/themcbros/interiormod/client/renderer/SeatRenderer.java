package tk.themcbros.interiormod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import tk.themcbros.interiormod.entity.SeatEntity;

import javax.annotation.Nonnull;

/**
 * @author TheMCBrothers
 */
public class SeatRenderer extends EntityRenderer<SeatEntity> {

    public SeatRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull SeatEntity entity) {
        return new ResourceLocation("");
    }

    @Override
    protected void renderName(@Nonnull SeatEntity entityIn, @Nonnull ITextComponent displayNameIn, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.renderName(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public void render(@Nonnull SeatEntity entityIn, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn) {}
}
