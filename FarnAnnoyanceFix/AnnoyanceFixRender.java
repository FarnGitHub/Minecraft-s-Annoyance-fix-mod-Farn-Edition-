package FarnAnnoyanceFix;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

public class AnnoyanceFixRender {
	public static final AnnoyanceFixRender instance = new AnnoyanceFixRender();

	public void renderBlockSlabInventory(RenderBlocks renderer, Block uu1, int i1) {
		Tessellator nw1 = Tessellator.instance;
		uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		nw1.startDrawingQuads();
		nw1.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderBottomFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(0, i1));
		nw1.draw();
		nw1.startDrawingQuads();
		nw1.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderTopFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(1, i1));
		nw1.draw();
		nw1.startDrawingQuads();
		nw1.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderEastFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(2, i1));
		nw1.draw();
		nw1.startDrawingQuads();
		nw1.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderWestFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(3, i1));
		nw1.draw();
		nw1.startDrawingQuads();
		nw1.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderNorthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(4, i1));
		nw1.draw();
		nw1.startDrawingQuads();
		nw1.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderSouthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSideAndMetadata(5, i1));
		nw1.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	public boolean renderBlockSlabWithUpperVariant(RenderBlocks renderer, Block uu1, int i1, int j1, int k1) {
		boolean flag = false;
		int l1 = ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(i1, j1, k1);
		if(l1 < 4) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else {
			uu1.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		}

		return flag;
	}

	public void renderBlockStairInventory(RenderBlocks renderer, Block uu1) {
		Tessellator nw1 = Tessellator.instance;
		for(int i9 = 0; i9 < 2; ++i9) {
			if(i9 == 0) {
				uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			}

			if(i9 == 1) {
				uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			}

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderBottomFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(0));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderTopFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(1));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderEastFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(2));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderWestFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(3));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderNorthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(4));
			nw1.draw();
			nw1.startDrawingQuads();
			nw1.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderSouthFace(uu1, 0.0D, 0.0D, 0.0D, uu1.getBlockTextureFromSide(5));
			nw1.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	public boolean renderBlockStairsWithUpperVariant(RenderBlocks renderer, Block uu1, int i1, int j1, int k1) {
		boolean flag = false;
		int l1 = ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(i1, j1, k1);
		if(l1 == 0) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 1) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 2) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 3) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 4) {
			uu1.setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 5) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 6) {
			uu1.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		} else if(l1 == 7) {
			uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			uu1.setBlockBounds(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(uu1, i1, j1, k1);
			flag = true;
		}

		uu1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return flag;
	}

}