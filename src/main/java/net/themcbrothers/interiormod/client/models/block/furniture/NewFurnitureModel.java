package net.themcbrothers.interiormod.client.models.block.furniture;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.BlockGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class NewFurnitureModel implements IUnbakedGeometry<NewFurnitureModel> {
    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        Material particleLocation = context.getMaterial("particle");
        TextureAtlasSprite particle = spriteGetter.apply(particleLocation);

        BakedModel bakedModel = null;

        BlockModel parent = ((BlockGeometryBakingContext) context).owner.parent;
        if (parent != null) {
            parent.textureMap.put("primary", Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("blocks/diamond_block"))));
            bakedModel = parent.bake(baker, parent, spriteGetter, modelState, modelLocation, context.isGui3d());
        }

        return new Baked(context.useAmbientOcclusion(), context.isGui3d(), context.useBlockLight(), particle, overrides, context.getTransforms(), bakedModel);
    }

    public static class Baked implements IDynamicBakedModel {
        private final boolean isAmbientOcclusion;
        private final boolean isGui3d;
        private final boolean isSideLit;
        private final TextureAtlasSprite particle;
        private final ItemOverrides overrides;
        private final ItemTransforms transforms;
        @Nullable
        private final BakedModel model;

        public Baked(boolean isAmbientOcclusion, boolean isGui3d, boolean isSideLit, TextureAtlasSprite particle, ItemOverrides overrides, ItemTransforms transforms, @Nullable BakedModel model) {
            this.isAmbientOcclusion = isAmbientOcclusion;
            this.isGui3d = isGui3d;
            this.isSideLit = isSideLit;
            this.particle = particle;
            this.overrides = overrides;
            this.transforms = transforms;
            this.model = model;
        }

        @Override
        public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
            return this.model != null ? this.model.getQuads(state, side, rand, extraData, renderType) : Collections.emptyList();
        }

        @Override
        public boolean useAmbientOcclusion() {
            return this.isAmbientOcclusion;
        }

        @Override
        public boolean isGui3d() {
            return this.isGui3d;
        }

        @Override
        public boolean usesBlockLight() {
            return this.isSideLit;
        }

        @Override
        public boolean isCustomRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return this.particle;
        }

        @Override
        public ItemOverrides getOverrides() {
            return this.overrides;
        }

        @SuppressWarnings("deprecation")
        @Override
        public ItemTransforms getTransforms() {
            return this.transforms;
        }
    }

    public static class Loader implements IGeometryLoader<NewFurnitureModel> {
        public static final Loader INSTANCE = new Loader();

        private Loader() {
        }

        @Override
        public NewFurnitureModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            return new NewFurnitureModel();
        }
    }
}
