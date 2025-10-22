package FarnAnnoyanceFix.game.block.slab;

import FarnAnnoyanceFix.FarnAnnoyanceFixCore;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CShadow;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.src.*;

@CTransformer(BlockStep.class)
public class BlockStepTransformer extends Block {
    private int[] faceToSide = new int[]{1, 0, 3, 2, 5, 4};
    private int[] offsetsXForSide = new int[]{0, 0, 0, 0, -1, 1};
    private int[] offsetsYForSide = new int[]{-1, 1, 0, 0, 0, 0};
    private int[] offsetsZForSide = new int[]{0, 0, -1, 1, 0, 0};

    @CShadow
    private boolean blockType;

    public BlockStepTransformer(int i, boolean bl) {
        super(i, 6, Material.rock);
    }

    @CInject(method="onBlockAdded", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_onBlockAdded(World world, int i, int j, int k, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.slabplacement) {
            callback.setCancelled(true);
        }
    }

    public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
        if(mod_FarnAnnoyanceFix.slabplacement) {
            if(!this.blockType) {
                MovingObjectPosition movingObjectPosition6 = ModLoader.getMinecraftInstance().objectMouseOver;
                float f7 = (float)movingObjectPosition6.hitVec.yCoord - (float)i3;
                if(i5 == 0 || i5 != 1 && f7 > 0.5F) {
                    int i8 = world1.getBlockMetadata(i2, i3, i4);
                    world1.setBlockAndMetadataWithNotify(i2, i3, i4, FarnAnnoyanceFixCore.upperSlab.blockID, i8);
                }
            }
        }
    }

    @CInject(method="shouldSideBeRendered", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.slabplacement) {
            if(this != Block.stairSingle && this != FarnAnnoyanceFixCore.upperSlab) {
                return;
            } else if(i5 != 1 && i5 != 0 && !super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5)) {
                callback.setReturnValue(false);
            } else {
                int i6 = i2 + this.offsetsXForSide[this.faceToSide[i5]];
                int i7 = i3 + this.offsetsYForSide[this.faceToSide[i5]];
                int i8 = i4 + this.offsetsZForSide[this.faceToSide[i5]];
                boolean z9 = iBlockAccess1.getBlockId(i6, i7, i8) == FarnAnnoyanceFixCore.upperSlab.blockID;
                boolean finalvalue =  !z9 ? (i5 == 1 ? true : (i5 == 0 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID)) : (i5 == 0 ? true : (i5 == 1 && super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID));
                callback.setReturnValue(finalvalue);
            }
        }
    }
}
