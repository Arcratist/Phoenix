package com.guitcube.pheonix.upsidedown.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class InvertedWaterBlock extends FlowingFluidBlock {

	public InvertedWaterBlock(RegistryObject<FlowingFluid> fluid, Properties builder) {
		super(fluid, builder);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand) {
		System.out.println("InvertedWaterBlock.tick()");

		// super.tick(state, world, pos, rand);
	}

}
