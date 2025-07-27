package FarnAnnoyanceFix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;

public class FarnAnnoyanceFixCore {
	Block[] DefaultpickAxeEffective = new Block[]{Block.stoneOvenActive, Block.stoneOvenIdle, Block.stairCompactCobblestone, Block.brick, Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector, Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner, Block.stairSingle, Block.stairDouble};
	Block[] DefaultAxeEffective = new Block[]{Block.workbench, Block.stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder, Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks, Block.jukebox, Block.musicBlock};
	private static File configFile;
	public static final FarnAnnoyanceFixCore instance = new FarnAnnoyanceFixCore();
	private boolean isNormalEnvironment;

	private FarnAnnoyanceFixCore() {
		try {
			this.isNormalEnvironment = Class.forName("uu") != null;
		} catch (Exception exception2) {
			this.isNormalEnvironment = false;
		}

	}

	public final void setUpToolEffectiveList() {
		configFile = new File(ModLoader.getMinecraftInstance().getMinecraftDir() + "/config/FarnAnnoyanceFixToolEffective.cfg");
		if (!configFile.exists()) {
			try {
				BufferedWriter configWriter = new BufferedWriter(new FileWriter(configFile));
				configWriter.write("#This is a configuration file for Farn's AnnoyanceFix mod");
				configWriter.write(NewLine() + "#That allowed you to add more effective block for each vanilla tool");
				configWriter.write(NewLine() + " ");
				configWriter.write(NewLine() + "#Input are blockID (To add more block you need seperate with comma)");
				configWriter.write(NewLine() + "#If your input are ItemID Then the game will crash");
				configWriter.write(NewLine());
				configWriter.write(NewLine());
				configWriter.write(NewLine() + "#Tool_Effective_Configuration");
				configWriter.write(NewLine());
				this.WriteLineWithArrayOption(configWriter, "PickAxeEffectiveList=", this.DefaultpickAxeEffective);
				this.WriteLineWithArrayOption(configWriter, "AxeEffectiveList=", this.DefaultAxeEffective);
				configWriter.write(NewLine() + "ShovelEffectiveList=" + Block.tilledField.blockID);
				configWriter.close();
			} catch (Exception e) {
				System.out.println("(Farn Annoyance Fix) Failed To Write Config File: " + e);
				e.printStackTrace();
			}
		}
		parseConfigTest(configFile);
	}

