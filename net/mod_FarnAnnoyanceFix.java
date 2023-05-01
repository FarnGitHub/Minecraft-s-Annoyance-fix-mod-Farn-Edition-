package net.minecraft.src;

import java.util.Map;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;

import FarnAnnoyanceFix.game.boat.*;
import FarnAnnoyanceFix.game.slab.*;
import FarnAnnoyanceFix.game.*;
import FarnAnnoyanceFix.*;

public class mod_FarnAnnoyanceFix extends BaseMod {
	public static int stairRender;

	public String Version() {
		return "2.5 pre-3";
	}

	public mod_FarnAnnoyanceFix() {
		stairRender = ModLoader.getUniqueBlockModelID(this, true);
	}

	public void ModsLoaded() {
		FarnAnnoyanceFixConfiguration config = FarnAnnoyanceFixConfiguration.instance;
		ModLoader.SetInGameHook(this, true, false);

		if(EnumOptionsAnnoyanceFix.boatfix.enumBoolean) {
			EntityProxyAPI.getInstance().overrideEntity("Boat", EntityBoat.class, 41, EntityBoatProxy.class);
			Item.boat = (new ItemBoatProxy(Item.boat.shiftedIndex)).setIconCoord(8, 8).setItemName("boat");
		}

		if(EnumOptionsAnnoyanceFix.fencefix.enumBoolean) {
			Block.blocksList[Block.fence.blockID] = null;
			Block fence = (new BlockFenceProxy(Block.fence.blockID, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange();
		}

		if(EnumOptionsAnnoyanceFix.woodstair.enumBoolean) {
			Block.blocksList[Block.stairCompactPlanks.blockID] = null;
			Block woodstair = (new BlockStairsProxy(Block.stairCompactPlanks.blockID, Block.planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange();
		}

		if(EnumOptionsAnnoyanceFix.stonestair.enumBoolean) {
			Block.blocksList[Block.stairCompactCobblestone.blockID] = null;
			Block cobblestair = (new BlockStairsProxy(Block.stairCompactCobblestone.blockID, Block.cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange();
		}
		
		if(EnumOptionsAnnoyanceFix.farmland.enumBoolean) {
			Block.blocksList[Block.tilledField.blockID] = null;
			Block farmland = (new BlockFarmlandProxy(Block.tilledField.blockID)).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setBlockName("farmland");		
		}

		if(EnumOptionsAnnoyanceFix.slab.enumBoolean) {
			Block.blocksList[Block.stairSingle.blockID] = null;
			Block stairSingle = (new BlockStepProxy(Block.stairSingle.blockID)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab");
			Block.blocksList[Block.stairDouble.blockID] = null;
			Block stairDouble = (new BlockStepNoMerge(Block.stairDouble.blockID, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab");
			Item.itemsList[Block.stairSingle.blockID] = (new ItemSlabProxy(Block.stairSingle.blockID - 256)).setItemName("stoneSlab");	
		}

		config.AddAllToolEffective();

		if(EnumOptionsAnnoyanceFix.doubleslabsrecipe.enumBoolean) {
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 3), new Object[]{"###", '#', Block.cobblestone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 0), new Object[]{"###", '#', Block.stone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 1), new Object[]{"###", '#', Block.sandStone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 2), new Object[]{"###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 3), new Object[]{"###", '#', Block.cobblestone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 0), new Object[]{"###", '#', Block.stone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 1), new Object[]{"###", '#', Block.sandStone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 2), new Object[]{"###", '#', Block.planks});
		}

		if(EnumOptionsAnnoyanceFix.doublestairsrecipe.enumBoolean) {
			config.removeRecipe(new ItemStack(Block.stairCompactCobblestone, 4), new Object[]{"#  ", "## ", "###", '#', Block.cobblestone});
			config.removeRecipe(new ItemStack(Block.stairCompactPlanks, 4), new Object[]{"#  ", "## ", "###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairCompactPlanks, 8), new Object[]{"#  ", "## ", "###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairCompactCobblestone, 8), new Object[]{"#  ", "## ", "###", '#', Block.cobblestone});
		}

	}

	public static void overrideBlock(int id, Block blockInstance) {
		ModLoader.RegisterBlock(blockInstance);
	}

	public static void overrideBlock(int id, Block blockInstance, Class itemBlock) {

		Item.itemsList[id] = null;
		ModLoader.RegisterBlock(blockInstance, itemBlock);
	}

	public void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
		if(modelID == stairRender) {
			AnnoyanceFixRender.instance.renderBlockStairInventory(renderer, block);
		}
	}

	public boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
		return modelID == stairRender ? AnnoyanceFixRender.instance.renderBlockStairsWithUpperVariant(renderer, block, x, y, z) : false;
	}

	public boolean OnTickInGame(Minecraft game) {
		InventoryPlayerUtils inventoryUtils = new InventoryPlayerUtils();
		if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
			if(game.objectMouseOver != null) {
				inventoryUtils.setCurrentItem(game.thePlayer, game.objectMouseOver);
			}
		}
		
		return true;
	}

	public void AddRenderer(Map renderers) {
		renderers.put(EntityBoatProxy.class, new RenderBoat());
	}
}
