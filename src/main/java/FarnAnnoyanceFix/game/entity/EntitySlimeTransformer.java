package FarnAnnoyanceFix.game.entity;

import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CRedirect;
import net.minecraft.src.EntitySlime;

@CTransformer(EntitySlime.class)
public class EntitySlimeTransformer {


    @CRedirect(method="setEntityDead", target = @CTarget(value="FIELD", target = "Lnet/minecraft/src/EntitySlime;health:I"))
    public int fixHealth(EntitySlime slime) {
        return Math.max(slime.health, 0);
    }
}
