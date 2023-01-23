package net.themcbrothers.interiormod.api.furniture;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author TheMCBrothers
 */
public class FurnitureMaterial {

    @Nonnull
    private final Supplier<? extends Block> blockSupplier;
    @Nonnull
    private final Supplier<? extends ItemLike> itemProvider;
    @Nullable
    private final ResourceLocation textureLocationOverride;
    @Nonnull
    private Function<FurnitureType, Boolean> typeValidator = type -> true;

    /**
     * @param blockSupplier           Block of the material.
     * @param textureLocationOverride Optional texture override. Leave null for particle texture.
     */
    public FurnitureMaterial(@Nonnull Supplier<? extends Block> blockSupplier, @Nullable ResourceLocation textureLocationOverride) {
        this(blockSupplier, blockSupplier, textureLocationOverride);
    }

    /**
     * @param blockSupplier           Block of the material.
     * @param itemProvider            Item used for crafting.
     * @param textureLocationOverride Optional texture override. Leave null for particle texture.
     */
    public FurnitureMaterial(@Nonnull Supplier<? extends Block> blockSupplier, @Nonnull Supplier<? extends ItemLike> itemProvider,
                             @Nullable ResourceLocation textureLocationOverride) {
        this.blockSupplier = blockSupplier;
        this.itemProvider = itemProvider;
        this.textureLocationOverride = textureLocationOverride;
    }

    public FurnitureMaterial setTypeValidator(@Nonnull Function<FurnitureType, Boolean> typeValidator) {
        this.typeValidator = typeValidator;
        return this;
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
    public MutableComponent getDisplayName() {
        return Component.translatable(this.blockSupplier.get().getDescriptionId());
    }

    /**
     * @return The texture of the material. Default is particle texture.
     */
    @Nonnull
    public ResourceLocation getTextureLocation() {
        return Objects.requireNonNullElseGet(this.textureLocationOverride,
                () -> Minecraft.getInstance().getBlockRenderer().getBlockModel(this.blockSupplier.get().defaultBlockState())
                        .getParticleIcon(ModelData.EMPTY).atlasLocation());
    }

    /**
     * @return Ingredient for crafting recipe
     */
    public Ingredient getIngredient() {
        return Ingredient.of(this.itemProvider.get());
    }

    public boolean isValidForType(FurnitureType furnitureType) {
        return this.typeValidator.apply(furnitureType);
    }
}
