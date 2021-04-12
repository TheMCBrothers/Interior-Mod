package tk.themcbros.interiormod.data;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyNbt;
import tk.themcbros.interiormod.init.InteriorBlocks;

/**
 * @author TheMCBrothers
 */
public class InteriorLootTables extends BaseLootTableProvider {

    public InteriorLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        this.lootTables.put(InteriorBlocks.CHAIR, this.createFurnitureTable("chair", InteriorBlocks.CHAIR));
        this.lootTables.put(InteriorBlocks.TABLE, this.createFurnitureTable("table", InteriorBlocks.TABLE));

        this.lootTables.put(InteriorBlocks.TRASH_CAN, this.createBasicTable("trash_can", InteriorBlocks.TRASH_CAN));
        this.lootTables.put(InteriorBlocks.FRIDGE, LootTable.builder().addLootPool(
                LootPool.builder().name("fridge")
                        .addEntry(ItemLootEntry.builder(InteriorBlocks.FRIDGE)
                                .acceptCondition(BlockStateProperty.builder(InteriorBlocks.FRIDGE)
                                        .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                                .withProp(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))))
                        .acceptCondition(SurvivesExplosion.builder())));
        this.lootTables.put(InteriorBlocks.FURNITURE_WORKBENCH, this.createBasicTable("furniture_workbench", InteriorBlocks.FURNITURE_WORKBENCH));
    }

    private LootTable.Builder createFurnitureTable(String name, Block block) {
        LootPool.Builder builder = LootPool.builder()
                .name(name)
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block)
                        .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                                .addOperation("primaryMaterial", "textures.primary", CopyNbt.Action.REPLACE)
                                .addOperation("secondaryMaterial", "textures.secondary", CopyNbt.Action.REPLACE))
                ).acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

}
