package FarnAnnoyanceFix.game;

import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.lenni0451.classtransform.annotations.injection.CRedirect;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.World;
import net.minecraft.src.mod_FarnAnnoyanceFix;

import java.util.List;

@CTransformer(World.class)
public class WorldTransformer {

    @CRedirect(method="tick", target = @CTarget(value="INVOKE", target="Lnet/minecraft/src/SpawnerAnimals;performSleepSpawning(Lnet/minecraft/src/World;Ljava/util/List;)Z"))
    public boolean annoyancefix_doNotDoNightMare(World world, List<EntityPlayer>plr) {
        if(mod_FarnAnnoyanceFix.noNightmare) {
            return false;
        }
        return SpawnerAnimals.performSleepSpawning(world, plr);
    }
}
