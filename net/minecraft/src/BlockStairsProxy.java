package net.minecraft.src;

public class BlockStairsProxy extends BlockStairs {
	public BlockStairsProxy(int i1, Block block2) {
		super(i1, block2);
	}

	public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
		this.doNothing();
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		this.doNothing();
	}

	public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
		this.dropBlockAsItem_do(world1, i2, i3, i4, new ItemStack(this.blockID, 1, 0));
	}

	public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
		this.doNothing();
	}

	private void doNothing() {
	}
}
