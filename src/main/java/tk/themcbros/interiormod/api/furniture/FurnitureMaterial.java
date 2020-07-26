package tk.themcbros.interiormod.api.furniture;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author TheMCBrothers
 */
public class FurnitureMaterial extends ForgeRegistryEntry<FurnitureMaterial> {

    @Nonnull
    private final Supplier<? extends Block> blockSupplier;
    @Nonnull
    private final Supplier<? extends IItemProvider> itemProvider;
    @Nullable
    private final ResourceLocation textureLocationOverride;

    /**
     * @param blockSupplier Block of the material.
     * @param textureLocationOverride Optional texture override. Leave null for particle texture.
     */
    public FurnitureMaterial(@Nonnull Supplier<? extends Block> blockSupplier, @Nullable ResourceLocation textureLocationOverride) {
        this(blockSupplier, blockSupplier, textureLocationOverride);
    }

    /**
     * @param blockSupplier Block of the material.
     * @param itemProvider Item used for crafting.
     * @param textureLocationOverride Optional texture override. Leave null for particle texture.
     */
    public FurnitureMaterial(@Nonnull Supplier<? extends Block> blockSupplier, @Nonnull Supplier<? extends IItemProvider> itemProvider,
                             @Nullable ResourceLocation textureLocationOverride) {
        this.blockSupplier = blockSupplier;
        this.itemProvider = itemProvider;
        this.textureLocationOverride = textureLocationOverride;
    }

    /**
     * @return The block of the material
     */
    @Nonnull
    public Block getBlock() {
        return this.blockSupplier.get();
    }

    /**
     * @return Display Name of the material (Translated block name)
     */
    @Nonnull
    public IFormattableTextComponent getDisplayName() {
        return new TranslationTextComponent(this.blockSupplier.get().getTranslationKey());
    }

    /**
     * @return The texture of the material. Default is particle texture.
     */
    @Nonnull
    public ResourceLocation getTextureLocation() {
        if (this.textureLocationOverride == null) {
            return Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(this.blockSupplier.get().getDefaultState())
                    .getParticleTexture(EmptyModelData.INSTANCE).getName();
        }
        return this.textureLocationOverride;
    }

    /**
     * @return Ingredient for crafting recipe
     */
    public Ingredient getIngredient() {
        return Ingredient.fromItems(this.itemProvider.get());
    }
}
