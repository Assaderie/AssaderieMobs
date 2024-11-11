package fr.assaderie.mobs;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fr.assaderie.mobs.config.AllDoganWolfConfig;
import fr.assaderie.mobs.config.AllEntityConfig;
import fr.assaderie.mobs.managers.ConfigDogAndWolfManager;
import fr.assaderie.mobs.managers.ConfigZombieManager;
import fr.assaderie.mobs.proxy.client.ClientProxy;
import fr.assaderie.mobs.proxy.server.CommonProxy;
import fr.assaderie.mobs.register.EntitySpawnManager;
import fr.assaderie.mobs.register.RegisterEntity;
import fr.assaderie.mobs.utils.References;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = References.MOD_ID, name = References.NAME, version = References.VERSION, acceptedMinecraftVersions = References.ACCEPT_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class AssaderieMobs {
	
	public static boolean isZombieAwarnessLoaded = false;
	
	MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
	    
    @Instance(References.MOD_ID)
    private static AssaderieMobs instance;
    
    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
    public static CommonProxy proxy;
    
    private static SoundEvents soundEvents;

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent event) {
        File configDir = new File(event.getModConfigurationDirectory(), "AssaderieMobs");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        AllEntityConfig.loadConfig(new File(configDir, "AllZombieConfig.cfg"));
        AllDoganWolfConfig.loadConfig(new File(configDir, "DogAndWolfConfig.cfg"));
        ConfigZombieManager.loadZombieConfigs(configDir);
        ConfigDogAndWolfManager.loadDogAndWolfConfigs(configDir);


        soundEvents = new SoundEvents();
        if (proxy instanceof ClientProxy) {
            MinecraftForge.EVENT_BUS.register(soundEvents);
        }
    }

    
    @Mod.Init
    public void init(FMLInitializationEvent event) {
        RegisterEntity.EntityRegister(this);
        EntitySpawnManager.addEntitySpawns();
        proxy.registerRenders();
                
        isZombieAwarnessLoaded = Loader.isModLoaded("ZAMod");
        if(isZombieAwarnessLoaded) {
        	System.out.println("ZombieAwareness détecté ! Activation des fonctionnalités spécifiques.");
        } else {
        	System.out.println("ZombieAwareness non détecté. Certaines fonctionnalités d'AssaderieMobs seront désactivées.");
        }
    }
    
    @Mod.PostInit
    public void postInit(FMLPostInitializationEvent event) {
        // Chargement des localisations pour plusieurs langues
        LanguageRegistry.instance().loadLocalization(References.LANG_EN, "en_US", false);
        LanguageRegistry.instance().loadLocalization(References.LANG_FR, "fr_FR", false);
    }
}
