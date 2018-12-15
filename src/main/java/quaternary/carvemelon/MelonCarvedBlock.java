package quaternary.carvemelon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

public class MelonCarvedBlock extends Block {
	public MelonCarvedBlock(Settings s) {
		super(s);
	}
	
	public static final EnumProperty<TwelveDirection> FACING = EnumProperty.create("facing", TwelveDirection.class);
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		PlayerEntity placer = ctx.getPlayer();
		if(placer == null) return getDefaultState();
		else {
			return getDefaultState().with(FACING, TwelveDirection.fromEntity(placer).getOpposite());
		}
	}
	
	public BlockState getCarvingState(Entity ent, Direction hitDirection) {
		TwelveDirection td = TwelveDirection.byPrimary.get(hitDirection);
		if(hitDirection.getAxis() == Direction.Axis.Y) {
			td = td.withSecondary(ent.getHorizontalFacing());
		}
		
		return getDefaultState().with(FACING, td);
	}
	
	@Override
	public PistonBehavior getPistonBehavior(BlockState var1) {
		return PistonBehavior.DESTROY;
	}
	
	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.with(FACING));
	}
}
