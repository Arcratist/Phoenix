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

	private boolean enableSourceCondense;
	private boolean enableSourceFloat = true;
	private int maxHeight = 120;
	private int densityDir = 1;

	public InvertedWaterFluid(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(World worldIn, BlockPos pos, IFluidState state) {
		int i = this.func_215667_a(worldIn, pos, state, state);
		if (state.isSource()) {
		//	if (worldIn.rand.nextInt(3) == 0) {
//
				if (shouldSourceBlockFloat(worldIn, pos)) {
					worldIn.setBlockState(pos.add(0, densityDir, 0), this.getDefaultState().getBlockState(), 3);
					worldIn.getPendingFluidTicks().scheduleTick(pos.add(0, densityDir, 0), state.getFluid(), i);
					worldIn.notifyNeighborsOfStateChange(pos.add(0, densityDir, 0), state.getBlockState().getBlock());
					return;
		//		}
			}
		}

		//worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

		//this.flowAround(worldIn, pos, state);

		System.out.println("IWF");
	}

	private boolean shouldSourceBlockFloat(World world, BlockPos pos) {

		BlockState state = world.getBlockState(pos.add(0, densityDir, 0));
		return enableSourceFloat && (state != this.getBlockState(getDefaultState()));
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