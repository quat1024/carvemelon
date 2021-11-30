package agency.highlysuspect.carvedmelons;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class MelonLanternBlock extends MelonCarvedBlock {
	public MelonLanternBlock(Properties s) {
		super(s);
	}
	
	@Override
	public boolean isSignalSource(BlockState state) {
		//Normally redstone dust would call this but I change that with a mixin.
		//Can't hurt to override though
		return true;
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter view, BlockPos pos, Direction direction) {
		TwelveDirection td = state.getValue(MelonCarvedBlock.FACING);
		return direction == td.primaryDirection.getOpposite() ? 15 : 0;
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState var1) {
		return PushReaction.NORMAL;
	}
}
