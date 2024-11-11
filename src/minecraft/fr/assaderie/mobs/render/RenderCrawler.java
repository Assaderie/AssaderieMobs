package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomCrawlerModel;
import net.minecraft.client.renderer.entity.RenderLiving;

public class RenderCrawler extends RenderLiving {

	public RenderCrawler() {
        super(new CustomCrawlerModel(), 0.5F);
    }
}
