package fr.assaderie.mobs.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.assaderie.mobs.AssaderieMobs;
import fr.assaderie.mobs.config.AllEntityConfig;
import fr.assaderie.mobs.managers.ConfigZombieManager;
import fr.assaderie.mobs.managers.ConfigZombieManager.ZombieConfig;
import fr.assaderie.mobs.utils.TexturesZombie;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class ZombieFourEntity extends EntityZombie {

    private ZombieConfig config;
    private static ForgeChunkManager.Ticket chunkTicket;

    public ZombieFourEntity(World world) {
        super(world);

        // Si enableZombieConfig est false, utiliser la configuration globale
        if (!AllEntityConfig.enableZombieConfig) {
            this.moveSpeed = AllEntityConfig.globalSpeed;
            this.setEntityHealth(AllEntityConfig.globalHealth);
            config = new ZombieConfig(
            	    AllEntityConfig.globalSpeed,
            	    AllEntityConfig.globalHealth,
            	    (int) AllEntityConfig.globalDamage,
            	    AllEntityConfig.sunDamageEnabled,
            	    AllEntityConfig.dropEnabled,
            	    AllEntityConfig.dropItemId,
            	    AllEntityConfig.animalDetectionRadius,
            	    AllEntityConfig.delayNearAnimal,
            	    AllEntityConfig.playerDetectionRadius,
            	    AllEntityConfig.canSpawnInDay,
            	    AllEntityConfig.torchSpawnRadius
            	);
        } else {
            config = ConfigZombieManager.getZombieConfig("ZombieFour");

            // Appliquer les paramètres de la configuration spécifique
            if (config != null) {
                this.moveSpeed = config.speed;
                this.setEntityHealth(config.health);
            } else {
                // Valeurs par défaut si la configuration spécifique est absente
                this.moveSpeed = 0.3F;
                this.setEntityHealth(20);
            }
        }

        this.texture = TexturesZombie.TEXTURE_ZOMBIE_FOUR;

        // Tâches d'IA
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, true));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));

        // Tâches de cible
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));

        this.getNavigator().setCanSwim(true);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
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
        int blockRadius = radius / 2; // Conversion du rayon en blocs

        for (int x = (int) this.posX - blockRadius; x <= (int) this.posX + blockRadius; x++) {
            for (int y = (int) this.posY - blockRadius; y <= (int) this.posY + blockRadius; y++) {
                for (int z = (int) this.posZ - blockRadius; z <= (int) this.posZ + blockRadius; z++) {
                    // Vérifier si le bloc est une torche
                    if (this.worldObj.getBlockId(x, y, z) == Block.torchWood.blockID) {
                        return true; // Une torche est trouvée dans le rayon
                    }
                }
            }
        }
        return false; // Aucune torche trouvée
    }

    private EntityAnimal targetAnimal = null;
    private int ticksNearAnimal = 0;
    private boolean hasAttacked = false;

    @Override
    public void onLivingUpdate() {
        boolean sunDamageEnabled = config != null ? config.sunDamageEnabled : AllEntityConfig.sunDamageEnabled;

        if (!sunDamageEnabled && this.isBurning()) {
            this.extinguish();
        } else if (sunDamageEnabled && this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            handleSunlightDamage();
        }

        double playerDetectionRadius = config != null ? config.playerDetectionRadius : AllEntityConfig.playerDetectionRadius;
        EntityPlayer nearestPlayer = this.worldObj.getClosestPlayerToEntity(this, playerDetectionRadius);

        if (nearestPlayer != null && !nearestPlayer.capabilities.isCreativeMode) {
            this.targetAnimal = null;
            this.setAttackTarget(nearestPlayer);
            this.getNavigator().tryMoveToEntityLiving(nearestPlayer, this.moveSpeed);
            super.onLivingUpdate();
            return;
        }

        // Utiliser animal_detection_radius pour détecter les animaux
        double animalDetectionRadius = config != null ? config.animalDetectionRadius : AllEntityConfig.animalDetectionRadius;
        if (this.targetAnimal == null || this.targetAnimal.isDead) {
            List<EntityAnimal> nearbyAnimals = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, this.boundingBox.expand(animalDetectionRadius, 3.0D, animalDetectionRadius));
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

            int delayNearAnimal = config != null ? config.delayNearAnimal : AllEntityConfig.delayNearAnimal;
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



    @Override
    public void setFire(int seconds) {
        boolean sunDamageEnabled = config != null ? config.sunDamageEnabled : AllEntityConfig.sunDamageEnabled;
        if (sunDamageEnabled) {
            super.setFire(seconds);
        }
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
        return config != null ? (int) config.damage : (int) AllEntityConfig.globalDamage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTexture() {
        return this.texture;
    }

    @Override
    protected int getDropItemId() {
        boolean dropEnabled = config != null ? config.dropEnabled : AllEntityConfig.dropEnabled;
        if (dropEnabled) {
            return config != null ? config.dropItemId : AllEntityConfig.dropItemId;
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean canDespawn() {
        return true; // Peut disparaître
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD; // Attribut mort-vivant
    }
    
    protected String getLivingSound() {
        return "zombie.say";
    }

    protected String getHurtSound() {
        return "zombie.hurt";
    }

    protected String getDeathSound() {
        return "zombie.death";
    }
    
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4) {
        this.playSound("zombie.step", 0.15F, 1.0F);
    }

    @Override
    public void dealFireDamage(int amount) {
        boolean sunDamageEnabled = config != null ? config.sunDamageEnabled : AllEntityConfig.sunDamageEnabled;
        if (sunDamageEnabled) {
            super.dealFireDamage(amount);
        }
    }
    
    @Override
    public void onDeath(DamageSource cause) {
        // Release the chunk ticket when the entity dies
        if (chunkTicket != null) {
            ForgeChunkManager.releaseTicket(chunkTicket);
            chunkTicket = null;
        }
        super.onDeath(cause);
    }
}
