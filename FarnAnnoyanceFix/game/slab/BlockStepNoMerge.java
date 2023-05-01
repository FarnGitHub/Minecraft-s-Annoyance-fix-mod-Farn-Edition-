package FarnAnnoyanceFix.game.slab;

import java.util.ArrayList;
import net.minecraft.src.*;

import java.util.Random;

public class BlockStepNoMerge extends BlockStep {

	public BlockStepNoMerge(int i1, boolean z2) {
		super(i1, z2);
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		return;
	}
}
