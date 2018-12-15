package quaternary.carvemelon.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(AxeItem.class)
public interface IMixinAxeItem {
	@Accessor(value = "EFFECTIVE_BLOCKS")
	Set<Block> getEffectiveBlocks();
}
