package br.com.finalcraft.bettermobdismemberment.client.render.gibmodels;

import br.com.finalcraft.bettermobdismemberment.client.entity.EntityGib;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class GMTSkeleton implements GibModelTemplate{

    public ModelRenderer head32;
    public ModelRenderer skeleArm;
    public ModelRenderer body32;
    public ModelRenderer skeleLeg;

    public GMTSkeleton(ModelBase modelBase) {
        skeleArm = new ModelRenderer(modelBase, 40, 16);
        skeleArm.setTextureSize(64, 32);
        skeleArm.addBox(-1F, -6F, -1F, 2, 12, 2);
        skeleArm.setRotationPoint(0F, 24F, 0F);
        skeleArm.rotateAngleX = 0F;
        skeleArm.rotateAngleY = 0F;
        skeleArm.rotateAngleZ = 0F;
        skeleArm.mirror = false;

        skeleLeg = new ModelRenderer(modelBase, 0, 16);
        skeleLeg.setTextureSize(64, 32);
        skeleLeg.addBox(-1F, -6F, -1F, 2, 12, 2);
        skeleLeg.setRotationPoint(0F, 24F, 0F);
        skeleLeg.rotateAngleX = 0F;
        skeleLeg.rotateAngleY = 0F;
        skeleLeg.rotateAngleZ = 0F;
        skeleLeg.mirror = false;

        head32 = new ModelRenderer(modelBase, 0, 0);
        head32.setTextureSize(64, 32);
        head32.addBox(-4F, -4F, -4F, 8, 8, 8);
        head32.setRotationPoint(0F, 20F, 0F);
        head32.rotateAngleX = 0F;
        head32.rotateAngleY = 0F;
        head32.rotateAngleZ = 0F;
        head32.mirror = false;

        body32 = new ModelRenderer(modelBase, 16, 16);
        body32.setTextureSize(64, 32);
        body32.addBox(-4F, -6F, -2F, 8, 12, 4);
        body32.setRotationPoint(0F, 22F, 0F);
        body32.rotateAngleX = 0F;
        body32.rotateAngleY = 0F;
        body32.rotateAngleZ = 0F;
        body32.mirror = false;
    }

    // ===============================================================================================
    // Selection Section
    // ===============================================================================================

    @Override
    public void spawnEntityGibInWorld(World world, EntityLivingBase living, Entity explo){
        for(int i = 0; i < 6; i++){
            world.spawnEntityInWorld(new EntityGib(world, living, i, explo, this));
        }
    }

    @Override
    public boolean isApplicableFor(EntityLivingBase living){
        if (living instanceof EntitySkeleton) return true;  //EntityPigZombie already extends EntityZombie
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
                skeleArm.rotateAngleY = f3 / 57.29578F;
                skeleArm.rotateAngleX = f4 / 57.29578F;
                return skeleArm;
            case 3:         //Body
                body32.rotateAngleY = f3 / 57.29578F;
                body32.rotateAngleX = f4 / 57.29578F;
                return body32;
            case 4:         //Legs
            case 5:         //Legs
                skeleLeg.rotateAngleY = f3 / 57.29578F;
                skeleLeg.rotateAngleX = f4 / 57.29578F;
                return skeleLeg;
        }
        return null;
    }
    
    @Override
    public void render(EntityGib ent, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, ent)
                .render(f5);
    }
}
