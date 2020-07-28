package tk.themcbros.interiormod.client.models.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.model.BlockModel.GuiLight;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import org.apache.commons.lang3.tuple.Triple;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.client.ClientUtils;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author TheMCBrothers
 */
public class ChairModel implements IBakedModel {

    private static final ItemOverrideList ITEM_OVERRIDE = new ChairItemOverride();

    private final ModelLoader modelLoader;
    private final BlockModel model;
    private final IBakedModel bakedModel;

    private final Map<Triple<FurnitureMaterial, FurnitureMaterial, Direction>, IBakedModel> cache = Maps.newHashMap();

    public ChairModel(ModelLoader modelLoader, BlockModel model, IBakedModel bakedModel) {
        this.modelLoader = modelLoader;
        this.model = model;
        this.bakedModel = bakedModel;
    }

    public IBakedModel getCustomModel(@Nullable FurnitureMaterial primary, @Nullable FurnitureMaterial secondary, @Nullable Direction facing) {

        // make sure values not null
        if (primary == null) primary = FurnitureMaterials.OAK_PLANKS.get();
        if (secondary == null) secondary = FurnitureMaterials.OAK_PLANKS.get();
        if (facing == null) facing = Direction.NORTH;

        IBakedModel customModel;

        Triple<FurnitureMaterial, FurnitureMaterial, Direction> key = Triple.of(primary, secondary, facing);
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
                    Maps.newHashMap(this.model.textures), this.isAmbientOcclusion(), GuiLight.FRONT,
                    this.model.getAllTransforms(), Lists.newArrayList(this.model.getOverrides()));
            newModel.name = this.model.name;
            newModel.parent = this.model.parent;

            RenderMaterial primaryMaterial = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, primary.getTextureLocation());
            RenderMaterial secondaryMaterial = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, secondary.getTextureLocation());

            newModel.textures.put("seat", Either.left(secondaryMaterial));
            newModel.textures.put("texture", Either.left(primaryMaterial));
            newModel.textures.put("particle", Either.left(primaryMaterial));

            customModel = newModel.bakeModel(this.modelLoader, ModelLoader.defaultTextureGetter(),
                    ClientUtils.getRotation(facing), InteriorMod.getId("chair_overriding"));
            this.cache.put(key, customModel);
        }

        assert customModel != null;
        return customModel;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return this.getCustomModel(FurnitureMaterials.OAK_PLANKS.get(), FurnitureMaterials.OAK_PLANKS.get(), Direction.NORTH).getQuads(state, side, rand);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
                                    @Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureTileEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureTileEntity.SECONDARY_MATERIAL);
        Direction facing = data.getData(ChairTileEntity.FACING);
        return this.getCustomModel(primary, secondary, facing).getQuads(state, side, rand);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        FurnitureMaterial primary = data.getData(FurnitureTileEntity.PRIMARY_MATERIAL);
        FurnitureMaterial secondary = data.getData(FurnitureTileEntity.SECONDARY_MATERIAL);
        Direction facing = data.getData(ChairTileEntity.FACING);
        return this.getCustomModel(primary, secondary, facing).getParticleTexture();
    }

    @Override
    public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos,
                                   @Nonnull BlockState state, @Nonnull IModelData tileData) {
        FurnitureMaterial primary = FurnitureMaterials.OAK_PLANKS.get();
        FurnitureMaterial secondary = FurnitureMaterials.OAK_PLANKS.get();
        Direction facing = Direction.NORTH;

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof FurnitureTileEntity) {
            primary = ((FurnitureTileEntity) tile).getPrimaryMaterial();
            secondary = ((FurnitureTileEntity) tile).getSecondaryMaterial();
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
        }

        tileData.setData(FurnitureTileEntity.PRIMARY_MATERIAL, primary);
        tileData.setData(FurnitureTileEntity.SECONDARY_MATERIAL, secondary);
        tileData.setData(ChairTileEntity.FACING, facing);
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
    public IBakedModel handlePerspective(TransformType cameraTransformType, MatrixStack mat) {
        return this.bakedModel.handlePerspective(cameraTransformType, mat);
    }

    @Override
    public boolean func_230044_c_() {
        return this.isGui3d();
    }

}
