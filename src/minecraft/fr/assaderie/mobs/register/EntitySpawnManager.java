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
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class EntitySpawnManager {
	
	public static void addEntitySpawns() {
		BiomeGenBase[] commonBiomes = getCommonBiomes();
		
		EntityRegistry.addSpawn(ZombieOneEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieTwoEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieThreeEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieFourEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieFiveEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieSixEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieSevenEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieEightEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieNineEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieTenEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		
		EntityRegistry.addSpawn(ZombieDogEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieWolfEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		
		EntityRegistry.addSpawn(ZombieCrawlerEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieFatEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
		EntityRegistry.addSpawn(ZombieTankEntity.class, 10, 4, 4, EnumCreatureType.monster, commonBiomes);
	}
	
	private static BiomeGenBase[] getCommonBiomes() {
	    return new BiomeGenBase[] {
	      BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.swampland, BiomeGenBase.forest, BiomeGenBase.taiga,
	      BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.mushroomIsland, BiomeGenBase.beach,
	      BiomeGenBase.icePlains, BiomeGenBase.hell, BiomeGenBase.sky
	    };
	  }
}
