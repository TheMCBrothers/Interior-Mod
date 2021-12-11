package net.themcbrothers.interiormod.data;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.themcbrothers.interiormod.blocks.LampOnAStickBlock;
import net.themcbrothers.interiormod.init.InteriorBlocks;

public class InteriorBlockLoot extends BlockLoot {
    @Override
    protected void addTables() {
        this.add(InteriorBlocks.CHAIR, InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.TABLE, InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.FRIDGE, BlockLoot::createDoorTable);
        this.add(InteriorBlocks.MODERN_DOOR, BlockLoot::createDoorTable);
        this.dropSelf(InteriorBlocks.LAMP);
        this.dropSelf(InteriorBlocks.TRASH_CAN);
        this.dropSelf(InteriorBlocks.FURNITURE_WORKBENCH);
        this.dropSelf(InteriorBlocks.LAMP);
        this.add(InteriorBlocks.LAMP_ON_A_STICK, block -> LootTable.lootTable().withPool(applyExplosionCondition(block,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.STICK).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(LampOnAStickBlock.PART, LampOnAStickBlock.Part.BOTTOM))))
                        .add(LootItem.lootTableItem(Items.STICK).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(LampOnAStickBlock.PART, LampOnAStickBlock.Part.MIDDLE))))
                        .add(LootItem.lootTableItem(InteriorBlocks.LAMP).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(LampOnAStickBlock.PART, LampOnAStickBlock.Part.TOP)))))));
    }

    private static LootTable.Builder createFurnitureTable(Block block) {
        LootPool.Builder builder = applyExplosionCondition(block, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("primaryMaterial", "BlockEntityTag.primaryMaterial")
                                .copy("secondaryMaterial", "BlockEntityTag.secondaryMaterial"))));
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InteriorBlocks.BLOCKS;
    }
}
