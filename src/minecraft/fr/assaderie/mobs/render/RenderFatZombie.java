package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomCrawlerModel;
import fr.assaderie.mobs.models.CustomZombieFatModel;
import net.minecraft.client.renderer.entity.RenderLiving;

public class RenderFatZombie extends RenderLiving {

	public RenderFatZombie() {
        super(new CustomZombieFatModel(), 0.5F);
    }
}
