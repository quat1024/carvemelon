package quaternary.carvemelon.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import quaternary.carvemelon.CarveMelon;
import quaternary.carvemelon.MelonLanternBlock;

@Mixin(RedstoneWireBlock.class)
public class MixinRedstoneWire {
	@Inject(
					at = @At("HEAD"),
					method = "method_10482(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
					cancellable = true
	)
	private static void hookConnectsTo(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() == CarveMelon.MELON_LANTERN) {
			cir.setReturnValue(state.get(MelonLanternBlock.FACING).primaryDirection.getOpposite() == direction);
		}
	}
}
