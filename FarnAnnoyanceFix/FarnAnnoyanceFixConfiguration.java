package FarnAnnoyanceFix;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import net.minecraft.src.*;

public class FarnAnnoyanceFixConfiguration {
	Block[] DefaultpickAxeEffective = new Block[]{Block.stoneOvenActive, Block.stoneOvenIdle, Block.stairCompactCobblestone, Block.brick, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector, Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner};
	Block[] DefaultAxeEffective = new Block[]{Block.workbench, Block.stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder, Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks, Block.jukebox, Block.musicBlock};
	private static File configFile;
	public static final FarnAnnoyanceFixConfiguration instance = new FarnAnnoyanceFixConfiguration();

	public static boolean fencefix = true;
	public static boolean boatfix = true;
	public static boolean stonestair = true;
	public static boolean woodstair = true;
	public static boolean farmland = true;

	public final void AddAllToolEffective() {
		configFile = new File(ModLoader.getMinecraftInstance().getMinecraftDir() + "/config/FarnAnnoyanceFix.cfg");
		if (!configFile.exists()) {
			try {
				BufferedWriter configWriter = new BufferedWriter(new FileWriter(configFile));
				this.WriteLine(configWriter, "#This is a configuration file for Farn's AnnoyanceFix mod");
				this.WriteLine(configWriter, "#That allowed you to add more effective block for each vanilla tool");
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Input are blockID (To add more block you need seperate with comma)");
				this.WriteLine(configWriter, "#If your input are ItemID Then the game will crash");
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Tool_Effective_Configuration");
				this.WriteLine(configWriter, " ");
				this.WriteLineWithArrayOption(configWriter, "PickAxeEffectiveList=", this.DefaultpickAxeEffective);
				this.WriteLineWithArrayOption(configWriter, "AxeEffectiveList=", this.DefaultAxeEffective);
				this.WriteLine(configWriter, "ShovelEffectiveList=" + Block.tilledField.blockID);
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Misc_Configuration");
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Make fence placable without require block underneath");
				this.WriteLine(configWriter, "ModernFencePlacement=" + fencefix);
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Make boat drop themselves and also make it don't break when collide with something anymore");
				this.WriteLine(configWriter, "BetterBoat=" + boatfix);
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Make cobblestone stairs drop themselves");
				this.WriteLine(configWriter, "StoneStairDrop=" + stonestair);
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Make oak stairs drop themselves");
				this.WriteLine(configWriter, "WoodStairDrop=" + woodstair);
				this.WriteLine(configWriter, " ");
				this.WriteLine(configWriter, "#Remove farm trampling by walking");
				this.WriteLine(configWriter, "NoTrampling=" + farmland);
				configWriter.close();
			} catch (Exception e) {System.out.println("(Farn Annoyance Fix) Failed To Write Config File: " + e);}
		}
		parseConfigTest(configFile);
	}

	public void WriteLine(BufferedWriter writer, String OptionName) {
		try {
			writer.write(OptionName);
			writer.newLine();
		} catch (Exception e) {}
	}

	public void WriteLineWithArrayOption(BufferedWriter writer, String OptionName, Block[] blocklist) {
		try {
			writer.write(OptionName);
			for(int i = 0; i < blocklist.length; i++) {
				if(i > 0) writer.write(",");
				int id = blocklist[i].blockID;
				writer.write(String.valueOf(id));
			}
			writer.newLine();
		} catch (Exception e) {}
	}

	private void parseConfigTest(File var0) {
		try {
			BufferedReader var1 = new BufferedReader(new FileReader(var0));
			while(true) {
				String var2 = var1.readLine();
				if(var2 == null) {
					var1.close();
					break;
				}
				String[] var3 = var2.split("=");
				if(var3[0].equals("PickAxeEffectiveList")) {
					this.addEffectiveToolsFromConfigFile(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, var3[1]);							
				}

				if(var3[0].equals("ShovelEffectiveList")) {
					this.addEffectiveToolsFromConfigFile(new Item[]{Item.shovelDiamond, Item.shovelGold, Item.shovelSteel, Item.shovelStone, Item.shovelWood}, var3[1]);							
				}

				if(var3[0].equals("AxeEffectiveList")) {
					this.addEffectiveToolsFromConfigFile(new Item[]{Item.axeDiamond, Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood}, var3[1]);							
				}

				if(var3[0].equals("ModernFencePlacement")) {
					fencefix = var3[1].equals("true");
				}

				if(var3[0].equals("BetterBoat")) {
					boatfix = var3[1].equals("true");
				}

				if(var3[0].equals("StoneStairDrop")) {
					stonestair = var3[1].equals("true");
				}

				if(var3[0].equals("WoodStairDrop")) {
					woodstair = var3[1].equals("true");
				}

				if(var3[0].equals("NoTrampling")) {
					farmland = var3[1].equals("true");
				}
			}
		} catch (Exception e) {System.out.println("(Farn Annoyance Fix) Failed To Read Config File: " + e);}
	}

