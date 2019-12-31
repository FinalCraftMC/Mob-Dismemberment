package br.com.finalcraft.bettermobdismemberment.client.render.gibmodels;

import br.com.finalcraft.bettermobdismemberment.client.entity.EntityGib;
import br.com.finalcraft.bettermobdismemberment.client.particle.ParticleBlood;
import br.com.finalcraft.bettermobdismemberment.client.util.ObfHelper;
import br.com.finalcraft.bettermobdismemberment.common.BetterMobDismemberment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface GibModelTemplate {

    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo);

    public ModelRenderer setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityGib ent);

    public boolean isApplicableFor(EntityLivingBase living);

    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5);

    public default void bindEntityTexture(EntityGib ent){};

    public default void  applyBloodParticles(EntityLivingBase living, Entity explo){
        if(BetterMobDismemberment.config.blood == 1) {
            for (int k = 0; k < (explo != null ? BetterMobDismemberment.config.bloodCount * 10 : BetterMobDismemberment.config.bloodCount); k++) {
                float var4 = 0.3F;
                double mX = (double) (-MathHelper.sin(living.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(living.rotationPitch / 180.0F * (float) Math.PI) * var4);
                double mZ = (double) (MathHelper.cos(living.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(living.rotationPitch / 180.0F * (float) Math.PI) * var4);
                double mY = (double) (-MathHelper.sin(living.rotationPitch / 180.0F * (float) Math.PI) * var4 + 0.1F);
                var4 = 0.02F;
                float var5 = living.getRNG().nextFloat() * (float) Math.PI * 2.0F;
                var4 *= living.getRNG().nextFloat();

                if (explo != null) {
                    var4 *= 100D;
                }

                mX += Math.cos((double) var5) * (double) var4;
                mY += (double) ((living.getRNG().nextFloat() - living.getRNG().nextFloat()) * 0.1F);
                mZ += Math.sin((double) var5) * (double) var4;

                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBlood(living.worldObj, living.posX, living.posY + 0.5D + (living.getRNG().nextDouble() * 0.7D), living.posZ, living.motionX + mX, living.motionY + mY, living.motionZ + mZ, living instanceof EntityPlayer));
            }
        }
    }

    //This is the default behavior off all default mod models (Creeper, Skeleton and Zombie)
    public default void entityCreationSizeDeffinition(EntityGib gib){
        if(gib.type == 0)
        {
            gib.rotationYaw = gib.parent.rotationYawHead;
            gib.setSize(0.5F, 0.5F);
            if(gib.parent instanceof EntityCreeper)
            {
                gib.posY += 1.25D;
            }
            else
            {
                gib.posY += 1.5D;
            }
        }
        else if(gib.type == 1 || gib.type == 2)
        {
            gib.setSize(0.3F, 0.4F);

            double offset = 0.350D;
            double offset1 = -0.250D;

            if(gib.parent instanceof EntitySkeleton)
            {
                offset -= 0.05D;
                gib.posY += 0.15D;
            }
            if(gib.type == 2)
            {
                offset *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posX += offset1 * Math.sin(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ -= offset1 * Math.cos(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 1.25D;

            gib.prevRotationPitch = gib.rotationPitch = -90F;
        }
        else if(gib.type == 3)
        {
            gib.setSize(0.5F, 0.5F);
            if(gib.parent instanceof EntityCreeper)
            {
                gib.posY += 0.75D;
            }
            else
            {
                gib.posY += 1.0D;
            }
        }
        else if(gib.type == 4 || gib.type == 5)
        {
            gib.setSize(0.3F, 0.4F);

            double offset = 0.125D;

            if(gib.type == 5)
            {
                offset *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 0.375D;
        }
        else if(gib.type >= 6)
        {
            gib.setSize(0.3F, 0.4F);

            double offset = 0.125D;
            double offset1 = -0.250D;

            if(gib.parent instanceof EntitySkeleton)
            {
                offset -= 0.05D;
                gib.posY += 0.15D;
            }
            if(gib.type % 2 == 1)
            {
                offset *= -1D;
            }
            if(gib.type >= 8)
            {
                offset1 *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posX += offset1 * Math.sin(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ -= offset1 * Math.cos(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 0.3125D;
        }
    }

    public default ResourceLocation getParentTexture(EntityGib gib){
        Render render = RenderManager.instance.getEntityRenderObject(gib.parent);
        return ObfHelper.invokeGetEntityTexture(render, render.getClass(), gib.parent);
    }

    public default float getRenderHeigh(EntityGib gib){
        return gib.height;
    }

    public default float getRenderWidth(EntityGib gib){
        return gib.width;
    }
}
