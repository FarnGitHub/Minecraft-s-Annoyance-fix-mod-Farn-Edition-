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
	public static Block upperStair = null;
	public static Item farnAnnoyanceFixBoat = null; 

	public String Version() {
		return "2.5.1";
	}

	public mod_FarnAnnoyanceFix() {
		stairRender = ModLoader.getUniqueBlockModelID(this, true);
	}

	public void ModsLoaded() {
		FarnAnnoyanceFixCore config = FarnAnnoyanceFixCore.instance;
		ModLoader.SetInGameHook(this, true, false);

		if(boatfix) {
			Item.itemsList[Item.boat.shiftedIndex] = null;
			FarnAnnoyanceFixCore.instance.overrideVanillaItem(Item.boat, (new ItemBoatProxy(Item.boat.shiftedIndex - 256)).setIconCoord(8, 8).setItemName("boat"));
			FarnAnnoyanceFixCore.instance.overrideEntity("Boat", EntityBoat.class, 41, EntityBoatProxy.class);
		}

		if(fencefix) {
			Block.blocksList[Block.fence.blockID] = null;
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.fence, (new BlockFenceProxy(Block.fence.blockID, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange());
		}

		if(woodstairdrop || upperstair) {
			Block.blocksList[Block.stairCompactPlanks.blockID] = null;
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.stairCompactPlanks, (new BlockStairsProxy(Block.stairCompactPlanks.blockID, Block.planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange());
			FarnAnnoyanceFixCore.instance.addEffectiveTools(new Item[]{Item.axeDiamond, Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood}, new Block[]{Block.stairCompactPlanks});
		}

		if(stonestairdrop || upperstair) {
			Block.blocksList[Block.stairCompactCobblestone.blockID] = null;
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.stairCompactCobblestone, (new BlockStairsProxy(Block.stairCompactCobblestone.blockID, Block.cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange());
			FarnAnnoyanceFixCore.instance.addEffectiveTools(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, new Block[]{Block.stairCompactCobblestone});
		}

		if(nofarmlandtrampling) {
			Block.blocksList[Block.tilledField.blockID] = null;
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.tilledField, (new BlockFarmlandProxy(Block.tilledField.blockID)).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setBlockName("farmland"));
		}

		if(slabplacement) {
			Block.blocksList[Block.stairSingle.blockID] = null;
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.stairSingle, (new BlockStepProxy(Block.stairSingle.blockID, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab"));
			Block.blocksList[Block.stairDouble.blockID] = null;
			ModLoader.RegisterBlock(upperStair  = (new BlockStepUpper(upperslabid)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab"));
			FarnAnnoyanceFixCore.instance.addEffectiveTools(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, new Block[]{upperStair});
			FarnAnnoyanceFixCore.overrideVanillaBlock(Block.stairDouble, (new BlockStepProxy(Block.stairDouble.blockID, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("stoneSlab"));
			Item.itemsList[Block.stairSingle.blockID] = null;
			Item.itemsList[Block.stairSingle.blockID] = (new ItemSlabProxy(Block.stairSingle.blockID - 256)).setItemName("stoneSlab");	
		}

		if(doubleslabsrecipe) {
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 3), new Object[]{"###", '#', Block.cobblestone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 0), new Object[]{"###", '#', Block.stone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 1), new Object[]{"###", '#', Block.sandStone});
			config.removeRecipe(new ItemStack(Block.stairSingle, 3, 2), new Object[]{"###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 3), new Object[]{"###", '#', Block.cobblestone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 0), new Object[]{"###", '#', Block.stone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 1), new Object[]{"###", '#', Block.sandStone});
			ModLoader.AddRecipe(new ItemStack(Block.stairSingle, 6, 2), new Object[]{"###", '#', Block.planks});
		}

		if(doublestairsrecipe) {
			config.removeRecipe(new ItemStack(Block.stairCompactCobblestone, 4), new Object[]{"#  ", "## ", "###", '#', Block.cobblestone});
			config.removeRecipe(new ItemStack(Block.stairCompactPlanks, 4), new Object[]{"#  ", "## ", "###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairCompactPlanks, 8), new Object[]{"#  ", "## ", "###", '#', Block.planks});
			ModLoader.AddRecipe(new ItemStack(Block.stairCompactCobblestone, 8), new Object[]{"#  ", "## ", "###", '#', Block.cobblestone});
		}

		config.setUpToolEffectiveList();

	}

	public void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
		if(modelID == stairRender) {
			FarnAnnoyanceFixCore.instance.renderBlockStairInventory(renderer, block);
		}
	}

	public boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
		return modelID == stairRender ? FarnAnnoyanceFixCore.instance.renderBlockStairsWithUpperVariant(renderer, block, x, y, z) : false;
	}

	public boolean OnTickInGame(Minecraft game) {
		if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
			if(game.objectMouseOver != null) {
				FarnAnnoyanceFixCore.instance.setCurrentItem(game.thePlayer, game.objectMouseOver);
			}
		}
		
		return true;
	}

	public void AddRenderer(Map renderers) {
		renderers.put(EntityBoatProxy.class, new RenderBoat());
	}

	@MLProp(name="Improved_CobbleStoneStair", info="Make cobbleStone stair drop themselves and allowed you to place upside")
	public static boolean stonestairdrop = true;

	@MLProp(name="Improved_Wooden_Stair", info="Make wooden stair drop themselves and allowed you to place upside")
	public static boolean woodstairdrop = true;

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

	@MLProp(name="Stair_Upper", info="Add Upper stair to the game")
	public static boolean upperstair = true;

	@MLProp(name="UpperSlab_BlockID", info="Block id for upper variant of slab")
	public static int upperslabid = 200;
}
