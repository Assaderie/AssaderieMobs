package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomZombieModel;
import fr.assaderie.mobs.utils.TexturesZombie;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;

public class RenderZombie extends RenderLiving {

    public RenderZombie() {
        super(new CustomZombieModel(), 0.5F);
    }
}
