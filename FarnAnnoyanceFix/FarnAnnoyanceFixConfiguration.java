package FarnAnnoyanceFix;

import java.lang.reflect.Field;

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
}