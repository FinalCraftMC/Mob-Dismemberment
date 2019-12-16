package me.ichun.mods.mobdismemberment.client.render.gibmodels;

import me.ichun.mods.mobdismemberment.client.entity.EntityGib;
import me.ichun.mods.mobdismemberment.client.particle.ParticleBlood;
import me.ichun.mods.mobdismemberment.common.MobDismemberment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GMTZombie implements GibModelTemplate{

    public ModelRenderer head64;
    public ModelRenderer arm64;
    public ModelRenderer body64;
    public ModelRenderer leg64;

    public GMTZombie(ModelBase modelBase) {
        head64 = new ModelRenderer(modelBase, 0, 0);
        head64.setTextureSize(64, 64);
        head64.addBox(-4F, -4F, -4F, 8, 8, 8);
        head64.setRotationPoint(0F, 20F, 0F);
        head64.rotateAngleX = 0F;
        head64.rotateAngleY = 0F;
        head64.rotateAngleZ = 0F;
        head64.mirror = false;

        arm64 = new ModelRenderer(modelBase, 40, 16);
        arm64.setTextureSize(64, 64);
        arm64.addBox(-2F, -6F, -2F, 4, 12, 4);
        arm64.setRotationPoint(0F, 22F, 0F);
        arm64.rotateAngleX = 0F;
        arm64.rotateAngleY = 0F;
        arm64.rotateAngleZ = 0F;
        arm64.mirror = false;

        body64 = new ModelRenderer(modelBase, 16, 16);
        body64.setTextureSize(64, 64);
        body64.addBox(-4F, -6F, -2F, 8, 12, 4);
        body64.setRotationPoint(0F, 22F, 0F);
        body64.rotateAngleX = 0F;
        body64.rotateAngleY = 0F;
        body64.rotateAngleZ = 0F;
        body64.mirror = false;

        leg64 = new ModelRenderer(modelBase, 0, 16);
        leg64.setTextureSize(64, 64);
        leg64.addBox(-2F, -6F, -2F, 4, 12, 4);
        leg64.setRotationPoint(0F, 24F, 0F);
        leg64.rotateAngleX = 0F;
        leg64.rotateAngleY = 0F;
        leg64.rotateAngleZ = 0F;
        leg64.mirror = false;
    }

    // ===============================================================================================
    // Selection Section
    // ===============================================================================================

    @Override
    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo){
        for(int i = 0; i < 6; i++){
            world.spawnEntityInWorld(new EntityGib(world, living, i, explo, this));
        }

        if(MobDismemberment.config.blood == 1) {
            for (int k = 0; k < (explo != null ? MobDismemberment.config.bloodCount * 10 : MobDismemberment.config.bloodCount); k++) {
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

    @Override
    public boolean isApplicableFor(EntityLivingBase living){
        if (living instanceof EntityZombie) return true;  //EntityPigZombie already extends EntityZombie
        return false;
    }

    // ===============================================================================================
    // Render Section
    // ===============================================================================================

    @Override
    public ModelRenderer setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityGib gib){
        switch ( gib.type ){
            case 0:         //Head
                head64.rotateAngleY = f3 / 57.29578F;
                head64.rotateAngleX = f4 / 57.29578F;
                return head64;
            case 1:         //Arm
            case 2:         //Arm
                arm64.rotateAngleY = f3 / 57.29578F;
                arm64.rotateAngleX = f4 / 57.29578F;
                return arm64;
            case 3:         //Body
                body64.rotateAngleY = f3 / 57.29578F;
                body64.rotateAngleX = f4 / 57.29578F;
                return body64;
            case 4:         //Legs
            case 5:         //Legs
                leg64.rotateAngleY = f3 / 57.29578F;
                leg64.rotateAngleX = f4 / 57.29578F;
                return leg64;
        }
        return null;
    }

    @Override
    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, ent)
                .render(f5);
    }
}
