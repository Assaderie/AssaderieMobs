package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomZombieTankModel;
import net.minecraft.client.renderer.entity.RenderLiving;

public class RenderTankZombie extends RenderLiving {

	public RenderTankZombie() {
        super(new CustomZombieTankModel(), 0.5F);
    }
}
