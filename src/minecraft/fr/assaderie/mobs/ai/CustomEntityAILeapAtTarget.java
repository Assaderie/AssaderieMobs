package fr.assaderie.mobs.ai;

import fr.assaderie.mobs.entity.ZombieWolfEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class CustomEntityAILeapAtTarget extends EntityAIBase {

    private final ZombieWolfEntity wolf;
    private EntityLiving leapTarget;
    private final float leapMotionY;

    public CustomEntityAILeapAtTarget(ZombieWolfEntity wolf, float leapMotionY) {
        this.wolf = wolf;
        this.leapMotionY = leapMotionY;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        this.leapTarget = this.wolf.getAttackTarget();

        if (this.leapTarget == null) {
            return false;
        } else {
            double distanceToTarget = this.wolf.getDistanceSqToEntity(this.leapTarget);
            return distanceToTarget >= 4.0D && distanceToTarget <= 16.0D && this.wolf.onGround && this.wolf.getRNG().nextInt(5) == 0;
        }
    }

    @Override
    public boolean continueExecuting() {
        return !this.wolf.onGround;
    }

    @Override
    public void startExecuting() {
        double dX = this.leapTarget.posX - this.wolf.posX;
        double dZ = this.leapTarget.posZ - this.wolf.posZ;
        float distance = MathHelper.sqrt_double(dX * dX + dZ * dZ);
        
        this.wolf.motionX += dX / (double)distance * 0.5D * 0.8D + this.wolf.motionX * 0.2D;
        this.wolf.motionZ += dZ / (double)distance * 0.5D * 0.8D + this.wolf.motionZ * 0.2D;
        this.wolf.motionY = this.leapMotionY;

        // Joue le son d'attaque spécifique au moment du saut
        this.wolf.playSound("wolf.attack", 1.0F, 1.0F);
    }
}
