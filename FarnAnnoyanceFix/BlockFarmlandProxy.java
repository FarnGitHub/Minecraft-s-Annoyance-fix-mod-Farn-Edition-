package FarnAnnoyanceFix;

import net.minecraft.src.*;

import java.util.Random;

public class BlockFarmlandProxy extends BlockFarmland {
	protected BlockFarmlandProxy(int i1) {
		super(i1);
	}

	public BlockFarmlandProxy(int i1, String none) {
		super(i1);
	}

	public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
		this.doNothing();
	}

	private void doNothing() {}
}
