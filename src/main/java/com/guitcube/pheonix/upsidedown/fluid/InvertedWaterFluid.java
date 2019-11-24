package com.guitcube.pheonix.upsidedown.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class InvertedWaterFluid extends ForgeFlowingFluid {

	public InvertedWaterFluid(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(World worldIn, BlockPos pos, IFluidState state) {
		/*
		 * if (!state.isSource()) { IFluidState ifluidstate =
		 * this.calculateCorrectFlowingState(worldIn, pos, worldIn.getBlockState(pos));
		 * int i = this.func_215667_a(worldIn, pos, state, ifluidstate); if
		 * (ifluidstate.isEmpty()) { state = ifluidstate; worldIn.setBlockState(pos,
		 * Blocks.AIR.getDefaultState(), 3); } else if (!ifluidstate.equals(state)) {
		 * state = ifluidstate; BlockState blockstate = ifluidstate.getBlockState();
		 * worldIn.setBlockState(pos, blockstate, 2);
		 * worldIn.getPendingFluidTicks().scheduleTick(pos, ifluidstate.getFluid(), i);
		 * worldIn.notifyNeighborsOfStateChange(pos, blockstate.getBlock()); } }
		 */
		// this.flowAround(worldIn, pos, state);

		System.out.println("IWF");
	}

	@Override
	protected void flowAround(IWorld worldIn, BlockPos pos, IFluidState stateIn) {
		System.out.println("FA");
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