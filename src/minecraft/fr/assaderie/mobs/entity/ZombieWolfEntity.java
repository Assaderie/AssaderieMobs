package fr.assaderie.mobs.entity;

import java.util.List;
import java.util.Random;

import ZombieAwareness.ZombieAwareness;
import fr.assaderie.mobs.ai.CustomEntityAILeapAtTarget;
import fr.assaderie.mobs.config.AllDoganWolfConfig;
import fr.assaderie.mobs.config.AllEntityConfig;
import fr.assaderie.mobs.managers.ConfigDogAndWolfManager;
import fr.assaderie.mobs.managers.ConfigDogAndWolfManager.DogAndWolfConfig;
import fr.assaderie.mobs.utils.TexturesZombie;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ZombieWolfEntity extends EntityMob {

    private Random random = new Random();
    private DogAndWolfConfig config;

    private boolean hasHowled = false;
    private boolean isHowling = false;
    private static final int HOWLING_DURATION = 60; // Durée du hurlement en ticks (3 secondes)
    private int howlingTimer = 0; // Timer de hurlement

    private static final float HOWL_HEAD_ANGLE = 60.0F;

    public ZombieWolfEntity(World world) {
        super(world);

        if (!AllDoganWolfConfig.enableDogConfig) {
            this.moveSpeed = AllDoganWolfConfig.globalSpeed;
            this.setEntityHealth(AllDoganWolfConfig.globalHealth);
            config = new DogAndWolfConfig(
                    AllDoganWolfConfig.globalSpeed,
                    AllDoganWolfConfig.globalHealth,
                    (int) AllDoganWolfConfig.globalDamage,
                    AllDoganWolfConfig.sunDamageEnabled,
                    AllDoganWolfConfig.dropEnabled,
                    AllDoganWolfConfig.dropItemId,
                    AllDoganWolfConfig.animalDetectionRadius,
                    AllDoganWolfConfig.delayNearAnimal,
                    AllDoganWolfConfig.playerDetectionRadius,
                    AllDoganWolfConfig.canSpawnInDay,
                    AllDoganWolfConfig.torchSpawnRadius
            );
        } else {
            config = ConfigDogAndWolfManager.getDogAndWolfConfig("ZombieWolf");
            if (config != null) {
                this.moveSpeed = config.speed;
                this.setEntityHealth(config.health);
            } else {
                this.moveSpeed = 0.3F;
                this.setEntityHealth(20);
            }
        }

        this.texture = TexturesZombie.TEXTURE_ZOMBIE_WOLF;
        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new CustomEntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }
    
    public boolean isHowling() {
        return isHowling;
    }

    public void startHowling() {
        isHowling = true;
        howlingTimer = HOWLING_DURATION;
        howlAtMoon();
    }
    
    
    private EntityAnimal targetAnimal = null;
    private int ticksNearAnimal = 0;
    private boolean hasAttacked = false;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        // Gestion du hurlement si le timer est actif
        if (howlingTimer > 0) {
            howlingTimer--;
            if (howlingTimer == 0) {
                isHowling = false; 
                setSitting(false);  // Fin du hurlement
            } else {
                this.getNavigator().clearPathEntity(); // Arrête complètement le mouvement pendant le hurlement
            }
            return; 
        }

        // Détection du joueur la nuit et déclenchement du hurlement
        if (!this.worldObj.isDaytime()) {
            double playerDetectRadius = config != null ? config.playerDetectionRadius : AllDoganWolfConfig.playerDetectionRadius;
            EntityPlayer nearestPlayer = this.worldObj.getClosestPlayerToEntity(this, playerDetectRadius);

            if (nearestPlayer != null && this.canEntityBeSeen(nearestPlayer) && !nearestPlayer.capabilities.isCreativeMode) {
                if (!hasHowled) {
                    startHowling(); 
                    hasHowled = true;
                    return;
                } else {
                    this.setAttackTarget(nearestPlayer);
                    this.getNavigator().tryMoveToEntityLiving(nearestPlayer, this.moveSpeed);
                    return;
                }
            }
        }

        // Comportement normal pour attaquer les animaux si aucun joueur n'est détecté la nuit
        manageAnimalAttackBehavior();
        //handleZombieAwarnessBehaviors();
    }

    private void manageAnimalAttackBehavior() {
        if (this.targetAnimal == null || this.targetAnimal.isDead) {
            List<EntityAnimal> nearbyAnimals = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, this.boundingBox.expand(5.0D, 3.0D, 5.0D));
            if (!nearbyAnimals.isEmpty()) {
                this.targetAnimal = nearbyAnimals.get(0);
                this.ticksNearAnimal = 0;
                this.hasAttacked = false;
            }
        }

        if (this.targetAnimal != null) {
            double distanceToTarget = this.getDistanceToEntity(this.targetAnimal);

            if (distanceToTarget <= 1.5D) { 
                this.ticksNearAnimal++;
                this.getLookHelper().setLookPositionWithEntity(this.targetAnimal, 10.0F, 10.0F);
            } else {
                this.ticksNearAnimal = 0;
                this.getNavigator().tryMoveToEntityLiving(this.targetAnimal, this.moveSpeed);
            }

            int delayNearAnimal = config != null ? config.delayNearAnimal : AllDoganWolfConfig.delayNearAnimal;
            if (this.ticksNearAnimal >= delayNearAnimal && !this.hasAttacked && this.canEntityBeSeen(this.targetAnimal)) {
                this.hasAttacked = true;
            }

            if (this.hasAttacked && distanceToTarget <= 1.5D) {
                this.attackEntityAsMob(this.targetAnimal);
                if (this.targetAnimal.isDead) {
                    this.targetAnimal = null;
                    this.hasAttacked = false;
                }
            }
        }
    }

    private void howlAtMoon() {
        this.playSound("wolf.wolfhowl", 1.0F, 1.0F);
        setSitting(true);
        this.rotationPitch = HOWL_HEAD_ANGLE;
    }

    public void setSitting(boolean sitting) {
        this.rotationPitch = sitting ? HOWL_HEAD_ANGLE : 0.0F;
    }

    private void handleZombieAwarnessBehaviors() {
        if (ZombieAwareness.awareness_Sound) {
            detectSound();
        }
        if (ZombieAwareness.awareness_Scent) {
            detectScent();
        }
    }

    private void detectSound() {
        List<Entity> nearbyEntities = this.worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(64, 64, 64));
        for (Entity entity : nearbyEntities) {
            if (entity != this && entity instanceof EntityMob && random.nextInt(100) < ZombieAwareness.soundStrength) {
                this.getNavigator().tryMoveToEntityLiving((EntityLiving) entity, 0.3F);
                break;
            }
        }
    }

    private void detectScent() {
        List<Entity> nearbyEntities = this.worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(64, 64, 64));
        for (Entity entity : nearbyEntities) {
            if (entity != this && entity instanceof EntityMob && random.nextInt(100) < ZombieAwareness.scentStrength) {
                this.getNavigator().tryMoveToEntityLiving((EntityLiving) entity, 0.3F);
                break;
            }
       }
    }

    @Override
    public boolean getCanSpawnHere() {
        boolean canSpawnInDay = config != null ? config.canSpawnInDay : AllEntityConfig.canSpawnInDay;
        
        // Prevent spawning during the day if not allowed by configuration
        if (!canSpawnInDay && this.worldObj.isDaytime()) {
            return false;
        }

        int torchRadius = config != null ? config.torchSpawnRadius : AllEntityConfig.torchSpawnRadius;

        // Keep the existing torch proximity check
        if (isNearTorch(torchRadius)) {
            return false;
        }

        // Allow spawning in dark areas like caves or buildings (light level ≤ 7)
        int lightLevel = this.worldObj.getBlockLightValue((int) this.posX, (int) this.posY, (int) this.posZ);
        return lightLevel <= 9;
    }
    
    
    private boolean isNearTorch(int radius) {
        int blockRadius = radius / 2;
        for (int x = (int) this.posX - blockRadius; x <= (int) this.posX + blockRadius; x++) {
            for (int y = (int) this.posY - blockRadius; y <= (int) this.posY + blockRadius; y++) {
                for (int z = (int) this.posZ - blockRadius; z <= (int) this.posZ + blockRadius; z++) {
                    if (this.worldObj.getBlockId(x, y, z) == Block.torchWood.blockID) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void handleSunlightDamage() {
        if (this.worldObj.canBlockSeeTheSky((int) this.posX, (int) this.posY, (int) this.posZ) && this.getBrightness(1.0F) > 0.5F) {
            this.setFire(8);
        }
    }

    @Override
    public int getAttackStrength(Entity entity) {
        return config != null ? (int) config.damage : (int) AllDoganWolfConfig.globalDamage;
    }

    @Override
    protected int getDropItemId() {
        return config != null && config.dropEnabled ? config.dropItemId : AllDoganWolfConfig.dropItemId;
    }

    @Override
    protected void dealFireDamage(int amount) {
        if (config != null && config.sunDamageEnabled) {
            super.dealFireDamage(amount);
        }
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    public void onJump() {
        this.playSound("wolf.attack", 1.0F, 1.0F);
    }

    @Override
    public String getTexture() {
        return this.texture;
    }

    @Override
    protected String getLivingSound() {
        return "wolf.say";
    }

    @Override
    protected String getHurtSound() {
        return "wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "wolf.death";
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4) {
        this.playSound("wolf.step", 0.15F, 1.0F);
    }

    @Override
    public int getMaxHealth() {
        return config != null ? config.health : AllDoganWolfConfig.globalHealth;
    }
}
