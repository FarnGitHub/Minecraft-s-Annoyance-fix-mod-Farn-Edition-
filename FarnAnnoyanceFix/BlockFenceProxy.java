package FarnAnnoyanceFix;

import net.minecraft.src.*;

public class BlockFenceProxy extends BlockFence {
	public BlockFenceProxy(int i1, int i2) {
		super(i1, i2);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return true;
	}
}
