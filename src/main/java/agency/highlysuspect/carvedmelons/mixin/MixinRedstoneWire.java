package agency.highlysuspect.carvedmelons.mixin;

import agency.highlysuspect.carvedmelons.Init;
import agency.highlysuspect.carvedmelons.MelonLanternBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class MixinRedstoneWire {
	@Inject(
		at = @At("HEAD"),
		method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
		cancellable = true
	)
	private static void whenConnectingTo(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() == Init.MELON_LANTERN) {
			//Point redstone dust into this block.
			cir.setReturnValue(state.getValue(MelonLanternBlock.FACING).primaryDirection.getOpposite() == direction);
		}
	}
}
