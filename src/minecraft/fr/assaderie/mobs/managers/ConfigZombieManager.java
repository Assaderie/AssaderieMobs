package fr.assaderie.mobs.managers;

import java.io.File;
import java.util.HashMap;

import fr.assaderie.mobs.config.AllEntityConfig;
import net.minecraftforge.common.Configuration;

public class ConfigZombieManager {

    private static final HashMap<String, ZombieConfig> zombieConfigs = new HashMap<String, ZombieConfig>();

    // Classe interne pour stocker la configuration de chaque zombie
    public static class ZombieConfig {
        public float speed;
        public int health;
        public int damage;
        public boolean sunDamageEnabled;
        public boolean dropEnabled;
        public int dropItemId;
        public int animalDetectionRadius;
        public int delayNearAnimal;
        public int playerDetectionRadius;
        public boolean canSpawnInDay;
        public int torchSpawnRadius;

        public ZombieConfig(float speed, int health, int damage, boolean sunDamageEnabled, boolean dropEnabled,
                            int dropItemId, int animalDetectionRadius, int delayNearAnimal, int playerDetectionRadius, boolean canSpawnInDay, int torchSpawnRadius) {
            this.speed = speed;
            this.health = health;
            this.damage = damage;
            this.sunDamageEnabled = sunDamageEnabled;
            this.dropEnabled = dropEnabled;
            this.dropItemId = dropItemId;
            this.animalDetectionRadius = animalDetectionRadius;
            this.delayNearAnimal = delayNearAnimal;
            this.playerDetectionRadius = playerDetectionRadius;
            this.canSpawnInDay = canSpawnInDay;
            this.torchSpawnRadius = torchSpawnRadius;
        }
    }

    public static void loadZombieConfigs(File configDir) {
        if (!AllEntityConfig.enableZombieConfig) {
            return;
        }

        // Créer le dossier ConfigZombie s'il n'existe pas
        File zombieConfigDir = new File(configDir, "Config_Zombie");
        if (!zombieConfigDir.exists()) {
            zombieConfigDir.mkdirs();
        }

        // Charger la configuration pour chaque type de zombie
        loadZombieConfig("ZombieOne", zombieConfigDir);
        loadZombieConfig("ZombieTwo", zombieConfigDir);
        loadZombieConfig("ZombieThree", zombieConfigDir);
        loadZombieConfig("ZombieFour", zombieConfigDir);
        loadZombieConfig("ZombieFive", zombieConfigDir);
        loadZombieConfig("ZombieSix", zombieConfigDir);
        loadZombieConfig("ZombieSeven", zombieConfigDir);
        loadZombieConfig("ZombieEight", zombieConfigDir);
        loadZombieConfig("ZombieNine", zombieConfigDir);
        loadZombieConfig("ZombieTen", zombieConfigDir);
        loadZombieConfig("Crawler", zombieConfigDir);
        loadZombieConfig("ZombieFat", zombieConfigDir);
        loadZombieConfig("ZombieTank", zombieConfigDir);
    }

    private static void loadZombieConfig(String zombieName, File configDir) {
        File zombieConfigFile = new File(configDir, zombieName + ".cfg");
        Configuration config = new Configuration(zombieConfigFile);

        try {
            config.load();

            // Lire les paramètres spécifiques du zombie
            float speed = (float) config.get(Configuration.CATEGORY_GENERAL, "speed", 0.3F, "Speed of the zombie \n Vitesse du zombie").getDouble(0.3F);
            int health = config.get(Configuration.CATEGORY_GENERAL, "health", 20, "Health of the zombie \n Santé du zombie").getInt(20);
            int damage = config.get(Configuration.CATEGORY_GENERAL, "damage", 5, "Damage dealt by the zombie \n Dégâts du zombie").getInt(5);
            boolean sunDamageEnabled = config.get(Configuration.CATEGORY_GENERAL, "sun_damage_enabled", false, "Is sun damage enabled \n Les dommages causés par le soleil sont-ils activés").getBoolean(false);
            boolean dropEnabled = config.get(Configuration.CATEGORY_GENERAL, "drop_enabled", true, "Are item drops enabled \n Les objets abandonnés (drops) sont-ils activés").getBoolean(true);
            int dropItemId = config.get(Configuration.CATEGORY_GENERAL, "drop_item_id", 367, "ID of the item dropped by the zombie \n ID de l'objet abandonné par le zombie").getInt(367);
            int animalDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "distance_block", 5, "Detection radius for blocks \n Distance de détection des blocs").getInt(5);

            // Convertir delay_near_animal en ticks
            String delayNearAnimalStr = config.get(Configuration.CATEGORY_GENERAL, "delay_near_animal", "20s", "Delay near an animal (in ticks or s/m/h/d) \n Délai près d'un animal (en ticks ou s/m/h/d)").value;
            int delayNearAnimal = convertTimeToTicks(delayNearAnimalStr);

            int playerDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "player_detection_radius", 16, "Player detection radius (in blocks) \n Rayon de détection du joueur (en blocs)").getInt(16);
            boolean canSpawnInDay = config.get(Configuration.CATEGORY_GENERAL, "can_spawn_in_day", true, "Can the zombie spawn during the day \n Le zombie peut apparaître le jour").getBoolean(true);
            int torchSpawnRadius = config.get(Configuration.CATEGORY_GENERAL, "torch_spawn_radius", 100, "Radius around torches preventing spawn \n Rayon autour des torches empêchant le spawn").getInt(100);

            // Enregistrer les paramètres dans la HashMap
            zombieConfigs.put(zombieName, new ZombieConfig(speed, health, damage, sunDamageEnabled, dropEnabled, dropItemId, animalDetectionRadius, delayNearAnimal, playerDetectionRadius, canSpawnInDay, torchSpawnRadius));

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la configuration pour " + zombieName + " : " + e.getMessage());
        } finally {
            config.save();
        }
    }

    public static ZombieConfig getZombieConfig(String zombieName) {
        return zombieConfigs.get(zombieName);
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
            ticks = Integer.parseInt(time); // Par défaut, interprété comme étant déjà en ticks
        }
        return ticks;
    }
}
