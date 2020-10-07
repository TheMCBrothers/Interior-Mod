package tk.themcbros.interiormod.client.models.block.furniture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class FurnitureModel implements IDynamicBakedModel {
    private static final ItemOverrideList ITEM_OVERRIDE = new FurnitureItemOverride();

    private final ModelBakery modelBakery;
    private final Function<RenderMaterial, TextureAtlasSprite> spriteGetter;
    private final BlockModel model;
    private final IBakedModel bakedModel;
    private final IModelTransform modelTransform;

    private final Map<String, IBakedModel> cache = Maps.newHashMap();

    public FurnitureModel(ModelBakery modelBakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, BlockModel model, IModelTransform modelTransform) {
        this.modelBakery = modelBakery;
        this.spriteGetter = spriteGetter;
        this.model = model;
        this.bakedModel = model.bakeModel(modelBakery, model, spriteGetter, modelTransform, InteriorMod.getId("furniture"), true);
        this.modelTransform = modelTransform;
    }

    public IBakedModel getCustomModel(@Nullable FurnitureMaterial primary, @Nullable FurnitureMaterial secondary) {
        // make sure values not null
        if (primary == null) primary = FurnitureMaterials.OAK_PLANKS.get();
        if (secondary == null) secondary = FurnitureMaterials.OAK_PLANKS.get();

        IBakedModel customModel;

        String key = primary + "/" + secondary;
        IBakedModel possibleModel = this.cache.get(key);

        if (possibleModel != null) {
            customModel = possibleModel;
        } else {
            List<BlockPart> elements = Lists.newArrayList();
            for (BlockPart part : this.model.getElements()) {
                elements.add(new BlockPart(part.positionFrom, part.positionTo, Maps.newHashMap(part.mapFaces),
                        part.partRotation, part.shade));
            }

            BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                    Maps.newHashMap(this.model.textures), this.isAmbientOcclusion(), BlockModel.GuiLight.FRONT,
                    this.model.getAllTransforms(), Lists.newArrayList(this.model.getOverrides()));
            newModel.name = this.model.name;
            newModel.parent = this.model.parent;

            RenderMaterial primaryMaterial = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, primary.getTextureLocation());
            RenderMaterial secondaryMaterial = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, secondary.getTextureLocation());

            newModel.textures.put("secondary", Either.left(secondaryMaterial));
            newModel.textures.put("primary", Either.left(primaryMaterial));
            newModel.textures.put("particle", Either.left(primaryMaterial));

            customModel = newModel.bakeModel(this.modelBakery, this.spriteGetter,
                    this.modelTransform, InteriorMod.getId("furniture_overriding"));
            this.cache.put(key, customModel);
        }

        assert customModel != null;
        return customModel;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return this.getCustomModel(FurnitureMaterials.OAK_PLANKS.get(), FurnitureMaterials.OAK_PLANKS.get()).getQuads(state, side, rand);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
                                    @Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureTileEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureTileEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getQuads(state, side, rand);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureTileEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureTileEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getParticleTexture();
    }

    @Override
    public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos,
                                   @Nonnull BlockState state, @Nonnull IModelData tileData) {
        FurnitureMaterial primary = FurnitureMaterials.OAK_PLANKS.get();
        FurnitureMaterial secondary = FurnitureMaterials.OAK_PLANKS.get();

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof FurnitureTileEntity) {
            primary = ((FurnitureTileEntity) tile).getPrimaryMaterial();
            secondary = ((FurnitureTileEntity) tile).getSecondaryMaterial();
        }

        tileData.setData(FurnitureTileEntity.PRIMARY_MATERIAL, primary);
        tileData.setData(FurnitureTileEntity.SECONDARY_MATERIAL, secondary);
        return tileData;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.bakedModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.bakedModel.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return this.bakedModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.bakedModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ITEM_OVERRIDE;
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return this.bakedModel.handlePerspective(cameraTransformType, mat);
    }

    @Override
    public boolean isSideLit() {
        return this.isGui3d();
    }

    private static class Geometry implements IModelGeometry<Geometry> {

        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
            BlockModel model = (BlockModel) owner.getOwnerModel();
            assert model != null;
            assert model.parent != null;
            return new FurnitureModel(bakery, spriteGetter, model.parent, modelTransform);
        }

        @Override
        public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return Collections.emptyList();
        }
    }

    public static class Loader implements IModelLoader<Geometry> {
        public static final Loader INSTANCE = new Loader();

        private Loader() {}

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }

        @Override
        public Geometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new Geometry();
        }
    }
}
