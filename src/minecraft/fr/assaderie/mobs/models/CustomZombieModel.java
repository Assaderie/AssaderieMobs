package fr.assaderie.mobs.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CustomZombieModel extends ModelBiped {

	public CustomZombieModel() {
        super(0.0F);
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, (float) 0.5); // Gauche a droit / Haut bas / avant arriere /
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);

        // Body (texture at 16, 16)
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4); // Width, Height, Depth of the body
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);

        // Right Arm (texture at 40, 16)
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);  // Adjusted arm size
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);

        // Left Arm (texture at 40, 16 - mirrored)
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);  // Adjusted arm size
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

        // Right Leg (texture at 0, 16)
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);  // Leg dimensions
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);

        // Left Leg (texture at 0, 16 - mirrored)
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);  // Leg dimensions
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
    }
    
    // Custom rotation angles if needed for animations
    @Override
    public void setRotationAngles(float time, float speed, float f3, float yaw, float pitch, float scale, Entity entity) {
        super.setRotationAngles(time, speed, f3, yaw, pitch, scale, entity);
    }
}
