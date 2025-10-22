package FarnAnnoyanceFix.game.entity;

import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CShadow;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.annotations.injection.CRedirect;
import net.minecraft.src.*;

@CTransformer(EntityBoat.class)
public abstract class EntityBoatTransformer extends Entity{

    @CShadow
    public int boatCurrentDamage;
    @CShadow
    public int boatTimeSinceHit;
    @CShadow
    public int boatRockDirection;

    public EntityBoatTransformer(World world) {
        super(world);
    }

    public EntityBoatTransformer(World world, double d, double e, double f) {
        this(world);
    }

    @CInject(method="attackEntityFrom", target = @CTarget("HEAD"), cancellable = true)
    public void annoyancefix_attackEntityFrom(Entity entity1, int i2, InjectionCallback callback) {
        if(mod_FarnAnnoyanceFix.boatfix) {
            if(!this.worldObj.multiplayerWorld && !this.isDead) {
                this.boatRockDirection = -this.boatRockDirection;
                this.boatTimeSinceHit = 10;
                this.boatCurrentDamage += i2 * 10;
                this.setBeenAttacked();
                if(this.boatCurrentDamage > 40) {
                    if(this.riddenByEntity != null) {
                        this.riddenByEntity.mountEntity(this);
                    }
                    this.dropItemWithOffset(Item.boat.shiftedIndex, 1, 0.0F);
                    this.setEntityDead();
                }
            }
            callback.setReturnValue(true);
        }
    }

    @CRedirect(method="onUpdate", target = @CTarget(value="GETFIELD", target="Lnet/minecraft/src/EntityBoat;isCollidedHorizontally:Z"))
    public boolean annoyancefix_preventBoatBreak(EntityBoat theBoat) {
        return !mod_FarnAnnoyanceFix.boatfix && theBoat.isCollidedHorizontally;
    }

    @CInject(method="interact", target = @CTarget(value="INVOKE", target="Lnet/minecraft/src/EntityPlayer;mountEntity(Lnet/minecraft/src/Entity;)V"))
    public void annoyancefix_setPosHigher(EntityPlayer var1, InjectionCallback callback) {
        if(var1.ridingEntity == null) {
            var1.setPosition(var1.posX, var1.posY+0.01f, var1.posZ);
        }
    }
}
