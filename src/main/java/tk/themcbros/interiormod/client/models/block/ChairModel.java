package tk.themcbros.interiormod.client.models.block;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Either;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.client.ClientUtils;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;

@SuppressWarnings("deprecation")
public class ChairModel implements IBakedModel {
	
	private static final ItemOverrideList ITEM_OVERRIDE = new ChairItemOverride();
	
	private ModelLoader modelLoader;
	private BlockModel model;
	private IBakedModel bakedModel;
	
	private final Map<Triple<String, String, Direction>, IBakedModel> cache = Maps.newHashMap();

	public ChairModel(ModelLoader modelLoader, BlockModel model, IBakedModel bakedModel) {
		this.modelLoader = modelLoader;
		this.model = model;
		this.bakedModel = bakedModel;
	}
	
	public IBakedModel getCustomModel(@Nonnull IFurnitureMaterial primary, @Nonnull IFurnitureMaterial secondary, @Nonnull Direction facing) {
		if(primary == null) primary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		if(secondary == null) secondary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		if(facing == null) facing = Direction.NORTH;
		
		String primaryTex = primary.getTexture();
		String secondaryTex = secondary.getTexture();
		
		return this.getCustomModel(primaryTex, secondaryTex, facing);
	}
	
	public IBakedModel getCustomModel(@Nonnull String primary, @Nonnull String secondary, @Nonnull Direction facing) {
		IBakedModel customModel = this.bakedModel;

		Triple<String, String, Direction> key = Triple.of(primary, secondary, facing);

		IBakedModel possibleModel = this.cache.get(key);

		if (possibleModel != null) {
			customModel = possibleModel;
		} else if (this.model != null) {
			List<BlockPart> elements = Lists.newArrayList();
			for (BlockPart part : this.model.getElements()) {
				elements.add(new BlockPart(part.positionFrom, part.positionTo, Maps.newHashMap(part.mapFaces),
						part.partRotation, part.shade));
			}

			BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
					Maps.newHashMap(this.model.textures), this.model.isAmbientOcclusion(), this.model.isGui3d(),
					this.model.getAllTransforms(), Lists.newArrayList(this.model.getOverrides()));
			newModel.name = this.model.name;
			newModel.parent = this.model.parent;
			
			Material primaryMaterial = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(primary));
			Material secondaryMaterial = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(secondary));

			newModel.textures.put("seat", Either.left(secondaryMaterial));
			newModel.textures.put("texture", Either.left(primaryMaterial));
			newModel.textures.put("particle", Either.left(primaryMaterial));

			customModel = newModel.func_225613_a_(this.modelLoader, ModelLoader.defaultTextureGetter(),
					ClientUtils.getRotation(facing), InteriorMod.getId("chair_overriding"));
			this.cache.put(key, customModel);
		}

		return customModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return this.getCustomModel(FurnitureRegistry.MATERIALS.getKeys().get(0), FurnitureRegistry.MATERIALS.getKeys().get(0), Direction.NORTH).getQuads(state, side, rand);
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
			@Nonnull IModelData data) {
		IFurnitureMaterial primary = data.getData(ChairTileEntity.MATERIAL);
		IFurnitureMaterial secondary = data.getData(ChairTileEntity.SEAT_MATERIAL);
		Direction facing = data.getData(ChairTileEntity.FACING);
		return this.getCustomModel(primary, secondary, facing).getQuads(state, side, rand);
	}

	@Override
	public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
		IFurnitureMaterial primary = data.getData(ChairTileEntity.MATERIAL);
		IFurnitureMaterial secondary = data.getData(ChairTileEntity.SEAT_MATERIAL);
		Direction facing = data.getData(ChairTileEntity.FACING);
		return this.getCustomModel(primary, secondary, facing).getParticleTexture();
	}
	
	@Override
	public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos,
			@Nonnull BlockState state, @Nonnull IModelData tileData) {
		IFurnitureMaterial primary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		IFurnitureMaterial secondary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		Direction facing = Direction.NORTH;

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ChairTileEntity) {
			primary = ((ChairTileEntity) tile).getMaterial();
			secondary = ((ChairTileEntity) tile).getSeatMaterial();
		}

		if (state.has(BlockStateProperties.HORIZONTAL_FACING)) {
			facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
		}

		tileData.setData(ChairTileEntity.MATERIAL, primary);
		tileData.setData(ChairTileEntity.SEAT_MATERIAL, secondary);
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

}
