package FarnAnnoyanceFix.game.slab;

import java.util.ArrayList;
import net.minecraft.src.*;

import java.util.Random;

public class BlockStepProxy extends BlockStepNoMerge {
	private int faceToSide[] = { 1, 0, 3, 2, 5, 4 };
	private int offsetsXForSide[] = { 0, 0, 0, 0, -1, 1 };
	private int offsetsYForSide[] = { -1, 1, 0, 0, 0, 0 };
	private int offsetsZForSide[] = { 0, 0, -1, 1, 0, 0 };	

	public BlockStepProxy(int i1) {
		super(i1, true);
	}

	public void setBlockBoundsBasedOnState (IBlockAccess blockAccess, int x, int y, int z) {
		if ((blockAccess.getBlockMetadata(x, y, z) & 4) != 0) {
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}
	
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {	
		int type = meta & 3;	
		if(type == 1) {
			return side == 0 ? 208 : (side == 1 ? 176 : 192);
		} else if(type == 2) {
			return 4;
		} else if(type == 3) {
			return 16;
		} else {
			return side <= 1 ? 6 : 5;		
		}		
 	}

	public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		if((world1.getBlockMetadata(i2, i3, i4) & 4) != 0) {
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
		}
	}	

	public int getBlockTextureFromSide(int i1) {
		return this.getBlockTextureFromSideAndMetadata(i1, 0);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int idDropped(int i1, Random random2) {
		return this.blockID;
	}

	public int quantityDropped(Random random1) {
		return 1;
	}

	protected int damageDropped(int i1) {
		return i1 & 3;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public void onBlockPlaced(World world, int x, int y, int z, int side)	{
		MovingObjectPosition mouse = ModLoader.getMinecraftInstance().objectMouseOver;
		float yWithinSide = (float)mouse.hitVec.yCoord - (float)y;

		if (side == 0 || side != 1 && yWithinSide > 0.5F) {
			int i = world.getBlockMetadata(x, y, z) & 3;
			world.setBlockMetadataWithNotify(x, y, z, i | 4);
		}
 	}

	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i2, int i3, int i4, int i5) {
 		if(this != Block.stairSingle) {
			return i5 == 0 && this.minY > 0.0D ? true : (i5 == 1 && this.maxY < 1.0D ? true : (i5 == 2 && this.minZ > 0.0D ? true : (i5 == 3 && this.maxZ < 1.0D ? true : (i5 == 4 && this.minX > 0.0D ? true : (i5 == 5 && this.maxX < 1.0D ? true : !blockAccess.isBlockOpaqueCube(i2, i3, i4))))));
		}
		
		if(i5 != 1 && i5 != 0 && !super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5)) {
			return false;
		}

		int i = i2;
		int j = i3;
		int k = i4;
		i += this.offsetsXForSide[this.faceToSide[i5]];
		j += this.offsetsYForSide[this.faceToSide[i5]];
		k += this.offsetsZForSide[this.faceToSide[i5]];
		boolean flag = (blockAccess.getBlockMetadata(i, j, k) & 8) != 0;

		if (!flag) {
			if (i5 == 1) {
				return true;
			}

			if (i5 == 0 && super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5)) {
				return true;
			} else {
				return blockAccess.getBlockId(i2, i3, i4) != blockID || (blockAccess.getBlockMetadata(i2, i3, i4) & 8) != 0;
			}
		}

		if (i5 == 0) {
			return true;
		}

		if (i5 == 1 && super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5)) {
			return true;
		} else {
			return blockAccess.getBlockId(i2, i3, i4) != blockID || (blockAccess.getBlockMetadata(i2, i3, i4) & 8) == 0;
		}
	}
}
