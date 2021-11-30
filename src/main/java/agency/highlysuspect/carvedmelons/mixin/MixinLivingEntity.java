package agency.highlysuspect.carvedmelons.mixin;

import agency.highlysuspect.carvedmelons.Init;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Inject(
		at = @At("HEAD"),
		method = "getEquipmentSlotForItem(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/EquipmentSlot;",
		cancellable = true
	)
	private static void whenGettingPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if(stack.getItem() == Init.MELON_CARVED.asItem()) {
			//Allow placing the carved melon item on the head.
			cir.setReturnValue(EquipmentSlot.HEAD);
		}
	}
}
