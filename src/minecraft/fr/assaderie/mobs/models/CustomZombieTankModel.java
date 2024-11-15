package fr.assaderie.mobs.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class CustomZombieTankModel extends ModelBase {
  ModelRenderer head;
  ModelRenderer body;
  ModelRenderer leftArm;
  ModelRenderer rightLeg;
  ModelRenderer leftLeg;
  ModelRenderer rightArm;

  public CustomZombieTankModel() {
    this.textureWidth = 128;
    this.textureHeight = 128;

    // Tête
    this.head = new ModelRenderer(this, 0, 64);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 10, 10, 10);
    this.head.setRotationPoint(-1.0F, -1.0F, 2.0F);
    this.head.setTextureSize(128, 128);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);

    // Corps
    this.body = new ModelRenderer(this, 4, 90);
    this.body.addBox(-4.0F, 0.0F, -2.0F, 20, 12, 20);
    this.body.setRotationPoint(-6.0F, 0.0F, -3.0F);
    this.body.setTextureSize(128, 128);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);

    // Bras gauche
    this.leftArm = new ModelRenderer(this, 73, 3);
    this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 13, 26, 13);
    this.leftArm.setRotationPoint(4.0F, 6.0F, 0.0F);
    this.leftArm.setTextureSize(128, 128);
    this.leftArm.mirror = true;
    setRotation(this.leftArm, 0.0F, 0.0F, -0.7435722F);

    // Jambe droite
    this.rightLeg = new ModelRenderer(this, 0, 16);
    this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 8, 12, 8);
    this.rightLeg.setRotationPoint(-7.0F, 12.0F, 3.0F);
    this.rightLeg.setTextureSize(128, 128);
    this.rightLeg.mirror = true;
    setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);

    // Jambe gauche
    this.leftLeg = new ModelRenderer(this, 0, 16);
    this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 8, 12, 8);
    this.leftLeg.setRotationPoint(2.0F, 12.0F, 3.0F);
    this.leftLeg.setTextureSize(128, 128);
    this.leftLeg.mirror = true;
    setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);

    // Bras droit
    this.rightArm = new ModelRenderer(this, 40, 12);
    this.rightArm.addBox(0.0F, 0.0F, 0.0F, 6, 16, 7);
    this.rightArm.setRotationPoint(-9.0F, 0.0F, 0.0F);
    this.rightArm.setTextureSize(128, 128);
    this.rightArm.mirror = true;
    setRotation(this.rightArm, 0.0F, 0.0F, 0.5410521F);
  }

  // Méthode pour dessiner le modèle avec une entité et différents paramètres de rotation
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.head.render(scale);
    this.body.render(scale);
    this.leftArm.render(scale);
    this.rightLeg.render(scale);
    this.leftLeg.render(scale);
    this.rightArm.render(scale);
  }

  // Définit la rotation d'une partie du modèle en fonction des angles x, y, et z
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  // Définit les angles de rotation des jambes et bras pour l'animation de marche
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount;
    this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount;
  }
}
