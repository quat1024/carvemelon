package agency.highlysuspect.carvedmelons.mixin;

import agency.highlysuspect.carvedmelons.Init;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Inject(
		at = @At("HEAD"),
		method = "getPreferredEquipmentSlot(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/EquipmentSlot;",
		cancellable = true
	)
	private static void whenGettingPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if(stack.getItem() == Init.MELON_CARVED.asItem()) {
			cir.setReturnValue(EquipmentSlot.HEAD);
		}
	}
}
