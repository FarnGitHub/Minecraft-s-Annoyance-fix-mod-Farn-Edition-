package net.minecraft.src;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;

import FarnAnnoyanceFix.game.block.slab.*;
import FarnAnnoyanceFix.*;

public class mod_FarnAnnoyanceFix extends BaseMod {

	public String Version() {
		return "2.5.1";
	}

	public void ModsLoaded() {
		FarnAnnoyanceFixCore.stairRender = ModLoader.getUniqueBlockModelID(this, true);
		ModLoader.SetInGameHook(this, true, false);
		if(slabplacement) {
			ModLoader.RegisterBlock(FarnAnnoyanceFixCore.upperSlab = (new BlockStepUpper(mod_FarnAnnoyanceFix.upperslabid)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab"));
			FarnAnnoyanceFixCore.instance.addEffectiveTools(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, new Block[]{FarnAnnoyanceFixCore.upperSlab});
			Item.itemsList[Block.stairSingle.blockID] = null;
			Item.itemsList[Block.stairSingle.blockID] = (new ItemSlabProxy(Block.stairSingle.blockID - 256)).setItemName("stoneSlab");
		}
		FarnAnnoyanceFixCore.instance.lateInit();
	}

	public void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
		if(modelID == FarnAnnoyanceFixCore.stairRender) {
			FarnAnnoyanceFixCore.instance.renderBlockStairInventory(renderer, block);
		}
	}

	public boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
		return modelID == FarnAnnoyanceFixCore.stairRender ? FarnAnnoyanceFixCore.instance.renderBlockStairsWithUpperVariant(renderer, block, x, y, z) : false;
	}

	public boolean OnTickInGame(Minecraft game) {
		if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
			if(game.objectMouseOver != null) {
				FarnAnnoyanceFixCore.instance.setCurrentItem(game.thePlayer, game.objectMouseOver);
			}
		}
		
		return true;
	}

	@MLProp(name="Stair_DropThemselves", info="Make cobbleStone stair drop themselves and allowed you to place upside")
	public static boolean stairdropthemselves = true;
	@MLProp(name="Modern_slab_Placement", info="1.3.1/Modern Slab Placement")
	public static boolean slabplacement = true;

	@MLProp(name="Better_Fence_Placement", info="Make fence able to place like normal block")
	public static boolean fencefix = true;

	@MLProp(name="Improved_boat", info="Make boat drop themselves and remove boat crashing when hit something")
	public static boolean boatfix = true;

	@MLProp(name="Better_stair_Recipe", info="Make stair's crafting recipe give you 8 block")
	public static boolean doublestairsrecipe = true;

	@MLProp(name="Modern_slab_recipe", info="Make slab's crafting recipe give you 6 block")
	public static boolean doubleslabsrecipe = true;

	@MLProp(name="No_FarmLand_trampling", info="Make farmland unable to get trampled by walking")
	public static boolean nofarmlandtrampling = true;

	@MLProp(name="Upper_Stair", info="Add Upper stair to the game")
	public static boolean upperstair = true;

	@MLProp(name="UpperSlab_BlockID", info="Block id for upper variant of slab")
	public static int upperslabid = 200;

	@MLProp(name="Disable_NightMare", info="Block id for upper variant of slab")
	public static  boolean noNightmare = true;
}
