package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomZombieModel;
import fr.assaderie.mobs.models.CustomDogModel;
import net.minecraft.client.renderer.entity.RenderLiving;

public class RenderDog extends RenderLiving {

    public RenderDog() {
        super(new CustomDogModel(), 0.5F);
    }
}
