package tk.themcbros.interiormod.client.events;

import java.util.Map;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.client.models.block.ChairModel;
import tk.themcbros.interiormod.client.models.block.TableModel;

@Mod.EventBusSubscriber(modid = InteriorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBake {

	@SubscribeEvent
	public static void onModelBakeEvent(final ModelBakeEvent event) {
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();

		// Chair Model
		try {
			ResourceLocation resourceLocation = FurnitureType.CHAIR.getBlock().getRegistryName();
			ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(),
					"block/" + resourceLocation.getPath());

			BlockModel model = (BlockModel) event.getModelLoader().getUnbakedModel(unbakedModelLoc);
			IBakedModel customModel = new ChairModel(event.getModelLoader(), model,
					model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							TRSRTransformation.getRotation(Direction.NORTH), DefaultVertexFormats.BLOCK),
					DefaultVertexFormats.BLOCK);
			// Replace all valid block states
			FurnitureType.CHAIR.getBlock().getStateContainer().getValidStates().forEach(state -> {
				modelRegistry.put(BlockModelShapes.getModelLocation(state), customModel);
			});

			// Replace inventory model
			modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);

		} catch (Exception e) {
			InteriorMod.LOGGER.warn("Could not get base model. Reverting to default textures...");
			e.printStackTrace();
		}
		
		// Table Model
		try {
			ResourceLocation resourceLocation = FurnitureType.TABLE.getBlock().getRegistryName();
			ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(),
					"block/" + resourceLocation.getPath());

			BlockModel model = (BlockModel) event.getModelLoader().getUnbakedModel(unbakedModelLoc);
			IBakedModel customModel = new TableModel(event.getModelLoader(), model,
					model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							TRSRTransformation.getRotation(Direction.NORTH), DefaultVertexFormats.BLOCK),
					DefaultVertexFormats.BLOCK);
			// Replace all valid block states
			FurnitureType.TABLE.getBlock().getStateContainer().getValidStates().forEach(state -> {
				modelRegistry.put(BlockModelShapes.getModelLocation(state), customModel);
			});

			// Replace inventory model
			modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);

		} catch (Exception e) {
			InteriorMod.LOGGER.warn("Could not get base model. Reverting to default textures...");
			e.printStackTrace();
		}
	}
}