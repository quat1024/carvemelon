package quaternary.carvemelon;

import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class MelonLanternBlock extends MelonCarvedBlock {
	public MelonLanternBlock(Settings s) {
		super(s);
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState state) {
		//Not called w/ redstone dust since I use a mixin for orientation sensitivity
		return true;
	}
	
	@Override
	public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction direction) {
		TwelveDirection td = state.get(MelonCarvedBlock.FACING);
		return direction == td.primaryDirection.getOpposite() ? 15 : 0;
	}
	
	@Override
	public PistonBehavior getPistonBehavior(BlockState var1) {
		return PistonBehavior.NORMAL;
	}
}
