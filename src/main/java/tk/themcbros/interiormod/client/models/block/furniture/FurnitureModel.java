package tk.themcbros.interiormod.client.models.block.furniture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.blockentity.FurnitureBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class FurnitureModel implements IDynamicBakedModel {
    private static final ItemOverrides ITEM_OVERRIDE = new FurnitureItemOverride();

    private final ModelBakery modelBakery;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final BlockModel model;
    private final BakedModel bakedModel;
    private final ModelState modelTransform;

    private final Map<String, BakedModel> cache = Maps.newHashMap();

    public FurnitureModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> spriteGetter, BlockModel model, ModelState modelTransform) {
        this.modelBakery = modelBakery;
        this.spriteGetter = spriteGetter;
        this.model = model;
        this.bakedModel = model.bake(modelBakery, model, spriteGetter, modelTransform, InteriorMod.getId("furniture"), true);
        this.modelTransform = modelTransform;
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
            List<BlockElement> elements = Lists.newArrayList();
            for (BlockElement part : this.model.getElements()) {
                elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces),
                        part.rotation, part.shade));
            }

            BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                    Maps.newHashMap(this.model.textureMap), this.useAmbientOcclusion(), BlockModel.GuiLight.FRONT,
                    this.model.getTransforms(), Lists.newArrayList(this.model.getOverrides()));
            newModel.name = this.model.name;
            newModel.parent = this.model.parent;

            Material primaryMaterial = new Material(TextureAtlas.LOCATION_BLOCKS, primary.getTextureLocation());
            Material secondaryMaterial = new Material(TextureAtlas.LOCATION_BLOCKS, secondary.getTextureLocation());

            newModel.textureMap.put("secondary", Either.left(secondaryMaterial));
            newModel.textureMap.put("primary", Either.left(primaryMaterial));
            newModel.textureMap.put("particle", Either.left(primaryMaterial));

            customModel = newModel.bake(this.modelBakery, this.spriteGetter,
                    this.modelTransform, InteriorMod.getId("furniture_overriding"));
            this.cache.put(key, customModel);
        }

        assert customModel != null;
        return customModel;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand) {
        return this.getCustomModel(FurnitureMaterials.OAK_PLANKS.get(), FurnitureMaterials.OAK_PLANKS.get()).getQuads(state, side, rand);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
                                    @Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureBlockEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureBlockEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getQuads(state, side, rand);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureBlockEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureBlockEntity.SECONDARY_MATERIAL);
        return this.getCustomModel(primary, secondary).getParticleIcon();
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        FurnitureMaterial primary = FurnitureMaterials.OAK_PLANKS.get();
        FurnitureMaterial secondary = FurnitureMaterials.OAK_PLANKS.get();

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof FurnitureBlockEntity) {
            primary = ((FurnitureBlockEntity) tile).getPrimaryMaterial();
            secondary = ((FurnitureBlockEntity) tile).getSecondaryMaterial();
        }

        tileData.setData(FurnitureBlockEntity.PRIMARY_MATERIAL, primary);
        tileData.setData(FurnitureBlockEntity.SECONDARY_MATERIAL, secondary);
        return tileData;
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
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        return this.bakedModel.handlePerspective(cameraTransformType, poseStack);
    }

    private static class Geometry implements IModelGeometry<Geometry> {

        @Override
        public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
            BlockModel model = (BlockModel) owner.getOwnerModel();
            assert model != null;
            assert model.parent != null;
            return new FurnitureModel(bakery, spriteGetter, model.parent, modelTransform);
        }

        @Override
        public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return Collections.emptyList();
        }
    }

    public static class Loader implements IModelLoader<Geometry> {
        public static final Loader INSTANCE = new Loader();

        private Loader() {
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
        }

        @Override
        public Geometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new Geometry();
        }
    }
}
