package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.client.Minecraft;
import FarnUtil.boat.*;
import org.lwjgl.input.Mouse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class mod_FarnAnnoyanceFix extends BaseMod {
	Minecraft mc = ModLoader.getMinecraftInstance();
	private EntityProxyAPI entityProxy = EntityProxyAPI.getInstance();
	InventoryPlayerUtils inventoryUtils = new InventoryPlayerUtils();
	Block[] pickAxeEffective = new Block[]{Block.stoneOvenActive, Block.stoneOvenIdle, Block.stairCompactCobblestone, Block.brick, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector, Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner};
	Block[] AxeEffective = new Block[]{Block.workbench, Block.stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder, Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks, Block.jukebox, Block.musicBlock};
	private static File configFile;

	public String Version() {
		return "2.0 Preview";
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
		this.writeConfigTest();
	}

	public boolean OnTickInGame(Minecraft game) {
		if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
			if(game.objectMouseOver != null) {
				inventoryUtils.setCurrentItem(game.thePlayer, game.objectMouseOver);
			}
		}
		
		return true;
	}

	private void writeConfigTest() {
		configFile = new File(this.mc.getMinecraftDir() + "/config/FarnAnnoyanceFixBlockEffectiveList.cfg");
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
				for(int i = 0; i < this.pickAxeEffective.length; i++) {
					if(i > 0) configWriter.write(",");
					int id = this.pickAxeEffective[i].blockID;
					configWriter.write(String.valueOf(id));
				}
				configWriter.newLine();
				configWriter.write("AxeEffectiveList=");
				for(int i = 0; i < this.AxeEffective.length; i++) {
					if(i > 0) configWriter.write(",");
					int id = this.AxeEffective[i].blockID;
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

	public void addEffectiveToolsFromConfigFile(Item[] effectiveTools, String lines) {
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
