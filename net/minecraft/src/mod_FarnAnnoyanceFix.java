package net.minecraft.src;

import java.util.Map;
import net.minecraft.client.Minecraft;
import FarnAnnoyanceFix.boat.*;
import FarnAnnoyanceFix.*;
import org.lwjgl.input.Mouse;

public class mod_FarnAnnoyanceFix extends BaseMod {

	public String Version() {
		return "2.0";
	}

	public mod_FarnAnnoyanceFix() {
		ModLoader.SetInGameHook(this, true, false);

		if(FarnAnnoyanceFixConfiguration.boatfix) {
			EntityProxyAPI.getInstance().overrideEntity("Boat", EntityBoat.class, 41, EntityBoatProxy.class);
			Item.itemsList[Item.boat.shiftedIndex] = null;
			Item boat = (new ItemBoatProxy(Item.boat.shiftedIndex - 256)).setIconCoord(8, 8).setItemName("boat");
		}

		if(FarnAnnoyanceFixConfiguration.fencefix) {
			Block.blocksList[85] = null;
			Block fence = (new BlockFenceProxy(85, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange();
		}

		if(FarnAnnoyanceFixConfiguration.woodstair) {
			Block.blocksList[53] = null;
			Block stairCompactPlanks = (new BlockStairsProxy(53, Block.planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange();
		}

		if(FarnAnnoyanceFixConfiguration.stonestair) {
			Block.blocksList[67] = null;
			Block stairCompactCobblestone = (new BlockStairsProxy(67, Block.cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange();
		}
		
		if(FarnAnnoyanceFixConfiguration.farmland) {		
			Block.blocksList[60] = null;
			Block tilledField = (new BlockFarmlandProxy(60)).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setBlockName("farmland");
		}

		FarnAnnoyanceFixConfiguration.instance.AddAllToolEffective();
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
