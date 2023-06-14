package FarnAnnoyanceFix.game.slab;

import java.util.Random;

public class BlockStepUpper extends BlockStepProxy {
	public BlockStepUpper(int i1) {
		super(i1, true);
		this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		this.setLightOpacity(0);
	}

	public int quantityDropped(Random random1) {
		return 1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
}
