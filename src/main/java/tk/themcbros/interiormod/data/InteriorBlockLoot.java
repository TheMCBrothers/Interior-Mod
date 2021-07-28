package tk.themcbros.interiormod.data;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import tk.themcbros.interiormod.init.InteriorBlocks;

public class InteriorBlockLoot extends BlockLoot {
    @Override
    protected void addTables() {
        this.add(InteriorBlocks.CHAIR, InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.TABLE, InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.FRIDGE, BlockLoot::createDoorTable);
        this.dropSelf(InteriorBlocks.LAMP);
        this.dropSelf(InteriorBlocks.TRASH_CAN);
        this.dropSelf(InteriorBlocks.FURNITURE_WORKBENCH);
    }

    private static LootTable.Builder createFurnitureTable(Block block) {
        LootPool.Builder builder = applyExplosionCondition(block, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("primaryMaterial", "textures.primary"))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("secondaryMaterial", "textures.secondary"))));
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InteriorBlocks.BLOCKS;
    }
}
