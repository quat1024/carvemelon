package quaternary.carvemelon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.fabricmc.fabric.events.PlayerInteractionEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.block.BlockItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import quaternary.carvemelon.mixin.IMixinAxeItem;

import java.util.Set;

public class CarveMelon implements ModInitializer {
	public static final String MODID = "carvemelon";
	
	public static MelonCarvedBlock MELON_CARVED;
	public static MelonLanternBlock MELON_LANTERN;
	
	@Override
	public void onInitialize() {
		//Create blocks
		MELON_CARVED = Registry.BLOCK.register(
						new Identifier(MODID, "carved_melon"),
						new MelonCarvedBlock(
										//Just copy the settings on the melon.
										FabricBlockSettings.copy(Blocks.MELON)
										.build()
						)
		);
		
		MELON_LANTERN = Registry.BLOCK.register(
						new Identifier(MODID, "melon_o_lantern"),
						new MelonLanternBlock(
										//This block glows, so let's add a light level setting too.
										FabricBlockSettings.copy(MELON_CARVED)
										.lightLevel(7)
										.build()
						)
		);
		
		//Create blockitems
		Registry.ITEM.register(Registry.BLOCK.getId(MELON_CARVED), new BlockItem(MELON_CARVED, new Item.Settings().itemGroup(ItemGroup.BUILDING_BLOCKS)));
		Registry.ITEM.register(Registry.BLOCK.getId(MELON_LANTERN), new BlockItem(MELON_LANTERN, new Item.Settings().itemGroup(ItemGroup.REDSTONE)));
		
		//Register the axe as being effective against these blocks
		//Since the list is static, I just need any axe item to get an instance of it
		Set<Block> axeEffectiveBlocks = ((IMixinAxeItem)Items.WOODEN_AXE).getEffectiveBlocks();
		axeEffectiveBlocks.add(MELON_CARVED);
		axeEffectiveBlocks.add(MELON_LANTERN);
		
		//Register an interact handler for carving the vanilla melon with shears
		PlayerInteractionEvent.INTERACT_BLOCK.register(((player, world, hand, pos, direction, hitX, hitY, hitZ) -> {
			if(player.isSpectator()) return ActionResult.PASS;
			
			ItemStack held = player.getStackInHand(hand);
			BlockState hitState = world.getBlockState(pos);
			if(held.getItem() instanceof ShearsItem && hitState.getBlock() == Blocks.MELON) {
				if(!world.isRemote) {
					//carve melon
					world.setBlockState(pos, MELON_CARVED.getCarvingState(player, direction));
					
					//damage shears
					if(!player.isCreative()) held.applyDamage(1, player);
					
					//play sound
					world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCK, 1.0F, 1.0F);
					
					//spawn a seed
					ItemEntity seeds = new ItemEntity(
									world,
									pos.getX() + 0.5 + direction.getOffsetX() * 0.65,
									pos.getY() + direction.getOffsetY() * 0.65,
									pos.getZ() + 0.5 + direction.getOffsetZ() * 0.65,
									new ItemStack(Items.MELON_SEEDS)
					);
					seeds.velocityX = 0.05 * direction.getOffsetX() + world.random.nextDouble() * 0.02;
					seeds.velocityY = 0.05;
					seeds.velocityZ = 0.05 * direction.getOffsetZ() + world.random.nextDouble() * 0.02;
					world.spawnEntity(seeds);
				}
				
				return ActionResult.SUCCESS;
			}
			
			return ActionResult.PASS;
		}));
	}
}
