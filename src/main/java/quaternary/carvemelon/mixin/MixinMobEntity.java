package quaternary.carvemelon.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import quaternary.carvemelon.CarveMelon;

@Mixin(MobEntity.class)
public class MixinMobEntity {
	@Inject(
		at = @At("HEAD"),
		method = "getPreferredEquipmentSlot(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/EquipmentSlot;",
		cancellable = true
	)
	private static void whenGettingPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if(stack.getItem() == CarveMelon.MELON_CARVED.asItem()) {
			cir.setReturnValue(EquipmentSlot.HEAD);
		}
	}
}
