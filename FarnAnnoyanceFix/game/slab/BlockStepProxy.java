package FarnAnnoyanceFix.game.slab;

import java.util.ArrayList;
import net.minecraft.src.*;

import java.util.Random;

public class BlockStepProxy extends BlockStep {
	private int faceToSide[] = { 1, 0, 3, 2, 5, 4 };
	private int offsetsXForSide[] = { 0, 0, 0, 0, -1, 1 };
	private int offsetsYForSide[] = { -1, 1, 0, 0, 0, 0 };
	private int offsetsZForSide[] = { 0, 0, -1, 1, 0, 0 };
	protected boolean isDouble;	

	public BlockStepProxy(int i1, boolean z2) {
		super(i1, z2);
		this.isDouble = z2;
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		return;
	}

	public void onBlockPlaced(World world, int x, int y, int z, int side) {
		if(!this.isDouble) {
			MovingObjectPosition mouse = ModLoader.getMinecraftInstance().objectMouseOver;
			float yWithinSide = (float)mouse.hitVec.yCoord - (float)y;

			if (side == 0 || side != 1 && yWithinSide > 0.5F) {
				int metadata = world.getBlockMetadata(x, y, z);
				world.setBlockAndMetadataWithNotify(x, y, z, mod_FarnAnnoyanceFix.upperStair.blockID, metadata);
			}
		}
 	}

	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i2, int i3, int i4, int i5) {
 		if(this != Block.stairSingle) {
			return super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5);
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
		boolean flag = blockAccess.getBlockId(i, j, k) == mod_FarnAnnoyanceFix.upperStair.blockID;

		if (!flag) {
			if (i5 == 1) {
				return true;
			}

			if (i5 == 0 && super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5)) {
				return true;
			} else {
				return blockAccess.getBlockId(i2, i3, i4) != this.blockID;
			}
		}

		if (i5 == 0) {
			return true;
		}

		if (i5 == 1 && super.shouldSideBeRendered(blockAccess, i2, i3, i4, i5)) {
			return true;
		} else {
			return blockAccess.getBlockId(i2, i3, i4) != this.blockID;
		}
	}
}
