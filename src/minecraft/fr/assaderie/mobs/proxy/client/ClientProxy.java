package fr.assaderie.mobs.proxy.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
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
import fr.assaderie.mobs.proxy.server.CommonProxy;
import fr.assaderie.mobs.render.RenderCrawler;
import fr.assaderie.mobs.render.RenderDog;
import fr.assaderie.mobs.render.RenderFatZombie;
import fr.assaderie.mobs.render.RenderTankZombie;
import fr.assaderie.mobs.render.RenderWolf;
import fr.assaderie.mobs.render.RenderZombie;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenders() {
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieOneEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieTwoEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieThreeEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieFourEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieFiveEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieSixEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieSevenEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieEightEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieNineEntity.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieTenEntity.class, new RenderZombie());
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieDogEntity.class, new RenderDog());
		RenderingRegistry.registerEntityRenderingHandler(ZombieWolfEntity.class, new RenderWolf());
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieCrawlerEntity.class, new RenderCrawler());
		RenderingRegistry.registerEntityRenderingHandler(ZombieFatEntity.class, new RenderFatZombie());
		RenderingRegistry.registerEntityRenderingHandler(ZombieTankEntity.class, new RenderTankZombie());
		
		
	}
}
