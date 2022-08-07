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
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.blocks.LampOnAStickBlock;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.Registration;

import java.util.stream.Collectors;

public class InteriorBlockLoot extends BlockLoot {
    @Override
    protected void addTables() {
        this.add(InteriorBlocks.CHAIR.get(), InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.TABLE.get(), InteriorBlockLoot::createFurnitureTable);
        this.add(InteriorBlocks.FRIDGE.get(), BlockLoot::createDoorTable);
        this.add(InteriorBlocks.MODERN_DOOR.get(), BlockLoot::createDoorTable);
        this.dropSelf(InteriorBlocks.LAMP.get());
        this.dropSelf(InteriorBlocks.TRASH_CAN.get());
        this.dropSelf(InteriorBlocks.FURNITURE_WORKBENCH.get());
        this.dropSelf(InteriorBlocks.LAMP.get());
        this.add(InteriorBlocks.LAMP_ON_A_STICK.get(), block -> LootTable.lootTable().withPool(applyExplosionCondition(block,
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.STICK).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(LampOnAStickBlock.PART, LampOnAStickBlock.Part.BOTTOM))))
                        .add(LootItem.lootTableItem(Items.STICK).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(LampOnAStickBlock.PART, LampOnAStickBlock.Part.MIDDLE))))
                        .add(LootItem.lootTableItem(InteriorBlocks.LAMP.get()).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
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
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
