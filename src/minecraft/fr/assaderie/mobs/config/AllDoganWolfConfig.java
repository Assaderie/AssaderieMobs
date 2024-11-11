package fr.assaderie.mobs.config;

import java.io.File;
import net.minecraftforge.common.Configuration;

public class AllDoganWolfConfig {

    public static boolean enableDogConfig = false;

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

            // Activer ou désactiver la configuration spécifique pour les chiens et les loups zombies
            enableDogConfig = config.get(Configuration.CATEGORY_GENERAL, "enable_dog_config", false, "Enable or disable specific configurations for zombie dogs and wolves \n Activer ou désactiver les configurations spécifiques pour les chiens et les loups zombies").getBoolean(false);

            // Paramètres globaux si la configuration spécifique est désactivée
            globalSpeed = (float) config.get(Configuration.CATEGORY_GENERAL, "global_speed", 0.3F, "Global speed of zombie dogs and wolves \n Vitesse globale des chiens et des loups zombies").getDouble(0.3F);
            globalHealth = config.get(Configuration.CATEGORY_GENERAL, "global_health", 20, "Global health of zombie dogs and wolves \n Santé globale des chiens et des loups zombies").getInt(20);
            globalDamage = (float) config.get(Configuration.CATEGORY_GENERAL, "global_damage", 5.0F, "Global damage of zombie dogs and wolves \n Dégâts globaux des chiens et des loups zombies").getDouble(5.0F);
            sunDamageEnabled = config.get(Configuration.CATEGORY_GENERAL, "sun_damage_enabled", false, "Is sun damage globally enabled \n Les dommages causés par le soleil sont-ils activés globalement").getBoolean(false);
            dropEnabled = config.get(Configuration.CATEGORY_GENERAL, "drop_enabled", true, "Are global item drops enabled \n Les objets abandonnés (drops) sont-ils activés globalement").getBoolean(true);
            dropItemId = config.get(Configuration.CATEGORY_GENERAL, "drop_item_id", 367, "ID of the globally dropped item \n ID de l'objet abandonné globalement").getInt(367);
            animalDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "animal_detection_radius", 5, "Block detection radius for animals \n Rayon de détection des animaux en blocs").getInt(5);
            String delayNearAnimalStr = config.get(Configuration.CATEGORY_GENERAL, "delay_near_animal", "20s", "Global delay near an animal (in ticks, or use s/m/h/d) \n Délai global avant attaque d'un animal (en ticks ou s/m/h/d)").value;
            delayNearAnimal = convertTimeToTicks(delayNearAnimalStr);
            playerDetectionRadius = config.get(Configuration.CATEGORY_GENERAL, "player_detection_radius", 16, "Global player detection radius (in blocks) \n Rayon global de détection du joueur").getInt(16);
            canSpawnInDay = config.get(Configuration.CATEGORY_GENERAL, "can_spawn_in_day", true, "Allow zombie dogs and wolves to spawn during daytime \n Permettre aux chiens et loups zombies d'apparaître pendant la journée").getBoolean(true);
            torchSpawnRadius = config.get(Configuration.CATEGORY_GENERAL, "torch_spawn_radius", 100, "Radius around torches preventing zombie dog and wolf spawns \n Rayon autour des torches empêchant le spawn des chiens et des loups zombies").getInt(100);

        } catch(Exception e) {
            System.out.println("Erreur lors du chargement de la configuration globale des chiens et des loups : " + e.getMessage());
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
        }
        return ticks;
    }
}
