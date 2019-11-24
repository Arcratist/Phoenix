package com.guitcube.pheonix.upsidedown.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class InvertedWaterFluid extends ForgeFlowingFluid {

	public InvertedWaterFluid(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(World worldIn, BlockPos pos, IFluidState state) {

	}

	public static class Flowing extends InvertedWaterFluid {
		public Flowing(Properties properties) {
			super(properties);
			setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
		}

		protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		public int getLevel(IFluidState state) {
			return state.get(LEVEL_1_8);
		}

		public boolean isSource(IFluidState state) {
			return false;
		}
	}

	public static class Source extends InvertedWaterFluid {
		public Source(Properties properties) {
			super(properties);
		}

		public int getLevel(IFluidState state) {
			return 8;
		}

		public boolean isSource(IFluidState state) {
			return true;
		}
	}

}
