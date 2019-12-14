package me.ichun.mods.mobdismemberment.client.render;

import ichun.common.core.EntityHelperBase;
import ichun.common.core.util.ObfHelper;
import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import me.ichun.mods.mobdismemberment.common.MobDismemberment;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGib extends Render{

    public ModelGib modelGib;

    public RenderGib(){
        modelGib = new ModelGib();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity){
        EntityGib gib = (EntityGib)entity;
        if (gib.parent != null) {
            Render render = RenderManager.instance.getEntityRenderObject(gib.parent);
            return ObfHelper.invokeGetEntityTexture(render, render.getClass(), gib.parent);
        } else {
            return AbstractClientPlayer.locationStevePng;
        }
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        EntityGib gib = (EntityGib)par1Entity;
        this.bindEntityTexture(par1Entity);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float alpha = 1.0F;
        if (gib.groundTime >= MobDismemberment.config.gibGroundTime) {
            alpha = 1.0F - ((float)(gib.groundTime - MobDismemberment.config.gibGroundTime) + par9) / 20.0F;
        }

        if (alpha < 0.0F) {
            alpha = 0.0F;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        GL11.glAlphaFunc(516, 0.003921569F);
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glTranslatef(0.0F, gib.type == 0 ? 0.25F : (gib.type <= 2 && gib.parent instanceof EntitySkeleton ? 0.0625F : 0.125F), 0.0F);
        GL11.glRotatef(EntityHelperBase.interpolateRotation(par1Entity.prevRotationYaw, par1Entity.rotationYaw, par9), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(EntityHelperBase.interpolateRotation(par1Entity.prevRotationPitch, par1Entity.rotationPitch, par9), -1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 1.5F - gib.height * 0.5F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelGib.render(gib, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
