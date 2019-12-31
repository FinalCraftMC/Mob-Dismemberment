package br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.customnpcs;

import br.com.finalcraft.bettermobdismemberment.client.entity.EntityGib;
import br.com.finalcraft.bettermobdismemberment.client.render.gibmodels.GibModelTemplate;
import br.com.finalcraft.bettermobdismemberment.client.util.CustomNPCsUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.opengl.GL11;

public class GMTHumanNPC implements GibModelTemplate, INPCGibModel{

    public ModelRenderer head32;
    public ModelRenderer arm32;
    public ModelRenderer body32;
    public ModelRenderer leg32;

    public GMTHumanNPC(ModelBase modelBase) {
        head32 = new ModelRenderer(modelBase, 0, 0);
        head32.setTextureSize(64, 32);
        head32.addBox(-4F, -4F, -4F, 8, 8, 8);
        head32.setRotationPoint(0F, 20F, 0F);
        head32.rotateAngleX = 0F;
        head32.rotateAngleY = 0F;
        head32.rotateAngleZ = 0F;
        head32.mirror = false;

        arm32 = new ModelRenderer(modelBase, 40, 16);
        arm32.setTextureSize(64, 32);
        arm32.addBox(-2F, -6F, -2F, 4, 12, 4);
        arm32.setRotationPoint(0F, 22F, 0F);
        arm32.rotateAngleX = 0F;
        arm32.rotateAngleY = 0F;
        arm32.rotateAngleZ = 0F;
        arm32.mirror = false;

        body32 = new ModelRenderer(modelBase, 16, 16);
        body32.setTextureSize(64, 32);
        body32.addBox(-4F, -6F, -2F, 8, 12, 4);
        body32.setRotationPoint(0F, 22F, 0F);
        body32.rotateAngleX = 0F;
        body32.rotateAngleY = 0F;
        body32.rotateAngleZ = 0F;
        body32.mirror = false;

        leg32 = new ModelRenderer(modelBase, 0, 16);
        leg32.setTextureSize(64, 32);
        leg32.addBox(-2F, -6F, -2F, 4, 12, 4);
        leg32.setRotationPoint(0F, 24F, 0F);
        leg32.rotateAngleX = 0F;
        leg32.rotateAngleY = 0F;
        leg32.rotateAngleZ = 0F;
        leg32.mirror = false;
    }

    // ===============================================================================================
    // Selection Section
    // ===============================================================================================

    @Override
    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo){
        for(int i = 0; i < 6; i++){
            world.spawnEntityInWorld(new EntityGib(world, living, i, explo, this));
        }
        applyBloodParticles(living,explo);
    }

    @Override
    public boolean isApplicableFor(EntityLivingBase living){
        if (CustomNPCsUtil.isApplicableNPC(living) && CustomNPCsUtil.isEntityNPCHuman(living)) return true;
        return false;
    }

    // ===============================================================================================
    // Render Section
    // ===============================================================================================

    @Override
    public ModelRenderer setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityGib gib){
        switch ( gib.type ){
            case 0:         //Head
                head32.rotateAngleY = f3 / 57.29578F;
                head32.rotateAngleX = f4 / 57.29578F;
                return head32;
            case 1:         //Arm
            case 2:         //Arm
                arm32.rotateAngleY = f3 / 57.29578F;
                arm32.rotateAngleX = f4 / 57.29578F;
                return arm32;
            case 3:         //Body
                body32.rotateAngleY = f3 / 57.29578F;
                body32.rotateAngleX = f4 / 57.29578F;
                return body32;
            case 4:         //Legs
            case 5:         //Legs
                leg32.rotateAngleY = f3 / 57.29578F;
                leg32.rotateAngleX = f4 / 57.29578F;
                return leg32;
        }
        return null;
    }

    @Override
    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5) {
        float modelRenderSize = (float) CustomNPCsUtil.getModelRenderSize(ent.parent);
        GL11.glScalef(0.2F * modelRenderSize, 0.2F * modelRenderSize, 0.2F * modelRenderSize); //Needs to rescale to the NPC size
        setRotationAngles(f, f1, f2, f3, f4, f5, ent)
                .render(f5);
    }

    @Override
    public ResourceLocation getParentTexture(EntityGib gib){
        EntityCustomNpc entityCustomNpc = (EntityCustomNpc) gib.parent;
        return new ResourceLocation(entityCustomNpc.display.texture);
    }

    @Override
    public void entityCreationSizeDeffinition(EntityGib gib){
        float mds = 0.2F * (float) CustomNPCsUtil.getModelRenderSize(gib.parent); //ModelRenderSize
        float mdsOffset = mds; //ModelRenderSizeOffset
        if(gib.type == 0){
            gib.rotationYaw = gib.parent.rotationYawHead;
            gib.setSize(0.5F * mds, 0.5F * mds);
            gib.posY += 1.5D * mds;
        }
        else if(gib.type == 1 || gib.type == 2){
            gib.setSize(0.3F * mds, 0.4F * mds);

            double offset = 0.350D * mdsOffset;
            double offset1 = -0.250D * mdsOffset;

            if(gib.type == 2){
                offset *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posX += offset1 * Math.sin(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ -= offset1 * Math.cos(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 1.25D * mds;

            gib.prevRotationPitch = gib.rotationPitch = -90F;
        }
        else if(gib.type == 3){
            gib.setSize(0.5F * mds, 0.5F * mds);
            gib.posY += 1.0D * mds;
        }
        else if(gib.type == 4 || gib.type == 5){
            gib.setSize(0.3F * mds, 0.4F * mds);

            double offset = 0.125D * mdsOffset;

            if(gib.type == 5){
                offset *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 0.375D * mds;
        }
        else if(gib.type >= 6){
            gib.setSize(0.3F * mds, 0.4F * mds);

            double offset = 0.125D * mdsOffset;
            double offset1 = -0.250D * mdsOffset;

            if(gib.type % 2 == 1){
                offset *= -1D;
            }
            if(gib.type >= 8){
                offset1 *= -1D;
            }

            gib.posX += offset * Math.cos(Math.toRadians(gib.parent.renderYawOffset));
            gib.posZ += offset * Math.sin(Math.toRadians(gib.parent.renderYawOffset));

            gib.posY += 0.3125D * mds;
        }
    }


    @Override
    public float getRenderHeigh(EntityGib gib) {
        float mds = 0.2F * (float) CustomNPCsUtil.getModelRenderSize(gib.parent); //ModelRenderSize
        return mds * gib.height;
    }
}
