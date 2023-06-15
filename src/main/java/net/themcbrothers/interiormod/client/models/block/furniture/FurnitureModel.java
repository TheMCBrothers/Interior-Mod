package net.themcbrothers.interiormod.client.models.block.furniture;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.blockentity.FurnitureBlockEntity;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author TheMCBrothers
 */
public class FurnitureModel implements IDynamicBakedModel {
    private static final ItemOverrides ITEM_OVERRIDE = new FurnitureItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    private final BlockModel model;
    private final BakedModel bakedModel;
    private final ModelState modelState;

    private final Map<Pair<FurnitureMaterial, FurnitureMaterial>, BakedModel> cache = Maps.newConcurrentMap();

    public FurnitureModel(BlockModel model, BakedModel bakedModel, ModelState modelState) {
        this.model = model;
        this.bakedModel = bakedModel;
        this.modelState = modelState;
    }

    public BakedModel getModelVariant(@NotNull ModelData data) {
        return this.getModelVariant(data.get(FurnitureBlockEntity.PRIMARY_MATERIAL), data.get(FurnitureBlockEntity.SECONDARY_MATERIAL));
    }

    public BakedModel getModelVariant(@Nullable FurnitureMaterial primary, @Nullable FurnitureMaterial secondary) {
        Pair<FurnitureMaterial, FurnitureMaterial> key = ImmutablePair.of(primary, secondary);
        return this.cache.computeIfAbsent(key, k -> bakeModelVariant(k.getLeft(), k.getRight()));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        return this.getModelVariant(extraData).getQuads(state, side, rand, ModelData.EMPTY, renderType);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return this.getModelVariant(data).getParticleIcon(data);
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        FurnitureMaterial primary = FurnitureMaterials.OAK_PLANKS.get();
        FurnitureMaterial secondary = FurnitureMaterials.OAK_PLANKS.get();

        if (level.getBlockEntity(pos) instanceof FurnitureBlockEntity furniture) {
            primary = furniture.getPrimaryMaterial();
            secondary = furniture.getSecondaryMaterial();
        }

        return modelData.derive().with(FurnitureBlockEntity.PRIMARY_MATERIAL, primary)
                .with(FurnitureBlockEntity.SECONDARY_MATERIAL, secondary).build();
    }

    public BakedModel bakeModelVariant(@Nullable FurnitureMaterial primaryMaterial, @Nullable FurnitureMaterial secondaryMaterial) {
        //noinspection deprecation
        List<BlockElement> parts = this.model.getElements();
        List<BlockElement> elements = new ArrayList<>(parts.size()); // We have to duplicate this, so we can edit it below.
        for (BlockElement part : parts) {
            elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }

        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                Maps.newHashMap(this.model.textureMap), this.model.hasAmbientOcclusion(), this.model.getGuiLight(),
                this.model.getTransforms(), new ArrayList<>(this.model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;


        Either<Material, String> primary = findTexture(primaryMaterial);
        newModel.textureMap.put("particle", primary);
        newModel.textureMap.put("primary", primary);
        newModel.textureMap.put("secondary", findTexture(secondaryMaterial));

        return Objects.requireNonNull(new ModelBaker() {
            @Override
            public BakedModel bake(@Nullable ResourceLocation location, @Nullable ModelState state, @Nullable Function<Material, TextureAtlasSprite> sprites) {
                return newModel.bake(this, newModel, Material::sprite,
                        modelState,
                        createResourceVariant(primaryMaterial, secondaryMaterial),
                        true
                );
            }

            @Override
            public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
                return Material::sprite;
            }

            @Override
            public UnbakedModel getModel(ResourceLocation location) {
                return newModel;
            }

            @Override
            @Nullable
            public BakedModel bake(ResourceLocation location, ModelState modelState1) {
                return this.bake(location, modelState1, getModelTextureGetter());
            }

        }.bake(null, null, null));
    }

    private ResourceLocation createResourceVariant(@Nullable FurnitureMaterial primaryMaterial, @Nullable FurnitureMaterial secondaryMaterial) {
        String primaryKey = secondaryMaterial != null
                ? Objects.requireNonNull(InteriorAPI.furnitureRegistry().getKey(secondaryMaterial)).toString().replace(':', '.')
                : "interiormod.furniture_material.missing";
        String secondaryKey = secondaryMaterial != null
                ? Objects.requireNonNull(InteriorAPI.furnitureRegistry().getKey(primaryMaterial)).toString().replace(':', '.')
                : "interiormod.furniture_material.missing";
        return new ModelResourceLocation(InteriorMod.getId("block/furniture"), "#primary=" + primaryKey + ",secondary=" + secondaryKey);
    }

    private Either<Material, String> findTexture(@Nullable FurnitureMaterial material) {
        ResourceLocation resource = material == null ? MISSING_TEXTURE : material.getTextureLocation();
        return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resource));
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
        return this.bakedModel.getParticleIcon(ModelData.EMPTY);
    }

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERRIDE;
    }

    private static class Geometry implements IUnbakedGeometry<Geometry> {
        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            BlockModel model = ((BlockGeometryBakingContext) context).owner.parent;
            assert model != null;
            return new FurnitureModel(model, model.bake(baker, model, spriteGetter, modelState, InteriorMod.getId("furniture"), context.isGui3d()), modelState);
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
