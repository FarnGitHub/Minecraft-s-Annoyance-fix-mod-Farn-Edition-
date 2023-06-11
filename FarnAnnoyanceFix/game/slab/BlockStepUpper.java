package FarnAnnoyanceFix.game.slab;

import java.util.ArrayList;
import net.minecraft.src.*;

import java.util.Random;

public class BlockStepUpper extends BlockStepProxy {

	public BlockStepUpper(int i1) {
		super(i1, false);
	}

	public void setBlockBoundsBasedOnState (IBlockAccess blockAccess, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
	}

	public int quantityDropped(Random random1) {
		return 1;
	}
}
