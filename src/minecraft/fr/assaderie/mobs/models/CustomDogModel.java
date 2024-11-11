package fr.assaderie.mobs.models;

import fr.assaderie.mobs.entity.ZombieDogEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class CustomDogModel extends ModelBase {
    ModelRenderer WolfHead;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Tail;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer Nose;

    public CustomDogModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        // Wolf Head
        this.WolfHead = new ModelRenderer(this, 0, 0);
        this.WolfHead.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 6);
        this.WolfHead.setRotationPoint(-1.0F, 11.5F, -9.0F);
        setRotation(this.WolfHead, 0.0F, 0.0F, 0.0F);

        // Body
        this.Body = new ModelRenderer(this, 18, 14);
        this.Body.addBox(-4.0F, -2.0F, -3.0F, 6, 13, 6);
        this.Body.setRotationPoint(0.0F, 14.0F, -3.0F);
        setRotation(this.Body, (float) Math.PI / 2, 0.0F, 0.0F);

        // Legs
        this.Leg1 = new ModelRenderer(this, 0, 18);
        this.Leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.Leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        setRotation(this.Leg1, 0.0F, 0.0F, 0.0F);

        this.Leg2 = new ModelRenderer(this, 0, 18);
        this.Leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.Leg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        setRotation(this.Leg2, 0.0F, 0.0F, 0.0F);

        this.Leg3 = new ModelRenderer(this, 0, 18);
        this.Leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.Leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        setRotation(this.Leg3, 0.0F, 0.0F, 0.0F);

        this.Leg4 = new ModelRenderer(this, 0, 18);
        this.Leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        this.Leg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        setRotation(this.Leg4, 0.0F, 0.0F, 0.0F);

        // Tail
        this.Tail = new ModelRenderer(this, 9, 18);
        this.Tail.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2);
        this.Tail.setRotationPoint(-1.0F, 12.0F, 7.0F);
        setRotation(this.Tail, 1.13F, 0.0F, 0.0F);

        // Ears
        this.Ear1 = new ModelRenderer(this, 16, 14);
        this.Ear1.addBox(-3.0F, -5.0F, 0.0F, 2, 3, 1);
        this.Ear1.setRotationPoint(-1.0F, 10.5F, -8.0F);
        setRotation(this.Ear1, 0.0F, 0.0F, 0.0F);

        this.Ear2 = new ModelRenderer(this, 16, 14);
        this.Ear2.addBox(1.0F, -5.0F, 0.0F, 2, 3, 1);
        this.Ear2.setRotationPoint(-1.0F, 10.5F, -8.0F);
        setRotation(this.Ear2, 0.0F, 0.0F, 0.0F);

        // Nose
        this.Nose = new ModelRenderer(this, 0, 34);
        this.Nose.addBox(-2.0F, 0.0F, -5.0F, 3, 3, 3);
        this.Nose.setRotationPoint(-0.5F, 11.5F, -9.0F);
        setRotation(this.Nose, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.WolfHead.render(f5);
        this.Body.render(f5);
        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Leg3.render(f5);
        this.Leg4.render(f5);
        this.Tail.render(f5);
        this.Ear1.render(f5);
        this.Ear2.render(f5);
        this.Nose.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    @Override
    public void setLivingAnimations(EntityLiving entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
    	super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);
    	
    	ZombieDogEntity zombieDogEntity = (ZombieDogEntity) entity;
    	
    	this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 06662f) * 1.4f * limbSwingAmount;
    	
    	this.Leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		
		this.Leg2.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		
		this.Leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		
		this.Leg4.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.WolfHead.rotateAngleX = headPitch / (180f / (float)Math.PI);
        this.WolfHead.rotateAngleY = netHeadYaw / (180f / (float)Math.PI);
        
        this.Ear1.rotateAngleX = headPitch / (180f / (float)Math.PI);
        this.Ear1.rotateAngleY = netHeadYaw / (180f / (float)Math.PI);
        
        this.Ear2.rotateAngleX = headPitch / (180f / (float)Math.PI);
        this.Ear2.rotateAngleY = netHeadYaw / (180f / (float)Math.PI);
        
        this.Nose.rotateAngleX = headPitch / (180f / (float)Math.PI);
        this.Nose.rotateAngleY = netHeadYaw / (180f / (float)Math.PI);
    }
}