	private void addEffectiveToolsFromConfigFile(Item[] effectiveTools, String lines) {
		try {
			if(lines.contains(",")) {
				String[] effectivelist = lines.split(",");
				for(int i = 0; i < effectivelist.length; i++)  {
					this.addEffectiveTools(effectiveTools, new Block[]{Block.blocksList[Integer.parseInt(effectivelist[i])]});							
				}
			} else {
				this.addEffectiveTools(effectiveTools, new Block[]{Block.blocksList[Integer.parseInt(lines)]});
			}						
		} catch (Exception e) {
			System.out.println("(Farn Annoyance Fix) Failed To Add Tool Effective From Config File: " + e);
		}
	}

	private void addEffectiveTools(Item[] effectiveTools, Block[] vulnerableBlocks) {
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
			System.out.println("(Farn Annoyance Fix) Failed To Add Tool Effective: " + e);
		}
	}

	public void removeRecipe(ItemStack itemStack1, Object... object2) {
		String string3 = "";
		int i4 = 0;
		int i5 = 0;
		int i6 = 0;
		if(object2[i4] instanceof String[]) {
			String[] string11 = (String[])((String[])object2[i4++]);

			for(int i8 = 0; i8 < string11.length; ++i8) {
				String string9 = string11[i8];
				++i6;
				i5 = string9.length();
				string3 = string3 + string9;
			}
		} else {
			while(object2[i4] instanceof String) {
				String string7 = (String)object2[i4++];
				++i6;
				i5 = string7.length();
				string3 = string3 + string7;
			}
		}

		HashMap hashMap12;
		for(hashMap12 = new HashMap(); i4 < object2.length; i4 += 2) {
			Character character13 = (Character)object2[i4];
			ItemStack itemStack15 = null;
			if(object2[i4 + 1] instanceof Item) {
				itemStack15 = new ItemStack((Item)object2[i4 + 1]);
			} else if(object2[i4 + 1] instanceof Block) {
				itemStack15 = new ItemStack((Block)object2[i4 + 1], 1, -1);
			} else if(object2[i4 + 1] instanceof ItemStack) {
				itemStack15 = (ItemStack)object2[i4 + 1];
			}

			hashMap12.put(character13, itemStack15);
		}

		ItemStack[] itemStack14 = new ItemStack[i5 * i6];

		for(int i16 = 0; i16 < i5 * i6; ++i16) {
			char c10 = string3.charAt(i16);
			if(hashMap12.containsKey(c10)) {
				itemStack14[i16] = ((ItemStack)hashMap12.get(c10)).copy();
			} else {
				itemStack14[i16] = null;
			}
		}

		try {
			List recipeList = (List)ModLoader.getPrivateValue(CraftingManager.class, CraftingManager.getInstance(), 1);
			InventoryCrafting grid = getInventoryCraftingFromShapedRecipe(i5, i6, itemStack14);		
			for(int i2 = 0; i2 < recipeList.size(); ++i2) {
				IRecipe iRecipe3 = (IRecipe)recipeList.get(i2);
				if(iRecipe3.matches(grid)) {
					recipeList.remove(i2);
 					break;
				}
			} 
		} catch (Exception e) {}
	}

	public InventoryCrafting getInventoryCraftingFromShapedRecipe(int width, int height, ItemStack[] itemStack14) {
		try {	
			InventoryCrafting craftingGrid = new InventoryCrafting(null, width, height);
			ModLoader.setPrivateValue(InventoryCrafting.class, craftingGrid, "a", (ItemStack[])itemStack14);
			return craftingGrid;	
		} catch (Exception e) {
			try {
				InventoryCrafting craftingGrid = new InventoryCrafting(null, width, height);
				ModLoader.setPrivateValue(InventoryCrafting.class, craftingGrid, "stackList", (ItemStack[])itemStack14);
				return craftingGrid;
			} catch (Exception ea) {ea.printStackTrace(); return null;}	
		}
	}
}