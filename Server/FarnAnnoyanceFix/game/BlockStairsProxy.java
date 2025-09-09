package FarnAnnoyanceFix.game;

import java.util.ArrayList;

import FarnAnnoyanceFix.FarnAnnoyanceFixCore;
import net.minecraft.src.*;

public class BlockStairsProxy extends BlockStairs {
	private int capturedSide = 0;

	public BlockStairsProxy(int i1, Block block2) {
		super(i1, block2);
	}

	public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
		return;
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		return;
	}

	public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
		return;
	}

	public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
		this.dropBlockAsItem_do(world1, i2, i3, i4, new ItemStack(this.blockID, 1, 0));
	}

	public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		if(mod_FarnAnnoyanceFixMP.upperstair) {
			int i7 = world1.getBlockMetadata(i2, i3, i4);
			if(i7 == 0) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 1) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 2) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 3) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 3) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 4) {
				this.setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 5) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 6) {
				this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			} else if(i7 == 7) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
				this.setBlockBounds(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
				this.getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			}

			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
		}
	}

	@Override
	public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
		capturedSide = i5;
		super.onBlockPlaced(world1, i2, i3, i4, i5);
	}

	public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
		if(mod_FarnAnnoyanceFixMP.upperstair) {
			int facing = MathHelper.floor_double((entityLiving5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			MovingObjectPosition mouse = FarnAnnoyanceFixCore.getPlayerMouse((EntityPlayer) entityLiving5, world1);
			float hitY = (float) mouse.hitVec.yCoord - (float) i3;

			int meta;
			if (facing == 0) meta = getMetaDataFromHitVec(capturedSide, hitY, 2);
			else if (facing == 1) meta = getMetaDataFromHitVec(capturedSide, hitY, 1);
			else if (facing == 2) meta = getMetaDataFromHitVec(capturedSide, hitY, 3);
			else meta = getMetaDataFromHitVec(capturedSide, hitY, 0);
			world1.setBlockMetadataWithNotify(i2, i3, i4, meta);
		} else {
			super.onBlockPlacedBy(world1, i2, i3, i4, entityLiving5);
		}
	}

	public void getCustomCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		AxisAlignedBB axisAlignedBB7 = this.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
		if(axisAlignedBB7 != null && axisAlignedBB5.intersectsWith(axisAlignedBB7)) {
			arrayList6.add(axisAlignedBB7);
		}

	}

	public int getMetaDataFromHitVec(int hit, float hitvec, int metadata) {
		return capturedSide == 0 || capturedSide != 1 && hitvec > 0.5F ? metadata | 4: metadata;
	}

}
