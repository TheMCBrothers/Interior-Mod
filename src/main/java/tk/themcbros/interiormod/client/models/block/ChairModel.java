package tk.themcbros.interiormod.client.models.block;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.model.TRSRTransformation;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;

@SuppressWarnings("deprecation")
public class ChairModel implements IBakedModel {
	
	private static final ItemOverrideList ITEM_OVERRIDE = new ChairItemOverride();
	
	private ModelLoader modelLoader;
	private BlockModel model;
	private IBakedModel bakedModel;
	
	private final VertexFormat format;
	private final Map<String, IBakedModel> cache = Maps.newHashMap();

	public ChairModel(ModelLoader modelLoader, BlockModel model, IBakedModel bakedModel, VertexFormat format) {
		this.modelLoader = modelLoader;
		this.model = model;
		this.bakedModel = bakedModel;
		this.format = format;
	}
	
	public IBakedModel getCustomModel(@Nonnull ResourceLocation texture, @Nonnull ResourceLocation seatTexture, @Nonnull Direction facing) {
		if(texture == null) texture = new ResourceLocation("block/oak_planks");
		if(seatTexture == null) seatTexture = new ResourceLocation("block/oak_log");
		if(facing == null) facing = Direction.NORTH;
		
		return this.getCustomModel(texture.toString(), seatTexture.toString(), facing);
	}
	
	public IBakedModel getCustomModel(@Nonnull String texture, @Nonnull String seatTexture, @Nonnull Direction facing) {
		IBakedModel customModel = this.bakedModel;

		String key = texture + "," + seatTexture + "," + facing.toString();

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

			newModel.textures.put("seat", seatTexture);
			newModel.textures.put("texture", texture);
			newModel.textures.put("particle", texture);

			customModel = newModel.bake(this.modelLoader, ModelLoader.defaultTextureGetter(),
					TRSRTransformation.getRotation(facing), this.format);
			this.cache.put(key, customModel);
		}

		return customModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return this.getCustomModel("block/oak_planks", "block/oak_log", Direction.NORTH).getQuads(state, side, rand);
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
			@Nonnull IModelData data) {
		ResourceLocation casing = data.getData(ChairTileEntity.TEXTURE);
		ResourceLocation bedding = data.getData(ChairTileEntity.SEAT_TEXTURE);
		Direction facing = data.getData(ChairTileEntity.FACING);
		return this.getCustomModel(casing, bedding, facing).getQuads(state, side, rand);
	}

	@Override
	public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
		ResourceLocation casing = data.getData(ChairTileEntity.TEXTURE);
		ResourceLocation bedding = data.getData(ChairTileEntity.SEAT_TEXTURE);
		Direction facing = data.getData(ChairTileEntity.FACING);
		return this.getCustomModel(casing, bedding, facing).getParticleTexture();
	}
	
	@Override
	public IModelData getModelData(@Nonnull IEnviromentBlockReader world, @Nonnull BlockPos pos,
			@Nonnull BlockState state, @Nonnull IModelData tileData) {
		ResourceLocation texture = new ResourceLocation("block/oak_planks");
		ResourceLocation seatTexture = new ResourceLocation("block/oak_log");
		Direction facing = Direction.NORTH;

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ChairTileEntity) {
			texture = ((ChairTileEntity) tile).getTexture();
			seatTexture = ((ChairTileEntity) tile).getSeatTexture();
		}

		if (state.has(BlockStateProperties.HORIZONTAL_FACING)) {
			facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
		}

		tileData.setData(ChairTileEntity.TEXTURE, texture);
		tileData.setData(ChairTileEntity.SEAT_TEXTURE, seatTexture);
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
	public ItemCameraTransforms getItemCameraTransforms() {
		return this.bakedModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ITEM_OVERRIDE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(
			ItemCameraTransforms.TransformType cameraTransformType) {
		return Pair.of(this, this.bakedModel.handlePerspective(cameraTransformType).getRight());
	}

}
