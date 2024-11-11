package fr.assaderie.mobs.managers;

import java.io.File;
import java.util.HashMap;

import fr.assaderie.mobs.config.AllDoganWolfConfig;
import fr.assaderie.mobs.config.AllEntityConfig;
import net.minecraftforge.common.Configuration;

public class ConfigDogAndWolfManager {

    private static final HashMap<String, DogAndWolfConfig> DogAndWolfConfig = new HashMap<String, DogAndWolfConfig>();

    public static class DogAndWolfConfig {
        public float speed;
        public int health;
        public int damage;
        public boolean sunDamageEnabled;
        public boolean dropEnabled;
        public int dropItemId;
        public int distanceBlock;
        public int delayNearAnimal;
        public int playerDetectionRadius;
        public boolean canSpawnInDay;
        public int torchSpawnRadius;

        public DogAndWolfConfig(float speed, int health, int damage, boolean sunDamageEnabled, boolean dropEnabled,
                                int dropItemId, int distanceBlock, int delayNearAnimal, int playerDetectionRadius, boolean canSpawnInDay, int torchSpawnRadius) {
            this.speed = speed;
            this.health = health;
            this.damage = damage;
            this.sunDamageEnabled = sunDamageEnabled;
            this.dropEnabled = dropEnabled;
            this.dropItemId = dropItemId;
            this.distanceBlock = distanceBlock;
            this.delayNearAnimal = delayNearAnimal;
            this.playerDetectionRadius = playerDetectionRadius;
            this.canSpawnInDay = canSpawnInDay;
            this.torchSpawnRadius = torchSpawnRadius;
        }
    }

    public static void loadDogAndWolfConfigs(File configDir) {
        if (!AllDoganWolfConfig.enableDogConfig) {
            return; 
        }

        File zombieDogAndWolf = new File(configDir, "Config_Dog_And_Wolf");
        if (!zombieDogAndWolf.exists()) {
            zombieDogAndWolf.mkdirs();
        }

        loadDogAndWolfConfig("ZombieDog", zombieDogAndWolf);
        loadDogAndWolfConfig("ZombieWolf", zombieDogAndWolf);
    }

    private static void loadDogAndWolfConfig(String name, File configDir) {
        File zombieDogAndWolf = new File(configDir, name + ".cfg");
        Configuration config = new Configuration(zombieDogAndWolf);

        try {
            config.load();

            float speed = (float) config.get(Configuration.CATEGORY_GENERAL, "speed", 0.3F, "Speed of the wolf or dog \n Vitesse du loup ou du chien").getDouble(0.3F);
            int health = config.get(Configuration.CATEGORY_GENERAL, "health", 20, "Health of the wolf or dog \n Santé du loup ou du chien").getInt(20);
            int damage = config.get(Configuration.CATEGORY_GENERAL, "damage", 5, "Damage dealt by the wolf or dog \n Dégâts infligés par le loup ou le chien").getInt(5);

            boolean sunDamageEnabled = config.get(Configuration.CATEGORY_GENERAL, "burn_in_sun", false, "Does the wolf or dog take damage in sunlight \n Le loup ou le chien subit des dommages au soleil").getBoolean(false);
            boolean itemDropEnabled = config.get(Configuration.CATEGORY_GENERAL, "item_drop", true, "Are item drops enabled \n Activation des objets abandonnés (drops)").getBoolean(true);
            int dropItemId = config.get(Configuration.CATEGORY_GENERAL, "drop_item_id", 367, "ID of the item dropped by the wolf or dog \n ID de l'objet abandonné par le loup ou le chien").getInt(367);

            int animalDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "animal_detection_radius", 5, "Block detection radius for animals \n Rayon de détection des animaux en blocs").getInt(5);

            // Convertir delay_before_attack_animal en ticks
            String delayBeforeAttackAnimalStr = config.get(Configuration.CATEGORY_GENERAL, "delay_before_attack_animal", "20s", "Delay before attacking an animal (in ticks, or use s/m/h/d) \n Délai avant d'attaquer un animal (en ticks ou s/m/h/d)").value;
            int delayBeforeAttackAnimal = convertTimeToTicks(delayBeforeAttackAnimalStr);

            int playerDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "player_detection_radius", 16, "Player detection radius (in blocks) \n Rayon de détection du joueur en blocs").getInt(16);

            boolean canSpawnInDay = config.get(Configuration.CATEGORY_GENERAL, "can_spawn_in_day", true, "Can the wolf or dog spawn during the day \n Le loup ou le chien peut apparaître le jour").getBoolean(true);
            int torchProtectionRadius = config.get(Configuration.CATEGORY_GENERAL, "torch_protection_radius", 100, "Radius around torches preventing spawn \n Rayon autour des torches empêchant le spawn").getInt(100);

            DogAndWolfConfig.put(name, new DogAndWolfConfig(speed, health, damage, sunDamageEnabled, itemDropEnabled, dropItemId, animalDetectionRadius, delayBeforeAttackAnimal, playerDetectionRadius, canSpawnInDay, torchProtectionRadius));

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la configuration pour " + name + " : " + e.getMessage());
        } finally {
            config.save();
        }
    }

    public static DogAndWolfConfig getDogAndWolfConfig(String name) {
        return DogAndWolfConfig.get(name);
    }
    
    // Méthode pour convertir les unités de temps en ticks
    private static int convertTimeToTicks(String time) {
        int ticks = 0;
        if (time.endsWith("s")) {  // Secondes
            ticks = Integer.parseInt(time.replace("s", "")) * 20;
        } else if (time.endsWith("m")) {  // Minutes
            ticks = Integer.parseInt(time.replace("m", "")) * 1200;
        } else if (time.endsWith("h")) {  // Heures
            ticks = Integer.parseInt(time.replace("h", "")) * 72000;
        } else if (time.endsWith("d")) {  // Jours
            ticks = Integer.parseInt(time.replace("d", "")) * 1728000;
        } else {
            ticks = Integer.parseInt(time); // Par défaut, en ticks
        }
        return ticks;
    }
}
