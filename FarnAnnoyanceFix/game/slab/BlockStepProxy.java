package FarnAnnoyanceFix.game.slab;

import net.minecraft.src.Block;
import net.minecraft.src.BlockStep;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_FarnAnnoyanceFix;

public class BlockStepProxy extends BlockStep {
	private int[] faceToSide = new int[]{1, 0, 3, 2, 5, 4};
	private int[] offsetsXForSide = new int[]{0, 0, 0, 0, -1, 1};
	private int[] offsetsYForSide = new int[]{-1, 1, 0, 0, 0, 0};
	private int[] offsetsZForSide = new int[]{0, 0, -1, 1, 0, 0};
	protected boolean isDouble;

	public BlockStepProxy(int i1, boolean z2) {
		super(i1, z2);
		this.isDouble = z2;
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
	}

	public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
		if(!this.isDouble) {
			MovingObjectPosition movingObjectPosition6 = ModLoader.getMinecraftInstance().objectMouseOver;
			float f7 = (float)movingObjectPosition6.hitVec.yCoord - (float)i3;
			if(i5 == 0 || i5 != 1 && f7 > 0.5F) {
				int i8 = world1.getBlockMetadata(i2, i3, i4);
				world1.setBlockAndMetadataWithNotify(i2, i3, i4, mod_FarnAnnoyanceFix.upperStair.blockID, i8);
			}
		}

	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		if(this != Block.stairSingle && this != mod_FarnAnnoyanceFix.upperStair) {
			return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
		} else if(i5 != 1 && i5 != 0 && !super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5)) {
			return false;
		} else {
			int i6 = i2 + this.offsetsXForSide[this.faceToSide[i5]];
			int i7 = i3 + this.offsetsYForSide[this.faceToSide[i5]];
			int i8 = i4 + this.offsetsZForSide[this.faceToSide[i5]];
			boolean z9 = iBlockAccess1.getBlockId(i6, i7, i8) == mod_FarnAnnoyanceFix.upperStair.blockID;
			return !z9 ? (i5 == 1 ? true : (i5 == 0 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID)) : (i5 == 0 ? true : (i5 == 1 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID));
		}
	}
}
