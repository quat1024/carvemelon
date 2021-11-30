package agency.highlysuspect.carvedmelons.mixin.client;

import agency.highlysuspect.carvedmelons.Init;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("MethodCallSideOnly")
@Mixin(Gui.class)
public abstract class MixinInGameHud {
	@Shadow protected abstract void renderTextureOverlay(ResourceLocation texture, float opacity);
	@Shadow @Final private static ResourceLocation PUMPKIN_BLUR_LOCATION;
	
	@Inject(
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/world/entity/player/Inventory;getArmor(I)Lnet/minecraft/world/item/ItemStack;"
		),
		method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V",
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void whenRendering(PoseStack matrices, float what, CallbackInfo cbi, Font blah, ItemStack stack) {
		if(stack.getItem() == Init.MELON_CARVED.asItem()) {
			//Render the pumpkinblur texture when wearing the carved melon.
			//At this point in the code, it's already checked that the camera is in first-person mode.
			renderTextureOverlay(PUMPKIN_BLUR_LOCATION, 1f);
		}
	}
}
