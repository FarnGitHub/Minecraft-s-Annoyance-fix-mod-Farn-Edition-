package FarnAnnoyanceFix.game;

import net.minecraft.src.*;

public class BlockFenceProxy extends BlockFence {
	public BlockFenceProxy(int i1, int i2) {
		super(i1, i2);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3, i4);
		return i5 == 0 || blocksList[i5].blockMaterial.getIsGroundCover();
	}
}
