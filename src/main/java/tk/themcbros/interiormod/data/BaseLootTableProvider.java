package tk.themcbros.interiormod.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.DynamicLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import tk.themcbros.interiormod.InteriorMod;

public abstract class BaseLootTableProvider extends LootTableProvider {
 
	protected final Map<Block, LootTable.Builder> lootTables = Maps.newHashMap();
	private final DataGenerator generator;
	
	public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
		this.generator = dataGeneratorIn;
	}
	
	protected abstract void addTables();
	
	protected LootTable.Builder createBasicTable(String name, Block block) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block))
				.acceptCondition(SurvivesExplosion.builder());
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createMachineTable(String name, Block block) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block)
						.acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
						.acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
								.addOperation("Items", "BlockEntityTag.Items", CopyNbt.Action.REPLACE)
								.addOperation("Upgrades", "BlockEntityTag.Upgrades", CopyNbt.Action.REPLACE)
								.addOperation("Energy", "BlockEntityTag.Energy", CopyNbt.Action.REPLACE))
						.acceptFunction(SetContents.builderIn()
								.addLootEntry(DynamicLootEntry.func_216162_a(new ResourceLocation("minecraft", "contents"))))
						.acceptCondition(SurvivesExplosion.builder())
				);
		return LootTable.builder().addLootPool(builder);
	}
	
	@Override
	public void act(DirectoryCache cache) {
		addTables();
		
		Map<ResourceLocation, LootTable> tables = Maps.newHashMap();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}
		
		writeTables(cache, tables);
	}
	
	private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
		Path outputFolder = this.generator.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = getPath(outputFolder, key);
			try {
				IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
			} catch (IOException ex) {
				InteriorMod.LOGGER.error("Couldn't write loot table {}", path, ex);
			}
		});
	}
	
	private static Path getPath(Path pathIn, ResourceLocation id) {
		return pathIn.resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
	}
	
	@Override
	public String getName() {
		return "InteriorMod LootTables";
	}

}
