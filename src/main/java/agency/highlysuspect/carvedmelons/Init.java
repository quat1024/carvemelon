package agency.highlysuspect.carvedmelons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Init implements ModInitializer {
	public static final String MODID = "carvedmelons";
	
	public static MelonCarvedBlock MELON_CARVED;
	public static MelonLanternBlock MELON_LANTERN;
	
	public static CreativeModeTab GROUP;
	
	@Override
	public void onInitialize() {
		//Create item group
		GROUP = FabricItemGroupBuilder.build(
			new ResourceLocation(MODID, "tab"),
			() -> new ItemStack(MELON_LANTERN)
		);
		
		//Create blocks
		MELON_CARVED = Registry.register(Registry.BLOCK,
			new ResourceLocation(MODID, "carved_melon"),
			new MelonCarvedBlock(
				//Just copy the settings on the melon.
				FabricBlockSettings.copyOf(Blocks.MELON)
			)
		);
		
		MELON_LANTERN = Registry.register(Registry.BLOCK,
			new ResourceLocation(MODID, "melon_o_lantern"),
			new MelonLanternBlock(
				//This block glows, so let's add a light level setting too.
				FabricBlockSettings.copyOf(MELON_CARVED).luminance(7)
			)
		);
		
		//Create blockitems
		Registry.register(Registry.ITEM, Registry.BLOCK.getKey(MELON_CARVED), new BlockItem(MELON_CARVED, new Item.Properties().tab(GROUP)));
		Registry.register(Registry.ITEM, Registry.BLOCK.getKey(MELON_LANTERN), new BlockItem(MELON_LANTERN, new Item.Properties().tab(GROUP)));
		
		//Register an interact handler for carving the vanilla melon with shears
		UseBlockCallback.EVENT.register(((player, world, hand, result) -> {
			if(player.isSpectator() || hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
			
			BlockPos pos = result.getBlockPos();
			Direction dir = result.getDirection();
			
			ItemStack held = player.getItemInHand(hand);
			BlockState hitState = world.getBlockState(pos);
			
			if(held.getItem() instanceof ShearsItem && hitState.getBlock() == Blocks.MELON) {
				if(!world.isClientSide) {
					//carve melon
					world.setBlockAndUpdate(pos, MELON_CARVED.getCarvingState(player, dir));
					
					//damage shears
					if(!player.isCreative()) {
						held.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
					}
					
					//play sound
					world.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
					
					//spawn a seed
					ItemEntity seeds = new ItemEntity(
						world,
						pos.getX() + 0.5 + dir.getStepX() * 0.65,
						pos.getY() + dir.getStepY() * 0.65,
						pos.getZ() + 0.5 + dir.getStepZ() * 0.65,
						new ItemStack(Items.MELON_SEEDS)
					);
					
					seeds.setDeltaMovement(
						0.05 * dir.getStepX() + world.random.nextDouble() * 0.02,
						0.05,
						0.05 * dir.getStepZ() + world.random.nextDouble() * 0.02
					);
					
					world.addFreshEntity(seeds);
				}
				
				return InteractionResult.SUCCESS;
			}
			
			return InteractionResult.PASS;
		}));
	}
}
