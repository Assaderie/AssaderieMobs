package fr.assaderie.mobs.entity;

import java.util.List;
import java.util.Random;

import ZombieAwareness.ZombieAwareness;
//import ZombieAwareness.ZombieAwareness;
import fr.assaderie.mobs.AssaderieMobs;
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

public class ZombieDogEntity extends EntityMob {

    private Random random = new Random();
    private DogAndWolfConfig config;

    // Variables pour la logique d'aboiement
    private int barkCount = 0;
    private long lastBarkTime = 0;
    private int currentBarkInterval = BARK_INTERVAL + random.nextInt(20); // Intervalles d'aboiement variant entre 20 et 40 ticks
    private static final int BARK_INTERVAL = 20; // Intervalle de base en ticks (1 seconde)

    public ZombieDogEntity(World world) {
        super(world);

        this.moveSpeed = 0.3F;
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
            config = ConfigDogAndWolfManager.getDogAndWolfConfig("ZombieDog");
            if (config != null) {
                this.moveSpeed = config.speed;
                this.setEntityHealth(config.health);
            } else {
                this.moveSpeed = 0.3F;
                this.setEntityHealth(20);
            }
        }

        this.texture = TexturesZombie.TEXTURE_ZOMBIE_DOG;
        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }

    private EntityAnimal targetAnimal = null;
    private int ticksNearAnimal = 0;
    private boolean hasAttacked = false;

    @Override
    public void onLivingUpdate() {
        if (AssaderieMobs.isZombieAwarnessLoaded) {
            //handleZombieAwarnessBehaviors();
        }

        boolean sunDamageEnabled = config != null ? config.sunDamageEnabled : AllDoganWolfConfig.sunDamageEnabled;

        if (!sunDamageEnabled && this.isBurning()) {
            this.extinguish();
        } else if (sunDamageEnabled && this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            handleSunlightDamage();
        }

        double playerDetectRadius = config != null ? config.playerDetectionRadius : AllDoganWolfConfig.playerDetectionRadius;
        EntityPlayer nearestPlayer = this.worldObj.getClosestPlayerToEntity(this, playerDetectRadius);

        if (nearestPlayer != null && !nearestPlayer.capabilities.isCreativeMode) {
            if (barkCount == 0) {
                barkCount = random.nextInt(3) + 3;  // Aléatoire entre 3 et 5 aboiements
            }

            // Aboie et s'approche graduellement du joueur
            if (barkCount > 0) {
                if (this.worldObj.getTotalWorldTime() - lastBarkTime >= currentBarkInterval) {
                    float volume = 0.8F + random.nextFloat() * 0.4F;
                    this.playSound("dog.bark", volume, 1.0F);
                    lastBarkTime = this.worldObj.getTotalWorldTime();
                    barkCount--;

                    currentBarkInterval = BARK_INTERVAL + random.nextInt(20);
                    this.getNavigator().tryMoveToEntityLiving(nearestPlayer, (float) (this.moveSpeed * 0.5));
                }
            } else {
                this.setAttackTarget(nearestPlayer);
                this.getNavigator().tryMoveToEntityLiving(nearestPlayer, this.moveSpeed);
            }

            super.onLivingUpdate();
            return;
        }

        // Comportement de poursuite des animaux si aucun joueur n'est détecté
        if (this.targetAnimal == null || this.targetAnimal.isDead) {
            List<EntityAnimal> nearbyAnimals = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, this.boundingBox.expand(5.0D, 3.0D, 5.0D));
            if (!nearbyAnimals.isEmpty()) {
                this.targetAnimal = nearbyAnimals.get(0);
                this.ticksNearAnimal = 0;
                this.hasAttacked = false;
            }
        }

        if (this.targetAnimal != null) {
            double distanceToTarget = this.getDistanceToEntity(this.targetAnimal); // distance directe sans hitbox

            if (distanceToTarget <= 1.5D) { // 1.5 blocs de distance d'attaque fixe
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

            if (this.hasAttacked && distanceToTarget <= 1.5D) { // attaque lorsque suffisamment proche
                this.attackEntityAsMob(this.targetAnimal);
                if (this.targetAnimal.isDead) {
                    this.targetAnimal = null;
                    this.hasAttacked = false;
                }
            }
        }

        super.onLivingUpdate();
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
        if (this.worldObj.canBlockSeeTheSky((int) this.posX, (int) this.posY, (int) this.posZ)) {
            float brightness = this.getBrightness(1.0F);
            if (brightness > 0.5F) {
                this.setFire(8);
            }
        }
    }

    @Override
    public int getAttackStrength(Entity entity) {
    	this.playSound("dog.dogattack", 1.0F, 1.0F);
        return config != null ? (int) config.damage : (int) AllDoganWolfConfig.globalDamage;
    }

    @Override
    protected int getDropItemId() {
        boolean dropEnabled = config != null ? config.dropEnabled : AllDoganWolfConfig.dropEnabled;
        if (dropEnabled) {
            return config != null ? config.dropItemId : AllDoganWolfConfig.dropItemId;
        } else {
            return -1;
        }
    }

    @Override
    protected void dealFireDamage(int amount) {
        boolean sunDamageEnabled = config != null ? config.sunDamageEnabled : AllDoganWolfConfig.sunDamageEnabled;
        if (sunDamageEnabled) {
            super.dealFireDamage(amount);
        }
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public String getTexture() {
        return this.texture;
    }

    @Override
    protected String getLivingSound() {
        return "dog.say";
    }

    @Override
    protected String getHurtSound() {
        return "dog.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "dog.death";
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4) {
        this.playSound("dog.step", 0.15F, 1.0F);
    }

    @Override
    public int getMaxHealth() {
        return config != null ? config.health : AllDoganWolfConfig.globalHealth;
    }
}