	public static String NewLine() {
		return System.getProperty("line.separator");
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
			String string2 = "";
			while((string2 = var1.readLine()) != null) {
				String[] var3 = string2.split("=");
				if(var3[0].equals("PickAxeEffectiveList")) this.addEffectiveToolsFromConfigFile(new Item[]{Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood}, var3[1]);							
				if(var3[0].equals("ShovelEffectiveList")) this.addEffectiveToolsFromConfigFile(new Item[]{Item.shovelDiamond, Item.shovelGold, Item.shovelSteel, Item.shovelStone, Item.shovelWood}, var3[1]);							
				if(var3[0].equals("AxeEffectiveList")) this.addEffectiveToolsFromConfigFile(new Item[]{Item.axeDiamond, Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood}, var3[1]);							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		InventoryCrafting craftingGrid = new InventoryCrafting(null, width, height);

		try {	
			ModLoader.setPrivateValue(InventoryCrafting.class, craftingGrid, this.isNormalEnvironment() ? "a" : "stackList", (ItemStack[])itemStack14);	
		} catch (Exception e) {
			return null;
		}

		return craftingGrid;
	}

	public void renderBlockStairInventory(RenderBlocks renderer, Block uu1) {
		Tessellator nw1 = Tessellator.instance;
		for(int i9 = 0; i9 < 2; ++i9) {
			if(i9 == 0) {
				uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			}

			if(i9 == 1) {
				uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			}

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderBottomFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(0));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderTopFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(1));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderEastFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(2));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderWestFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(3));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderNorthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(4));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderSouthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(5));
			nw1.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	public boolean renderBlockStairsWithUpperVariant(RenderBlocks renderer, Block uu1, int i1, int j1, int k1) {
		boolean flag = false;
		int l1 = ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(i1, j1, k1);
		if(l1 == 0) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 1) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 2) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 3) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 4) {
			uu1.setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 5) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 6) {
			uu1.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 7) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		}

		uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return flag;
	}

	public void setCurrentItem(EntityPlayerSP player, MovingObjectPosition hoveredObject) {
		int itemID = 0;
		int itemDamage = 0;
		World world = player.worldObj;
		
		if (hoveredObject.typeOfHit == EnumMovingObjectType.ENTITY) {
			Entity entityLookedAt = hoveredObject.entityHit;
			if (entityLookedAt instanceof EntityPainting) {
				itemID = Item.painting.shiftedIndex;
			} else if (entityLookedAt instanceof EntityBoat) {
				itemID = Item.boat.shiftedIndex;
			} else if (entityLookedAt instanceof EntityMinecart) {
				int minecartType = ((EntityMinecart)entityLookedAt).minecartType;
				if (minecartType == ((ItemMinecart)Item.minecartEmpty).minecartType) {
					itemID = Item.minecartEmpty.shiftedIndex;
				} else if (minecartType == ((ItemMinecart)Item.minecartCrate).minecartType) {
					itemID = Item.minecartCrate.shiftedIndex;
				} else if (minecartType == ((ItemMinecart)Item.minecartPowered).minecartType) {
					itemID = Item.minecartPowered.shiftedIndex;
				}
			}
		} else if (hoveredObject.typeOfHit == EnumMovingObjectType.TILE) {
			itemID = world.getBlockId(hoveredObject.blockX, hoveredObject.blockY, hoveredObject.blockZ);
			itemDamage = world.getBlockMetadata(hoveredObject.blockX, hoveredObject.blockY, hoveredObject.blockZ);
			
			if (itemID == Block.redstoneWire.blockID) {
				itemID = Item.redstone.shiftedIndex;
			} else if (itemID == Block.grass.blockID) {
				itemID = Block.dirt.blockID;
			} else if (itemID == Block.stairDouble.blockID) {
				itemID = Block.stairSingle.blockID;
			} else if (itemID == Block.doorWood.blockID) {
				itemID = Item.doorWood.shiftedIndex;
			} else if (itemID == Block.doorSteel.blockID) {
				itemID = Item.doorSteel.shiftedIndex;
			} else if (itemID == Block.signPost.blockID || itemID == Block.signWall.blockID) {
				itemID = Item.sign.shiftedIndex;
			} else if (itemID == Block.crops.blockID) {
				itemID = Item.seeds.shiftedIndex;
			} else if (itemID == Block.redstoneRepeaterIdle.blockID || itemID == Block.redstoneRepeaterActive.blockID) {
				itemID = Item.redstoneRepeater.shiftedIndex;
			} else if (itemID == Block.blockBed.blockID) {
				itemID = Item.bed.shiftedIndex;
			} else if (itemID == Block.cake.blockID) {
				itemID = Item.cake.shiftedIndex;
			} else if (itemID == Block.pistonExtension.blockID) {
				itemID = Block.pistonBase.blockID;
			}
		}
		
		int itemLocation = this.getInventorySlotContainItem(itemID, itemDamage, player);
		if (itemLocation == -1) {
			return;
		}

		if (itemLocation < 9) {
			player.inventory.currentItem = itemLocation;
		} else {
			if (getCurrentItem(player) != null) {
				int firstEmptyStack = getFirstEmptyStack(player);
				if (firstEmptyStack < 9 && firstEmptyStack >= 0) {
					player.inventory.currentItem = firstEmptyStack;
				} else {
					// No room in hotbar, swap items
					ItemStack tempItem = player.inventory.mainInventory[player.inventory.currentItem];
					player.inventory.mainInventory[player.inventory.currentItem] = player.inventory.mainInventory[itemLocation];
					player.inventory.mainInventory[itemLocation] = tempItem;
					return;
				}
			}

			player.inventory.mainInventory[player.inventory.currentItem] = player.inventory.mainInventory[itemLocation];
			player.inventory.mainInventory[itemLocation] = null;
		}
	}

	private int getFirstEmptyStack(EntityPlayerSP player) {
		for(int var1 = 0; var1 < player.inventory.mainInventory.length; ++var1) {
			if (player.inventory.mainInventory[var1] == null) {
				return var1;
			}
		}

		return -1;
	}

	public ItemStack getCurrentItem(EntityPlayerSP player) {
		return player.inventory.currentItem < 9 && player.inventory.currentItem >= 0 ? player.inventory.mainInventory[player.inventory.currentItem] : null;
	}

	public int getInventorySlotContainItem(int itemID, int itemDamage, EntityPlayerSP player) {
		for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
			if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].itemID == itemID && player.inventory.mainInventory[i].getItemDamage() == itemDamage) {
				return i;
			}
		}

		return -1;
	}

	public void overrideEntity(String name, Class originalClass, int id, Class overrideclass) {
		try {
			// Get all the fields with reflection
			HashMap<String, Class> stringToClassMapping = (HashMap<String, Class>) ModLoader.getPrivateValue(EntityList.class, null, this.isNormalEnvironment() ? "a" : "stringToClassMapping");
			HashMap<Class, String> classToStringMapping = (HashMap<Class, String>) ModLoader.getPrivateValue(EntityList.class, null, this.isNormalEnvironment() ? "b" : "classToStringMapping");
			HashMap<Integer, Class> IDtoClassMapping = (HashMap<Integer, Class>) ModLoader.getPrivateValue(EntityList.class, null, this.isNormalEnvironment() ? "c" : "IDtoClassMapping");
			HashMap<Class, Integer> classToIDMapping = (HashMap<Class, Integer>) ModLoader.getPrivateValue(EntityList.class, null, this.isNormalEnvironment() ? "d" : "classToIDMapping");

			// Remove vanilla Creepers from EntityList
			stringToClassMapping.remove(name);
			classToStringMapping.remove(originalClass);
			IDtoClassMapping.remove(id);
			classToIDMapping.remove(originalClass);

			// Get the private method to re-add the Creeper override
			Method addMapping;

			try {
				addMapping = EntityList.class.getDeclaredMethod(this.isNormalEnvironment() ? "a" : "addMapping", Class.class, String.class, int.class);
			} catch (Exception ex) {
				throw new NoSuchMethodException("Failed to find method 'a' ('addMapping'!");
			}

			addMapping.setAccessible(true);
			addMapping.invoke(null, overrideclass, name, id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isNormalEnvironment() {
		return this.isNormalEnvironment; 	
	}

	public static void overrideVanillaItem(Item vanillaitem, Item itemoverriden) {
		try {
			for(int i2 = 0; i2 < Item.class.getDeclaredFields().length; ++i2) {
				try {
					if(((Item)Item.class.getDeclaredFields()[i2].get((Object)null)).equals(vanillaitem)) {
						ModLoader.setPrivateValue(Item.class, (Object)null, i2, itemoverriden);
						break;
					}
				} catch (Exception exception10) {
				}
			}

			System.gc();
		} catch (Exception exception11) {
			throw new RuntimeException(exception11);
		}
	}

	public static void overrideVanillaBlock(Block block0, Block block1) {
		try {
			for(int i2 = 0; i2 < Block.class.getDeclaredFields().length; ++i2) {
				try {
					if(((Block)Block.class.getDeclaredFields()[i2].get((Object)null)).equals(block0)) {
						ModLoader.setPrivateValue(Block.class, (Object)null, i2, block1);
						break;
					}
				} catch (Exception exception10) {
				}
			}

			Item[] item12 = Item.itemsList;
			int i3 = item12.length;

			for(int i4 = 0; i4 < i3; ++i4) {
				Item item5 = item12[i4];

				try {
					Field field6 = ItemTool.class.getDeclaredFields()[0];
					field6.setAccessible(true);
					Block[] block7 = (Block[])((Block[])field6.get(item5));

					for(int i8 = 0; i8 < block7.length; ++i8) {
						if(block7[i8].equals(block0)) {
							block7[i8] = block1;
							field6.set(item5, block7);
							break;
						}
					}
				} catch (Exception exception9) {
				}
			}

			System.gc();
		} catch (Exception exception11) {
			throw new RuntimeException(exception11);
		}
	}

}