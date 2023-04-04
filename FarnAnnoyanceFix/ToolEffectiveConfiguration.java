package FarnAnnoyanceFix;

import java.lang.reflect.Field;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import net.minecraft.src.*;

public class ToolEffectiveConfiguration {
	Block[] DefaultpickAxeEffective = new Block[]{Block.stoneOvenActive, Block.stoneOvenIdle, Block.stairCompactCobblestone, Block.brick, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector, Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner};
	Block[] DefaultAxeEffective = new Block[]{Block.workbench, Block.stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder, Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks, Block.jukebox, Block.musicBlock};
	private static File configFile;
	public static final ToolEffectiveConfiguration instance = new ToolEffectiveConfiguration();

	public final void AddAllToolEffective() {
		configFile = new File(ModLoader.getMinecraftInstance().getMinecraftDir() + "/config/FarnAnnoyanceFixBlockEffectiveList.cfg");
		if (!configFile.exists()) {
			try {
				BufferedWriter configWriter = new BufferedWriter(new FileWriter(configFile));
				configWriter.write("#This is a configuration file for Farn's AnnoyanceFix mod");
				configWriter.newLine();
				configWriter.write("#That allowed you to add more effective block for each vanilla tool");
				configWriter.newLine();
				configWriter.write(" ");
				configWriter.newLine();
				configWriter.write("#Input are blockID (To add more block you need seperate with comma)");
				configWriter.newLine();
				configWriter.write("#If your input are ItemID Then the game will crash");
				configWriter.newLine();
				configWriter.write(" ");
				configWriter.newLine();
				configWriter.write("PickAxeEffectiveList=");
				for(int i = 0; i < this.DefaultpickAxeEffective.length; i++) {
					if(i > 0) configWriter.write(",");
					int id = this.DefaultpickAxeEffective[i].blockID;
					configWriter.write(String.valueOf(id));
				}
				configWriter.newLine();
				configWriter.write("AxeEffectiveList=");
				for(int i = 0; i < this.DefaultAxeEffective.length; i++) {
					if(i > 0) configWriter.write(",");
					int id = this.DefaultAxeEffective[i].blockID;
					configWriter.write(String.valueOf(id));
				}
				configWriter.newLine();
				configWriter.write("ShovelEffectiveList=" + Block.tilledField.blockID);
				configWriter.close();
			} catch (Exception e) {System.out.println("Failed To Write Config: " + e);}
		}
		parseConfigTest(configFile);
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
			}
		} catch (Exception e) {System.out.println("Failed To Read Config: " + e);}
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
		}
	}
}