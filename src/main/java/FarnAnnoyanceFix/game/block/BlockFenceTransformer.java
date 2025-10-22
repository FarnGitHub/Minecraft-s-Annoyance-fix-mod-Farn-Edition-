package FarnAnnoyanceFix.game.block;

import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.src.*;

@CTransformer(BlockFence.class)
public class BlockFenceTransformer {

    @CInject(method="canPlaceBlockAt", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_canPlaceBlockAt(World world1, int i2, int i3, int i4, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.fencefix) {
            int i5 = world1.getBlockId(i2, i3, i4);
            callback.setReturnValue(i5 == 0 || Block.blocksList[i5].blockMaterial.getIsGroundCover());
        }
    }
}
