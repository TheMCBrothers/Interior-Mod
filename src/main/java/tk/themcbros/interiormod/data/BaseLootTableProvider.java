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
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.DynamicLootEntry;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.SetContents;
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
				.addEntry(ItemLootEntry.builder(block)
						.acceptCondition(SurvivesExplosion.builder()));
		return LootTable.builder().addLootPool(builder);
	}
	
	protected LootTable.Builder createMachineTable(String name, Block block) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block)
						.acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY))
						.acceptFunction(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY)
								.func_216055_a("Items", "BlockEntityTag.Items", CopyNbt.Action.REPLACE)
								.func_216055_a("Upgrades", "BlockEntityTag.Upgrades", CopyNbt.Action.REPLACE)
								.func_216055_a("Energy", "BlockEntityTag.Energy", CopyNbt.Action.REPLACE))
						.acceptFunction(SetContents.func_215920_b()
								.func_216075_a(DynamicLootEntry.func_216162_a(new ResourceLocation("minecraft", "contents"))))
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
