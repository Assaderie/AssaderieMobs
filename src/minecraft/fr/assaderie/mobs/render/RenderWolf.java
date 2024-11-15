package fr.assaderie.mobs.render;

import fr.assaderie.mobs.models.CustomModelWolf;
import fr.assaderie.mobs.entity.ZombieWolfEntity;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

public class RenderWolf extends RenderLiving {

    private CustomModelWolf wolfModel;

    public RenderWolf() {
        super(new CustomModelWolf(), 0.5F);
        this.wolfModel = (CustomModelWolf) super.mainModel;
        this.setRenderPassModel(this.wolfModel);
    }

    /**
     * Renders the zombie wolf with custom properties
     */
    public void renderZombieWolf(ZombieWolfEntity entityWolf, double x, double y, double z, float yaw, float partialTicks) {
        super.doRenderLiving(entityWolf, x, y, z, yaw, partialTicks);
    }

    /**
     * Render the wolf's glowing eyes
     */
    protected int renderEyes(ZombieWolfEntity entityWolf, int pass, float partialTicks) {
        if (pass != 0) {
            return -1;
        } else {
            // Bind the custom texture for the wolf's eyes
            this.loadTexture("/mobs/assaderie/textures/entity/wolf/wolf_eyes.png"); // Adjust this path to your custom texture for wolf eyes

            // Set up OpenGL for glowing effect
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);

            // Set brightness for the glowing effect
            char brightness = 61680;
            int u = brightness % 65536;
            int v = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) u / 1.0F, (float) v / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            // Reset OpenGL states after rendering
            GL11.glEnable(GL11.GL_LIGHTING);  // Re-enable lighting
            GL11.glDisable(GL11.GL_BLEND);    // Disable blending to prevent overlap
            GL11.glEnable(GL11.GL_ALPHA_TEST);

            return 1; // Custom pass applied
        }
    }

    /**
     * Handle the render pass for the glowing eyes
     */
    protected int shouldRenderPass(EntityLiving entity, int pass, float partialTicks) {
        if (entity instanceof ZombieWolfEntity) {
            return this.renderEyes((ZombieWolfEntity) entity, pass, partialTicks);
        }
        return -1;
    }

    public void doRenderLiving(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        if (entity instanceof ZombieWolfEntity) {
            this.renderZombieWolf((ZombieWolfEntity) entity, x, y, z, yaw, partialTicks);
        }
    }

    /**
     * Bridge method for rendering the entity
     */
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        if (entity instanceof ZombieWolfEntity) {
            this.renderZombieWolf((ZombieWolfEntity) entity, x, y, z, yaw, partialTicks);
        }
    }
}
