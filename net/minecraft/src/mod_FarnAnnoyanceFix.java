package net.minecraft.src;

import java.lang.reflect.Field;

import java.util.Map;

import net.minecraft.client.Minecraft;

import FarnUtil.boat.*;

import org.lwjgl.input.Mouse;

public class mod_FarnAnnoyanceFix extends BaseMod {
	Minecraft mc;
	private EntityProxyAPI entityProxy = EntityProxyAPI.getInstance();
	InventoryPlayerUtils inventoryUtils = new InventoryPlayerUtils();

	public String Version() {
		return "1.0";
	}

	public mod_FarnAnnoyanceFix() {
		entityProxy.overrideEntity("Boat", EntityBoat.class, 41, EntityBoatProxy.class);
		Item.itemsList[Item.boat.shiftedIndex] = null;
		Item boat = (new ItemBoatProxy(Item.boat.shiftedIndex - 256)).setIconCoord(8, 8).setItemName("boat");

		ModLoader.SetInGameHook(this, true, false);

		Block.blocksList[85] = null;
		Block fence = (new BlockFenceProxy(85, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange();

		Block.blocksList[53] = null;
		Block stairCompactPlanks = (new BlockStairsProxy(53, Block.planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange();

		Block.blocksList[67] = null;
		Block stairCompactCobblestone = (new BlockStairsProxy(67, Block.cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange();

		Block.blocksList[60] = null;
		Block tilledField = (new BlockFarmlandProxy(60)).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setBlockName("farmland");

		this.addEffectiveTools(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, new Block[]{Block.stoneOvenActive, Block.stoneOvenIdle, stairCompactCobblestone, Block.brick, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector, Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner});
		this.addEffectiveTools(new Item[]{Item.axeDiamond, Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood}, new Block[]{Block.workbench, stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder, Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks, Block.jukebox, Block.musicBlock});
		this.addEffectiveTools(new Item[]{Item.shovelDiamond, Item.shovelGold, Item.shovelSteel, Item.shovelStone, Item.shovelWood}, new Block[]{tilledField});
	}

	public boolean OnTickInGame(Minecraft game) {
		if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
			if(game.objectMouseOver != null) {
				inventoryUtils.setCurrentItem(game.thePlayer, game.objectMouseOver);
			}
		}
		
		return true;
	}


	public void addEffectiveTools(Item[] effectiveTools, Block[] vulnerableBlocks) {
		try {
			Field blocksEffectiveAgainstField = ItemTool.class.getDeclaredFields()[0];
			blocksEffectiveAgainstField.setAccessible(true);

			for (Item tool : effectiveTools) {
				Block[] blocksEffectiveAgainstOriginal = (Block[]) blocksEffectiveAgainstField.get(tool);
				Block[] blocksEffectiveAgainst = new Block[blocksEffectiveAgainstOriginal.length + vulnerableBlocks.length];
				System.arraycopy(blocksEffectiveAgainstOriginal, 0, blocksEffectiveAgainst, 0, blocksEffectiveAgainstOriginal.length);
				System.arraycopy(vulnerableBlocks, 0, blocksEffectiveAgainst, blocksEffectiveAgainstOriginal.length, vulnerableBlocks.length);
				blocksEffectiveAgainstField.set(tool, blocksEffectiveAgainst);
			}

		} catch (Exception e) {
		}
	}

	public void AddRenderer(Map renderers) {
		renderers.put(EntityBoatProxy.class, new RenderBoatProxy());
	}
}
