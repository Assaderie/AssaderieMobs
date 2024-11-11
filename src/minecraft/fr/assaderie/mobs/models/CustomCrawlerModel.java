package fr.assaderie.mobs.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class CustomCrawlerModel extends ModelBase {
    // Parties du corps de la créature
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightArm;
    ModelRenderer leftArm;
    ModelRenderer tail;

    // Constructeur du modèle
    public CustomCrawlerModel() {
        this.textureWidth = 64; // Largeur de la texture
        this.textureHeight = 64; // Hauteur de la texture

        // Initialisation de la tête
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);  // Définit la forme de la tête
        this.head.setRotationPoint(0.0F, 22.0F, 0.0F);  // Position initiale
        this.head.setTextureSize(64, 64);  // Taille de la texture
        this.head.mirror = true;  // Indique que la texture est symétrique
        setRotation(this.head, 0.0F, 0.0F, 0.0F);  // Rotation initiale

        // Initialisation du corps
        this.body = new ModelRenderer(this, 0, 18);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 4);  // Définit la forme du corps
        this.body.setRotationPoint(0.0F, 22.0F, 0.0F);  // Position initiale
        this.body.setTextureSize(64, 64);
        this.body.mirror = true;
        setRotation(this.body, (float) Math.PI / 2, 0.0F, 0.0F);  // Rotation initiale

        // Initialisation du bras droit
        this.rightArm = new ModelRenderer(this, 40, 43);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);  // Forme du bras droit
        this.rightArm.setRotationPoint(-5.0F, 22.0F, 1.0F);  // Position initiale
        this.rightArm.setTextureSize(64, 64);
        this.rightArm.mirror = true;
        setRotation(this.rightArm, -(float) Math.PI / 2, 0.0F, 0.0F);  // Rotation initiale

        // Initialisation du bras gauche
        this.leftArm = new ModelRenderer(this, 19, 43);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);  // Forme du bras gauche
        this.leftArm.setRotationPoint(5.0F, 22.0F, 1.0F);  // Position initiale
        this.leftArm.setTextureSize(64, 64);
        this.leftArm.mirror = true;
        setRotation(this.leftArm, -(float) Math.PI / 2, 0.0F, 0.0F);  // Rotation initiale

        // Initialisation de la queue (ou os)
        this.tail = new ModelRenderer(this, 0, 42);
        this.tail.addBox(0.0F, 0.0F, 0.0F, 2, 14, 2);  // Forme de la queue
        this.tail.setRotationPoint(-1.0F, 23.0F, 7.0F);  // Position initiale
        this.tail.setTextureSize(64, 64);
        this.tail.mirror = true;
        setRotation(this.tail, 1.509348F, 0.0F, 0.0F);  // Rotation initiale
    }

    // Méthode pour rendre le modèle
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.tail.render(scale);
    }

    // Définit la rotation d'une partie du modèle
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;  // Rotation sur l'axe X
        model.rotateAngleY = y;  // Rotation sur l'axe Y
        model.rotateAngleZ = z;  // Rotation sur l'axe Z
    }

    // Définit les angles de rotation pendant les animations
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.rightArm.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;  // Mouvement du bras droit
        this.leftArm.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;  // Mouvement du bras gauche
    }
}
