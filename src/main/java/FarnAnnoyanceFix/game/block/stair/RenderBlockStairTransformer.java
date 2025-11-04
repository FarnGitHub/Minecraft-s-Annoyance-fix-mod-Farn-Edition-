package FarnAnnoyanceFix.game.block.stair;

import FarnAnnoyanceFix.FarnAnnoyanceFixCore;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CShadow;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.annotations.injection.COverride;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.mod_FarnAnnoyanceFix;

@CTransformer(RenderBlocks.class)
public class RenderBlockStairTransformer {

    @CShadow
    public IBlockAccess blockAccess;

    RenderBlocks annoyancefix_self_stair;

    @CInject(method="renderBlockStairs", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_renderBlockStairs(Block block, int i, int j, int k, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.upperstair) {
            if(annoyancefix_self_stair == null) {
                annoyancefix_self_stair = (RenderBlocks) (Object)this;
            }
            callback.setReturnValue(FarnAnnoyanceFixCore.instance.renderBlockStairsWithUpperVariant(annoyancefix_self_stair, block, i, j, k, blockAccess));
        }
    }
}
