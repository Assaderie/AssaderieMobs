package fr.assaderie.mobs.register;

import cpw.mods.fml.common.registry.EntityRegistry;
import fr.assaderie.mobs.entity.ZombieCrawlerEntity;
import fr.assaderie.mobs.entity.ZombieDogEntity;
import fr.assaderie.mobs.entity.ZombieEightEntity;
import fr.assaderie.mobs.entity.ZombieFatEntity;
import fr.assaderie.mobs.entity.ZombieFiveEntity;
import fr.assaderie.mobs.entity.ZombieFourEntity;
import fr.assaderie.mobs.entity.ZombieNineEntity;
import fr.assaderie.mobs.entity.ZombieOneEntity;
import fr.assaderie.mobs.entity.ZombieSevenEntity;
import fr.assaderie.mobs.entity.ZombieSixEntity;
import fr.assaderie.mobs.entity.ZombieTankEntity;
import fr.assaderie.mobs.entity.ZombieTenEntity;
import fr.assaderie.mobs.entity.ZombieThreeEntity;
import fr.assaderie.mobs.entity.ZombieTwoEntity;
import fr.assaderie.mobs.entity.ZombieWolfEntity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;

public class RegisterEntity {
	
	private static int startEntityId = 300;

	public static void EntityRegister(Object modInstance) {
		EntityRegistry.registerModEntity(ZombieOneEntity.class, "ZombieOne", 1, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieTwoEntity.class, "ZombieTwo", 2, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieThreeEntity.class, "ZombieThree", 3, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieFourEntity.class, "ZombieFour", 4, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieFiveEntity.class, "ZombieFive", 5, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieSixEntity.class, "ZombieSix", 6, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieSevenEntity.class, "ZombieSeven", 7, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieEightEntity.class, "ZombieEight", 8, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieNineEntity.class, "ZombieNine", 9, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieTenEntity.class, "ZombieTen", 10, modInstance, 80, 3, true);
		
		EntityRegistry.registerModEntity(ZombieDogEntity.class, "ZombieDog", 11, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieWolfEntity.class, "ZombieWolf", 12, modInstance, 80, 3, true);
		
		EntityRegistry.registerModEntity(ZombieCrawlerEntity.class, "Crawler", 13, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieFatEntity.class, "ZombieFat", 14, modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(ZombieTankEntity.class, "ZombieTank", 15, modInstance, 80, 3, true);
		
		
		
		registerEntityEgg(ZombieOneEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieTwoEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieThreeEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieFourEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieFiveEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieSixEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieSevenEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieEightEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieNineEntity.class, 6187376, 8092261);
		registerEntityEgg(ZombieTenEntity.class, 6187376, 8092261);
		
		registerEntityEgg(ZombieDogEntity.class, 656900, 7816742);
		registerEntityEgg(ZombieWolfEntity.class, 656900, 7816742);
		
		registerEntityEgg(ZombieCrawlerEntity.class, 4623500, 2334755);
		registerEntityEgg(ZombieFatEntity.class, 4623500, 2334755);
		registerEntityEgg(ZombieTankEntity.class, 4623500, 2334755);
	}
	
	private static int getUniqueEntityId() {
		do {
			startEntityId++;
		} while (EntityList.getClassFromID(startEntityId) != null);
		return startEntityId;
	}
	
	private static void registerEntityEgg(Class<?> entity, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}
}
