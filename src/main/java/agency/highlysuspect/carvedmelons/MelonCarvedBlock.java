package agency.highlysuspect.carvedmelons;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;

public class MelonCarvedBlock extends Block {
	public MelonCarvedBlock(Properties s) {
		super(s);
	}
	
	public static final EnumProperty<TwelveDirection> FACING = EnumProperty.create("facing", TwelveDirection.class);
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Player placer = ctx.getPlayer();
		if(placer == null) return defaultBlockState();
		else {
			return defaultBlockState().setValue(FACING, TwelveDirection.fromEntity(placer).getOpposite());
		}
	}
	
	public BlockState getCarvingState(Entity ent, Direction hitDirection) {
		TwelveDirection td = TwelveDirection.byPrimary.get(hitDirection);
		if(hitDirection.getAxis() == Direction.Axis.Y) {
			td = td.withSecondary(ent.getDirection());
		}
		
		return defaultBlockState().setValue(FACING, td);
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState var1) {
		return PushReaction.DESTROY;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, state.getValue(FACING).rotate(rotation));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, state.getValue(FACING).mirror(mirror));
	}
}
