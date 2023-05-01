package FarnAnnoyanceFix.game.slab;

import net.minecraft.src.*;

public class ItemSlabProxy extends ItemSlab {
	public int blocktype;

	public ItemSlabProxy(int var1) {
		super(var1);
		this.blocktype = var1 + 256;
	}

	public String getItemNameIS(ItemStack itemStack1) {
		try {
			return super.getItemName() + "." + BlockStep.field_22037_a[itemStack1.getItemDamage()];
		} catch (Exception e) {
			return "null";
		}

	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int face)  {
		
		if(itemStack.stackSize == 0) {
			return false;
		} else if(y == 127 && Block.blocksList[this.blocktype].blockMaterial.isSolid()) {
			return false;
		} else {
			int blockID = world.getBlockId(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			int type = meta & 3;
			boolean isUpper = (meta & 4) != 0;
			
			if(((face == 1 && !isUpper) || (face == 0 && isUpper)) && blockID == Block.stairSingle.blockID && meta == itemStack.getItemDamage()) {
				if (world.checkIfAABBIsClear(Block.stairDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlockAndMetadataWithNotify(x, y, z, Block.stairDouble.blockID, type)) {
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.stairDouble.stepSound.func_1145_d(), (Block.stairDouble.stepSound.getVolume() + 1.0F) / 2.0F, Block.stairDouble.stepSound.getPitch() * 0.8F);
					--itemStack.stackSize;
				}

				return true;
			} else if (onItemUseAux(itemStack, entityPlayer, world, x, y, z, face)) {
				return true;
			} else {
				return super.onItemUse(itemStack, entityPlayer, world, x, y, z, face);
			}
		}
	}

	private static boolean onItemUseAux (ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side) {
		switch (side) {
			case 0:	y --; break;
			case 1: y ++; break;
			case 2: z --; break;
			case 3: z ++; break;
			case 4: x --; break;
			case 5: x ++; break;
		}

		int blockID = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		int type = meta & 3;

		if (blockID == Block.stairSingle.blockID && type == itemStack.getItemDamage ()) {
			if (world.checkIfAABBIsClear(Block.stairDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) &&
				world.setBlockAndMetadataWithNotify(x, y, z, Block.stairDouble.blockID, type)
			) {
				world.playSoundEffect(
					(double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), 
					Block.stairDouble.stepSound.func_1145_d(), 
					(Block.stairDouble.stepSound.getVolume() + 1.0F) / 2.0F, 
					Block.stairDouble.stepSound.getPitch() * 0.8F
				);
				--itemStack.stackSize;
			}


			return true;
		} else {
			return false;
		}
	}
}
