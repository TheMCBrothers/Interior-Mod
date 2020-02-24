package tk.themcbros.interiormod.client.events;

import java.util.Map;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.blocks.TableBlock;
import tk.themcbros.interiormod.client.ClientUtils;
import tk.themcbros.interiormod.client.models.block.ChairModel;
import tk.themcbros.interiormod.client.models.block.TableModel;

@Mod.EventBusSubscriber(modid = InteriorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBake {

	@SubscribeEvent
	@SuppressWarnings({ "deprecation", "unused" })
	public static void onModelBakeEvent(final ModelBakeEvent event) {
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();

		// Chair Model
		try {
			ResourceLocation resourceLocation = FurnitureType.CHAIR.getBlock().getRegistryName();
			ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(),
					"block/" + resourceLocation.getPath());

			BlockModel model = (BlockModel) event.getModelLoader().getUnbakedModel(unbakedModelLoc);
			IBakedModel customModel = new ChairModel(event.getModelLoader(), model,
					model.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							ClientUtils.getRotation(Direction.NORTH), InteriorMod.getId("chair_overriding")));
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
			ResourceLocation tabelRegistryName = FurnitureType.TABLE.getBlock().getRegistryName();
			ResourceLocation normalLoc = new ResourceLocation(tabelRegistryName.getNamespace(),
					"block/" + tabelRegistryName.getPath());
			ResourceLocation endLoc = new ResourceLocation(tabelRegistryName.getNamespace(),
					"block/" + tabelRegistryName.getPath() + "_end");
			ResourceLocation cornerLoc = new ResourceLocation(tabelRegistryName.getNamespace(),
					"block/" + tabelRegistryName.getPath() + "_corner");

			BlockModel model = (BlockModel) event.getModelLoader().getUnbakedModel(normalLoc);
			BlockModel modelEnd = (BlockModel) event.getModelLoader().getUnbakedModel(endLoc);
			BlockModel modelCorner = (BlockModel) event.getModelLoader().getUnbakedModel(cornerLoc);
			IBakedModel customModel = new TableModel(event.getModelLoader(), model,
					model.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							ClientUtils.getRotation(Direction.NORTH), InteriorMod.getId("table_overriding")));
			IBakedModel customEndModel = new TableModel(event.getModelLoader(), modelEnd,
					modelEnd.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							ClientUtils.getRotation(Direction.NORTH), InteriorMod.getId("table_end_overriding")));
			IBakedModel customCornerModel = new TableModel(event.getModelLoader(), modelCorner,
					modelCorner.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
							ClientUtils.getRotation(Direction.NORTH), InteriorMod.getId("table_corner_overriding")));
			// Replace all valid block states
			FurnitureType.TABLE.getBlock().getStateContainer().getValidStates().forEach(state -> {
				boolean north = state.get(TableBlock.NORTH), east = state.get(TableBlock.EAST), south = state.get(TableBlock.SOUTH), west = state.get(TableBlock.WEST);
				modelRegistry.put(BlockModelShapes.getModelLocation(state), customModel);
			});

			// Replace inventory model
			modelRegistry.put(new ModelResourceLocation(tabelRegistryName, "inventory"), customModel);

		} catch (Exception e) {
			InteriorMod.LOGGER.warn("Could not get base model. Reverting to default textures...");
			e.printStackTrace();
		}
	}
}