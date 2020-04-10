package agency.highlysuspect.carvedmelons.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import agency.highlysuspect.carvedmelons.Init;

@SuppressWarnings("MethodCallSideOnly")
@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
	@Shadow
	protected abstract void renderPumpkinOverlay();
	
	@Inject(
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"
		),
		method = "render",
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void whenRendering(float what, CallbackInfo cbi, TextRenderer blah, ItemStack stack) {
		if(MinecraftClient.getInstance().options.perspective == 0 && stack.getItem() == Init.MELON_CARVED.asItem()) {
			this.renderPumpkinOverlay();
		}
	}
}
