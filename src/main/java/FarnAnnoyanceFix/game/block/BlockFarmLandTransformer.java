package FarnAnnoyanceFix.game.block;

import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.src.*;

@CTransformer(BlockFarmland.class)
public class BlockFarmLandTransformer {

    @CInject(method="onEntityWalking", target = @CTarget("HEAD"))
    public void annoyancefix_onEntityWalking(World world, int i, int j, int k, Entity entity, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.nofarmlandtrampling) {
            callback.setCancelled(true);
        }
    }
}
