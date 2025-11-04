package FarnAnnoyanceFix.game.block.stair;

import FarnAnnoyanceFix.FarnAnnoyanceFixCore;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.src.*;

import java.util.ArrayList;

@CTransformer(BlockStairs.class)
public abstract class BlockStairTransformer extends Block{

    protected BlockStairTransformer(int i, Block block) {
        super(i, block.blockIndexInTexture, block.blockMaterial);
    }

    @CInject(method="dropBlockAsItemWithChance", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.stairdropthemselves) {
            this.dropBlockAsItem_do(world1, i2, i3, i4, new ItemStack(this.blockID, 1, 0));
            callback.setCancelled(true);
        }
    }

    @CInject(method="onBlockPlacedBy", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.upperstair) {
            int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            MovingObjectPosition mouse = ModLoader.getMinecraftInstance().objectMouseOver;
            float var10 = (float)mouse.hitVec.yCoord - (float)i3;

            if(i6 == 0) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, this.getMetaDataFromHitVec(mouse.sideHit, var10, 2));
            }

            if(i6 == 1) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, this.getMetaDataFromHitVec(mouse.sideHit, var10, 1));
            }

            if(i6 == 2) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, this.getMetaDataFromHitVec(mouse.sideHit, var10, 3));
            }

            if(i6 == 3) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, this.getMetaDataFromHitVec(mouse.sideHit, var10, 0));
            }
            callback.setCancelled(true);
        }
    }

    @CInject(method="getCollidingBoundingBoxes", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.upperstair) {
            int i7 = world1.getBlockMetadata(i2, i3, i4);
            if(i7 == 0) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 1) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 2) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 3) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 3) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 4) {
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 5) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 6) {
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            } else if(i7 == 7) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
                this.setBlockBounds(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                this.annoyancefix_getCustomCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            }

            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            callback.setCancelled(true);
        }
    }

    public void annoyancefix_getCustomCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
        AxisAlignedBB axisAlignedBB7 = this.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
        if(axisAlignedBB7 != null && axisAlignedBB5.intersectsWith(axisAlignedBB7)) {
            arrayList6.add(axisAlignedBB7);
        }

    }
    public int getMetaDataFromHitVec(int hit, float hitvec, int metadata) {
        return hit != 0 && (hit == 1 || (double)hitvec <= 0.5D) ? metadata : metadata | 4;
    }

}
