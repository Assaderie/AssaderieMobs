package fr.assaderie.mobs.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class CustomZombieFatModel extends ModelBase {
  ModelRenderer head;
  ModelRenderer rightArm;
  ModelRenderer rightLeg;
  ModelRenderer leftLeg;
  ModelRenderer body;
  ModelRenderer leftArm;

  public CustomZombieFatModel() {
    this.textureWidth = 64;
    this.textureHeight = 64;

    // Tête
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.setRotationPoint(0.5F, 0.0F, 0.0F);
    this.head.setTextureSize(64, 64);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);

    // Bras droit
    this.rightArm = new ModelRenderer(this, 40, 0);
    this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 5, 15, 6);
    this.rightArm.setRotationPoint(-9.0F, 3.0F, 2.0F);
    this.rightArm.setTextureSize(64, 64);
    this.rightArm.mirror = true;
    setRotation(this.rightArm, -1.570796F, 0.0F, 0.0F);

    // Jambe droite
    this.rightLeg = new ModelRenderer(this, 0, 21);
    this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 6, 12, 6);
    this.rightLeg.setRotationPoint(-5.0F, 12.0F, 0.0F);
    this.rightLeg.setTextureSize(64, 64);
    this.rightLeg.mirror = true;
    setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);

    // Jambe gauche
    this.leftLeg = new ModelRenderer(this, 0, 21);
    this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 6, 12, 6);
    this.leftLeg.setRotationPoint(4.0F, 12.0F, 0.0F);
    this.leftLeg.setTextureSize(64, 64);
    this.leftLeg.mirror = true;
    setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);

    // Corps
    this.body = new ModelRenderer(this, 0, 41);
    this.body.addBox(0.0F, 0.0F, 0.0F, 15, 12, 11);
    this.body.setRotationPoint(-7.0F, 0.0F, -5.0F);
    this.body.setTextureSize(64, 64);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);

    // Bras gauche
    this.leftArm = new ModelRenderer(this, 40, 0);
    this.leftArm.addBox(0.0F, 0.0F, 0.0F, 5, 15, 6);
    this.leftArm.setRotationPoint(8.0F, 1.0F, 4.0F);
    this.leftArm.setTextureSize(64, 64);
    this.leftArm.mirror = true;
    setRotation(this.leftArm, -1.570796F, 0.0F, 0.0F);
  }

  // Méthode pour dessiner le modèle avec une entité et différents paramètres de rotation
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.head.render(scale);
    this.rightArm.render(scale);
    this.rightLeg.render(scale);
    this.leftLeg.render(scale);
    this.body.render(scale);
    this.leftArm.render(scale);
  }

  // Définit la rotation d'une partie du modèle en fonction des angles x, y, et z
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  // Définit les angles de rotation des jambes pour l'animation de marche
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount;
  }
}
