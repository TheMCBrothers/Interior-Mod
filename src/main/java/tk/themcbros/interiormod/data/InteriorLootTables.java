package tk.themcbros.interiormod.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import tk.themcbros.interiormod.init.InteriorBlocks;

public class InteriorLootTables extends BaseLootTableProvider {

	public InteriorLootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		this.lootTables.put(InteriorBlocks.CHAIR, this.createFurnitureTable("chair", InteriorBlocks.CHAIR));
		this.lootTables.put(InteriorBlocks.TABLE, this.createFurnitureTable("table", InteriorBlocks.TABLE));
	}
	
	private LootTable.Builder createFurnitureTable(String name, Block block) {
		LootPool.Builder builder = LootPool.builder()
				.name(name)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block)
						.acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
								.addOperation("material", "textures.primary", CopyNbt.Action.REPLACE)
								.addOperation("seatMaterial", "textures.secondary", CopyNbt.Action.REPLACE)
								.addOperation("legMaterial", "textures.secondary", CopyNbt.Action.REPLACE))
						.acceptCondition(SurvivesExplosion.builder())
				);
		return LootTable.builder().addLootPool(builder);
	}
	
}
