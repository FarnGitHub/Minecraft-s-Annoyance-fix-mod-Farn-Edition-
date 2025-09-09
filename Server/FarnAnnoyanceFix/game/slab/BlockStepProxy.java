package FarnAnnoyanceFix.game.slab;

import FarnAnnoyanceFix.FarnAnnoyanceFixCore;
import net.minecraft.src.*;

public class BlockStepProxy extends BlockStep {
	private int[] faceToSide = new int[]{1, 0, 3, 2, 5, 4};
	private int[] offsetsXForSide = new int[]{0, 0, 0, 0, -1, 1};
	private int[] offsetsYForSide = new int[]{-1, 1, 0, 0, 0, 0};
	private int[] offsetsZForSide = new int[]{0, 0, -1, 1, 0, 0};
	protected boolean isDouble;
	private int capturedSide = 0;

	public BlockStepProxy(int i1, boolean z2) {
		super(i1, z2);
		this.isDouble = z2;
	}

	@Override
	public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
		capturedSide = i5;
		super.onBlockPlaced(world1, i2, i3, i4, i5);
	}

	public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
		if(!this.isDouble && entityLiving5 instanceof EntityPlayer) {
			MovingObjectPosition movingObjectPosition6 = FarnAnnoyanceFixCore.getPlayerMouse((EntityPlayer) entityLiving5, world1);
			float f7 = (float)movingObjectPosition6.hitVec.yCoord - (float)i3;
			if(capturedSide == 0 || capturedSide != 1 && f7 > 0.5F) {
				int i8 = world1.getBlockMetadata(i2, i3, i4);
				world1.setBlockAndMetadataWithNotify(i2, i3, i4, mod_FarnAnnoyanceFixMP.upperStair.blockID, i8);
			}
		}
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		if(this != Block.stairSingle && this != mod_FarnAnnoyanceFixMP.upperStair) {
			return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
		} else if(i5 != 1 && i5 != 0 && !super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5)) {
			return false;
		} else {
			int i6 = i2 + this.offsetsXForSide[this.faceToSide[i5]];
			int i7 = i3 + this.offsetsYForSide[this.faceToSide[i5]];
			int i8 = i4 + this.offsetsZForSide[this.faceToSide[i5]];
			boolean z9 = iBlockAccess1.getBlockId(i6, i7, i8) == mod_FarnAnnoyanceFixMP.upperStair.blockID;
			return !z9 ? (i5 == 1 ? true : (i5 == 0 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID)) : (i5 == 0 ? true : (i5 == 1 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID));
		}
	}
}
