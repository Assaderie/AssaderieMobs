package fr.assaderie.mobs.config;

import java.io.File;
import net.minecraftforge.common.Configuration;

public class AllEntityConfig {

    public static boolean enableZombieConfig = false;

    // Paramètres globaux de configuration
    public static float globalSpeed;
    public static int globalHealth;
    public static float globalDamage;
    public static boolean sunDamageEnabled;
    public static boolean dropEnabled;
    public static int dropItemId;
    public static int animalDetectionRadius;
    public static int delayNearAnimal;
    public static int playerDetectionRadius;
    public static boolean canSpawnInDay;
    public static int torchSpawnRadius;

    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);

        try {
            config.load();

            // Charger la configuration principale pour les zombies
            enableZombieConfig = config.get(Configuration.CATEGORY_GENERAL, "enable_zombie_config", false, "Enable or disable specific configurations for zombies \n Activer ou désactiver les configurations spécifiques aux zombies").getBoolean(false);

            // Paramètres globaux si la configuration spécifique est désactivée
            globalSpeed =  (float) config.get(Configuration.CATEGORY_GENERAL, "global_speed", (double)0.3F, "Global speed of zombies \n Vitesse globale des zombies").getDouble((double)0.3F);
            globalHealth = config.get(Configuration.CATEGORY_GENERAL, "global_health", 20, "Global health of zombies \n Santé globale des zombies").getInt(20);
            globalDamage = (float) config.get(Configuration.CATEGORY_GENERAL, "global_damage", 5.0F, "Global damage of zombies \n Dégâts globaux des zombies").getDouble(5.0F);
            sunDamageEnabled = config.get(Configuration.CATEGORY_GENERAL, "sun_damage_enabled", false, "Is sun damage globally enabled \n Les dommages causés par le soleil sont-ils activés globalement").getBoolean(false);
            dropEnabled = config.get(Configuration.CATEGORY_GENERAL, "drop_enabled", true, "Are global item drops enabled \n Les objets abandonnés (drops) sont-ils activés globalement").getBoolean(true);
            dropItemId = config.get(Configuration.CATEGORY_GENERAL, "drop_item_id", 367, "ID of the globally dropped item \n ID de l'objet abandonné globalement").getInt(367);
            animalDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "animal_detection_radius", 5, "Block detection radius for animals \n Rayon de détection des animaux en blocs").getInt(5);

            // Utiliser convertTimeToTicks pour delayNearAnimal
            String delayNearAnimalStr = config.get(Configuration.CATEGORY_GENERAL, "delay_near_animal", "20s", "Global delay near an animal (in ticks, or use s/m/h/d) \n Délai global avant attaque d'un animal (en ticks ou s/m/h/d)").value;
            delayNearAnimal = convertTimeToTicks(delayNearAnimalStr);

            playerDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "player_detection_radius", 16, "Global player detection radius (in blocks) \n Rayon global de détection du joueur").getInt(16);
            canSpawnInDay = config.get(Configuration.CATEGORY_GENERAL, "can_spawn_in_day", true, "Allow zombies to spawn during daytime \n Permettre aux zombies d'apparaître pendant la journée").getBoolean(true);
            torchSpawnRadius = config.get(Configuration.CATEGORY_GENERAL, "torch_spawn_radius", 100, "Radius around torches preventing zombie spawns \n Rayon autour des torches empêchant le spawn des zombies").getInt(100);

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la configuration globale : " + e.getMessage());
        } finally {
            config.save();
        }
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
            ticks = Integer.parseInt(time); // Par défaut, on suppose qu'il est déjà en ticks
        }
        return ticks;
    }
}
