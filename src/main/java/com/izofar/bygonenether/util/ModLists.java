package com.izofar.bygonenether.util;

import com.google.common.collect.ImmutableList;
import com.izofar.bygonenether.init.ModBlocks;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.izofar.bygonenether.init.ModStructures;
import com.izofar.bygonenether.util.random.ModWeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

import java.util.List;

public abstract class ModLists {

	public static final List<Block> WITHERED_BLOCKS = ImmutableList.of(ModBlocks.WITHERED_BLACKSTONE.get(), ModBlocks.CHISELED_WITHERED_BLACKSTONE.get(), ModBlocks.CRACKED_WITHERED_BLACKSTONE.get(), ModBlocks.WITHERED_DEBRIS.get());
	public static final List<StructureFeature<?>> DELTALESS_STRUCTURES = ImmutableList.of(StructureFeature.BASTION_REMNANT, ModStructures.CATACOMB.get(), ModStructures.NETHER_FORTRESS.get(), ModStructures.CITADEL.get(), ModStructures.PIGLIN_MANOR.get());
    public static final WeightedRandomList<ModWeightedEntry<EntityType<? extends AbstractPiglin>>> PIGLIN_MANOR_MOBS = WeightedRandomList.create(
			new ModWeightedEntry<>(ModEntityTypes.PIGLIN_HUNTER.get(), 1),
			new ModWeightedEntry<>(EntityType.PIGLIN, 3)
		);
}
