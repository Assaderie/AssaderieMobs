package fr.assaderie.mobs.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.assaderie.mobs.entity.ZombieWolfEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class CustomModelWolf extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer body_rotation;
    private final ModelRenderer mane;
    private final ModelRenderer mane_rotation;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer tail;

    public CustomModelWolf() {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.0F, 13.5F, -7.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -2.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F));
        head.cubeList.add(new ModelBox(head, 16, 14, 2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F));
        head.cubeList.add(new ModelBox(head, 16, 14, -2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F));
        head.cubeList.add(new ModelBox(head, 0, 10, -0.5F, -0.02F, -5.0F, 3, 3, 4, 0.0F));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 14.0F, 2.0F);

        body_rotation = new ModelRenderer(this);
        body_rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_rotation);
        setRotationAngle(body_rotation, 1.5708F, 0.0F, 0.0F);
        body_rotation.cubeList.add(new ModelBox(body_rotation, 18, 14, -3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F));

        mane = new ModelRenderer(this);
        mane.setRotationPoint(-1.0F, 14.0F, 2.0F);

        mane_rotation = new ModelRenderer(this);
        mane_rotation.setRotationPoint(1.0F, 2.5F, -2.5F);
        mane.addChild(mane_rotation);
        setRotationAngle(mane_rotation, 1.5708F, 0.0F, 0.0F);
        mane_rotation.cubeList.add(new ModelBox(mane_rotation, 21, 0, -4.0F, -5.5F, -0.5F, 8, 6, 7, 0.0F));

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        leg1.cubeList.add(new ModelBox(leg1, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F));

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        leg2.cubeList.add(new ModelBox(leg2, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F));

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        leg3.cubeList.add(new ModelBox(leg3, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F));

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        leg4.cubeList.add(new ModelBox(leg4, 0, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F));

        tail = new ModelRenderer(this);
        tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        setRotationAngle(tail, 0.5672F, 0.0F, 0.0F);
        tail.cubeList.add(new ModelBox(tail, 9, 18, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        setLivingAnimations((EntityLiving) entity, f, f1, f2);
        head.render(f5);
        body.render(f5);
        mane.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        tail.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setLivingAnimations(EntityLiving entityLiving, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entityLiving, limbSwing, limbSwingAmount, partialTickTime);        
        if (entityLiving instanceof ZombieWolfEntity) {
            ZombieWolfEntity zombieWolfEntity = (ZombieWolfEntity) entityLiving;

            if (zombieWolfEntity.isHowling()) {
                // Animation de hurlement : ajuste seulement la position et l'angle
                head.setRotationPoint(-3.0F, 7.5F, -14.0F);
                head.rotateAngleX = 0.0F;
                head.rotateAngleY = 0.0F;
                head.rotateAngleZ = 0.0F;

                body.setRotationPoint(-3.0F, 3.0F, -4.0F);
                body.rotateAngleX = 0.0F;

                body_rotation.setRotationPoint(-3.0F, 3.0F, -4.0F);
                body_rotation.rotateAngleX = -1.5708F; // -90 degrés

                mane.setRotationPoint(-4.0F, 7.0F, -4.0F);

                leg1.setRotationPoint(0.5F, 0.0F, 6.0F);
                leg2.setRotationPoint(-2.5F, 0.0F, 6.0F);
                leg3.setRotationPoint(0.5F, 0.0F, -5.0F);
                leg4.setRotationPoint(-2.5F, 0.0F, -5.0F);

                tail.setRotationPoint(-1.0F, 1.0F, 9.0F);
            } else {
                // Animation par défaut
                this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

                this.leg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
                this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

                this.leg2.setRotationPoint(0.5F, 16.0F, 7.0F);
                this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

                this.leg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
                this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

                this.leg4.setRotationPoint(0.5F, 16.0F, -4.0F);
                this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            }
        }
    }


    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        this.head.rotateAngleX = headPitch / (180F / (float)Math.PI);
        this.head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
        
        if(entity instanceof ZombieWolfEntity && !((ZombieWolfEntity) entity).isHowling()) {
        	this.head.rotateAngleX = headPitch / (180F / (float)Math.PI);
            this.head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
        }
    }
}
