package net.themcbrothers.interiormod.client.models.block.furniture;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.blockentity.FurnitureBlockEntity;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author TheMCBrothers
 */
public class FurnitureModel implements IDynamicBakedModel {
    private static final ItemOverrides ITEM_OVERRIDE = new FurnitureItemOverride();

    private final ModelBaker modelBaker;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final BlockModel model;
    private final BakedModel bakedModel;
    private final ModelState modelState;

    private final Map<String, BakedModel> cache = Maps.newHashMap();

    public FurnitureModel(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, BlockModel model, ModelState modelState) {
        this.modelBaker = modelBaker;
        this.spriteGetter = spriteGetter;
        this.model = model;
        this.bakedModel = model.bake(modelBaker, model, spriteGetter, modelState, InteriorMod.getId("furniture"), true);
        this.modelState = modelState;
    }

    public BakedModel getCustomModel(@Nullable FurnitureMaterial primary, @Nullable FurnitureMaterial secondary) {
        // make sure values not null
        if (primary == null) primary = FurnitureMaterials.OAK_PLANKS.get();
        if (secondary == null) secondary = FurnitureMaterials.OAK_PLANKS.get();

        BakedModel customModel;

        String key = primary + "/" + secondary;
        BakedModel possibleModel = this.cache.get(key);

        if (possibleModel != null) {
            customModel = possibleModel;
        } else {
            Material primaryMaterial = new Material(TextureAtlas.LOCATION_BLOCKS, primary.getTextureLocation());
            Material secondaryMaterial = new Material(TextureAtlas.LOCATION_BLOCKS, secondary.getTextureLocation());

            this.model.textureMap.put("secondary", Either.left(secondaryMaterial));
            this.model.textureMap.put("primary", Either.left(primaryMaterial));
            this.model.textureMap.put("particle", Either.left(primaryMaterial));

            customModel = this.model.bake(this.modelBaker, this.model, this.spriteGetter,
                    this.modelState, InteriorMod.getId("furniture_overriding"), this.isGui3d());
            this.cache.put(key, customModel);
        }

        return customModel;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand) {
        return this.getCustomModel(FurnitureMaterials.OAK_PLANKS.get(), FurnitureMaterials.OAK_PLANKS.get()).getQuads(state, side, rand);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, @org.jetbrains.annotations.Nullable RenderType renderType) {
        FurnitureMaterial primary = extraData.get(FurnitureBlockEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = extraData.get(FurnitureBlockEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getQuads(state, side, rand);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull ModelData data) {
        FurnitureMaterial primary = data.get(FurnitureBlockEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.get(FurnitureBlockEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getParticleIcon();
    }

    @Nonnull
    @Override
    public ModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull ModelData tileData) {
        FurnitureMaterial primary = FurnitureMaterials.OAK_PLANKS.get();
        FurnitureMaterial secondary = FurnitureMaterials.OAK_PLANKS.get();

        if (world.getBlockEntity(pos) instanceof FurnitureBlockEntity furniture) {
            primary = furniture.getPrimaryMaterial();
            secondary = furniture.getSecondaryMaterial();
        }

        return tileData.derive().with(FurnitureBlockEntity.PRIMARY_MATERIAL, primary)
                .with(FurnitureBlockEntity.SECONDARY_MATERIAL, secondary).build();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.bakedModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.bakedModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.bakedModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.bakedModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.bakedModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERRIDE;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        return this.bakedModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    private static class Geometry implements IUnbakedGeometry<Geometry> {
        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            BlockModel model = ((BlockGeometryBakingContext) context).owner.parent;
            assert model != null;
            return new FurnitureModel(baker, spriteGetter, model, modelState);
        }
    }

    public static class Loader implements IGeometryLoader<Geometry> {
        public static final Loader INSTANCE = new Loader();

        private Loader() {
        }

        @Override
        public Geometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new Geometry();
        }
    }
}
