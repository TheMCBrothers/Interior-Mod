package tk.themcbros.interiormod.client.models.block;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Either;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockModel.GuiLight;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import tk.themcbros.interiormod.tileentity.TableTileEntity;

@SuppressWarnings("deprecation")
public class TableModel implements IBakedModel {
	
	private static final ItemOverrideList ITEM_OVERRIDE = new TableItemOverride();
	
	private ModelLoader modelLoader;
	private BlockModel model;
	private IBakedModel bakedModel;

	private final Map<String, IBakedModel> cache = Maps.newHashMap();

	public TableModel(ModelLoader modelLoader, BlockModel model, IBakedModel bakedModel) {
		this.modelLoader = modelLoader;
		this.model = model;
		this.bakedModel = bakedModel;
	}
	
	public IBakedModel getCustomModel(@Nonnull IFurnitureMaterial primary, @Nonnull IFurnitureMaterial secondary) {
		if(primary == null) primary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		if(secondary == null) secondary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		
		String primaryTex = primary.getTexture();
		String secondaryTex = secondary.getTexture();
		
		return this.getCustomModel(primaryTex, secondaryTex);
	}
	
	public IBakedModel getCustomModel(@Nonnull String primary, @Nonnull String secondary) {
		IBakedModel customModel = this.bakedModel;

		String key = String.format("%s;%s", primary, secondary);

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
					Maps.newHashMap(this.model.textures), this.model.isAmbientOcclusion(), GuiLight.SIDE,
					this.model.getAllTransforms(), Lists.newArrayList(this.model.getOverrides()));
			newModel.name = this.model.name;
			newModel.parent = this.model.parent;
			
			Material primaryMaterial = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(primary));
			Material secondaryMaterial = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(secondary));

			newModel.textures.put("secondary", Either.left(secondaryMaterial));
			newModel.textures.put("primary", Either.left(primaryMaterial));
			newModel.textures.put("particle", Either.left(primaryMaterial));

			customModel = newModel.bakeModel(this.modelLoader, ModelLoader.defaultTextureGetter(),
					ClientUtils.getRotation(Direction.NORTH), InteriorMod.getId("tabel_overriding"));
			this.cache.put(key, customModel);
		}

		return customModel;
	}
	
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return this.getCustomModel(FurnitureRegistry.MATERIALS.getKeys().get(0), FurnitureRegistry.MATERIALS.getKeys().get(0)).getQuads(state, side, rand);
	}
	
//	private void putVertex(BakedQuadBuilder builder, Vec3d normal, double x, double y, double z, float u,
//			float v, TextureAtlasSprite sprite, float color) {
//		for (int e = 0; e < format.getElements().size(); e++) {
//			switch (format.getElements().get(e).getUsage()) {
//
//			case POSITION:
//				builder.put(e, (float) x, (float) y, (float) z, 1.0f);
//				break;
//			case COLOR:
//				builder.put(e, color, color, color, 1.0f);
//				break;
//			case UV:
//				if (format.getElements().get(e).getIndex() == 0) {
//					u = sprite.getInterpolatedU(u);
//					v = sprite.getInterpolatedV(v);
//					builder.put(e, u, v, 0f, 1f);
//					break;
//				}
//			case NORMAL:
//				builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
//				break;
//			default:
//				builder.put(e);
//				break;
//			}
//		}
//	}
//
//	private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, float hilight) {
//		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
//
//		BakedQuadBuilder builder = new BakedQuadBuilder();
//		builder.setTexture(sprite);
//		putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, hilight);
//		putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16, sprite, hilight);
//		putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16, sprite, hilight);
//		putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0, sprite, hilight);
//		return builder.build();
//	}
//
//	private static Vec3d v(double x, double y, double z) {
//		return new Vec3d(x, y, z);
//	}
//	
//	private TextureAtlasSprite getTexture(IFurnitureMaterial material) {
//		return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation(material.getTexture()));
//	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand,
			@Nonnull IModelData data) {
		IFurnitureMaterial primary = data.getData(TableTileEntity.MATERIAL);
		IFurnitureMaterial secondary = data.getData(TableTileEntity.LEG_MATERIAL);
//		
		return this.getCustomModel(primary, secondary).getQuads(state, side, rand);
		
//		List<BakedQuad> quads = new ArrayList<>();
//		quads.add(createQuad(v(0, 1, 0), v(0, 1, 1), v(1, 1, 1), v(1, 1, 0), getTexture(primary), 1.0f));
//		quads.add(createQuad(v(0, .875, 0), v(0, .875, 1), v(1, .875, 1), v(1, .875, 0), getTexture(secondary), 1.0f));
//		return quads;
	}

	@Override
	public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
		IFurnitureMaterial primary = data.getData(TableTileEntity.MATERIAL);
		IFurnitureMaterial secondary = data.getData(TableTileEntity.LEG_MATERIAL);
		return this.getCustomModel(primary, secondary).getParticleTexture();
	}
	
	@Override
	public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos,
			@Nonnull BlockState state, @Nonnull IModelData tileData) {
		IFurnitureMaterial primary = FurnitureRegistry.MATERIALS.getKeys().get(0);
		IFurnitureMaterial secondary = FurnitureRegistry.MATERIALS.getKeys().get(0);

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TableTileEntity) {
			primary = ((TableTileEntity) tile).getMaterial();
			secondary = ((TableTileEntity) tile).getLegMaterial();
		}

		tileData.setData(TableTileEntity.MATERIAL, primary);
		tileData.setData(TableTileEntity.LEG_MATERIAL, secondary);
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
	public IBakedModel handlePerspective(TransformType cameraTransformType, MatrixStack mat) {
		return this.bakedModel.handlePerspective(cameraTransformType, mat);
	}

	@Override
	public boolean func_230044_c_() {
		return this.isGui3d();
	}

}
